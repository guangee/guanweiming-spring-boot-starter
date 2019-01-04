package com.guanweiming.common.utils;

import org.apache.commons.lang3.StringUtils;

/**
 * @author chezhu.xin
 */
public class StringUtil {

    private static final String SPLIT = ".";


    public static boolean isBlank(final CharSequence cs) {
        return StringUtils.isBlank(cs);
    }

    public static boolean isNotBlank(final CharSequence cs) {
        return StringUtils.isNotBlank(cs);
    }

    public static boolean isEmpty(final CharSequence cs) {
        return StringUtils.isEmpty(cs);
    }

    public static boolean isNotEmpty(final CharSequence cs) {
        return StringUtils.isNotEmpty(cs);
    }

    /**
     * 获取资源地址的扩展名
     *
     * @param src 资源地址
     * @return 扩展名
     */
    public static String getExtend(String src) {
        if (StringUtil.isBlank(src) || !src.contains(SPLIT)) {
            return "";
        }
        return src.substring(src.lastIndexOf(SPLIT) + 1);
    }
}
