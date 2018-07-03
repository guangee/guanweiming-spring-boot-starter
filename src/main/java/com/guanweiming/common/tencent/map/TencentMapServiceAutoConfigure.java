package com.guanweiming.common.tencent.map;

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
@EnableConfigurationProperties(TencentMapProperties.class)
@ConditionalOnProperty(prefix = "guanweiming.tencent.map", name = "enabled")
public class TencentMapServiceAutoConfigure {

    private final TencentMapProperties tencentMapProperties;

    public TencentMapServiceAutoConfigure(TencentMapProperties tencentMapProperties) {
        this.tencentMapProperties = tencentMapProperties;
    }

    @Bean
    @ConditionalOnMissingBean
    public TencentMapService tencentMapService() {
        log.debug("注册腾讯地图服务");
        return new TencentMapService(tencentMapProperties);
    }
}
