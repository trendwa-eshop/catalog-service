package org.trendwa.eshop.catalogservice.tools;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.time.Instant;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PictureFileKeyGenerator {

    public static String generate(MultipartFile file) {
        return "catalog-item-" + Instant.now().toEpochMilli() + "-" + file.getOriginalFilename();
    }

}
