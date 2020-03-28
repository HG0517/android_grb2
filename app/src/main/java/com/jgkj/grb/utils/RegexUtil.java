package com.jgkj.grb.utils;

import android.text.TextUtils;

import java.util.regex.Pattern;

/**
 * 正则
 * Created by jugekeji on 2018/11/3.
 */

public class RegexUtil {

    // 地址码 | 出生日期码 | 顺序及性别码 校验和
    // 123456 |  12345678  |     123        X
    // 123456 |    345678  |      23        X
    private static final String REGEX_ID_CARD_NO = "(^[1-9]\\d{5}(18|19|20|([23]\\d))\\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\\d{3}[0-9Xx]$)|(^[1-9]\\d{5}\\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\\d{2}[0-9Xx]$)";

    private static final String REGEX_PHONE_NUM = "^((13[0-9])|(14[5,7])|(15[^4,\\D])|(17[0,1,3,5,6,7,8])|(18[0-9])|(19[9]))\\d{8}$";

    /**
     * 验证身份证号码
     *
     * @param idCard 身份证号码
     * @return 验证结果
     */
    public static boolean checkIDCard(String idCard) {
        if (TextUtils.isEmpty(idCard)) return false;

        return Pattern.matches(REGEX_ID_CARD_NO, idCard);
    }

    /**
     * 验证手机号码
     *
     * @param mobile 手机号码
     * @return 验证结果
     */
    public static boolean checkMobile(String mobile) {
        if (TextUtils.isEmpty(mobile)) return false;

        return Pattern.matches(REGEX_PHONE_NUM, mobile);
    }

}
