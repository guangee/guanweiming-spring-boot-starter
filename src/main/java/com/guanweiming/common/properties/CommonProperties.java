package com.guanweiming.common.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author https://github.com/zziaguan/
 */
@ConfigurationProperties("tools.common")
public class CommonProperties {
    private String ip="juhe";

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }
}
