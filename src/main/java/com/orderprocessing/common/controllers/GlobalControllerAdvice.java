package com.orderprocessing.common.controllers;

import java.util.Locale;

import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.orderprocessing.common.exceptions.ApiError;
import com.orderprocessing.common.exceptions.ApiErrors;
import com.orderprocessing.common.security.MessageKeys;

@RestControllerAdvice
public class GlobalControllerAdvice {

	@ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiError handleMessageNotReadable(HttpMessageNotReadableException ex, Locale locale) {
        return new ApiError(ApiErrors.INCORRECT_INPUT, MessageKeys.INVALID_REQUEST_BODY);
    }

}
