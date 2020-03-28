package com.jgkj.grb.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.FrameLayout;

import com.jgkj.grb.R;
import com.jgkj.grb.base.BaseActivity;
import com.jgkj.grb.onclick.RxView;
import com.jgkj.grb.retrofit.ApiCallback;
import com.jgkj.grb.retrofit.ApiStores;
import com.jgkj.grb.ui.adapter.ProductEvaluaFlowTagAdapter;
import com.jgkj.grb.ui.bean.SpecsModel;
import com.jgkj.grb.ui.dialog.SpecsBottomDialog;
import com.jgkj.grb.ui.fragment.FragmentProductEvaluation;
import com.jgkj.grb.ui.mvp.product.ProductDetailsModel;
import com.jgkj.grb.utils.Logger;
import com.jgkj.grb.view.flowtag.FlowTagLayout;
import com.jgkj.utils.token.GetTokenUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;

/**
 * 商品评价
 */
public class ProductEvaluationActivity extends BaseActivity {

    public static void start(Context context, ProductDetailsModel.DataBean dataBean,
                             ProductDetailsModel.DataBean.SkusBean skusBean, int selectNum) {
        Intent intent = new Intent(context, ProductEvaluationActivity.class);
        Bundle bundle = new Bundle();
        bundle.putParcelable("dataBean", dataBean);
        bundle.putParcelable("skusBean", skusBean);
        bundle.putInt("selectNum", selectNum);
        intent.putExtra("bundle", bundle);
        context.startActivity(intent);
    }

    @BindView(R.id.flowTagLayout)
    FlowTagLayout mFlowTagLayout;

    // 客服
    @BindView(R.id.customer_service)
    FrameLayout mCustomerService;
    // 购物车
    @BindView(R.id.shop_cart)
    FrameLayout mShopCart;
    // 添加到购物车
    @BindView(R.id.add_to_cart)
    FrameLayout mAddToCart;
    // 立即购买
    @BindView(R.id.buy_now)
    FrameLayout mBuyNow;

    Fragment[] mList;

    ProductDetailsModel.DataBean mDataBean;
    ProductDetailsModel.DataBean.SkusBean mSkusBean;
    int mSelectNum = 1;

