package com.jgkj.grb.base;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.ColorInt;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.annotation.UiThread;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.hyphenate.chat.ChatClient;
import com.hyphenate.helpdesk.Error;
import com.hyphenate.helpdesk.callback.Callback;
import com.hyphenate.helpdesk.easeui.util.IntentBuilder;
import com.hyphenate.helpdesk.model.ContentFactory;
import com.jgkj.grb.R;
import com.jgkj.grb.eventbus.RefreshPushMsg;
import com.jgkj.grb.onclick.RxView;
import com.jgkj.grb.push.JExampleUtil;
import com.jgkj.grb.retrofit.ApiClient;
import com.jgkj.grb.retrofit.ApiStores;
import com.jgkj.grb.ui.activity.LoginActivity;
import com.jgkj.grb.ui.activity.RealnameFirstActivity;
import com.jgkj.grb.ui.activity.WebViewActivity;
import com.jgkj.grb.ui.dialog.UnrealNameDialog;
import com.jgkj.grb.utils.Logger;
import com.jgkj.grb.utils.ToastHelper;
import com.jgkj.utils.token.utils.des.HmacMd5;
import com.jgkj.utils.token.utils.sp.SharedPreferencesHelper;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

/**
 *
 */
public abstract class BaseFragment extends Fragment implements RxView.Action1 {
    public Activity mActivity;
    public SharedPreferencesHelper sharedPreferencesHelper;
    public SharedPreferencesHelper sharedPreferencesConfig;

    private CompositeDisposable mCompositeDisposable;
    private View mRootView;
    private Unbinder mUnbinder;
    // 界面是否加载完成
    private boolean isPrepared;
    // 是否已经懒加载
    private boolean isLazyLoaded;

    public View getRootView() {
        return mRootView;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (null != mRootView) {
            mUnbinder = ButterKnife.bind(this, mRootView);
            sharedPreferencesHelper = new SharedPreferencesHelper(getContext());
            sharedPreferencesConfig = new SharedPreferencesHelper(getContext(), Constants.SP_KEY_CONFIG);
            isPrepared = true;
            return mRootView;
        }

        mRootView = inflater.inflate(getLayoutResId(), container, false);
        mUnbinder = ButterKnife.bind(this, mRootView);
        sharedPreferencesHelper = new SharedPreferencesHelper(getContext());
        sharedPreferencesConfig = new SharedPreferencesHelper(getContext(), Constants.SP_KEY_CONFIG);
        mActivity = getActivity();
        init(savedInstanceState);
        isPrepared = true;
        return mRootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mActivity = getActivity();
        isPrepared = true;
        lazyLoad();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        lazyLoad();
    }

    private void lazyLoad() {
        if (getUserVisibleHint() && isPrepared && !isLazyLoaded) {
            onLazyLoad();
            isLazyLoaded = true;
        }
    }

    public abstract int getLayoutResId();

    public abstract void init(Bundle savedInstanceState);

    @UiThread
    protected abstract void onLazyLoad();

    public void setFitsSystemWindows(boolean fitSystemWindows) {
        getRootView().findViewById(R.id.topPanel).setFitsSystemWindows(fitSystemWindows);
    }

    public Toolbar initToolBar(View view, String title) {
        Toolbar toolbar = view.findViewById(R.id.toolbar);
        if (toolbar != null) {
            ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
            setHasOptionsMenu(true);
            TextView toolbar_title = toolbar.findViewById(R.id.toolbar_title);
            toolbar_title.setText(title);
        }
        ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(false);
            actionBar.setDisplayShowTitleEnabled(false);
        }
        return toolbar;
    }

    public Toolbar initToolBar(View view, String title, String menu, View.OnClickListener menuClickListener) {
        Toolbar toolbar = view.findViewById(R.id.toolbar);
        TextView toolbar_title = toolbar.findViewById(R.id.toolbar_title);
        toolbar_title.setText(title);
        TextView toolbar_menu = toolbar.findViewById(R.id.toolbar_menu);
        toolbar_menu.setText(menu);
        if (!TextUtils.isEmpty(menu)) {
            toolbar_menu.setVisibility(View.VISIBLE);
            if (null != menuClickListener)
                toolbar_menu.setOnClickListener(menuClickListener);
        }
        return toolbar;
    }

    public Toolbar setBackgroundColor(Toolbar toolbar, @ColorInt int color) {
        getRootView().findViewById(R.id.topPanel).setBackgroundColor(color);
        return toolbar;
    }

    public Toolbar setTitleTextColor(Toolbar toolbar, @ColorInt int color) {
        ((TextView) toolbar.findViewById(R.id.toolbar_title)).setTextColor(color);
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

    public void toastShow(int resId) {
        toastShow(getResources().getString(resId));
    }

    public void toastShow(String resId) {
        ToastHelper.showToast(mActivity, resId, Toast.LENGTH_LONG);
    }


    @Override
    public void onDestroyView() {
        onUnsubscribe();
        mUnbinder.unbind();
        super.onDestroyView();
    }

    @Override
    public void onClick(Object o) {

    }

    /**
     * 隐藏输入法
     */
    public void hideInputSoft() {
        View view = mActivity.getCurrentFocus();
        if (view != null) {
            if (view instanceof EditText) {
                view.clearFocus();
            }
            InputMethodManager inputMethodManager = (InputMethodManager) mActivity.getSystemService(Context.INPUT_METHOD_SERVICE);
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
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, mActivity.getResources().getDisplayMetrics());
    }

    /**
     * 得到屏幕宽度
     *
     * @return 宽度
     */
    public int getScreenWidth() {
        DisplayMetrics dm = new DisplayMetrics();
        mActivity.getWindowManager().getDefaultDisplay().getMetrics(dm);
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
        mActivity.getWindowManager().getDefaultDisplay().getMetrics(dm);
        int screenHeight = dm.heightPixels;
        return screenHeight;
    }

    // 得到剪贴板管理器
    public void copyContent(String content) {
        ClipboardManager cmb = (ClipboardManager) mActivity.getApplicationContext().getSystemService(Context.CLIPBOARD_SERVICE);
        cmb.setText(content.trim());
    }

    public ApiStores apiStores() {
        return ApiClient.retrofit().create(ApiStores.class);
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
        dialog.showDialog(getChildFragmentManager());
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
