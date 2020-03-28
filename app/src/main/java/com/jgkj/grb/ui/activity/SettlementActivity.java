package com.jgkj.grb.ui.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.jgkj.grb.R;
import com.jgkj.grb.base.BaseActivity;
import com.jgkj.grb.itemdecoration.SpaceItemDecoration;
import com.jgkj.grb.onclick.RxView;
import com.jgkj.grb.retrofit.ApiCallback;
import com.jgkj.grb.retrofit.ApiStores;
import com.jgkj.grb.ui.adapter.SettlementAdapter;
import com.jgkj.grb.ui.dialog.SettlementDialog;
import com.jgkj.grb.ui.mvp.SettlementModel;
import com.jgkj.grb.ui.mvp.personal.AddresManagementModel;
import com.jgkj.grb.ui.mvp.shopcart.CartIndexBean;
import com.jgkj.utils.token.GetTokenUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 结算
 */
public class SettlementActivity extends BaseActivity {

    public static void start(Context context) {
        Intent intent = new Intent(context, SettlementActivity.class);
        context.startActivity(intent);
    }

    // 购物车发起结算
    public static void start(Context context, ArrayList<String> idList) {
        Intent intent = new Intent(context, SettlementActivity.class);
        intent.putExtra("cart", true);
        intent.putStringArrayListExtra("idList", idList);
        context.startActivity(intent);
    }

    // 单商品发起结算
    public static void start(Context context, String pdId, String skuId, String pdNum) {
        Intent intent = new Intent(context, SettlementActivity.class);
        intent.putExtra("cart", false);
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
    @BindView(R.id.total_top)
    TextView mTotalTop;
    @BindView(R.id.total_bottom)
    TextView mTotalBottom;
    @BindView(R.id.pay_now)
    TextView mPayNow;
    @BindView(R.id.store_in_cloud)
    TextView mStoreIncloud;
    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;

    @BindView(R.id.total_freight)
    TextView mTotalFreight;
    @BindView(R.id.leaving_message)
    TextView mLeavingMessage;

    SettlementAdapter adapter;
    List<CartIndexBean.CartBean> mData = new ArrayList<>();

    boolean isCart = false;
    AddresManagementModel.DataBean mAddress;
    List<String> mSelectIdList;
    SettlementModel mSettlementModel;

    String mPdId = "";
    String mSkuId = "";
    String mPdNum = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settlement);
        Intent intent = getIntent();

        if (intent.hasExtra("cart")) {
            isCart = intent.getBooleanExtra("cart", false);
        }
        if (isCart && intent.hasExtra("idList")) {
            mSelectIdList = intent.getStringArrayListExtra("idList");
            if (null == mSelectIdList || mSelectIdList.size() <= 0) {
                toastShow(R.string.settlement_error_tip);
                onBackPressed();
                return;
            }
        } else {
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
        }

        Toolbar toolbar = initToolBar(getString(R.string.activity_title_settlement));

        RxView.setOnClickListeners(this, mSelectAddress, mStoreIncloud, mPayNow);

        mTotalTop.setText(String.format(getString(R.string.total_top_text), "0"));
        mTotalBottom.setText(String.format(getString(R.string.total_bottom_text), "0"));
        mTotalFreight.setText(String.format(getString(R.string.total_top_text), "0.00"));
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.addItemDecoration(new SpaceItemDecoration(dp2px(1)));
        adapter = new SettlementAdapter(this, mData);
        mRecyclerView.setAdapter(adapter);

