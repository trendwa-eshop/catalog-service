package org.trendwa.eshop.catalogservice.model;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.trendwa.eshop.catalogservice.exception.domain.CatalogDomainException;

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

    public int removeStock(int quantityDesired){
        if(this.availableStock == 0){
            throw new CatalogDomainException("No available stock for item " + this.name);
        }
        if ( quantityDesired <= 0){
            throw new CatalogDomainException("Item units desired must be greater than 0");
        }

        int removed = Math.min(quantityDesired, this.availableStock);
        this.availableStock -= removed;

        return removed;

    }
}
