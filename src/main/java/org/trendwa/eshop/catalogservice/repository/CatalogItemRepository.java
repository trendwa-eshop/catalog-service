package org.trendwa.eshop.catalogservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.trendwa.eshop.catalogservice.model.CatalogItem;

public interface CatalogItemRepository extends JpaRepository<CatalogItem, Long> {
}
