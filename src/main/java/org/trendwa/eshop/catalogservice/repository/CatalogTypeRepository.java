package org.trendwa.eshop.catalogservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.trendwa.eshop.catalogservice.model.CatalogType;

public interface CatalogTypeRepository extends JpaRepository<CatalogType, Long> {
}
