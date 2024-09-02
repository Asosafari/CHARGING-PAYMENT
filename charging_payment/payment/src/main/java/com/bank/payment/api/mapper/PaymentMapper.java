package com.bank.payment.api.mapper;

import com.bank.payment.api.dto.PaymentRequest;
import com.bank.payment.api.factory.PaymentFactory;
import com.bank.payment.api.factory.PaymentFactoryProvider;
import com.bank.payment.api.model.Payment;
import com.bank.payment.api.repository.PaymentUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * Author: ASOU SAFARI
 * Date:9/1/24
 * Time:11:01 PM
 */
@Component
@RequiredArgsConstructor
public class PaymentMapper {

   private final PaymentUserRepository paymentUserRepository;

   public Payment DtoToModel(PaymentRequest paymentRequest){
       PaymentFactory factory = PaymentFactoryProvider.getPaymentFactory(
               paymentRequest.getPaymentType().toString());
       return factory.createPayment(paymentUserRepository.findPaymentUserByChargingUserId(paymentRequest.getUserId()),
               paymentRequest.getAmount(),true,paymentRequest.getPaymentType());
   }

}
