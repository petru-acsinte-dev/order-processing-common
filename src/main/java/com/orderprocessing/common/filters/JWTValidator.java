package com.orderprocessing.common.filters;

import java.util.List;

import org.springframework.security.core.userdetails.UserDetails;

/**
 * Includes methods that extract the username and roles from JWT and that validate the JWT.
 */
public interface JWTValidator {

	/**
	 * Returns the username associated with the specified token.
	 * @param token The JWT token.
	 * @return The associated username.
	 */
	String getUsername(String token);

	/**
	 * Confirms whether the token is valid.
	 * @param token Token to validate.
	 * @param userDetails The user specific information.
	 * @return True if token is valid, false otherwise.
	 */
	boolean isTokenValid(String token, UserDetails userDetails);

	/**
	 * Returns the roles in the self-contained JWT.
	 * @param token
	 * @return
	 */
	List<String> getRoles(String token);

}
