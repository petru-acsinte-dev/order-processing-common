package com.orderprocessing.common.filters;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.orderprocessing.common.constants.Constants;
import com.orderprocessing.common.security.OrdersPrincipal;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
/**
 * Bean used to intercept every request.
 * Extracts JWT from Authorization header, reads the username and roles directly from token claims.
 * Sets Spring SecurityContext.
 * No database or UserDetailsService dependencies.
 */
public class JWTFilter extends OncePerRequestFilter {

	Logger log = LoggerFactory.getLogger(JWTFilter.class);

	private final JWTValidator jwtValidator;

	public JWTFilter(JWTValidator jwtValidator) {
		this.jwtValidator = jwtValidator;
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
		final UUID externalId = jwtValidator.getExternalId(token);
		final OrdersPrincipal principal = new OrdersPrincipal(username, externalId);

		if ((null != username) && (null == SecurityContextHolder.getContext().getAuthentication())) {
			final List<String> roles = jwtValidator.getRoles(token);
            final List<SimpleGrantedAuthority> authorities = roles.stream().map(SimpleGrantedAuthority::new).toList();

			final var authToken = new UsernamePasswordAuthenticationToken(principal, null, authorities);
			authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
			SecurityContextHolder.getContext().setAuthentication(authToken);
		}

		filterChain.doFilter(request, response);
	}

}
