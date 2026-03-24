package com.orderprocessing.common.filters;

import org.springframework.security.core.userdetails.UserDetails;

public interface JWTValidator {

	/**
	 * Returns the username associated with the specified token.
	 * @param token The JWT token.
	 * @return The associated username.
	 */
	String getUsername(String token);

	/**
	 * Confirms whethere the token is valid.
	 * @param token Token to validate.
	 * @param userDetails The user specific information.
	 * @return True if token is valid, false otherwise.
	 */
	boolean isTokenValid(String token, UserDetails userDetails);
}
