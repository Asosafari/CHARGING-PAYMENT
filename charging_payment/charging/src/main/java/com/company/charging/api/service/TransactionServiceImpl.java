package com.company.charging.api.service;

import com.company.charging.api.dto.OrderDTO;
import com.company.charging.api.dto.TransactionDTO;
import com.company.charging.api.exception.TransactionCreationException;

import com.company.charging.api.mapper.TransactionMapper;
import com.company.charging.api.model.*;
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
    @Transactional
    public Optional<TransactionDTO> createTransaction(OrderDTO orderDTO) {

        TransactionDTO transactionDTO = TransactionDTO.builder()
                .amount(orderDTO.getChargingPlan().getRatePerUnit().multiply(orderDTO.getChargingPlan().getPricePerUnit()))
                .chargingPlanId(orderDTO.getChargingPlan().getId())
                .userId(orderDTO.getUser().getId())
                .user(orderDTO.getUser())
                .transactionType(orderDTO.getTransactionType())
                .isSuccess(false)
                .isDeleted(false)
                .build();

        try {
            if (orderDTO.getTransactionType().equals(TransactionType.DIRECT)) {
                if (directPaymentService.processPayment(transactionDTO)) {
                    transactionDTO.setSuccess(true);
                    transactionDTO.setChargingPlan(orderDTO.getChargingPlan());
                } else {
                    log.error("Direct Payment Failed for user {}", transactionDTO.getUserId());
                }
            }
            else if (orderDTO.getTransactionType().equals(TransactionType.GATEWAY)) {
                    if (checkoutGatewayPayment(transactionDTO.getAmount()).equals("Successes")) {
                        transactionDTO.setSuccess(true);
                    } else {
                        log.error("Gateway Payment Failed for user {}", transactionDTO.getUserId());
                    }
            }else {
                return Optional.empty();
            }

            transactionDTO.setDescription("Charging successful balance : "
                    + paymentRequestRepository
                    .charging(transactionDTO.getUserId(), orderDTO.getChargingPlan().getRatePerUnit()));
            return Optional.of(transactionMapper
                    .mapToDto(transactionRepository
                            .save(transactionMapper
                                    .mapToModel(transactionDTO))));

        } catch (Exception e) {
            log.error("Failed to create transaction for order {}: {}", orderDTO, e.getMessage());
            //throw new TransactionCreationException("Failed to create transaction", e);
            return Optional.empty();
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



