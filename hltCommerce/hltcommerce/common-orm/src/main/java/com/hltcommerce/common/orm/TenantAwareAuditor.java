package com.hltcommerce.common.orm;

import org.springframework.data.domain.AuditorAware;

import java.util.Optional;

public class TenantAwareAuditor implements AuditorAware<String> {
    @Override
    public Optional<String> getCurrentAuditor() {
        // In real impl, resolve from SecurityContext. For scaffold, tie to tenant.
        String tenant = TenantContext.getTenantId();
        return Optional.ofNullable(tenant != null ? tenant + ":system" : "system");
    }
}

