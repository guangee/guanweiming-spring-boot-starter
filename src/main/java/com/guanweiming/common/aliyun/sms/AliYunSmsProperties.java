package com.guanweiming.common.aliyun.sms;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author https://github.com/zziaguan/
 */
@Data
@ConfigurationProperties("guanweiming.aliyun.sms")
public class AliYunSmsProperties {
    private String accessKey;
    private String secretKey;
    private String signName;
    private String templateCode;
}
