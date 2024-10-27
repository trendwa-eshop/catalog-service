package org.trendwa.eshop.catalogservice.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "catalog_type")
@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
public class CatalogType extends ApplicationEntity {
    public CatalogType(Long id) {
        super(id);
    }
    private String name;

}
