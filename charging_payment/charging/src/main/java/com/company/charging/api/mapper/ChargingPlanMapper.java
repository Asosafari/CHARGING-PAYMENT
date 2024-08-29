package com.company.charging.api.mapper;

import com.company.charging.api.dto.ChargingPlanDTO;
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
    ChargingPlan mapToModel(ChargingPlanDTO chargingPlanDTO);
}
