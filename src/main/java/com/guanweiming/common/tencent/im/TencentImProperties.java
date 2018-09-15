package com.guanweiming.common.tencent.im;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author guanweiming
 */
@Data
@ConfigurationProperties("guanweiming.tencent.im")
public class TencentImProperties {

    /**
     * 公钥
     */
    private String publicString;
    /**
     * 私钥
     */
    private String privateString;
    /**
     * appId
     */
    private long sdkAppId;
}
