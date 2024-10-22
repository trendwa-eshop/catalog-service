package org.trendwa.eshop.catalogservice.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.trendwa.eshop.catalogservice.validation.CatalogItemValidations.Description;
import org.trendwa.eshop.catalogservice.validation.CatalogItemValidations.Name;
import org.trendwa.eshop.catalogservice.validation.CatalogItemValidations.Price;
import org.trendwa.eshop.catalogservice.validation.CatalogItemValidations.Stock;

@Schema(description = "Schema for Catalog Item")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CatalogItemDto {
    @Schema(description = "Unique identifier of the catalog item, if you are updating an existing item, please provide the ID")
    private Long id;

    @Name
    @Schema(description = "Name of the catalog item", example = "Apple iPhone 13")
    private String name;

    @Description
    @Schema(description = "Description of the catalog item", example = "Latest model of Apple iPhone")
    private String description;

    @Price
    @Schema(description = "Price of the catalog item", example = "999.99")
    private Double price;

    @Schema(description = "File name of the catalog item's picture")
    private String pictureFileName;

    @Schema(description = "URI of the catalog item's picture")
    private String pictureUri;

    @Schema(description = "Type of the catalog item", example = "1")
    private CatalogTypeDto catalogType;

    @Schema(description = "Brand of the catalog item", example = "1")
    private CatalogBrandDto catalogBrand;

    @Stock
    @Schema(description = "Available stock of the catalog item", example = "50")
    private int availableStock;

    @Min(value = 0, message = "Restock threshold cannot be less than 0")
    @Schema(description = "Restock threshold of the catalog item", example = "10")
    private int restockThreshold;

    @Min(value = 0, message = "Maximum stock threshold cannot be less than 0")
    @Schema(description = "Maximum stock threshold of the catalog item", example = "100")
    private int maxStockThreshold;

    @Schema(description = "Indicates if the catalog item is on reorder", example = "false")
    private boolean onReorder;
}