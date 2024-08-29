package com.company.charging.api.dto;
import lombok.Data;

import java.math.BigDecimal;

/**
 * Author: ASOU SAFARI
 * Date:8/30/24
 * Time:1:21 AM
 */
@Data
public class ChargingPlanDTO {

    private Long id;
    private String planName;
    private BigDecimal ratePerUnit;
    private BigDecimal pricePerUnit;
    private String description;


}
