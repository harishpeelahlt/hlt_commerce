package com.hltcommerce.user.dto;

public class AuthResponse {
    private String tokenType;
    private String accessToken;
    private String firstName;
    private String lastName;
    private String email;

    public AuthResponse() {}

    public AuthResponse(String tokenType, String accessToken, String firstName, String lastName, String email) {
        this.tokenType = tokenType;
        this.accessToken = accessToken;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }

    public String getTokenType() { return tokenType; }
    public void setTokenType(String tokenType) { this.tokenType = tokenType; }
    public String getAccessToken() { return accessToken; }
    public void setAccessToken(String accessToken) { this.accessToken = accessToken; }
    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }
    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
}
