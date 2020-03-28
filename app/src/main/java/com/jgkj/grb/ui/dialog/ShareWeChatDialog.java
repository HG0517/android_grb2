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
import android.widget.ImageView;
import android.widget.TextView;

import com.jgkj.grb.R;
import com.jgkj.grb.utils.ScreenUtils;

import static com.jgkj.grb.utils.ScreenUtils.dp2px;

/**
 * 分享到微信平台
 * Created by jugekeji on 2019/8/8.
 */

public class ShareWeChatDialog extends AlertDialog implements View.OnClickListener {

    private Context mContext;
    private int mDecorViewPadding = 0; // dp
    private int mSideMargin; // 左右边距 dp
    private int height; // 高 dp

    private TextView mDialogTitle;
    private ImageView mDialogClose;
    private FrameLayout mShareWechatCircle;
    private FrameLayout mShareWechat;
    private OnActionClickListener mOnActionClickListener;

    public void setOnActionClickListener(OnActionClickListener mOnActionClickListener) {
        this.mOnActionClickListener = mOnActionClickListener;
    }

    public ShareWeChatDialog(@NonNull Context context) {
        super(context);
        this.mContext = context;
    }

    public ShareWeChatDialog(@NonNull Context context, int sideMargin, int height) {
        super(context);
        this.mContext = context;
        this.mSideMargin = sideMargin;
        this.height = height;
    }

    public ShareWeChatDialog(@NonNull Context context, int themeResId, int sideMargin, int height) {
        super(context, themeResId);
        this.mContext = context;
        this.mSideMargin = sideMargin;
        this.height = height;
    }

    public ShareWeChatDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
        this.mContext = context;
    }

    public ShareWeChatDialog(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        this.mContext = context;
    }

    public ShareWeChatDialog(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener, int sideMargin, int height) {
        super(context, cancelable, cancelListener);
        this.mContext = context;
        this.mSideMargin = sideMargin;
        this.height = height;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        View contentView = LayoutInflater.from(mContext).inflate(R.layout.layout_dialog_share_wechat, null);
        Window window = getWindow();

        window.setBackgroundDrawableResource(android.R.color.transparent);
        window.setGravity(Gravity.BOTTOM);
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
        mDialogClose = contentView.findViewById(R.id.dialog_close);
        mShareWechatCircle = contentView.findViewById(R.id.share_wechat_circle);
        mShareWechat = contentView.findViewById(R.id.share_wechat);

        mDialogClose.setOnClickListener(this);
        mShareWechatCircle.setOnClickListener(this);
        mShareWechat.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.dialog_close:
                if (null != mOnActionClickListener) {
                    mOnActionClickListener.onCancel();
                }
                cancel();
                break;
            case R.id.share_wechat_circle:
                if (null != mOnActionClickListener) {
                    mOnActionClickListener.onShareWeChatCircle();
                }
                cancel();
                break;
            case R.id.share_wechat:
                if (null != mOnActionClickListener) {
                    mOnActionClickListener.onShareWeChat();
                }
                cancel();
                break;
        }
    }

    public interface OnActionClickListener {
        void onCancel();

        void onShareWeChatCircle();

        void onShareWeChat();
    }
}
