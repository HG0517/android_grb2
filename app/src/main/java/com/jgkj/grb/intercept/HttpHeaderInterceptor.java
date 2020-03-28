package com.jgkj.grb.intercept;

import android.os.Build;
import android.support.annotation.NonNull;
import android.webkit.WebSettings;

import com.jgkj.grb.utils.Utils;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 接口拦截：添加公共头
 * Created by jugekeji on 2018/9/20.
 */

public class HttpHeaderInterceptor implements Interceptor {

    @NonNull
    @Override
    public Response intercept(@NonNull Chain chain) throws IOException {
        Request request = chain.request();

        // 修改信息：加入公共的 Header 信息
        Request.Builder newBuilder = request.newBuilder()
                .headers(request.headers())
                .removeHeader("User-Agent")
                .addHeader("User-Agent", UserAgent.getUserAgent());
        Request newRequest = newBuilder.build();

        // 执行修改后的请求

        return chain.proceed(newRequest);
    }

    public static class UserAgent {
        static String getUserAgent() {
            String userAgent = "";
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                try {
                    userAgent = WebSettings.getDefaultUserAgent(Utils.getApp());
                } catch (Exception e) {
                    userAgent = System.getProperty("http.agent");
                }
            } else {
                userAgent = System.getProperty("http.agent");
            }
            StringBuffer sb = new StringBuffer();
            for (int i = 0, length = userAgent.length(); i < length; i++) {
                char c = userAgent.charAt(i);
                if (c <= '\u001f' || c >= '\u007f') {
                    sb.append(String.format("\\u%04x", (int) c));
                } else {
                    sb.append(c);
                }
            }
            return sb.toString();
        }
    }
}
