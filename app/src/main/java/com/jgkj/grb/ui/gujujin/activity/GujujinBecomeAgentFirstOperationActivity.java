package com.jgkj.grb.ui.gujujin.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.jgkj.grb.R;
import com.jgkj.grb.base.BaseActivity;
import com.jgkj.grb.eventbus.RefreshUserInfo;
import com.jgkj.grb.glide.GlideApp;
import com.jgkj.grb.onclick.RxView;
import com.jgkj.grb.retrofit.ApiCallback;
import com.jgkj.grb.retrofit.ApiStores;
import com.jgkj.grb.ui.dialog.PaymentNowDialog;
import com.jgkj.grb.utils.Logger;
import com.jgkj.utils.token.GetTokenUtils;
import com.xgr.easypay.callback.IPayCallback;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * 谷聚金：成为代理商，首次操作：4 省，3 市，2 区/县，1 个人
 */
public class GujujinBecomeAgentFirstOperationActivity extends BaseActivity {

    public static void startActivityForResult(Activity context, int level) {
        Intent intent = new Intent(context, GujujinBecomeAgentFirstOperationActivity.class);
        intent.putExtra("level", level);
        context.startActivityForResult(intent, 10013);
    }

    @BindView(R.id.become_agent_speed_tv_0)
    TextView mBecomeAgentSpeedTv0;
    @BindView(R.id.become_agent_speed_tv_1)
    TextView mBecomeAgentSpeedTv1;
    @BindView(R.id.become_agent_speed_tv_2)
    TextView mBecomeAgentSpeedTv2;
    @BindView(R.id.become_agent_speed_tv_3)
    TextView mBecomeAgentSpeedTv3;
    @BindView(R.id.become_agent_speed_line_0)
    View mBecomeagentspeedline0;
    @BindView(R.id.become_agent_speed_line_1)
    View mBecomeagentspeedline1;
    @BindView(R.id.become_agent_speed_line_2)
    View mBecomeagentspeedline2;
    @BindView(R.id.become_agent_speed_iv_0)
    ImageView mBecomeAgentSpeedIv0;
    @BindView(R.id.become_agent_speed_iv_1)
    ImageView mBecomeAgentSpeedIv1;
    @BindView(R.id.become_agent_speed_iv_2)
    ImageView mBecomeAgentSpeedIv2;
    @BindView(R.id.become_agent_speed_iv_3)
    ImageView mBecomeAgentSpeedIv3;

    @BindView(R.id.userhead)
    CircleImageView mUserhead;
    @BindView(R.id.username)
    TextView mUsername;
    @BindView(R.id.selection_level_product)
    TextView mSelectionLevelProduct;
    @BindView(R.id.selection_level_)
    TextView mSelectionLevel_;
    @BindView(R.id.become_agent_tip)
    TextView mBecomeAgentTip;
    @BindView(R.id.become_agent_tip_)
    TextView mBecomeAgentTip_;

    @BindView(R.id.submission)
    CardView mSubmission;
    @BindView(R.id.submission_tv)
    TextView mSubmissionTv;

