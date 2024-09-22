package org.trendwa.eshop.catalogservice.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@Table(name = "catalog_type")
@NoArgsConstructor
public class CatalogType extends ApplicationEntity {
    public CatalogType(Long id) {
        super(id);
    }
    private String name;

}
