package com.company.charging.api.plans;

import com.company.charging.api.model.ChargingPlan;
import com.company.charging.api.model.User;
import jakarta.persistence.Entity;
import lombok.Builder;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;

/**
 * Author: ASOU SAFARI
 * Date:8/30/24
 * Time:12:44 AM
 */
@Entity
public class BasicChargingPlan extends ChargingPlan {

    @Builder
    public BasicChargingPlan(Long id,
                             String planName,
                             BigDecimal ratePerUnit,
                             BigDecimal pricePerUnit,
                             String description,
                             boolean isDeleted,
                             Set<User> users,
                             Integer version,
                             LocalDateTime createdDate,
                             LocalDateTime updateDate) {
        super(id, planName, ratePerUnit, pricePerUnit, description, isDeleted, users, version, createdDate, updateDate);
    }
}
