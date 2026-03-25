package com.orderprocessing.common.filters;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.crypto.SecretKey;

import org.springframework.security.core.userdetails.UserDetails;

import com.orderprocessing.common.constants.Constants;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

/**
 * Utility class providing common functionality around JWT validation and data extraction.
 */
public abstract class JWTValidatorCommon implements JWTValidator {

	private final SecretKey secretKey;

	protected JWTValidatorCommon(String secret) {
		this.secretKey = Keys.hmacShaKeyFor(Decoders.BASE64.decode(secret));
	}

	protected SecretKey getSecret() {
		return secretKey;
	}

	@Override
	public boolean isTokenValid(String token, UserDetails userDetails) {
		final boolean expired = isTokenExpired(token);
		if (expired) {
			return false;
		}
		final String username = getUsername(token);
		return username.equals(userDetails.getUsername());
	}

	@Override
	public String getUsername(String token) {
		final Claims claims = extractClaims(token);
		return claims.getSubject();
	}

	@Override
	public UUID getExternalId(String token) {
		final Claims claims = extractClaims(token);
		return UUID.fromString(claims.get(Constants.PARAM_EXTERNAL_ID, String.class));
	}

	@Override
	public List<String> getRoles(String token) {
	    final Claims claims = extractClaims(token);
	    final List<?> roles = claims.get(Constants.ROLES, List.class);
	    return roles.stream()
	    			.filter(String.class::isInstance)
	    			.map(String.class::cast)
	    			.toList();
	}

	private boolean isTokenExpired(String token) {
		final Claims claims = extractClaims(token);
		return claims.getExpiration().before(new Date());
	}

	private Claims extractClaims(String token) {
		return Jwts.parser()
				.verifyWith(secretKey)
				.build()
				.parseSignedClaims(token)
				.getPayload();
	}
}
