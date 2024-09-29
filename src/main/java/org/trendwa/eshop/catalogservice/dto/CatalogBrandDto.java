package org.trendwa.eshop.catalogservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CatalogBrandDto {
    private Long id;
    private String name;

    public CatalogBrandDto(Long id) {
        this.id = id;
    }
}