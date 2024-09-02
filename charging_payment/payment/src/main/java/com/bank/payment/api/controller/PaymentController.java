package com.bank.payment.api.controller;

import com.bank.payment.api.dto.PaymentRequest;
import com.bank.payment.api.sterategy.PaymentStrategy;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Author: ASOU SAFARI
 * Date:9/1/24
 * Time:10:19 PM
 */
@RestController
@RequestMapping("/api/v1/payments")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentStrategy strategy;

    @PostMapping("/check")
    public ResponseEntity<String> processPayment(@RequestBody PaymentRequest paymentRequest) {
        boolean isPaymentSuccessful = strategy.processPayment(paymentRequest);

        if (isPaymentSuccessful) {
            return new ResponseEntity<>("Payment successful", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Payment failed", HttpStatus.BAD_REQUEST);
        }
    }


}