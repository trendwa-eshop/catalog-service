package org.trendwa.eshop.catalogservice.mapper;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.trendwa.eshop.catalogservice.dto.CatalogBrandDto;
import org.trendwa.eshop.catalogservice.model.CatalogBrand;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CatalogBrandMapper {

        public static CatalogBrandDto mapToDto(CatalogBrand catalogBrand) {
            return new CatalogBrandDto(
                    catalogBrand.getId(),
                    catalogBrand.getName()
            );
        }

        public static CatalogBrand mapToEntity(CatalogBrandDto catalogBrandDto) {
            var catalogBrand = new CatalogBrand();
            catalogBrand.setId(catalogBrandDto.getId());
            catalogBrand.setName(catalogBrandDto.getName());
            return catalogBrand;
        }
}
