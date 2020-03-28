package com.jgkj.grb.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.jgkj.grb.R;
import com.jgkj.grb.base.BaseActivity;
import com.jgkj.grb.itemdecoration.SpaceItemDecoration;
import com.jgkj.grb.retrofit.ApiCallback;
import com.jgkj.grb.retrofit.ApiStores;
import com.jgkj.grb.ui.adapter.PersonalIncomeRecordAdapter;
import com.jgkj.grb.ui.mvp.personal.PersonalIncomeModel;
import com.jgkj.grb.ui.mvp.personal.PersonalIncomeRecordModel;
import com.jgkj.utils.token.GetTokenUtils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 个人中心：GRB，收益明细：农场玩家，收益记录
 */
public class PersonalIncomeRecordActivity extends BaseActivity {

    public static void start(Context context, PersonalIncomeModel.DataBean data) {
        Intent intent = new Intent(context, PersonalIncomeRecordActivity.class);
        intent.putExtra("data", data);
        context.startActivity(intent);
    }

    @BindView(R.id.smart_refresh_layout)
    SmartRefreshLayout mSmartRefreshLayout;
    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;

    PersonalIncomeRecordAdapter mAdapter;
    List<PersonalIncomeRecordModel.DataBean> mList = new ArrayList<>();
    PersonalIncomeModel.DataBean mData;
    int page = 1;
    int size = 10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_income_record);

        Toolbar toolbar = initToolBar(getString(R.string.activity_title_personal_income_record));

        Intent intent = getIntent();
        if (intent.hasExtra("data")) {
            mData = (PersonalIncomeModel.DataBean) intent.getSerializableExtra("data");
        }

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
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.addItemDecoration(new SpaceItemDecoration(dp2px(1)));
        mAdapter = new PersonalIncomeRecordAdapter(mActivity, mList, mData.getName());
        mRecyclerView.setAdapter(mAdapter);
    }

    private void onLazyLoad() {
        showProgressDialog();
        String token = GetTokenUtils.getToken(this, ApiStores.API_SERVER_USER_FARMLIST);
        addSubscription(apiStores().userFarmList(token, mData.getType(), page, size), new ApiCallback<PersonalIncomeRecordModel>() {
            @Override
            public void onSuccess(PersonalIncomeRecordModel model) {
                if (model.getCode() == 1) {
                    if (page == 1)
                        mList.clear();
                    page++;
                    mList.addAll(model.getData());
                    mAdapter.notifyDataSetChanged();
                    mSmartRefreshLayout.finishLoadMore(1, true, model.getData().size() < size);
                } else {
                    toastShow(model.getMsg());
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
