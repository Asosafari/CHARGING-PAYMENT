package com.company.charging.api.dto;

import com.company.charging.api.model.TransactionType;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

/**
 * Author: ASOU SAFARI
 * Date:9/1/24
 * Time:1:36 PM
 */
@Builder
@Data
public class DirectPaymentRequest {

    private Long userId;
    private Long chargingPlanId;
    private TransactionType transactionType;
    private BigDecimal amount;
    private String publicKey;

}
