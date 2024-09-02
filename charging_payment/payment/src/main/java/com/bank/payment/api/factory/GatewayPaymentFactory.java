package com.bank.payment.api.factory;

import com.bank.payment.api.model.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Author: ASOU SAFARI
 * Date:9/1/24
 * Time:11:25 AM
 */
public class GatewayPaymentFactory implements PaymentFactory {

    @Override
    public Payment createPayment(PaymentUser paymentUser,
                                  BigDecimal amount,
                                 boolean isSuccess,
                                 PaymentType paymentType) {

        return GatewayPayment.builder()
                .amount(amount)
                .isDeleted(isSuccess)
                .paymentType(paymentType)
                .isDeleted(false)
                .paymentStatus(PaymentStatus.COMPLETED)
                .createdDate(LocalDateTime.now())
                .build();
    }
}
