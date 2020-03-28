package com.jgkj.grb.retrofit;

import com.google.gson.JsonParseException;
import com.jgkj.grb.utils.Logger;

import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.util.concurrent.TimeoutException;

import io.reactivex.observers.DisposableObserver;
import retrofit2.HttpException;

/**
 */
public abstract class ApiCallback<M> extends DisposableObserver<M> {

    public abstract void onSuccess(M model);

    public abstract void onFailure(String msg);

    public abstract void onFinish();


    @Override
    public void onError(Throwable e) {
        // e.printStackTrace();
        if (e instanceof SocketException) {
            onFailure("网络异常");
        } else if (e instanceof TimeoutException || e instanceof SocketTimeoutException) {
            onFailure("请求超时");
        } else if (e instanceof JsonParseException) {
            onFailure("数据解析失败");
        } else if (e instanceof HttpException) {
            HttpException httpException = (HttpException) e;
            //httpException.response().errorBody().string()
            int code = httpException.code();
            String msg = httpException.getMessage();
            Logger.i("TAG_ApiCallback", "code=" + code);
            if (code == 504) {
                msg = "网络不给力";
            }
            if (code == 502 || code == 404) {
                msg = "服务器异常，请稍后再试";
            }
            onFailure(msg);
        } else {
            onFailure(e.getMessage());
        }
        onFinish();
    }

    @Override
    public void onNext(M model) {
        onSuccess(model);
    }

    @Override
    public void onComplete() {
        onFinish();
    }
}
