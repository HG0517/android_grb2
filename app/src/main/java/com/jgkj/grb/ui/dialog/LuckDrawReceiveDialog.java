package com.jgkj.grb.ui.dialog;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.jgkj.grb.R;
import com.jgkj.grb.onclick.RxView;
import com.jgkj.grb.utils.ScreenUtils;
import com.jgkj.grb.utils.ToastHelper;

import org.jetbrains.annotations.NotNull;

import static com.jgkj.grb.utils.ScreenUtils.dp2px;

/**
 * GRB抽奖：我的奖品，领取
 * Created by jugekeji on 2019/8/8.
 */

public class LuckDrawReceiveDialog extends AlertDialog implements RxView.Action1 {

    private Context mContext;
    private int mDecorViewPadding = 0; // dp
    private int mSideMargin = 25; // 左右边距 dp
    private int height; // 高 dp

    private ImageView mDialogClose;
    private TextView mDialogTitle;
    private EditText mUserNameEt;
    private EditText mUserPhoneEt;
    private EditText mUserAddressEt;
    private TextView mUserAddressTip;
    private CardView mUserAddressSure;
    private OnActionClickListener mOnActionClickListener;

    public void setOnActionClickListener(OnActionClickListener mOnActionClickListener) {
        this.mOnActionClickListener = mOnActionClickListener;
    }

    public LuckDrawReceiveDialog(@NonNull Context context) {
        super(context);
        this.mContext = context;
    }

    public LuckDrawReceiveDialog(@NonNull Context context, int sideMargin, int height) {
        super(context);
        this.mContext = context;
        this.mSideMargin = sideMargin;
        this.height = height;
    }

    public LuckDrawReceiveDialog(@NonNull Context context, int themeResId, int sideMargin, int height) {
        super(context, themeResId);
        this.mContext = context;
        this.mSideMargin = sideMargin;
        this.height = height;
    }

    public LuckDrawReceiveDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
        this.mContext = context;
    }

    public LuckDrawReceiveDialog(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        this.mContext = context;
    }

    public LuckDrawReceiveDialog(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener, int sideMargin, int height) {
        super(context, cancelable, cancelListener);
        this.mContext = context;
        this.mSideMargin = sideMargin;
        this.height = height;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        View contentView = LayoutInflater.from(mContext).inflate(R.layout.layout_dialog_luck_draw_receive, null);
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
        mUserNameEt = contentView.findViewById(R.id.username_et);
        mUserPhoneEt = contentView.findViewById(R.id.userphone_et);
        mUserAddressEt = contentView.findViewById(R.id.useraddress_et);
        mUserAddressTip = contentView.findViewById(R.id.useraddress_tip);
        mUserAddressSure = contentView.findViewById(R.id.useraddress_sure);

        RxView.setOnClickListeners(this, mDialogClose, mUserAddressSure);
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

    @Override
    public void onClick(Object o) {
        View v = (View) o;
        switch (v.getId()) {
            case R.id.dialog_close:
                if (null != mOnActionClickListener) {
                    mOnActionClickListener.onCancel();
                }
                break;
            case R.id.useraddress_sure:
                if (null != mOnActionClickListener) {
                    if (TextUtils.isEmpty(mUserNameEt.getText().toString())) {
                        ToastHelper.showToast(mContext, R.string.luck_draw_mine_receive_dialog_name_tip, Toast.LENGTH_SHORT);
                        return;
                    }
                    if (TextUtils.isEmpty(mUserPhoneEt.getText().toString())) {
                        ToastHelper.showToast(mContext, R.string.password_phone_number_tip, Toast.LENGTH_SHORT);
                        return;
                    }
                    if (TextUtils.isEmpty(mUserAddressEt.getText().toString())) {
                        ToastHelper.showToast(mContext, R.string.luck_draw_mine_receive_dialog_addr_tip_1, Toast.LENGTH_SHORT);
                        return;
                    }
                    mOnActionClickListener.onSure(mUserNameEt.getText().toString(), mUserPhoneEt.getText().toString(), mUserAddressEt.getText().toString());
                }
                break;
            default:
                break;
        }
        // 隐藏输入法，关闭界面
        hideSoftInput(mUserNameEt);
        mUserNameEt.postDelayed(this::cancel, 300);
    }

    public interface OnActionClickListener {
        void onCancel();

        void onSure(String name, String phone, String address);
    }
}
