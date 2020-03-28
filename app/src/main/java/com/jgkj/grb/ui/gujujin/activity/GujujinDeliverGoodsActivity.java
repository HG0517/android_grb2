package com.jgkj.grb.ui.gujujin.activity;

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
import android.view.Menu;
import android.view.MenuItem;

import com.jgkj.grb.R;
import com.jgkj.grb.base.BaseActivity;
import com.jgkj.grb.ui.fragment.FragmentOrderManagementPage;

import butterknife.BindView;

/**
 * 谷聚金：发货，区/县代理
 */
public class GujujinDeliverGoodsActivity extends BaseActivity {

    public static void start(Context context) {
        Intent intent = new Intent(context, GujujinDeliverGoodsActivity.class);
        context.startActivity(intent);
    }

    @BindView(R.id.tabLayout)
    TabLayout mTabLayout;
    @BindView(R.id.viewPager)
    ViewPager mViewPager;
    OrderDeliverPagerAdapter adapter;

    String[] tabText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gujujin_deliver_goods);
        Toolbar toolbar = initToolBar(getString(R.string.activity_title_gujujin_deliver_goods));

        initTabLayout();
        initViewPager();
    }

    private void initViewPager() {
        adapter = new OrderDeliverPagerAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(adapter);
        mTabLayout.setupWithViewPager(mViewPager);
        mViewPager.setOffscreenPageLimit(mTabLayout.getTabCount());
    }

    private void initTabLayout() {
        tabText = getResources().getStringArray(R.array.tab_order_delivery);
        for (String tab : tabText) {
            mTabLayout.addTab(mTabLayout.newTab().setText(tab));
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_search:
                //SearchActivity.start(mActivity, "order", mType);
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
        getMenuInflater().inflate(R.menu.menu_order_management, menu);
        return true;
    }

    class OrderDeliverPagerAdapter extends FragmentPagerAdapter {

        public OrderDeliverPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int i) {
            return FragmentOrderManagementPage.newInstance(9, i + 3);
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
