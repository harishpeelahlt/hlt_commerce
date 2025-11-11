package com.hltcommerce.user.service;

import com.hltcommerce.common.orm.TenantContext;
import com.hltcommerce.user.model.AppUserModel;
import com.hltcommerce.user.repo.AppUserRepository;
import com.hltcommerce.user.dto.AuthResponse;
import com.hltcommerce.user.dto.LoginRequest;
import com.hltcommerce.user.dto.RegisterRequest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;

@Service
public class AuthService {

    private final AppUserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public AuthService(AppUserRepository userRepository, PasswordEncoder passwordEncoder, JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    @Transactional
    public AuthResponse register(RegisterRequest req) {
        String tenantId = requiredTenant();
        userRepository.findByEmailAndTenantId(req.getEmail(), tenantId).ifPresent(u -> {
            throw new IllegalArgumentException("Email already registered for this tenant");
        });
        AppUserModel user = new AppUserModel();
        user.setFirstName(req.getFirstName());
        user.setLastName(req.getLastName());
        user.setEmail(req.getEmail());
        user.setPasswordHash(passwordEncoder.encode(req.getPassword()));
        user.setRole("ROLE_USER");
        user.setTenantId(tenantId);
        try {
            user = userRepository.save(user);
        } catch (DataIntegrityViolationException e) {
            throw new IllegalArgumentException("Email already registered");
        }
        return issueToken(user);
    }

    @Transactional(readOnly = true)
    public AuthResponse login(LoginRequest req) {
        String tenantId = requiredTenant();
        AppUserModel user = userRepository.findByEmailAndTenantId(req.getEmail(), tenantId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid credentials"));
        if (!passwordEncoder.matches(req.getPassword(), user.getPasswordHash())) {
            throw new IllegalArgumentException("Invalid credentials");
        }
        return issueToken(user);
    }

    private String requiredTenant() {
        String tenantId = TenantContext.getTenantId();
        if (tenantId == null || tenantId.isBlank()) {
            throw new IllegalStateException("Missing tenant. Provide '" + com.hltcommerce.common.orm.TenantFilter.TENANT_HEADER + "' header");
        }
        return tenantId;
    }

    private AuthResponse issueToken(AppUserModel user) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("tenant_id", user.getTenantId());
        claims.put("role", user.getRole());
        String token = jwtUtil.createToken(user.getEmail(), claims);
        return new AuthResponse("Bearer", token, user.getFirstName(), user.getLastName(), user.getEmail());
    }
}
