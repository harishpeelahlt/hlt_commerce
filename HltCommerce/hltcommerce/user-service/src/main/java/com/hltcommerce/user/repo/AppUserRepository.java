package com.hltcommerce.user.repo;

import com.hltcommerce.user.domain.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AppUserRepository extends JpaRepository<AppUser, Long> {
    Optional<AppUser> findByEmailAndTenantId(String email, String tenantId);
}

