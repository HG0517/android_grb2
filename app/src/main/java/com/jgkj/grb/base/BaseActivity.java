package com.jgkj.grb.base;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.ColorInt;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.GestureDetector;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.finddreams.languagelib.MultiLanguageUtil;
import com.finddreams.languagelib.OnChangeLanguageEvent;
import com.finddreams.languagelib.OnRecreateEvent;
import com.finddreams.languagelib.RefreshType;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.hyphenate.chat.ChatClient;
import com.hyphenate.helpdesk.Error;
import com.hyphenate.helpdesk.callback.Callback;
import com.hyphenate.helpdesk.easeui.util.IntentBuilder;
import com.hyphenate.helpdesk.model.ContentFactory;
import com.jgkj.grb.BuildConfig;
import com.jgkj.grb.R;
import com.jgkj.grb.eventbus.RefreshPushMsg;
import com.jgkj.grb.onclick.RxView;
import com.jgkj.grb.push.JExampleUtil;
import com.jgkj.grb.retrofit.ApiCallback;
import com.jgkj.grb.retrofit.ApiClient;
import com.jgkj.grb.retrofit.ApiStores;
import com.jgkj.grb.ui.activity.LoginActivity;
import com.jgkj.grb.ui.activity.RealnameFirstActivity;
import com.jgkj.grb.ui.activity.WebViewActivity;
import com.jgkj.grb.ui.dialog.UnrealNameDialog;
import com.jgkj.grb.utils.Logger;
import com.jgkj.grb.utils.ToastHelper;
import com.jgkj.utils.token.GetTokenUtils;
import com.jgkj.utils.token.utils.des.HmacMd5;
import com.jgkj.utils.token.utils.sp.SharedPreferencesHelper;
import com.jgkj.utils.token.utils.statusbar.StatusBarUtil;
import com.xgr.alipay.alipay.AliPay;
import com.xgr.alipay.alipay.AlipayInfoImpli;
import com.xgr.easypay.EasyPay;
import com.xgr.easypay.callback.IPayCallback;
import com.xgr.unionpay.unionpay.Mode;
import com.xgr.unionpay.unionpay.UnionPay;
import com.xgr.unionpay.unionpay.UnionPayInfoImpli;
import com.xgr.wechatpay.wxpay.WXPay;
import com.xgr.wechatpay.wxpay.WXPayInfoImpli;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import cn.testin.analysis.bug.BugOutApi;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;

/**
 *
 */
public abstract class BaseActivity extends AppCompatActivity implements RxView.Action1 {
    public Activity mActivity;
    public SharedPreferencesHelper sharedPreferencesHelper;
    public SharedPreferencesHelper sharedPreferencesConfig;

