package com.company.charging.api.service;
import com.company.charging.api.dto.TransactionDTO;
import com.company.charging.api.dto.UserDTO;
import com.company.charging.api.dto.DirectPaymentRequest;
import com.company.charging.api.repository.TransactionRepository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
    private final TransactionRepository transactionRepository;

    @PersistenceContext
    private final EntityManager entityManager;

    private final RestTemplate restTemplate = new RestTemplate();

    public boolean processPayment(TransactionDTO transactionDTO) {
        DirectPaymentRequest directPaymentRequest = createPaymentRequest(transactionDTO);
        directPaymentRequest.setEncryptedData(directPaymentRequest.getUserId() + "|" + directPaymentRequest.getAmount());

        String paymentApiUrl = "http://localhost:9090/api/v1/payments";
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
    public boolean registerToPaymentSystem(UserDTO userDTO){
        try {
            String paymentApiUrl = "http://localhost:9090/api/v1/paymentUsers/regester";
            ResponseEntity<String> response = restTemplate.postForEntity(paymentApiUrl,userDTO,String.class);
            if (response.getStatusCode() == HttpStatus.CREATED){
                savePublicKey(userDTO.getId(),userDTO.getId(),response.getBody());
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

        String sql = "INSERT INTO authorized_bank_users (user_id, bank_user_id, encrypted_public_key) " +
                "VALUES (:userId, :bankUserId, :publicKey)";
        Query query = entityManager.createNativeQuery(sql);
        query.setParameter("userId", userId);
        query.setParameter("bankUserId", bankUserId);
        query.setParameter("publicKey", publicKey);
        return query.executeUpdate();
    }


}