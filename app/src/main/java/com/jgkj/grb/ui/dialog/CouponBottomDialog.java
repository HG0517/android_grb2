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
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.jgkj.grb.R;
import com.jgkj.grb.itemdecoration.SpaceItemDecoration;
import com.jgkj.grb.ui.adapter.CouponAdapter;
import com.jgkj.grb.ui.mvp.product.ProductDetailsModel;
import com.jgkj.grb.ui.mvp.shopcart.CouponModel;
import com.jgkj.grb.utils.ScreenUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 购物车：领券列表
 * Created by brightpoplar@163.com on 2019/7/31.
 */
public class CouponBottomDialog extends DialogFragment {

    private String mTitle;
    private int mCancleResId;
    private View mRootView;
    private BottomDialogListener mListener;
    private boolean mIsDismiss = false;

    RecyclerView recyclerView;
    List<ProductDetailsModel.DataBean.CouponBean> mList;

    public static CouponBottomDialog newInstance(ArrayList<ProductDetailsModel.DataBean.CouponBean> list) {
        CouponBottomDialog dialog = new CouponBottomDialog();
        // Supply as arguments.
        Bundle args = new Bundle();
        args.putParcelableArrayList("list", list);
        dialog.setArguments(args);
        return dialog;
    }

    public static CouponBottomDialog newInstance(String title, int cancleResId, ArrayList<CouponModel.CouponBean> list) {
        CouponBottomDialog dialog = new CouponBottomDialog();
        // Supply as arguments.
        Bundle args = new Bundle();
        args.putString("title", title);
        args.putInt("cancle", cancleResId);
        args.putParcelableArrayList("list", list);
        dialog.setArguments(args);
        return dialog;
    }

    public RecyclerView getRecyclerView() {
        return recyclerView;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mTitle = getArguments().getString("title");
        mCancleResId = getArguments().getInt("cancle");
        mList = getArguments().getParcelableArrayList("list");
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        mRootView = inflater.inflate(R.layout.layout_coupon_bottom_dialog, container, false);
        return mRootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        TextView title = view.findViewById(R.id.dialog_title);
        ImageView close = view.findViewById(R.id.dialog_close);
        recyclerView = view.findViewById(R.id.recycler_view);

        if (!TextUtils.isEmpty(mTitle))
            title.setText(mTitle);
        if (mCancleResId != 0)
            close.setImageResource(mCancleResId);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.addItemDecoration(new SpaceItemDecoration(ScreenUtils.dp2px(getContext(), 10)));
        CouponAdapter adapter = new CouponAdapter(getContext(), mList);
        recyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener((view1, position) -> {
            if (mListener != null) {
                mListener.onItemClick(position);
            }
        });
        close.setOnClickListener(v -> {
            dismiss();
            if (mListener != null) {
                mListener.onConfirm();
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        Window window = getDialog().getWindow();
        WindowManager.LayoutParams params = window.getAttributes();

        params.gravity = Gravity.BOTTOM;
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.height = ScreenUtils.getScreenWidth((Activity) getContext());
        window.setAttributes(params);

        window.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#D9E5F9")));
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

        CouponBottomDialog.super.dismiss();
    }

    public interface BottomDialogListener {

        void onConfirm();

        void onItemClick(int position);
    }

    public void setOnBottomDialogListener(BottomDialogListener listener) {
        mListener = listener;
    }
}
