package com.company.charging.api.dto;

import com.company.charging.api.model.ChargingPlan;
import com.company.charging.api.model.PaymentType;
import com.company.charging.api.model.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
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
    private String planName;
    private String username;
    private BigDecimal amount;
    private boolean isSuccess;
    private boolean isDeleted;
    private PaymentType paymentType;
    private String description;
    @JsonIgnore
    private User user;
    @JsonIgnore
    private ChargingPlan chargingPlan;


}