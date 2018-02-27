package com.guanweiming.common.qiniu;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
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
@ConditionalOnProperty(prefix = "guanweiming.qiniu", name = "enabled", matchIfMissing = true)
public class QiniuServiceAutoConfigure implements BeanFactoryAware {

    private BeanFactory beanFactory;

    private final QiNiuProperties qiNiuProperties;

    @Autowired
    public QiniuServiceAutoConfigure(QiNiuProperties qiNiuProperties) {
        this.qiNiuProperties = qiNiuProperties;
    }

    @Bean
    @ConditionalOnMissingBean
    public QiniuService qiniuService() {
        log.debug("注册七牛云");
        QiniuService qiniuService = new QiniuService(qiNiuProperties.getAccessKey(), qiNiuProperties.getSecretKey(), qiNiuProperties.getBucket());
        ConfigurableBeanFactory configurableBeanFactory = (ConfigurableBeanFactory) beanFactory;
        configurableBeanFactory.registerSingleton("qiniuService", qiniuService);
        return qiniuService;
    }


    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = beanFactory;
    }
}
