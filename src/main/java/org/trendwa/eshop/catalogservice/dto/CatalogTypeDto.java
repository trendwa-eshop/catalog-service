package org.trendwa.eshop.catalogservice.dto;

public record CatalogTypeDto(Long id, String name) {
    public CatalogTypeDto(Long id) {
        this(id, null);
    }
}
