package com.jgkj.grb.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.CheckBox;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.jgkj.grb.R;
import com.jgkj.grb.base.BaseActivity;
import com.jgkj.grb.eventbus.RefreshUserInfo;
import com.jgkj.grb.onclick.RxView;
import com.jgkj.grb.retrofit.ApiCallback;
import com.jgkj.grb.retrofit.ApiStores;
import com.jgkj.grb.ui.dialog.PaymentNowDialog;
import com.jgkj.grb.ui.mvp.OrderSubOrderModel;
import com.jgkj.grb.utils.Logger;
import com.jgkj.utils.token.GetTokenUtils;
import com.xgr.easypay.callback.IPayCallback;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * 支付订单
 */
public class PaymentOrdersActivity extends BaseActivity {

    public static void startActivityForResult(Activity context, int type, String data) {
        Intent intent = new Intent(context, PaymentOrdersActivity.class);
        intent.putExtra("type", type);
        intent.putExtra("data", data);
        context.startActivityForResult(intent, 10010);
    }

    @BindView(R.id.total_top)
    TextView mTotalTop;
    @BindView(R.id.payment_total_dikou_num)
    TextView mTotalDikouNum;
    @BindView(R.id.total_bottom)
    TextView mTotalBottom;
    @BindView(R.id.payment_total_title_num)
    TextView mPaymentTotalTitleNum;
    @BindView(R.id.payment_total_num)
    TextView mPaymentTotalNum;
    @BindView(R.id.payment_total_title_freight)
    TextView mPaymentTotalTitleFreight;
    @BindView(R.id.payment_total_freight)
    TextView mPaymentTotalFreight;

    @BindView(R.id.payment_balance)
    FrameLayout mPaymentBalance;
    @BindView(R.id.payment_balance_total)
    TextView mPaymentBalanceTotal;
    @BindView(R.id.payment_balance_check)
    CheckBox mPaymentBalanceCheck;

    @BindView(R.id.payment_grb)
    FrameLayout mPaymentGRB;
    @BindView(R.id.payment_grb_total)
    TextView mPaymentGRBTotal;
    @BindView(R.id.payment_grb_check)
    CheckBox mPaymentGRBCheck;

    @BindView(R.id.payment_grc)
    FrameLayout mPaymentGRC;
    @BindView(R.id.payment_grc_total)
    TextView mPaymentGRCTotal;
    @BindView(R.id.payment_grc_check)
    CheckBox mPaymentGRCCheck;

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
    @BindView(R.id.pay_now_total)
    TextView mPayNowTotal;

    int mType = 1; // 1:商城订单，2：云仓库订单，3：抢购订单
    String mData = "";
    int paymentBalance = 0;
    int paymentGrb = 0;
    int paymentGrc = 0;
    int paymentOther = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_orders);

        Intent intent = getIntent();
        if (intent.hasExtra("type")) {
            mType = intent.getIntExtra("type", 1);
        }
        if (intent.hasExtra("data")) {
            mData = intent.getStringExtra("data");
        }
        if (TextUtils.isEmpty(mData)) {
            toastShow(R.string.open_activity_payment_order_error_option);
            return;
        }

        mPaymentTotalTitleFreight.setVisibility(mType == 2 ? View.GONE : View.VISIBLE);
        mPaymentTotalFreight.setVisibility(mType == 2 ? View.GONE : View.VISIBLE);

        Toolbar toolbar = initToolBar(getString(R.string.activity_title_payment_orders));

        RxView.setOnClickListeners(this, mPaymentBalance, mPaymentGRB, mPaymentGRC, mPaymentWeiXin, mPaymentAlipay, mPaymentCloudFlash, mBottomPanel);

        onLazyLoad();
    }

    private void onLazyLoad() {
        showProgressDialog();
        String token = GetTokenUtils.getToken(this, ApiStores.API_SERVER_ORDER_SUBORDER);
        addSubscription(apiStores().userOrderSubOrder(token, mData), new ApiCallback<OrderSubOrderModel>() {
            @Override
            public void onSuccess(OrderSubOrderModel model) {
                // {"code":1,"msg":"成功","time":1567935790,"data":{"cash":"0.00","grb":740,"total":0,"fee":0,"all_pv":600.4}}
                if (model.getCode() == 1) {
                    mTotalTop.setText(String.format(getString(R.string.total_top_text), model.getData().getTotal()));
                    mTotalDikouNum.setText(String.format("%sGRB\n%sGRC", model.getData().getGrb(), model.getData().getGrc()));
                    //mTotalBottom.setText(String.format(getString(R.string.total_bottom_text), model.getData().getAll_pv()));
                    mPaymentTotalFreight.setText(String.format(getString(R.string.total_top_text), model.getData().getFee()));
                    mPaymentBalanceTotal.setText(String.format(getString(R.string.payment_balance_total_text), model.getData().getUs_cash()));
                    mPaymentGRBTotal.setText(String.format(getString(R.string.payment_grb_total_text), model.getData().getUs_grb()));
                    mPaymentGRCTotal.setText(String.format(getString(R.string.payment_grb_total_text), model.getData().getUs_grc()));
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

    @Override
    public void onClick(Object o) {
        View v = (View) o;
        switch (v.getId()) {
            case R.id.payment_balance: // 余额支付 1
                mPaymentBalanceCheck.setChecked(!mPaymentBalanceCheck.isChecked());
                paymentBalance = mPaymentBalanceCheck.isChecked() ? 1 : 0;
                break;
            case R.id.payment_grb: // GRB支付 2
                mPaymentGRBCheck.setChecked(!mPaymentGRBCheck.isChecked());
                paymentGrb = mPaymentGRBCheck.isChecked() ? 2 : 0;
                break;
            case R.id.payment_grc: // GRC支付 7
                mPaymentGRCCheck.setChecked(!mPaymentGRCCheck.isChecked());
                paymentGrc = mPaymentGRCCheck.isChecked() ? 7 : 0;
                break;
            case R.id.payment_weixin: // 微信支付 4
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
                /*toastShow(R.string.not_yet_open_tip);*/
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
            case R.id.bottomPanel: // 立即支付
                if (paymentBalance <= 0 && paymentGrb <= 0 && paymentGrc <= 0 && paymentOther <= 0) {
                    toastShow(R.string.payment_order_error_payment);
                    return;
                }
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
                        orderSubOrderToPay(password);
                    }
                });
                break;
            default:
                break;
        }
    }

    private void orderSubOrderToPay(String password) {
        // type   支付方式， 1 现金余额，2 GRB，3 支付宝，4 微信，5 云闪付，7 GRC
        ArrayList<Object> paymentType = new ArrayList<>();
        if (paymentBalance > 0)
            paymentType.add(paymentBalance);
        if (paymentGrb > 0)
            paymentType.add(paymentGrb);
        if (paymentGrc > 0)
            paymentType.add(paymentGrc);
        if (paymentOther > 0)
            paymentType.add(paymentOther);
        Logger.i("TAG_PaymentType", paymentType.toString());
        showProgressDialog();
        String token = GetTokenUtils.getToken(this, ApiStores.API_SERVER_ORDER_SUBORDERTOPAY);
        addSubscription(apiStores().orderSubOrderToPay(token, mData, password, paymentType),
                new ApiCallback<String>() {
                    @Override
                    public void onSuccess(String model) {
                        try {
                            JSONObject result = new JSONObject(model);
                            if (result.optInt("code", 0) == 1) {
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
                                setResult(Activity.RESULT_OK);
                            } else {
                                toastShow(result.optString("msg", ""));
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

}
