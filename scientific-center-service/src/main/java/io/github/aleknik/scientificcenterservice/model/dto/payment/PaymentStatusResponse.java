package io.github.aleknik.scientificcenterservice.model.dto.payment;

import io.github.aleknik.scientificcenterservice.model.dto.payment.PaymentStatus;

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
