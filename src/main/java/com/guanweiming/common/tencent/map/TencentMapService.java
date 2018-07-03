package com.guanweiming.common.tencent.map;

import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * @author guanweiming
 */
@Slf4j
public class TencentMapService {
    private static int index = 0;
    private final List<String> keyList;

    public TencentMapService(TencentMapProperties tencentMapProperties) {
        keyList = Lists.newLinkedList(tencentMapProperties.getKey());
    }

    public synchronized String getGaoDeMapKey() {
        index = (index + 1) % keyList.size();
        String key = keyList.get(index);
        log.debug("gaoDeKey:\t{}\t{}",keyList.size(), key);
        return key;
    }
}
