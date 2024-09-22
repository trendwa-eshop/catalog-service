package org.trendwa.eshop.catalogservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.trendwa.eshop.catalogservice.model.CatalogItem;
import org.springframework.data.domain.Pageable;
import java.util.List;

/**
 * Repository interface for CatalogItem entities.
 * Extends JpaRepository to provide CRUD operations and custom query methods.
 */
public interface CatalogItemRepository extends JpaRepository<CatalogItem, Long> {

    /**
     * Finds a list of CatalogItem entities where the name contains the specified string.
     *
     * @param name the string to search for within the name field
     * @param pageable the pagination information
     * @return a list of CatalogItem entities matching the search criteria
     */
    List<CatalogItem> findByNameContaining(String name, Pageable pageable);

    /**
     * Finds a list of CatalogItem entities by the specified brand name.
     *
     * @param brand the brand name to search for
     * @param pageable the pagination information
     * @return a list of CatalogItem entities matching the brand name
     */
    List<CatalogItem> findByCatalogBrandName(String brand, Pageable pageable);

    /**
     * Finds a list of CatalogItem entities by the specified type name.
     *
     * @param type the type name to search for
     * @param pageable the pagination information
     * @return a list of CatalogItem entities matching the type name
     */
    List<CatalogItem> findByCatalogTypeName(String type, Pageable pageable);

    /**
     * Finds a list of CatalogItem entities by the specified brand name and type name.
     *
     * @param brand the brand name to search for
     * @param type the type name to search for
     * @param pageable the pagination information
     * @return a list of CatalogItem entities matching the brand name and type name
     */
    List<CatalogItem> findByCatalogBrandNameAndCatalogTypeName(String brand, String type, Pageable pageable);
}