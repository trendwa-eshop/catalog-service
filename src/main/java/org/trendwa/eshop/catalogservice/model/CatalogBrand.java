package org.trendwa.eshop.catalogservice.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "catalog_brand")
@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
public class CatalogBrand extends ApplicationEntity{

    public CatalogBrand(Long id) {
        super(id);
    }
    private String name;

}
