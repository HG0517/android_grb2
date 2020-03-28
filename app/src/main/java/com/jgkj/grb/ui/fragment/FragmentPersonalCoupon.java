package com.jgkj.grb.ui.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.jgkj.grb.R;
import com.jgkj.grb.base.BaseFragment;
import com.jgkj.grb.itemdecoration.SpaceItemDecoration;
import com.jgkj.grb.retrofit.ApiCallback;
import com.jgkj.grb.retrofit.ApiStores;
import com.jgkj.grb.ui.activity.ProductDetailsActivity;
import com.jgkj.grb.ui.adapter.PersonalCouponAdapter;
import com.jgkj.grb.ui.mvp.personal.CouponModel;
import com.jgkj.grb.utils.ScreenUtils;
import com.jgkj.utils.token.GetTokenUtils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 个人中心：我的优惠券
 * Created by brightpoplar@163.com on 2019/8/26.
 */
public class FragmentPersonalCoupon extends BaseFragment {

    public static FragmentPersonalCoupon newInstance(int status) {
        FragmentPersonalCoupon fragmentPersonalCoupon = new FragmentPersonalCoupon();
        Bundle bundle = new Bundle();
        bundle.putInt("status", status);
        fragmentPersonalCoupon.setArguments(bundle);
        return fragmentPersonalCoupon;
    }

    @BindView(R.id.smart_refresh_layout)
    SmartRefreshLayout mSmartRefreshLayout;
    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;

    PersonalCouponAdapter mAdapter;
    List<CouponModel.DataBean.ListBean> mList = new ArrayList<>();
    int page = 1;
    int size = 10;
    int status = 0;

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_personal_coupon;
    }

    @Override
    public void init(Bundle savedInstanceState) {
        Bundle arguments = getArguments();
        if (null != arguments) {
            status = arguments.getInt("status", status);
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
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.addItemDecoration(new SpaceItemDecoration(ScreenUtils.dp2px(getContext(), 10)));
        mAdapter = new PersonalCouponAdapter(mActivity, mList, status);
        mRecyclerView.setAdapter(mAdapter);

        mAdapter.setOnItemClickListener((view, position) -> {
            ProductDetailsActivity.start(mActivity, String.valueOf(mList.get(position).getP_id()));
        });
    }

    @Override
    protected void onLazyLoad() {
        showProgressDialog();
        String token = GetTokenUtils.getToken(mActivity, ApiStores.API_SERVER_USER_MYCOUPON);
        addSubscription(apiStores().userMyCoupon(token, status, page, size), new ApiCallback<CouponModel>() {
            @Override
            public void onSuccess(CouponModel model) {
                if (model.getCode() == 1) {
                    if (page == 1)
                        mList.clear();
                    page++;

                    mList.addAll(model.getData().getList());
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
                mSmartRefreshLayout.finishRefresh(1, true);
                mSmartRefreshLayout.finishLoadMore(1, true, true/*model.getData().size() < size*/);
            }
        });
    }
}
