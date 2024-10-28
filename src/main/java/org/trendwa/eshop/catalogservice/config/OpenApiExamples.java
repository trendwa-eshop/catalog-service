package org.trendwa.eshop.catalogservice.config;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class OpenApiExamples {
    public static final String CREATE_CATALOG_ITEM_BODY = """
            {
              "name": "Apple iPhone 12",
              "description": "Apple iPhone 12 with 5G",
              "price": 799.99,
              "catalogType": 1,
              "catalogBrand": 1,
              "availableStock": 100,
              "restockThreshold": 10,
              "maxStockThreshold": 1000,
              "onReorder": false
            }
            """;


    public static final String UPDATE_CATALOG_ITEM_BODY = """
            {
              "id": 1,
              "name": "Apple iPhone 12",
              "description": "Apple iPhone 12 with 5G",
              "price": 799.99,
              "pictureFileName": "iphone12.jpg",
              "catalogType": 1,
              "catalogBrand": 1,
              "availableStock": 100,
              "restockThreshold": 10,
              "maxStockThreshold": 1000,
              "onReorder": false
            }
            """;
}
