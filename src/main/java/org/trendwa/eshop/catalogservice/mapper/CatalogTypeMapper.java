package org.trendwa.eshop.catalogservice.mapper;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.trendwa.eshop.catalogservice.dto.CatalogTypeDto;
import org.trendwa.eshop.catalogservice.model.CatalogType;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CatalogTypeMapper {

    public static CatalogTypeDto mapToDto(CatalogType catalogType) {
        return new CatalogTypeDto(
                catalogType.getId(),
                catalogType.getName()
        );
    }

    public static CatalogType mapToEntity(CatalogTypeDto catalogTypeDto) {
        var catalogType = new CatalogType();
        catalogType.setId(catalogTypeDto.id());
        catalogType.setName(catalogTypeDto.name());
        return catalogType;
    }
}
