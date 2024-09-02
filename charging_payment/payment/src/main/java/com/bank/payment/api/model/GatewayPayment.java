package com.bank.payment.api.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.DecimalMin;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Author: ASOU SAFARI
 * Date:8/31/24
 * Time:11:39 PM
 */
@Entity
@Getter
@Setter
@Table(name = "gateway_payment")
public class GatewayPayment extends Payment{


    @Column(name = "card_number",nullable = false)
    private String cardNumber;
    @Column(name = "cvv",nullable = false)
    private String cvv;
    @Column(name = "date_expiry")
    private String dateOfExpiry;
    @Builder
    public GatewayPayment(Long id,
                          com.bank.payment.api.model.PaymentUser PaymentUser,
                          @DecimalMin(value = "0.00") BigDecimal amount,
                          boolean isSuccess,
                          PaymentStatus paymentStatus,
                          PaymentType paymentType,
                          LocalDateTime createdDate,
                          boolean isDeleted,
                          String cardNumber,
                          String cvv,
                          String dateOfExpiry) {
        super(id, PaymentUser, amount, isSuccess, paymentStatus, paymentType, createdDate, isDeleted);
        this.cardNumber = cardNumber;
        this.cvv = cvv;
        this.dateOfExpiry = dateOfExpiry;
    }
}
