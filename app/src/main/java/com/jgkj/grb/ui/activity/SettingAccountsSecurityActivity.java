package com.jgkj.grb.ui.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.jgkj.grb.R;
import com.jgkj.grb.base.BaseActivity;
import com.jgkj.grb.onclick.RxView;
import com.jgkj.grb.retrofit.ApiCallback;
import com.jgkj.grb.retrofit.ApiStores;
import com.jgkj.grb.utils.Logger;
import com.jgkj.utils.token.GetTokenUtils;
import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareConfig;
import com.umeng.socialize.bean.SHARE_MEDIA;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;

/**
 * 账户与安全：安全中心
 */
public class SettingAccountsSecurityActivity extends BaseActivity {

    public static void start(Context context) {
        Intent intent = new Intent(context, SettingAccountsSecurityActivity.class);
        context.startActivity(intent);
    }

    @BindView(R.id.login_password_fl)
    FrameLayout mLoginPasswordFl;

    @BindView(R.id.payment_password_fl)
    FrameLayout mPaymentPasswordFl;

    @BindView(R.id.real_name_binding_fl)
    FrameLayout mRealNameBindingFl;
    @BindView(R.id.real_name_binding_right)
    TextView mRealNameBindingTv;

    @BindView(R.id.mobile_phone_number_change_fl)
    FrameLayout mMobilePhoneNumberChangeFl;
    @BindView(R.id.mobile_phone_number_change_right)
    TextView mMobilePhoneNumberChangeTv;

    @BindView(R.id.wechat_binding_fl)
    FrameLayout mWeChatBindingFl;
    @BindView(R.id.wechat_binding_right)
    TextView mWeChatBindingTv;

    @BindView(R.id.bank_card_binding_fl)
    FrameLayout mBankCardBindingFl;
    @BindView(R.id.bank_card_binding_right)
    TextView mBankCardBindingTv;

    @BindView(R.id.qq_binding_fl)
    FrameLayout mQQBindingFl;
    @BindView(R.id.qq_binding_right)
    TextView mQQBindingTv;

    @BindView(R.id.alipay_binding_fl)
    FrameLayout mAlipayBindingFl;
    @BindView(R.id.alipay_binding_right)
    TextView mAlipayBindingTv;

