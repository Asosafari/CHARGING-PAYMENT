package com.bank.payment.api.service;

import com.bank.payment.api.model.Key;
import com.bank.payment.api.dto.PaymentRequest;
import com.bank.payment.api.mapper.PaymentMapper;
import com.bank.payment.api.model.Payment;
import com.bank.payment.api.model.PaymentStatus;
import com.bank.payment.api.model.PaymentType;
import com.bank.payment.api.repository.KeyRepository;
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
    private final KryService kryService;
    private final PaymentMapper paymentMapper;


    @Override
    @Transactional
    public boolean processPayment(PaymentRequest paymentRequest) {
        if (paymentRequest.getPaymentType().equals(PaymentType.DIRECT)) {
            try {
                Optional<Key> key = kryService.findKey(paymentRequest.getUserId());

                if (validateDecryptedData(key.get().getToken(), paymentRequest)) {
                    paymentRepository.updateAccountBalance(paymentRequest.getUserId(),paymentRequest.getAmount());
                    Payment payment = paymentMapper.DtoToModel(paymentRequest);
                    payment.setPaymentStatus(PaymentStatus.COMPLETED);
                    paymentRepository.save(payment);
                    return true;
                } else {
                    return false;
                }
            } catch (Exception e) {
                log.error("Failed to completed payment for order{}", paymentRequest.getPaymentType());
                return false;
            }
        }
        log.error("Invalid payment type for GatewayPaymentService{}", paymentRequest.getPaymentType());
        return false;
    }

private boolean validateDecryptedData(String decryptedData, PaymentRequest paymentRequest) {
    String expectedData = paymentRequest.getUserId() + "|" + paymentRequest.getAmount();
    return decryptedData.equals(expectedData);
}

}
