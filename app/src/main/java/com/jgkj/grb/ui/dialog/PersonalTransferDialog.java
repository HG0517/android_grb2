package com.jgkj.grb.ui.dialog;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
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
import com.jgkj.grb.utils.ScreenUtils;

/**
 * 个人中心：GRB，好友转让
 */
public class PersonalTransferDialog extends DialogFragment implements RxView.Action1 {

    private View mRootView;
    private OnActionClickListener mListener;
    private boolean mIsDismiss = false;

    private TextView personalTransferTip;
    private FrameLayout personalTransferCancel;
    private FrameLayout personalTransferConfirm;


    public static PersonalTransferDialog newInstance() {
        return new PersonalTransferDialog();
    }

    public void setPersonalTransferTip(String personalTransferTip) {
        this.personalTransferTip.setText(TextUtils.isEmpty(personalTransferTip) ? "" : personalTransferTip);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        mRootView = inflater.inflate(R.layout.layout_dialog_personal_transfer, container, false);
        return mRootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        personalTransferTip = view.findViewById(R.id.personal_transfer_tip);
        personalTransferCancel = view.findViewById(R.id.personal_transfer_cancel);
        personalTransferConfirm = view.findViewById(R.id.personal_transfer_confirm);

        RxView.setOnClickListeners(this, personalTransferCancel, personalTransferConfirm);
    }

    @Override
    public void onClick(Object o) {
        View v = (View) o;
        switch (v.getId()) {
            case R.id.personal_transfer_cancel:
                dismiss();
                if (mListener != null) {
                    mListener.onCancel();
                }
                break;
            case R.id.personal_transfer_confirm:
                dismiss();
                if (mListener != null) {
                    mListener.onConfirm();
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
        params.width = ScreenUtils.getScreenWidth((Activity) getContext()) - ScreenUtils.dp2px(getContext(), 40 * 2);
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

        PersonalTransferDialog.super.dismiss();
    }

    public interface OnActionClickListener {
        void onConfirm();

        void onCancel();
    }

    public void setOnActionClickListener(OnActionClickListener listener) {
        mListener = listener;
    }

}
