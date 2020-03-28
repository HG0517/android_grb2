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
import com.jgkj.grb.ui.adapter.SearchResultAdapter;
import com.jgkj.grb.ui.mvp.IndexPlaceGoodsModel;
import com.jgkj.grb.ui.mvp.index.GoodBeanModel;
import com.jgkj.utils.token.GetTokenUtils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 公让宝预售
 */
public class AdvanceSaleActivity extends BaseActivity {

    public static void start(Context context) {
        Intent intent = new Intent(context, AdvanceSaleActivity.class);
        context.startActivity(intent);
    }

    @BindView(R.id.smart_refresh_layout)
    SmartRefreshLayout mSmartRefreshLayout;
    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;
    SearchResultAdapter mAdapter;
    List<GoodBeanModel> mList = new ArrayList<>();

    int page = 1;
    int size = 10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_advance_sale);

        Toolbar toolbar = initToolBar(getString(R.string.activity_title_advance_sale));

        initSmartRefreshLayout();
        initRecyclerView();
        indexWillgoods();
    }

    private void initSmartRefreshLayout() {
        mSmartRefreshLayout.setEnableRefresh(true);
        mSmartRefreshLayout.setEnableLoadMore(true);
        mSmartRefreshLayout.setEnableAutoLoadMore(false);
        mSmartRefreshLayout.setOnRefreshListener(refreshLayout -> {
            // 刷新数据
            refreshLayout.setEnableLoadMore(true);
            page = 1;
            indexWillgoods();
        });
        mSmartRefreshLayout.setOnLoadMoreListener(refreshLayout -> {
            // 加载数据
            indexWillgoods();
        });
    }

    private void indexWillgoods() {
        showProgressDialog();
        String token = GetTokenUtils.getToken(this, ApiStores.API_SERVER_INDEX_WILLGOODS);
        addSubscription(apiStores().indexWillgoods(token, page, size), new ApiCallback<IndexPlaceGoodsModel>() {
            @Override
            public void onSuccess(IndexPlaceGoodsModel model) {
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
            }

            @Override
            public void onFinish() {
                dismissProgressDialog();
                mSmartRefreshLayout.finishRefresh(1, true);
            }
        });
    }

    private void initRecyclerView() {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new SearchResultAdapter(this, mList, 1);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener((view, position) -> {
            ProductDetailsActivity.start(this, String.valueOf(mList.get(position).getId()));
        });
    }
}
