package com.jgkj.grb.ui.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.CheckBox;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.jgkj.grb.R;
import com.jgkj.grb.base.BaseActivity;
import com.jgkj.grb.eventbus.RefreshUserInfo;
import com.jgkj.grb.glide.GlideApp;
import com.jgkj.grb.onclick.RxView;
import com.jgkj.grb.retrofit.ApiCallback;
import com.jgkj.grb.retrofit.ApiStores;
import com.jgkj.grb.ui.dialog.PaymentNowDialog;
import com.jgkj.grb.ui.dialog.SettlementDialog;
import com.jgkj.grb.ui.mvp.OrderManagementModel;
import com.jgkj.grb.ui.mvp.personal.AddresManagementModel;
import com.jgkj.grb.utils.Logger;
import com.jgkj.utils.token.GetTokenUtils;
import com.xgr.easypay.callback.IPayCallback;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;

/**
 * 支付订单：云仓库，提货
 */
public class PaymentOrdersCloudActivity extends BaseActivity {

    public static void start(Context context, String cId, OrderManagementModel.DataBean.DetailBean data) {
        Intent intent = new Intent(context, PaymentOrdersCloudActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("cId", cId);
        bundle.putSerializable("data", data);
        intent.putExtra("bundle", bundle);
        context.startActivity(intent);
    }

    @BindView(R.id.select_address)
    FrameLayout mSelectAddress;
    @BindView(R.id.select_address_tip)
    TextView mSelectAddressTip;
    @BindView(R.id.select_address_content)
    RelativeLayout mSelectAddressContent;
    @BindView(R.id.username)
    TextView mSelectAddressUsername;
    @BindView(R.id.user_phone)
    TextView mSelectAddressUserPhone;
    @BindView(R.id.select_address_detail)
    TextView mSelectAddressDetail;

    @BindView(R.id.order_info_iv)
    ImageView mOrderInfoIv;
    @BindView(R.id.order_info_name)
    TextView mOrderInfoName;
    @BindView(R.id.order_info_num)
    TextView mOrderInfoNum;
    @BindView(R.id.order_info_freight)
    TextView mOrderInfoFreight;

    @BindView(R.id.payment_weixin)
    FrameLayout mPaymentWeiXin;
    @BindView(R.id.payment_weixin_check)
    CheckBox mPaymentWeiXinCheck;

    @BindView(R.id.payment_alipay)
    FrameLayout mPaymentAlipay;
    @BindView(R.id.payment_alipay_check)
    CheckBox mPaymentAlipayCheck;

    @BindView(R.id.payment_cloud_flash)
    FrameLayout mPaymentCloudFlash;
    @BindView(R.id.payment_cloud_flash_check)
    CheckBox mPaymentCloudFlashCheck;

    @BindView(R.id.bottomPanel)
    FrameLayout mBottomPanel;

    String mCid = "";
    OrderManagementModel.DataBean.DetailBean mData;
    AddresManagementModel.DataBean mAddress;
    int paymentOther = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_orders_cloud);

        Toolbar toolbar = initToolBar(getString(R.string.activity_title_payment_orders));

        Intent intent = getIntent();
        if (intent.hasExtra("bundle")) {
            Bundle bundle = intent.getBundleExtra("bundle");
            mCid = bundle.getString("cId", "");
            mData = (OrderManagementModel.DataBean.DetailBean) bundle.getSerializable("data");
        }
        if (null == mData) {
            toastShow(R.string.open_activity_error_option);
            return;
        }

