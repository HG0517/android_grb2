package com.jgkj.grb.intercept;

import com.jgkj.grb.utils.Logger;

import okhttp3.logging.HttpLoggingInterceptor;

/**
 * Log 输出
 * Created by jugekeji on 2018/11/30.
 */

public class HttpLoggerInterceptor implements HttpLoggingInterceptor.Logger {
    private static final String TAG = "TAG_" + HttpLoggerInterceptor.class.getSimpleName();

    @Override
    public void log(String message) {
        Logger.i(TAG, message); // okHttp 的详细日志会打印出来
    }
}
