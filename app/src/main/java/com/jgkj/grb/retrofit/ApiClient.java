package com.jgkj.grb.retrofit;

import com.jgkj.grb.BuildConfig;
import com.jgkj.grb.intercept.HttpHeaderInterceptor;
import com.jgkj.grb.intercept.HttpLoggerInterceptor;
import com.zhy.http.okhttp.OkHttpUtils;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 *
 */
public class ApiClient {
    private static Retrofit mRetrofit;

    public static Retrofit retrofit() {
        if (mRetrofit == null) {
            OkHttpClient.Builder builder = new OkHttpClient.Builder()
                    .connectTimeout(30, TimeUnit.SECONDS)
                    .readTimeout(30, TimeUnit.SECONDS)
                    .writeTimeout(30, TimeUnit.SECONDS);
            // 公共头
            builder.addInterceptor(new HttpHeaderInterceptor());

            // Log 信息拦截器
            if (BuildConfig.DEBUG) {
                builder.addInterceptor(new HttpLoggingInterceptor(new HttpLoggerInterceptor()).setLevel(HttpLoggingInterceptor.Level.BODY));
            }

            OkHttpClient okHttpClient = builder.build();
            mRetrofit = new Retrofit.Builder()
                    .baseUrl(ApiStores.API_SERVER_URL)
                    .addConverterFactory(ScalarsConverterFactory.create()) // 基本数据类型 转换器
                    .addConverterFactory(GsonConverterFactory.create()) // Gson 数据类型 转换器
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .client(okHttpClient)
                    .build();

            OkHttpUtils.initClient(okHttpClient);
        }
        return mRetrofit;
    }

}
