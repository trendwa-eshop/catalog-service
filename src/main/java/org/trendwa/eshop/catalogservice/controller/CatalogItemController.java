package org.trendwa.eshop.catalogservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.trendwa.eshop.catalogservice.dto.CatalogItemDto;
import org.trendwa.eshop.catalogservice.service.CatalogService;

import java.util.List;

@RestController
@RequestMapping("/items")
public class CatalogItemController {

    @Autowired
    private CatalogService catalogService;

    @GetMapping
    public ResponseEntity<List<CatalogItemDto>> getAllItems(Pageable pageable) {
        List<CatalogItemDto> items = catalogService.getAll(pageable);
        return new ResponseEntity<>(items, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CatalogItemDto> getItemById(@PathVariable Long id) {
        CatalogItemDto item = catalogService.getItemById(id);
        return new ResponseEntity<>(item, HttpStatus.OK);
    }

    @GetMapping("/by")
    public ResponseEntity<List<CatalogItemDto>> getItemsByIds(@RequestParam List<Long> ids, Pageable pageable) {
        List<CatalogItemDto> items = catalogService.getItemsByIds(ids);
        return new ResponseEntity<>(items, HttpStatus.OK);
    }

    @GetMapping("/by/{name}")
    public ResponseEntity<List<CatalogItemDto>> getItemsByNameContaining(@PathVariable String name, Pageable pageable) {
        List<CatalogItemDto> items = catalogService.getItemsByNameContaining(name, pageable);
        return new ResponseEntity<>(items, HttpStatus.OK);
    }

    @GetMapping("/type/{typeId}/brand/{brandId}")
    public ResponseEntity<List<CatalogItemDto>> getItemsByBrandAndTypeId(@PathVariable Long typeId, @PathVariable Long brandId, Pageable pageable) {
        List<CatalogItemDto> items = catalogService.getItemsByBrandAndType(String.valueOf(brandId), String.valueOf(typeId), pageable);
        return new ResponseEntity<>(items, HttpStatus.OK);
    }

    @GetMapping("/type/all/brand/{brandId}")
    public ResponseEntity<List<CatalogItemDto>> getItemsByBrandId(@PathVariable Long brandId, Pageable pageable) {
        List<CatalogItemDto> items = catalogService.getItemsByBrand(String.valueOf(brandId), pageable);
        return new ResponseEntity<>(items, HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<Void> updateItem(@RequestBody CatalogItemDto item) {
        catalogService.save(item);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<CatalogItemDto> createItem(@RequestBody CatalogItemDto item) {
        CatalogItemDto createdItem = catalogService.save(item);
        return new ResponseEntity<>(createdItem, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteItemById(@PathVariable Long id) {
        catalogService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}