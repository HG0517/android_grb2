package com.jgkj.grb.ui.gujujin.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
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
import com.jgkj.grb.ui.gujujin.dialog.GujujinPaymentOfflineDialog;
import com.jgkj.grb.utils.Logger;
import com.jgkj.utils.token.GetTokenUtils;
import com.xgr.easypay.callback.IPayCallback;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * 谷聚金：成为代理商，支付方式
 */
public class GujujinBecomeAgentPaymentActivity extends BaseActivity {

    public static void startActivityForResult(Activity context, int level, int vid, int type) {
        Intent intent = new Intent(context, GujujinBecomeAgentPaymentActivity.class);
        intent.putExtra("level", level);
        intent.putExtra("vid", vid);
        intent.putExtra("type", type);
        context.startActivityForResult(intent, 10013);
    }

    @BindView(R.id.payment_offline)
    FrameLayout mPaymentOffline;
    @BindView(R.id.payment_offline_check)
    CheckBox mPaymentOfflineCheck;

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

    int mLevel = -1; // a_id
    int vId = -1; // v_id
    int mAngetType = -1; // type
    int paymentBalance = 0;
    int paymentGrb = 0;
    int paymentOffline = 0;
    int paymentOther = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gujujin_become_agent_payment);

        Toolbar toolbar = initToolBar(getString(R.string.activity_title_gujujin_become_agent_payment));

        Intent intent = getIntent();
        if (intent.hasExtra("level")) {
            mLevel = intent.getIntExtra("level", 0);
        }
        if (intent.hasExtra("vid")) {
            vId = intent.getIntExtra("vid", 0);
        }
        if (intent.hasExtra("type")) {
            mAngetType = intent.getIntExtra("type", 0);
        }
        if (mLevel < 0 || vId < 0 || mAngetType < 0) {
            toastShow(R.string.open_activity_error_option);
            onBackPressed();
            return;
        }

        RxView.setOnClickListeners(this, mPaymentOffline, mPaymentBalance, mPaymentGRB,
                mPaymentWeiXin, mPaymentAlipay, mPaymentCloudFlash, mBottomPanel);

        initPayment();
    }

    private void initPayment() {
        if (mLevel == 0) { // 进货结算
            mPaymentOffline.setVisibility(View.VISIBLE);
            mPaymentBalance.setVisibility(View.VISIBLE);
            mPaymentGRB.setVisibility(View.VISIBLE);
            mPaymentWeiXin.setVisibility(View.VISIBLE);
            mPaymentAlipay.setVisibility(View.VISIBLE);
            mPaymentCloudFlash.setVisibility(View.VISIBLE);
        } else if (mAngetType == 4 || mAngetType == 3) { // 省、市代理
            mPaymentOffline.setVisibility(View.GONE);
            mPaymentBalance.setVisibility(View.GONE);
            mPaymentGRB.setVisibility(View.VISIBLE);
            mPaymentWeiXin.setVisibility(View.GONE);
            mPaymentAlipay.setVisibility(View.GONE);
            mPaymentCloudFlash.setVisibility(View.GONE);
        } else if (mAngetType == 1 || mAngetType == 2) { // 个人代理、区县代理
            mPaymentOffline.setVisibility(View.GONE);
            mPaymentBalance.setVisibility(View.VISIBLE);
            mPaymentGRB.setVisibility(View.VISIBLE);
            mPaymentWeiXin.setVisibility(View.VISIBLE);
            mPaymentAlipay.setVisibility(View.VISIBLE);
            mPaymentCloudFlash.setVisibility(View.VISIBLE);
        } else if (mAngetType == 5) {
            mPaymentOffline.setVisibility(View.VISIBLE);
            mPaymentBalance.setVisibility(View.VISIBLE);
            mPaymentGRB.setVisibility(View.VISIBLE);
            mPaymentWeiXin.setVisibility(View.VISIBLE);
            mPaymentAlipay.setVisibility(View.VISIBLE);
            mPaymentCloudFlash.setVisibility(View.VISIBLE);
        }

        userMyLast();
    }

    /**
     * 余额
     */
    private void userMyLast() {
        String token = GetTokenUtils.getToken(this, ApiStores.API_SERVER_USER_MYLAST);
        addSubscription(apiStores().userMyLast(token), new ApiCallback<String>() {
            @Override
            public void onSuccess(String model) {
                try {
                    JSONObject result = new JSONObject(model);
                    if (result.optInt("code", 0) == 1) {
                        // {"code":1,"msg":"成功","time":1570606188,"data":{"cash":"25976.76","grb_cash":"101460.00000","grb_integral":"9969924999.99999","grb":9970026460}}
                        try {
                            JSONObject data = result.optJSONObject("data");
                            JSONObject userModel = new JSONObject(sharedPreferencesHelper.getSharedPreference("user", "").toString());
                            userModel.put("cash", data.optString("cash", "0"));
                            userModel.put("grb_cash", data.optString("grb_cash", "0"));
                            userModel.put("grb_integral", data.optString("grb_integral", "0"));
                            userModel.put("grb", data.optString("grb", "0"));
                            sharedPreferencesHelper.putApply("user", userModel.toString());
                        } catch (JSONException e) {
                        }
                        initPaymentBalance();
                    }
                } catch (JSONException e) {
                }
            }

            @Override
            public void onFailure(String msg) {
                initPaymentBalance();
            }

            @Override
            public void onFinish() {
            }
        });
    }

    private void initPaymentBalance() {
        try {
            JSONObject userModel = new JSONObject(sharedPreferencesHelper.getSharedPreference("user", "").toString());
            mPaymentBalanceTotal.setText(String.format(getString(R.string.payment_balance_total_text), userModel.optString("cash", "0")));
            mPaymentGRBTotal.setText(String.format(getString(R.string.payment_grb_total_text), userModel.optString("grb_cash", "0")));
        } catch (JSONException e) {
        }
    }

    @Override
    public void onClick(Object o) {
        View v = (View) o;
        switch (v.getId()) {
            case R.id.payment_offline: // 线下支付 6
                mPaymentOfflineCheck.setChecked(!mPaymentOfflineCheck.isChecked());
                mPaymentBalanceCheck.setChecked(false);
                mPaymentGRBCheck.setChecked(false);
                mPaymentWeiXinCheck.setChecked(false);
                mPaymentAlipayCheck.setChecked(false);
                mPaymentCloudFlashCheck.setChecked(false);
                paymentBalance = 0;
                paymentGrb = 0;
                paymentOffline = mPaymentOfflineCheck.isChecked() ? 6 : 0;
                paymentBalance = 0;
                paymentGrb = 0;
                paymentOther = 0;
                break;
            case R.id.payment_balance: // 余额支付 1
                mPaymentOfflineCheck.setChecked(false);
                mPaymentBalanceCheck.setChecked(!mPaymentBalanceCheck.isChecked());
                mPaymentWeiXinCheck.setChecked(false);
                mPaymentAlipayCheck.setChecked(false);
                mPaymentCloudFlashCheck.setChecked(false);
                paymentOffline = 0;
                paymentBalance = mPaymentBalanceCheck.isChecked() ? 1 : 0;
                paymentOther = 0;
                break;
            case R.id.payment_grb: // GRB支付 2
                mPaymentOfflineCheck.setChecked(false);
                mPaymentGRBCheck.setChecked(!mPaymentGRBCheck.isChecked());
                paymentOffline = 0;
                paymentGrb = mPaymentGRBCheck.isChecked() ? 2 : 0;
                break;
            case R.id.payment_weixin: // 微信支付 4
                /*toastShow(R.string.not_yet_open_tip);*/
                mPaymentOfflineCheck.setChecked(false);
                mPaymentBalanceCheck.setChecked(false);
                mPaymentWeiXinCheck.setChecked(!mPaymentWeiXinCheck.isChecked());
                mPaymentAlipayCheck.setChecked(false);
                mPaymentCloudFlashCheck.setChecked(false);
                paymentOffline = 0;
                paymentBalance = 0;
                paymentOther = mPaymentWeiXinCheck.isChecked() ? 4 : 0;
                break;
            case R.id.payment_alipay: // 支付宝支付 3
                /*toastShow(R.string.not_yet_open_tip);*/
                mPaymentOfflineCheck.setChecked(false);
                mPaymentBalanceCheck.setChecked(false);
                mPaymentAlipayCheck.setChecked(!mPaymentAlipayCheck.isChecked());
                mPaymentWeiXinCheck.setChecked(false);
                mPaymentCloudFlashCheck.setChecked(false);
                paymentOffline = 0;
                paymentBalance = 0;
                paymentOther = mPaymentAlipayCheck.isChecked() ? 3 : 0;
                break;
            case R.id.payment_cloud_flash: // 云闪付支付 5
                toastShow(R.string.not_yet_open_tip);
                /*mPaymentOfflineCheck.setChecked(false);
                mPaymentBalanceCheck.setChecked(false);
                mPaymentCloudFlashCheck.setChecked(!mPaymentCloudFlashCheck.isChecked());
                mPaymentWeiXinCheck.setChecked(false);
                mPaymentAlipayCheck.setChecked(false);
                paymentOffline = 0;
                paymentBalance = 0;
                paymentOther = mPaymentCloudFlashCheck.isChecked() ? 5 : 0;*/
                break;
            case R.id.bottomPanel: // 立即支付
                if (paymentBalance <= 0 && paymentGrb <= 0
                        && paymentOffline <= 0 && paymentOther <= 0) {
                    toastShow(R.string.payment_order_error_payment);
                    return;
                }
                showPaymentNowDialog();
                break;
            default:
                break;
        }
    }

    /**
     * 线下支付
     */
    private void showGujujinPaymentOfflineDialog(JSONObject data) {
        // {"code":1,"msg":"成功","time":1570858966,"data":{"total":188,"bank_account":"6228480218721012670","alipay":"15538945741","wechat":"15538945741"}}
        Logger.i("TAG_", "线下支付  信息展示");
        GujujinPaymentOfflineDialog dialog = GujujinPaymentOfflineDialog.newInstance(
                data.optString("total", "0.00"),
                data.optString("wechat", ""),
                data.optString("alipay", ""),
                data.optString("bank_account", ""));
        dialog.setCancelable(false);
        dialog.showDialog(getSupportFragmentManager());
        dialog.setOnDialogListener(new GujujinPaymentOfflineDialog.OnDialogListener() {

            @Override
            public void onCancel() {
            }

            @Override
            public void onSure() {
                paymentSuccess();
                onBackPressed();
            }
        });
    }

    /**
     * 支付密码
     */
    private void showPaymentNowDialog() {
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
                if (mAngetType == 1 || mLevel == 0) {
                    indexValleyOrderPay(password);
                } else {
                    indexValleyApplyPay(password);
                }
            }
        });
    }

    /**
     * 支付：个人代理
     */
    private void indexValleyOrderPay(String password) {
        // type   支付方式， 1 现金余额，2 GRB，3 支付宝 4 微信 5 云闪付
        ArrayList<Object> paymentType = new ArrayList<>();
        if (paymentBalance > 0)
            paymentType.add(paymentBalance);
        if (paymentGrb > 0)
            paymentType.add(paymentGrb);
        if (paymentOffline > 0)
            paymentType.add(paymentOffline);
        if (paymentOther > 0)
            paymentType.add(paymentOther);
        Logger.i("TAG_PaymentType", paymentType.toString());
        showProgressDialog();
        String token = GetTokenUtils.getToken(this, ApiStores.API_SERVER_VALLEY_ORDERPAY);
        addSubscription(apiStores().indexValleyOrderPay(token, vId, password, paymentType), new ApiCallback<String>() {
            @Override
            public void onSuccess(String model) {
                try {
                    JSONObject result = new JSONObject(model);
                    if (result.optInt("code", 0) == 1) {
                        if (mLevel == 0) { // 进货支付
                            if (paymentOffline == 6) { // 线下支付
                                showGujujinPaymentOfflineDialog(result.optJSONObject("data"));
                            } else {
                                paymentResults(result, 1);
                            }
                        } else { // 成为代理支付
                            paymentResults(result, 0);
                        }
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
     * 支付：区/县代理
     */
    private void indexValleyApplyPay(String password) {
        // type   支付方式， 1 现金余额，2 GRB，3 支付宝 4 微信 5 云闪付
        ArrayList<Object> paymentType = new ArrayList<>();
        if (paymentBalance > 0)
            paymentType.add(paymentBalance);
        if (paymentGrb > 0)
            paymentType.add(paymentGrb);
        if (paymentOther > 0)
            paymentType.add(paymentOther);
        Logger.i("TAG_PaymentType", paymentType.toString());
        showProgressDialog();
        String token = GetTokenUtils.getToken(this, ApiStores.API_SERVER_VALLEY_APPLYPAY);
        addSubscription(apiStores().indexValleyApplyPay(token, mLevel, vId, password, paymentType), new ApiCallback<String>() {
            @Override
            public void onSuccess(String model) {
                try {
                    JSONObject result = new JSONObject(model);
                    if (result.optInt("code", 0) == 1) {
                        paymentResults(result, 0);
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
     * 处理支付结果：支付完成，或者使用三方支付
     *
     * @param type 0 代理支付 1 进货支付
     */
    private void paymentResults(JSONObject result, int type) {
        JSONObject data = result.optJSONObject("data");
        int paytype = data.optInt("paytype", 0);
        if (paytype == 0) { // 支付完成
            toastShow(result.optString("msg", ""));
            if (type == 1) {
                paymentSuccess();
            } else {
                paymentSuccessful();
            }
            onBackPressed();
        } else if (paytype == 4) { // 微信支付
            JSONObject wechat = data.optJSONObject("wechat");
            wxpay(wechat, type == 1 ? payCallbackOther : payCallback);
        } else if (paytype == 3) { // 支付宝支付
            alipay(data.optString("alipay", ""), type == 1 ? payCallbackOther : payCallback);
        } else if (paytype == 5) { // 云闪付支付
            JSONObject flash = data.optJSONObject("flash");
            unionpay(flash, type == 1 ? payCallbackOther : payCallback);
        }
    }

    /**
     * 三方支付回调：代理支付
     */
    private IPayCallback payCallback = new IPayCallback() {
        @Override
        public void success() {
            showPayResult("支付成功", true, 0);
        }

        @Override
        public void failed(int code, String message) {
            showPayResult("支付失败", false, 0);
        }

        @Override
        public void cancel() {
            showPayResult("支付取消", false, 0);
        }
    };
    /**
     * 三方支付回调：进货支付
     */
    private IPayCallback payCallbackOther = new IPayCallback() {
        @Override
        public void success() {
            showPayResult("支付成功", true, 1);
        }

        @Override
        public void failed(int code, String message) {
            showPayResult("支付失败", false, 1);
        }

        @Override
        public void cancel() {
            showPayResult("支付取消", false, 1);
        }
    };

    /**
     * 三方支付结果
     */
    protected void showPayResult(String msg, boolean success, int type) {
        runOnUiThread(() -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setCancelable(false);
            builder.setTitle("支付结果通知");
            builder.setMessage(msg);
            builder.setNegativeButton("确定", (dialog, which) -> {
                        if (success) {
                            if (type == 1) {
                                paymentSuccess();
                            } else {
                                paymentSuccessful();
                            }
                        }
                        dialog.cancel();
                        if (success)
                            onBackPressed();
                    }
            );
            builder.create().show();
        });
    }

    /**
     * 支付完成：代理
     */
    private void paymentSuccessful() {
        try {
            JSONObject userModel = new JSONObject(sharedPreferencesHelper.getSharedPreference("user", "").toString());
            userModel.put("us_agent", mAngetType);
            sharedPreferencesHelper.putApply("user", userModel.toString());
            GujujinBecomeAgentCompleteActivity.start(mActivity);
            EventBus.getDefault().post(new RefreshUserInfo(isLogin()));
            setResult(Activity.RESULT_OK);
        } catch (JSONException e) {
        }
    }

    /**
     * 支付完成：进货
     */
    private void paymentSuccess() {
        EventBus.getDefault().post(new RefreshUserInfo(isLogin()));
        setResult(Activity.RESULT_OK);
    }

}
