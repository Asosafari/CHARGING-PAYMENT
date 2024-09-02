package com.bank.payment.api.sterategy;

import com.bank.payment.api.crypto.GenerateKey;
import com.bank.payment.api.model.Key;
import com.bank.payment.api.dto.PaymentUserRequest;
import com.bank.payment.api.mapper.PaymentUserMapper;
import com.bank.payment.api.model.PaymentUser;
import com.bank.payment.api.repository.PaymentUserRepository;
import com.bank.payment.api.service.PaymentUserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * Author: ASOU SAFARI
 * Date:9/2/24
 * Time:2:52 AM
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class PaymentUserServiceImpl implements PaymentUserService {
   private final GenerateKey generateKey;
   private final PaymentUserRepository paymentUserRepository;
   private final PaymentUserMapper paymentUserMapper;


    @Override
    @Transactional
    public String registerUserPayment(PaymentUserRequest paymentUserRequest) {
        try {
            PaymentUser paymentUser = paymentUserMapper.mapToModel(paymentUserRequest);

            String token = GenerateKey.generateToken();

            Key key = new Key();
            key.setToken(token);
            key.setPaymentUser(paymentUser);

            paymentUser.setKey(key);
            paymentUserRepository.save(paymentUser);

            return token;
        } catch (Exception e) {
            log.error("Register failed error", e);
            return "Register failed";
        }
    }

}