        RxView.setOnClickListeners(this, mSelectAddress, mPaymentWeiXin, mPaymentAlipay, mPaymentCloudFlash, mBottomPanel);
        initGoods();
        initAddress();
    }

    private void initGoods() {
        if (null != mData.getOr_pd_pic() && mData.getOr_pd_pic().size() > 0) {
            if (!TextUtils.isEmpty(mData.getOr_pd_pic().get(0))) {
                GlideApp.with(this)
                        .load(mData.getOr_pd_pic().get(0).startsWith("http:") || mData.getOr_pd_pic().get(0).startsWith("https:")
                                ? mData.getOr_pd_pic().get(0).replaceAll("\\\\", "/")
                                : ApiStores.API_SERVER_URL + mData.getOr_pd_pic().get(0).replaceAll("\\\\", "/"))
                        .centerCrop()
                        .into(mOrderInfoIv);
            }
        }
        mOrderInfoName.setText(mData.getOr_pd_name());
        mOrderInfoNum.setText(String.valueOf(mData.getOr_pd_num()));
        mOrderInfoFreight.setText(String.format(getString(R.string.total_top_text), null == mAddress ? "0" : String.valueOf(mAddress.getFee())));
    }

    @Override
    public void onClick(Object o) {
        View v = (View) o;
        switch (v.getId()) {
            case R.id.select_address:
                AddressManagementActivity.startActivityForResult(this);
                break;
            case R.id.payment_weixin: // 微信支付 4
                //toastShow(R.string.not_yet_open_tip);
                mPaymentWeiXinCheck.setChecked(!mPaymentWeiXinCheck.isChecked());
                if (mPaymentWeiXinCheck.isChecked()) {
                    paymentOther = 4;
                    mPaymentAlipayCheck.setChecked(false);
                    mPaymentCloudFlashCheck.setChecked(false);
                } else {
                    paymentOther = 0;
                }
                break;
            case R.id.payment_alipay: // 支付宝支付 3
                //toastShow(R.string.not_yet_open_tip);
                mPaymentAlipayCheck.setChecked(!mPaymentAlipayCheck.isChecked());
                if (mPaymentAlipayCheck.isChecked()) {
                    paymentOther = 3;
                    mPaymentWeiXinCheck.setChecked(false);
                    mPaymentCloudFlashCheck.setChecked(false);
                } else {
                    paymentOther = 0;
                }
                break;
            case R.id.payment_cloud_flash: // 云闪付支付 5
                toastShow(R.string.not_yet_open_tip);
                /*mPaymentCloudFlashCheck.setChecked(!mPaymentCloudFlashCheck.isChecked());
                if (mPaymentCloudFlashCheck.isChecked()) {
                    paymentOther = 5;
                    mPaymentWeiXinCheck.setChecked(false);
                    mPaymentAlipayCheck.setChecked(false);
                } else {
                    paymentOther = 0;
                }*/
                break;
            case R.id.bottomPanel:
                if (null == mAddress || mAddress.getId() <= 0) {
                    AddressManagementActivity.startActivityForResult(this);
                    toastShow(R.string.payment_order_error_addr);
                    return;
                }
                if (mAddress.getFee() > 0 && paymentOther <= 0) {
                    toastShow(R.string.payment_order_error_payment);
                    return;
                }
                SettlementDialog dialog = new SettlementDialog(this);
                dialog.setCancelable(false);
                dialog.show();
                dialog.setOnActionClickListener(() -> {
                    orderSubOrderToPay("");
                }); // paymentNow | orderSubOrderToPay
                dialog.setmNeedShowFreight(1);
                dialog.setUsername(mAddress.getAddr_receiver());
                dialog.setUserPhone(mAddress.getAddr_tel());
                dialog.setAddressDetail(mAddress.getProvince() + mAddress.getCity() + mAddress.getTown() + mAddress.getAddr_detail());
                dialog.setFreight(mAddress.getProvince() + mAddress.getCity());
                dialog.setTotalFreight(String.valueOf(mAddress.getFee()));
                break;
            default:
                break;
        }
    }

    private void paymentNow() {
        PaymentNowDialog dialog = new PaymentNowDialog(this);
        dialog.setCancelable(false);
        dialog.show();
        dialog.setOnFinishListener(new PaymentNowDialog.OnActionClickListener() {
            @Override
            public void onClose() {
            }

            @Override
            public void onInputFinish(String password) {
                Logger.i("TAG_PaymentOrder", "onInputFinish: " + password);
                //toastShow("提货成功！请到云仓库订单-待发货中查看");
                orderSubOrderToPay(password);
            }
        });
    }

    private void orderSubOrderToPay(String password) {
        // type   支付方式， 1 现金余额，2 GRB，3 支付宝 4 微信 5 云闪付
        showProgressDialog();
        String token = GetTokenUtils.getToken(this, ApiStores.API_SERVER_ORDER_ADDORDER);
        addSubscription(apiStores().userOrderAddOrder(token, mCid, mData.getPd_id(), mData.getOr_sku_id(),
                mData.getOr_pd_num(), 2, mAddress.getId(), 2, paymentOther),
                new ApiCallback<String>() {
                    @Override
                    public void onSuccess(String model) {
                        // {"code":2,"msg":"请选择收货地址","time":1568891134,"data":""}
                        try {
                            JSONObject result = new JSONObject(model);
                            if (result.getInt("code") == 1) {
                                JSONObject data = result.optJSONObject("data");
                                int paytype = data.optInt("paytype", 0);
                                if (paytype == 0) { // 支付完成
                                    toastShow(result.optString("msg", ""));
                                    EventBus.getDefault().post(new RefreshUserInfo(true));
                                    onBackPressed();
                                } else if (paytype == 4) { // 微信支付
                                    JSONObject wechat = data.optJSONObject("wechat");
                                    wxpay(wechat, payCallback);
                                } else if (paytype == 3) { // 支付宝支付
                                    alipay(data.optString("alipay", ""), payCallback);
                                } else if (paytype == 5) { // 云闪付支付
                                    JSONObject flash = data.optJSONObject("flash");
                                    unionpay(flash, payCallback);
                                }
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

    /**
     * 支付回调
     */
    private IPayCallback payCallback = new IPayCallback() {
        @Override
        public void success() {
            showPayResult("支付成功");
        }

        @Override
        public void failed(int code, String message) {
            showPayResult("支付失败");
        }

        @Override
        public void cancel() {
            showPayResult("支付取消");
        }
    };

    protected void showPayResult(String msg) {
        runOnUiThread(() -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setCancelable(false);
            builder.setTitle("支付结果通知");
            builder.setMessage(msg);
            builder.setNegativeButton("确定", (dialog, which) -> {
                        EventBus.getDefault().post(new RefreshUserInfo(true));
                        dialog.cancel();
                        onBackPressed();
                    }
            );
            builder.create().show();
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == 10007) {
                if (null != data && data.hasExtra("bundle")) {
                    Bundle bundle = data.getBundleExtra("bundle");
                    mAddress = (AddresManagementModel.DataBean) bundle.getSerializable("addr");
                    initAddress();
                }
            }
        }
    }

    private void initAddress() {
        if (null != mAddress && mAddress.getId() > 0) {
            mSelectAddressTip.setVisibility(View.GONE);
            mSelectAddressContent.setVisibility(View.VISIBLE);
            mSelectAddressUsername.setText(mAddress.getAddr_receiver());
            mSelectAddressUserPhone.setText(mAddress.getAddr_tel());
            mSelectAddressDetail.setText(String.format("%s%s%s%s", mAddress.getProvince(), mAddress.getCity(), mAddress.getTown(), mAddress.getAddr_detail()));
            mOrderInfoFreight.setText(String.format(getString(R.string.total_top_text), null == mAddress ? "0" : String.valueOf(mAddress.getFee())));

            userOrderFindfee();
        }
    }

    /**
     * 查询运费
     */
    private void userOrderFindfee() {
        showProgressDialog();
        String token = GetTokenUtils.getToken(mActivity, ApiStores.API_SERVER_ORDER_FINDFEE);
        addSubscription(apiStores().userOrderFindfee(token, mAddress.getId()), new ApiCallback<String>() {
            @Override
            public void onSuccess(String model) {
                try {
                    JSONObject result = new JSONObject(model);
                    if (result.getInt("code") == 1) {
                        mAddress = new Gson().fromJson(result.getString("data"), AddresManagementModel.DataBean.class);
                        Logger.i("TAG_OrderFindfee", mAddress.getId() + " , " + mAddress.getFee());
                        mOrderInfoFreight.setText(String.format(getString(R.string.total_top_text), null == mAddress ? "0" : String.valueOf(mAddress.getFee())));
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
