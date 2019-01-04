package com.guanweiming.common.utils;



/**
 * @author guanweiming
 */
public class EmailUtil {
    private final static int MIN_LENGTH = 4;

    /**
     * 处理email
     * null-》null
     * abcdef@163.com-》ab****ef@163.com
     * 如果@前面的字符串小于等于4位
     * demo@163.com-》d***@163.com
     *
     * @param email 邮箱地址
     * @return 处理完的邮箱
     */
    public static String getSafeEmail(String email) {
        if (StringUtil.isBlank(email)) {
            return null;
        }
        if (!email.contains("@")) {
            return null;
        }
        String[] array = email.split("@");
        String name = array[0];
        if (name.length() < MIN_LENGTH) {
            return name.substring(0, 1) + "***@" + array[1];
        }
        String prefix = name.substring(0, 2);
        String suffix = name.substring(name.length() - 2, name.length());
        return prefix + "****" + suffix + "@" + array[1];
    }
}
