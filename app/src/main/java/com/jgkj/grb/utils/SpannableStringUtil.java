package com.jgkj.grb.utils;

import android.support.annotation.NonNull;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.view.View;

import org.jetbrains.annotations.NotNull;

/**
 * SpannableStringUtil
 * Created by jugekeji on 2018/10/9.
 */

public class SpannableStringUtil {

    /**
     * 从末位开始向前指定长度，设置颜色，大小，点击
     *
     * @param str          String
     * @param length       需要设置的字符长度
     * @param relativeSize 大小
     * @param colorInt     颜色
     * @return
     */
    public static SpannableString getSpannableString(String str, int length,
                                                     float relativeSize, final int colorInt,
                                                     View.OnClickListener onClickListener) {
        int start = str.length() - length;
        int end = str.length();
        SpannableString spStr = new SpannableString(str);
        spStr.setSpan(new RelativeSizeSpan(relativeSize), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        spStr.setSpan(new ForegroundColorSpan(colorInt), start, end, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        spStr.setSpan(new ClickableSpan() {
            @Override
            public void onClick(@NotNull View view) {
                if (null != onClickListener) {
                    onClickListener.onClick(view);
                }
            }

            @Override
            public void updateDrawState(@NonNull TextPaint paint) {
                // 设置下划线 true 显示、false 不显示
                paint.setUnderlineText(false);
                // 设置删除线 true 显示、false 不显示
                paint.setStrikeThruText(false);
            }
        }, start, end, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);

        return spStr;
    }

    /**
     * 指定开始结束位置，设置颜色，大小，点击
     *
     * @param str          String
     * @param start        需要设置的字符开始位
     * @param end          需要设置的字符结束位
     * @param relativeSize 大小
     * @param colorInt     颜色
     * @return
     */
    public static SpannableString getSpannableString(String str, int start, int end,
                                                     float relativeSize, final int colorInt,
                                                     View.OnClickListener onClickListener) {
        SpannableString spStr = new SpannableString(str);
        spStr.setSpan(new RelativeSizeSpan(relativeSize), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        spStr.setSpan(new ForegroundColorSpan(colorInt), start, end, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        spStr.setSpan(new ClickableSpan() {
            @Override
            public void onClick(@NotNull View view) {
                if (null != onClickListener) {
                    onClickListener.onClick(view);
                }
            }

            @Override
            public void updateDrawState(@NonNull TextPaint paint) {
                // 设置下划线 true 显示、false 不显示
                paint.setUnderlineText(false);
                // 设置删除线 true 显示、false 不显示
                paint.setStrikeThruText(false);
            }
        }, start, end, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);

        return spStr;
    }
}
