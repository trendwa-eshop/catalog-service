package org.trendwa.eshop.catalogservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CatalogItemDto {
    private Long id;
    private String name;
    private String description;
    private Double price;
    private String pictureFileName;
    private String pictureUri;
    private CatalogTypeDto catalogType;
    private CatalogBrandDto catalogBrand;
    private int availableStock;
    private int restockThreshold;
    private int maxStockThreshold;
    private boolean onReorder;
}