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
import android.widget.FrameLayout;
import android.widget.TextView;

import com.jgkj.grb.R;
import com.jgkj.grb.onclick.RxView;
import com.jgkj.grb.utils.ScreenUtils;

import org.jetbrains.annotations.NotNull;

import static com.jgkj.grb.utils.ScreenUtils.dp2px;

/**
 * GRB抽奖
 * Created by jugekeji on 2019/8/8.
 */

public class LuckDrawDialog extends AlertDialog implements RxView.Action1 {

    private Context mContext;
    private int mDecorViewPadding = 0; // dp
    private int mSideMargin = 58; // 左右边距 dp
    private int height; // 高 dp

    private TextView mDialogTitle;
    private FrameLayout mCancelBtn;
    private TextView mCancelBtnTv;
    private FrameLayout mSureBtn;
    private TextView mSureBtnTv;
    private OnActionClickListener mOnActionClickListener;

    public void setOnActionClickListener(OnActionClickListener mOnActionClickListener) {
        this.mOnActionClickListener = mOnActionClickListener;
    }

    public void setDialogTitle(@NotNull String mDialogTitle) {
        this.mDialogTitle.setText(mDialogTitle);
    }

    public LuckDrawDialog(@NonNull Context context) {
        super(context);
        this.mContext = context;
    }

    public LuckDrawDialog(@NonNull Context context, int sideMargin, int height) {
        super(context);
        this.mContext = context;
        this.mSideMargin = sideMargin;
        this.height = height;
    }

    public LuckDrawDialog(@NonNull Context context, int themeResId, int sideMargin, int height) {
        super(context, themeResId);
        this.mContext = context;
        this.mSideMargin = sideMargin;
        this.height = height;
    }

    public LuckDrawDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
        this.mContext = context;
    }

    public LuckDrawDialog(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        this.mContext = context;
    }

    public LuckDrawDialog(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener, int sideMargin, int height) {
        super(context, cancelable, cancelListener);
        this.mContext = context;
        this.mSideMargin = sideMargin;
        this.height = height;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        View contentView = LayoutInflater.from(mContext).inflate(R.layout.layout_dialog_luck_draw, null);
        Window window = getWindow();

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
        mDialogTitle = contentView.findViewById(R.id.dialog_title);
        mCancelBtn = contentView.findViewById(R.id.dialog_btn_left);
        mCancelBtnTv = contentView.findViewById(R.id.dialog_btn_left_tv);
        mSureBtn = contentView.findViewById(R.id.dialog_btn_right);
        mSureBtnTv = contentView.findViewById(R.id.dialog_btn_right_tv);

        RxView.setOnClickListeners(this, mCancelBtn, mSureBtn);
    }

    @Override
    public void onClick(Object o) {
        View v = (View) o;
        switch (v.getId()) {
            case R.id.dialog_btn_left:
                if (null != mOnActionClickListener) {
                    mOnActionClickListener.onCancel();
                }
                cancel();
                break;
            case R.id.dialog_btn_right:
                if (null != mOnActionClickListener) {
                    mOnActionClickListener.onSure();
                }
                cancel();
                break;
        }
    }

    public interface OnActionClickListener {
        void onCancel();

        void onSure();
    }
}
