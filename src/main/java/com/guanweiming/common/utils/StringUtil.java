package com.guanweiming.common.utils;

import org.apache.commons.lang3.StringUtils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Enumeration;

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


    public static LocalDate toLocalDate(String str) {
        if (StringUtils.isBlank(str)) {
            return null;
        }
        return toLocalDate(str, "yyyy-MM-dd");
    }


    public static LocalDate toLocalDate(String str, String pattern) {
        if (StringUtils.isBlank(str)) {
            return null;
        }
        return LocalDate.parse(str, DateTimeFormatter.ofPattern(pattern));
    }

    public static String getRelevantDate(Date date) {
        return null;
    }

    public static String getRelevantTime(Date date) {
        return null;
    }

    public static String getNames(Enumeration<String> headers) {
        StringBuilder sb = new StringBuilder();
        while (headers.hasMoreElements()) {
            sb.append(headers.nextElement());
        }
        return sb.toString();
    }
}