    private Unbinder mUnbinder;
    private CompositeDisposable mCompositeDisposable;
    private List<Call> calls;

    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        super.setContentView(layoutResID);
        init();
    }

    @Override
    public void setContentView(View view) {
        super.setContentView(view);
        init();
    }

    @Override
    public void setContentView(View view, ViewGroup.LayoutParams params) {
        super.setContentView(view, params);
        init();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(MultiLanguageUtil.getInstance().attachBaseContext(newBase));
    }

    private void init() {
        // 注册监听：语言切换事件
        EventBus.getDefault().register(this);

        mActivity = this;
        mUnbinder = ButterKnife.bind(this);
        getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN
                        | WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        sharedPreferencesHelper = new SharedPreferencesHelper(this);
        sharedPreferencesConfig = new SharedPreferencesHelper(this, Constants.SP_KEY_CONFIG);

        //StatusBarUtil.setStatusBarDarkIconMode(this, true);
        StatusBarUtil.setStatusBar(this, Color.TRANSPARENT, 0, true);
    }

    // 语言切换事件：1、2、
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onReceiveEventBus(OnChangeLanguageEvent event) {
        Logger.i("TAG_", "ChangeLanguage : " + event.languageType + " , refreshType : " + event.refreshType);
        if (event.refreshType == RefreshType.REFRESH_ACTIVITY_CLEAR) {
            // 1、清除模式：打开主界面，并清除之前所有的界面
            // 这种情况只在 MainActivity 里进行处理
            // Intent intent = new Intent(this, MainActivity.class);
            // intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            // startActivity(intent);

        } else if (event.refreshType == RefreshType.REFRESH_ACTIVITY_RECREATE) {
            // 2、刷新模式：发送事件，重启界面，进行刷新界面
            EventBus.getDefault().post(new OnRecreateEvent(event.refreshType));
        }
    }

    // 2、重启，刷新界面事件，在所有需要刷新的界面添加注册
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onReceiveEventBus(OnRecreateEvent event) {
        if (event.refreshType == RefreshType.REFRESH_ACTIVITY_RECREATE) {
            recreate();
        }
    }

    CountDownTimer mCountDownTimer;

    protected void everyGetRegion(View clickView, TextView showView, String normalShow, String usTel) {
        showProgressDialog();
        String token = GetTokenUtils.getToken(this, ApiStores.API_SERVER_EVERY_SEND);
        addSubscription(apiStores().everySend(token, usTel), new ApiCallback<String>() {
            @Override
            public void onSuccess(String model) {
                try {
                    JSONObject result = new JSONObject(model);
                    if (result.getInt("code") == 1) {
                        toastShow(result.getString("msg"));
                        countdown(clickView, showView, normalShow);
                    } else {
                        toastShow(result.getString("msg"));
                    }
                } catch (JSONException e) {
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

    protected void countdown(View clickView, TextView showView, String normalShow) {
        clickView.setEnabled(false);
        if (null != mCountDownTimer)
            mCountDownTimer.cancel();

        mCountDownTimer = new CountDownTimer(60 * 1000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                showView.setText(String.format(Locale.getDefault(), "%dS", millisUntilFinished / 1000));
            }

            @Override
            public void onFinish() {
                clickView.setEnabled(true);
                showView.setText(normalShow);
            }
        };
        mCountDownTimer.start();
    }

    @Override
    protected void onResume() {
        BugOutApi.onResume(this);
        super.onResume();
    }

    @Override
    protected void onPause() {
        hideInputSoft();
        BugOutApi.onPause(this);
        super.onPause();
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        BugOutApi.onDispatchTouchEvent(this, ev);
        return super.dispatchTouchEvent(ev);
    }

    @Override
    protected void onDestroy() {
        // 取消监听：语言切换事件
        EventBus.getDefault().unregister(this);
        callCancel();
        onUnsubscribe();
        if (null != mUnbinder)
            mUnbinder.unbind();
        if (null != mCountDownTimer)
            mCountDownTimer.cancel();
        super.onDestroy();
    }

    @Override
    public void onClick(Object o) {
    }

    /**
     * 隐藏输入法
     */
    public void hideInputSoft() {
        View view = getCurrentFocus();
        if (view != null) {
            if (view instanceof EditText) {
                view.clearFocus();
            }
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            if (inputMethodManager != null) {
                inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
            }
        }
    }

    /**
     * 获取状态栏的高度
     */
    public int getStatusBarHeight() {
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        try {
            return getResources().getDimensionPixelSize(resourceId);
        } catch (Resources.NotFoundException e) {
            return 0;
        }
    }

    public int dp2px(int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, getResources().getDisplayMetrics());
    }

    /**
     * 得到屏幕宽度
     *
     * @return 宽度
     */
    public int getScreenWidth() {
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int screenWidth = dm.widthPixels;
        return screenWidth;
    }

    /**
     * 得到屏幕高度
     *
     * @return 高度
     */
    public int getScreenHeight() {
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int screenHeight = dm.heightPixels;
        return screenHeight;
    }

    // 得到剪贴板管理器
    public void copyContent(String content) {
        ClipboardManager cmb = (ClipboardManager) getApplicationContext().getSystemService(Context.CLIPBOARD_SERVICE);
        cmb.setText(content.trim());
    }

    public ApiStores apiStores() {
        return ApiClient.retrofit().create(ApiStores.class);
    }

    public void addCalls(Call call) {
        if (calls == null) {
            calls = new ArrayList<>();
        }
        calls.add(call);
    }

    private void callCancel() {
        if (calls != null && calls.size() > 0) {
            for (Call call : calls) {
                if (!call.isCanceled())
                    call.cancel();
            }
            calls.clear();
        }
    }


    public <T> void addSubscription(Observable<T> observable, DisposableObserver<T> observer) {
        if (mCompositeDisposable == null) {
            mCompositeDisposable = new CompositeDisposable();
        }
        mCompositeDisposable.add(observer);

        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

    public void addSubscription(Disposable disposable) {
        if (mCompositeDisposable == null) {
            mCompositeDisposable = new CompositeDisposable();
        }
        mCompositeDisposable.add(disposable);
    }

    public void onUnsubscribe() {
        Logger.i("TAG", "onUnSubscribe");
        //取消注册，以避免内存泄露
        if (mCompositeDisposable != null)
            mCompositeDisposable.dispose();
    }

    public Toolbar initToolBar(String title) {
        Toolbar toolbar = findViewById(R.id.toolbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            TextView toolbaTitle = toolbar.findViewById(R.id.toolbar_title);
            toolbaTitle.setText(title);
            initToolbarTouchListener(toolbar);
        }
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowTitleEnabled(false);
        }
        return toolbar;
    }

    public Toolbar initToolBar(String title, String menu, View.OnClickListener menuClickListener) {
        Toolbar toolbar = findViewById(R.id.toolbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            TextView toolbaTitle = toolbar.findViewById(R.id.toolbar_title);
            toolbaTitle.setText(title);
            TextView toolbaMenu = toolbar.findViewById(R.id.toolbar_menu);
            toolbaMenu.setText(menu);
            if (!TextUtils.isEmpty(menu)) {
                toolbaMenu.setVisibility(View.VISIBLE);
                if (null != menuClickListener)
                    toolbaMenu.setOnClickListener(menuClickListener);
            }
            initToolbarTouchListener(toolbar);
        }
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowTitleEnabled(false);
        }
        return toolbar;
    }

    public Toolbar initToolBar(String title, int menuRes, View.OnClickListener menuClickListener) {
        Toolbar toolbar = findViewById(R.id.toolbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            TextView toolbaTitle = toolbar.findViewById(R.id.toolbar_title);
            toolbaTitle.setText(title);
            TextView toolbaMenu = toolbar.findViewById(R.id.toolbar_menu);
            toolbaMenu.setText("");
            if (menuRes > 0) {
                toolbaMenu.setVisibility(View.VISIBLE);
                Drawable drawable = getResources().getDrawable(menuRes);
                drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
                toolbaMenu.setCompoundDrawables(drawable, null, null, null);
                if (null != menuClickListener)
                    toolbaMenu.setOnClickListener(menuClickListener);
            }
            initToolbarTouchListener(toolbar);
        }
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowTitleEnabled(false);
        }
        return toolbar;
    }

    public Toolbar initToolBarIcon(Toolbar toolbar, int leftId, int topId, int rightId, int bottomId, View.OnClickListener onClick) {
        if (null != toolbar) {
            TextView toolbaTitle = toolbar.findViewById(R.id.toolbar_title);
            Drawable left = leftId > 0 ? getResources().getDrawable(leftId) : toolbaTitle.getCompoundDrawables()[0];
            if (null != left)
                left.setBounds(0, 0, left.getIntrinsicWidth(), left.getIntrinsicHeight());
            Drawable top = topId > 0 ? getResources().getDrawable(topId) : toolbaTitle.getCompoundDrawables()[1];
            if (null != top)
                top.setBounds(0, 0, top.getIntrinsicWidth(), top.getIntrinsicHeight());
            Drawable right = rightId > 0 ? getResources().getDrawable(rightId) : toolbaTitle.getCompoundDrawables()[2];
            if (null != right)
                right.setBounds(0, 0, right.getIntrinsicWidth(), right.getIntrinsicHeight());
            Drawable bottom = bottomId > 0 ? getResources().getDrawable(bottomId) : toolbaTitle.getCompoundDrawables()[3];
            if (null != bottom)
                bottom.setBounds(0, 0, bottom.getIntrinsicWidth(), bottom.getIntrinsicHeight());
            toolbaTitle.setCompoundDrawables(left, top, right, bottom);

            toolbaTitle.setOnClickListener(v -> {
                if (null != onClick) {
                    onClick.onClick(v);
                }
            });
        }
        return toolbar;
    }

    public Toolbar initToolBarAsHome(String title) {
        Toolbar toolbar = findViewById(R.id.toolbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            TextView toolbaTitle = toolbar.findViewById(R.id.toolbar_title);
            toolbaTitle.setText(title);
            initToolbarTouchListener(toolbar);
        }
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(false);
            actionBar.setDisplayShowTitleEnabled(false);
        }
        return toolbar;
    }

    public Toolbar setTitleTextColor(Toolbar toolbar, @ColorInt int color) {
        ((TextView) toolbar.findViewById(R.id.toolbar_title)).setTextColor(color);
        return toolbar;
    }

    public Toolbar setBackgroundColor(Toolbar toolbar, @ColorInt int color) {
        findViewById(R.id.topPanel).setBackgroundColor(color);
        return toolbar;
    }

    public Toolbar setMenuText(Toolbar toolbar, @StringRes int resid) {
        return setMenuText(toolbar, getString(resid));
    }

    public Toolbar setMenuText(Toolbar toolbar, CharSequence text) {
        ((TextView) toolbar.findViewById(R.id.toolbar_menu)).setText(text);
        return toolbar;
    }

    public Toolbar setMenuTextColor(Toolbar toolbar, @ColorInt int color) {
        ((TextView) toolbar.findViewById(R.id.toolbar_menu)).setTextColor(color);
        return toolbar;
    }

    @SuppressLint({"ClickableViewAccessibility"})
    private void initToolbarTouchListener(Toolbar toolbar) {
        GestureDetector gestureDetector = new GestureDetector(mActivity, new GestureDetector.SimpleOnGestureListener() {
            @Override
            public boolean onSingleTapConfirmed(MotionEvent e) {
                // toastShow("单击");
                return super.onSingleTapConfirmed(e);
            }

            @Override
            public boolean onDoubleTap(MotionEvent e) {
                // toastShow("双击");
                return super.onDoubleTap(e);
            }
        });
        toolbar.setOnTouchListener((v, event) -> gestureDetector.onTouchEvent(event));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                super.onBackPressed();//返回
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void toastShow(int resId) {
        toastShow(getResources().getString(resId));
    }

    public void toastShow(String message) {
        runOnUiThread(() -> ToastHelper.showToast(mActivity, message, Toast.LENGTH_LONG));
    }

    public ProgressDialog progressDialog;

    public ProgressDialog showProgressDialog() {
        if (null == progressDialog) {
            progressDialog = new ProgressDialog(mActivity);
        }
        dismissProgressDialog();
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setCancelable(true);
        progressDialog.setMessage("加载中...");
        progressDialog.show();
        return progressDialog;
    }

    public ProgressDialog showProgressDialog(CharSequence message) {
        if (null == progressDialog) {
            progressDialog = new ProgressDialog(mActivity);
        }
        dismissProgressDialog();
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setCancelable(true);
        progressDialog.setMessage(message);
        progressDialog.show();
        return progressDialog;
    }

    public void dismissProgressDialog() {
        if (progressDialog != null && progressDialog.isShowing()) {
            // progressDialog.hide(); // 会导致android.view.WindowLeaked
            progressDialog.cancel();
        }
    }

    protected boolean isLogin() {
        if (TextUtils.isEmpty(sharedPreferencesHelper.getSharedPreference("user", "").toString())) {
            // 未登录
            LoginActivity.start(mActivity);
            return false;

        } else {
            // 已登录
            return true;
        }
    }

    protected int isRealname() {
        try {
            // is_realname  实名情况：0:未认证  1认证中 2已认证
            String userStr = sharedPreferencesHelper.getSharedPreference("user", "").toString();
            JSONObject user = new JSONObject(userStr);
            return user.optInt("is_realname", 0);
        } catch (JSONException e) {
            return 0;
        }
    }

    protected void showUnrealNameDialog() {
        showUnrealNameDialog("");
    }

    protected void showUnrealNameDialog(String msg) {
        UnrealNameDialog dialog = UnrealNameDialog.newInstance(msg);
        dialog.setCancelable(false);
        dialog.showDialog(getSupportFragmentManager());
        dialog.setOnActionClickListener(new UnrealNameDialog.OnActionClickListener() {
            @Override
            public void onConfirm() {
                RealnameFirstActivity.startActivityForResult(mActivity);
            }

            @Override
            public void onCancel() {
                //onBackPressed();
            }
        });
    }

    /**
     * 注册到环信客服
     */
    protected void registerChatClient(String username, String password) {
        if (ApiStores.KEFU_CHANNEL_DEBUG || TextUtils.isEmpty(username))
            return;
        ChatClient.getInstance().register(username, password, new Callback() {
            @Override
            public void onSuccess() {
                Logger.i("TAG_registerChatClient", "onSuccess");
                loginChatClient(username, password);
            }

            @Override
            public void onError(int code, String error) {
                Logger.i("TAG_registerChatClient", "onError：" + code + " , " + error);
                switch (code) {
                    case Error.NETWORK_ERROR: // 网络不可用
                        break;
                    case Error.USER_ALREADY_EXIST: // 用户已存在
                        loginChatClient(username, password);
                        break;
                    case Error.USER_ILLEGAL_ARGUMENT: // 用户名非法
                        break;
                    case Error.USER_AUTHENTICATION_FAILED: // 无开放注册权限（后台管理界面设置[开放|授权]）
                        break;
                    default:
                        break;
                }
            }

            @Override
            public void onProgress(int progress, String status) {
                Logger.i("TAG_registerChatClient", "onProgress：" + progress + " , " + status);
            }
        });
    }

    /**
     * 登录到环信客服
     */
    protected void loginChatClient(String username, String password) {
        ChatClient.getInstance().login(username, password, new Callback() {
            @Override
            public void onSuccess() {
                Logger.i("TAG_loginChatClient", "onSuccess");
            }

            @Override
            public void onError(int code, String error) {
                Logger.i("TAG_loginChatClient", "onError：" + code + " , " + error);
            }

            @Override
            public void onProgress(int progress, String status) {
                Logger.i("TAG_loginChatClient", "onProgress：" + progress + " , " + status);
            }
        });
    }

    /**
     * 环信客服
     */
    protected void huanXinKeFu() {
        if (ApiStores.KEFU_CHANNEL_DEBUG) {
            WebViewActivity.start(mActivity, ApiStores.API_SERVER_SERVICE, "在线客服");
            return;
        }
        if (ChatClient.getInstance().isLoggedInBefore()) {
            String userStr = sharedPreferencesHelper.getSharedPreference("user", "").toString();
            String us_tel = "";
            String us_nickname = "";
            try {
                JSONObject user = new JSONObject(userStr);
                us_tel = user.optString("us_tel", "");
                us_nickname = user.optString("us_nickname", "");
            } catch (JSONException e) {
            }
            // 已经登录，可以直接进入会话界面
            Intent intent = new IntentBuilder(mActivity)
                    // 界面标题，不设置显示IM服务号
                    .setTitleName("在线客服")
                    // 客服关联的IM服务号
                    .setServiceIMNumber(Constants.KEFU_CHANNEL_IM_ID)
                    // 访客属性
                    .setVisitorInfo(ContentFactory.createVisitorInfo(null)
                            .nickName(us_tel)
                            .name(us_nickname)
                            .phone(us_tel)
                    )
                    .build();
            startActivity(intent);
        } else {
            // 未登录，需要登录后，再进入会话界面
            if (isLogin()) {
                try {
                    JSONObject userModel = new JSONObject(sharedPreferencesHelper.getSharedPreference("user", "").toString());
                    String password = HmacMd5.getMd5(Constants.KEFU_CHANNEL_USER_PASSWORD);
                    registerChatClient(userModel.optString("id"/*"us_account"*/, ""), password);
                } catch (JSONException e) {
                }
            }
        }
    }

    /**
     * 微信支付
     */
    protected void wxpay(JSONObject data, IPayCallback payCallback) {
        // 实例化微信支付策略
        WXPay wxPay = WXPay.getInstance();
        // 构造微信订单实体。一般都是由服务端直接返回。
        WXPayInfoImpli wxPayInfoImpli = new WXPayInfoImpli();
        wxPayInfoImpli.setAppid(getString(R.string.weixin_appid));
        wxPayInfoImpli.setPartnerid(data.optString("partnerid", ""));
        wxPayInfoImpli.setPrepayId(data.optString("prepayid", ""));
        wxPayInfoImpli.setPackageValue(data.optString("package", ""));
        wxPayInfoImpli.setNonceStr(data.optString("noncestr", ""));
        wxPayInfoImpli.setTimestamp(data.optString("timestamp", ""));
        wxPayInfoImpli.setSign(data.optString("sign", ""));
        // 策略场景类调起支付方法开始支付，以及接收回调。
        EasyPay.pay(wxPay, this, wxPayInfoImpli, payCallback);
    }

    /**
     * 支付宝支付
     */
    protected void alipay(String orderInfo, IPayCallback payCallback) {
        // 实例化支付宝支付策略
        AliPay aliPay = new AliPay();
        // 构造支付宝订单实体。一般都是由服务端直接返回。
        AlipayInfoImpli alipayInfoImpli = new AlipayInfoImpli();
        alipayInfoImpli.setOrderInfo(orderInfo);
        // 策略场景类调起支付方法开始支付，以及接收回调。
        EasyPay.pay(aliPay, this, alipayInfoImpli, payCallback);
    }

    /**
     * 银联支付《云闪付》
     */
    protected void unionpay(JSONObject data, IPayCallback payCallback) {
        // 实例化银联支付策略
        UnionPay unionPay = new UnionPay();
        // 构造银联订单实体。一般都是由服务端直接返回。测试时可以用Mode.TEST,发布时用Mode.RELEASE。
        UnionPayInfoImpli unionPayInfoImpli = new UnionPayInfoImpli();
        unionPayInfoImpli.setTn(data.optString("tn", ""));
        unionPayInfoImpli.setMode(BuildConfig.DEBUG ? Mode.TEST : Mode.RELEASE);
        // 策略场景类调起支付方法开始支付，以及接收回调。
        EasyPay.pay(unionPay, this, unionPayInfoImpli, payCallback);
    }

    /**
     * 推送消息：已读，并更新
     * key：1：系统消息，2：商城活动，3：代理消息，4，资讯消息
     */
    protected void updatePushMsg(int key) {
        Gson gson = new Gson();
        Map<String, Integer> extra = gson.fromJson(sharedPreferencesConfig.getSharedPreference(JExampleUtil.KEY_CUSTOM_EXTRA, "").toString(),
                new TypeToken<Map<String, Integer>>() {
                }.getType());
        if (null != extra && !extra.isEmpty()) {
            extra.remove(String.valueOf(key));
        }

        EventBus.getDefault().post(new RefreshPushMsg(extra));
        sharedPreferencesConfig.putApply(JExampleUtil.KEY_CUSTOM_EXTRA, gson.toJson(extra));
    }

}
