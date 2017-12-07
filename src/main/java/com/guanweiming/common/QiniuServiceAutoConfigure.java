package com.guanweiming.common;

import com.guanweiming.common.properties.QiNiuProperties;
import com.guanweiming.common.service.QiniuService;
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
@ConditionalOnProperty(prefix = "tools.qiniu", value = "enabled", havingValue = "true")
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
