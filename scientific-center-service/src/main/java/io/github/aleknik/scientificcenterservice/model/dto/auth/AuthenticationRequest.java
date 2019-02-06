package io.github.aleknik.scientificcenterservice.model.dto.auth;

import javax.validation.constraints.NotEmpty;

/**
 * Represents user's authentication request.
 * Used when user tries to log in to the system.
 */
public class AuthenticationRequest {

    /**
     * User's email.
     */
    @NotEmpty
    private String email;

    /**
     * User's password.
     */
    @NotEmpty
    private String password;

    public AuthenticationRequest() {
    }

    public AuthenticationRequest(@NotEmpty String email, @NotEmpty String password) {
        this.email = email;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