        onLazyLoad();
    }

    private void onLazyLoad() {
        if (isCart) {
            userCartSettlement();
        } else {
            userOrderSettlement();
        }
    }

    @Override
    public void onClick(Object o) {
        View v = (View) o;
        switch (v.getId()) {
            case R.id.select_address:
                AddressManagementActivity.startActivityForResult(this);
                break;
            case R.id.store_in_cloud:
                /*if (TextUtils.isEmpty(mLeavingMessage.getText().toString().trim())) {
                    toastShow(R.string.settlement_leaving_message_input_tip);
                    return;
                }*/
                showSettlementDialog(2);
                break;
            case R.id.pay_now:
                /*if (TextUtils.isEmpty(mLeavingMessage.getText().toString().trim())) {
                    toastShow(R.string.settlement_leaving_message_input_tip);
                    return;
                }*/
                showSettlementDialog(1);
                break;
            default:
                break;
        }
    }

    private void showSettlementDialog(int type) {
        if (null == mAddress || mAddress.getId() <= 0) {
            AddressManagementActivity.startActivityForResult(this);
            toastShow(R.string.payment_order_error_addr);
            return;
        }
        SettlementDialog dialog = new SettlementDialog(this);
        dialog.setCancelable(false);
        dialog.show();
        dialog.setOnActionClickListener(() -> {
            // 支付订单
            if (isCart) {
                userCartAddOrder(type);
            } else {
                userOrderAddOrder(type);
            }
        });
        dialog.setmNeedShowFreight(type);
        dialog.setUsername(mAddress.getAddr_receiver());
        dialog.setUserPhone(mAddress.getAddr_tel());
        dialog.setAddressDetail(mAddress.getProvince() + mAddress.getCity() + mAddress.getTown() + mAddress.getAddr_detail());
        dialog.setFreight(mAddress.getProvince() + mAddress.getCity());
        dialog.setTotalFreight(String.valueOf(mAddress.getFee()));
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
            } else if (requestCode == 10010) {
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
                    mData.addAll(model.getData().getList());
                    adapter.notifyDataSetChanged();
                    mAddress = model.getData().getAddr();
                    initAddress();
                    mTotalTop.setText(String.format(getString(R.string.total_top_text), model.getData().getTotal()));
                    mTotalBottom.setText(String.format(getString(R.string.total_bottom_text), /*String.valueOf(model.getData().getAll_pv())*/"0"));
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

    /**
     * 单商品下单
     *
     * @param type 1:商城订单，2：云仓库订单，3：抢购订单
     */
    private void userOrderAddOrder(int type) {
        showProgressDialog();
        String token = GetTokenUtils.getToken(this, ApiStores.API_SERVER_ORDER_ADDORDER);
        addSubscription(apiStores().userOrderAddOrder(token, mPdId, mSkuId, mPdNum, type, mAddress.getId(), mLeavingMessage.getText().toString()), new ApiCallback<String>() {
            @Override
            public void onSuccess(String model) {
                //{"code":1,"msg":"订单提交成功","time":1567926433,"data":"72"}
                try {
                    JSONObject result = new JSONObject(model);
                    if (result.getInt("code") == 1) {
                        toastShow(result.getString("msg"));
                        PaymentOrdersActivity.startActivityForResult(mActivity, type, result.getString("data"));
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
     * 购物车结算
     */
    private void userCartSettlement() {
        showProgressDialog();
        String token = GetTokenUtils.getToken(mActivity, ApiStores.API_SERVER_CART_SETTLEMENT);
        addSubscription(apiStores().userCartSettlement(token, mSelectIdList), new ApiCallback<SettlementModel>() {
            @Override
            public void onSuccess(SettlementModel model) {
                mSettlementModel = model;
                if (model.getCode() == 1) {
                    mData.addAll(model.getData().getList());
                    adapter.notifyDataSetChanged();
                    mAddress = model.getData().getAddr();
                    initAddress();
                    mTotalTop.setText(String.format(getString(R.string.total_top_text), model.getData().getTotal()));
                    mTotalBottom.setText(String.format(getString(R.string.total_bottom_text), /*String.valueOf(model.getData().getAll_pv())*/"0"));
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

    /**
     * 购物车下单
     *
     * @param type 1:商城订单，2：云仓库订单，3：抢购订单
     */
    private void userCartAddOrder(int type) {
        showProgressDialog();
        String token = GetTokenUtils.getToken(this, ApiStores.API_SERVER_CART_ADDORDER);
        addSubscription(apiStores().userCartAddOrder(token, mSelectIdList, type, mAddress.getId(), mLeavingMessage.getText().toString()), new ApiCallback<String>() {
            @Override
            public void onSuccess(String model) {
                //{"code":1,"msg":"订单提交成功","time":1567926433,"data":"72"}
                try {
                    JSONObject result = new JSONObject(model);
                    if (result.getInt("code") == 1) {
                        toastShow(result.getString("msg"));
                        PaymentOrdersActivity.startActivityForResult(mActivity, type, result.getString("data"));
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
