package com.company.charging.api.service;

import com.company.charging.api.dto.TransactionDTO;
import com.company.charging.api.exception.TransactionCreationException;
import com.company.charging.api.mapper.TransactionMapper;
import com.company.charging.api.model.*;
import com.company.charging.api.repository.TransactionRepository;
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

    @Override
    public Page<TransactionDTO> getAllTransacton(String username,
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

            transactions = transactionRepository.findByUserUsernameAndChargingPlanPlanName(username,chargingPlanName,pageRequest);
        }

        else {
            transactions = transactionRepository.findAll(pageRequest);
        }

        return transactions.map(transactionMapper ::mapToDto);
    }



    private Page<Transaction> getAllTransactionByChargingPlanName(String chargingPlanName, PageRequest pageRequest) {
        return transactionRepository.findByChargingPlanPlanName(chargingPlanName,pageRequest);
    }

    private Page<Transaction> getAllTransactionByUsername(String username, PageRequest pageRequest) {
        return transactionRepository.findByUserUsername(username,pageRequest);
    }


    @Override
    @Transactional
    public Optional<TransactionDTO> createTrasaction(Order order) {

        Transaction transaction = Transaction.builder()
                .amount(BigDecimal.valueOf
                        (order.getChargingPlan().getRatePerUnit().doubleValue() *
                                order.getChargingPlan().getPricePerUnit().doubleValue()))
                .chargingPlan(order.getChargingPlan())
                .user(order.getUser())
                .transactionType(order.getTransactionType())
                .isSuccess(false)
                .build();

        try {
            if (order.getTransactionType().equals(TransactionType.DIRECT)) {
                String privateKey = transactionRepository.findPrivateKeyCheckout(transaction.getUser().getId());
                if (privateKey.equals("UNAUTHORIZED")) {
                    log.warn("Unauthorized direct payment  for user {}", transaction.getUser().getId());
                    return Optional.empty();
                }

                if (checkoutDirectPayment(transaction.getAmount(), privateKey).equals("Successes")) {
                    transaction.setSuccess(true);
                } else {
                    log.error("Direct Payment Failed for user {}", transaction.getUser().getId());
                }


                if (order.getTransactionType().equals(TransactionType.GATEWAY)) {
                    if (checkoutGatewayPayment(transaction.getAmount()).equals("Successes")) {
                        transaction.setSuccess(true);
                    } else {
                        log.error("Gateway Payment Failed for user {}", transaction.getUser().getId());
                    }
                }
            }

            transaction.setDescription("Charging successful balance : "
                    + transactionRepository.charging(transaction.getUser().getId(),order.getChargingPlan().getRatePerUnit()));

            return Optional.of(transactionMapper.mapToDto(transactionRepository.save(transaction)));

        } catch (Exception e) {
            log.error("Failed to create transaction for order {}: {}", order, e.getMessage());
            throw new TransactionCreationException("Failed to create transaction", e);
        }
    }


    private String checkoutGatewayPayment(BigDecimal amount) {
        return "Successes";
    }


    private String checkoutDirectPayment(BigDecimal amount, String privateKey) {

        return "Successes";
    }

    @Override
    public boolean deleteTrasction(Long id) {
        Optional<Transaction> transaction = transactionRepository.findById(id);
        if (transaction.isPresent()){
            transaction.get().softDelete();
            transactionRepository.save(transaction.get());
            return true;
        }
        return false;
    }


}



