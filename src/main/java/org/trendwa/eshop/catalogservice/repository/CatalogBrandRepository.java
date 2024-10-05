package org.trendwa.eshop.catalogservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.trendwa.eshop.catalogservice.model.CatalogBrand;

/**
 * Repository interface for CatalogBrand entities.
 * Extends JpaRepository to provide CRUD operations and custom query methods.
 * @author Musa Beytekin
 */
public interface CatalogBrandRepository extends JpaRepository<CatalogBrand, Long> {
}
