package com.company.charging.api.repository;
import com.company.charging.api.model.ChargingPlan;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.Optional;

@Repository
public interface ChargingPlanRepository extends JpaRepository<ChargingPlan,Long> {

    Page<ChargingPlan> findByRatePerUnitGreaterThanAndPricePerUnitLessThan(BigDecimal ratePerUnit, BigDecimal pricePerUnit, PageRequest pageRequest);
    Page<ChargingPlan> findByRatePerUnitGreaterThan(BigDecimal greaterThanRate, PageRequest pageRequest);

    Page<ChargingPlan> findByPricePerUnitLessThan(BigDecimal lessThanPricePerUnit, PageRequest pageRequest);

    Optional<ChargingPlan> findByPlanName(String planName);
}
