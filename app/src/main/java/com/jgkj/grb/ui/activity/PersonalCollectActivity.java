package com.jgkj.grb.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.jgkj.grb.R;
import com.jgkj.grb.base.BaseActivity;
import com.jgkj.grb.retrofit.ApiCallback;
import com.jgkj.grb.retrofit.ApiStores;
import com.jgkj.grb.ui.adapter.PersonalCollectAdapter;
import com.jgkj.grb.ui.mvp.CollectModel;
import com.jgkj.utils.token.GetTokenUtils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 个人中心：我的收藏
 */
public class PersonalCollectActivity extends BaseActivity {

    public static void start(Context context) {
        Intent intent = new Intent(context, PersonalCollectActivity.class);
        context.startActivity(intent);
    }

    @BindView(R.id.smart_refresh_layout)
    SmartRefreshLayout mSmartRefreshLayout;
    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;
    PersonalCollectAdapter mAdapter;
    List<CollectModel.DataBean> mList = new ArrayList<>();
    int page = 1;
    int size = 10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_collect);

        Toolbar toolbar = initToolBar(getString(R.string.activity_title_personal_collect));

        initSmartRefreshLayout();
        initRecyclerView();

        onLazyLoad();
    }

    private void initSmartRefreshLayout() {
        mSmartRefreshLayout.setEnableRefresh(false);
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

    private void initRecyclerView() {
        mRecyclerView.setNestedScrollingEnabled(false);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mActivity));
        mAdapter = new PersonalCollectAdapter(mActivity, mList);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemActionListener(new PersonalCollectAdapter.OnItemActionListener() {
            @Override
            public void onActionClick(View v, int position) {
                userDelcoll(mList.get(position));
            }

            @Override
            public void onItemClick(View view, int position) {
                ProductDetailsActivity.start(mActivity, String.valueOf(mList.get(position).getPd_id()));
            }
        });
    }

    private void userDelcoll(CollectModel.DataBean dataBean) {
        showProgressDialog();
        String token = GetTokenUtils.getToken(mActivity, ApiStores.API_SERVER_USER_DELCOLL);
        addSubscription(apiStores().userDelcoll(token, dataBean.getId()), new ApiCallback<String>() {
            @Override
            public void onSuccess(String model) {
                try {
                    JSONObject result = new JSONObject(model);
                    if (result.optInt("code", 0) == 1) {
                        toastShow(result.optString("msg", ""));

                        mList.remove(dataBean);
                        mAdapter.notifyDataSetChanged();
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
        String token = GetTokenUtils.getToken(mActivity, ApiStores.API_SERVER_USER_COLLECTION);
        addSubscription(apiStores().userCollection(token), new ApiCallback<CollectModel>() {
            @Override
            public void onSuccess(CollectModel model) {
                if (model.getCode() == 1) {
                    if (page == 1)
                        mList.clear();
                    mList.addAll(model.getData());
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
                mSmartRefreshLayout.finishRefresh(true);
                mSmartRefreshLayout.finishLoadMore(true);
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_search:
                SearchActivity.start(mActivity, "collect", 0);
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //getMenuInflater().inflate(R.menu.menu_order_management, menu);
        return true;
    }

}
