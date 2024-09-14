package org.trendwa.eshop.catalogservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.trendwa.eshop.catalogservice.model.CatalogBrand;

public interface CatalogBrandRepository extends JpaRepository<CatalogBrand, Long> {
}
