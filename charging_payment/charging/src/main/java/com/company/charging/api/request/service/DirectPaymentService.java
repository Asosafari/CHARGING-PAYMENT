package com.company.charging.api.request.service;
import com.company.charging.api.request.dto.PaymentUserRegisterRequest;
import com.company.charging.api.dto.TransactionDTO;
import com.company.charging.api.request.dto.DirectPaymentRequest;

import com.company.charging.api.service.PaymentType;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.client.RestTemplateBuilder;
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
@Slf4j
public class DirectPaymentService {
    @PersistenceContext
    private final EntityManager entityManager;

    private final RestTemplateBuilder restTemplateBuilder;

    public boolean processPayment(TransactionDTO transactionDTO) {
        DirectPaymentRequest directPaymentRequest = createPaymentRequest(transactionDTO);
        RestTemplate restTemplate = restTemplateBuilder.build();
        String paymentApiUrl = "/api/v1/payments/check";
        ResponseEntity<String> response = restTemplate.postForEntity(paymentApiUrl, directPaymentRequest, String.class);

        if (response.getStatusCode() == HttpStatus.OK) {
            transactionDTO.setSuccess(true);
            transactionDTO.setDescription(response.getBody());
            return true;
        }
        transactionDTO.setSuccess(false);
        return false;
    }


    @Transactional
    public boolean registerToPaymentSystem(PaymentUserRegisterRequest request){
        try {

            RestTemplate restTemplate = restTemplateBuilder.build();
            String paymentApiUrl = "/api/v1/paymentUsers/register";
            ResponseEntity<String> response = restTemplate.postForEntity(paymentApiUrl,request,String.class);
            if (response.getStatusCode() == HttpStatus.CREATED){
                savePublicKey(request.getChargingUserId(),request.getChargingUserId(),response.getBody());
                return true;
            }
            log.error("UNAUTHORIZED REQUEST");
            return false;
        }catch (Exception e){
            log.error(e.getMessage());
            return false;
        }

    }


    private DirectPaymentRequest createPaymentRequest(TransactionDTO transactionDTO) {
        return DirectPaymentRequest.builder()
                .amount(transactionDTO.getAmount())
                .publicKey(transactionDTO.getPublicKey())
                .userId(transactionDTO.getUserId())
                .paymentType(PaymentType.DIRECT)
                .build();
    }


    @Transactional
    private int savePublicKey(Long userId, Long bankUserId, String publicKey) {

        String sql = "INSERT INTO authorized_bank_users (user_id, bank_user_id, token_ch) " +
                "VALUES (:userId, :bankUserId, :publicKey)";
        Query query = entityManager.createNativeQuery(sql);
        query.setParameter("userId", userId);
        query.setParameter("bankUserId", bankUserId);
        query.setParameter("publicKey", publicKey);
        return query.executeUpdate();
    }


}