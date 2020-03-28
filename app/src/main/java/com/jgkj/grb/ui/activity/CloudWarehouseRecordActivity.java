package com.jgkj.grb.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.jgkj.grb.R;
import com.jgkj.grb.base.BaseActivity;
import com.jgkj.grb.retrofit.ApiCallback;
import com.jgkj.grb.retrofit.ApiStores;
import com.jgkj.grb.ui.adapter.CloudWarehouseRecordAdapter;
import com.jgkj.grb.ui.mvp.cloud.CloudWarehouseRecordModel;
import com.jgkj.utils.token.GetTokenUtils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 云仓库：提货记录
 */
public class CloudWarehouseRecordActivity extends BaseActivity {

    public static void start(Context context) {
        Intent intent = new Intent(context, CloudWarehouseRecordActivity.class);
        context.startActivity(intent);
    }

    @BindView(R.id.smart_refresh_layout)
    SmartRefreshLayout mSmartRefreshLayout;

    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;

    private CloudWarehouseRecordAdapter mAdapter;
    List<CloudWarehouseRecordModel.DataBean> mList = new ArrayList<>();
    private int page = 1;
    private int size = 10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cloud_warehouse_record);

        Toolbar toolbar = initToolBar(getString(R.string.activity_title_cloud_warehouse_record));

        initSmartRefreshLayout();
        initRecyclerView();
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

    private void initRecyclerView() {
        mRecyclerView.setNestedScrollingEnabled(false);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mActivity));
        mAdapter = new CloudWarehouseRecordAdapter(mActivity, mList);
        mRecyclerView.setAdapter(mAdapter);

        onLazyLoad();
    }

    private void onLazyLoad() {
        showProgressDialog();
        String token = GetTokenUtils.getToken(this, ApiStores.API_SERVER_ORDER_TAKEGOODS);
        addSubscription(apiStores().orderTakegoods(token, page, size), new ApiCallback<CloudWarehouseRecordModel>() {
            @Override
            public void onSuccess(CloudWarehouseRecordModel model) {
                if (model.getCode() == 1) {
                    if (page == 1)
                        mList.clear();
                    page++;
                    mList.addAll(model.getData());
                    mAdapter.notifyDataSetChanged();
                    mSmartRefreshLayout.finishLoadMore(1, true, model.getData().size() < size);
                } else {
                    toastShow(model.getMsg());
                    mSmartRefreshLayout.finishLoadMore(1, true, false);
                }
            }

            @Override
            public void onFailure(String msg) {
                toastShow(msg);
                mSmartRefreshLayout.finishLoadMore(1, true, false);
            }

            @Override
            public void onFinish() {
                dismissProgressDialog();
                mSmartRefreshLayout.finishRefresh(true);
            }
        });
    }
}
