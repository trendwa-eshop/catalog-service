package org.trendwa.eshop.catalogservice.service;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriComponentsBuilder;
import org.trendwa.eshop.catalogservice.tools.PictureFileKeyGenerator;
import software.amazon.awssdk.core.async.AsyncRequestBody;
import software.amazon.awssdk.transfer.s3.S3TransferManager;
import software.amazon.awssdk.transfer.s3.model.UploadRequest;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.Executors;


/**
 * Service for handling image storage operations with AWS S3.
 * <p>
 * This service uploads files to an S3 bucket and returns the public URL of the uploaded file.
 * </p>
 */
@Service
@RequiredArgsConstructor
public class S3ImageStorageService implements ImageStorageService {

    private final S3TransferManager transferManager;

    @Value("${aws.s3.bucket.name}")
    private String bucketName;

    @Value("${aws.cloudfront.base-url}")
    private String cloudFrontBaseUrl;

    @Value("${application.file-storage.allowed-mime-types}")
    private List<String> allowedMimeTypes;

    @Override
    public String upload(@NonNull MultipartFile image) throws IOException {
        validateImageType(image);
        String key = generateKey(image);
        UploadRequest uploadRequest = createUploadRequest(image, key);
        uploadToS3(uploadRequest);
        return buildFileUrl(key);
    }

    private void validateImageType(MultipartFile image) throws IOException {
        if (!allowedMimeTypes.contains(image.getContentType())) {
            throw new IOException("Image type (" + image.getContentType() + ") not allowed. Allowed types: "
                    + String.join(", ", allowedMimeTypes));
        }
    }

    private String generateKey(MultipartFile image) {
        return PictureFileKeyGenerator.generate(image);
    }

    private UploadRequest createUploadRequest(MultipartFile image, String key) throws IOException {
        return UploadRequest.builder()
                .putObjectRequest(b -> b.bucket(bucketName).key(key))
                .requestBody(AsyncRequestBody.fromInputStream(
                        image.getInputStream(),
                        image.getSize(),
                        Executors.newFixedThreadPool(3)
                ))
                .build();
    }

    private void uploadToS3(UploadRequest uploadRequest) {
        transferManager.upload(uploadRequest).completionFuture().join();
    }

    private String buildFileUrl(String key) {
        return UriComponentsBuilder.fromHttpUrl(cloudFrontBaseUrl)
                .path(key)
                .build().toUriString();
    }

}