package com.jgkj.grb.view.inputfilter;

import android.content.Context;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.TextView;

/**
 * EditText 添加文字限制的时候使用此 TextWatcher
 * Created by brightpoplar@163.com on 2019/8/10.
 */
public class MaxEditTextWatcher implements TextWatcher {
    // 都算一位 或 中文算两位
    public static final int ALL_ONE = 0;
    public static final int CHINESE_TWO = 1;
    private int mType;

    // 最大位数
    private int mMaxLen;
    private Context mContext;
    private EditText etText;
    private TextView tvByteLimit;

    // 输入结果
    private TextChangedCallBack mCallBack;

    public MaxEditTextWatcher(Context context, int type, int maxlen, EditText editText) {
        this(context, type, maxlen, editText, null);
    }

    public MaxEditTextWatcher(Context context, int type, int maxLen, EditText editText, TextView textView) {
        this(context, type, maxLen, editText, textView, null);
    }

    public MaxEditTextWatcher(Context context, int type, int maxLen, EditText editText, TextView textView,
                              TextChangedCallBack callBack) {
        mType = type;
        mMaxLen = maxLen;
        mContext = context;
        etText = editText;
        tvByteLimit = textView;
        mCallBack = callBack;
        if (mType == ALL_ONE) {
            etText.setFilters(new InputFilter[]{new MaxTextNormalLengthFilter(mContext, mMaxLen)});
        } else if (mType == CHINESE_TWO) {
            etText.setFilters(new InputFilter[]{new MaxTextTwoLengthFilter(mContext, mMaxLen)});
        }
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
    }

    @Override
    public void afterTextChanged(Editable editable) {
        if (tvByteLimit != null) {
            if (mType == ALL_ONE) {
                int length = etText.getText().length();
                tvByteLimit.setText(length + "/" + mMaxLen);
            } else if (mType == CHINESE_TWO) {
                int length = judgeTextLength(etText.getText().toString());
                tvByteLimit.setText(length + "/" + mMaxLen);
            }
        }

        if (mCallBack != null) {
            mCallBack.changed(editable);
        }
    }

    /**
     * 返回text的长度，一个汉字算两个，数字和英文算一个
     *
     * @param text String
     * @return TextLength
     */
    private int judgeTextLength(String text) {
        if (TextUtils.isEmpty(text)) {
            return 0;
        }
        int count = 0;
        for (int i = 0; i < text.length(); i++) {
            char item = text.charAt(i);
            if (item < 128) {
                count++;
            } else {
                count += 2;
            }
        }
        return count;
    }

    public interface TextChangedCallBack {
        void changed(Editable editable);
    }
}