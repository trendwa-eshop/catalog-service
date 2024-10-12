package org.trendwa.eshop.catalogservice.exception.handlers;

import jakarta.validation.constraints.NotNull;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.HandlerMethodValidationException;
import org.trendwa.eshop.catalogservice.dto.ErrorInfo;

@ControllerAdvice
public class GlobalExceptionHandler extends RestExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorInfo> handleGlobalException(Exception exception, WebRequest webRequest) {
        return buildErrorResponse(exception, webRequest, HttpStatus.INTERNAL_SERVER_ERROR, "An unexpected error occurred");
    }
}