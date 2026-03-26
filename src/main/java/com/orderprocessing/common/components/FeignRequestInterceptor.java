package com.orderprocessing.common.components;

import org.slf4j.MDC;
import org.springframework.stereotype.Component;

import com.orderprocessing.common.constants.Constants;

import feign.RequestInterceptor;
import feign.RequestTemplate;

@Component
public class FeignRequestInterceptor implements RequestInterceptor {

	@Override
	public void apply(RequestTemplate template) {
		final String requestId = MDC.get(Constants.CORRELATION_HEADER);
		if (null != requestId) {
			template.header(Constants.CORRELATION_HEADER, requestId);
		}
	}

}
