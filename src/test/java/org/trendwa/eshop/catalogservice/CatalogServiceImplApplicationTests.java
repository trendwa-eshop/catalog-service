package org.trendwa.eshop.catalogservice;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.TestPropertySource;

@Import(TestcontainersConfiguration.class)
@TestPropertySource(locations = "classpath:test.properties")
@SpringBootTest
class CatalogServiceImplApplicationTests {

    @Test
    void contextLoads() {
        System.out.println("Context loaded successfully");
    }

}
