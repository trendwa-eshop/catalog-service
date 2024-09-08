package org.trendwa.eshop.catalogservice.model;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@NoArgsConstructor
@Table(name = "catalog_items")
public class CatalogItem extends ApplicationEntity {

    private String name;
    private String description;
    private double price;
    private String pictureFileName;
    private String pictureUri;

    @ManyToOne
    private CatalogType catalogType;

    @ManyToOne
    private CatalogBrand catalogBrand;

    private int availableStock;
    private int restockThreshold;
    private int maxStockThreshold;
    private boolean onReorder;
}
