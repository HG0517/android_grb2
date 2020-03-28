package com.jgkj.grb.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.jgkj.grb.R;
import com.jgkj.grb.base.BaseActivity;
import com.jgkj.grb.onclick.RxView;
import com.jgkj.grb.retrofit.ApiCallback;
import com.jgkj.grb.retrofit.ApiStores;
import com.jgkj.utils.token.GetTokenUtils;
import com.jgkj.utils.token.utils.des.HmacMd5;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import io.reactivex.Observable;

/**
 * 更改登录密码：0
 * 找回登录密码：1
 * 更改支付密码：2
 */
public class PasswordChangeActivity extends BaseActivity {

    public static void startActivityForResult(Activity activity, int type) {
        Bundle bundle = new Bundle();
        bundle.putInt("type", type);
        Intent intent = new Intent(activity, PasswordChangeActivity.class);
        intent.putExtras(bundle);
        activity.startActivityForResult(intent, 10008);
    }

    private int mType = -1;

    @BindView(R.id.password_title)
    TextView mPasswordTitle;
    @BindView(R.id.password_phone_number_et)
    EditText mPasswordPhoneNumberEt;
    @BindView(R.id.password_verification_code_right)
    FrameLayout mPasswordVerificationCodeAction;
    @BindView(R.id.password_verification_code_tv)
    TextView mPasswordVerificationCodeTv;
    @BindView(R.id.password_verification_code_et)
    EditText mPasswordVerificationCodeEt;
    @BindView(R.id.password_new_et)
    EditText mPasswordNewEt;
    @BindView(R.id.password_change_submission)
    CardView mPasswordChangeSubmission;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password_change);

        Toolbar toolbar = initToolBar("");

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        if (null == extras) {
            onBackPressed();
            return;
        }
        mType = extras.getInt("type", -1);
        if (mType < 0) {
            onBackPressed();
            return;
        }

        String title = "";
        if (mType == 0) {
            title = String.format(getString(R.string.password_title_text),
                    getString(R.string.password_change_text),
                    getString(R.string.safety_center_login_password_text));
        } else if (mType == 1) {
            title = String.format(getString(R.string.password_title_text),
                    getString(R.string.password_retrieve_text),
                    getString(R.string.safety_center_login_password_text));
        } else if (mType == 2) {
            title = String.format(getString(R.string.password_title_text),
                    getString(R.string.password_change_text),
                    getString(R.string.safety_center_payment_password_text));
        }
        mPasswordTitle.setText(title);

        RxView.setOnClickListeners(this, mPasswordVerificationCodeAction, mPasswordChangeSubmission);
        mPasswordChangeSubmission.setAlpha(0.5f);
        mPasswordChangeSubmission.setClickable(false);
        mPasswordPhoneNumberEt.addTextChangedListener(textWatcher);
        mPasswordVerificationCodeEt.addTextChangedListener(textWatcher);
        mPasswordNewEt.addTextChangedListener(textWatcher);

        try {
            String userStr = sharedPreferencesHelper.getSharedPreference("user", "").toString();
            JSONObject user = new JSONObject(userStr);
            String us_tel = user.getString("us_tel");
            mPasswordPhoneNumberEt.setText(TextUtils.isEmpty(us_tel) || TextUtils.equals("null", us_tel) ? "" : us_tel);
        } catch (JSONException e) {
        }
    }

    @Override
    public void onClick(Object o) {
        View v = (View) o;
        switch (v.getId()) {
            case R.id.password_verification_code_right:
                if (TextUtils.isEmpty(mPasswordPhoneNumberEt.getText().toString().trim())) {
                    toastShow(R.string.password_phone_number_tip);
                    return;
                }
                everyGetRegion(mPasswordVerificationCodeAction, mPasswordVerificationCodeTv, getString(R.string.password_verification_code_action), mPasswordPhoneNumberEt.getText().toString().trim());
                break;
            case R.id.password_change_submission:
                if (TextUtils.isEmpty(mPasswordPhoneNumberEt.getText().toString().trim())) {
                    toastShow(R.string.password_phone_number_tip);
                    return;
                }
                if (TextUtils.isEmpty(mPasswordVerificationCodeEt.getText().toString().trim())) {
                    toastShow(R.string.password_verification_code_tip);
                    return;
                }
                if (TextUtils.isEmpty(mPasswordNewEt.getText().toString().trim())) {
                    toastShow(R.string.password_new_tip);
                    return;
                }
                userChangePwd();
                break;
            default:
                break;
        }
    }

    private void userChangePwd() {
        showProgressDialog();
        Observable<String> observable = null;
        if (mType == 2) { // 修改支付密码
            String token = GetTokenUtils.getToken(this, ApiStores.API_SERVER_USER_CHANGESAFE);
            observable = apiStores().userChangeSafe(token, mPasswordPhoneNumberEt.getText().toString().trim(),
                    mPasswordVerificationCodeEt.getText().toString().trim(), mPasswordNewEt.getText().toString().trim());
        } else if (mType == 0) { // 修改登录密码
            String token = GetTokenUtils.getToken(this, ApiStores.API_SERVER_USER_CHANGEPWD);
            observable = apiStores().userChangePwd(token, mPasswordPhoneNumberEt.getText().toString().trim(),
                    mPasswordVerificationCodeEt.getText().toString().trim(), mPasswordNewEt.getText().toString().trim());
        } else if (mType == 1) { // 忘记登录密码
            String token = GetTokenUtils.getToken(this, ApiStores.API_SERVER_EVERY_FORGET);
            observable = apiStores().userEveryForget(token, mPasswordPhoneNumberEt.getText().toString().trim(),
                    mPasswordVerificationCodeEt.getText().toString().trim(), mPasswordNewEt.getText().toString().trim());
        }

        addSubscription(observable, new ApiCallback<String>() {
            @Override
            public void onSuccess(String model) {
                try {
                    JSONObject result = new JSONObject(model);
                    if (result.getInt("code") == 1) {
                        toastShow(result.getString("msg"));
                        if (mType == 2) {
                        } else {
                            String hmacMd5 = HmacMd5.getMd5(mPasswordNewEt.getText().toString().trim());
                            sharedPreferencesHelper.putApply("password", hmacMd5);
                        }
                        onBackPressed();
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

    TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
        }

        @Override
        public void afterTextChanged(Editable s) {
            if (TextUtils.isEmpty(mPasswordPhoneNumberEt.getText().toString().trim())
                    && TextUtils.isEmpty(mPasswordVerificationCodeEt.getText().toString().trim())
                    && TextUtils.isEmpty(mPasswordNewEt.getText().toString().trim())
                    && mPasswordChangeSubmission.isClickable()) {

                mPasswordChangeSubmission.setClickable(false);
                mPasswordChangeSubmission.setAlpha(0.5f);
            } else if (!mPasswordChangeSubmission.isClickable()) {
                mPasswordChangeSubmission.setClickable(true);
                mPasswordChangeSubmission.setAlpha(1f);
            }
        }
    };
}
