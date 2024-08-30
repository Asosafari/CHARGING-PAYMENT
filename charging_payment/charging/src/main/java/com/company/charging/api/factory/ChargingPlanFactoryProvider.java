package com.company.charging.api.factory;

import com.company.charging.api.plans.BasicChargingPlan;
import com.company.charging.api.model.ChargingPlan;
import com.company.charging.api.plans.DefaultChargingPlan;
import com.company.charging.api.plans.PremiumChargingPlan;

import java.math.BigDecimal;

/**
 * Author: ASOU SAFARI
 * Date:8/30/24
 * Time:1:06 AM
 */
public class ChargingPlanFactoryProvider implements ChargingPlanFactory {
    @Override
    public ChargingPlan createChargingPlan(String type,
                                           String planName,
                                           BigDecimal ratePerUnit,
                                           BigDecimal pricePerUnit,
                                           String description) {
        return switch (type.toLowerCase()) {
            case "basic" -> BasicChargingPlan.builder()
                    .planName(planName)
                    .ratePerUnit(ratePerUnit)
                    .pricePerUnit(pricePerUnit)
                    .description(description)
                    .build();
            case "premium" -> PremiumChargingPlan.builder()
                    .planName(planName)
                    .ratePerUnit(ratePerUnit)
                    .pricePerUnit(pricePerUnit)
                    .description(description)
                    .build();
            default -> DefaultChargingPlan.builder()
                    .planName(planName)
                    .ratePerUnit(ratePerUnit)
                    .pricePerUnit(pricePerUnit)
                    .description(description)
                    .build();
        };
    }

}
