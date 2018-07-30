package com.guanweiming.common.map;

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
@EnableConfigurationProperties(GaoDeMapProperties.class)
@ConditionalOnProperty(prefix = "guanweiming.gaode.map", name = "enabled")
public class GaoDeMapServiceAutoConfigure {

    private final GaoDeMapProperties gaoDeMapProperties;

    public GaoDeMapServiceAutoConfigure(GaoDeMapProperties gaoDeMapProperties) {
        this.gaoDeMapProperties = gaoDeMapProperties;
    }

    @Bean
    @ConditionalOnMissingBean
    public GaoDeMapService gaoDeMapService() {
        log.debug("注册高德服务");
        return new GaoDeMapService(gaoDeMapProperties);
    }
}
