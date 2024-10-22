package org.trendwa.eshop.catalogservice.service;

import jakarta.transaction.Transactional;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.TestPropertySource;
import org.trendwa.eshop.catalogservice.AppTestConfiguration;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Import(AppTestConfiguration.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Transactional
@TestPropertySource(locations = "classpath:application-test.properties")
@Disabled
class S3FileStorageServiceIntegrationTest {

    @Autowired
    private S3FileStorageService s3FileStorageService;

    @Test
    void testUpload() throws Exception {
        Path tempFile = Files.createTempFile("test-file", ".txt");
        File file = tempFile.toFile();
        String resultUrl = s3FileStorageService.upload(file);
        assertTrue(StringUtils.isNoneEmpty(resultUrl));
    }
}