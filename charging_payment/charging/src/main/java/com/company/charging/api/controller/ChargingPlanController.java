package com.company.charging.api.controller;

import com.company.charging.api.dto.ChargingPlanDTO;
import com.company.charging.api.exception.NotFoundException;
import com.company.charging.api.service.ChargingService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/api/v1/plans/{chargingPlanId}")
    public ChargingPlanDTO getPlansById(@PathVariable("ChargingPlanId") Long planId){
        return chargingService.getPlanById(planId).orElseThrow(NotFoundException::new);
    }

    @PostMapping("/api/v1/plans/create")
    public ResponseEntity<ChargingPlanDTO> createCharging(@Validated @RequestBody ChargingPlanDTO chargingPlanDTO){
        ChargingPlanDTO savePlan = chargingService.CreateChargingPlan(chargingPlanDTO);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Location","/api/v1/plans/" + savePlan.getId());
        return new ResponseEntity<>(savePlan,headers, HttpStatus.CREATED);
    }

    @PutMapping("/api/v1/plans/{chargingPlanId}")
    public ResponseEntity updateChargingPlan(@PathVariable("chargingPlanId")Long chargingPlanId, @RequestBody ChargingPlanDTO chargingPlanDTO){

        if (chargingService.updatePlan(chargingPlanId,chargingPlanDTO).isEmpty()){
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/api/v1/plans/{chargingPlanId}")
    public ResponseEntity deleteChargingPlan(@PathVariable("chargingPlanId") Long chargingPlanId){
        if (!chargingService.deletePlan(chargingPlanId)){
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

}
