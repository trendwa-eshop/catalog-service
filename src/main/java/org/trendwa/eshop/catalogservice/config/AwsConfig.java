package org.trendwa.eshop.catalogservice.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.AwsCredentialsProvider;
import software.amazon.awssdk.auth.credentials.EnvironmentVariableCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3AsyncClient;
import software.amazon.awssdk.transfer.s3.S3TransferManager;

@Configuration
public class AwsConfig {

    @Value("${aws.s3.bucket.region}")
    private String bucketRegion;

    @Bean
    public AwsCredentialsProvider awsCredentialsProvider() {
        //Assumes that the AWS credentials are provided via environment variables (AWS_ACCESS_KEY_ID and AWS_SECRET_ACCESS_KEY).
        return EnvironmentVariableCredentialsProvider.create();
    }

    @Bean
    public S3AsyncClient s3AsyncClient(AwsCredentialsProvider awsCredentialsProvider) {
        return S3AsyncClient.builder()
                .credentialsProvider(awsCredentialsProvider)
                .region(Region.of(bucketRegion))
                .build();
    }

    @Bean
    public S3TransferManager s3TransferManager(S3AsyncClient s3AsyncClient) {
        return S3TransferManager.builder()
                .s3Client(s3AsyncClient)
                .build();
    }

}
