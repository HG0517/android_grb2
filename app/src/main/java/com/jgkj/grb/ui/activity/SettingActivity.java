package com.jgkj.grb.ui.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.finddreams.languagelib.MultiLanguageUtil;
import com.hyphenate.chat.ChatClient;
import com.hyphenate.helpdesk.callback.Callback;
import com.jgkj.grb.R;
import com.jgkj.grb.base.BaseActivity;
import com.jgkj.grb.eventbus.RefreshUserInfo;
import com.jgkj.grb.glide.GlideApp;
import com.jgkj.grb.onclick.RxView;
import com.jgkj.grb.retrofit.ApiStores;
import com.jgkj.grb.ui.dialog.SettingLanguageDialog;
import com.jgkj.grb.utils.CleanCacheDataUtils;
import com.jgkj.grb.utils.Logger;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * 设置
 */
public class SettingActivity extends BaseActivity {

    public static void start(Context context) {
        Intent intent = new Intent(context, SettingActivity.class);
        context.startActivity(intent);
    }

    @BindView(R.id.setting_personal)
    FrameLayout mSettingPersonal;
    @BindView(R.id.userhead)
    CircleImageView mUserhead;
    @BindView(R.id.username)
    TextView mUsername;

    @BindView(R.id.setting_accounts_security)
    FrameLayout mSettingAccountsSecurity;

    @BindView(R.id.setting_language)
    FrameLayout mSettingLanguage;
    @BindView(R.id.language_right)
    TextView mSettingLanguageRight;

    @BindView(R.id.setting_feed_back)
    FrameLayout mSettingFeedback;

    @BindView(R.id.setting_clear_cache)
    FrameLayout mSettingClearCache;
    @BindView(R.id.clear_cache_right)
    TextView mSettingClearCacheRight;

    @BindView(R.id.setting_about)
    FrameLayout mSettingAbout;
    @BindView(R.id.about_right)
    TextView mSettingAboutRight;

    @BindView(R.id.setting_logout)
    CardView mSettingLogOut;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        Toolbar toolbar = initToolBar("");
        toolbar.setTitle(R.string.activity_title_setting);

        RxView.setOnClickListeners(this, mSettingPersonal, mSettingAccountsSecurity,
                mSettingLanguage, mSettingFeedback, mSettingClearCache, mSettingAbout, mSettingLogOut);

        mSettingLanguageRight.setText(MultiLanguageUtil.getInstance().getLanguageName(this));
        mSettingClearCacheRight.setText(CleanCacheDataUtils.getAllCacheSize(this));
        try {
            PackageInfo packageInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
            int versionCode = packageInfo.versionCode;
            String versionName = packageInfo.versionName;
            String appName = packageInfo.applicationInfo.loadLabel(getPackageManager()).toString();

            /*if (versionName.indexOf("_", 1) > -1)
                versionName = versionName.substring(0, versionName.indexOf("_", 1));*/
            mSettingAboutRight.setText(String.format(getString(R.string.setting_about_version_text), versionName));

            inirUserInfo();
        } catch (PackageManager.NameNotFoundException e) {
        } catch (JSONException e) {
        }
    }

    private void inirUserInfo() throws JSONException {
        String userStr = sharedPreferencesHelper.getSharedPreference("user", "").toString();
        JSONObject user = new JSONObject(userStr);
        String us_head_pic = user.getString("us_head_pic");
        if (!TextUtils.isEmpty(us_head_pic)) {
            GlideApp.with(this)
                    .load(us_head_pic.startsWith("http:") || us_head_pic.startsWith("https:")
                            ? us_head_pic.replaceAll("\\\\", "/")
                            : ApiStores.API_SERVER_URL + us_head_pic.replaceAll("\\\\", "/"))
                    .centerCrop()
                    .into(mUserhead);
        }

        String us_nickname = user.getString("us_nickname");
        mUsername.setText(TextUtils.isEmpty(us_nickname) ? "" : us_nickname);
    }

    // 修改昵称
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onReceiveEventBus(RefreshUserInfo event) {
        if (event.isLogin) {
            try {
                inirUserInfo();
            } catch (JSONException e) {
            }
        }
    }

    @Override
    public void onClick(Object o) {
        View v = (View) o;
        switch (v.getId()) {
            case R.id.setting_personal:
                SettingPersonalActivity.startActivityForResult(mActivity);
                break;
            case R.id.setting_accounts_security:
                SettingAccountsSecurityActivity.start(mActivity);
                break;
            case R.id.setting_language:
                SettingLanguageDialog languageDialog = new SettingLanguageDialog(this);
                languageDialog.show();
                break;
            case R.id.setting_feed_back:
                break;
            case R.id.setting_clear_cache:
                CleanCacheDataUtils.clearAllCache(this);
                mSettingClearCacheRight.setText(CleanCacheDataUtils.getAllCacheSize(this));
                break;
            case R.id.setting_about:
                SettingAboutActivity.start(mActivity);
                break;
            case R.id.setting_logout:
                /*sharedPreferencesHelper.clearApply();
                EventBus.getDefault().post(new RefreshUserInfo(false));
                LoginActivity.start(mActivity);
                onBackPressed();*/
                logout();
                break;
            default:
                break;
        }
    }

    /**
     * 退出登录
     */
    private void logout() {
        ChatClient.getInstance().logout(true, new Callback() {
            @Override
            public void onSuccess() {
                Logger.i("TAG_logoutChatClient", "onSuccess");
                runOnUiThread(() -> {
                    sharedPreferencesHelper.clearApply();
                    EventBus.getDefault().post(new RefreshUserInfo(false));
                    LoginActivity.start(mActivity);
                    onBackPressed();
                });
            }

            @Override
            public void onError(int code, String error) {
                Logger.i("TAG_logoutChatClient", "onError：" + code + " , " + error);
            }

            @Override
            public void onProgress(int progress, String status) {
                Logger.i("TAG_logoutChatClient", "onProgress：" + progress + " , " + status);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == 10011) {
                if (null != data && data.hasExtra("path")) {
                    String path = data.getStringExtra("path");
                    GlideApp.with(this)
                            .load(path)
                            .centerCrop()
                            .into(mUserhead);
                }
            }
        }
    }
}
