package com.jgkj.utils.token.utils.date;

import android.text.TextUtils;
import android.util.Log;

import java.math.BigDecimal;
import java.text.DecimalFormat;

/**
 * 数字处理：保留小数、数字转汉
 * Created by jugekeji on 2018/10/19.
 */

public class CustomDecimalFormat {

    private static String[] units = {"", "十", "百", "千"};
    private static String[] quot = {"万", "亿", "兆", "京", "垓", "秭", "穰", "沟", "涧", "正", "载", "极", "恒河沙", "阿僧祗", "那由他", "不可思议", "无量", "大数"};
    private static char[] numArray = {'零', '一', '二', '三', '四', '五', '六', '七', '八', '九'};

    /**
     * 保留小数点后的两位
     * DecimalFormat 类构造方法的字符格式这里如果小数不足 2 位,会以 0 补足
     * format 方法返回的是字符串
     *
     * @param number 待转换数字
     * @return 转换后的汉字
     */
    public static String keep2Decimal(double number) {
        if (number <= 0) {
            return "0.00";
        }
        DecimalFormat decimalFormat = new DecimalFormat("#.##");
        return decimalFormat.format(number);
    }

    public static String keep2Decimal(String number) {
        if (TextUtils.isEmpty(number)) {
            return "0.00";
        }
        return String.format("%.2f", Double.valueOf(number));
    }

    // 个、十、百、千
    // 万、十万、百万、千万
    // 亿、十亿、百亿、千亿
    // 兆、十兆、百兆、千兆
    // 京、十京、百京、千京
    // 垓、十垓、百垓、千垓
    // 秭、十秭、百秭、千秭
    // 穰、十穰、百穰、千穰
    // 㘬、十㘬、百㘬、千㘬
    // 涧、十涧、百涧、千涧
    // 正、十正、百正、千正
    // 载、十载、百载、千载
    // 极、十极、百极、千极
    // 恒河沙、十恒河沙、百恒河沙、千恒河沙
    // 阿僧祗、十阿僧祗、百阿僧祗、千阿僧祗
    // 那由他、十那由他、百那由他、千那由他
    // 不可思议、十不可思议、百不可思议、千不可思议
    // 无量、十无量、百无量、千无量
    // 大数、十大数、百大数、千大数
    public static String convertNumberToChinese(String numberStr) {
        // 数字 数组
        String[] nums = new String[]{"零", "一", "二", "三", "四", "五", "六", "七", "八", "九"};
        // 位 数组
        String[] digits = new String[]{"", "十", "百", "千"};
        // 单位 数组
        String[] units = new String[]{"", "万", "亿", "兆", "京", "垓 ", "秭", "穰", "㘬", "涧", "正", "载", "极"};

        // 原数字为空返回 ""
        if (numberStr == null || numberStr.length() <= 0)
            return "";

        // 替换掉数字中的分隔符："," 和 " "，再或者其他的分隔符
        String x = numberStr.replaceAll(",", "").replaceAll(" ", "");
        if (x.length() <= 0)
            return "";
        // 四位一组得到组数：m == 0,四位整分； m > 0,四位整分+1
        int m = x.length() % 4; // 取模
        int k = (m > 0 ? x.length() / 4 + 1 : x.length() / 4);
        // 四位一组得到的组数 > 单位 数组长度:数字太大，不处理，返回原数字
        if (k > units.length) {
            return numberStr;
        }

        String chinese = ""; // 转换结果返回值
        int p = 0; // 字符位置指针
        // 外层循环在所有组中循环
        // 从左到右 高位到低位 四位一组 逐组处理
        // 每组最后加上一个单位: "兆","亿","万"
        for (int i = k; i > 0; i--) {
            int L = 4; // 四位一组，最高位组有可能不足四位
            if (i == k && m != 0) {
                L = m;
            }
            // 得到一组的数字
            String s = x.substring(p, p + L);
            int l = s.length(); // 该组数字的位数

            // 内层循环在该组中的每一位数上循环 从左到右 高位到低位
            for (int j = 0; j < l; j++) {
                // 处理该组中的每一位数加上所在位: "千","百","十",""(个)
                int n = Integer.parseInt(s.substring(j, j + 1));
                if (n == 0) {
                    // 组内 0 的处理，因为末位是个位 ""，所以处理末位之前的：非末位 && 后一位 > 0 && 之前的[末尾] 非
                    // 0
                    if ((j < l - 1) && (Integer.parseInt(s.substring(j + 1, j + 1 + 1)) > 0)
                            && !chinese.endsWith(nums[n])) {
                        chinese += nums[n];
                    }
                } else {
                    // 处理 1013 一千零"十三", 1113 一千一百"一十三"
                    // 1 在十位 && 百位为 0 ，十位的 1就是 "十"；其他情况下就是 "一十"
                    if (!(n == 1 && (chinese.endsWith(nums[0]) | chinese.length() == 0) && j == l - 2)) {
                        chinese += nums[n];
                    }
                    chinese += digits[l - j - 1];
                }
            }

            // 每组最后加上一个单位: 万,亿 等
            if (i < k) {
                // 不是最高位的一组
                if (Integer.parseInt(s) > 0) {
                    // 如果所有 4 位不全是 0， 则加上单位： 万,亿 等
                    chinese += units[i - 1];
                }
            } else {
                // 处理最高位的一组,最后必须加上单位
                chinese += units[i - 1];
            }

            // 移动字符位置指针
            p += L;
        }
        return chinese;
    }

