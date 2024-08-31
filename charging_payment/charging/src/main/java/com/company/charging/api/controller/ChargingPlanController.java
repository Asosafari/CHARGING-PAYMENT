package com.company.charging.api.controller;

import com.company.charging.api.dto.ChargingPlanDTO;
import com.company.charging.api.exception.NotFoundException;
import com.company.charging.api.service.ChargingService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

/**
 * Author: ASOU SAFARI
 * Date:8/30/24
 * Time:6:00 PM
 */

@RestController
@RequiredArgsConstructor
public class ChargingPlanController {

    private final ChargingService chargingService;

    @GetMapping("/api/v1/plans")
    public Page<ChargingPlanDTO> listOfPlans(@RequestParam(required = false) BigDecimal graterThanRate,
                                             @RequestParam(required = false) BigDecimal lessThanPricePerUnit,
                                             @RequestParam(required = false) Integer pageNumber,
                                             @RequestParam(required = false) Integer pageSize,
                                             @RequestParam(required = false) String sortProperty){

        return chargingService.listOfChargingPlan(graterThanRate,lessThanPricePerUnit,pageNumber,pageSize,sortProperty);
    }

    @GetMapping("/api/v1/plans/{planId}")
    public ChargingPlanDTO getPlansById(@PathVariable("planId") Long planId){
        return chargingService.getplanById(planId).orElseThrow(NotFoundException::new);
    }

}
