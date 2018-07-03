package com.guanweiming.common.tencent.im;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author guanweiming
 */
@Slf4j
@Configuration
@EnableConfigurationProperties(TencentImProperties.class)
@ConditionalOnProperty(prefix = "guanweiming.tencent.im", name = "enabled")
public class TencentImServiceAutoConfigure {
    private final TencentImProperties properties;

    public TencentImServiceAutoConfigure(TencentImProperties properties) {
        this.properties = properties;
    }

    @Bean
    @ConditionalOnMissingBean
    public TencentImService tencentImService() {
        log.debug("注册腾讯云IM");
        return new TencentImService(properties);
    }

}
