package org.trendwa.eshop.catalogservice.service;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.trendwa.eshop.catalogservice.dto.CatalogBrandDto;
import org.trendwa.eshop.catalogservice.dto.CatalogItemDto;
import org.trendwa.eshop.catalogservice.dto.CatalogTypeDto;

import java.io.IOException;
import java.util.List;

@Service
public interface CatalogService {


    /**
     * Retrieves a paginated list of catalog brands.
     *
     * @param pageable the pagination information
     * @return a list of catalog brand DTOs
     */
    List<CatalogBrandDto> getBrands(Pageable pageable);

    /**
     * Retrieves a paginated list of catalog types.
     *
     * @param pageable the pagination information
     * @return a list of catalog type DTOs
     */
    List<CatalogTypeDto> getTypes(Pageable pageable);

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
     * Creates a new catalog item.
     *
     * @param item         the catalog item to create
     * @param productImage the product image to upload
     * @return the created catalog item
     */
    CatalogItemDto create(CatalogItemDto item, MultipartFile productImage) throws IOException;

    /**
     * Updates an existing catalog item.
     *
     * @param item         the catalog item to update
     * @param productImage the product image to upload
     * @throws IOException if an I/O error occurs during file upload
     */
    void update(CatalogItemDto item, MultipartFile productImage) throws IOException;

    /**
     * Deletes a catalog item by its ID.
     *
     * @param id the ID of the catalog item to delete
     */
    void deleteById(Long id);


    /**
     * Flushes the entity manager.
     */
    void flush();


}