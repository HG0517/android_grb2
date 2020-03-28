package com.jgkj.grb.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jgkj.grb.R;
import com.jgkj.grb.base.BaseActivity;
import com.jgkj.grb.glide.GlideApp;
import com.jgkj.grb.itemdecoration.SpaceItemDecoration;
import com.jgkj.grb.retrofit.ApiCallback;
import com.jgkj.grb.retrofit.ApiStores;
import com.jgkj.grb.ui.adapter.FragmentFindAdapter;
import com.jgkj.grb.ui.mvp.BannerData;
import com.jgkj.grb.ui.mvp.find.FindModel;
import com.jgkj.grb.ui.mvp.find.FindPageModel;
import com.jgkj.utils.token.GetTokenUtils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.stx.xhb.xbanner.XBanner;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 发现：资讯
 */
public class FindActivity extends BaseActivity {

    public static void start(Context context) {
        Intent intent = new Intent(context, FindActivity.class);
        context.startActivity(intent);
    }

    @BindView(R.id.search_input_et)
    EditText mSearchInputEt;

    @BindView(R.id.smart_refresh_layout)
    SmartRefreshLayout mSmartRefreshLayout;
    @BindView(R.id.topContent)
    LinearLayout mTopContent;
    @BindView(R.id.top_content_title)
    TextView mTopContentTitle;
    @BindView(R.id.mXBanner)
    XBanner mXBanner;
    @BindView(R.id.tabLayout)
    TabLayout mTabLayout;

    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;
    @BindView(R.id.recycler_view_search)
    RecyclerView mRecyclerViewSearch;

    FragmentFindAdapter mAdapter;
    FragmentFindAdapter mAdapterSearch;
    List<FindPageModel.DataBean> mListInit = new ArrayList<>();
    List<FindPageModel.DataBean> mListSearch = new ArrayList<>();

    FindModel mFindModel;
    List<BannerData> mXBannerDatas = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find);

        Toolbar toolbar = initToolBar("");

        initSearchInput();
        initRecyclerView();
        onLazyLoad();
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
        indexSearchm(searchKey);
    }

    private void initRecyclerView() {
        // 标签下的列表
        mRecyclerView.setNestedScrollingEnabled(false);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mActivity));
        mRecyclerView.addItemDecoration(new SpaceItemDecoration(dp2px(1)));
        mAdapter = new FragmentFindAdapter(mActivity, mListInit);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener((view, position) -> {
            FindDetailsWebViewActivity.start(mActivity, String.valueOf(mListInit.get(position).getId()));
        });

        // 搜索结果列表
        mRecyclerViewSearch.setNestedScrollingEnabled(false);
        mRecyclerViewSearch.setLayoutManager(new LinearLayoutManager(mActivity));
        mRecyclerViewSearch.addItemDecoration(new SpaceItemDecoration(dp2px(1)));
        mAdapterSearch = new FragmentFindAdapter(mActivity, mListSearch);
        mRecyclerViewSearch.setAdapter(mAdapterSearch);
        mAdapterSearch.setOnItemClickListener((view, position) -> {
            FindDetailsWebViewActivity.start(mActivity, String.valueOf(mListSearch.get(position).getId()));
        });
    }

    private void initTabLayout() {
        if (null != mFindModel && null != mFindModel.getData().getCate() && mFindModel.getData().getCate().size() > 0) {
            for (int i = 0; i < mFindModel.getData().getCate().size(); i++) {
                mTabLayout.addTab(mTabLayout.newTab().setText(mFindModel.getData().getCate().get(i).getCate_name()));
            }
            mTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
                @Override
                public void onTabSelected(TabLayout.Tab tab) {
                    onLazyLoadPage(mFindModel.getData().getCate().get(tab.getPosition()).getId());
                }

                @Override
                public void onTabUnselected(TabLayout.Tab tab) {
                }

                @Override
                public void onTabReselected(TabLayout.Tab tab) {
                }
            });
            onLazyLoadPage(mFindModel.getData().getCate().get(0).getId());
        }
    }

    private void initXBanner() {
        mXBanner.setOnItemClickListener((banner, model, view, position) -> {
            if (model instanceof BannerData) {
                //FindDetailsWebViewActivity.start(mActivity);
            }
        });
        mXBanner.loadImage((banner, model, view, position) -> {
            if (model instanceof BannerData) {
                BannerData data = (BannerData) model;
                ImageView img = view.findViewById(R.id.showIv);
                TextView title = view.findViewById(R.id.showTitle);
                title.setText(data.getBannerTitle());
                GlideApp.with(this)
                        .load(data.getBannerUrl())
                        .centerCrop()
                        .into(img);
            }
        });
        if (null != mFindModel && null != mFindModel.getData().getHots() && mFindModel.getData().getHots().size() > 0) {
            mXBanner.setVisibility(View.VISIBLE);
            for (int i = 0; i < mFindModel.getData().getHots().size(); i++) {
                mXBannerDatas.add(new BannerData(
                        mFindModel.getData().getHots().get(i).getPic().startsWith("http:") || mFindModel.getData().getHots().get(i).getPic().startsWith("https:")
                                ? mFindModel.getData().getHots().get(i).getPic().replaceAll("\\\\", "/")
                                : ApiStores.API_SERVER_URL + mFindModel.getData().getHots().get(i).getPic().replaceAll("\\\\", "/"),
                        mFindModel.getData().getHots().get(i).getTitle()));
            }
            LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) mXBanner.getLayoutParams();
            if (mXBannerDatas.size() >= 3) {
                layoutParams.setMargins(0, 0, 0, 0);
                mXBanner.setIsClipChildrenMode(true);
            } else {
                layoutParams.setMargins(dp2px(15), dp2px(5), dp2px(15), 0);
                mXBanner.setIsClipChildrenMode(false);
            }
            mXBanner.setLayoutParams(layoutParams);

            mXBanner.setBannerData(R.layout.layout_item_fragment_find_xbanner, mXBannerDatas);
        } else {
            mXBanner.setVisibility(View.GONE);
        }
    }

    /**
     * 主接口数据
     */
    protected void onLazyLoad() {
        showProgressDialog();
        addSubscription(apiStores().consultation(), new ApiCallback<FindModel>() {
            @Override
            public void onSuccess(FindModel model) {
                mFindModel = model;
                if (model.getCode() == 1) {
                    initXBanner();
                    initTabLayout();
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

    /**
     * 列表数据
     */
    private void onLazyLoadPage(Object ca_id) {
        showProgressDialog();
        addSubscription(apiStores().consultationListNews(ca_id), new ApiCallback<FindPageModel>() {
            @Override
            public void onSuccess(FindPageModel model) {
                if (model.getCode() == 1) {
                    mListInit.clear();
                    mListInit.addAll(model.getData());
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
            }
        });
    }

    /**
     * 搜索
     */
    private void indexSearchm(String searchKey) {
        mTopContent.setVisibility(View.GONE);
        mRecyclerViewSearch.setVisibility(View.VISIBLE);
        showProgressDialog();
        String token = GetTokenUtils.getToken(mActivity, ApiStores.API_SERVER_INDEX_SEARCHM);
        addSubscription(apiStores().indexSearchm(token, searchKey), new ApiCallback<FindPageModel>() {
            @Override
            public void onSuccess(FindPageModel model) {
                if (model.getCode() == 1) {
                    mListSearch.clear();
                    mListSearch.addAll(model.getData());
                    mAdapterSearch.notifyDataSetChanged();
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

}
