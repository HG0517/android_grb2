package com.jgkj.grb.ui.gujujin.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.CheckBox;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jgkj.grb.R;
import com.jgkj.grb.base.BaseActivity;
import com.jgkj.grb.glide.GlideApp;
import com.jgkj.grb.onclick.RxView;
import com.jgkj.grb.retrofit.ApiCallback;
import com.jgkj.grb.retrofit.ApiStores;
import com.jgkj.grb.ui.activity.AddressManagementActivity;
import com.jgkj.grb.ui.mvp.SettlementModel;
import com.jgkj.grb.ui.mvp.personal.AddresManagementModel;
import com.jgkj.utils.token.GetTokenUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Locale;

import butterknife.BindView;

/**
 * 谷聚金：成为代理商，个人代理，结算
 */
public class GujujinBecomeAgentSettlementActivity extends BaseActivity {

    public static void startActivityForResult(Activity context, int level, int vid, int type) {
        Intent intent = new Intent(context, GujujinBecomeAgentSettlementActivity.class);
        intent.putExtra("level", level);
        intent.putExtra("vid", vid);
        intent.putExtra("type", type);
        context.startActivityForResult(intent, 10013);
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

    @BindView(R.id.shop_iv)
    ImageView mShopIv;
    @BindView(R.id.shop_name)
    TextView mShopName;
    @BindView(R.id.shop_total)
    TextView mShopTotal;
    @BindView(R.id.shop_total_)
    TextView mShopTotal_;
    @BindView(R.id.total_num)
    TextView mShopNum;

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

    @BindView(R.id.total_top)
    TextView mTotalTop;
    @BindView(R.id.pay_now)
    TextView mPayNow;

    int mLevel = -1; // a_id
    int vId = -1; // v_id
    int mAngetType = -1; // type
    AddresManagementModel.DataBean mAddress;
    SettlementModel mSettlementModel;
    int paymentGrb = 0;
    int paymentOther = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gujujin_become_agent_settlement);

        Toolbar toolbar = initToolBar(getString(R.string.activity_title_gujujin_become_agent_settlement));

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

        RxView.setOnClickListeners(this, mSelectAddress, mPaymentBalance, mPaymentGRB, mPayNow);

        userMyLast();
        indexValleySettlement();
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

    private void indexValleySettlement() {
        showProgressDialog();
        String token = GetTokenUtils.getToken(this, ApiStores.API_SERVER_VALLEY_SETTLEMENT);
        addSubscription(apiStores().indexValleySettlement(token), new ApiCallback<SettlementModel>() {
            @Override
            public void onSuccess(SettlementModel model) {
                if (model.getCode() == 1) {
                    mSettlementModel = model;
                    mAddress = model.getData().getAddr();
                    initAddress();
                    if (model.getData().getList().size() > 0)
                        GlideApp.with(mActivity)
                                .load(ApiStores.API_SERVER_URL + model.getData().getList().get(0).getPd_head_pic())
                                .into(mShopIv);
                    mShopName.setText(model.getData().getList().get(0).getPd_name());
                    mShopTotal.setText(String.format(getString(R.string.total_top_text), model.getData().getTotal()));
                    mTotalTop.setText(String.format(getString(R.string.total_top_text), model.getData().getTotal()));
                    mShopTotal_.setText(String.format("(¥%s+%s份GRB)", model.getData().getAll_price(), model.getData().getGrb()));
                    mShopNum.setText(String.format(Locale.getDefault(), "X%d", model.getData().getList().get(0).getPd_num()));
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
            case R.id.select_address:
                AddressManagementActivity.startActivityForResult(this);
                break;
            case R.id.payment_balance: // 余额支付 1
                mPaymentBalanceCheck.setChecked(!mPaymentBalanceCheck.isChecked());
                paymentOther = mPaymentBalanceCheck.isChecked() ? 1 : 0;
                break;
            case R.id.payment_grb: // GRB支付 2
                mPaymentGRBCheck.setChecked(!mPaymentGRBCheck.isChecked());
                paymentGrb = mPaymentGRBCheck.isChecked() ? 2 : 0;
                break;
            case R.id.pay_now:
                if (null == mAddress || mAddress.getId() <= 0) {
                    AddressManagementActivity.startActivityForResult(this);
                    toastShow(R.string.payment_order_error_addr);
                    return;
                }
                /*if (paymentGrb <= 0 && paymentOther <= 0) {
                    toastShow(R.string.payment_order_error_payment);
                    return;
                }*/
                indexValleyAddOrder();
                break;
            default:
                break;
        }
    }

    private void indexValleyAddOrder() {
        showProgressDialog();
        String token = GetTokenUtils.getToken(this, ApiStores.API_SERVER_VALLEY_ADDORDER);
        addSubscription(apiStores().indexValleyAddOrder(token, mSettlementModel.getData().getList().get(0).getId(), mSettlementModel.getData().getSku_id(),
                mSettlementModel.getData().getPd_num(), 4, 2, mAddress.getId()),
                new ApiCallback<String>() {
                    @Override
                    public void onSuccess(String model) {
                        // {"code":1,"msg":"订单生成成功","time":1570691487,"data":"129"}
                        try {
                            JSONObject result = new JSONObject(model);
                            if (result.optInt("code", 0) == 1) {
                                GujujinBecomeAgentPaymentActivity.startActivityForResult(mActivity, mLevel, result.optInt("data", 0), mAngetType);
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
            } else if (requestCode == 10013) {
                setResult(Activity.RESULT_OK);
                onBackPressed();
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
        }
    }

}
