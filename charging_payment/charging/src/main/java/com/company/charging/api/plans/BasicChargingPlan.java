package com.company.charging.api.plans;

import com.company.charging.api.model.ChargingPlan;
import jakarta.persistence.Entity;
import lombok.experimental.SuperBuilder;

/**
 * Author: ASOU SAFARI
 * Date:8/30/24
 * Time:12:44 AM
 */
@Entity
@SuperBuilder
public class BasicChargingPlan extends ChargingPlan {

    public void setPlanDescrition(){
        super.setDescription("Basic");
    }
}
