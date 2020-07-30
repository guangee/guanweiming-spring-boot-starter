package com.guanweiming.common.s3;

import com.amazonaws.ClientConfiguration;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author https://github.com/zziaguan/
 */
@Slf4j
@Configuration
@EnableConfigurationProperties(S3Properties.class)
@ConditionalOnProperty(prefix = "guanweiming.s3", name = "enabled")
public class S3ServiceAutoConfigure {


    private final S3Properties s3Properties;

    @Autowired
    public S3ServiceAutoConfigure(S3Properties s3Properties) {
        this.s3Properties = s3Properties;
    }


    @Bean
    @ConditionalOnMissingBean
    public AmazonS3 s3() {
        AWSCredentials credentials = new BasicAWSCredentials(s3Properties.getAccessKey(), s3Properties.getSecretKey());
        ClientConfiguration clientConfiguration = new ClientConfiguration();
        clientConfiguration.setSignerOverride("AWSS3V4SignerType");
        log.debug("注册S3");

        AmazonS3 s3Client = AmazonS3ClientBuilder
                .standard()
                .withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration(s3Properties.getUrl(), Regions.US_EAST_1.name()))
                .withPathStyleAccessEnabled(true)
                .withClientConfiguration(clientConfiguration)
                .withCredentials(new AWSStaticCredentialsProvider(credentials))
                .build();
        return s3Client;
    }
}
