package com.jgkj.grb.view.inputfilter;

import android.content.Context;
import android.text.InputFilter;
import android.text.Spanned;
import android.view.Gravity;
import android.widget.Toast;

import com.jgkj.grb.R;

/**
 * 中文算2个字符、英文和数字算1个字符：
 * Created by brightpoplar@163.com on 2019/8/10.
 */
public class MaxTextTwoLengthFilter implements InputFilter {
    private int mMaxLength;
    private Toast toast;

    public MaxTextTwoLengthFilter(Context context, int maxLen) {
        mMaxLength = maxLen;
        toast = Toast.makeText(context, R.string.nick_inpute_tip, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
    }

    @Override
    public CharSequence filter(CharSequence source, int start, int end,
                               Spanned dest, int dstart, int dend) {
        // 判断是否到达最大长度
        int count = 0;
        // 之前就存在的内容
        int dindex = 0;
        while (count <= mMaxLength && dindex < dest.length()) {
            char c = dest.charAt(dindex++);
            if (c < 128) {
                count = count + 1;
            } else {
                count = count + 2;
            }
        }
        if (count > mMaxLength) {
            toast.show();
            return dest.subSequence(0, dindex - 1);
        }
        // 从编辑框刚刚输入进去的内容
        int sindex = 0;
        while (count <= mMaxLength && sindex < source.length()) {
            char c = source.charAt(sindex++);
            if (c < 128) {
                count = count + 1;
            } else {
                count = count + 2;
            }
        }
        if (count > mMaxLength) {
            toast.show();
            sindex--;
        }
        return source.subSequence(0, sindex);
    }
}
