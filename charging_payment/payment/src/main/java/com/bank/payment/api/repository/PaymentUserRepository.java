package com.bank.payment.api.repository;

import com.bank.payment.api.model.PaymentUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;

import java.math.BigDecimal;

public interface PaymentUserRepository extends JpaRepository<PaymentUser,Long> {
    PaymentUser findPaymentUserByChargingUserId(Long chargingUserId);


}
