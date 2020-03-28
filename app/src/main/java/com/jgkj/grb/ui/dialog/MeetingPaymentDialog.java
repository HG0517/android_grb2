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
import android.widget.CheckBox;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.jgkj.grb.R;
import com.jgkj.grb.onclick.RxView;
import com.jgkj.grb.utils.ScreenUtils;
import com.jgkj.grb.utils.ToastHelper;
import com.jgkj.utils.token.utils.sp.SharedPreferencesHelper;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.jgkj.grb.utils.ScreenUtils.dp2px;

/**
 * 会议报名：支付报名费
 * Created by jugekeji on 2019/10/30.
 */

public class MeetingPaymentDialog extends AlertDialog implements RxView.Action1 {

    private Context mContext;
    private int mDecorViewPadding = 0; // dp
    private int mSideMargin = 0; // 左右边距 dp
    private int height; // 高 dp

    private TextView mDialogTitle;
    private ImageView mDialogClose;
    private TextView mDialogTitle1;

    FrameLayout mPaymentBalance;
    TextView mPaymentBalanceTotal;
    CheckBox mPaymentBalanceCheck;

    FrameLayout mPaymentGRB;
    TextView mPaymentGRBTotal;
    CheckBox mPaymentGRBCheck;

    FrameLayout mPaymentWeiXin;
    CheckBox mPaymentWeiXinCheck;

    FrameLayout mPaymentAlipay;
    CheckBox mPaymentAlipayCheck;

    FrameLayout mPaymentCloudFlash;
    CheckBox mPaymentCloudFlashCheck;

    FrameLayout mBottomPanel;

    int paymentBalance = 0;
    int paymentGrb = 0;
    int paymentOther = 0;

    private OnActionClickListener mOnActionClickListener;

    public void setOnActionClickListener(OnActionClickListener mOnActionClickListener) {
        this.mOnActionClickListener = mOnActionClickListener;
    }

    public void setDialogTitle(@NotNull String mDialogTitle) {
        this.mDialogTitle.setText(mDialogTitle);
    }

    public void setDialogTitle1(@NotNull String mDialogTitle1) {
        this.mDialogTitle1.setText(mDialogTitle1);
    }

    public void setPaymentBalanceEnabled(boolean mPaymentBalance) {
        this.mPaymentBalance.setEnabled(mPaymentBalance);
    }

    public void setPaymentGRBEnabled(boolean mPaymentGRB) {
        this.mPaymentGRB.setEnabled(mPaymentGRB);
    }

    public MeetingPaymentDialog(@NonNull Context context) {
        super(context);
        this.mContext = context;
    }

    public MeetingPaymentDialog(@NonNull Context context, int sideMargin, int height) {
        super(context);
        this.mContext = context;
        this.mSideMargin = sideMargin;
        this.height = height;
    }

    public MeetingPaymentDialog(@NonNull Context context, int themeResId, int sideMargin, int height) {
        super(context, themeResId);
        this.mContext = context;
        this.mSideMargin = sideMargin;
        this.height = height;
    }

