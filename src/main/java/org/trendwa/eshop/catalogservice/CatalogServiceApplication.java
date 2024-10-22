package org.trendwa.eshop.catalogservice;

import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.trendwa.eshop.catalogservice.repository.ApplicationJpaRepositoryImpl;

@SpringBootApplication
@OpenAPIDefinition(
        info = @Info(
                title = "Trendwa E-Shop Catalog Service API",
                version = "${version}",
                description = "API documentation for the Trendwa E-Shop Catalog Service",
                termsOfService = "https://trendwa.com.tr/terms/",
                contact = @Contact(
                        name = "Support Team",
                        email = "support@trendwa.com.tr",
                        url = "https://trendwa.com.tr/contact"
                )
        ),
        servers = {
                @Server(url = "http://trendwa.local/catalog/api/v1", description = "Local server, if you are running the application locally"),
                @Server(url = "http://trendwa.dev.com.tr/catalog/api/v1", description = "Development server"),
                @Server(url = "https://trendwa.com.tr/catalog/api/v1", description = "Production server"),
        },
        externalDocs = @ExternalDocumentation(
                description = "Find out more about the E-Shop Catalog Service",
                url = "https://trendwa.com.tr/docs"
        )
)
@EnableJpaRepositories(repositoryBaseClass = ApplicationJpaRepositoryImpl.class)
public class CatalogServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(CatalogServiceApplication.class, args);
    }

}
