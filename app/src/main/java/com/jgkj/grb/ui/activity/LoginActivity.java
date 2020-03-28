package com.jgkj.grb.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.finddreams.languagelib.RefreshType;
import com.google.gson.Gson;
import com.jgkj.grb.R;
import com.jgkj.grb.base.BaseActivity;
import com.jgkj.grb.base.Constants;
import com.jgkj.grb.eventbus.RefreshUserInfo;
import com.jgkj.grb.onclick.RxView;
import com.jgkj.grb.push.JExampleUtil;
import com.jgkj.grb.retrofit.ApiCallback;
import com.jgkj.grb.ui.dialog.SettingLanguageDialog;
import com.jgkj.grb.utils.Logger;
import com.jgkj.utils.token.utils.des.HmacMd5;
import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareConfig;
import com.umeng.socialize.bean.SHARE_MEDIA;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import cn.jpush.android.api.JPushInterface;

/**
 * 登录
 */
public class LoginActivity extends BaseActivity {

    public static void start(Context context) {
        Intent intent = new Intent(context, LoginActivity.class);
        context.startActivity(intent);
    }

    @BindView(R.id.login_register_title)
    FrameLayout mLoginRegisterAction;
    @BindView(R.id.login_phone_number_et)
    EditText mLoginPhoneNumberEt;
    @BindView(R.id.login_verification_code_right)
    FrameLayout mLoginVerificationCodeAction;
    @BindView(R.id.login_verification_code_tv)
    TextView mLoginVerificationCodeTv;
    @BindView(R.id.login_verification_code_et)
    EditText mLoginVerificationCodeEt;
    @BindView(R.id.login_password_et)
    EditText mLoginPasswordEt;
    @BindView(R.id.login_submission)
    CardView mLoginSubmission;
    @BindView(R.id.login_password_forget)
    FrameLayout mLoginPasswordForget;
    @BindView(R.id.login_wechat)
    FrameLayout mLoginWechat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Toolbar toolbar = initToolBar("");
        toolbar.setNavigationIcon(R.mipmap.back_navi_icon_black_fork);

        RxView.setOnClickListeners(this, mLoginRegisterAction, mLoginVerificationCodeAction, mLoginSubmission, mLoginPasswordForget, mLoginWechat);

        mLoginSubmission.setAlpha(0.5f);
        mLoginSubmission.setClickable(false);
        mLoginPhoneNumberEt.addTextChangedListener(textWatcher);
        mLoginVerificationCodeEt.addTextChangedListener(textWatcher);
        mLoginPasswordEt.addTextChangedListener(textWatcher);

