package org.trendwa.eshop.catalogservice.mapper;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.trendwa.eshop.catalogservice.dto.CatalogItemDto;
import org.trendwa.eshop.catalogservice.model.CatalogItem;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CatalogItemMapper {
    public static CatalogItemDto mapToDto(CatalogItem catalogItem) {
        return new CatalogItemDto(
                catalogItem.getId(),
                catalogItem.getName(),
                catalogItem.getDescription(),
                catalogItem.getPrice(),
                catalogItem.getPictureFileName(),
                catalogItem.getPictureUri(),
                CatalogTypeMapper.mapToDto(catalogItem.getCatalogType()),
                CatalogBrandMapper.mapToDto(catalogItem.getCatalogBrand()),
                catalogItem.getAvailableStock(),
                catalogItem.getRestockThreshold(),
                catalogItem.getMaxStockThreshold(),
                catalogItem.isOnReorder()
        );
    }

    public static CatalogItem mapToEntity(CatalogItemDto catalogItemDto) {
        var catalogItem = new CatalogItem();

        catalogItem.setId(catalogItemDto.getId());
        catalogItem.setName(catalogItemDto.getName());
        catalogItem.setDescription(catalogItemDto.getDescription());
        catalogItem.setPrice(catalogItemDto.getPrice());
        catalogItem.setPictureFileName(catalogItemDto.getPictureFileName());
        catalogItem.setPictureUri(catalogItemDto.getPictureUri());
        catalogItem.setCatalogType(CatalogTypeMapper.mapToEntity(catalogItemDto.getCatalogType()));
        catalogItem.setCatalogBrand(CatalogBrandMapper.mapToEntity(catalogItemDto.getCatalogBrand()));
        catalogItem.setAvailableStock(catalogItemDto.getAvailableStock());
        catalogItem.setRestockThreshold(catalogItemDto.getRestockThreshold());
        catalogItem.setMaxStockThreshold(catalogItemDto.getMaxStockThreshold());
        catalogItem.setOnReorder(catalogItemDto.isOnReorder());

        return catalogItem;
    }


}
