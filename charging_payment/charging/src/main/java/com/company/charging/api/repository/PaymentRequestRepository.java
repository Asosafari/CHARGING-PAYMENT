package com.company.charging.api.repository;

import com.company.charging.api.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;

/**
 * Author: ASOU SAFARI
 * Date:9/1/24
 * Time:3:08 PM
 */
@Repository
public interface PaymentRequestRepository extends JpaRepository<User,Long> {
    @Procedure(procedureName = "charging_account")
    BigDecimal charging(Long userId, BigDecimal ratePerUnit);

    @Procedure(procedureName = "get_authorized_bank_users")
    String findPrivateKeyCheckout(Long id);

}
