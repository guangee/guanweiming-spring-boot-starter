package com.guanweiming.common.utils;

/**
 * @author chezhu.xin
 */
@Deprecated
public interface MyLoader<K, V> {
    /**
     * 根据参数获取对象
     *
     * @param key 参数
     * @return 加载的对象
     */
    V load(K key);
}
