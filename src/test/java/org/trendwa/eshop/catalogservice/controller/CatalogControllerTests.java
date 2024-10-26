package org.trendwa.eshop.catalogservice.controller;

import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.MultipartBodyBuilder;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.TestContextManager;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.MultiValueMap;
import org.springframework.web.multipart.MultipartFile;
import org.trendwa.eshop.catalogservice.AppTestConfiguration;
import org.trendwa.eshop.catalogservice.dto.CatalogBrandDto;
import org.trendwa.eshop.catalogservice.dto.CatalogItemDto;
import org.trendwa.eshop.catalogservice.dto.CatalogTypeDto;
import org.trendwa.eshop.catalogservice.service.FileStorageService;
import org.trendwa.eshop.catalogservice.util.AppTestUtils;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.trendwa.eshop.catalogservice.util.MockingUtils.createMockMultipartFile;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Import(AppTestConfiguration.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Transactional
@TestPropertySource(locations = "classpath:application-test.properties")
@DisplayName("Catalog Item Controller Tests")
class CatalogControllerTests {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private FileStorageService fileStorageService;

    private final TestContextManager testContextManager = new TestContextManager(getClass());

    @BeforeEach
    void beforeEach() throws Exception {
        testContextManager.prepareTestInstance(this);
        AppTestUtils.insertSimpleData(testContextManager.getTestContext());
    }

    @BeforeAll
    @AfterEach
    void afterEachAndBeforeAll() throws Exception {
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
                .toList();
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
    @DisplayName("Should return 404 Not Found for non-existing item")
    void shouldReturnNotFoundForNonExistingItem() {
        ResponseEntity<String> response = restTemplate.getForEntity("/items/999999999", String.class);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());

        DocumentContext jsonContext = JsonPath.parse(response.getBody());
        assertEquals("Catalog item not found with id: 999999999", jsonContext.read("$.message"));
        assertEquals("The requested item was not found, please check the item id", jsonContext.read("$.details"));
        assertEquals("CatalogItemNotFoundException", jsonContext.read("$.exceptionType"));
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
    @DisplayName("Should return items by brand and type with pagination")
    void shouldReturnItemsByBrandAndTypeId() {
        String type = "shoes";
        String brand = "adidas";
        ResponseEntity<String> response = restTemplate.getForEntity("/items/type/" + type + "/brand/" + brand + "?page=0&size=10", String.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());

        DocumentContext jsonContext = JsonPath.parse(response.getBody());
        List<String> types = jsonContext.read("$[*].catalogType.name");
        List<String> brands = jsonContext.read("$[*].catalogBrand.name");

        assertTrue(types.stream().allMatch(t -> t.equalsIgnoreCase(type)));
        assertTrue(brands.stream().allMatch(b -> b.equalsIgnoreCase(brand)));

    }

    @Test
    @DisplayName("Should return items by brand with pagination")
    void shouldReturnItemsByBrandId() {
        String brand = "nike";
        ResponseEntity<String> response = restTemplate.getForEntity("/items/type/all/brand/" + brand + "?page=0&size=10", String.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());

        DocumentContext jsonContext = JsonPath.parse(response.getBody());
        List<String> brands = jsonContext.read("$[*].catalogBrand.name");
        assertTrue(brands.stream().allMatch(b -> b.equalsIgnoreCase(brand)));

    }

    @Test
    @DisplayName("Should update item")
    void shouldUpdateItem() throws IOException {
        CatalogItemDto itemToUpdate = new CatalogItemDto(
                1L,
                "Updated Item",
                "Updated Description",
                200.0,
                null,
                null,
                new CatalogTypeDto(2L),
                new CatalogBrandDto(2L),
                20,
                10,
                30,
                true
        );

        MockMultipartFile image = new MockMultipartFile("image", "test.jpg", "image/jpg", "test image content".getBytes());

        CatalogItemDto expectedItem = new CatalogItemDto(
                1L,
                "Updated Item",
                "Updated Description",
                200.0,
                "test.jpg",
                "http://testcdndomain.com/test.jpg",
                new CatalogTypeDto(2L, "Type 2"),
                new CatalogBrandDto(2L, "Brand 2"),
                20,
                10,
                30,
                true
        );

        when(fileStorageService.upload(any(MultipartFile.class))).thenReturn("http://testcdndomain.com/test.jpg");
        MultipartBodyBuilder builder = new MultipartBodyBuilder();
        builder.part("item", itemToUpdate);
        builder.part("image", image.getResource());

        HttpEntity<MultiValueMap<String, HttpEntity<?>>> requestEntity = new HttpEntity<>(builder.build());
        ResponseEntity<Void> response = restTemplate.exchange("/items", HttpMethod.PUT, requestEntity, Void.class);
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        ResponseEntity<CatalogItemDto> updatedItemResponse = restTemplate.getForEntity("/items/1", CatalogItemDto.class);
        assertEquals(HttpStatus.OK, updatedItemResponse.getStatusCode());
        assertNotNull(updatedItemResponse.getBody());
        assertUpdatedItem(updatedItemResponse.getBody(), expectedItem);
    }

    @Test
    @DisplayName("Should create item")
    void shouldCreateItem() throws IOException {
        CatalogItemDto newItem = new CatalogItemDto(
                null,
                "New Item",
                "New Description",
                100.0,
                null,
                null,
                new CatalogTypeDto(1L, "Type"),
                new CatalogBrandDto(1L, "Brand"),
                10,
                5,
                20,
                false
        );

        CatalogItemDto expectedItem = new CatalogItemDto(
                11L,
                "New Item",
                "New Description",
                100.0,
                "test.jpg",
                "http://testcdndomain.com/test.jpg",
                new CatalogTypeDto(1L, "Type"),
                new CatalogBrandDto(1L, "Brand"),
                10,
                5,
                20,
                false
        );

        when(fileStorageService.upload(any(MultipartFile.class))).thenReturn("http://testcdndomain.com/test.jpg");

        MultipartFile image = createMockMultipartFile("test.jpg");

        MultipartBodyBuilder builder = new MultipartBodyBuilder();
        builder.part("item", newItem);
        builder.part("image", image.getResource());

        HttpEntity<MultiValueMap<String, HttpEntity<?>>> requestEntity = new HttpEntity<>(builder.build());
        ResponseEntity<CatalogItemDto> response = restTemplate.postForEntity("/items", requestEntity, CatalogItemDto.class);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertCreatedItem(response.getBody(), expectedItem);
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


    @ParameterizedTest
    @CsvSource({
            "0, 1",
            "1, 1",
            "0, 2",
    })
    @DisplayName("Should return all catalog brands with pagination")
    void shouldReturnAllCatalogBrands(int pageNumber, int pageSize) {
        ResponseEntity<String> response = restTemplate.getForEntity("/brands?page=" + pageNumber + "&size=" + pageSize, String.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());

        DocumentContext jsonContext = JsonPath.parse(response.getBody());
        int brandCount = jsonContext.read("$.length()");
        assertEquals(pageSize, brandCount);
    }

    @ParameterizedTest
    @CsvSource({
            "0, 1",
            "1, 1",
            "0, 2",
    })
    @DisplayName("Should return all catalog types with pagination")
    void shouldReturnAllCatalogTypes(int pageNumber, int pageSize) {
        ResponseEntity<String> response = restTemplate.getForEntity("/types?page=" + pageNumber + "&size=" + pageSize, String.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());

        DocumentContext jsonContext = JsonPath.parse(response.getBody());
        int typeCount = jsonContext.read("$.length()");
        assertEquals(pageSize, typeCount);
    }

    private void assertUpdatedItem(CatalogItemDto actual, CatalogItemDto expected) {
        assertEquals(expected.getName(), actual.getName());
        assertEquals(expected.getDescription(), actual.getDescription());
        assertEquals(expected.getPrice(), actual.getPrice());
        assertEquals(expected.getPictureFileName(), actual.getPictureFileName());
        assertEquals(expected.getPictureUri(), actual.getPictureUri());
        assertEquals(expected.getCatalogType().getId(), actual.getCatalogType().getId());
        assertEquals(expected.getCatalogBrand().getId(), actual.getCatalogBrand().getId());
        assertEquals(expected.getAvailableStock(), actual.getAvailableStock());
        assertEquals(expected.getRestockThreshold(), actual.getRestockThreshold());
        assertEquals(expected.getMaxStockThreshold(), actual.getMaxStockThreshold());
        assertEquals(expected.isOnReorder(), actual.isOnReorder());
    }

    private void assertCreatedItem(CatalogItemDto actual, CatalogItemDto expected) {
        assertNotNull(actual.getId());
        assertUpdatedItem(actual, expected);
    }

}