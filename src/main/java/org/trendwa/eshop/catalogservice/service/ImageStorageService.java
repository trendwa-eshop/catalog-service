package org.trendwa.eshop.catalogservice.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface ImageStorageService {

    /**
     * Uploads a multipart file to the storage service.
     *
     * @param image the image to upload
     * @return the public URL of the uploaded image
     * @throws IOException if an I/O error occurs during the upload process
     */
    String upload(MultipartFile image) throws IOException;

}