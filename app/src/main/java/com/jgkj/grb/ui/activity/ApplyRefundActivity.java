package com.jgkj.grb.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.jgkj.grb.R;
import com.jgkj.grb.base.BaseActivity;
import com.jgkj.grb.glide.GlideApp;
import com.jgkj.grb.onclick.RxView;
import com.jgkj.grb.retrofit.ApiCallback;
import com.jgkj.grb.retrofit.ApiStores;
import com.jgkj.grb.ui.bean.RefundReasonsBean;
import com.jgkj.grb.ui.dialog.RefundReasonsDialog;
import com.jgkj.grb.ui.mvp.OrderManagementModel;
import com.jgkj.utils.token.GetTokenUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 申请退款
 */
public class ApplyRefundActivity extends BaseActivity {

    public static void start(Context context, OrderManagementModel.DataBean.DetailBean data) {
        Intent intent = new Intent(context, ApplyRefundActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("data", data);
        intent.putExtra("bundle", bundle);
        context.startActivity(intent);
    }

    @BindView(R.id.shop_iv)
    ImageView mShopIv;
    @BindView(R.id.shop_name)
    TextView mShopName;

    @BindView(R.id.refund_reasons)
    FrameLayout mRefundReasons;
    @BindView(R.id.refund_reasons_tv)
    TextView mRefundReasonsTv;

    @BindView(R.id.total_top)
    TextView mRefundTotalTop;
    @BindView(R.id.total_bottom)
    TextView mRefundTotalBottom;

    @BindView(R.id.refund_note_et)
    EditText mRefundNoteEt;

    @BindView(R.id.refund_sure)
    FrameLayout mRefundSure;
    OrderManagementModel.DataBean.DetailBean mData;
    List<RefundReasonsBean> mList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apply_refund);

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
        toolbar.setTitle(R.string.activity_title_apply_refund);

        RxView.setOnClickListeners(this, mRefundReasons, mRefundSure);

        String[] refundReasons = getResources().getStringArray(R.array.refund_reasons);
        for (String refundReason : refundReasons) {
            mList.add(new RefundReasonsBean(refundReason));
        }

        initGoodsView();
    }

    private void initGoodsView() {
        if (!TextUtils.isEmpty(mData.getPd_head_pic()))
            GlideApp.with(this)
                    .load(mData.getPd_head_pic().startsWith("http:") || mData.getPd_head_pic().startsWith("https:")
                            ? mData.getPd_head_pic().replaceAll("\\\\", "/")
                            : ApiStores.API_SERVER_URL + mData.getPd_head_pic().replaceAll("\\\\", "/"))
                    .into(mShopIv);
        mShopName.setText(mData.getOr_pd_name());
        mRefundTotalTop.setText(String.format(getString(R.string.total_top_text), mData.getOr_pd_total()));
        // mRefundTotalBottom.setText(String.format(getString(R.string.total_bottom_text), mData.getOr_pd_pv()));
    }

    @Override
    public void onClick(Object o) {
        View v = (View) o;
        switch (v.getId()) {
            case R.id.refund_reasons:
                hideInputSoft();
                RefundReasonsDialog mDialog = new RefundReasonsDialog(this);
                mDialog.show();
                mDialog.setOnActionClickListener(new RefundReasonsDialog.OnActionClickListener() {
                    @Override
                    public void onCancel() {
                    }

                    @Override
                    public void onItemSelect(int position) {
                        for (int i = 0; i < mList.size(); i++) {
                            mList.get(i).setSelect(false);
                        }
                        mList.get(position).setSelect(true);
                        mDialog.notifyDataSetChanged();
                        mRefundReasonsTv.setText(mList.get(position).getReasons());
                    }
                });
                mDialog.setDialogTitle(getString(R.string.apply_after_refund_reason_choose_tip));
                mDialog.setRecyclerViewDatas(mList);
                break;
            case R.id.refund_sure:
                if (TextUtils.isEmpty(mRefundReasonsTv.getText().toString().trim())) {
                    toastShow(R.string.apply_after_refund_reason_tip);
                    return;
                }
                if (TextUtils.isEmpty(mRefundNoteEt.getText().toString().trim())) {
                    toastShow(R.string.apply_after_refund_note_tip);
                    return;
                }
                indexOrderBackgoods();
                break;
            default:
                break;
        }
    }

    /**
     * 申请退款：提交申请
     */
    private void indexOrderBackgoods() {
        showProgressDialog();
        String token = GetTokenUtils.getToken(this, ApiStores.API_SERVER_ORDER_BACKGOODS);
        addSubscription(apiStores().indexOrderBackgoods(token, mData.getOr_id(), mData.getId(),
                mRefundReasonsTv.getText().toString().trim(), mRefundNoteEt.getText().toString().trim(), 1),
                new ApiCallback<String>() {
                    @Override
                    public void onSuccess(String model) {
                        // {"code":1,"msg":"成功","time":1568171708,"data":"申请成功"}
                        try {
                            JSONObject result = new JSONObject(model);
                            if (result.getInt("code") == 1) {
                                ApplyResultActivity.start(mActivity, 0);
                                onBackPressed();
                            } else {
                                toastShow(result.getString("msg"));
                            }
                        } catch (JSONException e) {
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
