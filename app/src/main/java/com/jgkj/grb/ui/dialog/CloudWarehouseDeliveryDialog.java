package com.jgkj.grb.ui.dialog;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.jgkj.grb.R;
import com.jgkj.grb.onclick.RxView;
import com.jgkj.grb.utils.ScreenUtils;

import org.jetbrains.annotations.NotNull;

import static com.jgkj.grb.utils.ScreenUtils.dp2px;

/**
 * 云仓库：提货数量
 * Created by jugekeji on 2019/8/8.
 */
public class CloudWarehouseDeliveryDialog extends AlertDialog implements RxView.Action1 {

    private Context mContext;
    private int mDecorViewPadding = 0; // dp
    private int mSideMargin = 25; // 左右边距 dp
    private int height; // 高 dp

    private TextView mDialogTitle;
    private EditText mCloudDeliveryNum;
    private FrameLayout mCloudDeliveryCut;
    private FrameLayout mCloudDeliveryAdd;
    private FrameLayout mCloudDeliveryCancel;
    private FrameLayout mCloudDeliverySure;
    private OnActionClickListener mOnActionClickListener;

    private int mNum = 1;
    private int mMaxNum = 1;

    public int getNum() {
        return mNum;
    }

    public void setNum(int mNum) {
        this.mNum = mNum;
    }

    public int getMaxNum() {
        return mMaxNum;
    }

    public void setMaxNum(int mMaxNum) {
        this.mMaxNum = mMaxNum;
    }

    public void setOnActionClickListener(OnActionClickListener mOnActionClickListener) {
        this.mOnActionClickListener = mOnActionClickListener;
    }

    public CloudWarehouseDeliveryDialog(@NonNull Context context) {
        super(context);
        this.mContext = context;
    }

    public CloudWarehouseDeliveryDialog(@NonNull Context context, int sideMargin, int height) {
        super(context);
        this.mContext = context;
        this.mSideMargin = sideMargin;
        this.height = height;
    }

    public CloudWarehouseDeliveryDialog(@NonNull Context context, int themeResId, int sideMargin, int height) {
        super(context, themeResId);
        this.mContext = context;
        this.mSideMargin = sideMargin;
        this.height = height;
    }

    public CloudWarehouseDeliveryDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
        this.mContext = context;
    }

    public CloudWarehouseDeliveryDialog(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        this.mContext = context;
    }

    public CloudWarehouseDeliveryDialog(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener, int sideMargin, int height) {
        super(context, cancelable, cancelListener);
        this.mContext = context;
        this.mSideMargin = sideMargin;
        this.height = height;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        View contentView = LayoutInflater.from(mContext).inflate(R.layout.layout_dialog_cloud_warehouse_delivery, null);
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
        mDialogTitle = contentView.findViewById(R.id.dialog_title);
        mCloudDeliveryNum = contentView.findViewById(R.id.cloud_delivery_num);
        mCloudDeliveryCut = contentView.findViewById(R.id.cloud_delivery_cut);
        mCloudDeliveryAdd = contentView.findViewById(R.id.cloud_delivery_add);
        mCloudDeliveryCancel = contentView.findViewById(R.id.cloud_delivery_cancel);
        mCloudDeliverySure = contentView.findViewById(R.id.cloud_delivery_sure);

        RxView.setOnClickListeners(this, mCloudDeliveryCut, mCloudDeliveryAdd, mCloudDeliveryCancel, mCloudDeliverySure);

        initShowNum();

        mCloudDeliveryNum.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (TextUtils.isEmpty(s.toString().trim())) {
                    mNum = 0;
                } else {
                    mNum = Integer.valueOf(s.toString().trim());
                    if (mNum < 1) {
                        mNum = 1;
                        mCloudDeliveryNum.setText(String.valueOf(mNum));
                    }
                    if (mNum > mMaxNum) {
                        mNum = mMaxNum;
                        mCloudDeliveryNum.setText(String.valueOf(mNum));
                    }
                }
                mCloudDeliveryCut.setEnabled(mNum > 1);
                mCloudDeliveryAdd.setEnabled(mNum < mMaxNum);
            }
        });
    }

    private void initShowNum() {
        mCloudDeliveryNum.setText(String.valueOf(mNum));
        mCloudDeliveryNum.setSelection(mCloudDeliveryNum.getText().toString().length());
        mCloudDeliveryCut.setEnabled(mNum > 1);
        mCloudDeliveryAdd.setEnabled(mNum < mMaxNum);
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
        //window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
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
            case R.id.cloud_delivery_cut:
                if (mNum > 1) {
                    --mNum;
                }
                initShowNum();
                break;
            case R.id.cloud_delivery_add:
                if (mNum < mMaxNum) {
                    ++mNum;
                }
                initShowNum();
                break;
            case R.id.cloud_delivery_cancel:
                if (null != mOnActionClickListener) {
                    mOnActionClickListener.onCancel();
                }
                // 隐藏输入法，关闭界面
                hideSoftInput(mCloudDeliveryNum);
                mDialogTitle.postDelayed(this::cancel, 300);
                break;
            case R.id.cloud_delivery_sure:
                if (null != mOnActionClickListener) {
                    mOnActionClickListener.onSure(mNum);
                }
                // 隐藏输入法，关闭界面
                hideSoftInput(mCloudDeliveryNum);
                mDialogTitle.postDelayed(this::cancel, 300);
                break;
            default:
                break;
        }
    }

    public interface OnActionClickListener {
        void onCancel();

        void onSure(int num);
    }
}
