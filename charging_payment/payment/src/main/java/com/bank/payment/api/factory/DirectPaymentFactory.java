package com.bank.payment.api.factory;

import com.bank.payment.api.model.*;
import lombok.Builder;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Author: ASOU SAFARI
 * Date:9/1/24
 * Time:11:22 AM
 */

public class DirectPaymentFactory implements PaymentFactory{
    @Override
    public Payment createPayment(PaymentUser paymentUser,
                                 BigDecimal amount,
                                 boolean isSuccess,
                                 PaymentType paymentType) {

        return DirectPayment.builder()
                .createdDate(LocalDateTime.now())
                .paymentType(paymentType)
                .amount(amount)
                .isSuccess(isSuccess)
                .createdDate(LocalDateTime.now())
                .paymentStatus(PaymentStatus.COMPLETED)
                .isDeleted(false)
                .build();


    }
}
