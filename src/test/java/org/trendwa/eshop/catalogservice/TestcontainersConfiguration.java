package org.trendwa.eshop.catalogservice;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.context.annotation.Bean;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.utility.DockerImageName;

@TestConfiguration(proxyBeanMethods = false)
public class TestcontainersConfiguration {

    @Bean
    @ServiceConnection
    MySQLContainer<?> mysqlContainer() {
        MySQLContainer<?> mySQLContainer = new MySQLContainer<>(DockerImageName.parse("mysql:9.0.1"))
                .withDatabaseName("catalog")
                .withUsername("root")
                .withPassword("root")
                .withInitScript("init.sql");
        mySQLContainer.start();
        return mySQLContainer;
    }


}
