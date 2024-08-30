package com.company.charging.api.model;

import lombok.Data;

/**
 * Author: ASOU SAFARI
 * Date:8/30/24
 * Time:10:39 AM
 */
@Data
public class Order {
    private User user;
    private ChargingPlan chargingPlan;
    private TransactionType transactionType;
}
