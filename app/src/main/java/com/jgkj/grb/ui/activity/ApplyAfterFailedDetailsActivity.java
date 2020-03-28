package com.jgkj.grb.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
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
import java.util.Locale;

import butterknife.BindView;

/**
 * 退款/售后：查看详情，售后申请审核未通过
 */
public class ApplyAfterFailedDetailsActivity extends BaseActivity {

    public static void start(Context context, String backId) {
        Intent intent = new Intent(context, ApplyAfterFailedDetailsActivity.class);
        intent.putExtra("backId", backId);
        context.startActivity(intent);
    }

    @BindView(R.id.details_status)
    TextView mDetailsStatus;
    @BindView(R.id.details_status_tip)
    TextView mDetailsStatusTip;

    @BindView(R.id.shop_iv)
    ImageView mDetailsShopIv;
    @BindView(R.id.shop_name)
    TextView mDetailsShopName;
    @BindView(R.id.shop_specs)
    TextView mDetailsShopSpecs;
    @BindView(R.id.total_num)
    TextView mDetailsShopNum;

    @BindView(R.id.refund_reasons)
    TextView mDetailsReasons;
    @BindView(R.id.after_sales_number)
    TextView mDetailsAfterSalesNumber;
    @BindView(R.id.after_sales_time)
    TextView mDetailsAfterSalesTime;
    @BindView(R.id.total_top)
    TextView mDetailsTotalTop;
    @BindView(R.id.total_bottom)
    TextView mDetailsTotalBottom;

    String mBackId = "";
    ApplyAfterDetailModel mApplyAfterDetailModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apply_after_failed_details);

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

    private void initDetailStatus() {
        // status 1 待审核 2 已审核 3 退款中 4 换货中 5 已完成 6 申请驳回
        if (mApplyAfterDetailModel.getData().getStatus() == 1) {
        } else if (mApplyAfterDetailModel.getData().getStatus() == 2) {
        } else if (mApplyAfterDetailModel.getData().getStatus() == 3) {
        } else if (mApplyAfterDetailModel.getData().getStatus() == 4) {
        } else if (mApplyAfterDetailModel.getData().getStatus() == 5) {
        } else if (mApplyAfterDetailModel.getData().getStatus() == 6) {
            mDetailsStatus.setText(R.string.apply_after_exchange_details_notpass);
            mDetailsStatusTip.setText(R.string.apply_after_exchange_details_notpass_tip);
            mDetailsAfterSalesTime.setText(String.format(getString(R.string.apply_after_exchange_details_audit_time), mApplyAfterDetailModel.getData().getAdd_time()));
        }
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
            mDetailsShopSpecs.setText(detailBean.getPd_spec());
            mDetailsShopNum.setText(String.format(Locale.getDefault(), "X %d", detailBean.getOr_pd_num()));
            mDetailsReasons.setText(String.format(getString(R.string.apply_after_exchange_details_reason), mApplyAfterDetailModel.getData().getNote()));
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
                    initDetailStatus();
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

}
