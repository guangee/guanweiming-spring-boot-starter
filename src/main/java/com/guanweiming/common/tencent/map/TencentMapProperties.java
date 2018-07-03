package com.guanweiming.common.tencent.map;

import com.google.common.collect.Lists;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

/**
 * @author guanweiming
 */
@Data
@ConfigurationProperties("guanweiming.tencent.map")
public class TencentMapProperties {
    /**
     * 腾讯地图Key
     */
    List<String> key = Lists.newArrayList();
}
