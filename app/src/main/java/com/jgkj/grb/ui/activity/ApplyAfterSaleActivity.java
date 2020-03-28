package com.jgkj.grb.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.jgkj.grb.R;
import com.jgkj.grb.base.BaseActivity;
import com.jgkj.grb.glide.GlideApp;
import com.jgkj.grb.onclick.RxView;
import com.jgkj.grb.retrofit.ApiStores;
import com.jgkj.grb.ui.mvp.OrderManagementModel;

import butterknife.BindView;

/**
 * 申请售后：申请方式 → 退货退款、换货
 */
public class ApplyAfterSaleActivity extends BaseActivity {

    public static void start(Context context, OrderManagementModel.DataBean.DetailBean data) {
        Intent intent = new Intent(context, ApplyAfterSaleActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("data", data);
        intent.putExtra("bundle", bundle);
        context.startActivity(intent);
    }

    @BindView(R.id.shop_iv)
    ImageView mShopIv;
    @BindView(R.id.shop_name)
    TextView mShopName;

    @BindView(R.id.apply_after_refund)
    FrameLayout mApplyAfterRefund;
    @BindView(R.id.apply_after_exchange)
    FrameLayout mApplyAfterExchange;

    OrderManagementModel.DataBean.DetailBean mData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apply_after_sale);

        Intent intent = getIntent();
        if (!intent.hasExtra("bundle")) {
            toastShow(R.string.open_activity_apply_after_error_option);
            onBackPressed();
            return;
        }
        Bundle bundle = intent.getBundleExtra("bundle");
        if (null == bundle) {
            toastShow(R.string.open_activity_apply_after_error_option);
            onBackPressed();
            return;
        }
        mData = (OrderManagementModel.DataBean.DetailBean) bundle.getSerializable("data");
        if (null == mData) {
            toastShow(R.string.open_activity_apply_after_error_option);
            onBackPressed();
            return;
        }

        Toolbar toolbar = initToolBar("");
        toolbar.setTitle(R.string.activity_title_apply_after_sale);

        RxView.setOnClickListeners(this, mApplyAfterRefund, mApplyAfterExchange);

        initGoodsView();
    }

    private void initGoodsView() {
        if (!TextUtils.isEmpty(mData.getPd_head_pic()))
            GlideApp.with(this)
                    .load(mData.getPd_head_pic().startsWith("http:") || mData.getPd_head_pic().startsWith("https:")
                            ? mData.getPd_head_pic().replaceAll("\\\\", "/")
                            : ApiStores.API_SERVER_URL + mData.getPd_head_pic().replaceAll("\\\\", "/"))
                    .centerCrop()
                    .into(mShopIv);
        mShopName.setText(mData.getOr_pd_name());
    }

    @Override
    public void onClick(Object o) {
        View v = (View) o;
        switch (v.getId()) {
            case R.id.apply_after_refund:
                ApplyAfterRefundActivity.start(mActivity, mData);
                onBackPressed();
                break;
            case R.id.apply_after_exchange:
                toastShow(R.string.open_activity_apply_after_exchange_);
                //ApplyAfterExchangeActivity.start(mActivity);
                //onBackPressed();
                break;
            default:
                break;
        }
    }
}
