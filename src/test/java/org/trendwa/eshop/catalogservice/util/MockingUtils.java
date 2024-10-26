package org.trendwa.eshop.catalogservice.util;

import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

public class MockingUtils {

    public static  MultipartFile createMockMultipartFile(String filename) {
        return new MockMultipartFile("image", filename, "image/jpg", "test image content".getBytes());
    }
}
