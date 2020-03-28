package com.jgkj.grb.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;

import com.jgkj.grb.R;
import com.jgkj.grb.base.BaseActivity;
import com.jgkj.grb.onclick.RxView;
import com.jgkj.grb.retrofit.ApiStores;
import com.jgkj.grb.ui.adapter.PersonalCollectAdapter;
import com.jgkj.utils.token.GetTokenUtils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 搜索：收藏
 */
public class SearchCollectResultActivity extends BaseActivity {

    public static void start(Context context, String searchKey) {
        Intent intent = new Intent(context, SearchCollectResultActivity.class);
        intent.putExtra("searchKey", searchKey);
        context.startActivity(intent);
    }

    @BindView(R.id.home)
    CardView mBackHome;
    @BindView(R.id.home_icon)
    ImageView mBackIcon;
    @BindView(R.id.search_input_et)
    EditText mSearchInputEt;

    @BindView(R.id.smart_refresh_layout)
    SmartRefreshLayout mSmartRefreshLayout;
    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;
    PersonalCollectAdapter mAdapter;
    List<Object> mList = new ArrayList<>();
    int page = 1;
    int size = 10;

    String mSearchKey = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_collect_result);

        RxView.setOnClickListeners(this, mBackHome);
        initSearchInput();
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
            mList.clear();
            gotoSearch(mSearchKey);
        });
        mSmartRefreshLayout.setOnLoadMoreListener(refreshLayout -> {
            // 加载数据
            gotoSearch(mSearchKey);
        });
    }

    private void initRecyclerView() {
        mRecyclerView.setNestedScrollingEnabled(false);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mActivity));
        mAdapter = new PersonalCollectAdapter(mActivity, null);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemActionListener(new PersonalCollectAdapter.OnItemActionListener() {
            @Override
            public void onActionClick(View v, int position) {
                mList.remove(position);
                mAdapter.notifyDataSetChanged();
            }

            @Override
            public void onItemClick(View view, int position) {
                ProductDetailsActivity.start(mActivity);
            }
        });
    }

    private void onLazyLoad(String searchKey) {
        showProgressDialog();
        String token = GetTokenUtils.getToken(this, ApiStores.API_SERVER_ORDER_SEARCH);
    }

    private void initSearchInput() {
        Intent intent = getIntent();
        if (intent.hasExtra("searchKey")
                && !TextUtils.isEmpty(intent.getStringExtra("searchKey"))) {
            mSearchKey = intent.getStringExtra("searchKey");
            mSearchInputEt.setText(mSearchKey);
            gotoSearch(mSearchInputEt.getText().toString());
        }
        mSearchInputEt.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_SEND
                    || actionId == EditorInfo.IME_ACTION_DONE
                    || (event != null && event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) {
                // do something
                if (event != null && event.getAction() == KeyEvent.ACTION_UP) {
                    gotoSearch(mSearchInputEt.getText().toString());
                }
                return true;
            }
            return false;
        });
    }

    private void gotoSearch(String searchKey) {
        if (TextUtils.isEmpty(searchKey)) {
            toastShow(R.string.search_input_empty);
            return;
        }
        hideInputSoft();
        // 接口
        onLazyLoad(mSearchKey);
    }

    @Override
    public void onClick(Object o) {
        View v = (View) o;
        switch (v.getId()) {
            case R.id.home:
                onBackPressed();
                break;
            default:
                break;
        }
    }

}
