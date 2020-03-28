package com.jgkj.grb.version;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatDialog;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.jgkj.grb.R;
import com.jgkj.grb.utils.ScreenUtils;

/**
 * author  : Administrator
 * e-mail  : brightpoplar@163.com
 * version :
 */

public class VersionUpdateDialog extends AppCompatDialog implements View.OnClickListener {
    private Context context;
    private View contentView;

    private TextView dialogTitle;
    private TextView dialogMsg;
    private FrameLayout dialogPositiveBtn;

    private OnBtnClickListener mOnBtnClickListener;

    private int width; // 左右边距
    private int height; // 高
    private boolean cancelable = true;


    public OnBtnClickListener getOnBtnClickListener() {
        return mOnBtnClickListener;
    }

    public void setOnBtnClickListener(OnBtnClickListener mOnBtnClickListener) {
        this.mOnBtnClickListener = mOnBtnClickListener;
    }

    public TextView getDialogTitle() {
        return dialogTitle;
    }

    public TextView getDialogMsg() {
        return dialogMsg;
    }

    public VersionUpdateDialog(Context context, int width, int height, boolean isMain) {
        super(context);
        this.context = context;
        this.width = width;
        this.height = height;
        this.cancelable = !isMain;
        setCancelable(cancelable);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        contentView = LayoutInflater.from(context).inflate(R.layout.layout_version_update_dialog, null);
        Window window = getWindow();
        window.setBackgroundDrawableResource(android.R.color.transparent);
        window.setGravity(Gravity.CENTER);
        window.getDecorView().setPadding(0, 0, 0, 0);
        window.setContentView(contentView);
        WindowManager.LayoutParams layoutParams = window.getAttributes();
        layoutParams.width = ScreenUtils.getScreenWidth((Activity) context) - ScreenUtils.dp2px(context, width) * 2;
        layoutParams.height = ScreenUtils.dp2px(context, height);
        window.setAttributes(layoutParams);

        initView(contentView);
    }

    private void initView(View contentView) {
        dialogTitle = contentView.findViewById(R.id.dialog_title);
        dialogMsg = contentView.findViewById(R.id.dialog_msg);
        dialogPositiveBtn = contentView.findViewById(R.id.dialog_sure);

        dialogPositiveBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.dialog_sure:
                if (null != mOnBtnClickListener) {
                    mOnBtnClickListener.onPositiveBtnClickListener();
                }
                break;
            default:
                break;
        }
    }

    public interface OnBtnClickListener {
        void onPositiveBtnClickListener();
    }
}
