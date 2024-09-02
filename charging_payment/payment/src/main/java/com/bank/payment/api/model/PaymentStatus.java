package com.bank.payment.api.model;

public enum PaymentStatus {
    PENDING("PENDING"),
    COMPLETED("COMPLETED"),
    FAILED("FAILED");

    PaymentStatus(String status) {
    }
}
