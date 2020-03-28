package com.jgkj.grb.ui.fragment;

import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.jgkj.grb.R;
import com.jgkj.grb.base.BaseFragment;
import com.jgkj.grb.retrofit.ApiCallback;
import com.jgkj.grb.retrofit.ApiStores;
import com.jgkj.grb.ui.activity.ApplyAfterExchangeDetailsActivity;
import com.jgkj.grb.ui.activity.ApplyAfterFailedDetailsActivity;
import com.jgkj.grb.ui.activity.ApplyAfterRefundDetailsActivity;
import com.jgkj.grb.ui.activity.ApplyAfterSaleActivity;
import com.jgkj.grb.ui.activity.ApplyRefundActivity;
import com.jgkj.grb.ui.activity.ApplyRefundDetailsActivity;
import com.jgkj.grb.ui.activity.OrderDetailsActivity;
import com.jgkj.grb.ui.activity.PaymentOrdersActivity;
import com.jgkj.grb.ui.activity.PaymentOrdersCloudActivity;
import com.jgkj.grb.ui.activity.PublishCommentsActivity;
import com.jgkj.grb.ui.activity.WebViewActivity;
import com.jgkj.grb.ui.adapter.OrderManagementAdapter;
import com.jgkj.grb.ui.dialog.CloudWarehouseDeliveryDialog;
import com.jgkj.grb.ui.dialog.FillInLogisticsDialog;
import com.jgkj.grb.ui.gujujin.activity.GujujinBecomeAgentPaymentActivity;
import com.jgkj.grb.ui.gujujin.activity.GujujinUploadCredentialsActivity;
import com.jgkj.grb.ui.mvp.OrderManagementModel;
import com.jgkj.grb.utils.Logger;
import com.jgkj.utils.token.GetTokenUtils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 我的订单：
 * type   ：1 商城订单，2 云仓库订单，3 抢购订单，6 云仓库，7 退款/售后订单，4：谷聚金平台订单，5：谷聚金区/县订单，9 谷聚金发货
 * status ：0 全部，1 待付款，3 待发货，4 待收货，5 待评价         8 搜索                                             0 待处理，1 完成
 * # 订单状态 0待付款 1支付审核 2退款审核  3待发货 4待收货 5待评价 6已完成 7售后 8已取消 9 待上传凭证
 * Created by brightpoplar@163.com on 2019/8/22.
 */
public class FragmentOrderManagementPage extends BaseFragment {

    public static FragmentOrderManagementPage newInstance(int type, int status) {
        FragmentOrderManagementPage fragmentOrderManagementPage = new FragmentOrderManagementPage();
        Bundle bundle = new Bundle();
        bundle.putInt("type", type);
        bundle.putInt("status", status);
        fragmentOrderManagementPage.setArguments(bundle);
        return fragmentOrderManagementPage;
    }

    public static FragmentOrderManagementPage newInstance(String searchKey, int type, int status) {
        FragmentOrderManagementPage fragmentOrderManagementPage = new FragmentOrderManagementPage();
        Bundle bundle = new Bundle();
        bundle.putString("searchKey", searchKey);
        bundle.putInt("type", type);
        bundle.putInt("status", status);
        fragmentOrderManagementPage.setArguments(bundle);
        return fragmentOrderManagementPage;
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

    private OrderManagementAdapter mAdapter;
    private List<OrderManagementModel.DataBean> mList = new ArrayList<>();
    private int page = 1;
    private int size = 10;

    private String mSearchKey = "";
    private int mType = 0;
    private int mStatus = 0;

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_order_management_page;
    }

    @Override
    public void init(Bundle savedInstanceState) {
        Bundle arguments = getArguments();
        if (null != arguments) {
            mSearchKey = arguments.getString("searchKey", "");
            mType = arguments.getInt("type", 0);
            mStatus = arguments.getInt("status", 0);
        }
        if (mType <= 0) {
            toastShow(R.string.open_activity_error_option);
            return;
        }

        initSmartRefreshLayout();
        initRecyclerView();
        initLoadingLayout();
        showLoadingLayout();
    }

    private void initSmartRefreshLayout() {
        mSmartRefreshLayout.setEnableRefresh(true);
        mSmartRefreshLayout.setEnableLoadMore(true);
        mSmartRefreshLayout.setEnableAutoLoadMore(false);
        mSmartRefreshLayout.setOnRefreshListener(refreshLayout -> {
            // 刷新数据
            refreshLayout.setEnableLoadMore(true);
            page = 1;
            onLazyLoad();
        });
        mSmartRefreshLayout.setOnLoadMoreListener(refreshLayout -> {
            // 加载数据
            onLazyLoad();
        });
    }

