package com.company.charging.api.service;

import com.company.charging.api.dto.TransactionDTO;
import com.company.charging.api.dto.OrderDTO;
import org.springframework.data.domain.Page;
import java.util.Optional;

public interface TransactionService {
    Optional<TransactionDTO> createTransaction(OrderDTO orderDTO);
    Page<TransactionDTO> getAllTransaction(String username,
                                           String chargingPlanName,
                                           Integer pageNumber,
                                           Integer pageSize);

    boolean deleteTransaction(Long id);

    Optional<TransactionDTO> getTransaction(Long transactionId);
}
