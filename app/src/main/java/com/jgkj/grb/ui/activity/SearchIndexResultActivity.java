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
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.jgkj.grb.R;
import com.jgkj.grb.base.BaseActivity;
import com.jgkj.grb.onclick.RxView;
import com.jgkj.grb.retrofit.ApiCallback;
import com.jgkj.grb.retrofit.ApiStores;
import com.jgkj.grb.ui.adapter.SearchResultAdapter;
import com.jgkj.grb.ui.mvp.index.GoodBeanModel;
import com.jgkj.grb.ui.mvp.index.IndexSearchModel;
import com.jgkj.utils.token.GetTokenUtils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 搜索：首页
 */
public class SearchIndexResultActivity extends BaseActivity implements RadioGroup.OnCheckedChangeListener {

    public static void start(Context context, String searchKey) {
        Intent intent = new Intent(context, SearchIndexResultActivity.class);
        intent.putExtra("searchKey", searchKey);
        context.startActivity(intent);
    }

    @BindView(R.id.home)
    CardView mBackHome;
    @BindView(R.id.home_icon)
    ImageView mBackIcon;
    @BindView(R.id.search_input_et)
    EditText mSearchInputEt;

    @BindView(R.id.centerPanel)
    RadioGroup mCenterPanel;
    @BindView(R.id.centerPanel_0)
    RadioButton mCenterPanel0;
    @BindView(R.id.centerPanel_1)
    RadioButton mCenterPanel1;
    @BindView(R.id.centerPanel_2)
    RadioButton mCenterPanel2;
    @BindView(R.id.centerPanel_3)
    RadioButton mCenterPanel3;
    @BindView(R.id.centerPanel_3_icon)
    TextView mCenterPanel3Icon;

    @BindView(R.id.smart_refresh_layout)
    SmartRefreshLayout mSmartRefreshLayout;
    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;
    SearchResultAdapter mAdapter;
    List<GoodBeanModel> mList = new ArrayList<>();

    String mSearchKey = "";
    int page = 1;
    int size = 10;
    int cate = 0; // cate ： 1 人气，2 销量，3 新品，4 价格
    int type = 1; // type  1 为升序，2 为降序

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);

        RxView.setOnClickListeners(this, mBackHome, mCenterPanel0, mCenterPanel1,
                mCenterPanel2, mCenterPanel3);
        mCenterPanel.setOnCheckedChangeListener(this);

        initSearchInput();
        initSmartRefreshLayout();
        initRecyclerView();
        mCenterPanel0.performClick();
    }

    private void initSmartRefreshLayout() {
        mSmartRefreshLayout.setEnableRefresh(true);
        mSmartRefreshLayout.setEnableLoadMore(true);
        mSmartRefreshLayout.setEnableAutoLoadMore(false);
        mSmartRefreshLayout.setOnRefreshListener(refreshLayout -> {
            // 刷新数据
            refreshLayout.setEnableLoadMore(true);
            page = 1;
            gotoSearch(mSearchInputEt.getText().toString());
        });
        mSmartRefreshLayout.setOnLoadMoreListener(refreshLayout -> {
            // 加载数据
            gotoSearch(mSearchInputEt.getText().toString());
        });
    }

    private void initRecyclerView() {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new SearchResultAdapter(this, mList);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener((view, position) -> {
            ProductDetailsActivity.start(this, String.valueOf(mList.get(position).getId()));
        });
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
                    page = 1;
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
        showProgressDialog();
        String token = GetTokenUtils.getToken(this, ApiStores.API_SERVER_INDEX_SEARCHGOODS);
        addSubscription(apiStores().indexSearchGoods(token, searchKey, cate, type, page, size), new ApiCallback<IndexSearchModel>() {
            @Override
            public void onSuccess(IndexSearchModel model) {
                if (model.getCode() == 1) {
                    if (page == 1)
                        mList.clear();
                    page++;

                    mList.addAll(model.getData().getData());
                    mAdapter.notifyDataSetChanged();
                    mSmartRefreshLayout.finishLoadMore(1, true, model.getData().getData().size() < size);
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
                mSmartRefreshLayout.finishRefresh(1, true);
            }
        });
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        mSmartRefreshLayout.finishRefresh();
        mSmartRefreshLayout.finishLoadMore();

        switch (checkedId) {
            case R.id.centerPanel_0:
                if (cate != 1) {
                    cate = 1;
                    type = 1;
                    page = 1;
                    gotoSearch(mSearchInputEt.getText().toString());
                }
                break;
            case R.id.centerPanel_1:
                if (cate != 2) {
                    cate = 2;
                    type = 1;
                    page = 1;
                    gotoSearch(mSearchInputEt.getText().toString());
                }
                break;
            case R.id.centerPanel_2:
                if (cate != 3) {
                    cate = 3;
                    type = 1;
                    page = 1;
                    gotoSearch(mSearchInputEt.getText().toString());
                }
                break;
            default:
                break;
        }
        mCenterPanel3Icon.setVisibility(cate == 4 ? View.VISIBLE : View.GONE);
        mCenterPanel3Icon.setRotation(type == 1 ? 180f : 0f);
    }

    @Override
    public void onClick(Object o) {
        View v = (View) o;
        switch (v.getId()) {
            case R.id.home:
                onBackPressed();
                break;
            case R.id.centerPanel_3:
                mSmartRefreshLayout.finishRefresh();
                mSmartRefreshLayout.finishLoadMore();
                if (cate != 4) {
                    cate = 4;
                    type = 1;
                } else {
                    type = type == 1 ? 2 : 1;
                }
                page = 1;
                gotoSearch(mSearchInputEt.getText().toString());
                mCenterPanel3Icon.setVisibility(cate == 4 ? View.VISIBLE : View.GONE);
                mCenterPanel3Icon.setRotation(type == 1 ? 180f : 0f);
                break;
            default:
                break;
        }
    }
}
