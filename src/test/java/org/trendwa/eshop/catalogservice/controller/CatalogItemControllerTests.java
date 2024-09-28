package org.trendwa.eshop.catalogservice.controller;

import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestContextManager;
import org.springframework.test.context.TestPropertySource;
import org.trendwa.eshop.catalogservice.TestcontainersConfiguration;
import org.trendwa.eshop.catalogservice.dto.CatalogBrandDto;
import org.trendwa.eshop.catalogservice.dto.CatalogItemDto;
import org.trendwa.eshop.catalogservice.dto.CatalogTypeDto;
import org.trendwa.eshop.catalogservice.util.AppTestUtils;

import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Import(TestcontainersConfiguration.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestPropertySource(locations = "classpath:application-test.properties")
@Transactional
@DisplayName("Catalog Item Controller Tests")

class CatalogItemControllerTests {

    @Autowired
    private TestRestTemplate restTemplate;

    private final TestContextManager testContextManager = new TestContextManager(getClass());

    @BeforeEach
    void beforeEach() throws Exception {
        testContextManager.prepareTestInstance(this);
        AppTestUtils.insertSimpleData(testContextManager.getTestContext());
    }

    @AfterEach
    void afterEach() throws Exception {
        testContextManager.prepareTestInstance(this);
        AppTestUtils.resetDatabase(testContextManager.getTestContext());
    }

    @Test
    @DisplayName("Should return all items with pagination")
    void shouldReturnAllItems() {
        ResponseEntity<String> response = restTemplate.getForEntity("/items?page=0&size=10", String.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());

        DocumentContext jsonContext = JsonPath.parse(response.getBody());
        int itemCount = jsonContext.read("$.length()");
        assertEquals(10, itemCount);
    }

    @Test
    @DisplayName("Should return items by IDs")
    void shouldReturnItemsByIds() {
        List<Long> requestedIds = List.of(1L, 2L, 3L);

        String idsParam = requestedIds.stream()
                .map(String::valueOf)
                .collect(Collectors.joining(","));

        ResponseEntity<String> response = restTemplate.getForEntity("/items/by?ids=" + idsParam, String.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());


        DocumentContext jsonContext = JsonPath.parse(response.getBody());
        int itemCount = jsonContext.read("$.length()");

        assertEquals(requestedIds.size(), itemCount);

        List<Integer> numberList = jsonContext.read("$[*].id", List.class);
        List<Long> returnedIds = numberList.stream()
                .map(Number::longValue)
                .collect(Collectors.toList());
        assertEquals(requestedIds, returnedIds);
    }

    @Test
    @DisplayName("Should return item by ID")
    void shouldReturnItemById() {
        ResponseEntity<CatalogItemDto> response = restTemplate.getForEntity("/items/1", CatalogItemDto.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    @Test
    @DisplayName("Should return items by name containing with pagination")
    void shouldReturnItemsByNameContaining() {
        ResponseEntity<String> response = restTemplate.getForEntity("/items/by/Air?page=0&size=10", String.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());

        DocumentContext jsonContext = JsonPath.parse(response.getBody());
        List<String> names = jsonContext.read("$[*].name");
        assertTrue(names.stream().allMatch(name -> name.contains("Air")));
    }

    @Test
    @DisplayName("Should return items by brand and type ID with pagination")
    void shouldReturnItemsByBrandAndTypeId() {
        ResponseEntity<String> response = restTemplate.getForEntity("/items/type/1/brand/1?page=0&size=10", String.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());

        DocumentContext jsonContext = JsonPath.parse(response.getBody());
        List<Integer> typeIds = jsonContext.read("$[*].catalogType.id");
        List<Integer> brandIds = jsonContext.read("$[*].catalogBrand.id");

        assertTrue(typeIds.stream().allMatch(id -> id == 1));
        assertTrue(brandIds.stream().allMatch(id -> id == 1));
    }

    @Test
    @DisplayName("Should return items by brand ID with pagination")
    void shouldReturnItemsByBrandId() {
        ResponseEntity<String> response = restTemplate.getForEntity("/items/type/all/brand/1?page=0&size=10", String.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());

        DocumentContext jsonContext = JsonPath.parse(response.getBody());
        List<Integer> brandIds = jsonContext.read("$[*].catalogBrand.id");

        assertTrue(brandIds.stream().allMatch(id -> id == 1));
    }

    @Test
    @DisplayName("Should update item")
    void shouldUpdateItem() {
        CatalogItemDto itemToUpdate = new CatalogItemDto(
                1L,
                "Updated Item",
                "Updated Description",
                200.0,
                "updated.jpg",
                "uri/updated.jpg",
                new CatalogTypeDto(2L),
                new CatalogBrandDto(2L),
                20,
                10,
                30,
                true
        );
        restTemplate.put("/items", itemToUpdate);
        ResponseEntity<CatalogItemDto> response = restTemplate.getForEntity("/items/1", CatalogItemDto.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertUpdatedItem(response.getBody(), itemToUpdate);
    }

    @Test
    @DisplayName("Should create item")
    void shouldCreateItem() {
        CatalogItemDto newItem = new CatalogItemDto(
                null,
                "New Item",
                "New Description",
                100.0,
                "new.jpg",
                "uri/new.jpg",
                new CatalogTypeDto(1L, "Type"),
                new CatalogBrandDto(1L, "Brand"),
                10,
                5,
                20,
                false
        );
        ResponseEntity<CatalogItemDto> response = restTemplate.postForEntity("/items", newItem, CatalogItemDto.class);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertCreatedItem(response.getBody(), newItem);
    }

    @Test
    @DisplayName("Should delete item by ID")
    void shouldDeleteItemById() {
        restTemplate.delete("/items/1");

        ResponseEntity<String> response = restTemplate.getForEntity("/items/1", String.class);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());

        ResponseEntity<String> allItemsResponse = restTemplate.getForEntity("/items", String.class);
        assertEquals(HttpStatus.OK, allItemsResponse.getStatusCode());
        assertNotNull(allItemsResponse.getBody());

        DocumentContext jsonContext = JsonPath.parse(allItemsResponse.getBody());
        List<Long> ids = jsonContext.read("$[*].id");
        assertFalse(ids.contains(1L));
    }

    private void assertUpdatedItem(CatalogItemDto actual, CatalogItemDto expected) {
        assertEquals(expected.name(), actual.name());
        assertEquals(expected.description(), actual.description());
        assertEquals(expected.price(), actual.price());
        assertEquals(expected.pictureFileName(), actual.pictureFileName());
        assertEquals(expected.pictureUri(), actual.pictureUri());
        assertEquals(expected.catalogType().id(), actual.catalogType().id());
        assertEquals(expected.catalogBrand().id(), actual.catalogBrand().id());
        assertEquals(expected.availableStock(), actual.availableStock());
        assertEquals(expected.restockThreshold(), actual.restockThreshold());
        assertEquals(expected.maxStockThreshold(), actual.maxStockThreshold());
        assertEquals(expected.onReorder(), actual.onReorder());
    }

    private void assertCreatedItem(CatalogItemDto actual, CatalogItemDto expected) {
        assertUpdatedItem(actual, expected);
    }

}