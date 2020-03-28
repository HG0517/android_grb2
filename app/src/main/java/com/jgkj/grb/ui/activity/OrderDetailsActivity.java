package com.jgkj.grb.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jgkj.grb.R;
import com.jgkj.grb.base.BaseActivity;
import com.jgkj.grb.itemdecoration.GridDividerItemDecoration;
import com.jgkj.grb.itemdecoration.SpaceItemDecoration;
import com.jgkj.grb.onclick.RxView;
import com.jgkj.grb.retrofit.ApiCallback;
import com.jgkj.grb.retrofit.ApiStores;
import com.jgkj.grb.ui.adapter.IndexChildContentAdapter;
import com.jgkj.grb.ui.adapter.SettlementAdapter;
import com.jgkj.grb.ui.mvp.OrderDetailsModel;
import com.jgkj.grb.utils.ScreenUtils;
import com.jgkj.utils.token.GetTokenUtils;
import com.jgkj.utils.token.utils.statusbar.StatusBarUtil;

import butterknife.BindView;

/**
 * 订单详情
 */
public class OrderDetailsActivity extends BaseActivity {

    public static void start(Context context, String orId) {
        Intent intent = new Intent(context, OrderDetailsActivity.class);
        intent.putExtra("orId", orId);
        context.startActivity(intent);
    }

    @BindView(R.id.details_status)
    TextView mDetailsStatus;
    @BindView(R.id.details_status_tip)
    TextView mDetailsStatusTip;
    @BindView(R.id.details_status_iv)
    ImageView mDetailsStatusIv;

    @BindView(R.id.details_address_username)
    TextView mDetailsAddressUsername;
    @BindView(R.id.details_address_userphone)
    TextView mDetailsAddressUserphone;
    @BindView(R.id.details_address_detail)
    TextView mDetailsAddressDetail;

    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;
    @BindView(R.id.details_freight)
    TextView mDetailsFreight;
    @BindView(R.id.details_order_remark_tv)
    TextView mDetailsOrderRemarkTv;
    @BindView(R.id.details_order_num_tv)
    TextView mDetailsOrderNumTv;
    @BindView(R.id.details_order_num_copy)
    TextView mDetailsOrderNumCopy;
    @BindView(R.id.details_order_time_tv)
    TextView mDetailsOrderTimeTv;
    @BindView(R.id.details_order_deliverer_tv)
    TextView mDetailsOrderDelivererTv;
    @BindView(R.id.details_order_time_delivery_tv)
    TextView mDetailsOrderTimeDeliveryTv;
    @BindView(R.id.details_order_time_completion_tv)
    TextView mDetailsOrderTimeCompletionTv;

    @BindView(R.id.bottomPanel)
    LinearLayout mBottomPanel;
    @BindView(R.id.recycler_view_hot)
    RecyclerView mRecyclerViewHot;

    OrderDetailsModel mOrderDetailsModel;
    SettlementAdapter mAdapterShop;
    IndexChildContentAdapter mAdapterHot;

