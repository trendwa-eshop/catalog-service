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

        catalogItem.setId(catalogItemDto.id());
        catalogItem.setName(catalogItemDto.name());
        catalogItem.setDescription(catalogItemDto.description());
        catalogItem.setPrice(catalogItemDto.price());
        catalogItem.setPictureFileName(catalogItemDto.pictureFileName());
        catalogItem.setPictureUri(catalogItemDto.pictureUri());
        catalogItem.setCatalogType(CatalogTypeMapper.mapToEntity(catalogItemDto.catalogType()));
        catalogItem.setCatalogBrand(CatalogBrandMapper.mapToEntity(catalogItemDto.catalogBrand()));
        catalogItem.setAvailableStock(catalogItemDto.availableStock());
        catalogItem.setRestockThreshold(catalogItemDto.restockThreshold());
        catalogItem.setMaxStockThreshold(catalogItemDto.maxStockThreshold());
        catalogItem.setOnReorder(catalogItemDto.onReorder());

        return catalogItem;
    }


}