    String[] tabs = {};
    List<String> mTabList = new ArrayList<>();
    ProductEvaluaFlowTagAdapter mFlowTagAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_evaluation);

        Intent intent = getIntent();
        if (!intent.hasExtra("bundle")) {
            toastShow(R.string.open_activity_error_option);
            onBackPressed();
            return;
        }
        Bundle bundle = intent.getBundleExtra("bundle");
        if (null == bundle) {
            toastShow(R.string.open_activity_error_option);
            onBackPressed();
            return;
        }
        mDataBean = bundle.getParcelable("dataBean");
        mSkusBean = bundle.getParcelable("skusBean");
        mSelectNum = bundle.getInt("selectNum", 1);
        if (null == mDataBean) {
            toastShow(R.string.open_activity_error_option);
            onBackPressed();
            return;
        }

        Toolbar toolbar = initToolBar(getString(R.string.activity_title_product_evaluation));

        initFlowTagLayout();

        RxView.setOnClickListeners(this, mCustomerService, mShopCart, mAddToCart, mBuyNow);
    }

    private void initFlowTagLayout() {
        tabs = getResources().getStringArray(R.array.tab_product_evaluation);
        for (int i = 0; i < tabs.length; i++) {
            if (i > 1) {
                mTabList.add(String.format(tabs[i], 0));
            } else {
                mTabList.add(tabs[i]);
            }
        }
        mFlowTagAdapter = new ProductEvaluaFlowTagAdapter<>(this);
        mFlowTagLayout.setAdapter(mFlowTagAdapter);
        mFlowTagLayout.setTagCheckedMode(FlowTagLayout.FLOW_TAG_CHECKED_SINGLE);
        mFlowTagLayout.setOnTagSelectListener((parent, selectedList) -> {
            if (null != selectedList && selectedList.size() > 0) {
                int size1 = selectedList.size();
                for (int i = 0; i < size1; i++) {
                    Logger.i("TAG", "select = " + mTabList.get(selectedList.get(i)));
                    showFragment(selectedList.get(i));
                }
            } else {
                Logger.i("TAG", "select none");
            }
        });
        mFlowTagAdapter.onlyAddAll(mTabList);

        initFragments(mTabList);
    }

    public void onRefreshLayout(int count1, int count2, int count3) {
        Logger.i("TAG_onRefreshLayout", "count1 = " + count1 + " ，count2 = " + count2 + " ，count3 = " + count3);
        mFlowTagAdapter.getDataList().set(2, String.format(tabs[2], count1));
        mFlowTagAdapter.getDataList().set(3, String.format(tabs[3], count2));
        mFlowTagAdapter.getDataList().set(4, String.format(tabs[4], count3));
        mFlowTagAdapter.notifyDataSetChanged();
    }

    private void initFragments(List<String> list) {
        mList = new Fragment[list.size()];
        showFragment(0);
    }

    private void showFragment(int position) {
        FragmentManager supportFragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = supportFragmentManager.beginTransaction();
        if (null == mList[position]) {
            mList[position] = FragmentProductEvaluation.newInstance(String.valueOf(mDataBean.getId()), position > 1 ? String.valueOf(position - 1) : "");
            fragmentTransaction.add(R.id.contentPanel, mList[position]);
        }
        hideAllFragment();
        fragmentTransaction.show(mList[position]);
        fragmentTransaction.commit();
    }

    private void hideAllFragment() {
        FragmentManager supportFragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = supportFragmentManager.beginTransaction();
        for (Fragment fragment : mList) {
            if (null != fragment) {
                fragmentTransaction.hide(fragment);
            }
        }
        fragmentTransaction.commit();
    }

    @Override
    public void onClick(Object o) {
        View v = (View) o;
        switch (v.getId()) {
            case R.id.customer_service: // 客服
                huanXinKeFu();
                break;
            case R.id.shop_cart: // 购物车
                ShoppingCartActivity.start(mActivity);
                break;
            case R.id.add_to_cart: // 添加到购物车
                if (isLogin())
                    userCartAdd();
                break;
            case R.id.buy_now: // 立即购买
                if (isLogin())
                    userBuyNow();
                break;
            default:
                break;
        }
    }

    /**
     * 立即购买
     */
    private void userBuyNow() {
        if (null == mSkusBean) {
            getSpecs(2);
            toastShow(R.string.product_details_spec_tip);
            return;
        }
        SettlementActivity.start(mActivity, String.valueOf(mDataBean.getId()), String.valueOf(mSkusBean.getId()), String.valueOf(mSelectNum));
        /*// is_realname  实名情况：0:未认证  1认证中 2已认证 3驳回
        int is_realname = isRealname();
        if (is_realname == 2) {
            SettlementActivity.start(mActivity, String.valueOf(mDataBean.getId()), String.valueOf(mSkusBean.getId()), String.valueOf(mSelectNum));
        } else if (is_realname == 1) {
            ApplyResultActivity.start(mActivity, 2);
        } else if(is_realname == 3){
            showUnrealNameDialog(getString(R.string.real_name_rejected));
        } else {
            showUnrealNameDialog();
        }*/
    }

    /**
     * 添加到购物车：pd_id 商品id，sku_id 规格id，num 商品数量，pd_spec  规格名称
     */
    private void userCartAdd() {
        if (null == mDataBean) {
            toastShow(R.string.product_details_cart_add_error_tip);
            return;
        }
        if (null == mSkusBean) {
            getSpecs(1);
            toastShow(R.string.product_details_spec_tip);
            return;
        }

        showProgressDialog();
        String token = GetTokenUtils.getToken(this, ApiStores.API_SERVER_CART_ADD);
        Map<String, Object> body = new HashMap<>();
        body.put("pd_id", mDataBean.getId());
        body.put("sku_id", mSkusBean.getId());
        body.put("num", Math.max(mSelectNum, 1));
        body.put("pd_spec", mSkusBean.getSku_name());
        addSubscription(apiStores().userCartAdd(token, body), new ApiCallback<String>() {
            @Override
            public void onSuccess(String model) {
                try {
                    JSONObject result = new JSONObject(model);
                    if (result.getInt("code") == 1) {
                        toastShow(result.getString("msg"));
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
     * 选择规格弹出框：0 选择，1 添加到购物车， 2 立即购买
     */
    private void getSpecs(int type) {
        if (null != mDataBean && null != mDataBean.getSkus() && mDataBean.getSkus().size() > 0) {
            SpecsModel specsModel = new SpecsModel();
            List<SpecsModel.SpecsBean> list = new ArrayList<>();
            SpecsModel.SpecsBean specsBean = new SpecsModel.SpecsBean();
            specsBean.setId(0);
            specsBean.setName(getString(R.string.apply_after_exchange_specs));
            specsBean.setList(mDataBean.getSkus());
            list.add(specsBean);
            specsModel.setList(list);

            SpecsBottomDialog dialog = SpecsBottomDialog.newInstance(R.mipmap.ic_close_circle_white, specsModel, mDataBean);
            dialog.showDialog(getSupportFragmentManager());
            dialog.setOnBottomDialogListener(new SpecsBottomDialog.BottomDialogListener() {
                @Override
                public void onCancel() {
                }

                @Override
                public void onBuyClickListener() {
                    Logger.i("TAG_", "选择规格 onConfirm：" + type);
                    if (type == 1) {
                        userCartAdd();
                    } else if (type == 2) {
                        userBuyNow();
                    }
                }

                @Override
                public void onNumChangedListener(int count) {
                    mSelectNum = count;
                }

                @Override
                public void OnSelectedClickListener(View view, int pIndex, int cIndex) {
                    if (cIndex == -1) {
                        dialog.setTotalTop(mDataBean.getPd_total());
                        dialog.setTotalBottom(dialog.getTotalBottom(mDataBean));
                    } else {
                        mSkusBean = mDataBean.getSkus().get(cIndex);
                        dialog.setTotalTop(mSkusBean.getSku_total());
                        dialog.setTotalBottom(dialog.getTotalBottom(mDataBean, mSkusBean));
                    }
                }
            });
        }
    }

}
