package com.company.charging.api.factory;

import com.company.charging.api.model.ChargingPlan;
import com.company.charging.api.model.ChargingPlanType;
import com.company.charging.api.plans.DefaultChargingPlan;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Author: ASOU SAFARI
 * Date:8/31/24
 * Time:2:03 PM
 */
public class DefaultChargingPlanFactory implements ChargingPlanFactory{
    @Override
    public ChargingPlan createChargingPlan(ChargingPlanType chargingPlanType,
                                           String planName,
                                           BigDecimal ratePerUnit,
                                           BigDecimal pricePerUnit,
                                           String description) {


        return DefaultChargingPlan.builder()
                .chargingPlanType(chargingPlanType)
                .planName(planName)
                .ratePerUnit(ratePerUnit)
                .pricePerUnit(pricePerUnit)
                .description(description)
                .createdDate(LocalDateTime.now())
                .isDeleted(false)
                .build();
    }
}
