package com.jgkj.grb.ui.gujujin.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.CheckBox;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.google.gson.Gson;
import com.jgkj.grb.R;
import com.jgkj.grb.base.BaseActivity;
import com.jgkj.grb.glide.GlideApp;
import com.jgkj.grb.glide.RoundTransformation;
import com.jgkj.grb.onclick.RxView;
import com.jgkj.grb.retrofit.ApiCallback;
import com.jgkj.grb.retrofit.ApiStores;
import com.jgkj.grb.ui.activity.AddressManagementActivity;
import com.jgkj.grb.ui.dialog.SettlementDialog;
import com.jgkj.grb.ui.mvp.SettlementModel;
import com.jgkj.grb.ui.mvp.personal.AddresManagementModel;
import com.jgkj.grb.ui.mvp.shopcart.CartIndexBean;
import com.jgkj.utils.token.GetTokenUtils;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;

/**
 * 谷聚金：进货结算，2 区/县、1 个人
 */
public class GujujinPurchaseSettlementActivity extends BaseActivity {

    public static void start(Context context, String pdId, String skuId, String pdNum) {
        Intent intent = new Intent(context, GujujinPurchaseSettlementActivity.class);
        intent.putExtra("pdId", pdId);
        intent.putExtra("skuId", skuId);
        intent.putExtra("pdNum", pdNum);
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

    @BindView(R.id.shop_iv)
    ImageView mShopIv;
    @BindView(R.id.shop_name)
    TextView mShopName;
    @BindView(R.id.shop_total)
    TextView mShopTotal;
    @BindView(R.id.total_num)
    TextView mShopNum;

    @BindView(R.id.freight)
    ConstraintLayout mFreight;
    @BindView(R.id.total_freight)
    TextView mTotalFreight;
    @BindView(R.id.deliverer)
    ConstraintLayout mDeliverer;
    @BindView(R.id.deliverer_rg)
    RadioGroup mDelivererRg;
    @BindView(R.id.deliverer_rb_0)
    RadioButton mDelivererRb0;
    @BindView(R.id.deliverer_rb_1)
    RadioButton mDelivererRb1;

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
    @BindView(R.id.total_bottom)
    TextView mTotalBottom;
    @BindView(R.id.store_in_cloud)
    TextView mStoreIncloud;
    @BindView(R.id.pay_now)
    TextView mPayNow;

    AddresManagementModel.DataBean mAddress;
    SettlementModel mSettlementModel;
    String mPdId = "";
    String mSkuId = "";
    String mPdNum = "";
    int paymentGrb = 0;
    int paymentOther = 0;
    int mAngetType = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gujujin_purchase_settlement);

        Toolbar toolbar = initToolBar(getString(R.string.activity_title_gujujin_purchase_settlement));

        Intent intent = getIntent();
        if (intent.hasExtra("pdId"))
            mPdId = intent.getStringExtra("pdId");
        if (intent.hasExtra("skuId"))
            mSkuId = intent.getStringExtra("skuId");
        if (intent.hasExtra("pdNum"))
            mPdNum = intent.getStringExtra("pdNum");

        if (TextUtils.isEmpty(mPdId) || TextUtils.isEmpty(mSkuId) || TextUtils.isEmpty(mPdNum)) {
            toastShow(R.string.settlement_error_tip);
            onBackPressed();
            return;
        }

        RxView.setOnClickListeners(this, mSelectAddress, mPaymentBalance, mPaymentGRB, mStoreIncloud, mPayNow);

        try {
            JSONObject userModel = new JSONObject(sharedPreferencesHelper.getSharedPreference("user", "").toString());
            // us_agent  0:无代理，1，个人代理，2县代理，3市代 4 省代
            mAngetType = userModel.optInt("us_agent", 0);
            if (mAngetType == 2) { // 区县代理
                mDeliverer.setVisibility(View.GONE);
            } else if (mAngetType == 1) { // 个人代理
                mStoreIncloud.setVisibility(View.GONE);
            }
        } catch (JSONException e) {
        }

