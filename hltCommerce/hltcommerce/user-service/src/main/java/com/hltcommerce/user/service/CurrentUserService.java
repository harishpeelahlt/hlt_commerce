package com.hltcommerce.user.service;

import com.hltcommerce.user.model.AppUserModel;
import com.hltcommerce.user.repo.AppUserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;


@Component
public class CurrentUserService {

    private final AppUserRepository userRepository;

    public CurrentUserService(AppUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    public AppUserModel getCurrentUser() {
        Authentication auth = getAuthentication();
        return (AppUserModel) userRepository.findByEmail(auth.getName())
                .orElseThrow(() -> new IllegalStateException("Logged-in user not found in database"));
    }

}
