package com.guanweiming.common.wx.miniapp;

import com.guanweiming.common.qiniu.QiNiuProperties;
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
@EnableConfigurationProperties(MiniAppProperties.class)
@ConditionalOnProperty(prefix = "guanweiming.wx.miniapp", name = "enabled", matchIfMissing = true)
public class MiniAppServiceAutoConfigure {


    private final MiniAppProperties miniAppProperties;

    public MiniAppServiceAutoConfigure(MiniAppProperties miniAppProperties) {
        this.miniAppProperties = miniAppProperties;
    }

    @Bean
    @ConditionalOnMissingBean
    public MiniAppService miniAppService() {
        log.debug("注册七牛云");
        return new MiniAppService(miniAppProperties);
    }
}
