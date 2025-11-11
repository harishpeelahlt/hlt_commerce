package com.hltcommerce.common.orm;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

public class TenantFilter extends OncePerRequestFilter {
    public static final String TENANT_HEADER = "X-hltcommerce-Tenant";

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        try {
            String tenantId = resolveTenant(request);
            if (StringUtils.hasText(tenantId)) {
                TenantContext.setTenantId(tenantId);
            }
            filterChain.doFilter(request, response);
        } finally {
            TenantContext.clear();
        }
    }

    private String resolveTenant(HttpServletRequest request) {
        String headerTenant = request.getHeader(TENANT_HEADER);
        if (StringUtils.hasText(headerTenant)) {
            return headerTenant;
        }
        String host = request.getServerName();
        // Expecting subdomain.tenant.hltcommerce.com or tenant.localhost
        if (host != null) {
            String[] parts = host.split("\\.");
            if (parts.length >= 3) {
                return parts[0];
            }
        }
        return null;
    }
}

