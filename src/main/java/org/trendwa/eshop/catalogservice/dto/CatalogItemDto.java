package org.trendwa.eshop.catalogservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Schema for Catalog Item")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CatalogItemDto {
    @Schema(description = "Unique identifier of the catalog item", example = "1")
    private Long id;

    @Schema(description = "Name of the catalog item", example = "Apple iPhone 13")
    private String name;

    @Schema(description = "Description of the catalog item", example = "Latest model of Apple iPhone")
    private String description;

    @Schema(description = "Price of the catalog item", example = "999.99")
    private Double price;

    @Schema(description = "File name of the catalog item's picture", example = "iphone13.jpg")
    private String pictureFileName;

    @Schema(description = "URI of the catalog item's picture", example = "uri/iphone13.jpg")
    private String pictureUri;

    @Schema(description = "Type of the catalog item", example = "{\"id\": 1, \"type\": \"Smartphone\"}")
    private CatalogTypeDto catalogType;

    @Schema(description = "Brand of the catalog item", example = "{\"id\": 1, \"brand\": \"Apple\"}")
    private CatalogBrandDto catalogBrand;

    @Schema(description = "Available stock of the catalog item", example = "50")
    private int availableStock;

    @Schema(description = "Restock threshold of the catalog item", example = "10")
    private int restockThreshold;

    @Schema(description = "Maximum stock threshold of the catalog item", example = "100")
    private int maxStockThreshold;

    @Schema(description = "Indicates if the catalog item is on reorder", example = "false")
    private boolean onReorder;
}