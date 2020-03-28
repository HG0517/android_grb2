package com.jgkj.grb.ui.activity;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.finddreams.languagelib.OnChangeLanguageEvent;
import com.finddreams.languagelib.RefreshType;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jgkj.grb.R;
import com.jgkj.grb.base.BaseActivity;
import com.jgkj.grb.eventbus.RefreshPushMsg;
import com.jgkj.grb.eventbus.RefreshUserInfo;
import com.jgkj.grb.onclick.RxView;
import com.jgkj.grb.push.JExampleUtil;
import com.jgkj.grb.retrofit.ApiCallback;
import com.jgkj.grb.retrofit.ApiStores;
import com.jgkj.grb.ui.dialog.MainNoticeDialog;
import com.jgkj.grb.ui.fragment.FragmentFind;
import com.jgkj.grb.ui.fragment.FragmentIndex;
import com.jgkj.grb.ui.fragment.FragmentPersonal;
import com.jgkj.grb.ui.fragment.FragmentShoppingCart;
import com.jgkj.grb.ui.mvp.personal.PersonalHeadModel;
import com.jgkj.grb.utils.Logger;
import com.jgkj.grb.version.VersionUpdateHelp;
import com.jgkj.utils.token.GetTokenUtils;
import com.jgkj.utils.token.utils.des.HmacMd5;
import com.jgkj.utils.token.utils.statusbar.StatusBarUtil;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;
import java.util.Set;

import butterknife.BindView;
import cn.bingoogolapple.badgeview.BGABadgeRadioButton;
import cn.bingoogolapple.badgeview.BGABadgeViewHelper;

/**
 * 主页
 */
public class MainActivity extends BaseActivity implements RadioGroup.OnCheckedChangeListener {

    @BindView(R.id.bottom_navigation_bar)
    RadioGroup mBottomNavigationBar;
    BGABadgeRadioButton mBottomNavigation0;
    BGABadgeRadioButton mBottomNavigation1;
    @BindView(R.id.bottom_navigation_2)
    TextView mBottomNavigation2;
    BGABadgeRadioButton mBottomNavigation3;
    BGABadgeRadioButton mBottomNavigation4;

    FragmentIndex mFragmentIndex;
    FragmentFind mFragmentFind;
    FragmentShoppingCart mFragmentShoppingCart;
    FragmentPersonal mFragmentPersonal;

    PackageInfo packageInfo;
    int currentVersionCode;
    String currentVersionName;
    String appName;

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        // 删除保存的 Fragment 状态，保存的时候直接不保存
        outState.putParcelable("android:support:fragments", null);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // 删除保存的 Fragment 状态，恢复的时候清空之前的保存
        if (savedInstanceState != null) {
            savedInstanceState.putParcelable("android:support:fragments", null);
        }
        super.onCreate(savedInstanceState);
        // 避免从桌面启动程序后，会重新实例化入口类的 activity
        if ((getIntent().getFlags() & Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT) != 0) {
            Logger.i("TAG_LAUNCHER", "0");
            finish();
            return;
        }
        if (!this.isTaskRoot()) { //判断该Activity是不是任务空间的源Activity，“非”也就是说是被系统重新实例化出来
            //如果你就放在launcher Activity中话，这里可以直接return了
            Intent mainIntent = getIntent();
            String action = mainIntent.getAction();
            Logger.i("TAG_LAUNCHER", "10");
            if (mainIntent.hasCategory(Intent.CATEGORY_LAUNCHER) && Intent.ACTION_MAIN.equals(action)) {
                Logger.i("TAG_LAUNCHER", "11");
                finish();
                return;
            }
        }

        setContentView(R.layout.activity_main);

        initNavBar();
        initFragment(0);

