package com.hltcommerce.auth.web;

import com.hltcommerce.common.orm.TenantContext;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.server.authorization.OAuth2Authorization;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationService;
import org.springframework.security.oauth2.server.authorization.authentication.OAuth2AccessTokenAuthenticationToken;
import org.springframework.security.oauth2.server.authorization.OAuth2TokenType;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

@RestController
public class CheckTokenController {

    private final OAuth2AuthorizationService authorizationService;

    public CheckTokenController(OAuth2AuthorizationService authorizationService) {
        this.authorizationService = authorizationService;
    }

    @PostMapping("/oauth/check_token")
    public ResponseEntity<Map<String, Object>> checkToken(@RequestParam("token") String token) {
        if (!StringUtils.hasText(token)) {
            return ResponseEntity.badRequest().build();
        }
        OAuth2Authorization auth = authorizationService.findByToken(token, OAuth2TokenType.ACCESS_TOKEN);
        if (auth == null) {
            return ResponseEntity.status(401).build();
        }
        Map<String, Object> claims = new HashMap<>();
        claims.put("active", true);
        claims.put("client_id", auth.getRegisteredClientId());
        claims.put("sub", auth.getPrincipalName());
        claims.put("user_name", auth.getPrincipalName());
        String tenant = (String) auth.getAttributes().getOrDefault("tenant_id", TenantContext.getTenantId());
        claims.put("tenant_id", tenant);
        Instant exp = auth.getAccessToken() != null ? auth.getAccessToken().getToken().getExpiresAt() : null;
        if (exp != null) claims.put("exp", exp.getEpochSecond());
        return ResponseEntity.ok(claims);
    }
}

