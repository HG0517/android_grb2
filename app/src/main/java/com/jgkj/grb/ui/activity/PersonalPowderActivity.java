package com.jgkj.grb.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.jgkj.grb.R;
import com.jgkj.grb.base.BaseActivity;
import com.jgkj.grb.retrofit.ApiCallback;
import com.jgkj.grb.retrofit.ApiStores;
import com.jgkj.grb.ui.adapter.PersonalPowderAdapter;
import com.jgkj.grb.ui.mvp.personal.PowderModel;
import com.jgkj.utils.token.GetTokenUtils;
import com.jgkj.utils.token.utils.statusbar.StatusBarUtil;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 我的宝粉
 */
public class PersonalPowderActivity extends BaseActivity {

    public static void start(Context context) {
        Intent intent = new Intent(context, PersonalPowderActivity.class);
        context.startActivity(intent);
    }

    @BindView(R.id.personal_balance)
    TextView mPersonalBalance;
    @BindView(R.id.personal_balance_1)
    TextView mPersonalBalance1;
    @BindView(R.id.personal_balance_2)
    TextView mPersonalBalance2;
    @BindView(R.id.smart_refresh_layout)
    SmartRefreshLayout mSmartRefreshLayout;
    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;
    PersonalPowderAdapter mAdapter;
    List<PowderModel.DataBean> mList = new ArrayList<>();

    int page = 1;
    int size = 10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_powder);

        //StatusBarUtil.setStatusBarDarkIconMode(this, false);
        StatusBarUtil.setStatusBar(this, Color.TRANSPARENT, 0, false);
        Toolbar toolbar = initToolBar(getString(R.string.activity_title_personal_powder));
        toolbar.setNavigationIcon(R.mipmap.back_navi_icon_white);
        setBackgroundColor(toolbar, Color.TRANSPARENT);
        setTitleTextColor(toolbar, Color.WHITE);

        initSmartRefreshLayout();
        initRecyclerView();

        onLazyLoad();
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
        mAdapter = new PersonalPowderAdapter(mActivity, mList);
        mRecyclerView.setAdapter(mAdapter);
    }

    private void onLazyLoad() {
        showProgressDialog();
        String token = GetTokenUtils.getToken(this, ApiStores.API_SERVER_USER_MYTEAM);
        addSubscription(apiStores().userMyTeam(token, page, size), new ApiCallback<PowderModel>() {
            @Override
            public void onSuccess(PowderModel model) {
                if (model.getCode() == 1) {
                    if (page == 1)
                        mList.clear();
                    page++;

                    mPersonalBalance.setText(String.format(getString(R.string.total_top_text), model.getData().getMoney_sum())); // 我的消费金额
                    mPersonalBalance1.setText(String.format(getString(R.string.total_top_text), model.getData().getZt_total())); // 宝粉业绩
                    mPersonalBalance2.setText(String.format("%s人", model.getData().getCount())); // 宝粉总数
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
