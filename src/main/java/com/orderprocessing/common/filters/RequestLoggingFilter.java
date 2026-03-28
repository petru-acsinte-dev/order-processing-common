package com.orderprocessing.common.filters;

import java.io.IOException;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.orderprocessing.common.constants.Constants;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class RequestLoggingFilter extends OncePerRequestFilter {

	private static final Logger log = LoggerFactory.getLogger(RequestLoggingFilter.class);

	private static final String PATH = "path"; //$NON-NLS-1$
	private static final String METHOD = "method"; //$NON-NLS-1$

	@Override
	protected void doFilterInternal(HttpServletRequest request,
									HttpServletResponse response,
									FilterChain filterChain)
			throws ServletException, IOException {

		final long startTime = System.currentTimeMillis();

		String requestId = request.getHeader(Constants.CORRELATION_HEADER);

		if (requestId == null || requestId.isBlank()) {
            requestId = UUID.randomUUID().toString().substring(0, 8);
        }

		try {
            MDC.put(Constants.CORRELATION_ID, requestId);

            response.setHeader(Constants.CORRELATION_HEADER, requestId);

            log.atDebug()
            .addKeyValue(METHOD, request.getMethod())
            .addKeyValue(PATH, request.getRequestURI())
            .addKeyValue("IP", request.getRemoteAddr()) //$NON-NLS-1$
            .log("incoming"); //$NON-NLS-1$

            filterChain.doFilter(request, response);
        } finally {
        	final long durationMs = System.currentTimeMillis() - startTime;

            log.atDebug()
               .addKeyValue(METHOD, request.getMethod())
               .addKeyValue(PATH, request.getRequestURI())
               .addKeyValue("status", response.getStatus()) //$NON-NLS-1$
               .addKeyValue("durationMs", durationMs) //$NON-NLS-1$
               .log("done"); //$NON-NLS-1$

            MDC.clear();
        }
	}

}
