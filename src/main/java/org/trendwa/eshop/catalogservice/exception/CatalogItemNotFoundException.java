package org.trendwa.eshop.catalogservice.exception;

public class CatalogItemNotFoundException extends RuntimeException {
    public CatalogItemNotFoundException(String message) {
        super(message);
    }

    public CatalogItemNotFoundException(Long itemId) {
        super("Catalog item not found with id: " + itemId);
    }
}
