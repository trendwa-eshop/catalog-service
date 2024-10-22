package org.trendwa.eshop.catalogservice.repository;

import org.trendwa.eshop.catalogservice.model.CatalogBrand;

/**
 * Repository interface for CatalogBrand entities.
 * Extends ApplicationJpaRepository to provide CRUD operations and custom query methods.
 * @author Musa Beytekin
 */
public interface CatalogBrandRepository extends ApplicationJpaRepository<CatalogBrand, Long> {
}
