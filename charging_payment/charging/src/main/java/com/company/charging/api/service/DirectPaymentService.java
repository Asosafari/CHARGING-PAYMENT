package com.company.charging.api.service;
import com.company.charging.api.dto.TransactionDTO;
import com.company.charging.api.mapper.TransactionMapper;
import com.company.charging.api.dto.DirectPaymentRequest;
import com.company.charging.api.repository.TransactionRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * Author: ASOU SAFARI
 * Date:9/1/24
 * Time:1:33 PM
 */
@Service
@RequiredArgsConstructor
public class DirectPaymentService {
    private final TransactionRepository transactionRepository;

    private final RestTemplate restTemplate = new RestTemplate();
    private final TransactionMapper transactionMapper;

    public boolean processPayment(TransactionDTO transactionDTO) {
        DirectPaymentRequest directPaymentRequest = createPaymentRequest(transactionDTO);

        String paymentApiUrl = "http://localhost:9000/api/v1/payments";
        ResponseEntity<String> response = restTemplate.postForEntity(paymentApiUrl, directPaymentRequest, String.class);

        if (response.getStatusCode() == HttpStatus.OK) {
            transactionDTO.setSuccess(true);
            transactionDTO.setDescription(response.getBody());
            return true;
        }
        transactionDTO.setSuccess(false);
        return false;
    }


    private DirectPaymentRequest createPaymentRequest(TransactionDTO transactionDTO) {
        return DirectPaymentRequest.builder()
                .userId(transactionDTO.getUserId())
                .chargingPlanId(transactionDTO.getChargingPlanId())
                .amount(transactionDTO.getAmount())
                .publicKey(transactionDTO.getPublicKey())
                .transactionType(transactionDTO.getTransactionType())
                .build();
    }

}