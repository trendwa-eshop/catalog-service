package org.trendwa.eshop.catalogservice;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.TestPropertySource;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.utility.DockerImageName;
import org.trendwa.eshop.catalogservice.service.ImageStorageService;

import java.util.List;

@TestConfiguration(proxyBeanMethods = false)
@TestPropertySource(locations = "classpath:application-test.properties")
public class AppTestConfiguration {

    @Container
    private static final MySQLContainer<?> MYSQL_CONTAINER;

    @MockBean
    private ImageStorageService imageStorageService;

    static {
        MYSQL_CONTAINER = new MySQLContainer<>(DockerImageName.parse("mysql:9.0.1"))
                .withDatabaseName("catalog")
                .withUsername("root")
                .withPassword("root")
                        .withReuse(true);

        MYSQL_CONTAINER.setPortBindings(List.of("61636:3306"));
        org.testcontainers.utility.TestcontainersConfiguration.getInstance()
                .updateUserConfig("testcontainers.reuse.enable", "true");
        MYSQL_CONTAINER.start();

    }

    @Bean
    @ServiceConnection
    MySQLContainer<?> mysqlContainer() {
        return MYSQL_CONTAINER;
    }
}