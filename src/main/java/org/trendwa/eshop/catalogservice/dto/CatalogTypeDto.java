package org.trendwa.eshop.catalogservice.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Schema(description = "Schema for Catalog Type")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CatalogTypeDto {
    @Schema(description = "Unique identifier of the catalog type", example = "1")
    private Long id;

    @Schema(description = "Name of the catalog type", example = "Smartphone")
    private String name;

    public CatalogTypeDto(Long id) {
        this.id = id;
    }
}