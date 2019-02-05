package io.github.aleknik.scientificcenterservice.model.dto;

public class PaymentStatusResponse {

    private PaymentStatus paymentStatus;

    public PaymentStatusResponse() {
    }

    public PaymentStatus getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(PaymentStatus paymentStatus) {
        this.paymentStatus = paymentStatus;
    }
}