        String registrationID = sharedPreferencesConfig.getSharedPreference(JExampleUtil.KEY_REGISTRATION_ID, JPushInterface.getRegistrationID(this)).toString();
        Logger.i("TAG_LoginActivity", String.format("RegistrationID = %s", registrationID));
    }

    @Override
    public void onClick(Object o) {
        View v = (View) o;
        switch (v.getId()) {
            case R.id.login_register_title:
                RegisterActivity.start(mActivity);
                break;
            case R.id.login_verification_code_right:
                if (TextUtils.isEmpty(mLoginPhoneNumberEt.getText().toString().trim())) {
                    toastShow(R.string.password_phone_number_tip);
                    return;
                }
                everyGetRegion(mLoginVerificationCodeAction, mLoginVerificationCodeTv, getString(R.string.password_verification_code_action), mLoginPhoneNumberEt.getText().toString().trim());
                break;
            case R.id.login_submission:
                String username = mLoginPhoneNumberEt.getText().toString().trim();
                String code = mLoginVerificationCodeEt.getText().toString().trim();
                String password = mLoginPasswordEt.getText().toString().trim();
                if (TextUtils.isEmpty(username)) {
                    toastShow(R.string.password_phone_number_tip);
                    return;
                }
                /*if (TextUtils.isEmpty(code)) {
                    toastShow(R.string.password_verification_code_tip);
                    return;
                }*/
                if (TextUtils.isEmpty(username)) {
                    toastShow(R.string.password_new_tip);
                    return;
                }
                login(username, password);
                break;
            case R.id.login_password_forget:
                PasswordChangeActivity.startActivityForResult(mActivity, 1);
                break;
            case R.id.login_wechat:
                toastShow(R.string.not_yet_open_tip);
                //loginWeChat();
                break;
            default:
                break;
        }
    }

    private void login(String username, String password) {
        showProgressDialog();
        String hmacMd5 = HmacMd5.getMd5(password);
        Map<String, Object> body = new HashMap<>();
        body.put("us_tel", username);
        body.put("password", hmacMd5);
        addSubscription(apiStores().login(body), new ApiCallback<String>() {
            @Override
            public void onSuccess(String model) {
                try {
                    JSONObject userModel = new JSONObject(model);
                    if (userModel.getInt("code") == 1) {
                        sharedPreferencesHelper.putApply("username", username);
                        sharedPreferencesHelper.putApply("password", hmacMd5);
                        sharedPreferencesHelper.putApply("user", userModel.getString("data"));

                        String uId = userModel.optJSONObject("data").optString("id"/*"us_account"*/, "");
                        JPushInterface.setAlias(mActivity, 0, uId);
                        registerChatClient(uId, HmacMd5.getMd5(Constants.KEFU_CHANNEL_USER_PASSWORD));

                        EventBus.getDefault().post(new RefreshUserInfo(true));
                        onBackPressed();
                    } else {
                        toastShow(userModel.getString("msg"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_setting_language: // 语言设置
                SettingLanguageDialog languageDialog = new SettingLanguageDialog(this);
                languageDialog.setRefreshType(RefreshType.REFRESH_ACTIVITY_CLEAR);
                languageDialog.show();
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_login, menu);
        return true;
    }

    TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
        }

        @Override
        public void afterTextChanged(Editable s) {
            if (TextUtils.isEmpty(mLoginPhoneNumberEt.getText().toString().trim())
                    && TextUtils.isEmpty(mLoginVerificationCodeEt.getText().toString().trim())
                    && TextUtils.isEmpty(mLoginPasswordEt.getText().toString().trim())
                    && mLoginSubmission.isClickable()) {

                mLoginSubmission.setClickable(false);
                mLoginSubmission.setAlpha(0.5f);
            } else if (!mLoginSubmission.isClickable()) {
                mLoginSubmission.setClickable(true);
                mLoginSubmission.setAlpha(1f);
            }
        }
    };

    /**
     * 微信授权
     */
    private void loginWeChat() {
        Logger.i("TAG", "loginWeChat is start ......");
        // 是否需要清除授权，以下两种选一
        deleteOauth(SHARE_MEDIA.WEIXIN);
        // isNeedAuthOnGetUserInfo(SHARE_MEDIA.WEIXIN, true);
    }

    /**
     * QQ 授权
     */
    private void loginQQ() {
        Logger.i("TAG", "loginQQ is start ......");
        // 是否需要清除授权，以下两种选一
        deleteOauth(SHARE_MEDIA.QQ);
        // isNeedAuthOnGetUserInfo(SHARE_MEDIA.QQ, true);
    }

    // 是否需要清除授权：清除的话，每次登录拉取确认界面
    private void isNeedAuthOnGetUserInfo(SHARE_MEDIA share_media, boolean b) {
        UMShareAPI.get(this).setShareConfig(new UMShareConfig().isNeedAuthOnGetUserInfo(b));
        UMShareAPI.get(this).getPlatformInfo(this, share_media, getPlatformInfo);
    }

    // 需要清除授权：不清除的话，已经授权过的平台，不会再次调起登录授权界面，直接获取三方信息
    private void deleteOauth(SHARE_MEDIA share_media) {
        UMShareAPI.get(this).deleteOauth(this, share_media, deleteOauth);
    }

    // 清除登录授权信息
    protected UMAuthListener deleteOauth = new UMAuthListener() {
        @Override
        public void onStart(SHARE_MEDIA share_media) {
            // 清除登录授权开始的回调
            Logger.i("TAG", share_media + " deleteOauth Authorize onStart");
        }

        @Override
        public void onError(SHARE_MEDIA arg0, int arg1, Throwable arg2) {
            Logger.i("TAG", arg0 + " deleteOauth Authorize fail : " + arg2.getMessage());
            isNeedAuthOnGetUserInfo(arg0, true);
        }

        @Override
        public void onComplete(SHARE_MEDIA arg0, int arg1, Map<String, String> arg2) {
            Logger.i("TAG", arg0 + " deleteOauth Authorize succeed ,arg1 = " + arg1);
            isNeedAuthOnGetUserInfo(arg0, true);
        }

        @Override
        public void onCancel(SHARE_MEDIA arg0, int arg1) {
            Logger.i("TAG", arg0 + " deleteOauth Authorize cancel ,arg1 = " + arg1);
            isNeedAuthOnGetUserInfo(arg0, true);
        }
    };

    // 登录授权回调监听
    protected UMAuthListener getPlatformInfo = new UMAuthListener() {
        @Override
        public void onStart(SHARE_MEDIA share_media) {
            // 授权开始的回调
            Logger.i("TAG", share_media + " Authorize onStart");
        }

        @Override
        public void onError(SHARE_MEDIA arg0, int arg1, Throwable arg2) {
            Gson gson = new Gson();
            Map<String, Object> map = new HashMap<>();
            map.put("status", false);
            map.put("statusCode", "102");
            map.put("message", arg2.getMessage());
            Map<String, String> resource = new HashMap<>();
            map.put("resource", resource);
            Logger.i("TAG", arg0 + " 登录失败：" + gson.toJson(map));
        }

        @Override
        public void onComplete(SHARE_MEDIA arg0, int arg1, Map<String, String> arg2) {
            Gson gson = new Gson();
            Map<String, Object> map = new HashMap<>();
            map.put("status", true);
            map.put("statusCode", "101");
            map.put("message", "登录授权成功");
            map.put("resource", arg2);
            Logger.i("TAG", arg0 + " 登录完成：" + gson.toJson(map));
        }

        @Override
        public void onCancel(SHARE_MEDIA arg0, int arg1) {
            Gson gson = new Gson();
            Map<String, Object> map = new HashMap<>();
            map.put("status", false);
            map.put("statusCode", "100");
            map.put("message", "登录授权取消");
            Map<String, String> resource = new HashMap<>();
            map.put("resource", resource);
            Logger.i("TAG", arg0 + " 登录取消：" + gson.toJson(map));
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
    }
}
