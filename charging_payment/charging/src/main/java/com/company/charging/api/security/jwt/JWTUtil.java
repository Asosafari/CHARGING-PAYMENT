package com.company.charging.api.security.jwt;

import com.company.charging.api.security.filter.Secret;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.crypto.SecretKey;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Author: ASOU SAFARI
 * Date:9/20/24
 * Time:5:12 PM
 */

public class JWTUtil {

    public static String jwtTokenGenerator() {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String jwt;
            if (authentication != null) {
                SecretKey key = Keys.hmacShaKeyFor(Secret.generateSecret());
                return jwt = Jwts.builder().issuer("Beh").subject("JWT_Token")
                        .claim("username", authentication.getName())
                        .claim("authorities", generateAuthorities(authentication.getAuthorities()))
                        .issuedAt(new Date())
                        .expiration(new Date(System.currentTimeMillis() + 10 * 60 * 1000L))
                        .signWith(key)
                        .compact();
            }
            return "UNAUTHORIZED";
    }
private static String generateAuthorities(Collection<? extends GrantedAuthority> collection) {
    Set<String> authorities = new HashSet<>();
    collection.forEach(grantedAuthority -> authorities.add(grantedAuthority.getAuthority()));
    return String.join(",", authorities);
    }
}
