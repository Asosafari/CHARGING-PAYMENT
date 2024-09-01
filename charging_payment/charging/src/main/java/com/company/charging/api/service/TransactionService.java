package com.company.charging.api.service;

import com.company.charging.api.dto.TransactionDTO;
import com.company.charging.api.dto.OrderDTO;
import org.springframework.data.domain.Page;
import java.util.Optional;

public interface TransactionService {
    Optional<TransactionDTO> createTrasaction(OrderDTO orderDTO);
    Page<TransactionDTO> getAllTransacton(String username,
                                          String chargingPlanName,
                                          Integer pageNumber,
                                          Integer pageSize);

    boolean deleteTrasction(Long id);
}
