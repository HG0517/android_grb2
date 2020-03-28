package com.jgkj.grb.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import com.jgkj.grb.R;
import com.jgkj.grb.base.BaseFragment;

import butterknife.BindView;

/**
 * 我的订单：type  1:商城订单，2：云仓库订单，3：抢购订单，4：谷聚金平台订单 ，5：谷聚金区/县订单
 * Created by brightpoplar@163.com on 2019/8/21.
 */
public class FragmentOrderManagement extends BaseFragment {

    public static FragmentOrderManagement newInstance(int type) {
        FragmentOrderManagement fragmentOrderManagement = new FragmentOrderManagement();
        Bundle bundle = new Bundle();
        bundle.putInt("type", type);
        fragmentOrderManagement.setArguments(bundle);
        return fragmentOrderManagement;
    }

    @BindView(R.id.tabLayout)
    TabLayout mTabLayout;
    @BindView(R.id.viewPager)
    ViewPager mViewPager;
    OrderManagementPagerAdapter adapter;

    String[] tabText;

    int mType = 0;

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_order_management;
    }

    @Override
    public void init(Bundle savedInstanceState) {

        Bundle arguments = getArguments();
        if (null == arguments) {
            toastShow(R.string.open_activity_error_option);
            return;
        }
        mType = arguments.getInt("type", 0);
        if (mType <= 0) {
            toastShow(R.string.open_activity_error_option);
            return;
        }

        initTabLayout();
        initViewPager();
    }

    private void initViewPager() {
        adapter = new OrderManagementPagerAdapter(getChildFragmentManager());
        mViewPager.setAdapter(adapter);
        mTabLayout.setupWithViewPager(mViewPager);
        mViewPager.setOffscreenPageLimit(mTabLayout.getTabCount());
    }

    private void initTabLayout() {
        tabText = getResources().getStringArray(mType == 4 || mType == 5 ? R.array.tab_gujvjin_order_management : R.array.tab_order_management);
        for (String tab : tabText) {
            mTabLayout.addTab(mTabLayout.newTab().setText(tab));
        }
    }

    @Override
    protected void onLazyLoad() {

    }

    class OrderManagementPagerAdapter extends FragmentPagerAdapter {

        public OrderManagementPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int i) {
            if (mType == 4 || mType == 5) {
                int type = -1;
                if (i > 1) {
                    type = i + 1;
                } else if (i == 0) {
                    type = 100;
                } else if (i == 1) {
                    type = i - 1;
                }
                return FragmentOrderManagementPage.newInstance(mType, type);
            }
            return FragmentOrderManagementPage.newInstance(mType, i > 1 ? i + 1 : i);
        }

        @Override
        public int getCount() {
            return null == tabText ? 0 : tabText.length;
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            if (getCount() > 0)
                return tabText[position];
            return super.getPageTitle(position);
        }
    }
}
