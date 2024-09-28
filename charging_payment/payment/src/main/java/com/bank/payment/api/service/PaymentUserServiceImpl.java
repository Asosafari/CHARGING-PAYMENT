package com.bank.payment.api.service;

import com.bank.payment.api.dto.PaymentUserRequest;
import com.bank.payment.api.mapper.PaymentMapper;
import com.bank.payment.api.mapper.PaymentUserMapper;
import com.bank.payment.api.model.PaymentUser;
import com.bank.payment.api.repository.PaymentUserRepository;
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
   private final PaymentUserRepository paymentUserRepository;
   private final PaymentUserMapper paymentUserMapper;


    @Override
    @Transactional
    public boolean registerUserPayment(PaymentUserRequest paymentUserRequest) {
        if (paymentUserRepository.findPaymentUserByUsername(paymentUserRequest.getUsername()).isEmpty()){
            PaymentUser paymentUser = paymentUserMapper.mapToModel(paymentUserRequest);
            paymentUserRepository.save(paymentUser);
            return true;
        }
        log.error("User exist");
        return false;
    }

}
