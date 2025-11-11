package com.hltcommerce.user.controller;

import com.hltcommerce.common.orm.TenantContext;
import com.hltcommerce.user.service.AuthService;
import com.hltcommerce.user.dto.AuthResponse;
import com.hltcommerce.user.dto.LoginRequest;
import com.hltcommerce.user.dto.RegisterRequest;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@Valid @RequestBody RegisterRequest request) {
        // Fallback: allow tenantId from body if header filter didn't set it
        if (!StringUtils.hasText(TenantContext.getTenantId()) && StringUtils.hasText(request.getTenantId())) {
            TenantContext.setTenantId(request.getTenantId());
        }
        return ResponseEntity.ok(authService.register(request));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody LoginRequest request) {
        // Fallback: allow tenantId from body if header filter didn't set it
        if (!StringUtils.hasText(TenantContext.getTenantId()) && StringUtils.hasText(request.getTenantId())) {
            TenantContext.setTenantId(request.getTenantId());
        }
        return ResponseEntity.ok(authService.login(request));
    }
}
