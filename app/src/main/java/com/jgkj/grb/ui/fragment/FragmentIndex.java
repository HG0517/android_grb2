package com.jgkj.grb.ui.fragment;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.CardView;
import android.text.TextUtils;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.jgkj.grb.R;
import com.jgkj.grb.base.BaseFragment;
import com.jgkj.grb.onclick.RxView;
import com.jgkj.grb.retrofit.ApiCallback;
import com.jgkj.grb.ui.activity.MessageManagementActivity;
import com.jgkj.grb.ui.activity.SearchActivity;
import com.jgkj.grb.ui.activity.WebViewActivity;
import com.jgkj.grb.ui.mvp.index.IndexCatesBean;
import com.jgkj.grb.ui.mvp.index.IndexCatesListModel;
import com.yzq.zxinglibrary.android.CaptureActivity;
import com.yzq.zxinglibrary.bean.ZxingConfig;
import com.yzq.zxinglibrary.common.Constant;

import java.util.Collections;
import java.util.List;

import butterknife.BindView;

/**
 * 首页
 * Created by brightpoplar@163.com on 2019/7/27.
 */
public class FragmentIndex extends BaseFragment {

    public static FragmentIndex newInstance() {
        return new FragmentIndex();
    }

    @BindView(R.id.index_top_iv)
    ImageView mIndexTopIv;

    @BindView(R.id.topPanel)
    ConstraintLayout mIndexTopPanel;
    @BindView(R.id.index_scan)
    FrameLayout mIndexTopScan;
    @BindView(R.id.index_search)
    CardView mIndexTopSearch;
    @BindView(R.id.index_service)
    FrameLayout mIndexTopService;
    @BindView(R.id.index_msg)
    FrameLayout mIndexTopMsg;

    @BindView(R.id.tabLayout)
    TabLayout mTabLayout;
    @BindView(R.id.viewPager)
    ViewPager mViewPager;

    IndexPagerAdapter mPagerAdapter;
    IndexCatesListModel mIndexCatesModel;

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_index;
    }

    @Override
    public void init(Bundle savedInstanceState) {
        mIndexTopPanel.setPadding(0, getStatusBarHeight(), 0, 0);
        RxView.setOnClickListeners(this, mIndexTopScan, mIndexTopSearch, mIndexTopService, mIndexTopMsg);
    }

    private void initPager() {
        Collections.reverse(mIndexCatesModel.getData().getCates());
        mPagerAdapter = new IndexPagerAdapter(getFragmentManager(), mIndexCatesModel.getData().getCates());
        mViewPager.setAdapter(mPagerAdapter);
        mTabLayout.setupWithViewPager(mViewPager);
        mViewPager.setOffscreenPageLimit(mTabLayout.getTabCount());
        for (int i = 0; i < mTabLayout.getTabCount(); i++) {
            TabLayout.Tab tab = mTabLayout.getTabAt(i);
            if (tab != null) {
                tab.setCustomView(mPagerAdapter.getTabView(i));
            }
        }
        mTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                View view = tab.getCustomView();
                if (tab.getPosition() == 0) {
                    getRootView().setBackgroundColor(Color.WHITE);
                } else {
                    getRootView().setBackgroundColor(Color.parseColor("#F8F8F8"));
                }
                if (view instanceof TextView) {
                    ((TextView) view).setTextSize(16);
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                View view = tab.getCustomView();
                if (view instanceof TextView) {
                    ((TextView) view).setTextSize(14);
                }
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    @Override
    protected void onLazyLoad() {
        showProgressDialog();
        addSubscription(apiStores().indexCates(), new ApiCallback<IndexCatesListModel>() {
            @Override
            public void onSuccess(IndexCatesListModel model) {
                mIndexCatesModel = model;
                if (mIndexCatesModel.getCode() == 1) {
                    initPager();
                } else {
                    toastShow(mIndexCatesModel.getMsg());
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
    public void onClick(Object o) {
        View v = (View) o;
        switch (v.getId()) {
            case R.id.index_scan:
                openScan();
                break;
            case R.id.index_search:
                SearchActivity.start(mActivity, "index", 0);
                break;
            case R.id.index_service:
                huanXinKeFu();
                break;
            case R.id.index_msg:
                if (isLogin())
                    MessageManagementActivity.start(mActivity);
                break;
            default:
                break;
        }
    }

    private void openScan() {
        Intent intent = new Intent(mActivity, CaptureActivity.class);
        setScanConfig(intent);
        startActivityForResult(intent, 10002);
    }

    private void setScanConfig(Intent intent) {
        ZxingConfig config = new ZxingConfig();
        config.setPlayBeep(true); //是否播放扫描声音 默认为true
        config.setShake(true); //是否震动  默认为true
        config.setShowbottomLayout(true); // 是否显示下方的其他功能布局 默认为true
        config.setShowFlashLight(true); //是否显示闪光灯按钮 默认为true
        config.setShowAlbum(true); //是否显示相册按钮 默认为true
        config.setDecodeBarCode(true); //是否扫描条形码 默认为true
        config.setFullScreenScan(true); //是否全屏扫描  默认为true  设为false则只会在扫描框中扫描
        config.setReactColor(R.color.colorAccent); //设置扫描框四个角的颜色 默认为白色
        config.setFrameLineColor(R.color.colorAccent); //设置扫描框边框颜色 默认无色
        config.setScanLineColor(R.color.colorAccent); //设置扫描线的颜色 默认白色
        intent.putExtra(Constant.INTENT_ZXING_CONFIG, config);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // 扫描二维码/条码回传
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == 10002 && data != null) {
                String content = data.getStringExtra(Constant.CODED_CONTENT);
                if (!TextUtils.isEmpty(content)
                        && (content.startsWith("http:") || content.startsWith("https:"))) {
                    WebViewActivity.start(mActivity, content, "");
                } else {
                    toastShow(String.format(getString(R.string.index_scan_result), content));
                }
            }
        }
    }

    class IndexPagerAdapter extends FragmentPagerAdapter {
        private List<IndexCatesBean> mList;

        public IndexPagerAdapter(FragmentManager fm, List<IndexCatesBean> mList) {
            super(fm);
            this.mList = mList;
        }

        @Override
        public Fragment getItem(int i) {
            if (mList.get(i).getId() == 27
                    || TextUtils.equals(mList.get(i).getCa_name(), "推荐"))
                return FragmentIndexPromote.newInstance(mList.get(i).getId());
            return FragmentIndexChild.newInstance(mList.get(i));
        }

        @Override
        public int getCount() {
            return null == mList ? 0 : mList.size();
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return mList.get(position).getCa_name();
        }

        public View getTabView(int position) {
            View view = getLayoutInflater().inflate(R.layout.layout_tablayout_item_index, null, false);
            TextView textView = view.findViewById(R.id.itemText);
            textView.setText(getPageTitle(position));
            if (0 == position)
                textView.setTextSize(16);
            return textView;
        }
    }
}
