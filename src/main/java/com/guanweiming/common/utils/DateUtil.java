package com.guanweiming.common.utils;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {
    public static Date stringConvertDate(String source) {
        if (StringUtil.isBlank(source)) {
            return null;
        }
        if (source.length() == 10) {
            try {
                return new SimpleDateFormat("yyyy-MM-dd").parse(source);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        } else if (source.length() > 10) {
            try {
                return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(source);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * 获取今天是周几
     *
     * @param date 日期
     * @return 周几
     */
    public static int getDayOfWeek(Date date) {
        Calendar calendar = Calendar.getInstance();
        return calendar.get(Calendar.DAY_OF_WEEK) - 1;
    }

    /**
     * 获取两个日期相差的分钟数
     *
     * @param start 开始时间
     * @param end   结束时间
     * @return 相差分钟数
     */
    public static long getMinutes(Date start, Date end) {
        if (start == null || end == null) {
            return 0;
        }
        return Math.abs(start.getTime()-end.getTime())/(1000*60)+1;
    }

    public static Date getDayStart() {
        Date date = new Date();
        date.setHours(0);
        date.setMinutes(0);
        date.setSeconds(0);
        return date;
    }

    public static Date getDayEnd(){
        Date date = new Date();
        date.setHours(23);
        date.setMinutes(59);
        date.setSeconds(59);
        return date;
    }
}
