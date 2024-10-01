package org.trendwa.eshop.catalogservice.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.trendwa.eshop.catalogservice.validation.CatalogBrandValidations.*;

@Schema(description = "Schema for Catalog Brand")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CatalogBrandDto {
    @Schema(description = "Unique identifier of the catalog brand", example = "1")
    private Long id;

    @Name
    @Schema(description = "Name of the catalog brand", example = "Apple")
    private String name;

    public CatalogBrandDto(Long id) {
        this.id = id;
    }
}