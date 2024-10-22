package org.trendwa.eshop.catalogservice;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.TestPropertySource;

@Import(AppTestConfiguration.class)
@SpringBootTest
@TestPropertySource(locations = "classpath:application-test.properties")
@Slf4j
class ContextLoadsTests {

    @Test
    void contextLoads() {
      log.info("Context loaded successfully!");
    }

}
