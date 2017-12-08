package com.guanweiming.common;

import com.guanweiming.common.properties.CommonProperties;
import com.guanweiming.common.service.CommonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author https://github.com/zziaguan/
 */
@Configuration
@EnableConfigurationProperties(CommonProperties.class)
@ConditionalOnProperty(prefix = "tools.common", value = "enabled", havingValue = "true")
public class CommonAutoConfigure {
    private final CommonProperties commonProperties;

    @Autowired
    public CommonAutoConfigure(CommonProperties commonProperties) {
        this.commonProperties = commonProperties;
    }

    @Bean
    @ConditionalOnMissingBean
    public CommonService corsFilter() {
        return new CommonService(commonProperties);
    }
}