    /**
     * 将整数转换成汉字数字
     *
     * @param num 需要转换的 int 数字， >= 4 位数
     * @return 转换后的汉字
     */
    public static String formatInteger(int num) {
        // 以四位分割成组
        String format = new DecimalFormat("#,####").format(num);
        String[] split = format.split(",");
        int length = split.length;
        StringBuilder sb = new StringBuilder();
        int count = 0;
        // 分组转化
        for (int i = length - 1; i >= 0; i--) {
            split[i] = formatIntegerShort(split[i]) + (count == 0 ? "" : quot[count - 1]);
            count++;
        }
        // 分组转化后进行组合
        for (int i = 0; i < length; i++) {
            sb.append(split[i]);
        }
        return sb.toString();
    }

    /**
     * 将整数转换成汉字数字
     *
     * @param num 需要转换的 String 数字，<= 4 位数
     * @return 转换后的汉字
     */
    public static String formatIntegerShort(String num) {
        if (TextUtils.isEmpty(num)) return "";

        try {
            char[] val = num.toCharArray(); // 1004
            int len = val.length;
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < len; i++) {
                String m = val[i] + "";
                int n = Integer.valueOf(m);
                boolean isZero = n == 0;
                String unit = units[(len - 1) - i];
                if (isZero) {
                    if ('0' == val[i - 1]) {
                        continue;
                    } else {
                        sb.append(numArray[n]);
                    }
                } else {
                    sb.append(numArray[n]);
                    sb.append(unit);
                }
            }
            return sb.toString();
        } catch (NumberFormatException e) {
            Log.e("TAG", "formatInteger: " + e.getMessage());
        }
        return "";
    }

    /**
     * 将小数转换成汉字数字
     *
     * @param decimal 需要转换的数字：小数后保留 3 位
     * @return 转换后的汉字
     */
    public static String formatDecimal(double decimal) {
        try {
            // String decimals = String.valueOf(decimal);
            String decimals = new DecimalFormat("#.###").format(decimal);
            // 分开整数部分和小数部分
            int decIndex = decimals.indexOf(".");
            if (decIndex == -1) {
                // 小数点后等于 0 时，不再处理
                return formatInteger(Integer.valueOf(decimals));
            } else {
                int integ = Integer.valueOf(decimals.substring(0, decIndex));
                int dec = Integer.valueOf(decimals.substring(decIndex + 1));
                // 小数点后等于 0 时，不再处理
                String result = formatInteger(integ) + (dec > 0 ? "." + formatFractionalPart(dec) : "");
                return result;
            }
        } catch (NumberFormatException e) {
            Log.e("TAG", "formatDecimal: " + e.getMessage());
        }
        return "";
    }

    /**
     * 格式化小数部分的数字
     *
     * @param decimal 需要转换的数字
     * @return 转换后的汉字
     */
    public static String formatFractionalPart(int decimal) {
        try {
            char[] val = String.valueOf(decimal).toCharArray();
            int len = val.length;
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < len; i++) {
                int n = Integer.valueOf(val[i] + "");
                sb.append(numArray[n]);
            }
            return sb.toString();
        } catch (NumberFormatException e) {
            Log.e("TAG", "formatFractionalPart: " + e.getMessage());
        }
        return "";
    }
}
