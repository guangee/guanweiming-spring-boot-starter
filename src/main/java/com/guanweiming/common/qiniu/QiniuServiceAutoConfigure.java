package com.guanweiming.common.qiniu;

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
@EnableConfigurationProperties(QiNiuProperties.class)
@ConditionalOnProperty(prefix = "guanweiming.qiniu", name = "enabled")
public class QiniuServiceAutoConfigure {


    private final QiNiuProperties qiNiuProperties;

    @Autowired
    public QiniuServiceAutoConfigure(QiNiuProperties qiNiuProperties) {
        this.qiNiuProperties = qiNiuProperties;
    }

    @Bean
    @ConditionalOnMissingBean
    public QiniuService qiniuService() {
        log.debug("注册七牛云");
        return new QiniuService(qiNiuProperties);
    }
}
