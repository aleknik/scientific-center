package io.github.aleknik.scientificcenterservice.model.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;

public class PaymentRequest {

    @NotEmpty
    private String buyerId;

    @NotEmpty
    private String productId;

    @NotEmpty
    private String clientId;

    @Positive
    @NotNull
    private BigDecimal amount;

    @NotEmpty
    private String successUrl;

    @NotEmpty
    private String errorUrl;

    private boolean subscriptionBased;

    public PaymentRequest() {
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getSuccessUrl() {
        return successUrl;
    }

    public void setSuccessUrl(String successUrl) {
        this.successUrl = successUrl;
    }

    public String getErrorUrl() {
        return errorUrl;
    }

    public void setErrorUrl(String errorUrl) {
        this.errorUrl = errorUrl;
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

    public boolean isSubscriptionBased() {
        return subscriptionBased;
    }

    public void setSubscriptionBased(boolean subscriptionBased) {
        this.subscriptionBased = subscriptionBased;
    }
}