package com.jgkj.grb.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.jgkj.grb.R;
import com.jgkj.grb.base.BaseActivity;
import com.jgkj.grb.retrofit.ApiCallback;
import com.jgkj.grb.ui.fragment.FragmentFlashSale;
import com.jgkj.grb.ui.mvp.FlashSaleModel;

import java.util.List;

import butterknife.BindView;

/**
 * 限时抢购
 */
public class FlashSaleActivity extends BaseActivity {

    public static void start(Context context) {
        Intent intent = new Intent(context, FlashSaleActivity.class);
        context.startActivity(intent);
    }

    @BindView(R.id.tabLayout)
    TabLayout mTabLayout;
    @BindView(R.id.viewPager)
    ViewPager mViewPager;
    FlashSalePagerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flash_sale);

        Toolbar toolbar = initToolBar(getString(R.string.activity_title_flash_sale));

        requestTabList();
    }

    private void requestTabList() {
        showProgressDialog();
        addSubscription(apiStores().limitProduct(), new ApiCallback<FlashSaleModel>() {
            @Override
            public void onSuccess(FlashSaleModel model) {
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

    private void initTabLayout(List<FlashSaleModel.DataBean> array) {
        if (null == array || array.size() <= 0)
            return;
        adapter = new FlashSalePagerAdapter(this, getSupportFragmentManager(), array);
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

    class FlashSalePagerAdapter extends FragmentPagerAdapter {
        private Context mContext;
        private List<FlashSaleModel.DataBean> mList;

        public FlashSalePagerAdapter(Context mContext, FragmentManager fm, List<FlashSaleModel.DataBean> mList) {
            super(fm);
            this.mContext = mContext;
            this.mList = mList;
        }

        @Override
        public Fragment getItem(int i) {
            return FragmentFlashSale.newInstance(mList.get(i));
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
            View tabItem = LayoutInflater.from(mContext).inflate(R.layout.layout_tablayout_item_flash_sale, null);
            FlashSaleModel.DataBean tabInfo = mList.get(position);
            TextView itemTop = tabItem.findViewById(R.id.itemTop);
            TextView itemBottom = tabItem.findViewById(R.id.itemBottom);
            itemTop.setText(tabInfo.getTime());
            itemBottom.setText(tabInfo.getStatus());
            return tabItem;
        }
    }
}
