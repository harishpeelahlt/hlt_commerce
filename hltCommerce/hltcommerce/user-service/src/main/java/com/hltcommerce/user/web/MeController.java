package com.hltcommerce.user.web;

import com.hltcommerce.user.dto.UserProfile;
import com.hltcommerce.user.service.CurrentUserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
public class MeController {

    private final CurrentUserService currentUserService;

    public MeController(CurrentUserService currentUserService) {
        this.currentUserService = currentUserService;
    }

    @GetMapping("/me")
    public ResponseEntity<UserProfile> me() {
        var user = currentUserService.getUserOrThrow();
        var dto = new UserProfile(
                user.getId(),
                user.getTenantId(),
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                user.getRole()
        );
        return ResponseEntity.ok(dto);
    }
}
