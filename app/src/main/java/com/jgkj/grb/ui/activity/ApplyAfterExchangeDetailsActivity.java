package com.jgkj.grb.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.jgkj.grb.R;
import com.jgkj.grb.base.BaseActivity;
import com.jgkj.grb.glide.GlideApp;
import com.jgkj.grb.retrofit.ApiCallback;
import com.jgkj.grb.retrofit.ApiStores;
import com.jgkj.grb.ui.mvp.ApplyAfterDetailModel;
import com.jgkj.utils.token.GetTokenUtils;
import com.jgkj.utils.token.utils.statusbar.StatusBarUtil;

import java.util.List;

import butterknife.BindView;

/**
 * 退款/售后：查看详情，换货
 */
public class ApplyAfterExchangeDetailsActivity extends BaseActivity {

    public static void start(Context context, String backId) {
        Intent intent = new Intent(context, ApplyAfterExchangeDetailsActivity.class);
        intent.putExtra("backId", backId);
        context.startActivity(intent);
    }

    @BindView(R.id.details_status)
    TextView mDetailsStatus;

    @BindView(R.id.details_status_speed_tv_0)
    TextView mDetailsStatusSpeedTv0;
    @BindView(R.id.details_status_speed_tv_1)
    TextView mDetailsStatusSpeedTv1;
    @BindView(R.id.details_status_speed_tv_2)
    TextView mDetailsStatusSpeedTv2;
    @BindView(R.id.details_status_speed_tv_3)
    TextView mDetailsStatusSpeedTv3;

    @BindView(R.id.details_status_speed_line_0)
    View mDetailsStatusSpeedLine0;
    @BindView(R.id.details_status_speed_line_1)
    View mDetailsStatusSpeedLine1;
    @BindView(R.id.details_status_speed_line_2)
    View mDetailsStatusSpeedLine2;

    @BindView(R.id.details_status_speed_iv_0)
    ImageView mDetailsStatusSpeedIv0;
    @BindView(R.id.details_status_speed_iv_1)
    ImageView mDetailsStatusSpeedIv1;
    @BindView(R.id.details_status_speed_iv_2)
    ImageView mDetailsStatusSpeedIv2;
    @BindView(R.id.details_status_speed_iv_3)
    ImageView mDetailsStatusSpeedIv3;

    @BindView(R.id.shop_iv)
    ImageView mDetailsShopIv;
    @BindView(R.id.shop_name)
    TextView mDetailsShopName;
    @BindView(R.id.total_top)
    TextView mDetailsTotalTop;
    @BindView(R.id.total_bottom)
    TextView mDetailsTotalBottom;

    @BindView(R.id.shop_specs)
    TextView mDetailsShopSpecs;
    @BindView(R.id.shop_specs_exchange)
    TextView mDetailsShopSpecsExchange;
    @BindView(R.id.total_num)
    TextView mDetailsShopNum;
    @BindView(R.id.refund_reasons)
    TextView mDetailsReasons;
    @BindView(R.id.after_sales_number)
    TextView mDetailsAfterSalesNumber;

