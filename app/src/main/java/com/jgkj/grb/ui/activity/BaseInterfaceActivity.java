package com.jgkj.grb.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import com.jgkj.grb.R;
import com.jgkj.grb.base.BaseActivity;
import com.jgkj.grb.retrofit.ApiCallback;
import com.jgkj.grb.retrofit.ApiStores;
import com.jgkj.grb.ui.adapter.SearchResultAdapter;
import com.jgkj.grb.ui.fragment.FragmentBaseInterface;
import com.jgkj.grb.ui.mvp.IndexPlaceGoodsModel;
import com.jgkj.grb.ui.mvp.IndexPlaceModel;
import com.jgkj.grb.ui.mvp.index.GoodBeanModel;
import com.jgkj.utils.token.GetTokenUtils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 公让宝基地
 */
public class BaseInterfaceActivity extends BaseActivity {

    public static void start(Context context) {
        Intent intent = new Intent(context, BaseInterfaceActivity.class);
        context.startActivity(intent);
    }

    @BindView(R.id.search_input)
    ConstraintLayout mSearchInput;
    @BindView(R.id.search_input_et)
    EditText mSearchInputEt;

    @BindView(R.id.tabLayout)
    TabLayout mTabLayout;
    @BindView(R.id.viewPager)
    ViewPager mViewPager;
    BaseInterfacePagerAdapter adapter;

    @BindView(R.id.smart_refresh_layout)
    SmartRefreshLayout mSmartRefreshLayout;
    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;
    SearchResultAdapter mAdapter;
    List<GoodBeanModel> mList = new ArrayList<>();
    String mSearchKey = "";
    int page = 1;
    int size = 10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base_interface);
        initToolBar("");

        mSearchInput.setPadding(0, getStatusBarHeight() + dp2px(5), 0, dp2px(5));
        initSearchInput();
        initSmartRefreshLayout();
        initRecyclerView();

        indexPlaceGoods();
    }

    private void initSmartRefreshLayout() {
        mSmartRefreshLayout.setEnableRefresh(true);
        mSmartRefreshLayout.setEnableLoadMore(true);
        mSmartRefreshLayout.setEnableAutoLoadMore(false);
        mSmartRefreshLayout.setOnRefreshListener(refreshLayout -> {
            // 刷新数据
            refreshLayout.setEnableLoadMore(true);
            page = 1;
            onSearchLoad();
        });
        mSmartRefreshLayout.setOnLoadMoreListener(refreshLayout -> {
            // 加载数据
            onSearchLoad();
        });
        mSmartRefreshLayout.setVisibility(View.GONE);
    }

    private void initRecyclerView() {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mActivity));
        mAdapter = new SearchResultAdapter(mActivity, mList, 0);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener((view, position) -> {
            ProductDetailsActivity.start(mActivity, String.valueOf(mList.get(position).getId()));
        });
    }

    private void initSearchInput() {
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
        if (page == 1 && TextUtils.equals(mSearchKey, searchKey))
            return;
        page = 1;
        mSearchKey = searchKey;
        onSearchLoad();
    }

    private void initTabLayout(List<IndexPlaceModel.DataBean> list) {
        adapter = new BaseInterfacePagerAdapter(this, getSupportFragmentManager(), list);
        mViewPager.setAdapter(adapter);
        mTabLayout.setupWithViewPager(mViewPager);
        mViewPager.setOffscreenPageLimit(mTabLayout.getTabCount());
        for (int i = 0; i < mTabLayout.getTabCount(); i++) {
            TabLayout.Tab tab = mTabLayout.getTabAt(i);
            if (tab != null) {
                tab.setCustomView(adapter.getTabItem(i));
            }
        }
    }

    private void onSearchLoad() {
        showProgressDialog();
        String token = GetTokenUtils.getToken(this, ApiStores.API_SERVER_INDEX_SEARCHPLACE);
        addSubscription(apiStores().indexSearchPlace(token, mSearchKey, page, size), new ApiCallback<IndexPlaceGoodsModel>() {
            @Override
            public void onSuccess(IndexPlaceGoodsModel model) {
                if (model.getCode() == 1) {
                    if (page == 1)
                        mList.clear();
                    page++;

                    showSearchView();

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
            }

            @Override
            public void onFinish() {
                dismissProgressDialog();
                mSmartRefreshLayout.finishRefresh(1, true);
            }
        });
    }

    private void indexPlaceGoods() {
        showProgressDialog();
        addSubscription(apiStores().indexPlaceGoods(), new ApiCallback<IndexPlaceModel>() {
            @Override
            public void onSuccess(IndexPlaceModel model) {
                if (model.getCode() == 1) {
                    initTabLayout(model.getData());
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

    class BaseInterfacePagerAdapter extends FragmentPagerAdapter {
        private Context mContext;
        private List<IndexPlaceModel.DataBean> mList;

        public BaseInterfacePagerAdapter(Context mContext, FragmentManager fm, List<IndexPlaceModel.DataBean> mList) {
            super(fm);
            this.mContext = mContext;
            this.mList = mList;
        }

        @Override
        public Fragment getItem(int i) {
            return FragmentBaseInterface.newInstance(mList.get(i));
        }

        @Override
        public int getCount() {
            return null == mList ? 0 : mList.size();
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return super.getPageTitle(position);
        }

        public View getTabItem(int position) {
            View tabItem = LayoutInflater.from(mContext).inflate(R.layout.layout_tablayout_item_base_interface, null);
            IndexPlaceModel.DataBean tabInfo = mList.get(position);
            TextView itemText = tabItem.findViewById(R.id.itemText);
            itemText.setText(tabInfo.getArea_name());
            return tabItem;
        }
    }

    private void showSearchView() {
        mTabLayout.setVisibility(View.GONE);
        mViewPager.setVisibility(View.GONE);
        mSmartRefreshLayout.setVisibility(View.VISIBLE);
    }

    private boolean showTabViewPager() {
        if (null != mSmartRefreshLayout && mSmartRefreshLayout.isShown()) {
            mSmartRefreshLayout.setVisibility(View.GONE);
            mTabLayout.setVisibility(View.VISIBLE);
            mViewPager.setVisibility(View.VISIBLE);
            return true;
        }
        return false;
    }
}
