package com.jgkj.grb.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;

import com.jgkj.grb.R;
import com.jgkj.grb.base.BaseActivity;
import com.jgkj.grb.onclick.RxView;
import com.jgkj.grb.ui.fragment.FragmentOrderManagement;

import butterknife.BindView;

/**
 * 我的订单
 */
public class OrderManagementActivity extends BaseActivity {

    public static void start(Context context) {
        Intent intent = new Intent(context, OrderManagementActivity.class);
        context.startActivity(intent);
    }

    @BindView(R.id.order_choose)
    FrameLayout mOrderChoose;
    @BindView(R.id.order_mall)
    CardView mOrderMall;
    @BindView(R.id.order_rush_buy)
    CardView mOrderRushBuy;
    @BindView(R.id.order_cloud_warehouse)
    CardView mOrderCloudWarehouse;

    FragmentOrderManagement mOrderManagementMall;
    FragmentOrderManagement mOrderManagementRushBuy;
    FragmentOrderManagement mOrderManagementCloudWarehouse;

    int mType = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_management);

        Toolbar toolbar = initToolBar(getString(R.string.activity_title_order_management_mall));
        initToolBarIcon(toolbar, 0, 0, R.mipmap.ic_order_arrow_down, 0, v -> {
            mOrderChoose.setVisibility(View.VISIBLE);
        });

        mOrderChoose.setPadding(0, getStatusBarHeight() + dp2px(44) - dp2px(5), 0, 0);

        RxView.setOnClickListeners(this, mOrderChoose, mOrderMall, mOrderRushBuy, mOrderCloudWarehouse);

        changeFragment(0);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_search:
                SearchActivity.start(mActivity, "order", mType);
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

    @Override
    public void onClick(Object o) {
        View v = (View) o;
        switch (v.getId()) {
            case R.id.order_choose:
                mOrderChoose.setVisibility(View.GONE);
                break;
            case R.id.order_mall:
                initToolBar(getString(R.string.activity_title_order_management_mall));
                mOrderChoose.setVisibility(View.GONE);
                changeFragment(0);
                break;
            case R.id.order_rush_buy:
                initToolBar(getString(R.string.activity_title_order_management_rushbuy));
                mOrderChoose.setVisibility(View.GONE);
                changeFragment(1);
                break;
            case R.id.order_cloud_warehouse:
                initToolBar(getString(R.string.activity_title_order_management_cloud));
                mOrderChoose.setVisibility(View.GONE);
                changeFragment(2);
                break;
            default:
                mOrderChoose.setVisibility(View.GONE);
                break;
        }
    }

    private void changeFragment(int index) {
        mType = index + 1;
        FragmentManager supportFragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = supportFragmentManager.beginTransaction();

        hideAllFragment(fragmentTransaction);
        showFragment(fragmentTransaction, index);

        fragmentTransaction.commit();
    }

    private void showFragment(FragmentTransaction fragmentTransaction, int index) {
        switch (index) {
            case 0:
                if (null == mOrderManagementMall) {
                    mOrderManagementMall = FragmentOrderManagement.newInstance(1);
                    fragmentTransaction.add(R.id.contentPanel, mOrderManagementMall);
                }
                fragmentTransaction.show(mOrderManagementMall);
                break;
            case 1:
                if (null == mOrderManagementRushBuy) {
                    mOrderManagementRushBuy = FragmentOrderManagement.newInstance(3);
                    fragmentTransaction.add(R.id.contentPanel, mOrderManagementRushBuy);
                }
                fragmentTransaction.show(mOrderManagementRushBuy);
                break;
            case 2:
                if (null == mOrderManagementCloudWarehouse) {
                    mOrderManagementCloudWarehouse = FragmentOrderManagement.newInstance(2);
                    fragmentTransaction.add(R.id.contentPanel, mOrderManagementCloudWarehouse);
                }
                fragmentTransaction.show(mOrderManagementCloudWarehouse);
                break;
            default:
                break;
        }
    }

    private void hideAllFragment(FragmentTransaction fragmentTransaction) {
        if (null != mOrderManagementMall) {
            fragmentTransaction.hide(mOrderManagementMall);
        }
        if (null != mOrderManagementRushBuy) {
            fragmentTransaction.hide(mOrderManagementRushBuy);
        }
        if (null != mOrderManagementCloudWarehouse) {
            fragmentTransaction.hide(mOrderManagementCloudWarehouse);
        }
    }
}
