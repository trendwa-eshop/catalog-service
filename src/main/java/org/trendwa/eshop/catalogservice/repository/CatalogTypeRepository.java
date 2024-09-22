package org.trendwa.eshop.catalogservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.trendwa.eshop.catalogservice.model.CatalogType;

/**
 * Repository interface for CatalogType entities.
 * Extends JpaRepository to provide CRUD operations and custom query methods.
 * @author Musa Beytekin
 */
public interface CatalogTypeRepository extends JpaRepository<CatalogType, Long> {
}
