package com.jgkj.grb.ui.gujujin.activity;

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
 * 谷聚金：订单，2 区/县代理、1 个人代理
 */
public class GujujinOrderActivity extends BaseActivity {

    public static void start(Context context, int level) {
        Intent intent = new Intent(context, GujujinOrderActivity.class);
        intent.putExtra("level", level);
        context.startActivity(intent);
    }

    @BindView(R.id.order_choose)
    FrameLayout mOrderChoose;
    @BindView(R.id.order_platform)
    CardView mOrderPlatform;
    @BindView(R.id.order_district)
    CardView mOrderDistrict;

    FragmentOrderManagement mOrderManagementPlatform;
    FragmentOrderManagement mOrderManagementDistrict;

    int mLevel = -1;
    int mType = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gujujin_order);

        Intent intent = getIntent();
        if (intent.hasExtra("level")) {
            mLevel = intent.getIntExtra("level", -1);
        }
        if (mLevel < 0) {
            toastShow(R.string.open_activity_error_option);
            return;
        }
        if (mLevel == 2) {
            initToolBar(getString(R.string.personal_order));
        } else if (mLevel == 1) {
            Toolbar toolbar = initToolBar(getString(R.string.gujujin_order_platform));
            initToolBarIcon(toolbar, 0, 0, R.mipmap.ic_order_arrow_down, 0, v -> {
                mOrderChoose.setVisibility(View.VISIBLE);
            });

            mOrderChoose.setPadding(0, getStatusBarHeight() + dp2px(44) - dp2px(5), 0, 0);
        }

        RxView.setOnClickListeners(this, mOrderChoose, mOrderPlatform, mOrderDistrict);

        changeFragment(4);
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

    @Override
    public void onClick(Object o) {
        View v = (View) o;
        switch (v.getId()) {
            case R.id.order_choose:
                mOrderChoose.setVisibility(View.GONE);
                break;
            case R.id.order_platform:
                initToolBar(getString(R.string.gujujin_order_platform));
                mOrderChoose.setVisibility(View.GONE);
                changeFragment(4);
                break;
            case R.id.order_district:
                initToolBar(getString(R.string.gujujin_order_county));
                mOrderChoose.setVisibility(View.GONE);
                changeFragment(5);
                break;
            default:
                mOrderChoose.setVisibility(View.GONE);
                break;
        }
    }

    private void changeFragment(int index) {
        mType = index;
        FragmentManager supportFragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = supportFragmentManager.beginTransaction();

        hideAllFragment(fragmentTransaction);
        showFragment(fragmentTransaction, index);

        fragmentTransaction.commit();
    }

    private void showFragment(FragmentTransaction fragmentTransaction, int index) {
        switch (index) {
            case 4:
                if (null == mOrderManagementPlatform) {
                    mOrderManagementPlatform = FragmentOrderManagement.newInstance(4);
                    fragmentTransaction.add(R.id.contentPanel, mOrderManagementPlatform);
                }
                fragmentTransaction.show(mOrderManagementPlatform);
                break;
            case 5:
                if (null == mOrderManagementDistrict) {
                    mOrderManagementDistrict = FragmentOrderManagement.newInstance(5);
                    fragmentTransaction.add(R.id.contentPanel, mOrderManagementDistrict);
                }
                fragmentTransaction.show(mOrderManagementDistrict);
                break;
            default:
                break;
        }
    }

    private void hideAllFragment(FragmentTransaction fragmentTransaction) {
        if (null != mOrderManagementPlatform) {
            fragmentTransaction.hide(mOrderManagementPlatform);
        }
        if (null != mOrderManagementDistrict) {
            fragmentTransaction.hide(mOrderManagementDistrict);
        }
    }

}
