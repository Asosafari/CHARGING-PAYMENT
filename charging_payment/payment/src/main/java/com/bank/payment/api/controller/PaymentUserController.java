package com.bank.payment.api.controller;

import com.bank.payment.api.dto.PaymentUserRequest;
import com.bank.payment.api.service.PaymentUserService;
import jakarta.persistence.GeneratedValue;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

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
    public ResponseEntity<String> register(@RequestBody PaymentUserRequest paymentUserRequest) {
        String publicKey = paymentUserService.registerUserPayment(paymentUserRequest);
        if (!publicKey.equals("Register failed")) {
            return new ResponseEntity<>(publicKey, HttpStatus.CREATED);
        }
        return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
    }
}
