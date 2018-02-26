package com.guanweiming.common.cors;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author https://github.com/zziaguan/
 */
@Slf4j
@Configuration
@EnableConfigurationProperties(CorsProperties.class)
@ConditionalOnProperty(name = "guanweiming.cors.enable", matchIfMissing = true)
public class CorsFilterAutoConfigure {

    private final CorsProperties corsProperties;

    @Autowired
    public CorsFilterAutoConfigure(CorsProperties corsProperties) {
        this.corsProperties = corsProperties;
    }


    @Bean
    @ConditionalOnMissingBean
    public CorsFilter corsFilter() {
        log.info("配置跨域");
        return new CorsFilter(corsProperties.getMaxAge(), corsProperties.getAllowOrigin(), corsProperties.getAllowMethods());
    }
}
