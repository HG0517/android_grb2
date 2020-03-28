package com.jgkj.grb.ui.dialog;

import android.graphics.Bitmap;
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
import com.jgkj.grb.utils.ScreenUtils;
import com.yzq.zxinglibrary.encode.CodeCreator;

/**
 * 个人中心：GRB，钱包地址
 */
public class PersonalWalletAddressDialog extends DialogFragment {

    private int mType = 0;
    private String mAddress = "";
    private String mQrcode = "";
    private View mRootView;
    private boolean mIsDismiss = false;

    public static PersonalWalletAddressDialog newInstance(int type, String address, String qrcode) {
        PersonalWalletAddressDialog dialog = new PersonalWalletAddressDialog();
        // Supply as arguments.
        Bundle args = new Bundle();
        args.putInt("type", type);
        args.putString("address", address);
        args.putString("qrcode", qrcode);
        dialog.setArguments(args);
        return dialog;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle arguments = getArguments();
        if (null != arguments) {
            mType = arguments.getInt("type");
            mAddress = arguments.getString("address");
            mQrcode = arguments.getString("qrcode");
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        mRootView = inflater.inflate(R.layout.layout_personal_wallet_address_dialog, container, false);
        return mRootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ImageView close = view.findViewById(R.id.dialog_close);
        TextView title = view.findViewById(R.id.dialog_title);
        ImageView qrcode = view.findViewById(R.id.address_qrcode);
        TextView addressTv = view.findViewById(R.id.address_tv);
        CardView addressCopy = view.findViewById(R.id.address_copy);

        if (mType == 0) {
            title.setText(R.string.personal_wallet_address_tip_0);
        } else if (mType == 1) {
            title.setText(R.string.personal_wallet_address_tip_1);
        } else if (mType == 2) {
            title.setText(R.string.personal_wallet_address_tip_2);
        }
        addressTv.setText(mAddress);
        Bitmap qrCodeBm = CodeCreator.createQRCode(mQrcode, 300, 300, null);
        qrcode.setImageBitmap(qrCodeBm);

        close.setOnClickListener(v -> {
            dismiss();
        });
        addressCopy.setOnClickListener(v -> {
            ScreenUtils.copyContent(getContext(), mAddress);
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        Window window = getDialog().getWindow();
        WindowManager.LayoutParams params = window.getAttributes();

        params.gravity = Gravity.CENTER;
        params.width = ScreenUtils.getScreenWidth(getActivity()) - ScreenUtils.dp2px(getActivity(), 25 * 2);
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

        PersonalWalletAddressDialog.super.dismiss();
    }
}
