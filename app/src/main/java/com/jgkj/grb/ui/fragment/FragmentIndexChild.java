package com.jgkj.grb.ui.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.jgkj.grb.R;
import com.jgkj.grb.base.BaseFragment;
import com.jgkj.grb.glide.GlideApp;
import com.jgkj.grb.glide.RoundTransformation;
import com.jgkj.grb.itemdecoration.GridDividerItemDecoration;
import com.jgkj.grb.retrofit.ApiCallback;
import com.jgkj.grb.retrofit.ApiStores;
import com.jgkj.grb.ui.activity.ProductDetailsActivity;
import com.jgkj.grb.ui.adapter.IndexChildContentAdapter;
import com.jgkj.grb.ui.adapter.IndexChildTopAdapter;
import com.jgkj.grb.ui.mvp.BannerData;
import com.jgkj.grb.ui.mvp.index.IndexCatesBean;
import com.jgkj.grb.ui.mvp.index.IndexCatesChildListModel;
import com.jgkj.grb.ui.mvp.index.IndexCatesChildPageModel;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.stx.xhb.xbanner.XBanner;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 首页：pager
 * Created by brightpoplar@163.com on 2019/7/27.
 */
public class FragmentIndexChild extends BaseFragment {

    public static FragmentIndexChild newInstance(IndexCatesBean index) {
        FragmentIndexChild fragmentIndexChild = new FragmentIndexChild();
        Bundle bundle = new Bundle();
        bundle.putSerializable("index", index);
        fragmentIndexChild.setArguments(bundle);
        return fragmentIndexChild;
    }

    @BindView(R.id.smart_refresh_layout)
    SmartRefreshLayout mSmartRefreshLayout;

    @BindView(R.id.mXBanner)
    XBanner mXBanner;
    @BindView(R.id.recycler_view_top)
    RecyclerView mRecyclerViewTop;
    @BindView(R.id.recycler_view_content)
    RecyclerView mRecyclerViewContent;

    List<BannerData> mXBannerDatas = new ArrayList<>();

    IndexChildTopAdapter mTopAdapter;

