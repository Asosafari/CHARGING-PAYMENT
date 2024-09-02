package com.company.charging.api.factory;


import com.company.charging.api.model.ChargingPlanType;

/**
 * Author: ASOU SAFARI
 * Date:8/30/24
 * Time:1:06 AM
 */
public class ChargingPlanFactoryProvider {

    public static ChargingPlanFactory createChargingPlan(ChargingPlanType chargingPlanType) {
        return switch (chargingPlanType.name()) {
            case "BASIC" -> new BasicChargingPlanFactory();
            case "PREMIUM" -> new PremiumChargingPlanFactor();
            case "DEFAULT" -> new DefaultChargingPlanFactory();
            default -> new DefaultChargingPlanFactory();
        };
    }
}
