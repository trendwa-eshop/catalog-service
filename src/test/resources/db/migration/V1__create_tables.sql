-- src/main/resources/db/migration/V1__create_tables.sql
CREATE TABLE IF NOT EXISTS catalog_brand
(
    ID         BIGINT AUTO_INCREMENT PRIMARY KEY,
    CREATED_AT TIMESTAMP    NOT NULL,
    CREATED_BY VARCHAR(255),
    UPDATED_AT TIMESTAMP,
    UPDATED_BY VARCHAR(255),
    name      VARCHAR(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS catalog_type
(
    ID         BIGINT AUTO_INCREMENT PRIMARY KEY,
    CREATED_AT TIMESTAMP    NOT NULL,
    CREATED_BY VARCHAR(255),
    UPDATED_AT TIMESTAMP,
    UPDATED_BY VARCHAR(255),
    name       VARCHAR(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS catalog_item
(
    ID                  BIGINT AUTO_INCREMENT PRIMARY KEY,
    CREATED_AT          TIMESTAMP    NOT NULL,
    CREATED_BY          VARCHAR(255),
    UPDATED_AT          TIMESTAMP,
    UPDATED_BY          VARCHAR(255),
    name                VARCHAR(255) NOT NULL,
    description         TEXT,
    price               DOUBLE,
    picture_file_name   VARCHAR(255) UNIQUE ,
    picture_uri         VARCHAR(255) UNIQUE ,
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