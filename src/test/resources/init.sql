CREATE TABLE catalog_brand
(
    ID         BIGINT AUTO_INCREMENT PRIMARY KEY,
    CREATED_AT TIMESTAMP    NOT NULL,
    CREATED_BY VARCHAR(255),
    UPDATED_AT TIMESTAMP,
    UPDATED_BY VARCHAR(255),
    name      VARCHAR(255) NOT NULL
);

CREATE TABLE catalog_type
(
    ID         BIGINT AUTO_INCREMENT PRIMARY KEY,
    CREATED_AT TIMESTAMP    NOT NULL,
    CREATED_BY VARCHAR(255),
    UPDATED_AT TIMESTAMP,
    UPDATED_BY VARCHAR(255),
    name       VARCHAR(255) NOT NULL
);

CREATE TABLE catalog_item
(
    ID                  BIGINT AUTO_INCREMENT PRIMARY KEY,
    CREATED_AT          TIMESTAMP    NOT NULL,
    CREATED_BY          VARCHAR(255),
    UPDATED_AT          TIMESTAMP,
    UPDATED_BY          VARCHAR(255),
    name                VARCHAR(255) NOT NULL,
    description         TEXT,
    price               DOUBLE,
    picture_file_name   VARCHAR(255) unique,
    picture_uri         VARCHAR(255) unique,
    catalog_type_id     BIGINT,
    catalog_brand_id    BIGINT,
    available_stock     INT,
    restock_threshold   INT,
    max_stock_threshold INT,
    on_reorder          BOOLEAN DEFAULT false,
    CONSTRAINT fk_catalog_type FOREIGN KEY (catalog_type_id) REFERENCES catalog_type (ID),
    CONSTRAINT fk_catalog_brand FOREIGN KEY (catalog_brand_id) REFERENCES catalog_brand (ID)
);

CREATE INDEX idx_catalog_item_name ON catalog_item (name);

-- Insert data into catalog_brand
INSERT INTO catalog_brand (CREATED_AT, CREATED_BY, UPDATED_AT, UPDATED_BY, name)
VALUES ('2023-01-01 10:00:00', 'admin', '2023-01-01 10:00:00', 'admin', 'Nike'),
       ('2023-01-01 10:00:00', 'admin', '2023-01-01 10:00:00', 'admin', 'Adidas');

-- Insert data into catalog_type
INSERT INTO catalog_type (CREATED_AT, CREATED_BY, UPDATED_AT, UPDATED_BY, name)
VALUES ('2023-01-01 10:00:00', 'admin', '2023-01-01 10:00:00', 'admin', 'Shoes'),
       ('2023-01-01 10:00:00', 'admin', '2023-01-01 10:00:00', 'admin', 'Apparel');

-- Insert data into catalog_item
INSERT INTO catalog_item (CREATED_AT, CREATED_BY, UPDATED_AT, UPDATED_BY, name, description, price, picture_file_name,
                           picture_uri, catalog_type_id, catalog_brand_id, available_stock, restock_threshold,
                           max_stock_threshold, on_reorder)
VALUES ('2023-01-01 10:00:00', 'admin', '2023-01-01 10:00:00', 'admin', 'Air Max 90', 'Classic Nike running shoes',
        120.00, 'airmax90.jpg', 'http://example.com/airmax90.jpg', 1, 1, 50, 5, 100, false),
       ('2023-01-01 10:00:00', 'admin', '2023-01-01 10:00:00', 'admin', 'Ultraboost',
        'High-performance Adidas running shoes', 180.00, 'ultraboost.jpg', 'http://example.com/ultraboost.jpg', 1, 2,
        30, 3, 80, true),
       ('2023-01-01 10:00:00', 'admin', '2023-01-01 10:00:00', 'admin', 'Air Jordan 1', 'Iconic basketball shoes',
        150.00, 'airjordan1.jpg', 'http://example.com/airjordan1.jpg', 1, 1, 40, 4, 90, false),
       ('2023-01-01 10:00:00', 'admin', '2023-01-01 10:00:00', 'admin', 'Yeezy Boost 350', 'Popular Adidas sneakers',
        220.00, 'yeezyboost350.jpg', 'http://example.com/yeezyboost350.jpg', 1, 2, 20, 2, 70, true),
       ('2023-01-01 10:00:00', 'admin', '2023-01-01 10:00:00', 'admin', 'Nike Air Force 1', 'Classic Nike sneakers',
        100.00, 'airforce1.jpg', 'http://example.com/airforce1.jpg', 1, 1, 60, 6, 110, false),
       ('2023-01-01 10:00:00', 'admin', '2023-01-01 10:00:00', 'admin', 'Adidas Superstar', 'Classic Adidas sneakers',
        85.00, 'superstar.jpg', 'http://example.com/superstar.jpg', 1, 2, 70, 7, 120, false),
       ('2023-01-01 10:00:00', 'admin', '2023-01-01 10:00:00', 'admin', 'Nike Blazer Mid',
        'Retro Nike basketball shoes', 110.00, 'blazermid.jpg', 'http://example.com/blazermid.jpg', 1, 1, 45, 4, 95,
        false),
       ('2023-01-01 10:00:00', 'admin', '2023-01-01 10:00:00', 'admin', 'Adidas NMD', 'Modern Adidas sneakers', 130.00,
        'nmd.jpg', 'http://example.com/nmd.jpg', 1, 2, 35, 3, 85, true),
       ('2023-01-01 10:00:00', 'admin', '2023-01-01 10:00:00', 'admin', 'Nike React Element 55',
        'Comfortable Nike running shoes', 140.00, 'reactelement55.jpg', 'http://example.com/reactelement55.jpg', 1, 1,
        55, 5, 105, false),
       ('2023-01-01 10:00:00', 'admin', '2023-01-01 10:00:00', 'admin', 'Adidas Gazelle', 'Classic Adidas trainers',
        95.00, 'gazelle.jpg', 'http://example.com/gazelle.jpg', 1, 2, 65, 6, 115, false);