    public MeetingPaymentDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
        this.mContext = context;
    }

    public MeetingPaymentDialog(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        this.mContext = context;
    }

    public MeetingPaymentDialog(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener, int sideMargin, int height) {
        super(context, cancelable, cancelListener);
        this.mContext = context;
        this.mSideMargin = sideMargin;
        this.height = height;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        View contentView = LayoutInflater.from(mContext).inflate(R.layout.layout_dialog_meeting_payment, null);
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
        mDialogTitle1 = contentView.findViewById(R.id.dialog_title_1);

        mPaymentBalance = contentView.findViewById(R.id.payment_balance);
        mPaymentBalanceTotal = contentView.findViewById(R.id.payment_balance_total);
        mPaymentBalanceCheck = contentView.findViewById(R.id.payment_balance_check);
        mPaymentGRB = contentView.findViewById(R.id.payment_grb);
        mPaymentGRBTotal = contentView.findViewById(R.id.payment_grb_total);
        mPaymentGRBCheck = contentView.findViewById(R.id.payment_grb_check);
        mPaymentWeiXin = contentView.findViewById(R.id.payment_weixin);
        mPaymentWeiXinCheck = contentView.findViewById(R.id.payment_weixin_check);
        mPaymentAlipay = contentView.findViewById(R.id.payment_alipay);
        mPaymentAlipayCheck = contentView.findViewById(R.id.payment_alipay_check);
        mPaymentCloudFlash = contentView.findViewById(R.id.payment_cloud_flash);
        mPaymentCloudFlashCheck = contentView.findViewById(R.id.payment_cloud_flash_check);
        mBottomPanel = contentView.findViewById(R.id.bottomPanel);

        RxView.setOnClickListeners(this, mDialogClose, mBottomPanel, mPaymentBalance, mPaymentGRB, mPaymentWeiXin, mPaymentAlipay, mPaymentCloudFlash);

        try {
            SharedPreferencesHelper sharedPreferencesHelper = new SharedPreferencesHelper(mContext);
            JSONObject userModel = new JSONObject(sharedPreferencesHelper.getSharedPreference("user", "").toString());
            mPaymentBalanceTotal.setText(String.format(mContext.getString(R.string.payment_balance_total_text), userModel.optString("cash", "0")));
            mPaymentGRBTotal.setText(String.format(mContext.getString(R.string.payment_grb_total_text), userModel.optString("grb_cash", "0")));
        } catch (JSONException e) {
            mPaymentBalanceTotal.setText(String.format(mContext.getString(R.string.payment_balance_total_text), "0"));
            mPaymentGRBTotal.setText(String.format(mContext.getString(R.string.payment_grb_total_text), "0"));
        }
    }

    @Override
    public void onClick(Object o) {
        View v = (View) o;
        switch (v.getId()) {
            case R.id.payment_balance: // 余额支付 1
                mPaymentBalanceCheck.setChecked(!mPaymentBalanceCheck.isChecked());
                /*paymentBalance = mPaymentBalanceCheck.isChecked() ? 1 : 0;*/
                if (mPaymentBalanceCheck.isChecked()) {
                    paymentOther = 1;
                    mPaymentGRBCheck.setChecked(false);
                    mPaymentWeiXinCheck.setChecked(false);
                    mPaymentAlipayCheck.setChecked(false);
                    mPaymentCloudFlashCheck.setChecked(false);
                } else {
                    paymentOther = 0;
                }
                break;
            case R.id.payment_grb: // GRB支付 2
                mPaymentGRBCheck.setChecked(!mPaymentGRBCheck.isChecked());
                /*paymentGrb = mPaymentGRBCheck.isChecked() ? 2 : 0;*/
                if (mPaymentGRBCheck.isChecked()) {
                    paymentOther = 2;
                    mPaymentBalanceCheck.setChecked(false);
                    mPaymentWeiXinCheck.setChecked(false);
                    mPaymentAlipayCheck.setChecked(false);
                    mPaymentCloudFlashCheck.setChecked(false);
                } else {
                    paymentOther = 0;
                }
                break;
            case R.id.payment_weixin: // 微信支付 4
                mPaymentWeiXinCheck.setChecked(!mPaymentWeiXinCheck.isChecked());
                if (mPaymentWeiXinCheck.isChecked()) {
                    paymentOther = 4;
                    mPaymentBalanceCheck.setChecked(false);
                    mPaymentGRBCheck.setChecked(false);
                    mPaymentAlipayCheck.setChecked(false);
                    mPaymentCloudFlashCheck.setChecked(false);
                } else {
                    paymentOther = 0;
                }
                break;
            case R.id.payment_alipay: // 支付宝支付 3
                mPaymentAlipayCheck.setChecked(!mPaymentAlipayCheck.isChecked());
                if (mPaymentAlipayCheck.isChecked()) {
                    paymentOther = 3;
                    mPaymentBalanceCheck.setChecked(false);
                    mPaymentGRBCheck.setChecked(false);
                    mPaymentWeiXinCheck.setChecked(false);
                    mPaymentCloudFlashCheck.setChecked(false);
                } else {
                    paymentOther = 0;
                }
                break;
            case R.id.payment_cloud_flash: // 云闪付支付 5
                ToastHelper.showToast(getContext(), getContext().getString(R.string.not_yet_open_tip), 0);
                /*mPaymentCloudFlashCheck.setChecked(!mPaymentCloudFlashCheck.isChecked());
                if (mPaymentCloudFlashCheck.isChecked()) {
                    paymentOther = 5;
                    mPaymentBalanceCheck.setChecked(false);
                    mPaymentGRBCheck.setChecked(false);
                    mPaymentWeiXinCheck.setChecked(false);
                    mPaymentAlipayCheck.setChecked(false);
                } else {
                    paymentOther = 0;
                }*/
                break;
            case R.id.dialog_close:
                if (null != mOnActionClickListener) {
                    mOnActionClickListener.onCancel();
                }
                cancel();
                break;
            case R.id.bottomPanel:
                if (/*paymentBalance <= 0 && paymentGrb <= 0 && */paymentOther <= 0) {
                    ToastHelper.showToast(mContext, R.string.payment_order_error_payment, 0);
                    return;
                }
                if (null != mOnActionClickListener) {
                    ArrayList<Object> paymentType = new ArrayList<>();
                    /*if (paymentBalance > 0)
                        paymentType.add(paymentBalance);
                    if (paymentGrb > 0)
                        paymentType.add(paymentGrb);*/
                    if (paymentOther > 0)
                        paymentType.add(paymentOther);
                    mOnActionClickListener.onSure(paymentType);
                }
                cancel();
                break;
            default:
                break;
        }
    }

    public interface OnActionClickListener {
        void onCancel();

        void onSure(ArrayList<Object> paymentType);
    }
}
