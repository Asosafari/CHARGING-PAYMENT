package com.company.charging.api.factory;

import com.company.charging.api.model.ChargingPlan;

import java.math.BigDecimal;

/**
 * Author: ASOU SAFARI
 * Date:8/30/24
 * Time:12:15 AM
 */
public interface ChargingPlanFactory {

    ChargingPlan createChargingPlan(String type,
                                    String planName,
                                    BigDecimal ratePerUnit,
                                    BigDecimal pricePerUnit,
                                    String description);

}
