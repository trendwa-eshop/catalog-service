package org.trendwa.eshop.catalogservice.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;
import org.trendwa.eshop.catalogservice.dto.CatalogItemDto;
import org.trendwa.eshop.catalogservice.service.CatalogService;

import java.util.List;

@RestController
@RequestMapping("/items")
@Tag(name = "Catalog Item Controller", description = "Operations related to catalog items")
@RequiredArgsConstructor
@Slf4j
public class CatalogItemController {

    private final CatalogService catalogService;

    @GetMapping
    @Operation(summary = "Get all items", description = "Retrieve all catalog items with pagination")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved list"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<List<CatalogItemDto>> getAllItems(Pageable pageable) {
        log.info("GET /items called ");
        List<CatalogItemDto> items = catalogService.getAll(pageable);
        log.debug("GET /items response: {}", items);
        return new ResponseEntity<>(items, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get item by ID", description = "Retrieve a catalog item by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved item"),
            @ApiResponse(responseCode = "404", description = "Item not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<CatalogItemDto> getItemById(@PathVariable Long id) {
        log.info("GET /items/{} called ", id);
        CatalogItemDto item = catalogService.getItemById(id);
        log.debug("GET /items/{} response: {}", id, item);
        return new ResponseEntity<>(item, HttpStatus.OK);
    }

    @GetMapping("/by")
    @Operation(summary = "Get items by IDs", description = "Retrieve catalog items by their IDs with pagination")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved items"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<List<CatalogItemDto>> getItemsByIds(@RequestParam List<Long> ids) {
        log.info("GET /items/by called with ids: {}", ids.toString());
        List<CatalogItemDto> items = catalogService.getItemsByIds(ids);
        log.debug("GET /items/by response: {}", items);
        return new ResponseEntity<>(items, HttpStatus.OK);
    }

    @GetMapping("/by/{name}")
    @Operation(summary = "Get items by name", description = "Retrieve catalog items containing the specified name with pagination")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved items"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<List<CatalogItemDto>> getItemsByNameContaining(@PathVariable String name, Pageable pageable) {
        log.info("GET /items/by/{} called ", name);
        List<CatalogItemDto> items = catalogService.getItemsByNameContaining(name, pageable);
        log.debug("GET /items/by/{} response: {}", name, items);
        return new ResponseEntity<>(items, HttpStatus.OK);
    }

    @GetMapping("/type/{typeId}/brand/{brandId}")
    @Operation(summary = "Get items by brand and type ID", description = "Retrieve catalog items by brand and type ID with pagination")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved items"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<List<CatalogItemDto>> getItemsByBrandAndTypeId(@PathVariable Long typeId, @PathVariable Long brandId, Pageable pageable) {
        log.info("GET /items/type/{}/brand/{} called ", typeId, brandId);
        List<CatalogItemDto> items = catalogService.getItemsByBrandAndType(String.valueOf(brandId), String.valueOf(typeId), pageable);
        log.debug("GET /items/type/{}/brand/{} response: {}", typeId, brandId, items);
        return new ResponseEntity<>(items, HttpStatus.OK);
    }

    @GetMapping("/type/all/brand/{brandId}")
    @Operation(summary = "Get items by brand ID", description = "Retrieve catalog items by brand ID with pagination")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved items"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<List<CatalogItemDto>> getItemsByBrandId(@PathVariable Long brandId, Pageable pageable) {
        log.info("GET /items/type/all/brand/{} called ", brandId);
        List<CatalogItemDto> items = catalogService.getItemsByBrand(String.valueOf(brandId), pageable);
        log.debug("GET /items/type/all/brand/{} response: {}", brandId, items);
        return new ResponseEntity<>(items, HttpStatus.OK);
    }

    @PutMapping
    @Operation(summary = "Update item", description = "Update an existing catalog item")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully updated item"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<Void> updateItem(@Valid @RequestBody CatalogItemDto item) {
        log.info("PUT /items called with item: {}", item);
        catalogService.save(item);
        log.debug("PUT /items response: {}", item);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping
    @Operation(summary = "Create item", description = "Create a new catalog item")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Successfully created item"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<CatalogItemDto> createItem(@Valid @RequestBody CatalogItemDto item) {
        log.info("POST /items called with item: {}", item);
        CatalogItemDto createdItem = catalogService.save(item);
        log.debug("POST /items response: {}", createdItem);

        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromPath("/items/{id}");
        UriComponents uriComponents = uriBuilder.buildAndExpand(createdItem.getId());

        return ResponseEntity.created(uriComponents.toUri()).body(createdItem);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete item by ID", description = "Delete a catalog item by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Successfully deleted item"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<Void> deleteItemById(@PathVariable Long id) {
        log.info("DELETE /items/{} called ", id);
        catalogService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}