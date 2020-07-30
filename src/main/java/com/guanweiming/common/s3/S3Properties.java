package com.guanweiming.common.s3;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author https://github.com/zziaguan/
 */
@Data
@ConfigurationProperties("guanweiming.s3")
public class S3Properties {

    /**
     * accessKey
     */
    private String accessKey;
    /**
     * secretKey
     */
    private String secretKey;
    /**
     * 空间名
     */
    private String bucket;
    /**
     * 基础访问地址
     */
    private String url;
}