    String mOrId = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_details);

        Intent intent = getIntent();
        if (intent.hasExtra("orId")) {
            mOrId = intent.getStringExtra("orId");
        }
        if (TextUtils.isEmpty(mOrId)) {
            toastShow(R.string.open_activity_error_option);
            onBackPressed();
            return;
        }

        //StatusBarUtil.setStatusBarDarkIconMode(this, false);
        StatusBarUtil.setStatusBar(this, Color.TRANSPARENT, 0, false);
        Toolbar toolbar = initToolBar("");
        setBackgroundColor(toolbar, Color.TRANSPARENT);
        toolbar.setNavigationIcon(R.mipmap.back_navi_icon_white);
        RxView.setOnClickListeners(this, mDetailsOrderNumCopy);

        onLazyLoad();
    }

    private void onLazyLoad() {
        showProgressDialog();
        String token = GetTokenUtils.getToken(this, ApiStores.API_SERVER_ORDER_ORDERDETAIL);
        addSubscription(apiStores().indexOrderDetail(token, mOrId), new ApiCallback<OrderDetailsModel>() {
            @Override
            public void onSuccess(OrderDetailsModel model) {
                mOrderDetailsModel = model;
                if (model.getCode() == 1) {
                    initOrderStatus();
                    initOrderAddress();
                    initRecyclerViewShop();
                    initRecyclerViewHot();
                } else {
                    toastShow(model.getMsg());
                }
            }

            @Override
            public void onFailure(String msg) {
                toastShow(msg);
            }

            @Override
            public void onFinish() {
                dismissProgressDialog();
            }
        });
    }

    /**
     * 收货地址
     */
    private void initOrderAddress() {
        mDetailsAddressUsername.setText(mOrderDetailsModel.getData().getTake_name());
        mDetailsAddressUserphone.setText(mOrderDetailsModel.getData().getTake_tel());
        mDetailsAddressDetail.setText(mOrderDetailsModel.getData().getTake_addr());
    }

    /**
     * 订单状态：or_status   0 待付款  3 待发货 4 待收货  5 待评价
     */
    private void initOrderStatus() {
        if (null != mOrderDetailsModel) {
            if (mOrderDetailsModel.getData().getOr_status() == 0) {
                mDetailsStatus.setText(R.string.order_details_status_0);
                mDetailsStatusTip.setText("");
                mDetailsStatusIv.setImageResource(R.mipmap.ic_order_details_0);
            } else if (mOrderDetailsModel.getData().getOr_status() == 3) {
                mDetailsStatus.setText(R.string.order_details_status_3);
                mDetailsStatusTip.setText(R.string.order_details_status_31);
                mDetailsStatusIv.setImageResource(R.mipmap.ic_order_details_1);
            } else if (mOrderDetailsModel.getData().getOr_status() == 4) {
                mDetailsStatus.setText(R.string.order_details_status_4);
                mDetailsStatusTip.setText(R.string.order_details_status_41);
                mDetailsStatusIv.setImageResource(R.mipmap.ic_order_details_2);

                mDetailsOrderTimeDeliveryTv.setText(String.format(getString(R.string.order_details_delivery_time), mOrderDetailsModel.getData().getDeliver_time()));
                mDetailsOrderTimeDeliveryTv.setVisibility(View.VISIBLE);
            } else if (mOrderDetailsModel.getData().getOr_status() == 5) {
                mDetailsStatus.setText(R.string.order_details_status_5);
                mDetailsStatusTip.setText(R.string.order_details_status_51);
                mDetailsStatusIv.setImageResource(R.mipmap.ic_order_details_3);

                mDetailsOrderTimeCompletionTv.setText(String.format(getString(R.string.apply_refund_details_completion_time), mOrderDetailsModel.getData().getOr_finish_time()));
                mDetailsOrderTimeCompletionTv.setVisibility(View.VISIBLE);
            }

            mDetailsFreight.setText(String.format(getString(R.string.total_top_text), String.valueOf(mOrderDetailsModel.getData().getFee())));
            mDetailsOrderRemarkTv.setText(String.format(getString(R.string.order_details_order_remark), mOrderDetailsModel.getData().getOr_remark()));
            mDetailsOrderNumTv.setText(String.format(getString(R.string.order_view_num), mOrderDetailsModel.getData().getOr_num()));
            mDetailsOrderTimeTv.setText(String.format(getString(R.string.order_details_order_time), mOrderDetailsModel.getData().getOr_add_time()));
            if (mOrderDetailsModel.getData().getType() == 4 && mOrderDetailsModel.getData().getType3() > 0) {
                mDetailsOrderDelivererTv.setVisibility(View.VISIBLE);
                mDetailsOrderDelivererTv.setText(String.format(getString(R.string.order_details_order_deliverer),
                        mOrderDetailsModel.getData().getType3() == 2 ? getString(R.string.order_details_order_deliverer_2) : getString(R.string.order_details_order_deliverer_1)));
            }
        }
    }

    private void initRecyclerViewShop() {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.addItemDecoration(new SpaceItemDecoration(dp2px(1)));
        mAdapterShop = new SettlementAdapter(this, mOrderDetailsModel.getData().getList());
        mRecyclerView.setAdapter(mAdapterShop);
    }

    private void initRecyclerViewHot() {
        mRecyclerViewHot.setNestedScrollingEnabled(false);
        mRecyclerViewHot.setLayoutManager(new GridLayoutManager(mActivity, 2, GridLayoutManager.VERTICAL, false));
        mRecyclerViewHot.addItemDecoration(new GridDividerItemDecoration(dp2px(7), Color.TRANSPARENT));
        mAdapterHot = new IndexChildContentAdapter(mActivity, mOrderDetailsModel.getData().getHots());
        mRecyclerViewHot.setAdapter(mAdapterHot);
        mAdapterHot.setOnItemClickListener((view, position) -> {
            ProductDetailsActivity.start(mActivity, String.valueOf(mOrderDetailsModel.getData().getHots().get(position).getId()));
        });
    }

    @Override
    public void onClick(Object o) {
        View v = (View) o;
        switch (v.getId()) {
            case R.id.details_order_num_copy:
                ScreenUtils.copyContent(this, mOrderDetailsModel.getData().getOr_num());
                toastShow("已复制");
                break;
            default:
                break;
        }
    }
}
