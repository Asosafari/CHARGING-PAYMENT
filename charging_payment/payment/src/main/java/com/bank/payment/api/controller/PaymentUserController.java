package com.bank.payment.api.controller;

import com.bank.payment.api.dto.PaymentUserRequest;
import com.bank.payment.api.security.JWTValidatorFilter;
import com.bank.payment.api.service.PaymentUserService;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Author: ASOU SAFARI
 * Date:9/2/24
 * Time:2:47 AM
 */
@RestController
@RequiredArgsConstructor
public class PaymentUserController {
    private final PaymentUserService paymentUserService;


    @PostMapping("/api/v1/paymentUsers/register")
    public ResponseEntity<String> register(@RequestBody PaymentUserRequest paymentUserRequest,
                                           @RequestHeader(name = "Authorization") String token) {


        if (JWTValidatorFilter.isValid(token)) {
            if (paymentUserService.registerUserPayment(paymentUserRequest)) {
                return new ResponseEntity<>(HttpStatus.CREATED);
            }
        }
        return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
    }
}
