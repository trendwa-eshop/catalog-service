package org.trendwa.eshop.catalogservice.model;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.trendwa.eshop.catalogservice.dummydata.CatalogItemDummyData;
import org.trendwa.eshop.catalogservice.exception.domain.CatalogDomainException;
import org.trendwa.eshop.catalogservice.repository.ApplicationJpaRepositoryImpl;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;


@DataJpaTest
@JsonTest
@DisplayName("Catalog Item Tests")
@EnableJpaRepositories(repositoryBaseClass = ApplicationJpaRepositoryImpl.class)
@TestClassOrder(ClassOrderer.OrderAnnotation.class)
@Disabled
class CatalogItemTest {

    @Autowired
    private JacksonTester<CatalogItem> json;

    @Autowired
    private JacksonTester<CatalogItem[]> jsonList;

    private CatalogItem[] catalogItems;

    @BeforeEach
    void setUp() {
        catalogItems = CatalogItemDummyData.items;
    }

    @Nested
    @DisplayName("Json serialization tests")
    @Order(1)
    class JsonSerializationTests {

        @Test
        @DisplayName("Test serialization of a single catalog item")
        void catalogItemSerializationTest() throws IOException {
            CatalogItem catalogItem = catalogItems[0];
            assertThat(json.write(catalogItem)).isStrictlyEqualToJson("catalog-item-single.json");
        }

        @Test
        @DisplayName("Test deserialization of a single catalog item")
        void catalogItemDeserializationTest() throws IOException {
            String expected = """
                    {
                      "id": 1,
                      "createdAt": "2023-10-05T12:00:00",
                      "createdBy": "system",
                      "updatedAt": "2023-10-05T12:00:00",
                      "updatedBy": "system",
                      "name": "Sony TV",
                      "description": "55 inch 4K TV",
                      "price": 799.99,
                      "pictureFileName": "sony_tv.jpg",
                      "pictureUri": "/images/sony_tv.jpg",
                      "catalogType": {
                        "id": 1,
                        "createdAt": "2023-10-05T12:00:00",
                        "createdBy": "system",
                        "updatedAt": "2023-10-05T12:00:00",
                        "updatedBy": "system",
                        "name": "Electronics"
                      },
                      "catalogBrand": {
                        "id": 1,
                        "createdAt": "2023-10-05T12:00:00",
                        "createdBy": "system",
                        "updatedAt": "2023-10-05T12:00:00",
                        "updatedBy": "system",
                        "name": "Sony"
                      },
                      "availableStock": 50,
                      "restockThreshold": 10,
                      "maxStockThreshold": 100,
                      "onReorder": false
                    }
                    """;
            assertThat(json.parseObject(expected)).usingRecursiveComparison()
                    .isEqualTo(catalogItems[0]);
        }

        @Test
        @DisplayName("Test serialization of a list of catalog items")
        void catalogItemListSerializationTest() throws IOException {
            assertThat(jsonList.write(catalogItems)).isStrictlyEqualToJson("catalog-item-list.json");
        }

