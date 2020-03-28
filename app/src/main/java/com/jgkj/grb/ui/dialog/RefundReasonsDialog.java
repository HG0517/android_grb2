package com.jgkj.grb.ui.dialog;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.jgkj.grb.R;
import com.jgkj.grb.itemdecoration.SpaceItemDecoration;
import com.jgkj.grb.onclick.RxView;
import com.jgkj.grb.ui.adapter.RefundReasonsAdapter;
import com.jgkj.grb.ui.bean.RefundReasonsBean;
import com.jgkj.grb.utils.ScreenUtils;

import java.util.ArrayList;
import java.util.List;

import static com.jgkj.grb.utils.ScreenUtils.dp2px;

/**
 * 申请退款：退款原因
 * Created by jugekeji on 2019/8/22.
 */
public class RefundReasonsDialog extends AlertDialog implements RxView.Action1 {

    private Context mContext;
    private int mDecorViewPadding = 0; // dp
    private int mSideMargin = 0; // 左右边距 dp
    private int height; // 高 dp

    private TextView mDialogTitle;
    private ImageView mDialogClose;
    private RecyclerView mRecyclerView;
    private RefundReasonsAdapter mAdapter;
    private List<RefundReasonsBean> mList = new ArrayList<>();
    private OnActionClickListener mOnActionClickListener;

    private int selectPosition = -1;

    public void setOnActionClickListener(OnActionClickListener mOnActionClickListener) {
        this.mOnActionClickListener = mOnActionClickListener;
    }

    public void setDialogTitle(String mDialogTitle) {
        this.mDialogTitle.setText(TextUtils.isEmpty(mDialogTitle) ? "" : mDialogTitle);
    }

    public RefundReasonsDialog(@NonNull Context context) {
        super(context);
        this.mContext = context;
    }

    public RefundReasonsDialog(@NonNull Context context, int sideMargin, int height) {
        super(context);
        this.mContext = context;
        this.mSideMargin = sideMargin;
        this.height = height;
    }

    public RefundReasonsDialog(@NonNull Context context, int themeResId, int sideMargin, int height) {
        super(context, themeResId);
        this.mContext = context;
        this.mSideMargin = sideMargin;
        this.height = height;
    }

    public RefundReasonsDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
        this.mContext = context;
    }

    public RefundReasonsDialog(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        this.mContext = context;
    }

    public RefundReasonsDialog(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener, int sideMargin, int height) {
        super(context, cancelable, cancelListener);
        this.mContext = context;
        this.mSideMargin = sideMargin;
        this.height = height;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        View contentView = LayoutInflater.from(mContext).inflate(R.layout.layout_dialog_refund_reasons, null);
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
        mRecyclerView = contentView.findViewById(R.id.recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        mRecyclerView.addItemDecoration(new SpaceItemDecoration(dp2px(mContext, 1)));
        mAdapter = new RefundReasonsAdapter(mContext, mList);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener((view, position) -> {
            if (null != mOnActionClickListener) {
                mOnActionClickListener.onItemSelect(position);
            }
            cancel();
        });

        RxView.setOnClickListeners(this, mDialogClose);
    }

    public void setRecyclerViewDatas(List<RefundReasonsBean> list) {
        mList.clear();
        mList.addAll(list);
        notifyDataSetChanged();
    }

    public void notifyDataSetChanged() {
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onClick(Object o) {
        View v = (View) o;
        switch (v.getId()) {
            case R.id.dialog_close:
                if (null != mOnActionClickListener) {
                    mOnActionClickListener.onCancel();
                }
                cancel();
                break;
            default:
                break;
        }
    }

    public interface OnActionClickListener {
        void onCancel();

        void onItemSelect(int position);
    }
}
