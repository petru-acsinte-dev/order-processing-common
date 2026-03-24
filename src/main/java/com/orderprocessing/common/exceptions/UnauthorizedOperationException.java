package com.orderprocessing.common.exceptions;

import com.orderprocessing.common.security.MessageKeys;

public class UnauthorizedOperationException extends ForbiddenApiException {

	private static final long serialVersionUID = 1L;

	public UnauthorizedOperationException() {
		super(ApiErrors.UNAUTHORIZED_OPERATION, MessageKeys.UNAUTHORIZED_OPERATION);
	}

}