    IndexChildContentAdapter mContentAdapter;
    int page = 1;
    int size = 10;
    IndexCatesBean mIndex;
    IndexCatesChildListModel mIndexModel;
    int selectChild = -1;

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_index_child;
    }

    @Override
    public void init(Bundle savedInstanceState) {
        Bundle arguments = getArguments();
        if (null == arguments)
            return;
        mIndex = (IndexCatesBean) arguments.getSerializable("index");

        initSmartRefreshLayout();

        mRecyclerViewContent.setNestedScrollingEnabled(false);
        mRecyclerViewContent.setLayoutManager(new GridLayoutManager(mActivity, 2, GridLayoutManager.VERTICAL, false));
        mRecyclerViewContent.addItemDecoration(new GridDividerItemDecoration(dp2px(7), Color.TRANSPARENT));
    }

    private void initSmartRefreshLayout() {
        mSmartRefreshLayout.setEnableRefresh(true);
        mSmartRefreshLayout.setEnableLoadMore(false);
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

    private void initRecyclerViewContent(IndexCatesChildPageModel model) {
        if (null == model.getData() || model.getData().size() <= 0) {
            mRecyclerViewContent.setVisibility(View.GONE);
        } else {
            mRecyclerViewContent.setVisibility(View.VISIBLE);
            mContentAdapter = new IndexChildContentAdapter(mActivity, model.getData());
            mRecyclerViewContent.setAdapter(mContentAdapter);
            mContentAdapter.setOnItemClickListener((view, position) -> {
                ProductDetailsActivity.start(mActivity, String.valueOf(model.getData().get(position).getId()));
            });
        }
    }

    private void initRecyclerViewTop() {
        mRecyclerViewTop.setNestedScrollingEnabled(false);
        if (null != mIndexModel && null != mIndexModel.getData()
                && null != mIndexModel.getData().getSons()
                && mIndexModel.getData().getSons().size() < 5) {
            int spanCount = mIndexModel.getData().getSons().size() > 0 ? mIndexModel.getData().getSons().size() : 5;
            mRecyclerViewTop.setLayoutManager(new GridLayoutManager(mActivity, spanCount, GridLayoutManager.VERTICAL, false));
        } else {
            mRecyclerViewTop.setLayoutManager(new GridLayoutManager(mActivity, 5, GridLayoutManager.VERTICAL, false));
        }
        mTopAdapter = new IndexChildTopAdapter(mActivity, mIndexModel.getData().getSons());
        mRecyclerViewTop.setAdapter(mTopAdapter);
        mTopAdapter.setOnItemClickListener((view, position) -> {
            for (IndexCatesBean item : mIndexModel.getData().getSons()) {
                item.setSelect(false);
            }
            mIndexModel.getData().getSons().get(position).setSelect(true);
            mTopAdapter.notifyDataSetChanged();

            if (selectChild != position) {
                selectChild = position;
                onLazyLoadChild(mIndexModel.getData().getSons().get(position).getId());
            }
        });
        if (null != mIndexModel.getData().getSons() && mIndexModel.getData().getSons().size() > 0) {
            selectChild = 0;
            mIndexModel.getData().getSons().get(0).setSelect(true);
            mTopAdapter.notifyDataSetChanged();
            onLazyLoadChild(mIndexModel.getData().getSons().get(0).getId());
        } else {
            mRecyclerViewContent.setVisibility(View.GONE);
        }
    }

    private void initXBanner() {
        if (null == mIndex || TextUtils.isEmpty(mIndex.getTurn_pic())) {
            mXBanner.setVisibility(View.GONE);
        } else {
            mXBanner.setVisibility(View.VISIBLE);
            mXBanner.setOnItemClickListener((banner, model, view, position) -> {
                if (model instanceof BannerData) {
                    //ProductDetailsActivity.start(mActivity);
                }
            });
            mXBanner.loadImage((banner, model, view, position) -> {
                if (model instanceof BannerData) {
                    BannerData data = (BannerData) model;
                    ImageView img = (ImageView) view;
                    GlideApp.with(this)
                            .load(data.getBannerUrl().replaceAll("\\\\", "/"))
                            .transform(new CenterCrop(), new RoundTransformation(mActivity, 10))
                            .into(img);
                }
            });
            mXBannerDatas.clear();
            mXBannerDatas.add(new BannerData(
                    mIndex.getTurn_pic().startsWith("http:") || mIndex.getTurn_pic().startsWith("https:")
                            ? mIndex.getTurn_pic() : ApiStores.API_SERVER_URL + mIndex.getTurn_pic(), ""));
            mXBanner.setBannerData(mXBannerDatas);
        }
    }

    @Override
    protected void onLazyLoad() {
        showProgressDialog();
        addSubscription(apiStores().indexIndex(mIndex.getId()), new ApiCallback<IndexCatesChildListModel>() {
            @Override
            public void onSuccess(IndexCatesChildListModel model) {
                mIndexModel = model;
                if (mIndexModel.getCode() == 1) {
                    initXBanner();
                    initRecyclerViewTop();
                } else {
                    toastShow(mIndexModel.getMsg());
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
                mSmartRefreshLayout.finishLoadMore(1, true, true);
            }
        });
    }

    protected void onLazyLoadChild(Object caid) {
        showProgressDialog();
        addSubscription(apiStores().indexCateGoods(caid), new ApiCallback<IndexCatesChildPageModel>() {
            @Override
            public void onSuccess(IndexCatesChildPageModel model) {
                if (model.getCode() == 1) {
                    initRecyclerViewContent(model);
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

    @Override
    public void onResume() {
        super.onResume();
        mXBanner.startAutoPlay();
    }

    @Override
    public void onStop() {
        super.onStop();
        mXBanner.stopAutoPlay();
    }
}
