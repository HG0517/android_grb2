package com.jgkj.grb.view.datepicker;

import com.jgkj.grb.utils.Logger;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * 说明：日期格式化工具
 * 作者：liuwan1992
 * 添加时间：2018/12/17
 * 修改人：liuwan1992
 * 修改时间：2018/12/18
 */
public class DateFormatUtils {

    public static final String DATE_FORMAT_PATTERN_YMD = "yyyy-MM-dd";
    public static final String DATE_FORMAT_PATTERN_HMS = "HH:mm:ss";
    public static final String DATE_FORMAT_PATTERN_YMD_HM = "yyyy-MM-dd HH:mm";
    public static final String DATE_FORMAT_PATTERN_YMD_HMS = "yyyy-MM-dd HH:mm:ss";

    /**
     * 时间戳转字符串
     *
     * @param timestamp     时间戳
     * @param isPreciseTime 是否包含时分
     * @return 格式化的日期字符串
     */
    public static String long2Str(long timestamp, boolean isPreciseTime) {
        return long2Str(timestamp, getFormatPattern(isPreciseTime));
    }

    public static String long2Str(long timestamp, String pattern) {
        return new SimpleDateFormat(pattern, Locale.CHINA).format(new Date(timestamp));
    }

    /**
     * 字符串转时间戳
     *
     * @param dateStr       日期字符串
     * @param isPreciseTime 是否包含时分
     * @return 时间戳
     */
    public static long str2Long(String dateStr, boolean isPreciseTime) {
        return str2Long(dateStr, getFormatPattern(isPreciseTime));
    }

    public static long str2Long(String dateStr, String pattern) {
        try {
            return new SimpleDateFormat(pattern, Locale.CHINA).parse(dateStr).getTime();
        } catch (Throwable ignored) {
        }
        return 0;
    }

