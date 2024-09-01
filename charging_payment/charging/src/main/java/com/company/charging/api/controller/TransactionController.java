package com.company.charging.api.controller;


import com.company.charging.api.dto.OrderDTO;
import com.company.charging.api.dto.TransactionDTO;
import com.company.charging.api.exception.NotFoundException;
import com.company.charging.api.service.TransactionService;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

/**
 * Author: ASOU SAFARI
 * Date:8/30/24
 * Time:6:00 PM
 */
@RestController
@RequiredArgsConstructor
public class TransactionController {
    TransactionService transactionService;

    @GetMapping("/api/v1/transactions")
    public Page<TransactionDTO> listOfTransactions(@RequestParam(required = false) String username,
                                                   @RequestParam(required = false) String chargingPlanName,
                                                   @RequestParam(required = false) Integer pageNumber,
                                                   @RequestParam(required = false) Integer pageSize){
        return transactionService.getAllTransaction(username,chargingPlanName,pageNumber,pageSize);
    }

    @GetMapping("/api/v1/transactions/{transactionId}")
    public Optional<TransactionDTO> getTransaction(@Validated @PathVariable("transactionId") Long transactionId ){
       return Optional.ofNullable(transactionService.getTransaction(transactionId).orElseThrow(NotFoundException::new));
    }

    @PostMapping("/api/v1/transactions/create")
    public ResponseEntity<TransactionDTO> createTransaction(@RequestBody OrderDTO orderDTO){
        Optional<TransactionDTO> saveTransactionDto = transactionService.createTransaction(orderDTO);
        if (saveTransactionDto.isPresent()){
            HttpHeaders headers = new HttpHeaders();
            headers.add("Location", "/api/v1/transactions/" +saveTransactionDto.get().getId());
            return new ResponseEntity<>(saveTransactionDto.get(), headers, HttpStatus.CREATED);
        }

        return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
    }

    @DeleteMapping("/api/v1/users/{transactionId}")
    public ResponseEntity deleteTransaction(@PathVariable("transactionId") Long transactionId){

        if (!transactionService.deleteTransaction(transactionId)){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
