package com.jgkj.grb.ui.dialog;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.jgkj.grb.R;
import com.jgkj.grb.utils.ScreenUtils;
import com.jgkj.grb.view.password.PasswordInputView;

import org.jetbrains.annotations.NotNull;

import static com.jgkj.grb.utils.ScreenUtils.dp2px;

/**
 * CameraAlbumDialog
 * Created by jugekeji on 2019/3/18.
 */

public class PaymentNowDialog extends AlertDialog implements PasswordInputView.OnFinishListener {

    private Context mContext;
    private int mDecorViewPadding = 25; // dp
    private int mSideMargin; // 左右边距 dp
    private int height; // 高 dp

    private ImageView mDialogClose;
    private TextView mDialogTitle;
    private PasswordInputView mPasswordInput;
    private View mPasswordInputHelp;
    private TextView mDialogTip;
    private OnActionClickListener mOnActionClickListener;

    public void setOnFinishListener(OnActionClickListener mOnActionClickListener) {
        this.mOnActionClickListener = mOnActionClickListener;
    }

    public PaymentNowDialog(@NonNull Context context) {
        super(context);
        this.mContext = context;
    }

    public PaymentNowDialog(@NonNull Context context, int sideMargin, int height) {
        super(context);
        this.mContext = context;
        this.mSideMargin = sideMargin;
        this.height = height;
    }

    public PaymentNowDialog(@NonNull Context context, int themeResId, int sideMargin, int height) {
        super(context, themeResId);
        this.mContext = context;
        this.mSideMargin = sideMargin;
        this.height = height;
    }

    public PaymentNowDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
        this.mContext = context;
    }

    public PaymentNowDialog(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        this.mContext = context;
    }

    public PaymentNowDialog(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener, int sideMargin, int height) {
        super(context, cancelable, cancelListener);
        this.mContext = context;
        this.mSideMargin = sideMargin;
        this.height = height;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        View contentView = LayoutInflater.from(mContext).inflate(R.layout.layout_password_view, null);
        Window window = getWindow();

        // 显示输入法
        showSoftInput(window);

        window.setBackgroundDrawableResource(android.R.color.transparent);
        window.setGravity(Gravity.CENTER);
        window.getDecorView().setPadding(dp2px(mContext, mDecorViewPadding), 0, dp2px(mContext, mDecorViewPadding), 0);
        window.setContentView(contentView);
        WindowManager.LayoutParams layoutParams = window.getAttributes();
        layoutParams.width = 0 == mSideMargin ? WindowManager.LayoutParams.MATCH_PARENT : ScreenUtils.getScreenWidth((Activity) mContext) - dp2px(mContext, mSideMargin) * 2;
        layoutParams.height = 0 == height ? WindowManager.LayoutParams.WRAP_CONTENT : dp2px(mContext, height);
        window.setAttributes(layoutParams);

        initView(contentView);
    }

    private void initView(View contentView) {
        mDialogClose = contentView.findViewById(R.id.dialog_close);
        mDialogTitle = contentView.findViewById(R.id.dialog_title);
        mPasswordInput = contentView.findViewById(R.id.password_input);
        mPasswordInputHelp = contentView.findViewById(R.id.password_input_help);
        mDialogTip = contentView.findViewById(R.id.dialog_tip);

        mPasswordInput.setOnFinishListener(this);
        mDialogClose.setOnClickListener(v -> {
            if (null != mOnActionClickListener) {
                mOnActionClickListener.onClose();
            }
            // 隐藏输入法，关闭界面
            hideSoftInput(mPasswordInput);
            mPasswordInput.postDelayed(this::cancel, 300);
        });

        mPasswordInputHelp.setOnClickListener(v -> {
            mPasswordInput.requestFocus();
            // 显示输入法
            showSoftInput(mPasswordInput);
        });
    }

    /**
     * 显示输入法
     *
     * @param window window
     */
    private void showSoftInput(@NotNull Window window) {
        // 清除 flags，获取焦点
        window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE |
                WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
        // 弹出输入法
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
    }

    /**
     * 显示输入法
     *
     * @param editText editText
     */
    private void showSoftInput(@NotNull EditText editText) {
        InputMethodManager inputMethodManager = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.showSoftInput(editText, InputMethodManager.SHOW_IMPLICIT);
    }

    /**
     * 隐藏输入法
     *
     * @param editText editText
     */
    private void hideSoftInput(@NotNull EditText editText) {
        InputMethodManager inputMethodManager = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(editText.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
    }

    /**
     * 输入完成：每次输入都会调用，可根据输入数据，判断是否完成
     */
    @Override
    public void setOnPasswordFinished() {
        if (null != mOnActionClickListener
                && mPasswordInput.getOriginText().length() == mPasswordInput.getMaxPasswordLength()) {

            // 隐藏输入法，关闭界面
            hideSoftInput(mPasswordInput);
            mPasswordInput.postDelayed(() -> {
                mOnActionClickListener.onInputFinish(mPasswordInput.getOriginText());
                cancel();
            }, 300);
        }
    }

    public interface OnActionClickListener {
        void onClose();

        void onInputFinish(String password);
    }
}
