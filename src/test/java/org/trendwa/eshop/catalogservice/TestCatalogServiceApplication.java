package org.trendwa.eshop.catalogservice;

import org.springframework.boot.SpringApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.trendwa.eshop.catalogservice.repository.ApplicationJpaRepositoryImpl;


@EnableJpaRepositories(repositoryBaseClass = ApplicationJpaRepositoryImpl.class)
public class TestCatalogServiceApplication {

    public static void main(String[] args) {
        SpringApplication.from(CatalogServiceApplication::main).with(AppTestConfiguration.class).run(args);
    }

}
