package org.trendwa.eshop.catalogservice.dto;

public record CatalogItemDto(
        Long id,
        String name,
        String description,
        double price,
        String pictureFileName,
        String pictureUri,
        CatalogTypeDto catalogType,
        CatalogBrandDto catalogBrand,
        int availableStock,
        int restockThreshold,
        int maxStockThreshold,
        boolean onReorder
) {
}