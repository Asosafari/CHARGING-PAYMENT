package com.company.charging.api.service;

import com.company.charging.api.dto.ChargingPlanDTO;
import com.company.charging.api.mapper.ChargingPlanMapper;
import com.company.charging.api.model.ChargingPlan;
import com.company.charging.api.repository.ChargingPlanRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;


import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

/**
 * Author: ASOU SAFARI
 * Date:8/30/24
 * Time:1:29 AM
 */
@Service
@RequiredArgsConstructor
public class ChargingServiceImpl implements ChargingService {

    private final ChargingPlanRepository chargingPlanRepository;
    private final ChargingPlanMapper chargingPlanMapper;


    @Override
    public Page<ChargingPlanDTO> listOfChargingPlan(BigDecimal greaterThanRate,
                                             BigDecimal lessThanPricePerUnit,
                                             Integer pageNumber, Integer pageSize,
                                             String sortProperty) {

        PageRequest pageRequest = BuildPageRequest.build(pageNumber, pageSize, sortProperty);
        Page<ChargingPlan> plans;

        if (greaterThanRate != null && lessThanPricePerUnit == null) {
            plans = getAllPlansByUpperLimitRate(greaterThanRate,pageRequest);
        } else if (greaterThanRate == null && lessThanPricePerUnit != null) {
            plans = getAllPlansByLowerLimitPrice(lessThanPricePerUnit, pageRequest);
        } else if (greaterThanRate == null){
            plans = chargingPlanRepository.findAll(pageRequest);
        }else {
            plans = chargingPlanRepository.findByRatePerUnitGreaterThanAndPricePerUnitLessThan(greaterThanRate,lessThanPricePerUnit,pageRequest);
        }
        return plans.map(chargingPlanMapper ::mapToDTO);

    }

    private Page<ChargingPlan> getAllPlansByLowerLimitPrice(BigDecimal lessThanPricePerUnit, PageRequest pageRequest) {
        return chargingPlanRepository.findByPricePerUnitLessThan(lessThanPricePerUnit,pageRequest);
    }

    private Page<ChargingPlan> getAllPlansByUpperLimitRate(BigDecimal greaterThanRate,PageRequest pageRequest) {
        return chargingPlanRepository.findByRatePerUnitGreaterThan(greaterThanRate,pageRequest);
    }

    @Override
    public Optional<ChargingPlanDTO> getPlanById(Long id) {
        return Optional.ofNullable(chargingPlanMapper.mapToDTO(chargingPlanRepository.findById(id).orElse(null)));
    }

    @Override
    public ChargingPlanDTO CreateChargingPlan(ChargingPlanDTO chargingPlanDTO) {
        return chargingPlanMapper
                .mapToDTO(chargingPlanRepository.
                        save(chargingPlanMapper
                                .maptomodel(chargingPlanDTO)));
    }

    @Override
    public Optional<ChargingPlanDTO> updatePlan(Long id, ChargingPlanDTO chargingPlanDTO) {
        AtomicReference<Optional<ChargingPlanDTO>> atomicReference = new AtomicReference<>();
        chargingPlanRepository.findById(id).ifPresentOrElse(foundPlan ->{
            foundPlan.setPlanName(chargingPlanDTO.getPlanName());
            foundPlan.setDescription(chargingPlanDTO.getDescription());
            foundPlan.setRatePerUnit(chargingPlanDTO.getRatePerUnit());
            foundPlan.setPricePerUnit(chargingPlanDTO.getPricePerUnit());
            foundPlan.setUpdateDate(LocalDateTime.now());
            atomicReference.set(Optional.of(chargingPlanMapper.mapToDTO(chargingPlanRepository.save(foundPlan))));
        }, () -> atomicReference.set(Optional.empty())
        );

        return atomicReference.get();
    }

    @Override
    public boolean deletePlan(Long id) {
        Optional<ChargingPlan> plan = chargingPlanRepository.findById(id);
        if (plan.isPresent()){
            plan.get().softDelete();
            chargingPlanRepository.save(plan.get());
            return true;
        }
        return false;
    }
}
