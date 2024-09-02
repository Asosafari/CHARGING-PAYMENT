package com.company.charging.api.plans;

import com.company.charging.api.model.ChargingPlan;
import com.company.charging.api.model.ChargingPlanType;
import com.company.charging.api.model.User;
import jakarta.persistence.Entity;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;

/**
 * Author: ASOU SAFARI
 * Date:8/30/24
 * Time:12:44 AM
 */
@Entity
@NoArgsConstructor
public class BasicChargingPlan extends ChargingPlan {

    @Builder
    public BasicChargingPlan(Long id,
                             String planName,
                             BigDecimal ratePerUnit,
                             BigDecimal pricePerUnit,
                             String description,
                             boolean isDeleted,
                             Set<User> users,
                             ChargingPlanType chargingPlanType,
                             Integer version,
                             LocalDateTime createdDate,
                             LocalDateTime updateDate) {


        super(id, planName, ratePerUnit, pricePerUnit, description,
                isDeleted, users, chargingPlanType, version, createdDate, updateDate);
    }

}
