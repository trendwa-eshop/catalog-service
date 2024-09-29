package org.trendwa.eshop.catalogservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CatalogTypeDto {
    private Long id;
    private String name;

    public CatalogTypeDto(Long id) {
        this.id = id;
    }
}