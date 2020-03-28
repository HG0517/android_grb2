package com.jgkj.utils.token;

import android.content.Context;

/**
 * 获取加密 Token
 * Created by jugekeji on 2018/11/19.
 */
public class GetTokenUtils {

    public static String getToken(Context context, String url) {
        return getToken(context, url, null, null);
    }

    public static String getToken(Context context, String url, String username, String password) {
        String token = createTokenDouble(context, url, username, password);
        if (null == token)
            return null;

        return token.replace("\n", "").trim();
    }

    public static String getToken(Context context, String url, String endWith) {
        return getToken(context, url, null, null, endWith);
    }

    public static String getToken(Context context, String url, String username, String password, String endWith) {
        String token = getToken(context, url, username, password);
        if (null == token)
            return null;

        return token + (null == endWith ? "" : endWith);
    }

    private static String createToken(Context context, String url, String username, String password) {
        return CreateToken.getToken(context, url, username, password);
    }

    private static String createTokenDouble(Context context, String url, String username, String password) {
        return CreateToken.getTokenDouble(context, url, username, password);
    }

}
