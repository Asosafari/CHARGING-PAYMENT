package com.bank.payment.api.service;

import com.bank.payment.api.model.*;
import com.bank.payment.api.dto.PaymentRequest;
import com.bank.payment.api.mapper.PaymentMapper;
import com.bank.payment.api.repository.PaymentRepository;
import com.bank.payment.api.repository.PaymentUserRepository;
import com.bank.payment.api.sterategy.PaymentStrategy;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Author: ASOU SAFARI
 * Date:9/1/24
 * Time:12:46 AM
 */
@Service
@RequiredArgsConstructor
@Slf4j
@Primary
public class DirectPaymentService implements PaymentStrategy {
    private final PaymentRepository paymentRepository;
    private final PaymentMapper paymentMapper;
    private final PaymentUserRepository paymentUserRepository;


    @Override
    @Transactional
    public boolean processPayment(PaymentRequest paymentRequest) {
        Optional<PaymentUser> paymentUser = paymentUserRepository.findPaymentUserByUsername(paymentRequest.getUsername());
        if (paymentUser.isPresent() && paymentRequest.getPaymentType().equals(PaymentType.DIRECT)){
            try {
                        paymentUserRepository.updateAccountBalance(paymentUser.get().getId(), paymentRequest.getAmount());
                        Payment payment = paymentMapper.DtoToModel(paymentRequest);
                        payment.setPaymentStatus(PaymentStatus.COMPLETED);
                        payment.setPaymentUser(paymentUser.get());
                        payment.setPaymentType(paymentRequest.getPaymentType());
                        paymentRepository.save(payment);
                        return true;

            } catch (Exception e) {
                log.error("Failed to completed payment for order{}", paymentRequest.getPaymentType());
                return false;
            }
        }
        log.error("UserNot found Or Invalid payment type for GatewayPaymentService{}", paymentRequest.getPaymentType());
        return false;
    }
}
