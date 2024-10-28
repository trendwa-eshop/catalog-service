package org.trendwa.eshop.catalogservice.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.*;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;
import org.trendwa.eshop.catalogservice.config.OpenApiExamples;
import org.trendwa.eshop.catalogservice.dto.*;
import org.trendwa.eshop.catalogservice.service.CatalogService;

import java.io.IOException;
import java.util.List;

@RestController
@Tag(name = "Catalog Controller", description = "Operations related to catalog")
@RequestMapping(produces = {MediaType.APPLICATION_JSON_VALUE})
@RequiredArgsConstructor
@Slf4j
public class CatalogController {

    private final CatalogService catalogService;

    @GetMapping("/types")
    @Operation(summary = "Get all catalog types", description = "Retrieve all catalog types with pagination")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved list"),

    })
    public ResponseEntity<List<CatalogTypeDto>> getAllTypes(@ParameterObject Pageable pageable) {
        List<CatalogTypeDto> types = catalogService.getTypes(pageable);
        return new ResponseEntity<>(types, HttpStatus.OK);
    }


    @GetMapping("/items")
    @Operation(summary = "Get all items", description = "Retrieve all catalog items with pagination")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved list"),

    })
    public ResponseEntity<List<CatalogItemDto>> getAllItems(@ParameterObject Pageable pageable) {
        List<CatalogItemDto> items = catalogService.getAll(pageable);
        return new ResponseEntity<>(items, HttpStatus.OK);
    }

    @GetMapping("/items/{id}")
    @Operation(summary = "Get item by ID", description = "Retrieve a catalog item by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved item", useReturnTypeSchema = true),
            @ApiResponse(responseCode = "404", description = "Item not found", content = @Content(schema = @Schema(implementation = ErrorInfo.class))),
    })
    public ResponseEntity<CatalogItemDto> getItemById(@PathVariable Long id) {
        CatalogItemDto item = catalogService.getItemById(id);
        return new ResponseEntity<>(item, HttpStatus.OK);
    }

    @GetMapping("/brands")
    @Operation(summary = "Get all catalog brands", description = "Retrieve all catalog brands with pagination")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved list"),
    })
    public ResponseEntity<List<CatalogBrandDto>> getAllBrands(@ParameterObject Pageable pageable) {
        List<CatalogBrandDto> brands = catalogService.getBrands(pageable);
        return new ResponseEntity<>(brands, HttpStatus.OK);
    }

    @GetMapping("/items/by")
    @Operation(summary = "Get items by IDs", description = "Retrieve catalog items by their IDs with pagination")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved items"),
    })
    public ResponseEntity<List<CatalogItemDto>> getItemsByIds(@RequestParam List<Long> ids) {
        List<CatalogItemDto> items = catalogService.getItemsByIds(ids);
        return new ResponseEntity<>(items, HttpStatus.OK);
    }

    @GetMapping("/items/by/{name}")
    @Operation(summary = "Get items by name", description = "Retrieve catalog items containing the specified name with pagination")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved items"),
    })
    public ResponseEntity<List<CatalogItemDto>> getItemsByNameContaining(@PathVariable String name, @ParameterObject Pageable pageable) {
        List<CatalogItemDto> items = catalogService.getItemsByNameContaining(name, pageable);
        return new ResponseEntity<>(items, HttpStatus.OK);
    }

    @GetMapping("/items/type/{type}/brand/{brand}")
    @Operation(summary = "Get items by brand and type ", description = "Retrieve catalog items by brand and type with pagination")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved items"),
    })
    public ResponseEntity<List<CatalogItemDto>> getItemsByBrandAndType(@PathVariable String type, @PathVariable String brand, @ParameterObject Pageable pageable) {
        List<CatalogItemDto> items = catalogService.getItemsByBrandAndType(brand, type, pageable);
        return new ResponseEntity<>(items, HttpStatus.OK);
    }

    @GetMapping("/items/type/all/brand/{brand}")
    @Operation(summary = "Get items by brand", description = "Retrieve catalog items by brand with pagination")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved items"),
    })
    public ResponseEntity<List<CatalogItemDto>> getItemsByBrand(@PathVariable String brand, @ParameterObject Pageable pageable) {
        List<CatalogItemDto> items = catalogService.getItemsByBrand(String.valueOf(brand), pageable);
        return new ResponseEntity<>(items, HttpStatus.OK);
    }

    @PutMapping(value = "/items",
            consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    @Operation(summary = "Update item", description = "Update an existing catalog item")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Successfully updated item"),
    })
    public ResponseEntity<Void> updateItem(@ModelAttribute @io.swagger.v3.oas.annotations.parameters.RequestBody(content = @Content(
            mediaType = MediaType.MULTIPART_FORM_DATA_VALUE,
            schemaProperties =
                    {
                            @SchemaProperty(name = "item", schema = @Schema(type = "object", example = OpenApiExamples.UPDATE_CATALOG_ITEM_BODY)),
                            @SchemaProperty(name = "image", schema = @Schema(type = "string", format = "binary"))
                    }
    )) CatalogItemForm form) throws IOException {
        catalogService.save(form.toDto(), form.getImage());
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping(
            value = "/items",
            consumes = {MediaType.MULTIPART_FORM_DATA_VALUE}
    )
    @Operation(summary = "Create item", description = "Create a new catalog item"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Successfully created item"),
    })
    @Transactional
    public ResponseEntity<CatalogItemDto> createItem(
            @ModelAttribute @io.swagger.v3.oas.annotations.parameters.RequestBody(content = @Content(
                    mediaType = MediaType.MULTIPART_FORM_DATA_VALUE,
                    schemaProperties =
                            {
                                    @SchemaProperty(name = "item", schema = @Schema(type = "object", example = OpenApiExamples.CREATE_CATALOG_ITEM_BODY)),
                                    @SchemaProperty(name = "image", schema = @Schema(type = "string", format = "binary"))
                            }
            )) CatalogItemForm form, HttpServletRequest request) throws IOException {
        CatalogItemDto createdItem = catalogService.save(form.toDto(), form.getImage());
        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromPath(request.getContextPath() + "/items/{id}");
        UriComponents uriComponents = uriBuilder.buildAndExpand(createdItem.getId());

        return ResponseEntity.created(uriComponents.toUri()).body(createdItem);
    }


    @DeleteMapping("/items/{id}")
    @Operation(summary = "Delete item by ID", description = "Delete a catalog item by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Successfully deleted item"),
            @ApiResponse(responseCode = "404", description = "Item not found", content = @Content(schema = @Schema(implementation = ErrorInfo.class)))
    })
    public ResponseEntity<Void> deleteItemById(@PathVariable Long id) {
        catalogService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}