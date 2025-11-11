package com.hltcommerce.auth.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "hltcommerce.auth")
public record AuthProperties(String tokenStore) {
}

