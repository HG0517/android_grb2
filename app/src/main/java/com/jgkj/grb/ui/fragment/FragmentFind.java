package com.jgkj.grb.ui.fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jgkj.grb.R;
import com.jgkj.grb.base.BaseFragment;
import com.jgkj.grb.eventbus.RefreshPushMsg;
import com.jgkj.grb.onclick.RxView;
import com.jgkj.grb.push.JExampleUtil;
import com.jgkj.grb.ui.activity.ConventionCentreActivity;
import com.jgkj.grb.ui.activity.FindActivity;
import com.jgkj.grb.ui.activity.MessageManagementActivity;

import org.greenrobot.eventbus.EventBus;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;

/**
 * 发现
 * Created by brightpoplar@163.com on 2019/7/27.
 */
public class FragmentFind extends BaseFragment {

    public static FragmentFind newInstance() {
        return new FragmentFind();
    }

    @BindView(R.id.find_find)
    FrameLayout mFindFind;
    @BindView(R.id.find_convention)
    FrameLayout mFindConvention;
    @BindView(R.id.find_message)
    FrameLayout mFindMessage;

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_find;
    }

    @Override
    public void init(Bundle savedInstanceState) {
        RxView.setOnClickListeners(this, mFindFind, mFindConvention, mFindMessage);
    }

    @Override
    public void onClick(Object o) {
        View v = (View) o;
        switch (v.getId()) {
            case R.id.find_find: // 资讯
                FindActivity.start(mActivity);
                updatePushMsg(4);
                break;
            case R.id.find_convention: // 会议
                if (isLogin())
                    ConventionCentreActivity.start(mActivity);
                break;
            case R.id.find_message: // 消息
                if (isLogin())
                    MessageManagementActivity.start(mActivity);
                break;
            default:
                break;
        }
    }

    @Override
    protected void onLazyLoad() {

    }

}
