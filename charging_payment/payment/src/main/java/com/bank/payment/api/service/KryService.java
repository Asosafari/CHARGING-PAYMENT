package com.bank.payment.api.service;

import com.bank.payment.api.model.Key;
import com.bank.payment.api.repository.KeyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Author: ASOU SAFARI
 * Date:9/2/24
 * Time:6:03 PM
 */
@Service
@RequiredArgsConstructor
public class KryService {
    private KeyRepository keyRepository;

    Optional<Key> findKey(Long chargingUserId){
        return keyRepository.findKeyByPaymentUserId(chargingUserId);
    }
}
