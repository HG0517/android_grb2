package com.jgkj.grb.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.jgkj.grb.R;
import com.jgkj.grb.base.BaseActivity;
import com.jgkj.grb.retrofit.ApiCallback;
import com.jgkj.grb.retrofit.ApiStores;
import com.jgkj.grb.ui.adapter.PersonalEvaluationAdapter;
import com.jgkj.grb.ui.mvp.personal.PersonalEvaluationModel;
import com.jgkj.utils.token.GetTokenUtils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 我的评价
 */
public class PersonalEvaluationActivity extends BaseActivity {

    public static void start(Context context) {
        Intent intent = new Intent(context, PersonalEvaluationActivity.class);
        context.startActivity(intent);
    }

    @BindView(R.id.smart_refresh_layout)
    SmartRefreshLayout mSmartRefreshLayout;
    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;
    PersonalEvaluationAdapter mAdapter;
    List<PersonalEvaluationModel.DataBean> mList = new ArrayList<>();

    int page = 1;
    int size = 10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_evaluation);

        Toolbar toolbar = initToolBar(getString(R.string.activity_title_personal_evaluation));

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
        mAdapter = new PersonalEvaluationAdapter(mActivity, mList);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemActionListener(new PersonalEvaluationAdapter.OnItemActionListener() {
            @Override
            public void onItemEvaluationClick(View v, int position) {
                ProductEvaluateDetailsActivity.start(mActivity, mList.get(position));
            }

            @Override
            public void onItemDeleteClick(View v, int position) {
                userDelComment(mList.get(position));
            }

            @Override
            public void onItemShopClick(View v, int position) {
                ProductDetailsActivity.start(mActivity, String.valueOf(mList.get(position).getP_id()));
            }

            @Override
            public void onItemClick(View view, int position) {
            }
        });
    }

    private void userDelComment(PersonalEvaluationModel.DataBean data) {
        showProgressDialog();
        String token = GetTokenUtils.getToken(this, ApiStores.API_SERVER_USER_DELCOMMENT);
        addSubscription(apiStores().userDelComment(token, data.getId()), new ApiCallback<String>() {
            @Override
            public void onSuccess(String model) {
                // {"code":1,"msg":"成功","time":1568872607,"data":"删除成功"}
                try {
                    JSONObject result = new JSONObject(model);
                    if (result.optInt("code", 0) == 1) {
                        mList.remove(data);
                        mAdapter.notifyDataSetChanged();
                        toastShow(result.optString("msg", ""));
                    } else {
                        toastShow(result.optString("msg", ""));
                    }
                } catch (JSONException e) {
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

    private void onLazyLoad() {
        showProgressDialog();
        String token = GetTokenUtils.getToken(this, ApiStores.API_SERVER_USER_MYCOMMENT);
        addSubscription(apiStores().userMyComment(token, page, size), new ApiCallback<PersonalEvaluationModel>() {
            @Override
            public void onSuccess(PersonalEvaluationModel model) {
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
