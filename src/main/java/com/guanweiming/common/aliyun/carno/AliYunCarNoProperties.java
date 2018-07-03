package com.guanweiming.common.aliyun.carno;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author guanweiming
 */
@Data
@ConfigurationProperties("guanweiming.aliyun.ocr.car")
public class AliYunCarNoProperties {
    private String appCode;
}
