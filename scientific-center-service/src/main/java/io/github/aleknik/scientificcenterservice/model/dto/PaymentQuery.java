package io.github.aleknik.scientificcenterservice.model.dto;

import javax.validation.constraints.NotEmpty;

public class PaymentQuery {

    @NotEmpty
    private String buyerId;

    @NotEmpty
    private String productId;

    @NotEmpty
    private String clientId;

    public PaymentQuery() {
    }

    public String getBuyerId() {
        return buyerId;
    }

    public void setBuyerId(String buyerId) {
        this.buyerId = buyerId;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }
}
