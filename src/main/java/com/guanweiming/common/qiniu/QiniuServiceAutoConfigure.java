package com.guanweiming.common.qiniu;

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
@ConditionalOnClass(QiniuService.class)
@EnableConfigurationProperties(QiNiuProperties.class)
@ConditionalOnProperty(name = "tools.qiniu.enable",matchIfMissing = false)
public class QiniuServiceAutoConfigure {

    private final QiNiuProperties qiNiuProperties;

    @Autowired
    public QiniuServiceAutoConfigure(QiNiuProperties qiNiuProperties) {
        this.qiNiuProperties = qiNiuProperties;
    }

    @Bean
    @ConditionalOnMissingBean
    public QiniuService qiniuService() {
        return new QiniuService(qiNiuProperties.getAccessKey(), qiNiuProperties.getSecretKey(), qiNiuProperties.getBucket());
    }


}
