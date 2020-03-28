package com.jgkj.grb.ui.gujujin.dialog;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.SpannableString;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.jgkj.grb.R;
import com.jgkj.grb.onclick.RxView;
import com.jgkj.grb.ui.activity.ServiceHelpWebViewActivity;
import com.jgkj.grb.utils.ScreenUtils;
import com.jgkj.grb.utils.SpannableStringUtil;

import org.jetbrains.annotations.NotNull;

/**
 * 谷聚金：线下支付提示
 * Created by brightpoplar@163.com on 2019/9/28.
 */
public class GujujinPaymentOfflineDialog extends DialogFragment implements RxView.Action1 {

    private View mRootView;
    private OnDialogListener mListener;
    private boolean mIsDismiss = false;

    private TextView dialogTitle;
    private TextView wechatAccount;
    private TextView alipayAccount;
    private TextView bankAccount;
    private TextView paymentTip;
    private FrameLayout dialogCancel;
    private FrameLayout dialogSure;

    String mPayTotal = "";
    String mWechatAccount = "";
    String mAlipayAccount = "";
    String mBankAccount = "";

    public static GujujinPaymentOfflineDialog newInstance(String payTotal, String wechatAccount, String alipayAccount, String bankAccount) {
        GujujinPaymentOfflineDialog gujujinPaymentOfflineDialog = new GujujinPaymentOfflineDialog();
        Bundle bundle = new Bundle();
        bundle.putString("payTotal", payTotal);
        bundle.putString("wechat", wechatAccount);
        bundle.putString("alipay", alipayAccount);
        bundle.putString("bank", bankAccount);
        gujujinPaymentOfflineDialog.setArguments(bundle);
        return gujujinPaymentOfflineDialog;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        mRootView = inflater.inflate(R.layout.layout_dialog_gujujin_payment_offline, container, false);
        return mRootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        dialogTitle = view.findViewById(R.id.dialog_title);
        wechatAccount = view.findViewById(R.id.wechat_account);
        alipayAccount = view.findViewById(R.id.alipay_account);
        bankAccount = view.findViewById(R.id.bank_account);
        paymentTip = view.findViewById(R.id.payment_tip);
        dialogCancel = view.findViewById(R.id.dialog_cancel);
        dialogSure = view.findViewById(R.id.dialog_sure);

        RxView.setOnClickListeners(this, dialogCancel, dialogSure);

        Bundle arguments = getArguments();
        if (null != arguments) {
            mPayTotal = arguments.getString("payTotal", "0");
            mWechatAccount = arguments.getString("wechat", "");
            mAlipayAccount = arguments.getString("alipay", "");
            mBankAccount = arguments.getString("bank", "");
        }

        String tip = "¥" + mPayTotal;
        String text = String.format(getString(R.string.gujujin_payment_offline_title), tip);
        SpannableString spannableString =
                SpannableStringUtil.getSpannableString(text, text.indexOf(tip), text.indexOf(tip) + tip.length(),
                        1f, Color.parseColor("#FF4141"), null);
        dialogTitle.setText(spannableString);
        wechatAccount.setText(mWechatAccount);
        alipayAccount.setText(mAlipayAccount);
        bankAccount.setText(mBankAccount);
    }

    @Override
    public void onClick(Object o) {
        View v = (View) o;
        switch (v.getId()) {
            case R.id.dialog_cancel:
                dismiss();
                if (mListener != null) {
                    mListener.onCancel();
                }
                break;
            case R.id.dialog_sure:
                dismiss();
                if (mListener != null) {
                    mListener.onSure();
                }
                break;
            default:
                break;
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        Window window = getDialog().getWindow();
        WindowManager.LayoutParams params = window.getAttributes();

        params.gravity = Gravity.CENTER;
        params.width = ScreenUtils.getScreenWidth(getActivity()) - ScreenUtils.dp2px(getActivity(), 20) * 2;
        window.setAttributes(params);

        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        window.getDecorView().setOnClickListener(v -> dismiss());
    }

    /**
     * 弹出对话框
     *
     * @param fragmentManager FragmentManager
     */
    public void showDialog(FragmentManager fragmentManager) {
        // DialogFragment.show() will take care of adding the fragment
        // in a transaction.  We also want to remove any currently showing
        // dialog, so make our own transaction and take care of that here.
        FragmentTransaction ft = fragmentManager.beginTransaction();
        Fragment prev = fragmentManager.findFragmentByTag(getTag());
        if (prev != null) {
            ft.remove(prev);
        }
        ft.addToBackStack(null);
        // Create and show the dialog.
        show(ft, getTag());
    }

    /**
     * 对话框消失
     */
    @Override
    public void dismiss() {
        if (mIsDismiss) {
            return;
        }
        mIsDismiss = true;

        GujujinPaymentOfflineDialog.super.dismiss();
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

    public interface OnDialogListener {
        void onCancel();

        void onSure();
    }

    public void setOnDialogListener(OnDialogListener listener) {
        mListener = listener;
    }

}