        initVersionForceUpdate();
        initUserInfo();
    }

    /**
     * 已登录，刷新用户数据
     */
    private void initUserInfo() {
        if (!TextUtils.isEmpty(sharedPreferencesHelper.getSharedPreference("user", "").toString())) {
            String token = GetTokenUtils.getToken(mActivity, ApiStores.API_SERVER_USER_MYHEAD);
            addSubscription(apiStores().userMyHead(token), new ApiCallback<PersonalHeadModel>() {
                @Override
                public void onSuccess(PersonalHeadModel model) {
                    if (model.getCode() == 1) {
                        try {
                            JSONObject userModel = new JSONObject(sharedPreferencesHelper.getSharedPreference("user", "").toString());
                            userModel.put("us_head_pic", model.getData().getUs_head_pic());
                            userModel.put("us_nickname", model.getData().getUs_nickname());
                            userModel.put("us_level", model.getData().getLevel());
                            userModel.put("is_realname", model.getData().getIs_realname());
                            userModel.put("us_agent", model.getData().getUs_agent());
                            userModel.put("us_agent2", model.getData().getUs_agent2());
                            userModel.put("out", model.getData().getOut());
                            userModel.put("cash", model.getData().getCash());
                            userModel.put("grb_integral", model.getData().getGrb_integral());
                            userModel.put("grb_cash", model.getData().getGrb_cash());
                            userModel.put("coupon_num", model.getData().getCoupon_num());
                            sharedPreferencesHelper.putApply("user", userModel.toString());
                        } catch (JSONException e) {
                        }
                    }
                }

                @Override
                public void onFailure(String msg) {
                }

                @Override
                public void onFinish() {
                }
            });
        }
    }

    /**
     * APP 版本检测
     */
    private void initVersionForceUpdate() {
        try {
            packageInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
            currentVersionCode = packageInfo.versionCode;
            currentVersionName = packageInfo.versionName;
            appName = packageInfo.applicationInfo.loadLabel(getPackageManager()).toString();

            if (currentVersionName.indexOf("_", 1) > -1)
                currentVersionName = currentVersionName.substring(0, currentVersionName.indexOf("_", 1));
            VersionUpdateHelp.forceUpdate(this,
                    ApiStores.API_SERVER_URL + ApiStores.API_SERVER_APP_CONTROL,
                    currentVersionCode, currentVersionName, appName, true, needUpdate -> {
                        if (!needUpdate) {
                            showMainNoticeDialog();
                        }
                    });
        } catch (PackageManager.NameNotFoundException e) {
            showMainNoticeDialog();
        }
    }

    /**
     * 获取公告信息
     */
    private void showMainNoticeDialog() {
        String token = GetTokenUtils.getToken(this, ApiStores.API_SERVER_INDEX_NEWSLIST);
        addSubscription(apiStores().indexNewHome(token, 4, 1, 1), new ApiCallback<String>() {
            @Override
            public void onSuccess(String model) {
                try {
                    JSONObject result = new JSONObject(model);
                    if (result.optInt("code", 0) == 1) {
                        JSONObject data = result.optJSONObject("data");
                        if (null != data && !TextUtils.isEmpty(data.optString("id"))) {
                            MainNoticeDialog dialog = new MainNoticeDialog(MainActivity.this);
                            dialog.setCanceledOnTouchOutside(false);
                            dialog.show();
                            dialog.setOnActionClickListener(() -> {
                                MessageDetailsWebViewActivity.start(mActivity, data.optString("id"));
                            });
                            dialog.setDialogTitle(data.optString("title"));
                            dialog.setDialogMessage(data.optString("simple"));
                        }
                    }
                } catch (JSONException e) {
                }
            }

            @Override
            public void onFailure(String msg) {
            }

            @Override
            public void onFinish() {
            }
        });
    }

    // 语言切换事件：1、2、
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onReceiveEventBus(OnChangeLanguageEvent event) {
        if (event.refreshType == RefreshType.REFRESH_ACTIVITY_CLEAR) {
            // 1、清除模式：打开主界面，并清除之前所有的界面
            // 这种情况只在 MainActivity 里进行处理
            Intent intent = new Intent(this, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);

        } else if (event.refreshType == RefreshType.REFRESH_ACTIVITY_RECREATE) {
            // 2、刷新模式：发送事件，重启界面，进行刷新界面
            super.onReceiveEventBus(event);
        }
    }

    // 登录状态变化
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onReceiveEventBus(RefreshUserInfo event) {
        if (null != mFragmentShoppingCart) {
            mFragmentShoppingCart.autoRefresh(event.isLogin);
        }
        if (null != mFragmentPersonal) {
            mFragmentPersonal.initUserInfo(event.isLogin);
        }
    }

    // 推送消息
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onReceiveEventBus(RefreshPushMsg event) {
        initPushMsg(event.map);
    }

    private void initPushMsg(Map<String, Integer> map) {
        if (null == map || map.isEmpty()) {
            mBottomNavigation1.getBadgeViewHelper().hiddenBadge();
        } else {
            int sunMsg = 0;
            Set<Map.Entry<String, Integer>> entrySet = map.entrySet();
            for (Map.Entry entry : entrySet) {
                sunMsg += Integer.valueOf(entry.getValue().toString());
            }

            if (sunMsg > 0) {
                mBottomNavigation1.getBadgeViewHelper().showTextBadge(String.valueOf(sunMsg));
            } else {
                mBottomNavigation1.getBadgeViewHelper().hiddenBadge();
            }
        }

        if (null != mFragmentPersonal) {
            mFragmentPersonal.initPushMsg(map);
        }
    }

    private void initNavBar() {
        mBottomNavigationBar.setOnCheckedChangeListener(this);

        mBottomNavigation0 = findViewById(R.id.bottom_navigation_0);
        mBottomNavigation1 = findViewById(R.id.bottom_navigation_1);
        mBottomNavigation3 = findViewById(R.id.bottom_navigation_3);
        mBottomNavigation4 = findViewById(R.id.bottom_navigation_4);
        mBottomNavigation1.getBadgeViewHelper().setBadgeGravity(BGABadgeViewHelper.BadgeGravity.RightTop);
        mBottomNavigation1.getBadgeViewHelper().setBadgeVerticalMarginDp(2);
        mBottomNavigation1.getBadgeViewHelper().setBadgeHorizontalMarginDp(10);

        mBottomNavigationBar.getChildAt(0).performClick();
        RxView.setOnClickListeners(this, mBottomNavigation0, mBottomNavigation1, mBottomNavigation2,
                mBottomNavigation3, mBottomNavigation4);

        Map<String, Integer> extra = new Gson().fromJson(sharedPreferencesConfig.getSharedPreference(JExampleUtil.KEY_CUSTOM_EXTRA, "").toString(),
                new TypeToken<Map<String, Integer>>() {
                }.getType());
        initPushMsg(extra);
    }

    @Override
    public void onClick(Object o) {
        View v = (View) o;
        switch (v.getId()) {
            case R.id.bottom_navigation_1:
                //mBottomNavigation1.getBadgeViewHelper().hiddenBadge();
                break;
            case R.id.bottom_navigation_2:
                if (isLogin()) {
                    String username = sharedPreferencesHelper.getSharedPreference("username", "").toString();
                    String password = sharedPreferencesHelper.getSharedPreference("password", "").toString();
                    WebViewActivity.start(this, String.format("%s/?user=%s/?pwd=%s",
                            ApiStores.API_SERVER_URL_GAME, username,
                            HmacMd5.getHmacMd5(password.getBytes(), password.getBytes())), "");
                }
                break;
            default:
                break;
        }
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId) {
            case R.id.bottom_navigation_0:
                initFragment(0);
                break;
            case R.id.bottom_navigation_1:
                initFragment(1);
                break;
            case R.id.bottom_navigation_2:
                //initFragment(2);
                break;
            case R.id.bottom_navigation_3:
                initFragment(3);
                break;
            case R.id.bottom_navigation_4:
                initFragment(4);
                break;
        }
    }

    private void initFragment(int i) {
        FragmentManager mFragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
        hideFragment(fragmentTransaction);
        switch (i) {
            case 0:
                //StatusBarUtil.setStatusBarDarkIconMode(this, false);
                StatusBarUtil.setStatusBar(this, Color.TRANSPARENT, 0, false);
                if (mFragmentIndex == null) {
                    mFragmentIndex = FragmentIndex.newInstance();
                    fragmentTransaction.add(R.id.layout_content, mFragmentIndex, FragmentIndex.class.getSimpleName());
                } else {
                    fragmentTransaction.show(mFragmentIndex);
                }
                break;

            case 1:
                //StatusBarUtil.setStatusBarDarkIconMode(this, true);
                StatusBarUtil.setStatusBar(this, Color.TRANSPARENT, 0, true);
                if (mFragmentFind == null) {
                    mFragmentFind = FragmentFind.newInstance();
                    fragmentTransaction.add(R.id.layout_content, mFragmentFind, FragmentFind.class.getSimpleName());
                } else {
                    fragmentTransaction.show(mFragmentFind);
                }
                break;

            case 2:
                break;

            case 3:
                //StatusBarUtil.setStatusBarDarkIconMode(this, true);
                StatusBarUtil.setStatusBar(this, Color.TRANSPARENT, 0, true);
                if (mFragmentShoppingCart == null) {
                    mFragmentShoppingCart = FragmentShoppingCart.newInstance();
                    fragmentTransaction.add(R.id.layout_content, mFragmentShoppingCart, FragmentShoppingCart.class.getSimpleName());
                } else {
                    fragmentTransaction.show(mFragmentShoppingCart);
                    // 每次点击到购物车，自动刷新购物车
                    // 不需要刷新，不添加此代码；可在购物车界面手动下拉刷新
                    mFragmentShoppingCart.autoRefresh(isLogin());
                }
                break;

            case 4:
                //StatusBarUtil.setStatusBarDarkIconMode(this, false);
                StatusBarUtil.setStatusBar(this, Color.TRANSPARENT, 0, false);
                if (mFragmentPersonal == null) {
                    mFragmentPersonal = FragmentPersonal.newInstance();
                    fragmentTransaction.add(R.id.layout_content, mFragmentPersonal, FragmentPersonal.class.getSimpleName());
                } else {
                    fragmentTransaction.show(mFragmentPersonal);
                    mFragmentPersonal.initUserInfo(isLogin());
                }
                break;
            default:
                break;
        }
        fragmentTransaction.commit();
    }

    private void hideFragment(FragmentTransaction fragmentTransaction) {
        if (mFragmentIndex != null) {
            fragmentTransaction.hide(mFragmentIndex);
        }
        if (mFragmentFind != null) {
            fragmentTransaction.hide(mFragmentFind);
        }
        if (mFragmentShoppingCart != null) {
            fragmentTransaction.hide(mFragmentShoppingCart);
        }
        if (mFragmentPersonal != null) {
            fragmentTransaction.hide(mFragmentPersonal);
        }
    }

    boolean mIsExit;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (mIsExit) {
                onBackPressed();
            } else {
                toastShow(String.format(getString(R.string.main_exit_app_tip), getString(R.string.app_name)));
                mIsExit = true;
                getWindow().getDecorView().postDelayed(() -> {
                    mIsExit = false;
                }, 2000);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

}
