package com.jgkj.utils.token;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.util.Log;

import com.jgkj.utils.token.utils.des.AESEncode;
import com.jgkj.utils.token.utils.des.HmacMd5;

/**
 * 加密 Token
 * Created by jugekeji on 2017/9/13.
 */
public class CreateToken {
    /**
     * 生成加密 Token ，有 bearer 前缀
     *
     * @param url      接口 url
     * @param username 用户名
     * @param password 用户密码
     * @return 加密 Token
     */
    public static String getTokenDouble(Context context, String url, String username, String password) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        String userName = sharedPreferences.getString("username", "");
        String passwordMD5 = sharedPreferences.getString("password", "");
        if (TextUtils.isEmpty(password) && TextUtils.isEmpty(passwordMD5) && TextUtils.isEmpty(userName)) {
            return null;
        }

        long timeMillis = System.currentTimeMillis();
        String path = url + ":" + timeMillis;
        String aesEncode = null;
        if (!TextUtils.isEmpty(password)) {
            try {
                String hmacMd5 = HmacMd5.getMd5(password);
                if (TextUtils.isEmpty(hmacMd5))
                    return null;
                hmacMd5 = HmacMd5.getHmacMd5(hmacMd5.getBytes(), hmacMd5.getBytes());
                aesEncode = "bearer " + username + ":" + AESEncode.getAESEncode(hmacMd5.substring(0, 16), path);
                return aesEncode;
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            if (TextUtils.isEmpty(passwordMD5))
                return null;
            passwordMD5 = HmacMd5.getHmacMd5(passwordMD5.getBytes(), passwordMD5.getBytes());
            if (TextUtils.isEmpty(passwordMD5))
                return null;
            aesEncode = "bearer " + userName + ":" + AESEncode.getAESEncode(passwordMD5.substring(0, 16), path);
            return aesEncode;
        }
        return null;
    }

    public static String getToken(Context context, String url, String username, String password) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        String userName = sharedPreferences.getString("username", "");
        String passwordMD5 = sharedPreferences.getString("password", "");
        if (TextUtils.isEmpty(password) && TextUtils.isEmpty(passwordMD5) && TextUtils.isEmpty(userName)) {
            return null;
        }

        long timeMillis = System.currentTimeMillis();
        String path = url + ":" + timeMillis;
        String aesEncode = null;
        if (!TextUtils.isEmpty(password)) {
            try {
                String hmacMd5 = HmacMd5.getHmacMd5(password.getBytes(), password.getBytes());
                aesEncode = "bearer " + username + ":" + AESEncode.getAESEncode(hmacMd5.substring(0, 16), path);
                return aesEncode;
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            aesEncode = "bearer " + userName + ":" + AESEncode.getAESEncode(passwordMD5.substring(0, 16), path);
            return aesEncode;
        }
        return null;
    }
}
