package com.hltcommerce.user.web;

import com.hltcommerce.common.orm.TenantContext;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.OAuth2AuthenticatedPrincipal;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

public class TenantValidationFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && auth.isAuthenticated() && auth.getPrincipal() instanceof OAuth2AuthenticatedPrincipal principal) {
            String tokenTenant = principal.getAttribute("tenant_id");
            String ctxTenant = TenantContext.getTenantId();
            if (tokenTenant != null && ctxTenant != null && !tokenTenant.equals(ctxTenant)) {
                response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                response.getWriter().write("Tenant mismatch");
                return;
            }
        }
        filterChain.doFilter(request, response);
    }
}

