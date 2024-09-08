package org.trendwa.eshop.catalogservice.dummydata;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.trendwa.eshop.catalogservice.model.CatalogBrand;
import org.trendwa.eshop.catalogservice.model.CatalogItem;
import org.trendwa.eshop.catalogservice.model.CatalogType;

import java.time.LocalDateTime;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CatalogItemDummyData {
    public static List<CatalogItem> items;

    private static final LocalDateTime FIXED_TIMESTAMP = LocalDateTime.of(2023, 10, 5, 12, 0);
    static {
        CatalogType electronicsType = new CatalogType();
        electronicsType.setId(1L);
        electronicsType.setCreatedAt(FIXED_TIMESTAMP);
        electronicsType.setCreatedBy("system");
        electronicsType.setUpdatedAt(FIXED_TIMESTAMP);
        electronicsType.setUpdatedBy("system");
        electronicsType.setType("Electronics");

        CatalogType clothingType = new CatalogType();
        clothingType.setId(2L);
        clothingType.setCreatedAt(FIXED_TIMESTAMP);
        clothingType.setCreatedBy("system");
        clothingType.setUpdatedAt(FIXED_TIMESTAMP);
        clothingType.setUpdatedBy("system");
        clothingType.setType("Clothing");

        CatalogBrand sonyBrand = new CatalogBrand();
        sonyBrand.setId(1L);
        sonyBrand.setCreatedAt(FIXED_TIMESTAMP);
        sonyBrand.setCreatedBy("system");
        sonyBrand.setUpdatedAt(FIXED_TIMESTAMP);
        sonyBrand.setUpdatedBy("system");
        sonyBrand.setBrand("Sony");

        CatalogBrand nikeBrand = new CatalogBrand();
        nikeBrand.setId(2L);
        nikeBrand.setCreatedAt(FIXED_TIMESTAMP);
        nikeBrand.setCreatedBy("system");
        nikeBrand.setUpdatedAt(FIXED_TIMESTAMP);
        nikeBrand.setUpdatedBy("system");
        nikeBrand.setBrand("Nike");

        CatalogItem item1 = new CatalogItem();
        item1.setId(1L);
        item1.setCreatedAt(FIXED_TIMESTAMP);
        item1.setCreatedBy("system");
        item1.setUpdatedAt(FIXED_TIMESTAMP);
        item1.setUpdatedBy("system");
        item1.setName("Sony TV");
        item1.setDescription("55 inch 4K TV");
        item1.setPrice(799.99);
        item1.setPictureFileName("sony_tv.jpg");
        item1.setPictureUri("/images/sony_tv.jpg");
        item1.setCatalogType(electronicsType);
        item1.setCatalogBrand(sonyBrand);
        item1.setAvailableStock(50);
        item1.setRestockThreshold(10);
        item1.setMaxStockThreshold(100);
        item1.setOnReorder(false);

        CatalogItem item2 = new CatalogItem();
        item2.setId(2L);
        item2.setCreatedAt(FIXED_TIMESTAMP);
        item2.setCreatedBy("system");
        item2.setUpdatedAt(FIXED_TIMESTAMP);
        item2.setUpdatedBy("system");
        item2.setName("Nike Shoes");
        item2.setDescription("Running shoes");
        item2.setPrice(120.00);
        item2.setPictureFileName("nike_shoes.jpg");
        item2.setPictureUri("/images/nike_shoes.jpg");
        item2.setCatalogType(clothingType);
        item2.setCatalogBrand(nikeBrand);
        item2.setAvailableStock(200);
        item2.setRestockThreshold(20);
        item2.setMaxStockThreshold(300);
        item2.setOnReorder(false);

        CatalogItem item3 = new CatalogItem();
        item3.setId(3L);
        item3.setCreatedAt(FIXED_TIMESTAMP);
        item3.setCreatedBy("system");
        item3.setUpdatedAt(FIXED_TIMESTAMP);
        item3.setUpdatedBy("system");
        item3.setName("Sony Headphones");
        item3.setDescription("Noise-cancelling headphones");
        item3.setPrice(199.99);
        item3.setPictureFileName("sony_headphones.jpg");
        item3.setPictureUri("/images/sony_headphones.jpg");
        item3.setCatalogType(electronicsType);
        item3.setCatalogBrand(sonyBrand);
        item3.setAvailableStock(150);
        item3.setRestockThreshold(15);
        item3.setMaxStockThreshold(250);
        item3.setOnReorder(false);

        CatalogItem item4 = new CatalogItem();
        item4.setId(4L);
        item4.setCreatedAt(FIXED_TIMESTAMP);
        item4.setCreatedBy("system");
        item4.setUpdatedAt(FIXED_TIMESTAMP);
        item4.setUpdatedBy("system");
        item4.setName("Nike T-Shirt");
        item4.setDescription("Sports T-Shirt");
        item4.setPrice(35.00);
        item4.setPictureFileName("nike_tshirt.jpg");
        item4.setPictureUri("/images/nike_tshirt.jpg");
        item4.setCatalogType(clothingType);
        item4.setCatalogBrand(nikeBrand);
        item4.setAvailableStock(300);
        item4.setRestockThreshold(30);
        item4.setMaxStockThreshold(500);
        item4.setOnReorder(false);

        CatalogItem item5 = new CatalogItem();
        item5.setId(5L);
        item5.setCreatedAt(FIXED_TIMESTAMP);
        item5.setCreatedBy("system");
        item5.setUpdatedAt(FIXED_TIMESTAMP);
        item5.setUpdatedBy("system");
        item5.setName("Sony PlayStation 5");
        item5.setDescription("Next-gen gaming console");
        item5.setPrice(499.99);
        item5.setPictureFileName("sony_ps5.jpg");
        item5.setPictureUri("/images/sony_ps5.jpg");
        item5.setCatalogType(electronicsType);
        item5.setCatalogBrand(sonyBrand);
        item5.setAvailableStock(30);
        item5.setRestockThreshold(5);
        item5.setMaxStockThreshold(50);
        item5.setOnReorder(true);
        
        items = List.of(item1, item2, item3, item4, item5);
    }
}
