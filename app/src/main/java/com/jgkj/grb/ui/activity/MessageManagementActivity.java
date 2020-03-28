package com.jgkj.grb.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.FrameLayout;

import com.jgkj.grb.R;
import com.jgkj.grb.base.BaseActivity;
import com.jgkj.grb.onclick.RxView;
import com.jgkj.grb.ui.fragment.FragmentMessage;

import butterknife.BindView;

/**
 * 消息
 */
public class MessageManagementActivity extends BaseActivity {

    public static void start(Context context) {
        Intent intent = new Intent(context, MessageManagementActivity.class);
        context.startActivity(intent);
    }

    @BindView(R.id.message_system)
    FrameLayout mMessageSystem;
    @BindView(R.id.message_mall)
    FrameLayout mMessageMall;
    @BindView(R.id.message_proxy)
    FrameLayout mMessageProxy;

    FragmentMessage mFragmentMessageSystem;
    FragmentMessage mFragmentMessageMall;
    FragmentMessage mFragmentMessageProxy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_management);

        Toolbar toolbar = initToolBar(getString(R.string.activity_title_message_management));

        RxView.setOnClickListeners(this, mMessageSystem, mMessageMall, mMessageProxy);

        changeFragment(0);
    }

    @Override
    public void onClick(Object o) {
        View v = (View) o;
        // 1：系统消息，2：商城活动，3：代理消息，4，资讯消息
        switch (v.getId()) {
            case R.id.message_system:
                changeFragment(0);
                updatePushMsg(1);
                break;
            case R.id.message_mall:
                changeFragment(1);
                updatePushMsg(2);
                break;
            case R.id.message_proxy:
                changeFragment(2);
                updatePushMsg(3);
                break;
            default:
                break;
        }
    }

    private void changeFragment(int index) {
        FragmentManager supportFragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = supportFragmentManager.beginTransaction();

        hideAllFragment(fragmentTransaction);
        showFragment(fragmentTransaction, index);

        fragmentTransaction.commit();
    }

    private void showFragment(FragmentTransaction fragmentTransaction, int index) {
        switch (index) {
            case 0:
                if (null == mFragmentMessageSystem) {
                    mFragmentMessageSystem = FragmentMessage.newInstance(index + 1);
                    fragmentTransaction.add(R.id.bottomPanel, mFragmentMessageSystem);
                }
                fragmentTransaction.show(mFragmentMessageSystem);
                break;
            case 1:
                if (null == mFragmentMessageMall) {
                    mFragmentMessageMall = FragmentMessage.newInstance(index + 1);
                    fragmentTransaction.add(R.id.bottomPanel, mFragmentMessageMall);
                }
                fragmentTransaction.show(mFragmentMessageMall);
                break;
            case 2:
                if (null == mFragmentMessageProxy) {
                    mFragmentMessageProxy = FragmentMessage.newInstance(index + 1);
                    fragmentTransaction.add(R.id.bottomPanel, mFragmentMessageProxy);
                }
                fragmentTransaction.show(mFragmentMessageProxy);
                break;
            default:
                break;
        }
    }

    private void hideAllFragment(FragmentTransaction fragmentTransaction) {
        if (null != mFragmentMessageSystem) {
            fragmentTransaction.hide(mFragmentMessageSystem);
        }
        if (null != mFragmentMessageMall) {
            fragmentTransaction.hide(mFragmentMessageMall);
        }
        if (null != mFragmentMessageProxy) {
            fragmentTransaction.hide(mFragmentMessageProxy);
        }
    }
}
