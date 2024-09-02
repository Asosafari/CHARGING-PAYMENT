package com.bank.payment.api.repository;

import com.bank.payment.api.model.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;

import java.math.BigDecimal;

public interface PaymentRepository extends JpaRepository<Payment,Long> {

    @Procedure(procedureName = "update_account_balance")
    boolean updateAccountBalance(Long PaymentId, BigDecimal amount);
}
