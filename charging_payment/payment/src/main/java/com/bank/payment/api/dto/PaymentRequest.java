package com.bank.payment.api.dto;

import com.bank.payment.api.model.PaymentType;
import lombok.Data;

import java.math.BigDecimal;

/**
 * Author: ASOU SAFARI
 * Date:9/1/24
 * Time:8:43 PM
 */
@Data
public class PaymentRequest {
    private String username;
    private BigDecimal amount;
    private PaymentType paymentType;

}
