package org.trendwa.eshop.catalogservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.transfer.s3.S3TransferManager;
import software.amazon.awssdk.transfer.s3.model.UploadFileRequest;

import java.io.File;
import java.io.IOException;


/**
 * Service for handling file storage operations with AWS S3.
 * <p>
 * This service uploads files to an S3 bucket and returns the public CDN URL of the uploaded file.
 * </p>
 */
@Service
@RequiredArgsConstructor
@Profile("prod")
public class S3FileStorageService implements FileStorageService {

    private final S3TransferManager transferManager;

    @Value("${aws.s3.bucket.name}")
    private String bucketName;

    @Value("${aws.cloudfront.domain}")
    private String cloudFrontDomain;


    @Override
    public String upload(File file) {

        UploadFileRequest uploadFileRequest = UploadFileRequest.builder()
                .putObjectRequest(b -> b.bucket(bucketName).key(file.getName()))
                .source(file.toPath())
                .build();

        transferManager.uploadFile(uploadFileRequest).completionFuture().join();
        return "https://" + cloudFrontDomain + "/" + file.getName();
    }

    @Override
    public String upload(MultipartFile file) throws IOException {
        UploadFileRequest uploadFileRequest = UploadFileRequest.builder()
                .putObjectRequest(b -> b.bucket(bucketName).key(file.getName()))
                .source(file.getResource().getFile().toPath())
                .build();

        transferManager.uploadFile(uploadFileRequest).completionFuture().join();
        return "https://" + cloudFrontDomain + "/" + file.getName();
    }
}