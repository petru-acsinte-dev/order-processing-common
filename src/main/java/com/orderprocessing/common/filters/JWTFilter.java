package com.orderprocessing.common.filters;

import java.io.IOException;

import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.orderprocessing.common.constants.Constants;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JWTFilter extends OncePerRequestFilter {

	private final JWTValidator jwtValidator;
	private final UserDetailsService userDetailsService;

	public JWTFilter(JWTValidator jwtValidator, UserDetailsService userDetailsService) {
		this.jwtValidator = jwtValidator;
		this.userDetailsService = userDetailsService;
	}

	@Override
	protected void doFilterInternal(
			HttpServletRequest request,
			HttpServletResponse response,
			FilterChain filterChain) throws ServletException, IOException {

		final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);

		if ((null == authHeader) || ! authHeader.startsWith(Constants.BEARER)) {
			filterChain.doFilter(request, response);
			return;
		}

		final String token = authHeader.substring(Constants.BEARER.length());

		final String username = jwtValidator.getUsername(token);

		if ((null != username) && (null == SecurityContextHolder.getContext().getAuthentication())) {
			final UserDetails userDetails = userDetailsService.loadUserByUsername(username);

			if (jwtValidator.isTokenValid(token, userDetails)) {
				final var authToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
				authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				SecurityContextHolder.getContext().setAuthentication(authToken);
			}
		}

		filterChain.doFilter(request, response);
	}

}
