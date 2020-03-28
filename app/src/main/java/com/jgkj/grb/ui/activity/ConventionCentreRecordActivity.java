package com.jgkj.grb.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.jgkj.grb.R;
import com.jgkj.grb.base.BaseActivity;
import com.jgkj.grb.eventbus.RefreshUserInfo;
import com.jgkj.grb.itemdecoration.SpaceItemDecoration;
import com.jgkj.grb.retrofit.ApiCallback;
import com.jgkj.grb.retrofit.ApiStores;
import com.jgkj.grb.ui.adapter.ConventionCentreRecordAdapter;
import com.jgkj.grb.ui.bean.CentreRecordModel;
import com.jgkj.grb.ui.dialog.MeetingPaymentDialog;
import com.jgkj.grb.ui.dialog.PaymentNowDialog;
import com.jgkj.utils.token.GetTokenUtils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.xgr.easypay.callback.IPayCallback;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 会议中心：报名记录
 */
public class ConventionCentreRecordActivity extends BaseActivity {

    public static void start(Context context) {
        Intent intent = new Intent(context, ConventionCentreRecordActivity.class);
        context.startActivity(intent);
    }

    @BindView(R.id.smart_refresh_layout)
    SmartRefreshLayout mSmartRefreshLayout;
    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;

    ConventionCentreRecordAdapter mAdapter;
    List<CentreRecordModel.DataBean> mList = new ArrayList<>();
    int page = 1;
    int size = 10;

    int mPaymentIndex = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_convention_centre_record);

        Toolbar toolbar = initToolBar(getString(R.string.activity_title_convention_centre_record));

        initSmartRefreshLayout();
        initRecyclerView();

        onLazyLoad();
    }

    private void initSmartRefreshLayout() {
        mSmartRefreshLayout.setEnableRefresh(true);
        mSmartRefreshLayout.setEnableLoadMore(false);
        mSmartRefreshLayout.setEnableAutoLoadMore(false);
        mSmartRefreshLayout.setOnRefreshListener(refreshLayout -> {
            // 刷新数据
            refreshLayout.setEnableLoadMore(false);
            mList.clear();
            page = 1;
            onLazyLoad();
        });
        mSmartRefreshLayout.setOnLoadMoreListener(refreshLayout -> {
            // 加载数据
            onLazyLoad();
        });
    }

    private void initRecyclerView() {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mActivity));
        mRecyclerView.addItemDecoration(new SpaceItemDecoration(dp2px(10)));
        mAdapter = new ConventionCentreRecordAdapter(mActivity, mList);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener((view, position) -> {
            mPaymentIndex = position;
            showMeetingPaymentDialog(mList.get(position));
        });
    }

    /**
     * 报名支付：支付方式
     */
    private void showMeetingPaymentDialog(CentreRecordModel.DataBean dataBean) {
        MeetingPaymentDialog dialog = new MeetingPaymentDialog(this);
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
        dialog.setPaymentBalanceEnabled(dataBean.getCash() == 1);
        dialog.setPaymentGRBEnabled(dataBean.getGrb() == 1);
        dialog.setDialogTitle1(String.format(getString(R.string.total_top_text), dataBean.getFee()));
        dialog.setOnActionClickListener(new MeetingPaymentDialog.OnActionClickListener() {
            @Override
            public void onCancel() {
            }

            @Override
            public void onSure(ArrayList<Object> paymentType) {
                showPaymentNowDialog(dataBean.getId(), paymentType);
            }
        });
    }

    /**
     * 报名支付：支付密码
     */
    private void showPaymentNowDialog(String id, ArrayList<Object> paymentType) {
        PaymentNowDialog dialog = new PaymentNowDialog(mActivity);
        dialog.setCancelable(false);
        dialog.show();
        dialog.setOnFinishListener(new PaymentNowDialog.OnActionClickListener() {
            @Override
            public void onClose() {
            }

            @Override
            public void onInputFinish(String password) {
                indexMeetingPayFee(id, paymentType, password);
            }
        });
    }

    private void indexMeetingPayFee(String id, ArrayList<Object> paymentType, String password) {
        showProgressDialog();
        String token = GetTokenUtils.getToken(mActivity, ApiStores.API_SERVER_MEETING_PAYFEE);
        addSubscription(apiStores().indexMeetingPayFee(token, id, paymentType, password), new ApiCallback<String>() {
            @Override
            public void onSuccess(String model) {
                try {
                    JSONObject result = new JSONObject(model);
                    if (result.optInt("code", 0) == 1) {
                        JSONObject data = result.optJSONObject("data");
                        int paytype = data.optInt("paytype", 0);
                        if (paytype == 0) { // 支付完成
                            toastShow(result.optString("msg", ""));
                            mList.get(mPaymentIndex).setStatus(1);
                            mAdapter.notifyDataSetChanged();
                            EventBus.getDefault().post(new RefreshUserInfo(true));
                        } else if (paytype == 4) { // 微信支付
                            JSONObject wechat = data.optJSONObject("wechat");
                            wxpay(wechat, payCallback);
                        } else if (paytype == 3) { // 支付宝支付
                            alipay(data.optString("alipay", ""), payCallback);
                        } else if (paytype == 5) { // 云闪付支付
                            JSONObject flash = data.optJSONObject("flash");
                            unionpay(flash, payCallback);
                        }
                    } else if (result.optInt("code", 0) == 3) {
                        showPaymentNowDialog(id, paymentType);
                        toastShow(result.optString("msg", ""));
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
            mList.get(mPaymentIndex).setStatus(1);
            mAdapter.notifyDataSetChanged();
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
                    }
            );
            builder.create().show();
        });
    }

    private void onLazyLoad() {
        showProgressDialog();
        String token = GetTokenUtils.getToken(this, ApiStores.API_SERVER_MEETING_MYMEETING);
        addSubscription(apiStores().userMeeting(token), new ApiCallback<CentreRecordModel>() {
            @Override
            public void onSuccess(CentreRecordModel model) {
                if (model.getCode() == 1) {
                    if (page == 1)
                        mList.clear();
                    mList.addAll(model.getData());
                    mAdapter.notifyDataSetChanged();
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
                mSmartRefreshLayout.finishRefresh(true);
                mSmartRefreshLayout.finishLoadMore(true);
            }
        });
    }

}