        @Test
        @DisplayName("Test deserialization of a list of catalog items")
        void catalogItemListDeserializationTest() throws IOException {
            String expected = """
                    [
                      {
                        "id": 1,
                        "createdAt": "2023-10-05T12:00:00",
                        "createdBy": "system",
                        "updatedAt": "2023-10-05T12:00:00",
                        "updatedBy": "system",
                        "name": "Sony TV",
                        "description": "55 inch 4K TV",
                        "price": 799.99,
                        "pictureFileName": "sony_tv.jpg",
                        "pictureUri": "/images/sony_tv.jpg",
                        "catalogType": {
                          "id": 1,
                          "createdAt": "2023-10-05T12:00:00",
                          "createdBy": "system",
                          "updatedAt": "2023-10-05T12:00:00",
                          "updatedBy": "system",
                          "name": "Electronics"
                        },
                        "catalogBrand": {
                          "id": 1,
                          "createdAt": "2023-10-05T12:00:00",
                          "createdBy": "system",
                          "updatedAt": "2023-10-05T12:00:00",
                          "updatedBy": "system",
                          "name": "Sony"
                        },
                        "availableStock": 50,
                        "restockThreshold": 10,
                        "maxStockThreshold": 100,
                        "onReorder": false
                      },
                      {
                        "id": 2,
                        "createdAt": "2023-10-05T12:00:00",
                        "createdBy": "system",
                        "updatedAt": "2023-10-05T12:00:00",
                        "updatedBy": "system",
                        "name": "Nike Shoes",
                        "description": "Running shoes",
                        "price": 120.00,
                        "pictureFileName": "nike_shoes.jpg",
                        "pictureUri": "/images/nike_shoes.jpg",
                        "catalogType": {
                          "id": 2,
                          "createdAt": "2023-10-05T12:00:00",
                          "createdBy": "system",
                          "updatedAt": "2023-10-05T12:00:00",
                          "updatedBy": "system",
                          "name": "Clothing"
                        },
                        "catalogBrand": {
                          "id": 2,
                          "createdAt": "2023-10-05T12:00:00",
                          "createdBy": "system",
                          "updatedAt": "2023-10-05T12:00:00",
                          "updatedBy": "system",
                          "name": "Nike"
                        },
                        "availableStock": 200,
                        "restockThreshold": 20,
                        "maxStockThreshold": 300,
                        "onReorder": false
                      },
                      {
                        "id": 3,
                        "createdAt": "2023-10-05T12:00:00",
                        "createdBy": "system",
                        "updatedAt": "2023-10-05T12:00:00",
                        "updatedBy": "system",
                        "name": "Sony Headphones",
                        "description": "Noise-cancelling headphones",
                        "price": 199.99,
                        "pictureFileName": "sony_headphones.jpg",
                        "pictureUri": "/images/sony_headphones.jpg",
                        "catalogType": {
                          "id": 1,
                          "createdAt": "2023-10-05T12:00:00",
                          "createdBy": "system",
                          "updatedAt": "2023-10-05T12:00:00",
                          "updatedBy": "system",
                          "name": "Electronics"
                        },
                        "catalogBrand": {
                          "id": 1,
                          "createdAt": "2023-10-05T12:00:00",
                          "createdBy": "system",
                          "updatedAt": "2023-10-05T12:00:00",
                          "updatedBy": "system",
                          "name": "Sony"
                        },
                        "availableStock": 150,
                        "restockThreshold": 15,
                        "maxStockThreshold": 250,
                        "onReorder": false
                      },
                      {
                        "id": 4,
                        "createdAt": "2023-10-05T12:00:00",
                        "createdBy": "system",
                        "updatedAt": "2023-10-05T12:00:00",
                        "updatedBy": "system",
                        "name": "Nike T-Shirt",
                        "description": "Sports T-Shirt",
                        "price": 35.00,
                        "pictureFileName": "nike_tshirt.jpg",
                        "pictureUri": "/images/nike_tshirt.jpg",
                        "catalogType": {
                          "id": 2,
                          "createdAt": "2023-10-05T12:00:00",
                          "createdBy": "system",
                          "updatedAt": "2023-10-05T12:00:00",
                          "updatedBy": "system",
                          "name": "Clothing"
                        },
                        "catalogBrand": {
                          "id": 2,
                          "createdAt": "2023-10-05T12:00:00",
                          "createdBy": "system",
                          "updatedAt": "2023-10-05T12:00:00",
                          "updatedBy": "system",
                          "name": "Nike"
                        },
                        "availableStock": 300,
                        "restockThreshold": 30,
                        "maxStockThreshold": 500,
                        "onReorder": false
                      },
                      {
                        "id": 5,
                        "createdAt": "2023-10-05T12:00:00",
                        "createdBy": "system",
                        "updatedAt": "2023-10-05T12:00:00",
                        "updatedBy": "system",
                        "name": "Sony PlayStation 5",
                        "description": "Next-gen gaming console",
                        "price": 499.99,
                        "pictureFileName": "sony_ps5.jpg",
                        "pictureUri": "/images/sony_ps5.jpg",
                        "catalogType": {
                          "id": 1,
                          "createdAt": "2023-10-05T12:00:00",
                          "createdBy": "system",
                          "updatedAt": "2023-10-05T12:00:00",
                          "updatedBy": "system",
                          "name": "Electronics"
                        },
                        "catalogBrand": {
                          "id": 1,
                          "createdAt": "2023-10-05T12:00:00",
                          "createdBy": "system",
                          "updatedAt": "2023-10-05T12:00:00",
                          "updatedBy": "system",
                          "name": "Sony"
                        },
                        "availableStock": 30,
                        "restockThreshold": 5,
                        "maxStockThreshold": 50,
                        "onReorder": true
                      }
                    ]
                    """;

            assertThat(jsonList.parseObject(expected)).usingRecursiveComparison().isEqualTo(catalogItems);
        }
    }


    @Nested
    @DisplayName("Function tests")
    @Order(2)
    class FunctionTests {


        /*
         * Tests the decrementing of item quantity in inventory.
         *
         * This test verifies that:
         * 1. When there is sufficient stock, the quantity removed matches the desired quantity.
         * 2. When stock is insufficient, the method removes the available stock and returns the
         *    amount removed.
         * 3. A negative quantity request is invalid and should throw an CatalogDomainException.
         * 4. When there is no available stock, the method should throw an CatalogDomainException.
         */
        @Nested
        @DisplayName("Remove Stock Tests")
        class RemoveStockTests {

            @Test
            @DisplayName("When there is sufficient stock, the quantity removed should match the desired quantity")
            void testRemoveStockSufficientStock() {
                CatalogItem catalogItem = createCatalogItem();
                int removedStock = catalogItem.removeStock(10);
                assertThat(removedStock).isEqualTo(10);
                assertThat(catalogItem.getAvailableStock()).isEqualTo(40);
            }

