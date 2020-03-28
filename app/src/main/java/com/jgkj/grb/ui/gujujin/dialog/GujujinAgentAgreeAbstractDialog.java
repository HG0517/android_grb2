package com.jgkj.grb.ui.gujujin.dialog;

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
import android.text.method.LinkMovementMethod;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.jgkj.grb.R;
import com.jgkj.grb.onclick.RxView;
import com.jgkj.grb.ui.activity.ServiceHelpWebViewActivity;
import com.jgkj.grb.utils.ScreenUtils;
import com.jgkj.grb.utils.SpannableStringUtil;

/**
 * 谷聚金：代理协议书
 * Created by brightpoplar@163.com on 2019/9/25.
 */
public class GujujinAgentAgreeAbstractDialog extends DialogFragment implements RxView.Action1 {

    private View mRootView;
    private OnDialogListener mListener;
    private boolean mIsDismiss = false;

    private TextView dialogTitle;
    private TextView agreeAbstractContent;
    private TextView agreeAbstractTip;
    private FrameLayout dialogCancel;
    private FrameLayout dialogSure;

    public static GujujinAgentAgreeAbstractDialog newInstance() {
        return new GujujinAgentAgreeAbstractDialog();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        mRootView = inflater.inflate(R.layout.layout_dialog_gujujin_agent_agree_abstract, container, false);
        return mRootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        dialogTitle = view.findViewById(R.id.dialog_title);
        agreeAbstractContent = view.findViewById(R.id.agree_abstract_content);
        agreeAbstractTip = view.findViewById(R.id.agree_abstract_tip);
        dialogCancel = view.findViewById(R.id.dialog_cancel);
        dialogSure = view.findViewById(R.id.dialog_sure);

        RxView.setOnClickListeners(this, dialogCancel, dialogSure);

        String tip = getString(R.string.gujujin_agency_agreement);
        SpannableString spannableString = SpannableStringUtil.getSpannableString(String.format(getString(R.string.register_terms_of_service_abstract_text), tip),
                tip.length(), 1f, Color.parseColor("#FB355C"),
                v -> {
                    //TermsOfServiceActivity.start(getContext());
                    ServiceHelpWebViewActivity.start(getContext(), "23");
                });
        // 不设置点击不生效
        agreeAbstractTip.setMovementMethod(LinkMovementMethod.getInstance());
        agreeAbstractTip.setText(spannableString);
        // 去掉点击后文字的背景色
        // mRegisterTermsOfServiceTv.setHighlightColor(Color.TRANSPARENT);
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
        params.width = ScreenUtils.getScreenWidth(getActivity()) - ScreenUtils.dp2px(getActivity(), 40) * 2;
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

        GujujinAgentAgreeAbstractDialog.super.dismiss();
    }

    public interface OnDialogListener {
        void onCancel();

        void onSure();
    }

    public void setOnDialogListener(OnDialogListener listener) {
        mListener = listener;
    }

}
