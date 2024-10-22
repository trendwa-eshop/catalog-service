package org.trendwa.eshop.catalogservice.service.development;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.trendwa.eshop.catalogservice.service.FileStorageService;
import software.amazon.awssdk.core.async.AsyncRequestBody;
import software.amazon.awssdk.transfer.s3.S3TransferManager;
import software.amazon.awssdk.transfer.s3.model.UploadFileRequest;
import software.amazon.awssdk.transfer.s3.model.UploadRequest;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.Executors;


/**
 * Service for handling file storage operations with AWS S3.
 * <p>
 * This service uploads files to an S3 bucket and returns the public CDN URL of the uploaded file.
 * Uses http instead of https.
 * </p>
 */
@Service
@RequiredArgsConstructor
@Profile("dev")
public class S3FileStorageService implements FileStorageService {

    private final S3TransferManager transferManager;
    private final S3TransferManager s3TransferManager;

    @Value("${aws.s3.bucket.name}")
    private String bucketName;

    @Value("${aws.cloudfront.domain}")
    private String cloudFrontDomain;

    @Value("${application.file-storage.allowed-mime-types}")
    private List<String> allowedMimeTypes;

    @Override
    public String upload(File file) {
        UploadFileRequest uploadFileRequest = UploadFileRequest.builder()
                .putObjectRequest(b -> b.bucket(bucketName).key(file.getName()))
                .source(file)
                .build();

        transferManager.uploadFile(uploadFileRequest).completionFuture().join();
        return "http://" + cloudFrontDomain + "/" + file.getName();
    }

    @Override
    public String upload(@NonNull MultipartFile image) throws IOException {
        if (!allowedMimeTypes.contains(image.getContentType())) {
            throw new IOException("Image type (" + image.getContentType() + ") not allowed. Allowed types: "
                    + String.join(", ", allowedMimeTypes));
        }

        UploadRequest uploadRequest = UploadRequest.builder()
                .putObjectRequest(b -> b.bucket(bucketName).key(image.getOriginalFilename()))
                .requestBody(AsyncRequestBody.fromInputStream(
                        image.getInputStream(),
                        image.getSize(),
                        Executors.newFixedThreadPool(3)
                ))
                .build();

        transferManager.upload(uploadRequest).completionFuture().join();
        return "http://" + cloudFrontDomain + "/" + image.getOriginalFilename();
    }
}