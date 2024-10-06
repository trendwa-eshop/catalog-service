package org.trendwa.eshop.catalogservice;

import org.springframework.boot.SpringApplication;
import org.springframework.test.context.TestPropertySource;

public class TestCatalogServiceApplication {

    public static void main(String[] args) {
        SpringApplication.from(CatalogServiceApplication::main).with(TestcontainersConfiguration.class).run(args);
    }

}
