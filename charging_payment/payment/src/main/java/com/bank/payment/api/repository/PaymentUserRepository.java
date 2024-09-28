package com.bank.payment.api.repository;

import com.bank.payment.api.model.PaymentUser;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;

import javax.swing.text.html.Option;
import java.math.BigDecimal;
import java.util.Optional;

public interface PaymentUserRepository extends JpaRepository<PaymentUser,Long> {
    Optional<PaymentUser> findPaymentUserByUsername(String Username);
    @Modifying
    @Transactional
    @Query("UPDATE PaymentUser p SET p.accountBalance = p.accountBalance + :amount WHERE p.id = :userId")
    int updateAccountBalance(@Param("userId") Long userId, @Param("amount") BigDecimal amount);

}
