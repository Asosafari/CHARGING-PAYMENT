package com.company.charging.api.mapper;

import com.company.charging.api.dto.TransactionDTO;
import com.company.charging.api.model.Transaction;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface TransactionMapper {

    TransactionMapper INSTANCE = Mappers.getMapper(TransactionMapper.class);
    default TransactionDTO mapToDto(Transaction transaction) {
        if (transaction == null) {
            return null;
        }


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
