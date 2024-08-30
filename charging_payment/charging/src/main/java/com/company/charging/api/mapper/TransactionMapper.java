package com.company.charging.api.mapper;

import com.company.charging.api.dto.TransactionDTO;
import com.company.charging.api.model.Transaction;
import org.mapstruct.Mapper;

@Mapper
public interface TransactionMapper {
    default TransactionDTO mapToDto(Transaction transaction){
        return TransactionDTO.builder()
                .id(transaction.getId())
                .amount(transaction.getAmount())
                .chargingPlanName(transaction.getChargingPlan().getPlanName())
                .transactionType(transaction.getTransactionType())
                .isDeleted(transaction.isDeleted())
                .username(transaction.getUser().getUsername())
                .isSuccess(transaction.isSuccess())
                .build();
    }
}
