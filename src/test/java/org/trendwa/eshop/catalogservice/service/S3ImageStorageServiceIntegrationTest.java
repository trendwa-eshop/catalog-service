package org.trendwa.eshop.catalogservice.service;

import jakarta.transaction.Transactional;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.TestPropertySource;
import org.springframework.web.multipart.MultipartFile;
import org.trendwa.eshop.catalogservice.AppIntegrationTestConfiguration;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Import({AppIntegrationTestConfiguration.class})
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Transactional
@TestPropertySource(locations = "classpath:application-test.properties")
@Disabled
class S3ImageStorageServiceIntegrationTest {

    @Autowired
    private S3ImageStorageService s3FileStorageService;

    @Test
    void shouldUploadImage() throws Exception {

        MultipartFile file = new MockMultipartFile("test-file.jpg", "test-file.jpg", "image/jpg", "test data".getBytes());
        String resultUrl = s3FileStorageService.upload(file);
        assertTrue(StringUtils.isNoneEmpty(resultUrl));
    }

    @Test
    void shouldThrowAnErrorWhenFileMimeTypeNotAllowed() {
        MultipartFile file = new MockMultipartFile("test-file.php", "test-file.php", "application/php", "test data".getBytes());
        assertThrows(IOException.class, () -> s3FileStorageService.upload(file));
    }
}