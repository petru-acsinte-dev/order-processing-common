package com.orderprocessing.common.tests;

import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.List;

import javax.crypto.SecretKey;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

public class TestJwtTokenGenerator {

	/**
     * Generates a signed JWT token for use in ITs.
     * @param username The username to embed in the token.
     * @param roles List of roles e.g. "ROLE_ADMIN", "ROLE_USER"
     * @param secret The signing secret — must match the service's test config.
     * @return Signed JWT token string.
     */
    public static String generateToken(String username, List<String> roles, String secret) {
        final SecretKey key = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
        return Jwts.builder()
                .subject(username)
                .claim("roles", roles) //$NON-NLS-1$
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + 3_600_000)) // 1 hour
                .signWith(key)
                .compact();
    }

    public static String generateToken(String username, String role, String secret) {
        return generateToken(username, List.of(role), secret);
    }

}
