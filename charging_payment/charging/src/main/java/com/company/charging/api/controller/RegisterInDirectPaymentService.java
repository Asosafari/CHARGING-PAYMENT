package com.company.charging.api.controller;

import com.company.charging.api.request.dto.PaymentUserRegisterRequest;
import com.company.charging.api.dto.TransactionDTO;
import com.company.charging.api.request.service.DirectPaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * Author: ASOU SAFARI
 * Date:9/2/24
 * Time:9:58 AM
 */
@RestController
@RequiredArgsConstructor
public class RegisterInDirectPaymentService {
    private final DirectPaymentService paymentService;
    @PostMapping("/api/v1/paymentUsers/create")
    public ResponseEntity<TransactionDTO> registerUserAsPaymentUser(@RequestBody PaymentUserRegisterRequest request) {
        if ( paymentService.registerToPaymentSystem(request)){
            return new ResponseEntity<>(HttpStatus.CREATED);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

}
