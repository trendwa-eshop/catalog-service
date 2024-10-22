package org.trendwa.eshop.catalogservice.repository;

import org.trendwa.eshop.catalogservice.model.CatalogType;

/**
 * Repository interface for CatalogType entities.
 * Extends ApplicationJpaRepository to provide CRUD operations and custom query methods.
 * @author Musa Beytekin
 */
public interface CatalogTypeRepository extends ApplicationJpaRepository<CatalogType, Long> {
}
