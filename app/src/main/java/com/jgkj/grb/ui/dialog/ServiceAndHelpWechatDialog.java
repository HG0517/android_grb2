package com.jgkj.grb.ui.dialog;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.jgkj.grb.R;
import com.jgkj.grb.glide.GlideApp;
import com.jgkj.grb.retrofit.ApiStores;
import com.jgkj.grb.utils.ScreenUtils;

import org.jetbrains.annotations.NotNull;

import static com.jgkj.grb.utils.ScreenUtils.dp2px;

/**
 * 客服与帮助：微信
 * Created by jugekeji on 2019/10/30.
 */

public class ServiceAndHelpWechatDialog extends AlertDialog implements View.OnLongClickListener {

    private Context mContext;
    private int mDecorViewPadding = 30; // dp
    private int mSideMargin = 0; // 左右边距 dp
    private int height; // 高 dp

    private TextView mDialogTitle;
    private TextView mDialogTitle1;
    private ImageView mDialogIv;
    private View.OnLongClickListener mOnLongClickListener;

    public void setDialogTitle(@NotNull String mDialogTitle) {
        this.mDialogTitle.setText(mDialogTitle);
    }

    public void setDialogTitle1(@NotNull String mDialogTitle1) {
        this.mDialogTitle1.setText(mDialogTitle1);
    }

    public void setDialogIv(@NotNull String mDialogIv) {
        if (TextUtils.isEmpty(mDialogIv)) {
            this.mDialogIv.setOnLongClickListener(null);
        } else {
            this.mDialogIv.setOnLongClickListener(this);
        }
        GlideApp.with(mContext)
                .load(TextUtils.isEmpty(mDialogIv) ? null :
                        (mDialogIv.startsWith("http:") || mDialogIv.startsWith("https:") ? mDialogIv : ApiStores.API_SERVER_URL + mDialogIv))
                .fallback(new ColorDrawable(Color.GRAY))
                .fitCenter()
                .into(this.mDialogIv);
    }

    public void setOnLongClickListener(View.OnLongClickListener mOnLongClickListener) {
        this.mOnLongClickListener = mOnLongClickListener;
    }

    public ServiceAndHelpWechatDialog(@NonNull Context context) {
        super(context);
        this.mContext = context;
    }

    public ServiceAndHelpWechatDialog(@NonNull Context context, int sideMargin, int height) {
        super(context);
        this.mContext = context;
        this.mSideMargin = sideMargin;
        this.height = height;
    }

    public ServiceAndHelpWechatDialog(@NonNull Context context, int themeResId, int sideMargin, int height) {
        super(context, themeResId);
        this.mContext = context;
        this.mSideMargin = sideMargin;
        this.height = height;
    }

    public ServiceAndHelpWechatDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
        this.mContext = context;
    }

    public ServiceAndHelpWechatDialog(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        this.mContext = context;
    }

    public ServiceAndHelpWechatDialog(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener, int sideMargin, int height) {
        super(context, cancelable, cancelListener);
        this.mContext = context;
        this.mSideMargin = sideMargin;
        this.height = height;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        View contentView = LayoutInflater.from(mContext).inflate(R.layout.layout_dialog_service_wechat, null);
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
        mDialogTitle1 = contentView.findViewById(R.id.dialog_title_1);
        mDialogIv = contentView.findViewById(R.id.service_wechat_iv);
    }

    @Override
    public boolean onLongClick(View v) {
        switch (v.getId()) {
            case R.id.service_wechat_iv:
                if (null != mOnLongClickListener) {
                    mOnLongClickListener.onLongClick(v);
                }
                return true;
            default:
                return false;
        }
    }
}
