package com.jgkj.grb.ui.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.jgkj.grb.R;
import com.jgkj.grb.base.BaseFragment;
import com.jgkj.grb.retrofit.ApiCallback;
import com.jgkj.grb.ui.activity.ProductDetailsActivity;
import com.jgkj.grb.ui.adapter.SearchResultAdapter;
import com.jgkj.grb.ui.mvp.IndexPlaceGoodsModel;
import com.jgkj.grb.ui.mvp.IndexPlaceModel;
import com.jgkj.grb.ui.mvp.index.GoodBeanModel;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 公让宝基地
 * Created by brightpoplar@163.com on 2019/9/4.
 */
public class FragmentBaseInterface extends BaseFragment {

    public static FragmentBaseInterface newInstance(IndexPlaceModel.DataBean data) {
        FragmentBaseInterface fragmentBaseInterface = new FragmentBaseInterface();
        Bundle bundle = new Bundle();
        bundle.putSerializable("data", data);
        fragmentBaseInterface.setArguments(bundle);
        return fragmentBaseInterface;
    }

    @BindView(R.id.smart_refresh_layout)
    SmartRefreshLayout mSmartRefreshLayout;
    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;
    SearchResultAdapter mAdapter;
    List<GoodBeanModel> mList = new ArrayList<>();

    int page = 1;
    int size = 10;

    IndexPlaceModel.DataBean mData;

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_base_interface;
    }

    @Override
    public void init(Bundle savedInstanceState) {
        Bundle arguments = getArguments();
        if (null != arguments) {
            mData = (IndexPlaceModel.DataBean) arguments.getSerializable("data");
        }
        if (null == mData) {
            toastShow(R.string.open_activity_error_option);
            return;
        }

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
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mActivity));
        mAdapter = new SearchResultAdapter(mActivity, mList, 0);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener((view, position) -> {
            ProductDetailsActivity.start(mActivity, String.valueOf(mList.get(position).getId()));
        });
    }

    @Override
    protected void onLazyLoad() {
        showProgressDialog();
        addSubscription(apiStores().indexAreaGoods(mData.getArea_id(), page, size), new ApiCallback<IndexPlaceGoodsModel>() {
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
}
