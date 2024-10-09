package org.trendwa.eshop.catalogservice.service;

import org.hibernate.exception.ConstraintViolationException;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.TestContextManager;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.trendwa.eshop.catalogservice.TestcontainersConfiguration;
import org.trendwa.eshop.catalogservice.dto.CatalogBrandDto;
import org.trendwa.eshop.catalogservice.dto.CatalogItemDto;
import org.trendwa.eshop.catalogservice.dto.CatalogTypeDto;
import org.trendwa.eshop.catalogservice.exception.CatalogItemNotFoundException;
import org.trendwa.eshop.catalogservice.util.AppTestUtils;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Import(TestcontainersConfiguration.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Transactional(propagation = Propagation.REQUIRES_NEW)
class CatalogServiceTests {

    @Autowired
    private CatalogService catalogService;

    private final TestContextManager testContextManager = new TestContextManager(getClass());

    @BeforeAll
    void beforeAll() throws Exception {
        testContextManager.prepareTestInstance(this);
        AppTestUtils.resetDatabase(testContextManager.getTestContext());
        AppTestUtils.insertSimpleData(testContextManager.getTestContext());
    }

    @AfterAll
    void afterAll() throws Exception {
        testContextManager.prepareTestInstance(this);
        AppTestUtils.resetDatabase(testContextManager.getTestContext());
    }

    @Test
    @DisplayName("Should return all items with pagination")
    void shouldReturnAllItems() {
        List<CatalogItemDto> response = catalogService.getAll(PageRequest.of(0, 5));
        assertEquals(5, response.size());
    }

    @Test
    @DisplayName("Should return item by ID")
    void shouldReturnItemById() {
        assertNotNull(catalogService.getItemById(1L));
    }

    @Test
    @DisplayName("Should return items by IDs")
    void shouldReturnItemsByIds() {
        assertEquals(3, catalogService.getItemsByIds(List.of(1L, 2L, 3L)).size());
    }

    @Test
    @DisplayName("Should return items by name containing")
    void shouldReturnItemsByNameContaining() {
        assertEquals(3, catalogService.getItemsByNameContaining("Air", null).size());
    }

    @Test
    @DisplayName("Should return items by brand")
    void shouldReturnItemsByBrand() {
        assertEquals(5, catalogService.getItemsByBrand("Nike", null).size());
    }

    @Test
    @DisplayName("Should return items by type")
    void shouldReturnItemsByType() {
        assertEquals(10, catalogService.getItemsByType("Shoes", null).size());
    }

    @Test
    @DisplayName("Should return items by brand and type")
    void shouldReturnItemsByBrandAndType() {
        assertEquals(5, catalogService.getItemsByBrandAndType("Nike", "Shoes", null).size());
    }

    @Test
    @DisplayName("Should add item")
    void shouldAddItem() {
        assertNotNull(catalogService.save(generateCatalogItemDto(null)).getId());
    }

    @Test
    @DisplayName("Should throw exception if item's picture file name is not unique")
    void shouldThrowExceptionIfItemPictureFileNameIsNotUnique() {
        CatalogItemDto itemToSave = generateCatalogItemDto(null, "test.jpg", null);
        catalogService.save(itemToSave);
        DataIntegrityViolationException exception = assertThrows(DataIntegrityViolationException.class, () -> catalogService.save(itemToSave));
        assertEquals(ConstraintViolationException.ConstraintKind.UNIQUE, ((ConstraintViolationException) exception.getCause()).getKind());
    }

    @Test
    @DisplayName("Should throw exception if item's picture URI is not unique")
    void shouldThrowExceptionIfItemPictureUriIsNotUnique() {
        CatalogItemDto itemToSave = generateCatalogItemDto(null, null, "uri/test.jpg");
        catalogService.save(itemToSave);
        DataIntegrityViolationException exception = assertThrows(DataIntegrityViolationException.class, () -> catalogService.save(itemToSave));
        assertEquals(ConstraintViolationException.ConstraintKind.UNIQUE, ((ConstraintViolationException) exception.getCause()).getKind());
    }

    @Test
    @DisplayName("Should update item")
    void shouldUpdateItem() {
        CatalogItemDto savedItem = catalogService.save(generateCatalogItemDto(null));
        CatalogItemDto updatedItem = catalogService.save(generateCatalogItemDto(savedItem.getId(), "test.jpg", null));
        assertEquals("test.jpg", updatedItem.getPictureFileName());
    }

    @Test
    @DisplayName("Should throw exception if updated item's picture file name is not unique")
    void shouldThrowExceptionIfUpdatedItemPictureFileNameIsNotUnique() {
        CatalogItemDto savedItem = catalogService.save(generateCatalogItemDto(null, "test.jpg", null));
        catalogService.save(generateCatalogItemDto(null, "test2.jpg", null));
        DataIntegrityViolationException exception = assertThrows(DataIntegrityViolationException.class, () -> {
            catalogService.save(generateCatalogItemDto(savedItem.getId(), "test2.jpg", null));
            catalogService.flush();
        });
        assertEquals(ConstraintViolationException.ConstraintKind.UNIQUE, ((ConstraintViolationException) exception.getCause()).getKind());
    }

    @Test
    @DisplayName("Should throw exception if updated item's picture URI is not unique")
    void shouldThrowExceptionIfUpdatedItemPictureUriIsNotUnique() {
        CatalogItemDto savedItem = catalogService.save(generateCatalogItemDto(null, null, "uri/test.jpg"));
        catalogService.save(generateCatalogItemDto(null, null, "uri/test2.jpg"));
        DataIntegrityViolationException exception = assertThrows(DataIntegrityViolationException.class, () -> {
            catalogService.save(generateCatalogItemDto(savedItem.getId(), null, "uri/test2.jpg"));
            catalogService.flush();
        });
        assertEquals(ConstraintViolationException.ConstraintKind.UNIQUE, ((ConstraintViolationException) exception.getCause()).getKind());
    }

    @Test
    @DisplayName("Should remove item")
    void shouldRemoveItem() {
        CatalogItemDto savedItem = catalogService.save(generateCatalogItemDto(null));
        catalogService.deleteById(savedItem.getId());
        assertThrows(CatalogItemNotFoundException.class, () -> catalogService.getItemById(savedItem.getId()));
    }

    private CatalogItemDto generateCatalogItemDto(Long id, String pictureFileName, String pictureUri) {
        return new CatalogItemDto(
                id,
                "Test Item",
                "Test Description",
                100.0,
                pictureFileName,
                pictureUri,
                new CatalogTypeDto(1L),
                new CatalogBrandDto(1L),
                10,
                5,
                20,
                false);
    }

    private CatalogItemDto generateCatalogItemDto(Long id) {
        return generateCatalogItemDto(id, null, null);
    }
}