    private void initLoadingLayout() {
        ((ImageView) mLayoutEmpty.findViewById(R.id.empty_image)).setImageResource(R.mipmap.ic_layout_empty_order_management);
        ((TextView) mLayoutEmpty.findViewById(R.id.empty_text)).setText(R.string.order_view_empty);
    }

    private void showLoadingLayout() {
        mRecyclerView.setVisibility(View.GONE);
        mLayoutLoading.setVisibility(View.VISIBLE);
        mLayoutEmpty.setVisibility(View.GONE);
        mLayoutError.setVisibility(View.GONE);
    }

    private void showContentLayout() {
        mRecyclerView.setVisibility(View.VISIBLE);
        mLayoutLoading.setVisibility(View.GONE);
        mLayoutEmpty.setVisibility(View.GONE);
        mLayoutError.setVisibility(View.GONE);
    }

    private void showEmptyLayout() {
        mRecyclerView.setVisibility(View.GONE);
        mLayoutLoading.setVisibility(View.GONE);
        mLayoutEmpty.setVisibility(View.VISIBLE);
        mLayoutError.setVisibility(View.GONE);
    }

    private void showErrorLayout() {
        mRecyclerView.setVisibility(View.GONE);
        mLayoutLoading.setVisibility(View.GONE);
        mLayoutEmpty.setVisibility(View.GONE);
        mLayoutError.setVisibility(View.VISIBLE);
    }

    @Override
    protected void onLazyLoad() {
        if (mStatus == 8) { // 搜索订单
            String token = GetTokenUtils.getToken(mActivity, ApiStores.API_SERVER_ORDER_SEARCH);
            addSubscription(apiStores().orderSearch(token, mSearchKey, mType), getApiCallback());
        } else if (mType == 1 || mType == 2 || mType == 3) { // 我的订单
            String token = GetTokenUtils.getToken(mActivity, ApiStores.API_SERVER_ORDER_INDEX);
            addSubscription(apiStores().indexOrderIndex(token, mType, mStatus, page, size), getApiCallback());
        } else if (mType == 6) { // 云仓库
            String token = GetTokenUtils.getToken(mActivity, ApiStores.API_SERVER_ORDER_CLOUDLIST);
            addSubscription(apiStores().orderCloudlist(token, page, size), getApiCallback());
        } else if (mType == 7) { // 退款/售后订单
            String token = GetTokenUtils.getToken(mActivity, ApiStores.API_SERVER_ORDER_BACKLIST);
            addSubscription(apiStores().indexOrderBacklist(token, mStatus, page, size), getApiCallback());
        } else if (mType == 4 || mType == 5) { // 谷聚金：我的订单
            int type = 1;
            if (mType == 5) {
                type = 2;
            }
            String token = GetTokenUtils.getToken(mActivity, ApiStores.API_SERVER_VALLEY_ORDER);
            addSubscription(apiStores().indexValleyOrder(token, type, mStatus, page, size), getApiCallback());
        } else if (mType == 9) { // 谷聚金：发货订单
            String token = GetTokenUtils.getToken(mActivity, ApiStores.API_SERVER_ORDER_SENDLIST);
            addSubscription(apiStores().indexOrderSendlist(token, mStatus, page, size), getApiCallback());
        }
    }

    public void onRefreshSearch(String searchKey, int type, int status) {
        mSearchKey = searchKey;
        mType = type;
        mStatus = status;

        if (mStatus == 8) {
            mSmartRefreshLayout.setEnableLoadMore(true);
            page = 1;
            String token = GetTokenUtils.getToken(mActivity, ApiStores.API_SERVER_ORDER_SEARCH);
            addSubscription(apiStores().orderSearch(token, mSearchKey, mType), getApiCallback());
        }
    }

