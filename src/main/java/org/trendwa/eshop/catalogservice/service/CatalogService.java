package org.trendwa.eshop.catalogservice.service;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.trendwa.eshop.catalogservice.dto.CatalogItemDto;

import java.util.List;

@Service
public interface CatalogService {


    /**
     * Retrieves all catalog items with pagination support.
     *
     * @param pageable the pagination information
     * @return a list of catalog items
     */
    List<CatalogItemDto> getAll(Pageable pageable);

    /**
     * Retrieves a catalog item by its ID.
     *
     * @param id the ID of the catalog item
     * @return the catalog item with the specified ID
     */
    CatalogItemDto getItemById(Long id);

    /**
     * Retrieves catalog items by their IDs.
     *
     * @param ids the list of IDs of the catalog items
     * @return a list of catalog items with the specified IDs
     */
    List<CatalogItemDto> getItemsByIds(List<Long> ids);

    /**
     * Retrieves catalog items whose names contain the specified string, with pagination support.
     *
     * @param name     the string to search for in the catalog item names
     * @param pageable the pagination information
     * @return a list of catalog items whose names contain the specified string
     */
    List<CatalogItemDto> getItemsByNameContaining(String name, Pageable pageable);

    /**
     * Retrieves catalog items by their brand, with pagination support.
     *
     * @param brand    the brand of the catalog items
     * @param pageable the pagination information
     * @return a list of catalog items with the specified brand
     */
    List<CatalogItemDto> getItemsByBrand(String brand, Pageable pageable);

    /**
     * Retrieves catalog items by their type, with pagination support.
     *
     * @param type     the type of the catalog items
     * @param pageable the pagination information
     * @return a list of catalog items with the specified type
     */
    List<CatalogItemDto> getItemsByType(String type, Pageable pageable);

    /**
     * Retrieves catalog items by their brand and type, with pagination support.
     *
     * @param brand    the brand of the catalog items
     * @param type     the type of the catalog items
     * @param pageable the pagination information
     * @return a list of catalog items with the specified brand and type
     */
    List<CatalogItemDto> getItemsByBrandAndType(String brand, String type, Pageable pageable);

    /**
     * Saves a catalog item.
     *
     * @param item the catalog item to save
     * @return the saved catalog item
     */
    CatalogItemDto save(CatalogItemDto item);

    /**
     * Deletes a catalog item by its ID.
     *
     * @param id the ID of the catalog item to delete
     */
    void deleteById(Long id);
}