package com.company.charging.api.dto;
import com.company.charging.api.model.ChargingPlanType;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

/**
 * Author: ASOU SAFARI
 * Date:8/30/24
 * Time:1:21 AM
 */
@Data
@Builder
public class ChargingPlanDTO {
    private Long id;
    private String planName;
    private BigDecimal ratePerUnit;
    private BigDecimal pricePerUnit;
    private String description;
    private ChargingPlanType chargingPlanType;

}
