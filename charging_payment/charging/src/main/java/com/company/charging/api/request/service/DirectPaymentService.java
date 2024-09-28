package com.company.charging.api.request.service;
import com.company.charging.api.dto.UserDTO;
import com.company.charging.api.model.User;
import com.company.charging.api.repository.UserRepository;
import com.company.charging.api.dto.TransactionDTO;
import com.company.charging.api.request.dto.DirectPaymentRequest;

import com.company.charging.api.security.jwt.JWTUtil;
import com.company.charging.api.service.PaymentType;
import com.company.charging.api.service.UserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

/**
 * Author: ASOU SAFARI
 * Date:9/1/24
 * Time:1:33 PM
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class DirectPaymentService {
    private final UserService userService;
    private final UserRepository userRepository;

    private final RestTemplateBuilder restTemplateBuilder;

    public boolean processPayment(TransactionDTO transactionDTO) {
        DirectPaymentRequest directPaymentRequest = createPaymentRequest(transactionDTO);
        RestTemplate restTemplate = restTemplateBuilder.build();
        String paymentApiUrl = "/api/v1/payments/check";
        String jwt = JWTUtil.jwtTokenGenerator();
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + jwt);
        HttpEntity<DirectPaymentRequest> entity = new HttpEntity<>(directPaymentRequest,headers);
        ResponseEntity<String> response = restTemplate.postForEntity(paymentApiUrl, entity,String.class);

        if (response.getStatusCode() == HttpStatus.OK) {
            transactionDTO.setSuccess(true);
            transactionDTO.setDescription(response.getBody());
            return true;
        }
        transactionDTO.setSuccess(false);
        return false;
    }


    @Transactional
    public boolean registerToPaymentSystem(UserDTO request){
        Optional<User>  user = userRepository.findByUsername(request.getUsername());
        if (user.isPresent() && !user.get().isAuthorized()){
            try {
                RestTemplate restTemplate = restTemplateBuilder.build();
                String paymentApiUrl = "/api/v1/paymentUsers/register";
                String jwt = JWTUtil.jwtTokenGenerator();
                HttpHeaders headers = new HttpHeaders();
                headers.set("Authorization", "Bearer " + jwt);
                HttpEntity<UserDTO> entity = new HttpEntity<>(request,headers);
                log.info(String.valueOf(entity.getBody()));
                log.info("JWT Token: " + jwt);
                ResponseEntity<String> response = restTemplate.postForEntity(paymentApiUrl,entity,String.class);
                log.info(response.getBody());
                if (response.getStatusCode() == HttpStatus.CREATED){
                    request.setIsAuthorized(true);
                    userService.patchUser(user.get().getId(),request);
                    return true;
                }
                log.error("UNAUTHORIZED REQUEST");
                return false;
            }catch (Exception e){
                log.error(e.getMessage());
                return false;
            }
        }
        log.info("User is ");
        return false;
    }


    private DirectPaymentRequest createPaymentRequest(TransactionDTO transactionDTO) {
        return DirectPaymentRequest.builder()
                .amount(transactionDTO.getAmount())
                .userId(transactionDTO.getUserId())
                .paymentType(PaymentType.DIRECT)
                .build();
    }

}