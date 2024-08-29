package com.company.charging.api.plans;

import com.company.charging.api.model.ChargingPlan;
import jakarta.persistence.Entity;
import lombok.experimental.SuperBuilder;

/**
 * Author: ASOU SAFARI
 * Date:8/30/24
 * Time:1:13 AM
 */
@Entity
@SuperBuilder
public class DefaultChargingPlan extends ChargingPlan {

    public void setPlanDescrition(){
        super.setDescription("Default");
    }
}
