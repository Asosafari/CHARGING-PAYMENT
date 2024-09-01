package com.company.charging.api.mapper;

import com.company.charging.api.dto.ChargingPlanDTO;
import com.company.charging.api.factory.ChargingPlanFactory;
import com.company.charging.api.factory.ChargingPlanFactoryProvider;
import com.company.charging.api.model.ChargingPlan;
import org.mapstruct.Mapper;

/**
 * Author: ASOU SAFARI
 * Date:8/30/24
 * Time:1:43 AM
 */
@Mapper
public interface ChargingPlanMapper {
    ChargingPlanDTO mapToDTO (ChargingPlan chargingPlan);

    default ChargingPlan mapToModel(ChargingPlanDTO chargingPlanDTO){
        ChargingPlanFactory factory = ChargingPlanFactoryProvider.createChargingPlan(chargingPlanDTO.getChargingPlanType());
        return factory.createChargingPlan(chargingPlanDTO.getChargingPlanType(),
                chargingPlanDTO.getPlanName(),
                chargingPlanDTO.getRatePerUnit(),
                chargingPlanDTO.getPricePerUnit(),
                chargingPlanDTO.getDescription());
    }
}
