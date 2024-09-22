package org.trendwa.eshop.catalogservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.trendwa.eshop.catalogservice.model.CatalogItem;
import org.springframework.data.domain.Pageable;
import java.util.List;

public interface CatalogItemRepository extends JpaRepository<CatalogItem, Long> {

    List<CatalogItem> findByNameContaining(String name, Pageable pageable);
    List<CatalogItem> findByCatalogBrandName(String brand, Pageable pageable);
    List<CatalogItem> findByCatalogTypeName(String type, Pageable pageable);
    List<CatalogItem> findByCatalogBrandNameAndCatalogTypeName(String brand, String type, Pageable pageable);
}
