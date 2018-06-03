package com.guanweiming.common.aliyun.sms;

import lombok.extern.slf4j.Slf4j;
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
@EnableConfigurationProperties(AliYunSmsProperties.class)
@ConditionalOnProperty(prefix = "guanweiming.aliyun.sms", name = "enabled", matchIfMissing = true)
public class AliYunSmsServiceAutoConfigure {


    private final AliYunSmsProperties aliYunSmsProperties;

    public AliYunSmsServiceAutoConfigure(AliYunSmsProperties aliYunSmsProperties) {
        this.aliYunSmsProperties = aliYunSmsProperties;
    }


    @Bean
    @ConditionalOnMissingBean
    public AliYunSmsService qiniuService() {
        log.debug("注册七牛云");
        return new AliYunSmsService(aliYunSmsProperties);
    }
}
