package com.company.charging.api.service;

import com.company.charging.api.dto.OrderDTO;
import com.company.charging.api.dto.TransactionDTO;
import com.company.charging.api.exception.TransactionCreationException;
import com.company.charging.api.mapper.TransactionMapper;
import com.company.charging.api.model.*;
import com.company.charging.api.repository.PaymentRequestRepository;
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
    private final PaymentRequestRepository paymentRequestRepository;
    private final DirectPaymentService directPaymentService;

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
    public Optional<TransactionDTO> createTrasaction(OrderDTO orderDTO) {

        TransactionDTO transactionDTO = TransactionDTO.builder()
                .amount(orderDTO.getChargingPlan().getRatePerUnit().multiply(orderDTO.getChargingPlan().getPricePerUnit()))
                .chargingPlanId(orderDTO.getChargingPlan().getId())
                .userId(orderDTO.getUser().getId())
                .transactionType(orderDTO.getTransactionType())
                .isSuccess(false)
                .isDeleted(false)
                .build();

        try {
            if (orderDTO.getTransactionType().equals(TransactionType.DIRECT)) {

                String publicKey = paymentRequestRepository.findPrivateKeyCheckout(transactionDTO.getUserId());
                transactionDTO.setPublicKey(publicKey);

                if (publicKey.equals("UNAUTHORIZED")) {
                    log.warn("Unauthorized direct payment  for user {}", transactionDTO.getUserId());
                    return Optional.empty();
                }

                if (directPaymentService.processPayment(transactionDTO)) {
                    transactionDTO.setSuccess(true);
                } else {
                    log.error("Direct Payment Failed for user {}", transactionDTO.getUserId());
                }

                if (orderDTO.getTransactionType().equals(TransactionType.GATEWAY)) {
                    if (checkoutGatewayPayment(transactionDTO.getAmount()).equals("Successes")) {
                        transactionDTO.setSuccess(true);
                    } else {
                        log.error("Gateway Payment Failed for user {}", transactionDTO.getUserId());
                    }
                }
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
            throw new TransactionCreationException("Failed to create transaction", e);
        }
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

    private String checkoutGatewayPayment(BigDecimal amount) {
        return "Successes";
    }

    private Page<Transaction> getAllTransactionByChargingPlanName(String chargingPlanName, PageRequest pageRequest) {
        return transactionRepository.findByChargingPlanPlanName(chargingPlanName,pageRequest);
    }

    private Page<Transaction> getAllTransactionByUsername(String username, PageRequest pageRequest) {
        return transactionRepository.findByUserUsername(username,pageRequest);
    }

}



