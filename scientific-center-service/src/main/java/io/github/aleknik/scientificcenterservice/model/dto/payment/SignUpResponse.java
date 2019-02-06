package io.github.aleknik.scientificcenterservice.model.dto.payment;

public class SignUpResponse {

    private String clientId;

    public SignUpResponse() {
    }

    public SignUpResponse(String clientId) {
        this.clientId = clientId;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }
}
