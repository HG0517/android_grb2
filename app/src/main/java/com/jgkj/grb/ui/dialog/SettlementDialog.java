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
import android.widget.ImageView;
import android.widget.TextView;

import com.jgkj.grb.R;
import com.jgkj.grb.onclick.RxView;
import com.jgkj.grb.utils.ScreenUtils;

import static com.jgkj.grb.utils.ScreenUtils.dp2px;

/**
 * 结算：地址运费确认
 */

public class SettlementDialog extends AlertDialog implements RxView.Action1 {

    private Context mContext;
    private int mDecorViewPadding = 15; // dp
    private int mSideMargin; // 左右边距 dp
    private int height; // 高 dp

    private ImageView mDialogClose;
    private TextView mDialogTitle;
    private TextView mDialogSubtitle0;
    private TextView mUsername;
    private TextView mUserPhone;
    private TextView mAddressDetail;
    private TextView mDialogSubtitle1;
    private TextView mFreight;
    private TextView mTotalFreight;
    private TextView mConfirmationAddressDesc;
    private CardView mConfirmationAction;
    private TextView mConfirmationAddressTv;

    private int mNeedShowFreight = 0;

    private OnActionClickListener mOnActionClickListener;

    public void setOnActionClickListener(OnActionClickListener mOnActionClickListener) {
        this.mOnActionClickListener = mOnActionClickListener;
    }

    public int getmNeedShowFreight() {
        return mNeedShowFreight;
    }

    public void setmNeedShowFreight(int mNeedShowFreight) {
        this.mNeedShowFreight = mNeedShowFreight;
        initShowFreight(mNeedShowFreight);
    }

    private void initShowFreight(int needShowFreight) {
        mDialogSubtitle1.setVisibility(needShowFreight == 2 ? View.GONE : View.VISIBLE);
        mFreight.setVisibility(needShowFreight == 2 ? View.GONE : View.VISIBLE);
        mTotalFreight.setVisibility(needShowFreight == 2 ? View.GONE : View.VISIBLE);
    }

    public void setUsername(String mUsername) {
        this.mUsername.setText(mUsername);
    }

    public void setUserPhone(String mUserPhone) {
        this.mUserPhone.setText(mUserPhone);
    }

    public void setAddressDetail(String mAddressDetail) {
        this.mAddressDetail.setText(mAddressDetail);
    }

    public void setFreight(String mFreight) {
        this.mFreight.setText(String.format(mContext.getString(R.string.settlement_dialog_subtitle_2_text), mFreight));
    }

    public void setTotalFreight(String mTotalFreight) {
        this.mTotalFreight.setText(String.format(mContext.getResources().getString(R.string.total_top_text), mTotalFreight));
    }

    public SettlementDialog(@NonNull Context context) {
        super(context);
        this.mContext = context;
    }

    public SettlementDialog(@NonNull Context context, int sideMargin, int height) {
        super(context);
        this.mContext = context;
        this.mSideMargin = sideMargin;
        this.height = height;
    }

    public SettlementDialog(@NonNull Context context, int themeResId, int sideMargin, int height) {
        super(context, themeResId);
        this.mContext = context;
        this.mSideMargin = sideMargin;
        this.height = height;
    }

    public SettlementDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
        this.mContext = context;
    }

    public SettlementDialog(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        this.mContext = context;
    }

    public SettlementDialog(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener, int sideMargin, int height) {
        super(context, cancelable, cancelListener);
        this.mContext = context;
        this.mSideMargin = sideMargin;
        this.height = height;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        View contentView = LayoutInflater.from(mContext).inflate(R.layout.layout_dialog_settlement, null);
        Window window = getWindow();
        window.setBackgroundDrawableResource(android.R.color.transparent);
        window.setGravity(Gravity.CENTER);
        window.getDecorView().setPadding(dp2px(mContext, mDecorViewPadding), 0, dp2px(mContext, mDecorViewPadding), dp2px(mContext, mDecorViewPadding));
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
        mDialogSubtitle0 = contentView.findViewById(R.id.dialog_subtitle_0);
        mUsername = contentView.findViewById(R.id.username);
        mUserPhone = contentView.findViewById(R.id.user_phone);
        mAddressDetail = contentView.findViewById(R.id.address_detail);
        mDialogSubtitle1 = contentView.findViewById(R.id.dialog_subtitle_1);
        mFreight = contentView.findViewById(R.id.freight);
        mTotalFreight = contentView.findViewById(R.id.total_freight);
        mConfirmationAddressDesc = contentView.findViewById(R.id.confirmation_address_desc);
        mConfirmationAction = contentView.findViewById(R.id.confirmation_action);
        mConfirmationAddressTv = contentView.findViewById(R.id.confirmation_address_tv);

        RxView.setOnClickListeners(this, mDialogClose, mConfirmationAction);

        initShowFreight(mNeedShowFreight);
    }

    @Override
    public void onClick(Object o) {
        View v = (View) o;
        switch (v.getId()) {
            case R.id.dialog_close:
                cancel();
                break;
            case R.id.confirmation_action:
                if (null != mOnActionClickListener) {
                    mOnActionClickListener.onConfirmationClick();
                }
                cancel();
                break;
            default:
                cancel();
                break;
        }
    }

    public interface OnActionClickListener {
        void onConfirmationClick();
    }
}
