package org.trendwa.eshop.catalogservice.exception.handlers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import org.trendwa.eshop.catalogservice.dto.ErrorInfo;

import java.time.LocalDateTime;

public class ExceptionHandlerRest extends ResponseEntityExceptionHandler {

    protected ResponseEntity<ErrorInfo> buildErrorResponse(Exception exception, WebRequest webRequest, HttpStatus status, String details) {
        ErrorInfo errorInfo = new ErrorInfo();

        errorInfo.setStatusCode(status.value());
        errorInfo.setErrorCode(status.name());
        errorInfo.setMessage(exception.getMessage());
        errorInfo.setExceptionType(exception.getClass().getSimpleName());
        errorInfo.setTimestamp(LocalDateTime.now());
        errorInfo.setDetails(details);
        errorInfo.setPath(((ServletWebRequest) webRequest).getRequest().getRequestURI());

        return new ResponseEntity<>(errorInfo, status);
    }
}