package com.jgkj.grb.onclick;

import android.annotation.SuppressLint;
import android.os.Looper;
import android.support.annotation.CheckResult;
import android.support.annotation.NonNull;
import android.view.View;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.functions.Consumer;

/**
 * 监听onclick 事件防抖动：RxJava2 实现
 * Created by jugekeji on 2018/9/28.
 */

public class RxView {
    /**
     * 防止重复点击
     *
     * @param action 监听器：接口 Action1
     * @param target 目标 view
     */
    @SuppressLint("CheckResult")
    public static void setOnClickListeners(final Action1<View> action, @NonNull View... target) {
        for (View view : target) {
            RxView.onClick(view).throttleFirst(500, TimeUnit.MILLISECONDS).subscribe(new Consumer<View>() {
                @Override
                public void accept(@io.reactivex.annotations.NonNull View view) throws Exception {
                    action.onClick(view);
                }
            });
        }
    }

    /**
     * 监听onclick 事件防抖动
     *
     * @param view 目标 view
     * @return Observable
     */
    @CheckResult
    @NonNull
    private static Observable<View> onClick(@NonNull View view) {
        Preconditions.checkNotNull(view, "view == null");
        return Observable.create(new ViewClickOnSubscribe(view));
    }

    /**
     * onclick 事件防抖动
     */
    private static class ViewClickOnSubscribe implements ObservableOnSubscribe<View> {
        private View view;

        ViewClickOnSubscribe(View view) {
            this.view = view;
        }

        @Override
        public void subscribe(@io.reactivex.annotations.NonNull final ObservableEmitter<View> e) throws Exception {
            Preconditions.checkUiThread();

            View.OnClickListener listener = v -> {
                if (!e.isDisposed() && v.isEnabled()) {
                    e.onNext(view);
                }
            };
            view.setOnClickListener(listener);
        }
    }

    /**
     * 先决条件
     */
    public static class Preconditions {
        public static void checkArgument(boolean assertion, String message) {
            if (!assertion) {
                throw new IllegalArgumentException(message);
            }
        }

        static <T> T checkNotNull(T value, String message) {
            if (value == null) {
                throw new NullPointerException(message);
            }
            return value;
        }

        static void checkUiThread() {
            if (Looper.getMainLooper() != Looper.myLooper()) {
                throw new IllegalStateException("Must be called from the main thread. Was: " + Thread.currentThread());
            }
        }

        private Preconditions() {
            throw new AssertionError("No instances.");
        }
    }

    /**
     * A one-argument action. 点击事件转发接口，在需要的地方，实现该接口
     *
     * @param <T> the first argument type
     */
    public interface Action1<T> {
        void onClick(T t);
    }
}
