package org.trendwa.eshop.catalogservice.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "catalog_brand")
@NoArgsConstructor
public class CatalogBrand extends ApplicationEntity{

    public CatalogBrand(Long id) {
        super(id);
    }
    private String name;

}
