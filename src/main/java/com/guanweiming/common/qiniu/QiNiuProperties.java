package com.guanweiming.common.qiniu;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author https://github.com/zziaguan/
 */
@Data
@ConfigurationProperties("guanweiming.qiniu")
public class QiNiuProperties {
    private String accessKey;
    private String secretKey;
    private String bucket;
}
