package com.bank.payment.api.repository;

import com.bank.payment.api.model.Key;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface KeyRepository extends JpaRepository<Key,Long> {

   Optional <Key> findKeyByPaymentUserId(Long id);
}
