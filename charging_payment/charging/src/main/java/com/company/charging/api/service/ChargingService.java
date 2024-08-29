package com.company.charging.api.service;

import com.company.charging.api.dto.ChargingPlanDTO;
import org.springframework.data.domain.Page;

import java.math.BigDecimal;
import java.util.Optional;

public interface ChargingService {
    Page<ChargingPlanDTO> listOfUsers(BigDecimal greaterThanRate,BigDecimal lessThanPricePerUnit,
                                      Integer pageNumber, Integer pageSize,String sortProperty);
    Optional<ChargingPlanDTO> getUserById(Long id);
    Optional<ChargingPlanDTO> updateUser(Long id, ChargingPlanDTO chargingPlanDTO);
    boolean deleteUser(Long id);

}