    String mBackId = "";
    ApplyAfterDetailModel mApplyAfterDetailModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apply_after_exchange_details);

        //StatusBarUtil.setStatusBarDarkIconMode(this, false);
        StatusBarUtil.setStatusBar(this, Color.TRANSPARENT, 0, false);
        Toolbar toolbar = initToolBar("");
        setBackgroundColor(toolbar, Color.TRANSPARENT);
        toolbar.setNavigationIcon(R.mipmap.back_navi_icon_white);

        Intent intent = getIntent();
        if (!intent.hasExtra("backId")) {
            toastShow(R.string.open_activity_error_option);
            onBackPressed();
            return;
        }
        mBackId = intent.getStringExtra("backId");

        indexOrderBackdetail();
    }

    private void initGoodsView() {
        List<ApplyAfterDetailModel.DataBean.DetailBean> detail = mApplyAfterDetailModel.getData().getDetail();
        if (null != detail && detail.size() > 0) {
            ApplyAfterDetailModel.DataBean.DetailBean detailBean = detail.get(0);
            if (null != detailBean.getOr_pd_pic() && detailBean.getOr_pd_pic().size() > 0) {
                GlideApp.with(this)
                        .load(detailBean.getOr_pd_pic().get(0).startsWith("http:") || detailBean.getOr_pd_pic().get(0).startsWith("https:")
                                ? detailBean.getOr_pd_pic().get(0).replaceAll("\\\\", "/")
                                : ApiStores.API_SERVER_URL + detailBean.getOr_pd_pic().get(0).replaceAll("\\\\", "/"))
                        .centerCrop()
                        .into(mDetailsShopIv);
                mDetailsTotalTop.setText(String.format(getString(R.string.total_top_text), detailBean.getOr_pd_total()));
                //mDetailsTotalBottom.setText(String.format(getString(R.string.total_bottom_text), detailBean.getOr_pd_pv()));
            }
            mDetailsShopName.setText(detailBean.getOr_pd_name());
            mDetailsShopSpecs.setText(String.format(getString(R.string.apply_after_exchange_details_specs), detailBean.getPd_spec()));
            mDetailsShopSpecsExchange.setText(String.format(getString(R.string.apply_after_exchange_details_specs_ex), detailBean.getPd_spec()));
            mDetailsShopNum.setText(String.format(getString(R.string.apply_after_exchange_details_num), detailBean.getOr_pd_num()));
            mDetailsReasons.setText(String.format(getString(R.string.apply_after_exchange_details_reason_ex), mApplyAfterDetailModel.getData().getNote()));
            mDetailsAfterSalesNumber.setText(String.format(getString(R.string.apply_after_sales_number), mApplyAfterDetailModel.getData().getNum()));
        }
    }

    private void indexOrderBackdetail() {
        showProgressDialog();
        String token = GetTokenUtils.getToken(this, ApiStores.API_SERVER_ORDER_BACKDETAIL);
        addSubscription(apiStores().indexOrderBackdetail(token, mBackId), new ApiCallback<ApplyAfterDetailModel>() {
            @Override
            public void onSuccess(ApplyAfterDetailModel model) {
                mApplyAfterDetailModel = model;
                if (model.getCode() == 1) {
                    initStatusSpeed(model.getData().getStatus());
                    initGoodsView();
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

    private void initStatusSpeed(int statusSpeed) {
        // status 1 待审核 2 已审核 3 退款中 4 换货中 5 已完成 6 申请驳回
        switch (statusSpeed) {
            case 1:
                mDetailsStatus.setText(R.string.apply_after_exchange_details_status_1);
                mDetailsStatusSpeedTv0.setTextColor(Color.parseColor("#333333"));
                mDetailsStatusSpeedTv1.setTextColor(Color.parseColor("#666666"));
                mDetailsStatusSpeedTv2.setTextColor(Color.parseColor("#666666"));
                mDetailsStatusSpeedTv3.setTextColor(Color.parseColor("#666666"));
                mDetailsStatusSpeedIv0.setSelected(true);
                mDetailsStatusSpeedIv1.setSelected(false);
                mDetailsStatusSpeedIv2.setSelected(false);
                mDetailsStatusSpeedIv3.setSelected(false);
                mDetailsStatusSpeedLine0.setAlpha(0.3f);
                mDetailsStatusSpeedLine1.setAlpha(0.3f);
                mDetailsStatusSpeedLine2.setAlpha(0.3f);
                break;
            case 2:
                mDetailsStatus.setText(R.string.apply_after_exchange_details_status_2);
                mDetailsStatusSpeedTv0.setTextColor(Color.parseColor("#333333"));
                mDetailsStatusSpeedTv1.setTextColor(Color.parseColor("#333333"));
                mDetailsStatusSpeedTv2.setTextColor(Color.parseColor("#666666"));
                mDetailsStatusSpeedTv3.setTextColor(Color.parseColor("#666666"));
                mDetailsStatusSpeedIv0.setSelected(true);
                mDetailsStatusSpeedIv1.setSelected(true);
                mDetailsStatusSpeedIv2.setSelected(false);
                mDetailsStatusSpeedIv3.setSelected(false);
                mDetailsStatusSpeedLine0.setAlpha(1.0f);
                mDetailsStatusSpeedLine1.setAlpha(0.3f);
                mDetailsStatusSpeedLine2.setAlpha(0.3f);
                break;
            case 4:
                mDetailsStatus.setText(R.string.apply_after_exchange_details_status_4);
                mDetailsStatusSpeedTv0.setTextColor(Color.parseColor("#333333"));
                mDetailsStatusSpeedTv1.setTextColor(Color.parseColor("#333333"));
                mDetailsStatusSpeedTv2.setTextColor(Color.parseColor("#333333"));
                mDetailsStatusSpeedTv3.setTextColor(Color.parseColor("#666666"));
                mDetailsStatusSpeedIv0.setSelected(true);
                mDetailsStatusSpeedIv1.setSelected(true);
                mDetailsStatusSpeedIv2.setSelected(true);
                mDetailsStatusSpeedIv3.setSelected(false);
                mDetailsStatusSpeedLine0.setAlpha(1.0f);
                mDetailsStatusSpeedLine1.setAlpha(1.0f);
                mDetailsStatusSpeedLine2.setAlpha(0.3f);
                break;
            case 5:
                mDetailsStatus.setText(R.string.apply_after_exchange_details_status_5);
                mDetailsStatusSpeedTv0.setTextColor(Color.parseColor("#333333"));
                mDetailsStatusSpeedTv1.setTextColor(Color.parseColor("#333333"));
                mDetailsStatusSpeedTv2.setTextColor(Color.parseColor("#333333"));
                mDetailsStatusSpeedTv3.setTextColor(Color.parseColor("#333333"));
                mDetailsStatusSpeedIv0.setSelected(true);
                mDetailsStatusSpeedIv1.setSelected(true);
                mDetailsStatusSpeedIv2.setSelected(true);
                mDetailsStatusSpeedIv3.setSelected(true);
                mDetailsStatusSpeedLine0.setAlpha(1.0f);
                mDetailsStatusSpeedLine1.setAlpha(1.0f);
                mDetailsStatusSpeedLine2.setAlpha(1.0f);
                break;
            default:
                break;
        }
    }

}
