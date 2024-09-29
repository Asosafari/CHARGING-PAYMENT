package com.company.charging.api.mapper;

import com.company.charging.api.dto.TransactionDTO;
import com.company.charging.api.model.Transaction;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class TransactionMapper {


    public TransactionDTO mapToDto(Transaction transaction) {
        if (transaction == null) {
            return null;
        }
        return TransactionDTO.builder()
                .id(transaction.getId())
                .amount(transaction.getAmount())
                .planName(transaction.getChargingPlan().getPlanName())
                .paymentType(transaction.getPaymentType())
                .isDeleted(transaction.isDeleted())
                .username(transaction.getUser().getUsername())
                .isSuccess(transaction.isSuccess())
                .build();
    }

    public Transaction mapToModel(TransactionDTO transactionDTO){
        if (transactionDTO == null){
            return null;
        }
       return Transaction.builder()
                .user(transactionDTO.getUser())
                .chargingPlan(transactionDTO.getChargingPlan())
                .description(transactionDTO.getDescription())
                .createdDate(LocalDateTime.now())
                .amount(transactionDTO.getAmount())
                .isSuccess(transactionDTO.isSuccess())
               .paymentType(transactionDTO.getPaymentType())
               .user(transactionDTO.getUser())
                .isDeleted(transactionDTO.isDeleted())
                .build();
    }
}
