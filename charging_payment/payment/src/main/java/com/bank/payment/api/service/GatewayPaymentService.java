package com.bank.payment.api.service;

import com.bank.payment.api.dto.PaymentRequest;
import com.bank.payment.api.sterategy.PaymentStrategy;


/**
 * Author: ASOU SAFARI
 * Date:9/1/24
 * Time:12:49 AM
 */
public class GatewayPaymentService implements PaymentStrategy {
    @Override
    public boolean processPayment(PaymentRequest paymentRequest) {
        //implement GatewayPaymentService logic
        return true;
    }

}
