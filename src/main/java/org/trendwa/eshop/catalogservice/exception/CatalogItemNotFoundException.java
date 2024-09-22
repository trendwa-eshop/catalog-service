package org.trendwa.eshop.catalogservice.exception;

public class CatalogItemNotFoundException extends RuntimeException {
    public CatalogItemNotFoundException(String message) {
        super(message);
    }
}
