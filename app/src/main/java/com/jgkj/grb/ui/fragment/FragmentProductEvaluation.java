package com.jgkj.grb.ui.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.jgkj.grb.R;
import com.jgkj.grb.base.BaseFragment;
import com.jgkj.grb.retrofit.ApiCallback;
import com.jgkj.grb.retrofit.ApiStores;
import com.jgkj.grb.ui.activity.ProductEvaluateDetailsActivity;
import com.jgkj.grb.ui.activity.ProductEvaluationActivity;
import com.jgkj.grb.ui.adapter.ProductEvaluationAdapter;
import com.jgkj.grb.ui.mvp.personal.PersonalEvaluationModel;
import com.jgkj.grb.ui.mvp.product.ProductEvaluationModel;
import com.jgkj.utils.token.GetTokenUtils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 商品评价
 * Created by brightpoplar@163.com on 2019/8/6.
 */
public class FragmentProductEvaluation extends BaseFragment {

    public static FragmentProductEvaluation newInstance(String pdId, String index) {
        FragmentProductEvaluation fragmentProductEvaluation = new FragmentProductEvaluation();
        Bundle bundle = new Bundle();
        bundle.putString("pdId", pdId);
        bundle.putString("index", index);
        fragmentProductEvaluation.setArguments(bundle);
        return fragmentProductEvaluation;
    }

    @BindView(R.id.smart_refresh_layout)
    SmartRefreshLayout mSmartRefreshLayout;
    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;

    ProductEvaluationAdapter mAdapter;
    List<PersonalEvaluationModel.DataBean> mList = new ArrayList<>();

    String mPdId = "";
    String mIndex = "";
    private int page = 1;
    private int size = 10;

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_product_evaluation;
    }

    @Override
    public void init(Bundle savedInstanceState) {
        Bundle arguments = getArguments();
        if (null == arguments)
            return;
        mPdId = arguments.getString("pdId", "");
        mIndex = arguments.getString("index", "");

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
        mAdapter = new ProductEvaluationAdapter(mActivity, mList);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mActivity));
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener((view, position) -> {
            ProductEvaluateDetailsActivity.start(mActivity, mList.get(position));
        });
    }

    @Override
    protected void onLazyLoad() {
        showProgressDialog();
        String token = GetTokenUtils.getToken(mActivity, ApiStores.API_SERVER_INDEX_GOODSCOMMENT);
        addSubscription(apiStores().indexGoodsComment(token, mPdId, mIndex, page, size), new ApiCallback<ProductEvaluationModel>() {
            @Override
            public void onSuccess(ProductEvaluationModel model) {
                if (model.getCode() == 1) {
                    if (page == 1)
                        mList.clear();
                    page++;
                    mList.addAll(model.getData().getList());
                    mAdapter.notifyDataSetChanged();
                    mSmartRefreshLayout.finishLoadMore(1, true, model.getData().getList().size() < size);

                    if (getActivity() instanceof ProductEvaluationActivity) {
                        ProductEvaluationActivity activity = (ProductEvaluationActivity) getActivity();
                        activity.onRefreshLayout(model.getData().getCount1(), model.getData().getCount2(), model.getData().getCount3());
                    }
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
