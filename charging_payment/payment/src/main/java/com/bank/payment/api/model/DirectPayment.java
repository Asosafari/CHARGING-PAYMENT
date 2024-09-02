package com.bank.payment.api.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.DecimalMin;
import lombok.Builder;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Author: ASOU SAFARI
 * Date:8/31/24
 * Time:11:35 PM
 */
@Entity
@Table(name = "payments_direct")
public class DirectPayment extends Payment{

    @Builder
    public DirectPayment(Long id,
                         PaymentUser user,
                         @DecimalMin(value = "0.00") BigDecimal amount,
                         boolean isSuccess,
                         PaymentStatus paymentStatus,
                         PaymentType paymentType,
                         LocalDateTime createdDate,
                         boolean isDeleted) {

        super(id, user, amount, isSuccess, paymentStatus, paymentType, createdDate, isDeleted);
    }
}
