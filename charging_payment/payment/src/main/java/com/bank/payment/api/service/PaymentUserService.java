package com.bank.payment.api.service;

import com.bank.payment.api.dto.PaymentUserRequest;
import com.bank.payment.api.factory.DirectPaymentFactory;
import org.springframework.context.annotation.Primary;

/**
 * Author: ASOU SAFARI
 * Date:9/2/24
 * Time:2:48 AM
 */

public interface PaymentUserService {

    boolean registerUserPayment(PaymentUserRequest paymentUserRequest);
}
