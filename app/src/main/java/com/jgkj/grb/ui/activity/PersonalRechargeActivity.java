package com.jgkj.grb.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.jgkj.grb.R;
import com.jgkj.grb.base.BaseActivity;
import com.jgkj.grb.eventbus.RefreshUserInfo;
import com.jgkj.grb.itemdecoration.GridDividerItemDecoration;
import com.jgkj.grb.onclick.RxView;
import com.jgkj.grb.retrofit.ApiCallback;
import com.jgkj.grb.retrofit.ApiStores;
import com.jgkj.grb.ui.adapter.PersonalRechargeAdapter;
import com.jgkj.grb.utils.SpannableStringUtil;
import com.jgkj.utils.token.GetTokenUtils;
import com.xgr.easypay.callback.IPayCallback;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 个人中心：账户充值
 */
public class PersonalRechargeActivity extends BaseActivity {

    public static void start(Context context) {
        Intent intent = new Intent(context, PersonalRechargeActivity.class);
        context.startActivity(intent);
    }

    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;

    @BindView(R.id.personal_recharge_money)
    EditText mPersonalRechargeMoney;

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

    @BindView(R.id.personal_recharge_sure)
    CardView mPersonalRechargeSure;

    @BindView(R.id.personal_recharge_tip)
    TextView mPersonalRechargeTip;

    PersonalRechargeAdapter mAdapter;
    List<PersonalRechargeBean> mList = new ArrayList<>();

    int paymentOther = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_recharge);

        Toolbar toolbar = initToolBar(getString(R.string.activity_title_personal_recharge));

        String text = getString(R.string.personal_recharge_text);
        String tip = getString(R.string.personal_recharge_tip);
        SpannableString spannableString = SpannableStringUtil.getSpannableString(String.format(text, tip),
                tip.length(), 1f, Color.parseColor("#FB355C"),
                v -> {
                    //TermsOfServiceActivity.start(mActivity);
                    ServiceHelpWebViewActivity.start(mActivity, "22");
                });
        // 不设置点击不生效
        mPersonalRechargeTip.setMovementMethod(LinkMovementMethod.getInstance());
        mPersonalRechargeTip.setText(spannableString);
        // 去掉点击后文字的背景色
        // mRegisterTermsOfServiceTv.setHighlightColor(Color.TRANSPARENT);

        initRecyclerView();

        RxView.setOnClickListeners(this, mPaymentWeiXin, mPaymentAlipay, mPaymentCloudFlash, mPersonalRechargeSure);
    }

    private void initRecyclerView() {
        mRecyclerView.setLayoutManager(new GridLayoutManager(this, 2, GridLayoutManager.VERTICAL, false));
        mRecyclerView.addItemDecoration(new GridDividerItemDecoration(dp2px(20), Color.TRANSPARENT));
        mAdapter = new PersonalRechargeAdapter(this, mList);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener((view, position) -> {
            for (int i = 0; i < mList.size(); i++) {
                mList.get(i).setSelect(false);
            }
            mList.get(position).setSelect(true);
            mAdapter.notifyDataSetChanged();

            mPersonalRechargeMoney.setText(String.valueOf(mList.get(position).getMoney()));
        });

        mList.add(new PersonalRechargeBean(5000, "充5000元", "(50%GRC+50%人民币)"));
        mList.add(new PersonalRechargeBean(3000, "充3000元", "(50%GRC+50%人民币)"));
        mList.add(new PersonalRechargeBean(2000, "充2000元", "(50%GRC+50%人民币)"));
        mList.add(new PersonalRechargeBean(1000, "充1000元", "(50%GRC+50%人民币)"));
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onClick(Object o) {
        View v = (View) o;
        switch (v.getId()) {
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
            case R.id.personal_recharge_sure:
                if (TextUtils.isEmpty(mPersonalRechargeMoney.getText().toString().trim())
                        || Integer.parseInt(mPersonalRechargeMoney.getText().toString().trim()) <= 0) {
                    toastShow(R.string.personal_recharge_tip_11);
                    return;
                }
                userPayRecharge(mPersonalRechargeMoney.getText().toString().trim(), paymentOther);
                break;
            default:
                break;
        }
    }

    private void userPayRecharge(String money, int payment) {
        if (payment <= 0) {
            toastShow(R.string.payment_order_error_payment);
            return;
        }

        showProgressDialog();
        String token = GetTokenUtils.getToken(this, ApiStores.API_SERVER_USER_PAY_RECHARGE);
        addSubscription(apiStores().userPayRecharge(token, money, payment), new ApiCallback<String>() {
            @Override
            public void onSuccess(String model) {
                try {
                    JSONObject result = new JSONObject(model);
                    if (result.optInt("code", 0) == 1) {
                        if (payment == 4) { // 微信支付
                            JSONObject data = result.optJSONObject("data");
                            wxpay(data, payCallback);
                        } else if (payment == 3) { // 支付宝支付
                            alipay(result.optString("data", ""), payCallback);
                        } else if (payment == 5) { // 云闪付支付
                            JSONObject data = result.optJSONObject("data");
                            unionpay(data, payCallback);
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
                        // ApplyResultActivity.start(mActivity, 3);
                        onBackPressed();
                    }
            );
            builder.create().show();
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_cloud_record:
                PersonalRechargeRecordActivity.start(mActivity);
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_cloud_warehouse, menu);
        return true;
    }

    public class PersonalRechargeBean {
        boolean select;
        int money;
        String moneyString;
        String moneyRatio;

        public PersonalRechargeBean() {
        }

        public PersonalRechargeBean(int money, String moneyString, String moneyRatio) {
            this.money = money;
            this.moneyString = moneyString;
            this.moneyRatio = moneyRatio;
        }

        public PersonalRechargeBean(boolean select, int money, String moneyString, String moneyRatio) {
            this.select = select;
            this.money = money;
            this.moneyString = moneyString;
            this.moneyRatio = moneyRatio;
        }

        public boolean isSelect() {
            return select;
        }

        public void setSelect(boolean select) {
            this.select = select;
        }

        public int getMoney() {
            return money;
        }

        public void setMoney(int money) {
            this.money = money;
        }

        public String getMoneyString() {
            return moneyString;
        }

        public void setMoneyString(String moneyString) {
            this.moneyString = moneyString;
        }

        public String getMoneyRatio() {
            return moneyRatio;
        }

        public void setMoneyRatio(String moneyRatio) {
            this.moneyRatio = moneyRatio;
        }
    }
}
