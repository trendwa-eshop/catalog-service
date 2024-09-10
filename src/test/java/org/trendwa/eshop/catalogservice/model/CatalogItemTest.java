package org.trendwa.eshop.catalogservice.model;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;
import org.trendwa.eshop.catalogservice.dummydata.CatalogItemDummyData;
import org.trendwa.eshop.catalogservice.exception.domain.CatalogDomainException;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;


@JsonTest
@DisplayName("Catalog Item Tests")
@TestClassOrder(ClassOrderer.OrderAnnotation.class)
class CatalogItemTest {

    @Autowired
    private JacksonTester<CatalogItem> json;

    @Autowired
    private JacksonTester<CatalogItem[]> jsonList;

    private CatalogItem[] catalogItems;

    @BeforeEach
    void setUp() {
        catalogItems = CatalogItemDummyData.items.clone();
    }

    @Nested
    @DisplayName("Json serialization tests")
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
                        "type": "Electronics"
                      },
                      "catalogBrand": {
                        "id": 1,
                        "createdAt": "2023-10-05T12:00:00",
                        "createdBy": "system",
                        "updatedAt": "2023-10-05T12:00:00",
                        "updatedBy": "system",
                        "brand": "Sony"
                      },
                      "availableStock": 50,
                      "restockThreshold": 10,
                      "maxStockThreshold": 100,
                      "onReorder": false
                    }
                    """;
            assertThat(json.parseObject(expected))
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
                          "type": "Electronics"
                        },
                        "catalogBrand": {
                          "id": 1,
                          "createdAt": "2023-10-05T12:00:00",
                          "createdBy": "system",
                          "updatedAt": "2023-10-05T12:00:00",
                          "updatedBy": "system",
                          "brand": "Sony"
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
                          "type": "Clothing"
                        },
                        "catalogBrand": {
                          "id": 2,
                          "createdAt": "2023-10-05T12:00:00",
                          "createdBy": "system",
                          "updatedAt": "2023-10-05T12:00:00",
                          "updatedBy": "system",
                          "brand": "Nike"
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
                          "type": "Electronics"
                        },
                        "catalogBrand": {
                          "id": 1,
                          "createdAt": "2023-10-05T12:00:00",
                          "createdBy": "system",
                          "updatedAt": "2023-10-05T12:00:00",
                          "updatedBy": "system",
                          "brand": "Sony"
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
                          "type": "Clothing"
                        },
                        "catalogBrand": {
                          "id": 2,
                          "createdAt": "2023-10-05T12:00:00",
                          "createdBy": "system",
                          "updatedAt": "2023-10-05T12:00:00",
                          "updatedBy": "system",
                          "brand": "Nike"
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
                          "type": "Electronics"
                        },
                        "catalogBrand": {
                          "id": 1,
                          "createdAt": "2023-10-05T12:00:00",
                          "createdBy": "system",
                          "updatedAt": "2023-10-05T12:00:00",
                          "updatedBy": "system",
                          "brand": "Sony"
                        },
                        "availableStock": 30,
                        "restockThreshold": 5,
                        "maxStockThreshold": 50,
                        "onReorder": true
                      }
                    ]
                    """;

            assertThat(jsonList.parseObject(expected)).isEqualTo(catalogItems);
        }
    }


    @Nested
    @DisplayName("Function tests")
    class FunctionTests {

        /*
         * Tests the decrementing of item quantity in inventory.
         *
         * This test verifies that:
         * 1. When there is sufficient stock, the quantity removed matches the desired quantity.
         * 2. When stock is insufficient, the method removes the available stock and returns the
         *    amount removed.
         * 3. A negative quantity request is invalid and should throw an IllegalArgumentException.
         */

        @Test
        @DisplayName("Test Remove Stock")
        void testRemoveStock() {
            CatalogItem catalogItem = createCatalogItem();

            // Test negative quantity removal
            CatalogDomainException exception = assertThrows(CatalogDomainException.class, () -> {
                catalogItem.removeStock(-10);
            });
            assertThat(exception.getMessage()).isEqualTo("Item units desired must be greater than 0");

            // Test valid stock removal
            int removedStock = catalogItem.removeStock(10);
            assertThat(removedStock).isEqualTo(10);
            assertThat(catalogItem.getAvailableStock()).isEqualTo(40);

            // Test removal more than available stock
            removedStock = catalogItem.removeStock(50);
            assertThat(removedStock).isEqualTo(40);
            assertThat(catalogItem.getAvailableStock()).isZero();
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
