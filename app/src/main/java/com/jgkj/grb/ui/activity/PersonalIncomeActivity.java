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
import com.jgkj.grb.ui.adapter.PersonalIncomeAdapter;
import com.jgkj.grb.ui.mvp.personal.PersonalIncomeModel;
import com.jgkj.utils.token.GetTokenUtils;
import com.jgkj.utils.token.utils.statusbar.StatusBarUtil;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 个人中心：GRB，收益明细：主界面，农场玩家
 */
public class PersonalIncomeActivity extends BaseActivity {

    public static void start(Context context) {
        Intent intent = new Intent(context, PersonalIncomeActivity.class);
        context.startActivity(intent);
    }

    @BindView(R.id.num_0_1)
    TextView mNum0;
    @BindView(R.id.num_1_1)
    TextView mNum1;
    @BindView(R.id.num_2_1)
    TextView mNum2;

    @BindView(R.id.smart_refresh_layout)
    SmartRefreshLayout mSmartRefreshLayout;
    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;

    PersonalIncomeAdapter mAdapter;
    List<PersonalIncomeModel.DataBean> mList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_income);

        StatusBarUtil.setStatusBar(this, Color.TRANSPARENT, 0, false);
        Toolbar toolbar = initToolBar(getString(R.string.activity_title_personal_income));
        toolbar.setNavigationIcon(R.mipmap.back_navi_icon_white);
        setBackgroundColor(toolbar, Color.TRANSPARENT);
        setTitleTextColor(toolbar, Color.WHITE);

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
            onLazyLoad();
        });
        mSmartRefreshLayout.setOnLoadMoreListener(refreshLayout -> {
            // 加载数据
            onLazyLoad();
        });
    }

    private void initRecyclerView() {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new PersonalIncomeAdapter(mActivity, mList);
        mRecyclerView.setAdapter(mAdapter);

        mAdapter.setOnItemClickListener((view, position) -> {
            PersonalIncomeRecordActivity.start(mActivity, mList.get(position));
        });
    }

    private void onLazyLoad() {
        showProgressDialog();
        String token = GetTokenUtils.getToken(this, ApiStores.API_SERVER_USER_FARMAGENT);
        addSubscription(apiStores().userFarmAgent(token), new ApiCallback<PersonalIncomeModel>() {
            @Override
            public void onSuccess(PersonalIncomeModel model) {
                if (model.getCode() == 1) {
                    mList.clear();
                    mList.addAll(model.getData().getList());
                    mAdapter.notifyDataSetChanged();
                    mNum0.setText(model.getData().getFarm());
                    mNum1.setText(model.getData().getSum());
                    mNum2.setText(model.getData().getProfit());
                } else {
                    mNum0.setText("0");
                    mNum1.setText("0.00");
                    mNum2.setText("0.00");
                    toastShow(model.getMsg());
                }
            }

            @Override
            public void onFailure(String msg) {
                mNum0.setText("0");
                mNum1.setText("0.00");
                mNum2.setText("0.00");
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
