package com.hltcommerce.user.dto;

public class UserProfile {
    private Long id;
    private String tenantId;
    private String firstName;
    private String lastName;
    private String email;
    private String role;

    public UserProfile() {}

    public UserProfile(Long id, String tenantId, String firstName, String lastName, String email, String role) {
        this.id = id;
        this.tenantId = tenantId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.role = role;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getTenantId() { return tenantId; }
    public void setTenantId(String tenantId) { this.tenantId = tenantId; }
    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }
    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }
}
