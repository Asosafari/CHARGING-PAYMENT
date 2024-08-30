package com.company.charging.api.service;

import com.company.charging.api.dto.ChargingPlanDTO;
import org.springframework.data.domain.Page;

import java.math.BigDecimal;
import java.util.Optional;

public interface ChargingService {
    Page<ChargingPlanDTO>listOfChargingPlan(BigDecimal greaterThanRate,BigDecimal lessThanPricePerUnit,
                                      Integer pageNumber, Integer pageSize,String sortProperty);
    Optional<ChargingPlanDTO> getplanById(Long id);
    Optional<ChargingPlanDTO> updatePlan(Long id, ChargingPlanDTO chargingPlanDTO);
    boolean deletePlan(Long id);

}
