package com.bank.payment.api.factory;

import com.bank.payment.api.model.Payment;
import com.bank.payment.api.model.PaymentStatus;
import com.bank.payment.api.model.PaymentType;
import com.bank.payment.api.model.PaymentUser;
import jakarta.validation.constraints.DecimalMin;

import java.math.BigDecimal;

public interface PaymentFactory {

    Payment createPayment(PaymentUser paymentUser,
                          @DecimalMin(value = "0.00") BigDecimal amount,
                          boolean isSuccess,
                          PaymentType paymentType);
}
