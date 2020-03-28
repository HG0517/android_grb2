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
import android.support.v7.widget.CardView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.jgkj.grb.R;
import com.jgkj.grb.onclick.RxView;
import com.jgkj.grb.utils.ScreenUtils;

/**
 * 谷聚金：成为代理商，填写资料，性别
 * Created by brightpoplar@163.com on 2019/9/26.
 */
public class BecomeAgentGenderDialog extends DialogFragment implements RxView.Action1 {

    private View mRootView;
    private OnDialogListener mListener;
    private boolean mIsDismiss = false;

    private TextView dialogTitle;
    private TextView genderMale;
    private TextView genderFemale;
    private ImageView dialogCancel;
    private CardView dialogSure;

    private int gender = 0;

    public static BecomeAgentGenderDialog newInstance() {
        return new BecomeAgentGenderDialog();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        mRootView = inflater.inflate(R.layout.layout_dialog_become_agent_gender, container, false);
        return mRootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        dialogTitle = view.findViewById(R.id.dialog_title);
        genderMale = view.findViewById(R.id.gender_male);
        genderFemale = view.findViewById(R.id.gender_female);
        dialogCancel = view.findViewById(R.id.dialog_close);
        dialogSure = view.findViewById(R.id.submission);

        RxView.setOnClickListeners(this, genderMale, genderFemale, dialogCancel, dialogSure);

    }

    @Override
    public void onClick(Object o) {
        View v = (View) o;
        switch (v.getId()) {
            case R.id.gender_male:
                gender = 1;
                genderMale.setSelected(true);
                genderFemale.setSelected(false);
                break;
            case R.id.gender_female:
                gender = 2;
                genderMale.setSelected(false);
                genderFemale.setSelected(true);
                break;
            case R.id.dialog_close:
                dismiss();
                if (mListener != null) {
                    mListener.onCancel();
                }
                break;
            case R.id.submission:
                dismiss();
                if (mListener != null) {
                    mListener.onSure(gender);
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
        params.width = ScreenUtils.getScreenWidth(getActivity()) - ScreenUtils.dp2px(getActivity(), 20 * 2);
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

        BecomeAgentGenderDialog.super.dismiss();
    }

    public interface OnDialogListener {
        void onCancel();

        void onSure(int gender);
    }

    public void setOnDialogListener(OnDialogListener listener) {
        mListener = listener;
    }

}
