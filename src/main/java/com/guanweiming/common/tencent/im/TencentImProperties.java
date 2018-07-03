package com.guanweiming.common.tencent.im;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author guanweiming
 */
@Data
@ConfigurationProperties("guanweiming.tencent.im")
public class TencentImProperties {
    private String publicString;
    private String privateString;
    private long sdkAppId;
}
