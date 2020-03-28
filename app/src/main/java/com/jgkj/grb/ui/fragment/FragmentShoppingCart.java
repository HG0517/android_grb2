package com.jgkj.grb.ui.fragment;

import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.jgkj.grb.R;
import com.jgkj.grb.base.BaseFragment;
import com.jgkj.grb.itemdecoration.SpaceItemDecoration;
import com.jgkj.grb.onclick.RxView;
import com.jgkj.grb.retrofit.ApiCallback;
import com.jgkj.grb.retrofit.ApiStores;
import com.jgkj.grb.ui.activity.ProductDetailsActivity;
import com.jgkj.grb.ui.activity.SettlementActivity;
import com.jgkj.grb.ui.adapter.ShoppingCartAdapter;
import com.jgkj.grb.ui.bean.SpecsModel;
import com.jgkj.grb.ui.dialog.CouponBottomDialog;
import com.jgkj.grb.ui.dialog.SpecsBottomDialog;
import com.jgkj.grb.ui.mvp.product.ProductDetailsModel;
import com.jgkj.grb.ui.mvp.shopcart.CartIndexBean;
import com.jgkj.grb.ui.mvp.shopcart.CartIndexModel;
import com.jgkj.grb.utils.Logger;
import com.jgkj.utils.token.GetTokenUtils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import butterknife.BindView;

/**
 * 购物车
 * Created by brightpoplar@163.com on 2019/7/27.
 */
public class FragmentShoppingCart extends BaseFragment {

    public static FragmentShoppingCart newInstance() {
        return new FragmentShoppingCart();
    }

    @BindView(R.id.smart_refresh_layout)
    SmartRefreshLayout mSmartRefreshLayout;

    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;

    @BindView(R.id.layout_loading)
    ConstraintLayout mLayoutLoading;
    @BindView(R.id.layout_empty)
    ConstraintLayout mLayoutEmpty;
    @BindView(R.id.layout_error)
    ConstraintLayout mLayoutError;

    @BindView(R.id.toolbar_right_title)
    TextView mToolbarRightTitle;
    public boolean isEditMode = false;

    @BindView(R.id.all_select)
    CheckBox mAllSelect;
    @BindView(R.id.total_left)
    TextView mTotalLeft;
    @BindView(R.id.total_top)
    TextView mTotalTop;
    @BindView(R.id.total_bottom)
    TextView mTotalBottom;
    @BindView(R.id.total_right)
    TextView mTotalRight;

    @BindView(R.id.bottom_cl)
    ConstraintLayout mBottomCl;
    @BindView(R.id.total_collect)
    TextView mTotalCollect;
    @BindView(R.id.total_delete)
    TextView mTotalDelete;

    private int mNum = 0;
    private float mTotalPrice = 0f;
    private float mTotalPv = 0f;

    private ShoppingCartAdapter mAdapter;
    private CartIndexModel mDatas;
    private int page = 1;
    private int size = 10;

