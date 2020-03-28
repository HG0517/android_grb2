package com.jgkj.utils.token.utils.date;

import android.text.TextUtils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * 时间、日期
 * Created by jugekeji on 2018/10/26.
 * 时间格式中 yyyy-MM-dd HH:mm:ss ，注意字母 y 不能大写
 */

public class DateUtils {

    /**
     * 根据时间表示格式，返回当前时间
     *
     * @param sformat 时间格式
     * @return
     */
    public static String getCurrentDateFormat(String sformat) {
        Date currentTime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat(sformat);
        String dateString = formatter.format(currentTime);
        return dateString;
    }

    /**
     * 根据时间戳和时间表示格式，返回时间
     *
     * @param date    时间戳
     * @param sformat 时间格式
     * @return
     */
    public static String getDateFormat(long date, String sformat) {
        Date currentTime = new Date(date);
        SimpleDateFormat formatter = new SimpleDateFormat(sformat);
        String dateString = formatter.format(currentTime);
        return dateString;
    }

    /**
     * 根据时间戳获取周内序列
     *
     * @param ldate 时间戳
     * @return 周内序列
     */
    public static int getDayOfWeekIndex(long ldate) {
        Date strDate = new Date(ldate);
        Calendar c = Calendar.getInstance(Locale.CHINA);
        c.setTime(strDate);

        // 获取索引：1 = 星期日  2 = 星期一  3 = 星期二  4 = 星期三  5 = 星期四  6 = 星期五  7 = 星期六
        return c.get(Calendar.DAY_OF_WEEK);
    }

    /**
     * 根据时间戳获取星期几
     *
     * @param date 时间戳
     * @return 星期几
     */
    public static String getDayOfWeekStr(long date) {
        Date strDate = new Date(date);
        Calendar c = Calendar.getInstance(Locale.CHINA);
        c.setTime(strDate);

        // 直接获取
        return new SimpleDateFormat("EEEE").format(c.getTime());
    }

    /**
     * 根据时间戳获取星期几：先获取周内序列，返回数据样式可修改
     *
     * @param date 时间戳
     * @return 周几
     */
    public static String getWeekStr(long date) {
        switch (getDayOfWeekIndex(date)) {
            case 1:
                return "周日";
            case 2:
                return "周一";
            case 3:
                return "周二";
            case 4:
                return "周三";
            case 5:
                return "周四";
            case 6:
                return "周五";
            case 7:
                return "周六";
            default:
                return "";
        }
    }

    /**
     * 当前时间年度周序列，即得到当前时间在年度内第几周
     *
     * @return 年度内第几周
     */
    public static int getWeekOfYear() {
        Calendar c = Calendar.getInstance(Locale.CHINA);
        // String week = Integer.toString(c.get(Calendar.WEEK_OF_YEAR));
        // String year = Integer.toString(c.get(Calendar.YEAR));
        return c.get(Calendar.WEEK_OF_YEAR);
    }

    /**
     * 根据时间戳获得年度周序列，即得到当前时间在年度内第几周
     *
     * @param date 时间戳
     * @return 年度内第几周
     */
    public static int getWeekOfYear(long date) {
        Date strDate = new Date(date);
        Calendar c = Calendar.getInstance(Locale.CHINA);
        c.setTime(strDate);
        // String week = Integer.toString(c.get(Calendar.WEEK_OF_YEAR));
        // String year = Integer.toString(c.get(Calendar.YEAR));
        return c.get(Calendar.WEEK_OF_YEAR);
    }

    /**
     * 获取一周内每一天的日期
     *
     * @param dateMill
     * @return
     */
    public static List<Long> getWeekDayList(long dateMill) {
        // 存放每一天时间的集合
        List<Long> weekMillisList = new ArrayList<>();
        // Calendar
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(dateMill);
        // 本周的第几天：1 = 星期日  2 = 星期一  3 = 星期二  4 = 星期三  5 = 星期四  6 = 星期五  7 = 星期六
        int weekNumber = calendar.get(Calendar.DAY_OF_WEEK);
        // 获取本周一的毫秒值 M24HOURMS
        long mondayMill = dateMill - 86400000 * (weekNumber - 2);

        for (int i = 0; i < 7; i++) {
            weekMillisList.add(mondayMill + 86400000 * i);
        }
        return weekMillisList;
    }

    /**
     * 当前是一年内的几月
     *
     * @param date
     * @return
     */
    public static String getMonthOfYear(long date) {
        Date strDate = new Date(date);
        Calendar c = Calendar.getInstance(Locale.CHINA);
        c.setTime(strDate);
        String week = Integer.toString(c.get(Calendar.MONTH) + 1);
        // String year = Integer.toString(c.get(Calendar.YEAR));
        return week;
    }

    /**
     * 两个时间之间的天数：date1 - date2  |   未来时间 >  当前时间  >  过去时间
     * date1、date2 是格式化过的时间
     *
     * @param date1 开始时间格式
     * @param date2 结束时间格式
     * @return 天数差值，整天数
     */
    public static long getDaysDifferent(String date1, String date2, String sformat) {
        if (TextUtils.isEmpty(date1) || TextUtils.isEmpty(date2))
            return 0;

        SimpleDateFormat myFormatter = new SimpleDateFormat(sformat);
        long dayDiff = 0;
        try {
            Date d1 = myFormatter.parse(date1);
            Date d2 = myFormatter.parse(date2);
            dayDiff = (d1.getTime() - d2.getTime()) / (24 * 60 * 60 * 1000);
        } catch (Exception e) {

        }
        return dayDiff;
    }

