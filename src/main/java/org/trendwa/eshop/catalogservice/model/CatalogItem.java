package org.trendwa.eshop.catalogservice.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.trendwa.eshop.catalogservice.exception.domain.CatalogDomainException;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@NoArgsConstructor
@Table(name = "catalog_item", indexes = {
        @Index(name = "idx_catalog_items_name", columnList = "name"),
})
public class CatalogItem extends ApplicationEntity {

    @Column(nullable = false, name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "price")
    private double price;

    @Column(name = "picture_file_name", unique = true)
    private String pictureFileName;

    @Column(name = "picture_uri", unique = true)
    private String pictureUri;

    @ManyToOne(cascade = {
            CascadeType.DETACH})
    @JoinColumn(name = "catalog_type_id")
    private CatalogType catalogType;

    @ManyToOne(cascade = {
            CascadeType.DETACH})
    @JoinColumn(name = "catalog_brand_id")
    private CatalogBrand catalogBrand;

    @Column(name = "available_stock")
    private int availableStock;

    @Column(name = "restock_threshold")
    private int restockThreshold;

    @Column(name = "max_stock_threshold")
    private int maxStockThreshold;

    @Column(name = "on_reorder")
    private boolean onReorder;

    /**
     * Removes stock from the catalog item.
     *
     * @param quantityDesired the number of units to remove from the stock
     * @return the number of units actually removed from the stock
     * @throws CatalogDomainException if the quantity desired is less than 0 or if there is no available stock
     */
    public int removeStock(int quantityDesired) {
        if (this.availableStock == 0) {
            throw new CatalogDomainException("No available stock for item " + this.name);
        }
        if (quantityDesired <= 0) {
            throw new CatalogDomainException("Item units desired must be greater than 0");
        }

        int removed = Math.min(quantityDesired, this.availableStock);
        this.availableStock -= removed;

        return removed;

    }

    /**
     * Adds stock to the catalog item.
     *
     * @param quantityDesired the number of units to add to the stock
     * @return the number of units actually added to the stock
     * @throws CatalogDomainException if the quantity desired is less than 0
     */
    public int addStock(int quantityDesired) {
        if (quantityDesired < 0) {
            throw new CatalogDomainException("Item units desired must be greater than 0");
        }

        int originalStock = this.availableStock;
        this.availableStock = Math.min(this.availableStock + quantityDesired, this.maxStockThreshold);

        int addedStock = this.availableStock - originalStock;

        if (addedStock > 0) {
            this.onReorder = false;
        }

        return addedStock;
    }
}
