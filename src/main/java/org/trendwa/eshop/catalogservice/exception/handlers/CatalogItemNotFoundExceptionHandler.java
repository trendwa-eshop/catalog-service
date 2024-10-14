package org.trendwa.eshop.catalogservice.exception.handlers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.trendwa.eshop.catalogservice.dto.ErrorInfo;
import org.trendwa.eshop.catalogservice.exception.CatalogItemNotFoundException;

@ControllerAdvice
public class CatalogItemNotFoundExceptionHandler extends ExceptionHandlerRest {

    @ExceptionHandler(CatalogItemNotFoundException.class)
    public ResponseEntity<ErrorInfo> handleCatalogItemNotFoundException(CatalogItemNotFoundException exception, WebRequest request) {
        return buildErrorResponse(exception, request, HttpStatus.NOT_FOUND, "The requested item was not found, please check the item id");
    }
}