            @Test
            @DisplayName("When stock is insufficient, the method should remove the available stock and return the amount removed")
            void testRemoveStockInsufficientStock() {
                CatalogItem catalogItem = createCatalogItem();
                int removedStock = catalogItem.removeStock(60);
                assertThat(removedStock).isEqualTo(50);
                assertThat(catalogItem.getAvailableStock()).isZero();
            }

            @Test
            @DisplayName("A negative quantity request is invalid and should throw an IllegalArgumentException")
            void testRemoveStockNegativeQuantity() {
                CatalogItem catalogItem = createCatalogItem();
                CatalogDomainException exception = assertThrows(CatalogDomainException.class, () ->
                        catalogItem.removeStock(-10)
                );
                assertThat(exception.getMessage()).isEqualTo("Item units desired must be greater than 0");
            }

            @Test
            @DisplayName("When there is no available stock, the method should throw an CatalogDomainException")
            void testRemoveStockNoStock() {
                CatalogItem catalogItem = createCatalogItem();
                catalogItem.removeStock(50);
                CatalogDomainException exception = assertThrows(CatalogDomainException.class, () ->
                        catalogItem.removeStock(10)
                );
                assertThat(exception.getMessage()).isEqualTo("No available stock for item " + catalogItem.getName());
            }
        }


        /*
         * Tests the incrementing of item quantity in inventory.
         *
         * These test verifies that:
         * 1. When the quantity to add is positive, the stock is increased by the desired quantity.
         * 2. When the quantity to add is negative, the method throws an CatalogDomainException.
         * 3. When the quantity to add is zero, the method does not change the stock.
         * 4. When the quantity to add is greater than the max stock threshold, the method adds the stock up to the max threshold.
         * 5. When max stock threshold is reached, the method does not add any stock.
         * 6. When the stock is added, the onReorder flag is set to false.
         * 7. The method returns the amount of stock added.
         */
        @Nested
        @DisplayName("Add Stock Tests")
        class AddStockTests {


            @Test
            @DisplayName("When the quantity to add is positive, the stock should be increased by the desired quantity")
            void testAddStockPositive() {
                CatalogItem catalogItem = createCatalogItem();
                int addedStock = catalogItem.addStock(10);
                assertThat(addedStock).isEqualTo(10);
                assertThat(catalogItem.getAvailableStock()).isEqualTo(60);
            }

            @Test
            @DisplayName("When the quantity to add is negative, the method should throw an CatalogDomainException")
            void testAddStockNegative() {
                CatalogItem catalogItem = createCatalogItem();
                CatalogDomainException exception = assertThrows(CatalogDomainException.class, () ->
                        catalogItem.addStock(-10)
                );
                assertThat(exception.getMessage()).isEqualTo("Item units desired must be greater than 0");
            }

            @Test
            @DisplayName("When the quantity to add is zero, the method should not change the stock")
            void testAddStockZero() {
                CatalogItem catalogItem = createCatalogItem();
                int addedStock = catalogItem.addStock(0);
                assertThat(addedStock).isZero();
                assertThat(catalogItem.getAvailableStock()).isEqualTo(50);
            }

            @Test
            @DisplayName("When the quantity to add is greater than the max stock threshold, the method should add the stock up to the max threshold")
            void testAddStockMaxThreshold() {
                CatalogItem catalogItem = createCatalogItem();
                int addedStock = catalogItem.addStock(100);
                assertThat(addedStock).isEqualTo(50);
                assertThat(catalogItem.getAvailableStock()).isEqualTo(100);
            }

            @Test
            @DisplayName("When max stock threshold is reached, the method should not add any stock")
            void testAddStockMaxThresholdReached() {
                CatalogItem catalogItem = createCatalogItem();
                catalogItem.addStock(100);
                int addedStock = catalogItem.addStock(10);
                assertThat(addedStock).isZero();
                assertThat(catalogItem.getAvailableStock()).isEqualTo(100);
            }

            @Test
            @DisplayName("When the stock is added, the onReorder flag should be set to false")
            void testAddStockOnReorder() {
                CatalogItem catalogItem = createCatalogItem();
                catalogItem.setOnReorder(true);
                catalogItem.addStock(10);
                assertThat(catalogItem.isOnReorder()).isFalse();
            }
        }
    }

    private CatalogItem createCatalogItem() {
        CatalogItem catalogItem = new CatalogItem();
        catalogItem.setId(1L);
        catalogItem.setName("Sony TV");
        catalogItem.setDescription("55 inch 4K TV");
        catalogItem.setPrice(799.99);
        catalogItem.setPictureFileName("sony_tv.jpg");
        catalogItem.setPictureUri("/images/sony_tv.jpg");
        catalogItem.setAvailableStock(50);
        catalogItem.setRestockThreshold(10);
        catalogItem.setMaxStockThreshold(100);
        catalogItem.setOnReorder(false);
        return catalogItem;
    }

}
