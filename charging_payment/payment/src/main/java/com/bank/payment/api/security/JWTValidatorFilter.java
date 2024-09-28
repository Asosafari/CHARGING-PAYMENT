package com.bank.payment.api.security;

import com.bank.payment.api.security.jwt.Secret;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;

/**
 * Author: ASOU SAFARI
 * Date:9/28/24
 * Time:11:16 AM
 */
public class JWTValidatorFilter {
    public static boolean isValid(String token) {
        if (token != null && token.startsWith("Bearer ")) {
            token = token.substring(7).trim();
            try {
                SecretKey key = Keys.hmacShaKeyFor(Secret.generateSecret());
                Claims claims = Jwts.parser()
                        .verifyWith(key)
                        .build()
                        .parseSignedClaims(token)
                        .getPayload();
                return true;
            } catch (JwtException e) {
                throw new JwtException("Invalid Token");
            }
        }
        return false;
    }
}

