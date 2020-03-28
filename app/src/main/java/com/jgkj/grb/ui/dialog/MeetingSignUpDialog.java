package com.jgkj.grb.ui.dialog;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.jgkj.grb.R;
import com.jgkj.grb.onclick.RxView;
import com.jgkj.grb.utils.ScreenUtils;

import org.jetbrains.annotations.NotNull;

import static com.jgkj.grb.utils.ScreenUtils.dp2px;

/**
 * 会议报名
 * Created by jugekeji on 2019/8/8.
 */

public class MeetingSignUpDialog extends AlertDialog implements RxView.Action1 {

    private Context mContext;
    private int mDecorViewPadding = 0; // dp
    private int mSideMargin = 0; // 左右边距 dp
    private int height; // 高 dp

    private TextView mDialogTitle;
    private TextView mDialogTip2;
    private CardView mSureBtn;
    private OnActionClickListener mOnActionClickListener;

    public void setOnActionClickListener(OnActionClickListener mOnActionClickListener) {
        this.mOnActionClickListener = mOnActionClickListener;
    }

    public void setDialogTitle(@NotNull String mDialogTitle) {
        this.mDialogTitle.setText(mDialogTitle);
    }

    public void setDialogTip2(@NotNull String mDialogTip2) {
        this.mDialogTip2.setText(mDialogTip2);
    }

    public MeetingSignUpDialog(@NonNull Context context) {
        super(context);
        this.mContext = context;
    }

    public MeetingSignUpDialog(@NonNull Context context, int sideMargin, int height) {
        super(context);
        this.mContext = context;
        this.mSideMargin = sideMargin;
        this.height = height;
    }

    public MeetingSignUpDialog(@NonNull Context context, int themeResId, int sideMargin, int height) {
        super(context, themeResId);
        this.mContext = context;
        this.mSideMargin = sideMargin;
        this.height = height;
    }

    public MeetingSignUpDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
        this.mContext = context;
    }

    public MeetingSignUpDialog(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        this.mContext = context;
    }

    public MeetingSignUpDialog(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener, int sideMargin, int height) {
        super(context, cancelable, cancelListener);
        this.mContext = context;
        this.mSideMargin = sideMargin;
        this.height = height;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        View contentView = LayoutInflater.from(mContext).inflate(R.layout.layout_dialog_meeting_signup, null);
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
        mDialogTip2 = contentView.findViewById(R.id.dialog_tip_2);
        mSureBtn = contentView.findViewById(R.id.convention_signup_sure);

        RxView.setOnClickListeners(this, mSureBtn);
    }

    @Override
    public void onClick(Object o) {
        View v = (View) o;
        switch (v.getId()) {
            case R.id.convention_signup_sure:
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
