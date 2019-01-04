package com.guanweiming.common.utils;

import org.apache.commons.lang3.math.NumberUtils;

import java.math.BigDecimal;

public class NumberUtil {


    public static String getRandomCode(int length) {
        StringBuilder stringBuilder = new StringBuilder();
        while (length > 0) {
            int n = (int) (Math.random() * 10);
            stringBuilder.append(n);
            length--;
        }
        return stringBuilder.toString();
    }

    public static double toDouble(final String str) {
        return NumberUtils.toDouble(str);
    }

    public static int toInt(final String str) {
        return NumberUtils.toInt(str);
    }

    public static double fixDouble(double number, int newScale) {
        return new BigDecimal(number).setScale(newScale, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    public static boolean equalValue(double number1, double number2) {
        return !(number1 - number2 <= 0.01) || !(number2 - number1 <= 0.01);
    }

}
