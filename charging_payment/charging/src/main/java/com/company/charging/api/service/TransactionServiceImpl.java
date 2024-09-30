package com.company.charging.api.service;

import com.company.charging.api.dto.TransactionDTO;

import com.company.charging.api.exception.TransactionCreationException;
import com.company.charging.api.mapper.TransactionMapper;
import com.company.charging.api.model.*;
import com.company.charging.api.model.PaymentType;
import com.company.charging.api.repository.ChargingPlanRepository;
import com.company.charging.api.repository.UserRepository;
import com.company.charging.api.request.rpository.PaymentRequestRepository;
import com.company.charging.api.repository.TransactionRepository;
import com.company.charging.api.request.service.DirectPaymentService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;

import java.util.Optional;

/**
 * Author: ASOU SAFARI
 * Date:8/29/24
 * Time:6:19 PM
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository transactionRepository;
    private final TransactionMapper transactionMapper;
    private final PaymentRequestRepository paymentRequestRepository;
    private final DirectPaymentService directPaymentService;
    private final UserRepository userRepository;
    private final ChargingPlanRepository chargingPlanRepository;

    @Override
    public Page<TransactionDTO> getAllTransaction(String username,
                                                  String chargingPlanName,
                                                  Integer pageNumber,
                                                  Integer pageSize) {

        PageRequest pageRequest = BuildPageRequest.build(pageNumber, pageSize, username);
        Page<Transaction> transactions;

        if (StringUtils.hasText(username ) && !StringUtils.hasText(chargingPlanName ) ) {

           transactions = getAllTransactionByUsername(username,pageRequest);

        } else if(!StringUtils.hasText(username) && StringUtils.hasText(chargingPlanName)){

            transactions = getAllTransactionByChargingPlanName(chargingPlanName,pageRequest);

        }else if (StringUtils.hasText(username) && StringUtils.hasText(chargingPlanName)){

            transactions = transactionRepository
                    .findByUserUsernameAndChargingPlanPlanName(username,chargingPlanName,pageRequest);
        }

        else {
            transactions = transactionRepository.findAll(pageRequest);
        }

        return transactions.map(transactionMapper ::mapToDto);
    }

    @Override

    public Optional<TransactionDTO> createTransaction(TransactionDTO transactionDTO) {
        transactionDTO.setUser(userRepository.findByUsername(transactionDTO.getUsername()).get());
        transactionDTO.setChargingPlan(chargingPlanRepository.findByPlanName(transactionDTO.getPlanName()).get());
        transactionDTO.setAmount(transactionDTO.getChargingPlan().
                getRatePerUnit().multiply(transactionDTO.getChargingPlan().getPricePerUnit()));
        if (transaction(transactionDTO)) {
            transactionDTO.setDescription("Charging successful balance : "
                    + paymentRequestRepository.charging(transactionDTO.getUser().getId(), transactionDTO.getChargingPlan().getRatePerUnit()));

            return Optional.of(transactionMapper.mapToDto(transactionRepository.save(transactionMapper.mapToModel(transactionDTO))));
        }
        return Optional.empty();
    }

    @Transactional
    private boolean transaction(TransactionDTO transactionDTO){
        try {
            if (transactionDTO.getPaymentType().equals(PaymentType.DIRECT)) {
                if (directPaymentService.processPayment(transactionDTO)) {
                    transactionDTO.setSuccess(true);
                    return true;
                } else {
                    log.error("Direct Payment Failed for user {}", transactionDTO.getUsername());
                    return false;
                }
            }
            else if (transactionDTO.getPaymentType().equals(PaymentType.GATEWAY)) {
                if (checkoutGatewayPayment(transactionDTO.getAmount()).equals("Successes")) {
                    transactionDTO.setSuccess(true);
                    return true;
                } else {
                    log.error("Gateway Payment Failed for user {}", transactionDTO.getUsername());
                    return false;
                }
            }else {
                return false;
            }
        } catch (Exception e) {
            log.error("Failed to create transaction for order {}: {}", transactionDTO, e.getMessage());
            throw new TransactionCreationException("Failed to create transaction", e);
        }
    }


    @Override
    public boolean deleteTransaction(Long id) {
        Optional<Transaction> transaction = transactionRepository.findById(id);
        if (transaction.isPresent()){
            transaction.get().softDelete();
            transactionRepository.save(transaction.get());
            return true;
        }
        return false;
    }

    @Override
    public Optional<TransactionDTO> getTransaction(Long transactionId) {
        return Optional.ofNullable(transactionMapper.mapToDto(transactionRepository.findById(transactionId).orElse(null)));
    }

    private String checkoutGatewayPayment(BigDecimal amount) {

        //implement
        return "Successes";
    }

    private Page<Transaction> getAllTransactionByChargingPlanName(String chargingPlanName, PageRequest pageRequest) {
        return transactionRepository.findByChargingPlanPlanName(chargingPlanName,pageRequest);
    }

    private Page<Transaction> getAllTransactionByUsername(String username, PageRequest pageRequest) {
        return transactionRepository.findByUserUsername(username,pageRequest);
    }

}



