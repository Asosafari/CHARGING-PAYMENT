package com.bank.payment.api.dto;

import com.bank.payment.api.model.PaymentType;
import lombok.Data;

import java.math.BigDecimal;

/**
 * Author: ASOU SAFARI
 * Date:9/2/24
 * Time:2:49 AM
 */
@Data
public class PaymentUserRequest {
    private Long  chargingUserId;
    private BigDecimal amount;
    private String publicKey;
    private String encryptedData;
    private PaymentType paymentType;
    private BigDecimal accountBalance;
}
