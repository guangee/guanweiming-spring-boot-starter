package com.guanweiming.common.map;

import com.google.common.collect.Lists;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

/**
 * @author guanweiming
 */
@Data
@ConfigurationProperties("guanweiming.gaode.map")
public class GaoDeMapProperties {
    /**
     * 高德地图Key
     */
    List<String> key = Lists.newArrayList();
}
