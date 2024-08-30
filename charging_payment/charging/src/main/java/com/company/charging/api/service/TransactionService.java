package com.company.charging.api.service;

import com.company.charging.api.dto.TransactionDTO;
import com.company.charging.api.model.Order;
import org.springframework.data.domain.Page;
import java.util.Optional;

public interface TransactionService {
    Optional<TransactionDTO> createTrasaction(Order order);
    Page<TransactionDTO> getAllTransacton(String username,
                                          String chargingPlanName,
                                          Integer pageNumber,
                                          Integer pageSize);

    public boolean deleteUser(Long id);
}
