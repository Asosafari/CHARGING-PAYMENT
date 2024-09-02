package com.bank.payment.api.crypto;
import com.bank.payment.api.model.PaymentUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.crypto.Cipher;
import java.security.*;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Base64;
import java.util.Optional;
import java.util.UUID;

/**
 * Author: ASOU SAFARI
 * Date:9/1/24
 * Time:11:43 AM
 */
@Slf4j
@Service
public class GenerateKey {
    public static String generateToken() {
        return UUID.randomUUID().toString();
    }
}