    ArrayList<String> mSelectIdList = new ArrayList<>();

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_shopping_cart;
    }

    @Override
    public void init(Bundle savedInstanceState) {
        getRootView().findViewById(R.id.topPanel).setPadding(0, getStatusBarHeight() + dp2px(5), 0, dp2px(5));

        initSmartRefreshLayout();
        initRecyclerView();
        initLoadingLayout();
        showLoadingLayout();

        RxView.setOnClickListeners(this, mToolbarRightTitle, mAllSelect, mTotalRight, mTotalCollect, mTotalDelete);
        initEditMode();
        initBottomView(mNum, mTotalPrice, mTotalPv);
    }

    private void initBottomView(int num, float price, float pv) {
        mTotalTop.setText(String.format(getString(R.string.total_top_text), String.valueOf(price)));
        mTotalBottom.setText(String.format(getString(R.string.total_bottom_text), String.valueOf(pv)));
        mTotalRight.setText(String.format(getString(R.string.total_right_text), num));
        mTotalDelete.setText(String.format(getString(R.string.total_delete_text), num));
    }

    @Override
    public void onClick(Object o) {
        View v = (View) o;
        switch (v.getId()) {
            case R.id.total_right: // 结算
                Logger.i("TAG_total_right", mSelectIdList.toString());
                if (isLogin() && null != mSelectIdList && mSelectIdList.size() > 0) {
                    SettlementActivity.start(mActivity, mSelectIdList);
                    /*// is_realname  实名情况：0:未认证  1认证中 2已认证 3驳回
                    int is_realname = isRealname();
                    if (is_realname == 2) {
                        SettlementActivity.start(mActivity, mSelectIdList);
                    } else if (is_realname == 1) {
                        ApplyResultActivity.start(mActivity, 2);
                    } else if (is_realname == 3) {
                        showUnrealNameDialog(getString(R.string.real_name_rejected));
                    } else {
                        showUnrealNameDialog("");
                    }*/
                }
                break;
            case R.id.total_collect:
                Logger.i("TAG_total_collect", mSelectIdList.toString());
                if (isLogin() && null != mSelectIdList && mSelectIdList.size() > 0) {
                    cartCollection();
                }
                break;
            case R.id.total_delete:
                Logger.i("TAG_total_delete", mSelectIdList.toString());
                if (isLogin() && null != mSelectIdList && mSelectIdList.size() > 0) {
                    cartDelete();
                }
                break;
            case R.id.toolbar_right_title:
                isEditMode = !isEditMode;
                initEditMode();
                break;
            case R.id.all_select:
                mSelectIdList.clear();
                if (mAllSelect.isChecked()) { // 全选
                    mNum = 0;
                    mTotalPrice = 0f;
                    mTotalPv = 0f;
                    int size = mDatas.getData().size();
                    for (int i = 0; i < size; i++) {
                        // 失效的不计算
                        if (mDatas.getData().get(i).isInvalid()) {
                            continue;
                        }
                        mDatas.getData().get(i).setSelect(true);
                        int size1 = mDatas.getData().get(i).getList().size();
                        for (int j = 0; j < size1; j++) {
                            mNum++;
                            mDatas.getData().get(i).getList().get(j).setSelect(true);
                            if (mDatas.getData().get(i).getList().get(j).getPaytype() == 2)
                                mTotalPrice = mTotalPrice + mDatas.getData().get(i).getList().get(j).getPd_num() * Float.parseFloat(/*mDatas.getData().get(i).getList().get(j).getPd_price()*/"0"); // num * price
                            mTotalPv = mTotalPv + mDatas.getData().get(i).getList().get(j).getPd_num() * Float.parseFloat(/*mDatas.getData().get(i).getList().get(j).getPd_pv()*/"0"); // num * price

                            mSelectIdList.add(String.valueOf(mDatas.getData().get(i).getList().get(j).getId()));
                        }
                    }
                } else { // 反全选
                    mNum = 0;
                    mTotalPrice = 0f;
                    mTotalPv = 0f;
                    int size = mDatas.getData().size();
                    for (int i = 0; i < size; i++) {
                        // 失效的不计算
                        if (mDatas.getData().get(i).isInvalid()) {
                            continue;
                        }
                        mDatas.getData().get(i).setSelect(false);
                        int size1 = mDatas.getData().get(i).getList().size();
                        for (int j = 0; j < size1; j++) {
                            mDatas.getData().get(i).getList().get(j).setSelect(false);
                        }
                    }
                }
                mAdapter.notifyDataSetChanged();
                initBottomView(mNum, mTotalPrice, mTotalPv);
                break;
            default:
                break;
        }
    }

    public void initEditMode() {
        if (isEditMode) {
            mToolbarRightTitle.setText(getString(R.string.shop_cart_top_right_1));
            mTotalLeft.setVisibility(View.GONE);
            mTotalTop.setVisibility(View.GONE);
            mTotalBottom.setVisibility(View.GONE);
            mTotalRight.setVisibility(View.GONE);
            mTotalCollect.setVisibility(View.VISIBLE);
            mTotalDelete.setVisibility(View.VISIBLE);
        } else {
            mToolbarRightTitle.setText(getString(R.string.shop_cart_top_right_0));
            mTotalLeft.setVisibility(View.GONE); // VISIBLE
            mTotalTop.setVisibility(View.GONE); // VISIBLE
            mTotalBottom.setVisibility(View.GONE); // VISIBLE
            mTotalRight.setVisibility(View.VISIBLE);
            mTotalCollect.setVisibility(View.GONE);
            mTotalDelete.setVisibility(View.GONE);
        }
    }

    private void initLoadingLayout() {
        ((ImageView) mLayoutEmpty.findViewById(R.id.empty_image)).setImageResource(R.mipmap.ic_layout_empty_shop_cart);
        ((TextView) mLayoutEmpty.findViewById(R.id.empty_text)).setText(getString(R.string.layout_empty_shop_cart));
        mLayoutEmpty.findViewById(R.id.retry_button).setBackgroundResource(R.mipmap.ic_layout_empty_shop_cart_action);
    }

    private void showLoadingLayout() {
        mRecyclerView.setVisibility(View.GONE);
        mLayoutLoading.setVisibility(View.VISIBLE);
        mLayoutEmpty.setVisibility(View.GONE);
        mLayoutError.setVisibility(View.GONE);
    }

    private void showContentLayout() {
        mBottomCl.setVisibility(View.VISIBLE);
        mRecyclerView.setVisibility(View.VISIBLE);
        mLayoutLoading.setVisibility(View.GONE);
        mLayoutEmpty.setVisibility(View.GONE);
        mLayoutError.setVisibility(View.GONE);
    }

    private void showEmptyLayout() {
        mBottomCl.setVisibility(View.GONE);
        mRecyclerView.setVisibility(View.GONE);
        mLayoutLoading.setVisibility(View.GONE);
        mLayoutEmpty.setVisibility(View.VISIBLE);
        mLayoutError.setVisibility(View.GONE);
    }

    private void showErrorLayout() {
        mBottomCl.setVisibility(View.GONE);
        mRecyclerView.setVisibility(View.GONE);
        mLayoutLoading.setVisibility(View.GONE);
        mLayoutEmpty.setVisibility(View.GONE);
        mLayoutError.setVisibility(View.VISIBLE);
    }

    private void initSmartRefreshLayout() {
        mSmartRefreshLayout.setEnableRefresh(true);
        mSmartRefreshLayout.setEnableLoadMore(true);
        mSmartRefreshLayout.setEnableAutoLoadMore(false);
        mSmartRefreshLayout.setOnRefreshListener(refreshLayout -> {
            // 刷新数据
            refreshLayout.setEnableLoadMore(true);
            isEditMode = false;
            initEditMode();
            page = 1;
            mDatas.getData().clear();
            mAllSelect.setChecked(false);
            initBottomView(mNum = 0, mTotalPrice = 0f, mTotalPv = 0f);
            onLazyLoad();
        });
        mSmartRefreshLayout.setOnLoadMoreListener(refreshLayout -> {
            // 加载数据
            onLazyLoad();
        });
    }

    private void initRecyclerView() {
        mDatas = new CartIndexModel();
        mDatas.setData(new ArrayList<>());
        mAdapter = new ShoppingCartAdapter(mActivity, mDatas.getData());
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mActivity));
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.addItemDecoration(new SpaceItemDecoration(dp2px(10)));
        mAdapter.setOnItemCheckedChangeListener(new ShoppingCartAdapter.OnItemCheckedChangeListener() {
            @Override
            public void onCheckedChange(int parentPos, int childPos, int num, float price, float pv) {
                Logger.i("TAG_onCheckedChange", childPos + " , " + num);
                if (childPos < 0) { // item 上的反/全选
                    if (num < 0) {
                        for (int i = 0; i < mDatas.getData().get(parentPos).getList().size(); i++) {
                            mSelectIdList.remove(String.valueOf(mDatas.getData().get(parentPos).getList().get(i).getId()));
                        }
                    } else {
                        for (int i = 0; i < mDatas.getData().get(parentPos).getList().size(); i++) {
                            if (mSelectIdList.indexOf(String.valueOf(mDatas.getData().get(parentPos).getList().get(i).getId())) >= 0)
                                continue;
                            mSelectIdList.add(String.valueOf(mDatas.getData().get(parentPos).getList().get(i).getId()));
                        }
                    }
                } else { // 内 item 上的选择
                    if (num < 0) {
                        mSelectIdList.remove(String.valueOf(mDatas.getData().get(parentPos).getList().get(childPos).getId()));
                    } else {
                        if (mSelectIdList.indexOf(String.valueOf(mDatas.getData().get(parentPos).getList().get(childPos).getId())) < 0)
                            mSelectIdList.add(String.valueOf(mDatas.getData().get(parentPos).getList().get(childPos).getId()));
                    }
                }

                mNum = mNum + num;
                mTotalPrice = mTotalPrice + price;
                mTotalPv = mTotalPv + pv;
                int size = 0;
                for (int i = 0; i < mDatas.getData().size(); i++) {
                    // 失效的不计算
                    if (!mDatas.getData().get(i).isInvalid()) {
                        int size1 = mDatas.getData().get(i).getList().size();
                        for (int j = 0; j < size1; j++) {
                            size++;
                        }
                    }
                }
                mAllSelect.setChecked(mNum == size);
                initBottomView(mNum, mTotalPrice, mTotalPv);
            }

            @Override
            public void onNumCut(int parentPos, int childPos, int num, float price, float pv) {
                showProgressDialog();
                String token = GetTokenUtils.getToken(mActivity, ApiStores.API_SERVER_CART_CUTNUM);
                CartIndexBean.CartBean cartBean = mDatas.getData().get(parentPos).getList().get(childPos);
                addSubscription(apiStores().userCartCutnum(token, cartBean.getId()), new ApiCallback<String>() {
                    @Override
                    public void onSuccess(String model) {
                        // {"code":1,"msg":"成功","time":1567843512,"data":""}
                        try {
                            JSONObject result = new JSONObject(model);
                            if (result.getInt("code") == 1) {
                                cartBean.setPd_num(cartBean.getPd_num() + num);
                                mAdapter.notifyDataSetChanged();
                                mNum = mNum + num;
                                mTotalPrice = mTotalPrice + price;
                                mTotalPv = mTotalPv + pv;
                                initBottomView(mNum, mTotalPrice, mTotalPv);
                            }
                            toastShow(result.getString("msg"));
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
            public void onNumAdd(int parentPos, int childPos, int num, float price, float pv) {
                showProgressDialog();
                String token = GetTokenUtils.getToken(mActivity, ApiStores.API_SERVER_CART_ADDNUM);
                CartIndexBean.CartBean cartBean = mDatas.getData().get(parentPos).getList().get(childPos);
                addSubscription(apiStores().userCartAddnum(token, cartBean.getId()), new ApiCallback<String>() {
                    @Override
                    public void onSuccess(String model) {
                        try {
                            JSONObject result = new JSONObject(model);
                            if (result.getInt("code") == 1) {
                                cartBean.setPd_num(cartBean.getPd_num() + num);
                                mAdapter.notifyDataSetChanged();
                                mNum = mNum + num;
                                mTotalPrice = mTotalPrice + price;
                                mTotalPv = mTotalPv + pv;
                                initBottomView(mNum, mTotalPrice, mTotalPv);
                            }
                            toastShow(result.getString("msg"));
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
            public void onSecurities(int position) {
                ProductDetailsModel mModel = new ProductDetailsModel();
                CouponBottomDialog bottomListDialog = CouponBottomDialog.newInstance((ArrayList<ProductDetailsModel.DataBean.CouponBean>) mModel.getData().getCoupon());
                bottomListDialog.showDialog(getFragmentManager());
                bottomListDialog.setOnBottomDialogListener(new CouponBottomDialog.BottomDialogListener() {
                    @Override
                    public void onConfirm() {
                    }

                    @Override
                    public void onItemClick(int position) {
                        if (!mModel.getData().getCoupon().get(position).isSecurity()) {
                            mModel.getData().getCoupon().get(position).setSecurity(true);
                            bottomListDialog.getRecyclerView().getAdapter().notifyDataSetChanged();
                        }
                    }
                });
            }

            @Override
            public void onSpecs(int position, int childPos) {
                SpecsModel specsModel = new SpecsModel();
                List<SpecsModel.SpecsBean> list = new ArrayList<>();

                SpecsModel.SpecsBean specsBean = new SpecsModel.SpecsBean();
                specsBean.setId(0);
                specsBean.setName("规格");
                List<ProductDetailsModel.DataBean.SkusBean> listChild = new ArrayList<>();
                specsBean.setList(listChild);
                list.add(specsBean);

                specsModel.setList(list);

                SpecsBottomDialog dialog = SpecsBottomDialog.newInstance(R.mipmap.ic_close_circle_white, specsModel, null);
                dialog.showDialog(getFragmentManager());
                dialog.setOnBottomDialogListener(new SpecsBottomDialog.BottomDialogListener() {
                    @Override
                    public void onCancel() {
                    }

                    @Override
                    public void onBuyClickListener() {

                    }

                    @Override
                    public void onNumChangedListener(int count) {
                        Logger.i("TAG", "onNumChangedListener: " + count);
                    }

                    @Override
                    public void OnSelectedClickListener(View view, int pIndex, int cIndex) {
                        Logger.i("TAG", "OnSelectedClickListener: " + pIndex + " , " + cIndex);
                    }
                });
            }

            @Override
            public void onItemClick(View v, int position, int childPos) {
                ProductDetailsActivity.start(mActivity, String.valueOf(mDatas.getData().get(position).getList().get(childPos).getPd_id()));
            }

            @Override
            public void onItemClick(View view, int position) {
            }
        });
    }

    /**
     * 多选收藏
     */
    private void cartCollection() {
        showProgressDialog();
        String token = GetTokenUtils.getToken(mActivity, ApiStores.API_SERVER_CART_COLLECTION);
        addSubscription(apiStores().userCartCollection(token, mSelectIdList), new ApiCallback<String>() {
            @Override
            public void onSuccess(String model) {
                try {
                    JSONObject result = new JSONObject(model);
                    if (result.getInt("code") == 1) {
                        toastShow(R.string.collect_success_text);
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
     * 多选删除
     */
    private void cartDelete() {
        showProgressDialog();
        String token = GetTokenUtils.getToken(mActivity, ApiStores.API_SERVER_CART_DELCART);
        addSubscription(apiStores().userCartDelete(token, mSelectIdList), new ApiCallback<String>() {
            @Override
            public void onSuccess(String model) {
                try {
                    JSONObject result = new JSONObject(model);
                    if (result.getInt("code") == 1) {
                        for (int i = 0; i < mSelectIdList.size(); i++) {
                            Iterator<CartIndexBean> iteratorItem = mDatas.getData().iterator();
                            while (iteratorItem.hasNext()) {
                                CartIndexBean nextItem = iteratorItem.next();
                                List<CartIndexBean.CartBean> list = nextItem.getList();
                                Iterator<CartIndexBean.CartBean> iterator = list.iterator();
                                while (iterator.hasNext()) {
                                    CartIndexBean.CartBean next = iterator.next();
                                    if (TextUtils.equals(mSelectIdList.get(i), String.valueOf(next.getId()))) {

                                        if (next.isSelect()) {
                                            float price = next.getPaytype() == 2 ? next.getPd_num() * Float.parseFloat(/*next.getPd_price()*/"0") : 0; // num * price
                                            float pv = next.getPd_num() * Float.parseFloat(/*next.getPd_pv()*/"0"); // num * pv

                                            mNum = mNum - next.getPd_num();
                                            mTotalPrice = mTotalPrice - price;
                                            mTotalPv = mTotalPv - pv;
                                            initBottomView(mNum, mTotalPrice, mTotalPv);
                                        }
                                        iterator.remove();
                                    }
                                }
                                if (list.size() == 0) {
                                    iteratorItem.remove();
                                }
                            }
                            if (mDatas.getData().size() == 0) {
                                page = 1;
                                mAllSelect.setChecked(false);
                                initBottomView(mNum = 0, mTotalPrice = 0f, mTotalPv = 0f);
                                showEmptyLayout();
                            }
                        }
                        toastShow(result.getString("msg"));
                        mAdapter.notifyDataSetChanged();
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
    protected void onLazyLoad() {
        if (isLogin()) {
            showProgressDialog();
            String token = GetTokenUtils.getToken(mActivity, ApiStores.API_SERVER_CART_INDEX);
            addSubscription(apiStores().userCartIndex(token), new ApiCallback<CartIndexModel>() {
                @Override
                public void onSuccess(CartIndexModel model) {
                    if (model.getCode() == 1) {
                        page++;

                        mDatas.getData().addAll(model.getData());
                        mAdapter.notifyDataSetChanged();
                        if (mDatas.getData().size() > 0) {
                            showContentLayout();
                        } else {
                            showEmptyLayout();
                        }
                    } else {
                        if (page == 1)
                            showErrorLayout();
                    }
                }

                @Override
                public void onFailure(String msg) {
                    toastShow(msg);
                }

                @Override
                public void onFinish() {
                    dismissProgressDialog();
                    mSmartRefreshLayout.finishRefresh(1, true);
                    mSmartRefreshLayout.finishLoadMore(1, true, mDatas.getData().size() < size * page);
                }
            });
        } else {
            showEmptyLayout();
            mSmartRefreshLayout.finishRefresh(1, true);
            mSmartRefreshLayout.finishLoadMore(1, true, true);
        }
    }

    public void autoRefresh(boolean isLogin) {
        if (isLogin) {
            mSmartRefreshLayout.autoRefresh(300);
        } else {
            mSmartRefreshLayout.setEnableLoadMore(true);
            page = 1;
            mDatas.getData().clear();
            mAllSelect.setChecked(false);
            initBottomView(mNum = 0, mTotalPrice = 0f, mTotalPv = 0f);
            mAdapter.notifyDataSetChanged();
            showEmptyLayout();
        }
    }
}