    int mLevel = -1; // a_id
    int vId = 0; // v_id
    int mAngetType = 0; // type

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gujujin_become_agent_first_operation);

        Toolbar toolbar = initToolBar("");
        toolbar.setTitle(R.string.activity_title_gujujin_become_agent_first_operation);

        Intent intent = getIntent();
        if (intent.hasExtra("level")) {
            mLevel = intent.getIntExtra("level", -1);
        }
        if (mLevel < 0) {
            toastShow(R.string.gujujin_become_agent_no_level);
            onBackPressed();
            return;
        }

        RxView.setOnClickListeners(this, mSubmission);
        initSpeed();
        setUserInfo();
        indexValleyShow();
    }

    private void setUserInfo() {
        try {
            JSONObject userModel = new JSONObject(sharedPreferencesHelper.getSharedPreference("user", "").toString());
            GlideApp.with(mActivity)
                    .load(ApiStores.API_SERVER_URL + userModel.optString("us_head_pic", null))
                    .into(mUserhead);
            mUsername.setText(userModel.optString("us_nickname", ""));
        } catch (JSONException e) {
        }
    }

    private void indexValleyShow() {
        showProgressDialog();
        String token = GetTokenUtils.getToken(this, ApiStores.API_SERVER_VALLEY_SHOW);
        addSubscription(apiStores().indexValleyShow(token, mLevel), new ApiCallback<String>() {
            @Override
            public void onSuccess(String model) {
                // {"code":1,"msg":"成功","time":1569743703,"data":{"id":1,"area_name":"省代理","price":0,"grb":10000000,"level":0,"type":4,"note":"1000万份GRB"}}
                try {
                    JSONObject result = new JSONObject(model);
                    if (result.optInt("code", 0) == 1) {
                        showLevel(result.getJSONObject("data"));
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

    private void showLevel(JSONObject data) {
        mBecomeAgentTip.setText(String.format(getString(R.string.gujujin_become_agent_payment_tip),
                data.optString("area_name", getString(R.string.gujujin_become_agent_payment_default_tip))));
        mBecomeAgentTip_.setText(data.optString("note", ""));
        mAngetType = data.optInt("type", 1);
        vId = data.optInt("id", 1);
        switch (mAngetType) {
            case 4:
                mSelectionLevel_.setCompoundDrawablesWithIntrinsicBounds(R.mipmap.ic_gujvjin_daili_31, 0, 0, 0);
                mSelectionLevel_.setText(R.string.gujujin_become_agent_level_4);
                mSubmissionTv.setText(R.string.gujujin_become_agent_sure_1);
                break;
            case 3:
                mSelectionLevel_.setCompoundDrawablesWithIntrinsicBounds(R.mipmap.ic_gujvjin_daili_21, 0, 0, 0);
                mSelectionLevel_.setText(R.string.gujujin_become_agent_level_3);
                mSubmissionTv.setText(R.string.gujujin_become_agent_sure_1);
                break;
            case 2:
                mSelectionLevel_.setCompoundDrawablesWithIntrinsicBounds(R.mipmap.ic_gujvjin_daili_11, 0, 0, 0);
                mSelectionLevel_.setText(R.string.gujujin_become_agent_level_2);
                mSubmissionTv.setText(R.string.gujujin_become_agent_sure_1);
                break;
            case 1:
                mSelectionLevel_.setCompoundDrawablesWithIntrinsicBounds(R.mipmap.ic_gujvjin_daili_01, 0, 0, 0);
                mSelectionLevel_.setText(R.string.gujujin_become_agent_level_1);
                mSubmissionTv.setText(R.string.gujujin_become_agent_sure_0);
                break;
            default:
                break;
        }
    }

    @Override
    public void onClick(Object o) {
        View v = (View) o;
        switch (v.getId()) {
            case R.id.submission:
                if (mAngetType == 1) { // 个人代理，先结算
                    GujujinBecomeAgentSettlementActivity.startActivityForResult(this, mLevel, vId, mAngetType);
                } else if (mAngetType == 4 || mAngetType == 3) { // 省、市代理，支付
                    showPaymentNowDialog();
                } else { // 区县代理，先选择支付方式
                    GujujinBecomeAgentPaymentActivity.startActivityForResult(this, mLevel, vId, mAngetType);
                }
                break;
            default:
                break;
        }
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
                indexValleyApplyPay(password);
            }
        });
    }

    /**
     * 支付：省、市代理
     */
    private void indexValleyApplyPay(String password) {
        showProgressDialog();
        String token = GetTokenUtils.getToken(this, ApiStores.API_SERVER_VALLEY_APPLYPAY);
        addSubscription(apiStores().indexValleyApplyPay(token, mLevel, vId, password, null), new ApiCallback<String>() {
            @Override
            public void onSuccess(String model) {
                try {
                    JSONObject result = new JSONObject(model);
                    if (result.optInt("code", 0) == 1) {
                        JSONObject data = result.optJSONObject("data");
                        int paytype = data.optInt("paytype", 0);
                        if (paytype == 0) { // 支付完成
                            toastShow(result.optString("msg", ""));
                            paymentSuccessful();
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
            showPayResult("支付成功", true);
        }

        @Override
        public void failed(int code, String message) {
            showPayResult("支付失败", false);
        }

        @Override
        public void cancel() {
            showPayResult("支付取消", false);
        }
    };

    protected void showPayResult(String msg, boolean success) {
        runOnUiThread(() -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setCancelable(false);
            builder.setTitle("支付结果通知");
            builder.setMessage(msg);
            builder.setNegativeButton("确定", (dialog, which) -> {
                        if (success)
                            paymentSuccessful();
                        dialog.cancel();
                        if (success)
                            onBackPressed();
                    }
            );
            builder.create().show();
        });
    }

    private void paymentSuccessful() {
        try {
            JSONObject userModel = new JSONObject(sharedPreferencesHelper.getSharedPreference("user", "").toString());
            userModel.put("us_agent", mAngetType);
            sharedPreferencesHelper.putApply("user", userModel.toString());
            EventBus.getDefault().post(new RefreshUserInfo(isLogin()));
            GujujinBecomeAgentCompleteActivity.start(mActivity);
            setResult(Activity.RESULT_OK);
        } catch (JSONException e) {
        }
    }

    private void initSpeed() {
        mBecomeAgentSpeedTv0.setSelected(true);
        mBecomeAgentSpeedIv0.setSelected(true);
        mBecomeAgentSpeedTv1.setSelected(true);
        mBecomeAgentSpeedIv1.setSelected(true);
        mBecomeAgentSpeedTv2.setSelected(true);
        mBecomeAgentSpeedIv2.setSelected(true);
        mBecomeagentspeedline0.setBackgroundColor(Color.parseColor("#F53C5E"));
        mBecomeagentspeedline1.setBackgroundColor(Color.parseColor("#F53C5E"));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == 10013) {
                setResult(Activity.RESULT_OK);
                onBackPressed();
            }
        }
    }

}
