package org.trendwa.eshop.catalogservice.exception.handlers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.trendwa.eshop.catalogservice.dto.ErrorInfo;
import org.trendwa.eshop.catalogservice.exception.domain.CatalogDomainException;

@ControllerAdvice
public class CatalogDomainExceptionHandler extends ExceptionHandlerRest {

    @ExceptionHandler(CatalogDomainException.class)
    public ResponseEntity<ErrorInfo> handleCatalogDomainException(CatalogDomainException exception, WebRequest webRequest) {
        return buildErrorResponse(exception, webRequest, HttpStatus.BAD_REQUEST, "A catalog domain error occurred");
    }
}