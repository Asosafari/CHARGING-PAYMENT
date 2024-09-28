package com.bank.payment.api.security.jwt;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Author: ASOU SAFARI
 * Date:9/20/24
 * Time:9:05 PM
 */
public class Secret {

    public static byte[] generateSecret() {
        try {
            return Files.readAllBytes(Paths.get("src/main/resources/secret"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
