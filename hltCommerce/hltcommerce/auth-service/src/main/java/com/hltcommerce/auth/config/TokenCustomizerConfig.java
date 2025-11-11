package com.hltcommerce.auth.config;

import com.hltcommerce.common.orm.TenantContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.server.authorization.token.OAuth2TokenClaimsContext;
import org.springframework.security.oauth2.server.authorization.token.OAuth2TokenCustomizer;

@Configuration
public class TokenCustomizerConfig {

    @Bean
    public OAuth2TokenCustomizer<OAuth2TokenClaimsContext> tokenCustomizer() {
        return context -> {
            var claims = context.getClaims();
            String tenant = TenantContext.getTenantId();
            if (tenant != null) {
                claims.claim("tenant_id", tenant);
            }
            claims.claim("userId", context.getPrincipal().getName());
        };
    }
}

