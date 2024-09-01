package com.company.charging.api.dto;

import com.company.charging.api.model.ChargingPlan;
import com.company.charging.api.model.TransactionType;
import com.company.charging.api.model.User;
import lombok.Data;

/**
 * Author: ASOU SAFARI
 * Date:8/30/24
 * Time:10:39 AM
 */
@Data
public class OrderDTO {
    private User user;
    private ChargingPlan chargingPlan;
    private TransactionType transactionType;
}