    @NotNull
    private ApiCallback<OrderManagementModel> getApiCallback() {
        showProgressDialog();
        return new ApiCallback<OrderManagementModel>() {
            @Override
            public void onSuccess(OrderManagementModel model) {
                if (model.getCode() == 1) {
                    if (page == 1) {
                        mList.clear();
                        mAdapter.notifyDataSetChanged();
                    }
                    page++;
                    mList.addAll(model.getData());
                    mSmartRefreshLayout.finishLoadMore(1, true, model.getData().size() < size);
                    mAdapter.notifyDataSetChanged();
                    if (mList.size() > 0) {
                        showContentLayout();
                    } else {
                        showEmptyLayout();
                    }
                } else {
                    ((TextView) mLayoutError.findViewById(R.id.error_text)).setText(model.getMsg());
                    showErrorLayout();
                    mSmartRefreshLayout.finishLoadMore(1, true, false);
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
            }
        };
    }

    private void initRecyclerView() {
        mRecyclerView.setNestedScrollingEnabled(false);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mActivity));
        mAdapter = new OrderManagementAdapter(mActivity, mList, mType);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new OrderManagementAdapter.OnItemActionListener() {
            @Override
            public void onApplyRefund(View view, int parentPos, int childPos) {
                // 云仓库、订单：申请退款
                ApplyRefundActivity.start(mActivity, mList.get(parentPos).getDetail().get(childPos));
            }

            @Override
            public void onImmediateDelivery(View view, int parentPos, int childPos) {
                // 云仓库：立即提货
                immediateDelivery(mList.get(parentPos).getId(), mList.get(parentPos).getDetail().get(childPos));
            }

            @Override
            public void onApplyAfterSale(View view, int parentPos, int childPos) {
                // 订单：申请售后
                ApplyAfterSaleActivity.start(mActivity, mList.get(parentPos).getDetail().get(childPos));
            }

            @Override
            public void onCancelAnOrder(View view, int parentPos, int childPos) {
                // 订单：取消订单
                orderCancel(mList.get(parentPos));
            }

            @Override
            public void onContactService(View view, int parentPos, int childPos) {
                // 订单：联系客服
                huanXinKeFu();
            }

            @Override
            public void onImmediatePayment(View view, int parentPos, int childPos) {
                // 订单：立即支付
                if (mType == 4 || mType == 5) {
                    GujujinBecomeAgentPaymentActivity.startActivityForResult(mActivity, 0, mList.get(parentPos).getId(), 0);
                } else {
                    PaymentOrdersActivity.startActivityForResult(mActivity, mType, String.valueOf(mList.get(parentPos).getId()));
                }
            }

            @Override
            public void onConfirmReceipt(View view, int parentPos, int childPos) {
                // 订单：确认收货
                indexOrderMakesure(mList.get(parentPos));
            }

            @Override
            public void onViewLogistics(View view, int parentPos, int childPos) {
                // 订单：查看物流
            }

            @Override
            public void onImmediateEvaluation(View view, int parentPos, int childPos) {
                // 订单：立即评价
                PublishCommentsActivity.start(mActivity, mList.get(parentPos).getDetail().get(childPos));
            }

            @Override
            public void onSeeDetails(View view, int parentPos, int childPos) {
                // 退款/售后：查看详情
                // status 1 待审核 2 已审核 3 退款中 4 换货中 5 已完成 6 申请驳回
                OrderManagementModel.DataBean dataBean = mList.get(parentPos);
                if (dataBean.getStatus() == 6) { // 详情：售后申请审核未通过
                    ApplyAfterFailedDetailsActivity.start(mActivity, String.valueOf(dataBean.getId()));
                } else if (dataBean.getType() == 1) { // 详情：申请退款
                    ApplyRefundDetailsActivity.start(mActivity, String.valueOf(dataBean.getId()));
                } else if (dataBean.getType() == 2) { // 详情：退货退款
                    ApplyAfterRefundDetailsActivity.start(mActivity, String.valueOf(dataBean.getId()));
                } else if (dataBean.getType() == 3) { // 详情：换货<暂时不做>
                    ApplyAfterExchangeDetailsActivity.start(mActivity, String.valueOf(dataBean.getId()));
                }
            }

            @Override
            public void onFillInLogistics(View view, int parentPos, int childPos) {
                // 退款/售后：填写物流
                fillInLogistics(mList.get(parentPos));
            }

            @Override
            public void onDeliverGoods(View view, int parentPos, int childPos) {
                // 谷聚金：发货
                deliverGoods(mList.get(parentPos));
            }

            @Override
            public void onUpload(View view, int parentPos, int childPos) {
                // 谷聚金：上传凭证
                GujujinUploadCredentialsActivity.start(mActivity, String.valueOf(mList.get(parentPos).getId()));
            }

            @Override
            public void onItemClick(View view, int position) {
                OrderDetailsActivity.start(mActivity, String.valueOf(mList.get(position).getId()));
            }
        });
    }

    /**
     * 谷聚金：发货
     *
     * @param data 订单
     */
    private void deliverGoods(OrderManagementModel.DataBean data) {
        showProgressDialog();
        OrderManagementModel.DataBean.DetailBean detailBean = data.getDetail().get(0);
        String token = GetTokenUtils.getToken(mActivity, ApiStores.API_SERVER_ORDER_SENDPRODUCT);
        addSubscription(apiStores().indexOrderSendProduct(token, detailBean.getOr_id(), detailBean.getPd_id(), detailBean.getOr_pd_num()),
                new ApiCallback<String>() {
                    @Override
                    public void onSuccess(String model) {
                        try {
                            JSONObject result = new JSONObject(model);
                            if (result.optInt("code", 0) == 1) {
                                mList.remove(data);
                                mAdapter.notifyDataSetChanged();
                                toastShow(result.optString("data", ""));
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
     * 取消订单
     *
     * @param data 订单
     */
    private void orderCancel(OrderManagementModel.DataBean data) {
        showProgressDialog();
        String token = GetTokenUtils.getToken(mActivity, ApiStores.API_SERVER_ORDER_CANCEL);
        addSubscription(apiStores().orderCancel(token, data.getId()), new ApiCallback<String>() {
            @Override
            public void onSuccess(String model) {
                try {
                    JSONObject result = new JSONObject(model);
                    if (result.getInt("code") == 1) {
                        toastShow(result.getString("msg"));
                        mList.remove(data);
                        mAdapter.notifyDataSetChanged();
                        if (mList.size() > 0) {
                            showContentLayout();
                        } else {
                            showEmptyLayout();
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
     * 确认收货
     *
     * @param data 订单
     */
    private void indexOrderMakesure(OrderManagementModel.DataBean data) {
        showProgressDialog();
        String token = GetTokenUtils.getToken(mActivity, ApiStores.API_SERVER_ORDER_MAKESURE);
        addSubscription(apiStores().indexOrderMakesure(token, data.getId()), new ApiCallback<String>() {
            @Override
            public void onSuccess(String model) {
                try {
                    JSONObject result = new JSONObject(model);
                    if (result.getInt("code") == 1) {
                        toastShow(result.getString("msg"));
                        mList.remove(data);
                        mAdapter.notifyDataSetChanged();
                        if (mList.size() > 0) {
                            showContentLayout();
                        } else {
                            showEmptyLayout();
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
     * 填写物流
     */
    private void fillInLogistics(OrderManagementModel.DataBean data) {
        FillInLogisticsDialog dialog = FillInLogisticsDialog.newInstance();
        dialog.showDialog(getFragmentManager());
        dialog.setOnDialogListener((company, number) -> {
            showProgressDialog();
            String token = GetTokenUtils.getToken(mActivity, ApiStores.API_SERVER_ORDER_BACKEXPRESS);
            addSubscription(apiStores().indexOrderBackexpress(token, data.getId(), company, number), new ApiCallback<String>() {
                @Override
                public void onSuccess(String model) {
                    // {"code":1,"msg":"成功","time":1568603365,"data":"填写成功"}
                    try {
                        JSONObject result = new JSONObject(model);
                        if (result.getInt("code") == 1) {
                            data.setExpress_name(company);
                            data.setExpress_num(number);
                            mAdapter.notifyDataSetChanged();
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
        });
    }

    /**
     * 云仓库：立即提货
     */
    private void immediateDelivery(int cId, OrderManagementModel.DataBean.DetailBean data) {
        CloudWarehouseDeliveryDialog dialog = new CloudWarehouseDeliveryDialog(mActivity);
        dialog.setCancelable(false);
        dialog.show();
        dialog.setOnActionClickListener(new CloudWarehouseDeliveryDialog.OnActionClickListener() {
            @Override
            public void onCancel() {
            }

            @Override
            public void onSure(int num) {
                Logger.i("TAG_NUM", "num = " + num);
                data.setOr_pd_num(num);
                PaymentOrdersCloudActivity.start(mActivity, String.valueOf(cId), data);
            }
        });
        dialog.setMaxNum(data.getOr_pd_num());
    }
}
