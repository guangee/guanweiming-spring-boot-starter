package com.guanweiming.common.wx.miniapp;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties("guanweiming.wx.miniapp")
public class MiniAppProperties {
    /**
     * 小程序id
     */
    private final String appId;
    /**
     * 小程序密钥
     */
    private final String appSecret;
}
