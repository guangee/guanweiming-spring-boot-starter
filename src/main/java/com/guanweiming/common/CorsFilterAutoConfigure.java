package com.guanweiming.common;

import com.guanweiming.common.properties.CorsProperties;
import com.guanweiming.common.service.CorsFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author https://github.com/zziaguan/
 */
@Configuration
@ConditionalOnClass(CorsFilter.class)
@EnableConfigurationProperties(CorsProperties.class)
@ConditionalOnProperty(prefix = "tools.cors", value = "enabled", havingValue = "true")
public class CorsFilterAutoConfigure {

    private final CorsProperties corsProperties;

    @Autowired
    public CorsFilterAutoConfigure(CorsProperties corsProperties) {
        this.corsProperties = corsProperties;
    }


    @Bean
    @ConditionalOnMissingBean
    public CorsFilter corsFilter() {
        return new CorsFilter(corsProperties.getMaxAge(), corsProperties.getAllowOrigin(), corsProperties.getAllowMethods());
    }
}
