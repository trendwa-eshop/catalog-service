package org.trendwa.eshop.catalogservice.exception.handlers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import org.trendwa.eshop.catalogservice.dto.ErrorResponseDto;
import org.trendwa.eshop.catalogservice.exception.CatalogItemNotFoundException;

import java.time.LocalDateTime;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(CatalogItemNotFoundException.class)
    public ResponseEntity<ErrorResponseDto> handleCatalogItemNotFoundException(CatalogItemNotFoundException exception, WebRequest webRequest) {
        ErrorResponseDto error = new ErrorResponseDto();
        error.setStatusCode(HttpStatus.NOT_FOUND.value());
        error.setErrorCode(HttpStatus.NOT_FOUND.name());
        error.setMessage(exception.getMessage());
        error.setTimestamp(LocalDateTime.now());
        error.setDetails("The requested item was not found, please check the item id");
        error.setPath(((ServletWebRequest) webRequest).getRequest().getRequestURI());
        error.setExceptionType(exception.getClass().getSimpleName());

        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }


    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseDto> handleGlobalException(Exception exception, WebRequest webRequest) {
        ErrorResponseDto error = new ErrorResponseDto();
        error.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
        error.setErrorCode(HttpStatus.INTERNAL_SERVER_ERROR.name());
        error.setMessage(exception.getMessage());
        error.setTimestamp(LocalDateTime.now());
        error.setDetails("An unexpected error occurred");
        error.setPath(((ServletWebRequest) webRequest).getRequest().getRequestURI());
        error.setExceptionType(exception.getClass().getSimpleName());

        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
