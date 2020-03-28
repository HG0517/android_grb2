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

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;

/**
 * 绑定手机号
 * 手机号换绑
 */
public class BindingMobileActivity extends BaseActivity {

    public static void startActivityForResult(Activity context) {
        Intent intent = new Intent(context, BindingMobileActivity.class);
        context.startActivityForResult(intent, 10008);
    }

    @BindView(R.id.password_phone_number_et)
    EditText mPhoneNumberEt;
    @BindView(R.id.password_verification_code_right)
    FrameLayout mVerificationCodeAction;
    @BindView(R.id.password_verification_code_tv)
    TextView mVerificationCodeTv;
    @BindView(R.id.password_verification_code_et)
    EditText mVerificationCodeEt;
    @BindView(R.id.password_change_submission)
    CardView mBindingSubmission;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_binding_mobile);

        Toolbar toolbar = initToolBar("");

        RxView.setOnClickListeners(this, mVerificationCodeAction, mBindingSubmission);
        mBindingSubmission.setAlpha(0.5f);
        mBindingSubmission.setClickable(false);
        mPhoneNumberEt.addTextChangedListener(textWatcher);
        mVerificationCodeEt.addTextChangedListener(textWatcher);
    }

    @Override
    public void onClick(Object o) {
        View v = (View) o;
        switch (v.getId()) {
            case R.id.password_verification_code_right:
                if (TextUtils.isEmpty(mPhoneNumberEt.getText().toString().trim())) {
                    toastShow(R.string.password_phone_number_tip);
                    return;
                }
                everyGetRegion(mVerificationCodeAction, mVerificationCodeTv, getString(R.string.password_verification_code_action), mPhoneNumberEt.getText().toString().trim());
                break;
            case R.id.password_change_submission:
                if (TextUtils.isEmpty(mPhoneNumberEt.getText().toString().trim())) {
                    toastShow(R.string.password_phone_number_tip);
                    return;
                }
                if (TextUtils.isEmpty(mVerificationCodeEt.getText().toString().trim())) {
                    toastShow(R.string.password_verification_code_tip);
                    return;
                }
                userBlindTel(mPhoneNumberEt.getText().toString().trim(), mVerificationCodeEt.getText().toString().trim());
                break;
            default:
                break;
        }
    }

    private void userBlindTel(String phone, String code) {
        showProgressDialog();
        String token = GetTokenUtils.getToken(this, ApiStores.API_SERVER_USER_BLINDTEL);
        addSubscription(apiStores().userBlindTel(token, phone, code), new ApiCallback<String>() {
            @Override
            public void onSuccess(String model) {
                try {
                    JSONObject result = new JSONObject(model);
                    if (result.optInt("code", 0) == 1) {
                        toastShow(result.optString("msg", ""));

                        JSONObject userModel = new JSONObject(sharedPreferencesHelper.getSharedPreference("user", "").toString());
                        userModel.put("us_tel", phone);
                        sharedPreferencesHelper.putApply("username", phone);
                        sharedPreferencesHelper.putApply("user", userModel.toString());

                        setResult(Activity.RESULT_OK);
                        onBackPressed();
                    } else {
                        toastShow(result.optString("msg", ""));
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
            if (TextUtils.isEmpty(mPhoneNumberEt.getText().toString().trim())
                    && TextUtils.isEmpty(mVerificationCodeEt.getText().toString().trim())
                    && mBindingSubmission.isClickable()) {

                mBindingSubmission.setClickable(false);
                mBindingSubmission.setAlpha(0.5f);
            } else if (!mBindingSubmission.isClickable()) {
                mBindingSubmission.setClickable(true);
                mBindingSubmission.setAlpha(1f);
            }
        }
    };
}
