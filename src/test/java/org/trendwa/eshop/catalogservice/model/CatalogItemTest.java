package org.trendwa.eshop.catalogservice.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;
import org.trendwa.eshop.catalogservice.dummydata.CatalogItemDummyData;

import java.io.IOException;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@JsonTest
class CatalogItemTest {

    @Autowired
    private JacksonTester<CatalogItem> json;

    @Autowired
    private JacksonTester<CatalogItem[]> jsonList;


    private List<CatalogItem> catalogItems;

    @BeforeEach
    void setUp() {
        catalogItems = CatalogItemDummyData.items;
    }

    @Test
    void catalogItemSerializationTest() throws IOException {
        CatalogItem catalogItem = catalogItems.getFirst();
        assertThat(json.write(catalogItem)).isStrictlyEqualToJson("catalog-item-single.json");
    }

    @Test
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
        assertThat(json.parse(expected))
                .isEqualTo(catalogItems.getFirst());
    }


}
