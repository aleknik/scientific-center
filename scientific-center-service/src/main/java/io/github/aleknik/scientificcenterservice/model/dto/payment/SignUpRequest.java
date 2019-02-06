package io.github.aleknik.scientificcenterservice.model.dto.payment;

import javax.validation.constraints.NotEmpty;

public class SignUpRequest {

    @NotEmpty
    private String email;

    public SignUpRequest() {
    }

    public SignUpRequest(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
