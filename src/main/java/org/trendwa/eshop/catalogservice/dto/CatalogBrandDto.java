package org.trendwa.eshop.catalogservice.dto;

public record CatalogBrandDto(Long id, String name) {
    public CatalogBrandDto(Long id) {
        this(id, null);
    }
}
