package com.hltcommerce.common.orm;

import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.web.filter.OncePerRequestFilter;

@AutoConfiguration
@EnableJpaAuditing(auditorAwareRef = "auditorAware")
public class CommonOrmAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public AuditorAware<String> auditorAware() {
        return new TenantAwareAuditor();
    }

    @Bean
    @ConditionalOnMissingBean
    public OncePerRequestFilter tenantFilter() {
        return new TenantFilter();
    }
}

