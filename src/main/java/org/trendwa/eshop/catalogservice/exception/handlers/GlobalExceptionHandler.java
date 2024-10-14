package org.trendwa.eshop.catalogservice.exception.handlers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.trendwa.eshop.catalogservice.dto.ErrorInfo;

@ControllerAdvice
public class GlobalExceptionHandler extends ExceptionHandlerRest {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorInfo> handleGlobalException(Exception exception, WebRequest webRequest) {
        return buildErrorResponse(exception, webRequest, HttpStatus.INTERNAL_SERVER_ERROR, "An unexpected error occurred");
    }
}