package org.trendwa.eshop.catalogservice.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

public interface FileStorageService {

    /**
     * Uploads a file to the storage service.
     *
     * @param file the file to be uploaded
     * @return the public URL of the uploaded file
     */
    String upload(File file);


    /**
     * Uploads a multipart file to the storage service.
     *
     * @param file the file to be uploaded
     * @return the public URL of the uploaded file
     */
    String upload(MultipartFile file) throws IOException;

}