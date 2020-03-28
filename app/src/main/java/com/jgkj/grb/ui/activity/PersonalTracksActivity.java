package com.jgkj.grb.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.jgkj.grb.R;
import com.jgkj.grb.base.BaseActivity;
import com.jgkj.grb.retrofit.ApiCallback;
import com.jgkj.grb.retrofit.ApiStores;
import com.jgkj.grb.ui.adapter.PersonalTracksAdapter;
import com.jgkj.grb.ui.mvp.personal.PersonalTracksModel;
import com.jgkj.utils.token.GetTokenUtils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 我的足迹
 */
public class PersonalTracksActivity extends BaseActivity {

    public static void start(Context context) {
        Intent intent = new Intent(context, PersonalTracksActivity.class);
        context.startActivity(intent);
    }

    @BindView(R.id.smart_refresh_layout)
    SmartRefreshLayout mSmartRefreshLayout;
    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;
    PersonalTracksAdapter mAdapter;
    List<PersonalTracksModel.DataBean> mList = new ArrayList<>();
    int page = 1;
    int size = 10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_tracks);

        Toolbar toolbar = initToolBar(getString(R.string.activity_title_personal_tracks));

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
            mList.clear();
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
        mAdapter = new PersonalTracksAdapter(mActivity, mList);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemActionListener(new PersonalTracksAdapter.OnItemActionListener() {
            @Override
            public void onItemClick(View v, int postion, int posChild) {
                ProductDetailsActivity.start(mActivity, String.valueOf(mList.get(postion).getGoods().get(posChild).getPd_id()));
            }

            @Override
            public void onItemClick(View view, int position) {
            }
        });
    }

    private void onLazyLoad() {
        showProgressDialog();
        String token = GetTokenUtils.getToken(mActivity, ApiStores.API_SERVER_USER_SKIM);
        addSubscription(apiStores().userSkim(token), new ApiCallback<PersonalTracksModel>() {
            @Override
            public void onSuccess(PersonalTracksModel model) {
                if (model.getCode() == 1) {
                    if (model.getData().size() > 0) {
                        mList.addAll(model.getData());
                        mAdapter.notifyDataSetChanged();
                    }
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

    private void userClean() {
        showProgressDialog();
        String token = GetTokenUtils.getToken(mActivity, ApiStores.API_SERVER_USER_CLEAN);
        addSubscription(apiStores().userClean(token), new ApiCallback<String>() {
            @Override
            public void onSuccess(String model) {
                try {
                    JSONObject result = new JSONObject(model);
                    if (result.optInt("code", 0) == 1) {
                        toastShow(result.optString("msg"));
                        mList.clear();
                        mAdapter.notifyDataSetChanged();
                    } else {
                        toastShow(result.optString("msg"));
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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_clear:
                if (null != mList && !mList.isEmpty())
                    userClean();
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
        getMenuInflater().inflate(R.menu.menu_personal_tracks, menu);
        return true;
    }

    @Deprecated
    protected List<PersonalTracksModel> convert(List<PersonalTracksModel.DataBean> list) {
        List<PersonalTracksModel> result = new ArrayList<>();
        PersonalTracksModel personalTracksModel = null;
        for (int i = 0; i < list.size(); i++) {
            if (i == 0) {
                personalTracksModel = new PersonalTracksModel();
                List<PersonalTracksModel.DataBean> l = new ArrayList<>();
                l.add(list.get(i));
                personalTracksModel.setData(l);
                personalTracksModel.setMsg(list.get(i).getTime());
                result.add(personalTracksModel);
            } else if (TextUtils.equals(list.get(i).getTime(), list.get(i - 1).getTime())) {
                personalTracksModel.getData().add(list.get(i));
            } else {
                personalTracksModel = new PersonalTracksModel();
                List<PersonalTracksModel.DataBean> l = new ArrayList<>();
                l.add(list.get(i));
                personalTracksModel.setData(l);
                personalTracksModel.setMsg(list.get(i).getTime());
                result.add(personalTracksModel);
            }
        }
        return result;
    }

}
