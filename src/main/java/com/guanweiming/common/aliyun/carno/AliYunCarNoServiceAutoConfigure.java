package com.guanweiming.common.aliyun.carno;

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
@EnableConfigurationProperties(AliYunCarNoProperties.class)
@ConditionalOnProperty(prefix = "guanweiming.aliyun.ocr.car", name = "enabled")
public class AliYunCarNoServiceAutoConfigure {

    private final AliYunCarNoProperties aliYunCarNoProperties;

    public AliYunCarNoServiceAutoConfigure(AliYunCarNoProperties aliYunCarNoProperties) {
        this.aliYunCarNoProperties = aliYunCarNoProperties;
    }

    @Bean
    @ConditionalOnMissingBean
    public AliYunCarNoService aliYunCarNoService() {
        log.debug("注册阿里云ocr车牌识别");
        return new AliYunCarNoService(aliYunCarNoProperties);
    }
}
