package com.bank.payment.api.sterategy;

import com.bank.payment.api.dto.PaymentRequest;

public interface PaymentStrategy {

    boolean processPayment(PaymentRequest paymentRequest);
}
