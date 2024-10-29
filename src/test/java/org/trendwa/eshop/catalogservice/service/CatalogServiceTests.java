package org.trendwa.eshop.catalogservice.service;

import org.hibernate.exception.ConstraintViolationException;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.TestContextManager;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.trendwa.eshop.catalogservice.AppTestConfiguration;
import org.trendwa.eshop.catalogservice.dto.CatalogBrandDto;
import org.trendwa.eshop.catalogservice.dto.CatalogItemDto;
import org.trendwa.eshop.catalogservice.dto.CatalogTypeDto;
import org.trendwa.eshop.catalogservice.exception.CatalogItemNotFoundException;
import org.trendwa.eshop.catalogservice.util.AppTestUtils;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.trendwa.eshop.catalogservice.util.MockingUtils.createMockMultipartFile;

@SpringBootTest
@Import(AppTestConfiguration.class)
@TestPropertySource(locations = "classpath:application-test.properties")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Transactional(propagation = Propagation.REQUIRES_NEW)
class CatalogServiceTests {

    @Autowired
    private CatalogService catalogService;

    @Autowired
    private ImageStorageService imageStorageService;

    @BeforeEach
    void setUp() throws IOException {
        when(imageStorageService.upload(any(MultipartFile.class))).thenAnswer(invocation -> {
            MultipartFile file = invocation.getArgument(0);
            return "http://testcdndomain.com/" + file.getOriginalFilename();
        });
    }

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
    @DisplayName("Should return all items with pagination and sorting")
    void shouldReturnAllItemsWithPaginationAndSorting() {
        List<CatalogItemDto> response = catalogService.getAll(PageRequest.of(0, 5, Sort.by("name").ascending()));
        assertEquals(5, response.size());
        assertTrue(response.get(0).getName().compareTo(response.get(1).getName()) <= 0);
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
    void shouldAddItem() throws IOException {
        MultipartFile image = createMockMultipartFile("test.jpg");
        assertNotNull(catalogService.save(generateCatalogItemDto(null), image).getId());
    }

    @Test
    @DisplayName("Should throw exception if item's picture file name is not unique")
    void shouldThrowExceptionIfItemPictureFileNameIsNotUnique() throws IOException {
        CatalogItemDto itemToSave = generateCatalogItemDto(null);
        MultipartFile file = createMockMultipartFile("test.jpg");

        catalogService.save(itemToSave, file);
        DataIntegrityViolationException exception = assertThrows(DataIntegrityViolationException.class, () -> catalogService.save(itemToSave, file));
        assertEquals(ConstraintViolationException.ConstraintKind.UNIQUE, ((ConstraintViolationException) exception.getCause()).getKind());
    }

    @Test
    @DisplayName("Should throw exception if item's picture URI is not unique")
    void shouldThrowExceptionIfItemPictureUriIsNotUnique() throws IOException {
        CatalogItemDto itemToSave = generateCatalogItemDto(null);
        MultipartFile file = createMockMultipartFile("test.jpg");

        catalogService.save(itemToSave, file);
        DataIntegrityViolationException exception = assertThrows(DataIntegrityViolationException.class, () -> catalogService.save(itemToSave, file));
        assertEquals(ConstraintViolationException.ConstraintKind.UNIQUE, ((ConstraintViolationException) exception.getCause()).getKind());
    }

    @Test
    @DisplayName("Should update item")
    void shouldUpdateItem() throws IOException {
        CatalogItemDto savedItem = catalogService.save(generateCatalogItemDto(null), createMockMultipartFile("some.jpg"));
        MultipartFile file = createMockMultipartFile("test.jpg");
        catalogService.save(generateCatalogItemDto(savedItem.getId()), file);
        CatalogItemDto updatedItem = catalogService.getItemById(savedItem.getId());
        assertEquals("http://testcdndomain.com/test.jpg", updatedItem.getPictureUri());
    }

    @Test
    @DisplayName("Should throw exception if updated item's picture file name is not unique")
    void shouldThrowExceptionIfUpdatedItemPictureFileNameIsNotUnique() throws IOException {
        MultipartFile image = createMockMultipartFile("test.jpg");
        MultipartFile image2 = createMockMultipartFile("test2.jpg");
        CatalogItemDto savedItem = catalogService.save(generateCatalogItemDto(null), image);
        catalogService.save(generateCatalogItemDto(null), image2);

        DataIntegrityViolationException exception = assertThrows(DataIntegrityViolationException.class, () -> {
            catalogService.save(generateCatalogItemDto(savedItem.getId()), image2);
            catalogService.flush();
        });
        assertEquals(ConstraintViolationException.ConstraintKind.UNIQUE, ((ConstraintViolationException) exception.getCause()).getKind());
    }

    @Test
    @DisplayName("Should throw exception if updated item's picture URI is not unique")
    void shouldThrowExceptionIfUpdatedItemPictureUriIsNotUnique() throws IOException {

        MultipartFile testImage1 = createMockMultipartFile("test.jpg");
        MultipartFile testImage2 = createMockMultipartFile("test2.jpg");

        CatalogItemDto savedItem = catalogService.save(generateCatalogItemDto(null), testImage1);
        catalogService.save(generateCatalogItemDto(null), testImage2);

        DataIntegrityViolationException exception = assertThrows(DataIntegrityViolationException.class, () -> {
            catalogService.save(generateCatalogItemDto(savedItem.getId()), testImage2);
            catalogService.flush();
        });
        assertEquals(ConstraintViolationException.ConstraintKind.UNIQUE, ((ConstraintViolationException) exception.getCause()).getKind());
    }

    @Test
    @DisplayName("Should remove item")
    void shouldRemoveItem() throws IOException {
        CatalogItemDto savedItem = catalogService.save(generateCatalogItemDto(null), createMockMultipartFile("test.jpg"));
        catalogService.deleteById(savedItem.getId());
        assertThrows(CatalogItemNotFoundException.class, () -> catalogService.getItemById(savedItem.getId()));
    }

    private CatalogItemDto generateCatalogItemDto(Long id) {
        return new CatalogItemDto(
                id,
                "Test Item",
                "Test Description",
                100.0,
                null,
                null,
                new CatalogTypeDto(1L),
                new CatalogBrandDto(1L),
                10,
                5,
                20,
                false);
    }


}