    @BindView(R.id.cloud_flash_binding_fl)
    FrameLayout mCloudFlashBindingFl;
    @BindView(R.id.cloud_flash_binding_right)
    TextView mCloudFlashBindingTv;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_accounts_security);

        Toolbar toolbar = initToolBar("");
        toolbar.setTitle(R.string.activity_title_setting_accounts_security);

        RxView.setOnClickListeners(this, mLoginPasswordFl, mPaymentPasswordFl,
                mRealNameBindingFl, mMobilePhoneNumberChangeFl, mWeChatBindingFl,
                mBankCardBindingFl, mQQBindingFl, mAlipayBindingFl, mCloudFlashBindingFl);

        initUserView();
    }

    private void initUserView() {
        try {
            // is_realname  实名情况：0:未认证  1认证中 2已认证
            // us_name  姓名 ；us_tel 手机号；we_account 微信账号；bank_account 银行卡号；qq   QQ账号；ali_account  支付宝账号；flashover  云闪付账号
            String userStr = sharedPreferencesHelper.getSharedPreference("user", "").toString();
            JSONObject user = new JSONObject(userStr);
            int is_realname = user.getInt("is_realname");
            String us_name = user.getString("us_name");
            String us_tel = user.getString("us_tel");
            String we_account = user.getString("we_account");
            String bank_account = user.getString("bank_account");
            String qq = user.getString("qq");
            String ali_account = user.getString("ali_account");
            String flashover = user.getString("flashover");
            if (is_realname == 2) {
                mRealNameBindingTv.setText(TextUtils.isEmpty(us_name) || TextUtils.equals("null", us_name) ? getString(R.string.safety_center_binding_certified_text) : us_name);
                mRealNameBindingTv.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
            } else if (is_realname == 1) {
                mRealNameBindingTv.setText(R.string.safety_center_binding_certification_text);
                mRealNameBindingTv.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.mipmap.ic_unbound_red), null, null, null);
            } else if (is_realname == 3) {
                mRealNameBindingTv.setText(R.string.real_name_rejected);
                mRealNameBindingTv.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.mipmap.ic_unbound_red), null, null, null);
            } else {
                mRealNameBindingTv.setText(getString(R.string.safety_center_binding_uncertified_text));
                mRealNameBindingTv.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.mipmap.ic_unbound_red), null, null, null);
            }
            mMobilePhoneNumberChangeTv.setText(TextUtils.isEmpty(us_tel) || TextUtils.equals("null", us_tel) ? "" : us_tel);
            if (TextUtils.isEmpty(we_account) || TextUtils.equals("null", we_account)) {
                mWeChatBindingTv.setText(getString(R.string.safety_center_binding_unbound_text));
                mWeChatBindingTv.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.mipmap.ic_unbound_red), null, null, null);
            } else {
                mWeChatBindingTv.setText(we_account);
                mWeChatBindingTv.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
            }
            if (TextUtils.isEmpty(bank_account) || TextUtils.equals("null", bank_account)) {
                mBankCardBindingTv.setText(getString(R.string.safety_center_binding_unbound_text));
                mBankCardBindingTv.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.mipmap.ic_unbound_red), null, null, null);
            } else {
                mBankCardBindingTv.setText(bank_account);
                mBankCardBindingTv.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
            }
            if (TextUtils.isEmpty(qq) || TextUtils.equals("null", qq)) {
                mQQBindingTv.setText(getString(R.string.safety_center_binding_unbound_text));
                mQQBindingTv.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.mipmap.ic_unbound_red), null, null, null);
            } else {
                mQQBindingTv.setText(qq);
                mQQBindingTv.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
            }
            if (TextUtils.isEmpty(ali_account) || TextUtils.equals("null", ali_account)) {
                mAlipayBindingTv.setText(getString(R.string.safety_center_binding_unbound_text));
                mAlipayBindingTv.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.mipmap.ic_unbound_red), null, null, null);
            } else {
                mAlipayBindingTv.setText(ali_account);
                mAlipayBindingTv.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
            }
            if (TextUtils.isEmpty(flashover) || TextUtils.equals("null", flashover) || TextUtils.equals("0", flashover)) {
                mCloudFlashBindingTv.setText(getString(R.string.safety_center_binding_unbound_text));
                mCloudFlashBindingTv.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.mipmap.ic_unbound_red), null, null, null);
            } else {
                mCloudFlashBindingTv.setText(flashover);
                mCloudFlashBindingTv.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
            }
        } catch (JSONException e) {
        }
    }

    @Override
    public void onClick(Object o) {
        View v = (View) o;
        // is_realname  实名情况：0:未认证  1认证中 2已认证
        int is_realname = 0;
        switch (v.getId()) {
            case R.id.login_password_fl:
                PasswordChangeActivity.startActivityForResult(mActivity, 0);
                break;
            case R.id.payment_password_fl:
                PasswordChangeActivity.startActivityForResult(mActivity, 2);
                break;
            case R.id.real_name_binding_fl:
                is_realname = isRealname();
                if (is_realname == 0) {
                    RealnameFirstActivity.startActivityForResult(mActivity);
                } else if (is_realname == 1) {
                    ApplyResultActivity.start(mActivity, 2);
                } else if (is_realname == 3) {
                    showUnrealNameDialog(getString(R.string.real_name_rejected));
                } else {
                    toastShow(R.string.safety_center_binding_certified_text);
                }
                break;
            case R.id.mobile_phone_number_change_fl:
                BindingMobileActivity.startActivityForResult(mActivity);
                break;
            case R.id.wechat_binding_fl:
                //toastShow(R.string.not_yet_open_tip);
                loginWeChat();
                break;
            case R.id.bank_card_binding_fl:
                is_realname = isRealname();
                if (is_realname == 0) {
                    RealnameFirstActivity.startActivityForResult(mActivity);
                } else if (is_realname == 1) {
                    ApplyResultActivity.start(mActivity, 2);
                } else if (is_realname == 3) {
                    showUnrealNameDialog(getString(R.string.real_name_rejected));
                } else {
                    BindingBankCardsActivity.startActivityForResult(mActivity);
                }
                break;
            case R.id.qq_binding_fl:
                //toastShow(R.string.not_yet_open_tip);
                loginQQ();
                break;
            case R.id.alipay_binding_fl:
                BindingAlipayActivity.startActivityForResult(mActivity);
                break;
            case R.id.cloud_flash_binding_fl:
                BindingCloudFlashActivity.startActivityForResult(mActivity);
                break;
            default:
                break;
        }
    }

    private void bindThreePartyAccount(SHARE_MEDIA shareMedia, Map<String, Object> data) {
        String url = ApiStores.API_SERVER_USER_BINDWECHAT;
        String token = GetTokenUtils.getToken(this, url);
        int type = -1;
        if (shareMedia == SHARE_MEDIA.WEIXIN) {
            type = 0;
        } else if (shareMedia == SHARE_MEDIA.QQ) {
            type = 3;
        }
        int finalType = type;
        addSubscription(apiStores().userBindWechat(token, type, new Gson().toJson(data)), new ApiCallback<String>() {
            @Override
            public void onSuccess(String model) {
                // {"code":1,"msg":"绑定成功","time":1575542985,"data":{"qq":"UID_17A3E3F10AB0DF7C051C23CA6790A787"}}
                // {"code":1,"msg":"绑定成功","time":1575543015,"data":{"we_account":"oUMuY1BHhTdpDjldu-M1mGlQIP7I"}}
                try {
                    JSONObject result = new JSONObject(model);
                    if (result.optInt("code", 0) == 1) {
                        JSONObject data1 = result.optJSONObject("data");
                        String userStr = sharedPreferencesHelper.getSharedPreference("user", "").toString();
                        JSONObject user = new JSONObject(userStr);
                        if (finalType == 0)
                            user.put("we_account", data1.optString("we_account", "已绑定"));
                        if (finalType == 3)
                            user.put("qq", data1.optString("qq", "已绑定"));
                        sharedPreferencesHelper.putApply("user", user.toString());
                        initUserView();
                        toastShow(result.optString("msg", "绑定成功"));
                    } else {
                        toastShow(result.optString("msg", "绑定失败"));
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
            bindThreePartyAccount(arg0, map);
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
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == 10008) {
                // 实名认证、绑定支付宝、绑定云闪付、绑定银行卡
                Logger.i("TAG_Realname", "实名认证|绑定支付宝|绑定云闪付|绑定银行卡|绑定手机号， OK ！");
                initUserView();
            }
        }
    }
}
