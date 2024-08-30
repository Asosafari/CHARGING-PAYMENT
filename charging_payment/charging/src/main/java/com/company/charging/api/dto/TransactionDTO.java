package com.company.charging.api.dto;

import com.company.charging.api.model.TransactionType;
import lombok.Builder;
import lombok.Data;


import java.math.BigDecimal;


/**
 * Author: ASOU SAFARI
 * Date:8/30/24
 * Time:5:11 PM
 */
@Data
@Builder
public class TransactionDTO {
    private Long id;
    private String chargingPlanName;
    private String username;
    private BigDecimal amount;
    private boolean isSuccess;
    private boolean isDeleted;
    private TransactionType transactionType;


}
