package com.guanweiming.common.utils;


/**
 * @author guanweiming
 */
public class PhoneUtil {
    public static final int PHONE_LENGTH = 11;

    /**
     * 获取安全号码 18888888888--》188****8888
     * 手机号码必须是11位才能处理，否则返回空
     *
     * @param phone 原始手机号码
     * @return 处理过的手机号码
     */
    public static String getSafePhone(String phone) {
        if (StringUtil.isBlank(phone) || phone.length() != PHONE_LENGTH) {
            return null;
        }
        String prefix = phone.substring(0, 3);
        String suffix = phone.substring(phone.length() - 4, phone.length());

        return prefix + "****" + suffix;
    }
}