    /**
     * 秒转换为指定格式的日期
     *
     * @param second
     * @param pattern
     * @return
     */
    public static String secondToDate(long second, String pattern) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(second * 1000);
        Date date = calendar.getTime();
        SimpleDateFormat format = new SimpleDateFormat(pattern, Locale.CHINA);
        return format.format(date);
    }

    /**
     * 两个秒值的差值
     *
     * @param second  秒值
     * @param second1 秒值1
     * @return String[] 天/时/分/秒
     */
    public static String[] secondDiff(long second, long second1) {
        String[] diffArray = new String[4];
        long minSecond = Math.min(second, second1);
        long maxSecond = Math.max(second, second1);
        long diff = maxSecond - minSecond;
        long days = diff / 86400;// 转换天数
        diff = diff % 86400;// 剩余秒数
        long hours = diff / 3600;// 转换小时数
        diff = diff % 3600;// 剩余秒数
        long minutes = diff / 60;// 转换分钟
        diff = diff % 60;// 剩余秒数
        String defauleStr = "00";
        String offsetStr = "%02d";
        if (0 < days) {
            diffArray[0] = String.valueOf(days);
            diffArray[1] = String.format(Locale.getDefault(), offsetStr, hours);
            diffArray[2] = String.format(Locale.getDefault(), offsetStr, minutes);
            diffArray[3] = String.format(Locale.getDefault(), offsetStr, diff);
            return diffArray;
        } else if (0 < hours) {
            diffArray[0] = defauleStr;
            diffArray[1] = String.format(Locale.getDefault(), offsetStr, hours);
            diffArray[2] = String.format(Locale.getDefault(), offsetStr, minutes);
            diffArray[3] = String.format(Locale.getDefault(), offsetStr, diff);
            return diffArray;
        } else if (0 < minutes) {
            diffArray[0] = defauleStr;
            diffArray[1] = defauleStr;
            diffArray[2] = String.format(Locale.getDefault(), offsetStr, minutes);
            diffArray[3] = String.format(Locale.getDefault(), offsetStr, diff);
            return diffArray;
        } else if (0 < second) {
            diffArray[0] = defauleStr;
            diffArray[1] = defauleStr;
            diffArray[2] = defauleStr;
            diffArray[3] = String.format(Locale.getDefault(), offsetStr, diff);
            return diffArray;
        } else {
            diffArray[0] = defauleStr;
            diffArray[1] = defauleStr;
            diffArray[2] = defauleStr;
            diffArray[3] = defauleStr;
            return diffArray;
        }
    }
    public static String[] millisDiff(long millis) {
        String[] diffArray = new String[4];
        int ss = 1000;
        int mi = ss * 60;
        int hh = mi * 60;
        int dd = hh * 24;

        long days = millis / dd;
        long hours = (millis - days * dd) / hh;
        long minutes = (millis - days * dd - hours * hh) / mi;
        long seconds = (millis - days * dd - hours * hh - minutes * mi) / ss;


        String defauleStr = "00";
        String offsetStr = "%02d";
        if (0 < days) {
            diffArray[0] = String.valueOf(days);
            diffArray[1] = String.format(Locale.getDefault(), offsetStr, hours);
            diffArray[2] = String.format(Locale.getDefault(), offsetStr, minutes);
            diffArray[3] = String.format(Locale.getDefault(), offsetStr, seconds);
            return diffArray;
        } else if (0 < hours) {
            diffArray[0] = defauleStr;
            diffArray[1] = String.format(Locale.getDefault(), offsetStr, hours);
            diffArray[2] = String.format(Locale.getDefault(), offsetStr, minutes);
            diffArray[3] = String.format(Locale.getDefault(), offsetStr, seconds);
            return diffArray;
        } else if (0 < minutes) {
            diffArray[0] = defauleStr;
            diffArray[1] = defauleStr;
            diffArray[2] = String.format(Locale.getDefault(), offsetStr, minutes);
            diffArray[3] = String.format(Locale.getDefault(), offsetStr, seconds);
            return diffArray;
        } else if (0 < seconds) {
            diffArray[0] = defauleStr;
            diffArray[1] = defauleStr;
            diffArray[2] = defauleStr;
            diffArray[3] = String.format(Locale.getDefault(), offsetStr, seconds);
            return diffArray;
        } else {
            diffArray[0] = defauleStr;
            diffArray[1] = defauleStr;
            diffArray[2] = defauleStr;
            diffArray[3] = defauleStr;
            return diffArray;
        }
    }

    /**
     * 返回日时分秒
     *
     * @param second
     * @return
     */
    private static String secondToTime(long second) {
        long days = second / 86400;// 转换天数
        second = second % 86400;// 剩余秒数
        long hours = second / 3600;// 转换小时数
        second = second % 3600;// 剩余秒数
        long minutes = second / 60;// 转换分钟
        second = second % 60;// 剩余秒数
        if (0 < days) {
            return days + "天，" + hours + "小时，" + minutes + "分，" + second + "秒";
        } else {
            return hours + "小时，" + minutes + "分，" + second + "秒";
        }
    }

    private static String getFormatPattern(boolean showSpecificTime) {
        if (showSpecificTime) {
            return DATE_FORMAT_PATTERN_YMD_HM;
        } else {
            return DATE_FORMAT_PATTERN_YMD;
        }
    }

    /**
     * 获取当前月份的时间范围
     *
     * @return
     */
    public static DateRange getThisMonth() {
        Calendar startCalendar = Calendar.getInstance();
        startCalendar.set(Calendar.DAY_OF_MONTH, 1);
        setMinTime(startCalendar);

        Calendar endCalendar = Calendar.getInstance();
        endCalendar.set(Calendar.DAY_OF_MONTH, endCalendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        setMaxTime(endCalendar);

        return new DateRange(startCalendar.getTime(), endCalendar.getTime());
    }

    /**
     * 获取上个月的时间范围
     *
     * @return
     */
    public static DateRange getLastMonth() {
        Calendar startCalendar = Calendar.getInstance();
        startCalendar.add(Calendar.MONTH, -1);
        startCalendar.set(Calendar.DAY_OF_MONTH, 1);
        setMinTime(startCalendar);

        Calendar endCalendar = Calendar.getInstance();
        endCalendar.add(Calendar.MONTH, -1);
        endCalendar.set(Calendar.DAY_OF_MONTH, endCalendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        setMaxTime(endCalendar);

        return new DateRange(startCalendar.getTime(), endCalendar.getTime());
    }

    /**
     * 获取当前季度的时间范围
     *
     * @return current quarter
     */
    public static DateRange getThisQuarter() {
        Calendar startCalendar = Calendar.getInstance();
        startCalendar.set(Calendar.MONTH, ((int) startCalendar.get(Calendar.MONTH) / 3) * 3);
        startCalendar.set(Calendar.DAY_OF_MONTH, 1);
        setMinTime(startCalendar);

        Calendar endCalendar = Calendar.getInstance();
        endCalendar.set(Calendar.MONTH, ((int) startCalendar.get(Calendar.MONTH) / 3) * 3 + 2);
        endCalendar.set(Calendar.DAY_OF_MONTH, endCalendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        setMaxTime(endCalendar);

        return new DateRange(startCalendar.getTime(), endCalendar.getTime());
    }

    /**
     * 获取上个季度的时间范围
     *
     * @return
     */
    public static DateRange getLastQuarter() {
        Calendar startCalendar = Calendar.getInstance();
        startCalendar.set(Calendar.MONTH, ((int) startCalendar.get(Calendar.MONTH) / 3 - 1) * 3);
        startCalendar.set(Calendar.DAY_OF_MONTH, 1);
        setMinTime(startCalendar);

        Calendar endCalendar = Calendar.getInstance();
        endCalendar.set(Calendar.MONTH, ((int) endCalendar.get(Calendar.MONTH) / 3 - 1) * 3 + 2);
        endCalendar.set(Calendar.DAY_OF_MONTH, endCalendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        setMaxTime(endCalendar);

        return new DateRange(startCalendar.getTime(), endCalendar.getTime());
    }

    /**
     * 获取本年的时间范围
     *
     * @return
     */
    public static DateRange getThisYear() {
        Calendar startCalendar = Calendar.getInstance();
        startCalendar.set(Calendar.DAY_OF_YEAR, 1);
        setMinTime(startCalendar);

        Calendar endCalendar = Calendar.getInstance();
        endCalendar.set(Calendar.DAY_OF_YEAR, endCalendar.getActualMaximum(Calendar.DAY_OF_YEAR));
        setMaxTime(endCalendar);

        return new DateRange(startCalendar.getTime(), endCalendar.getTime());
    }

    /**
     * 获取上一年的时间范围
     *
     * @return
     */
    public static DateRange getLastYear() {
        Calendar startCalendar = Calendar.getInstance();
        startCalendar.add(Calendar.YEAR, -1);
        startCalendar.set(Calendar.DAY_OF_YEAR, 1);
        setMinTime(startCalendar);

        Calendar endCalendar = Calendar.getInstance();
        endCalendar.add(Calendar.YEAR, -1);
        endCalendar.set(Calendar.DAY_OF_YEAR, endCalendar.getActualMaximum(Calendar.DAY_OF_YEAR));
        setMaxTime(endCalendar);

        return new DateRange(startCalendar.getTime(), endCalendar.getTime());
    }

    private static void setMinTime(Calendar calendar) {
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
    }

    private static void setMaxTime(Calendar calendar) {
        calendar.set(Calendar.HOUR_OF_DAY, calendar.getActualMaximum(Calendar.HOUR_OF_DAY));
        calendar.set(Calendar.MINUTE, calendar.getActualMaximum(Calendar.MINUTE));
        calendar.set(Calendar.SECOND, calendar.getActualMaximum(Calendar.SECOND));
        calendar.set(Calendar.MILLISECOND, calendar.getActualMaximum(Calendar.MILLISECOND));
    }

    public static class DateRange {
        private Date start;
        private Date end;

        DateRange(Date start, Date end) {
            this.start = start;
            this.end = end;
        }

        public Date getStart() {
            return start;
        }

        public void setStart(Date start) {
            this.start = start;
        }

        public Date getEnd() {
            return end;
        }

        public void setEnd(Date end) {
            this.end = end;
        }
    }


}
