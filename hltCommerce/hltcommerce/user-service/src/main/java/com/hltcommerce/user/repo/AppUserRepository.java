package com.hltcommerce.user.repo;

import com.hltcommerce.user.model.AppUserModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AppUserRepository extends JpaRepository<AppUserModel, Long> {
    Optional<AppUserModel> findByEmailAndTenantId(String email, String tenantId);
}

