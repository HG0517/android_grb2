package com.jgkj.grb.ui.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.jgkj.grb.R;
import com.jgkj.grb.base.BaseFragment;
import com.jgkj.grb.itemdecoration.SpaceItemDecoration;
import com.jgkj.grb.retrofit.ApiCallback;
import com.jgkj.grb.retrofit.ApiStores;
import com.jgkj.grb.ui.activity.MessageDetailsWebViewActivity;
import com.jgkj.grb.ui.adapter.MessageAdapter;
import com.jgkj.grb.ui.mvp.message.MessageModel;
import com.jgkj.utils.token.GetTokenUtils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * 消息：type 1：系统消息，2：商城活动，3：代理消息
 * Created by brightpoplar@163.com on 2019/8/20.
 */
public class FragmentMessage extends BaseFragment {

    public static FragmentMessage newInstance(int type) {
        FragmentMessage fragmentMessage = new FragmentMessage();
        Bundle bundle = new Bundle();
        bundle.putInt("type", type);
        fragmentMessage.setArguments(bundle);
        return fragmentMessage;
    }

    @BindView(R.id.smart_refresh_layout)
    SmartRefreshLayout mSmartRefreshLayout;

    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;

    private int mType = 1;

    private MessageModel mDatas;
    private MessageAdapter mAdapter;
    private int page = 1;
    private int size = 10;

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_message;
    }

    @Override
    public void init(Bundle savedInstanceState) {
        Bundle arguments = getArguments();
        if (null != arguments)
            mType = arguments.getInt("type", 1);

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
        mDatas = new MessageModel();
        mDatas.setData(new ArrayList<>());
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mActivity));
        mRecyclerView.addItemDecoration(new SpaceItemDecoration(dp2px(1)));
        mAdapter = new MessageAdapter(mActivity, mType, mDatas.getData());
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener((view, position) -> {
            MessageDetailsWebViewActivity.start(mActivity, String.valueOf(mDatas.getData().get(position).getId()));
        });
    }

    @Override
    protected void onLazyLoad() {
        showProgressDialog();
        String token = GetTokenUtils.getToken(mActivity, ApiStores.API_SERVER_INDEX_NEWSLIST);
        addSubscription(apiStores().indexNewslist(token, mType, page, size), new ApiCallback<MessageModel>() {
            @Override
            public void onSuccess(MessageModel model) {
                if (model.getCode() == 1) {
                    if (page == 1) {
                        mDatas.getData().clear();
                        mAdapter.notifyDataSetChanged();
                    }
                    page++;
                    mDatas.getData().addAll(model.getData());
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
                mSmartRefreshLayout.finishRefresh(true);
                mSmartRefreshLayout.finishLoadMore(true);
            }
        });
    }
}
