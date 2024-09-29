package org.trendwa.eshop.catalogservice.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.init.ScriptUtils;
import org.springframework.test.context.TestContext;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

@Slf4j
public class AppTestUtils {

    /**
     * Resets the database to a known state by executing a SQL script.
     * The script is expected to be located at src/test/resources/db/scripts/reset_database.sql.
     *
     * @param testContext the TestContext providing access to the application context and test class information
     */
    public static void resetDatabase(TestContext testContext) {
        DataSource dataSource = testContext.getApplicationContext().getBean(DataSource.class);
        ClassPathResource resource = new ClassPathResource("db/scripts/reset_database.sql");
        try (Connection connection = dataSource.getConnection()) {
            ScriptUtils.executeSqlScript(connection, resource);
            log.info("Database reset successfully after test class {}", testContext.getTestClass());
        } catch (SQLException e) {
            throw new RuntimeException("Failed to reset database after test class: ", e);
        }
    }

    /**
     * Inserts simple data into the database by executing a SQL script.
     * The script is expected to be located at src/test/resources/db/scripts/insert_simple_datas.sql.
     *
     * @param testContext the TestContext providing access to the application context and test class information
     */
    public static void insertSimpleData(TestContext testContext) {
        DataSource dataSource = testContext.getApplicationContext().getBean(DataSource.class);
        ClassPathResource resource = new ClassPathResource("db/scripts/insert_simple_datas.sql");
        try (Connection connection = dataSource.getConnection()) {
            ScriptUtils.executeSqlScript(connection, resource);
            log.info("Inserted simple data successfully before test class {}", testContext.getTestClass());
        } catch (SQLException e) {
            throw new RuntimeException("Failed to insert simple data before test class: ", e);
        }
    }
}
