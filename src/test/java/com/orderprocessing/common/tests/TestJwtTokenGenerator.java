package com.orderprocessing.common.tests;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.crypto.SecretKey;

import com.orderprocessing.common.constants.Constants;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

public class TestJwtTokenGenerator {

	/**
     * Generates a signed JWT token for use in ITs.
     * @param username The username to embed in the token.
     * @param externalId The user external identifier (UUID).
     * @param roles List of roles (e.g. "ROLE_ADMIN", "ROLE_USER")
     * @param secret The signing secret — must match the service's test config.
     * @return Signed JWT token string.
     */
    public static String generateToken(	String username,
    									UUID externalId,
    									List<String> roles,
    									String secret) {
    	final SecretKey key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(secret));
        return Jwts.builder()
                .subject(username)
                .claim(Constants.ROLES, roles)
                .claim(Constants.PARAM_EXTERNAL_ID, externalId)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + 3_600_000)) // 1 hour
                .signWith(key)
                .compact();
    }

    /**
     * Generates a signed JWT token for use in ITs.
     * @param username The username to embed in the token.
     * @param externalId The user external identifier (UUID).
     * @param role A singular role to assign (e.g. "ROLE_ADMIN", "ROLE_USER")
     * @param secret The signing secret — must match the service's test config.
     * @return Signed JWT token string.
     */
    public static String generateToken(	String username,
    									UUID externalId,
    									String role,
    									String secret) {
        return generateToken(username, externalId, List.of(role), secret);
    }

}
