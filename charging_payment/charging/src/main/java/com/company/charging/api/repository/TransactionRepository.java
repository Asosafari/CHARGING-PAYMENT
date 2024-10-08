package com.company.charging.api.repository;

import com.company.charging.api.model.Transaction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction,Long> {

    Page<Transaction> findByUserUsernameAndChargingPlanPlanName(String username,
                                                                String chargingPlanName,
                                                                PageRequest pageRequest);

    Page<Transaction> findByChargingPlanPlanName(String chargingPlanName, PageRequest pageRequest);
    Page<Transaction> findByUserUsername(String username, PageRequest pageRequest);
}