        userOrderSettlement();
    }

    /**
     * 单商品结算
     */
    private void userOrderSettlement() {
        showProgressDialog();
        String token = GetTokenUtils.getToken(mActivity, ApiStores.API_SERVER_ORDER_SETTLEMENT);
        addSubscription(apiStores().userOrderSettlement(token, mPdId, mSkuId, mPdNum), new ApiCallback<SettlementModel>() {
            @Override
            public void onSuccess(SettlementModel model) {
                mSettlementModel = model;
                if (model.getCode() == 1) {
                    //mData.addAll(model.getData().getList());
                    mAddress = model.getData().getAddr();
                    initAddress();

                    CartIndexBean.CartBean cartBean = model.getData().getList().get(0);
                    GlideApp.with(mActivity)
                            .load(ApiStores.API_SERVER_URL + cartBean.getPd_head_pic())
                            .transform(new CenterCrop(), new RoundTransformation(mActivity, 5))
                            .into(mShopIv);
                    mDelivererRb0.setEnabled(model.getData().getAgent_status() == 1);
                    mShopName.setText(cartBean.getPd_name());
                    mShopTotal.setText(String.format(getString(R.string.total_top_text), model.getData().getTotal()));
                    mShopNum.setText(String.format("X %s", model.getData().getPd_num()));
                    mTotalTop.setText(String.format(getString(R.string.total_top_text), model.getData().getTotal()));
                    mTotalFreight.setText(String.format(getString(R.string.total_top_text), String.valueOf(0)));
                    mTotalBottom.setText(String.format(getString(R.string.gujujin_purchase_settlement_freight), String.valueOf(0)));
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
            case R.id.store_in_cloud:
                showSettlementDialog(2);
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
                if (mAngetType == 1 && !mDelivererRb0.isChecked() && !mDelivererRb1.isChecked()) {
                    toastShow(R.string.payment_order_select_deliverer);
                    return;
                }
                showSettlementDialog(4);
                break;
            default:
                break;
        }
    }

    private void showSettlementDialog(int type) {
        if (type == 2) {
            userOrderAddOrder(type);
            return;
        }

        SettlementDialog dialog = new SettlementDialog(this);
        dialog.setCancelable(false);
        dialog.show();
        dialog.setOnActionClickListener(() -> {
            indexValleyAddOrder(type);
        });
        dialog.setmNeedShowFreight(type);
        dialog.setUsername(mAddress.getAddr_receiver());
        dialog.setUserPhone(mAddress.getAddr_tel());
        dialog.setAddressDetail(mAddress.getProvince() + mAddress.getCity() + mAddress.getTown() + mAddress.getAddr_detail());
        dialog.setFreight(mAddress.getProvince() + mAddress.getCity());
        dialog.setTotalFreight(String.valueOf(mAddress.getFee()));
    }

    /**
     * 1 ：平台发货；2：区县发货
     */
    private void indexValleyAddOrder(int type) {
        showProgressDialog();
        String token = GetTokenUtils.getToken(this, ApiStores.API_SERVER_VALLEY_ADDORDER);
        int type3 = 0;
        if (mDelivererRb0.isChecked()) {
            type3 = 2;
        } else if (mDelivererRb1.isChecked()) {
            type3 = 1;
        }
        addSubscription(apiStores().indexValleyPurchaseAddOrder(token,
                mSettlementModel.getData().getList().get(0).getId(), mSettlementModel.getData().getSku_id(),
                mSettlementModel.getData().getPd_num(), type, type3, 1, mAddress.getId()),
                new ApiCallback<String>() {
                    @Override
                    public void onSuccess(String model) {
                        // {"code":1,"msg":"订单生成成功","time":1570691487,"data":"129"}
                        try {
                            JSONObject result = new JSONObject(model);
                            if (result.optInt("code", 0) == 1) {
                                GujujinBecomeAgentPaymentActivity.startActivityForResult(mActivity, 0, result.optInt("data", 0), mAngetType);
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
     * 单商品下单：存入云仓库
     *
     * @param type 1:商城订单，2：云仓库订单，3：抢购订单
     */
    private void userOrderAddOrder(int type) {
        showProgressDialog();
        String token = GetTokenUtils.getToken(this, ApiStores.API_SERVER_ORDER_ADDORDER);
        addSubscription(apiStores().userOrderAddOrder(token, mSettlementModel.getData().getList().get(0).getId(),
                mSettlementModel.getData().getSku_id(), mSettlementModel.getData().getPd_num(),
                type, 1, mAddress.getId(), ""), new ApiCallback<String>() {
            @Override
            public void onSuccess(String model) {
                //{"code":1,"msg":"订单提交成功","time":1567926433,"data":"72"}
                try {
                    JSONObject result = new JSONObject(model);
                    if (result.getInt("code") == 1) {
                        toastShow(result.getString("msg"));
                        GujujinBecomeAgentPaymentActivity.startActivityForResult(mActivity, 0, result.optInt("data", 0), mAngetType);
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

                        mTotalFreight.setText(String.format(getString(R.string.total_top_text), String.valueOf(mAddress.getFee())));
                        mTotalBottom.setText(String.format(getString(R.string.gujujin_purchase_settlement_freight), String.valueOf(mAddress.getFee())));
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
