package com.hltcommerce.user.service;

import com.hltcommerce.common.orm.TenantContext;
import com.hltcommerce.user.model.AppUserModel;
import com.hltcommerce.user.repo.AppUserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.OAuth2AuthenticatedPrincipal;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class CurrentUserService {

    private final AppUserRepository userRepository;

    public CurrentUserService(AppUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    public Optional<OAuth2AuthenticatedPrincipal> getPrincipal() {
        Authentication auth = getAuthentication();
        if (auth == null || !auth.isAuthenticated()) {
            return Optional.empty();
        }
        Object principal = auth.getPrincipal();
        if (principal instanceof OAuth2AuthenticatedPrincipal p) {
            return Optional.of(p);
        }
        return Optional.empty();
    }

    public Optional<String> getEmail() {
        return getPrincipal().map(p -> {
            // Prefer name(), else fall back to common attributes
            String name = p.getName();
            if (name != null && !name.isBlank()) return name;
            String email = p.getAttribute("email");
            if (email != null && !email.isBlank()) return email;
            String userName = p.getAttribute("user_name");
            return (userName != null && !userName.isBlank()) ? userName : null;
        });
    }

    public Optional<String> getTenantId() {
        // Prefer header-parsed TenantContext; else try token attribute
        String ctx = TenantContext.getTenantId();
        if (ctx != null && !ctx.isBlank()) return Optional.of(ctx);
        return getPrincipal().map(p -> p.getAttribute("tenant_id"));
    }

    public Optional<AppUserModel> getUserOptional() {
        Optional<String> emailOpt = getEmail();
        Optional<String> tenantOpt = getTenantId();
        if (emailOpt.isEmpty() || tenantOpt.isEmpty()) return Optional.empty();
        return userRepository.findByEmailAndTenantId(emailOpt.get(), tenantOpt.get());
    }

    public AppUserModel getUserOrThrow() {
        String email = getEmail().orElseThrow(() ->
                new IllegalStateException("No authenticated user email present in security context"));
        String tenantId = getTenantId().orElseThrow(() ->
                new IllegalStateException("Missing tenant. Provide '" + com.hltcommerce.common.orm.TenantFilter.TENANT_HEADER + "' header"));
        return userRepository.findByEmailAndTenantId(email, tenantId)
                .orElseThrow(() -> new IllegalStateException("Logged-in user not found in database"));
    }
}
