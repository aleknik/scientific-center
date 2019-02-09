package io.github.aleknik.scientificcenterservice.model.dto.auth;

import javax.validation.constraints.NotEmpty;

/**
 * Represents user's authentication request.
 * Used when user tries to log in to the system.
 */
public class AuthenticationRequest {

    /**
     * User's username.
     */
    @NotEmpty
    private String username;

    /**
     * User's password.
     */
    @NotEmpty
    private String password;

    public AuthenticationRequest() {
    }

    public AuthenticationRequest(@NotEmpty String username, @NotEmpty String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
