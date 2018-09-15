package com.guanweiming.common.qiniu;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author https://github.com/zziaguan/
 */
@Data
@ConfigurationProperties("guanweiming.qiniu")
public class QiNiuProperties {

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
    private String host;
}