    /**
     * 两个时间之间的天数：date1 - date2  |   未来时间 >  当前时间  >  过去时间
     *
     * @param date1 开始时间戳
     * @param date2 结束时间戳
     * @return 天数差值，整天数
     */
    public static long getDaysDifferent(long date1, long date2) {
        long dayDiff = (date1 - date2) / (24 * 60 * 60 * 1000);
        return dayDiff;
    }

    /**
     * 判断是否为今天(效率比较高)
     *
     * @param date 传入的需要对比的时间戳：可以是过去的，现在的，未来的
     * @return true 是今天， false 不是今天
     */
    public static boolean isToday(long date) {
        return android.text.format.DateUtils.isToday(date);
    }

    /**
     * 距离当前时间过去了多久：n年前、  n月前、  n周前、  n天前、  n小时前、  n分钟前
     *
     * @param date 传入的时间戳：过去的
     * @return 过去了多久
     */
    public static String getHowLongAgo(long date) {
        // 当前时间
        Calendar today = Calendar.getInstance(Locale.CHINA);
        Date todayDate = new Date(System.currentTimeMillis());
        today.setTime(todayDate);

        // 过去
        Calendar ago = Calendar.getInstance(Locale.CHINA);
        Date agoDate = new Date(date);
        ago.setTime(agoDate);

        // 年份不同时，计算年份差值
        if (today.get(Calendar.YEAR) != ago.get(Calendar.YEAR))
            return today.get(Calendar.YEAR) - ago.get(Calendar.YEAR) + "年前";
        // 同年内计算月份差值
        if ((today.get(Calendar.MONTH) + 1) != (ago.get(Calendar.MONTH) + 1))
            return (today.get(Calendar.MONTH) + 1) - (ago.get(Calendar.MONTH) + 1) + "个月前";
        // 同月内计算周数差值
        if (today.get(Calendar.WEEK_OF_YEAR) != ago.get(Calendar.WEEK_OF_YEAR))
            return today.get(Calendar.WEEK_OF_YEAR) - ago.get(Calendar.WEEK_OF_YEAR) + "个周前";
        else return getDistanceTime(date);
//        // 同周内计算天数差值
//        if (today.get(Calendar.DAY_OF_YEAR) != ago.get(Calendar.DAY_OF_YEAR))
//            return today.get(Calendar.DAY_OF_YEAR) - ago.get(Calendar.DAY_OF_YEAR) + "天前";
//        // 同天内计算时数差值
//        if (today.get(Calendar.HOUR_OF_DAY) != ago.get(Calendar.HOUR_OF_DAY))
//            return today.get(Calendar.HOUR_OF_DAY) - ago.get(Calendar.HOUR_OF_DAY) + "小时前";
//        // 同时内计算分数差值
//        if (today.get(Calendar.MINUTE) != ago.get(Calendar.MINUTE))
//            return today.get(Calendar.MINUTE) - ago.get(Calendar.MINUTE) + "分钟前";
//        return "moments";
    }

    /**
     * 计算当前时间 time2 减去过去时间 time1 的差值 差值只设置 几天 几个小时 或 几分钟
     * 根据差值返回多长时间前或多长时间后
     */
    public static String getDistanceTime(long time1) {
        long time2 = System.currentTimeMillis();
        long day = 0;
        long hour = 0;
        long min = 0;
        long sec = 0;
        long diff;
        String flag;
        if (time1 < time2) {
            diff = time2 - time1;
            flag = "前";
        } else {
            diff = time1 - time2;
            flag = "后";
        }
        day = diff / (24 * 60 * 60 * 1000);
        hour = (diff / (60 * 60 * 1000) - day * 24);
        min = ((diff / (60 * 1000)) - day * 24 * 60 - hour * 60);
        sec = (diff / 1000 - day * 24 * 60 * 60 - hour * 60 * 60 - min * 60);
        if (day != 0) return day + "天" + flag;
        if (hour != 0) return hour + "小时" + flag;
        if (min != 0) return min + "分钟" + flag;
        return "刚刚";
    }

    /**
     * 根据毫秒时间戳来格式化字符串
     * 今天显示几时几分、昨天显示昨天、前天显示前天
     * 早于前天的显示具体年-月-日，如2017-06-12
     *
     * @param timeStamp 毫秒值
     * @return 几时几分 昨天 前天 或者 yyyy-MM-dd 类型字符串
     */
    public static String format(long timeStamp) {
        String strTime = "";
        long curTimeMillis = System.currentTimeMillis();
        Date curDate = new Date(curTimeMillis);
        int todayHoursSeconds = curDate.getHours() * 60 * 60;
        int todayMinutesSeconds = curDate.getMinutes() * 60;
        int todaySeconds = curDate.getSeconds();
        int todayMillis = (todayHoursSeconds + todayMinutesSeconds + todaySeconds) * 1000;
        long todayStartMillis = curTimeMillis - todayMillis;
        if (timeStamp >= todayStartMillis) {
            Date date = new Date(timeStamp);
            strTime = date.getHours() + ":" + date.getMinutes();//显示几时几分
            return strTime;
        }
        int oneDayMillis = 24 * 60 * 60 * 1000;
        long yesterdayStartMillis = todayStartMillis - oneDayMillis;
        if (timeStamp >= yesterdayStartMillis) {
            return "昨天";
        }
        long yesterdayBeforeStartMillis = yesterdayStartMillis - oneDayMillis;
        if (timeStamp >= yesterdayBeforeStartMillis) {
            return "前天";
        }
        strTime = DateUtils.getDateFormat(timeStamp, "yyyy-MM-dd ");
        return strTime;
    }

}
