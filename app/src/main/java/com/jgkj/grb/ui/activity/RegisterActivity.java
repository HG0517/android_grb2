package com.jgkj.grb.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.jgkj.grb.R;
import com.jgkj.grb.base.BaseActivity;
import com.jgkj.grb.onclick.RxView;
import com.jgkj.grb.retrofit.ApiCallback;
import com.jgkj.grb.retrofit.ApiStores;
import com.jgkj.grb.ui.dialog.RegisterAgreeAbstractDialog;
import com.jgkj.grb.utils.SpannableStringUtil;
import com.jgkj.utils.token.GetTokenUtils;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;

/**
 * 注册
 */
public class RegisterActivity extends BaseActivity {

    public static void start(Context context) {
        Intent intent = new Intent(context, RegisterActivity.class);
        context.startActivity(intent);
    }

    @BindView(R.id.register_referee_et)
    EditText mRegisterRefereeEt;
    @BindView(R.id.register_username_et)
    EditText mRegisterUsernameEt;
    @BindView(R.id.register_phone_number_et)
    EditText mRegisterPhoneNumberEt;
    @BindView(R.id.register_verification_code_right)
    FrameLayout mRegisterVerificationCodeAction;
    @BindView(R.id.register_verification_code_tv)
    TextView mRegisterVerificationCodeTv;
    @BindView(R.id.register_verification_code_et)
    EditText mRegisterVerificationCodeEt;
    @BindView(R.id.register_password_et)
    EditText mRegisterPasswordEt;
    @BindView(R.id.register_submission)
    CardView mRegisterSubmission;
    @BindView(R.id.register_terms_of_service_tv)
    TextView mRegisterTermsOfServiceTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        Toolbar toolbar = initToolBar("");

        RxView.setOnClickListeners(this, mRegisterVerificationCodeAction, mRegisterSubmission);
        mRegisterSubmission.setAlpha(0.5f);
        mRegisterSubmission.setClickable(false);
        mRegisterPhoneNumberEt.addTextChangedListener(textWatcher);
        mRegisterVerificationCodeEt.addTextChangedListener(textWatcher);
        mRegisterPasswordEt.addTextChangedListener(textWatcher);

        String text = getString(R.string.register_terms_of_service_text);
        String tip = getString(R.string.register_terms_of_service_tip);
        SpannableString spannableString = SpannableStringUtil.getSpannableString(String.format(text, tip),
                tip.length(), 1f, Color.parseColor("#FB355C"),
                v -> {
                    //TermsOfServiceActivity.start(mActivity);
                    ServiceHelpWebViewActivity.start(mActivity, "14");
                });
        // 不设置点击不生效
        mRegisterTermsOfServiceTv.setMovementMethod(LinkMovementMethod.getInstance());
        mRegisterTermsOfServiceTv.setText(spannableString);
        // 去掉点击后文字的背景色
        // mRegisterTermsOfServiceTv.setHighlightColor(Color.TRANSPARENT);

        showRegisterAgreeAbstract();
    }

    @Override
    public void onClick(Object o) {
        View v = (View) o;
        switch (v.getId()) {
            case R.id.register_verification_code_right:
                if (TextUtils.isEmpty(mRegisterPhoneNumberEt.getText().toString().trim())) {
                    toastShow(R.string.password_phone_number_tip);
                    return;
                }
                everyGetRegion(mRegisterVerificationCodeAction, mRegisterVerificationCodeTv, getString(R.string.password_verification_code_action), mRegisterPhoneNumberEt.getText().toString().trim());
                break;
            case R.id.register_submission:
                if (TextUtils.isEmpty(mRegisterRefereeEt.getText().toString().trim())) {
                    toastShow(R.string.register_referee_tip);
                    return;
                }
                if (TextUtils.isEmpty(mRegisterUsernameEt.getText().toString().trim())) {
                    toastShow(R.string.register_username_tip);
                    return;
                }
                if (TextUtils.isEmpty(mRegisterPhoneNumberEt.getText().toString().trim())) {
                    toastShow(R.string.password_phone_number_tip);
                    return;
                }
                if (TextUtils.isEmpty(mRegisterVerificationCodeEt.getText().toString().trim())) {
                    toastShow(R.string.password_verification_code_tip);
                    return;
                }
                if (TextUtils.isEmpty(mRegisterPasswordEt.getText().toString().trim())
                        || mRegisterPasswordEt.getText().toString().trim().length() < 6) {
                    toastShow(R.string.register_password_tip);
                    return;
                }
                everyRegister();
                break;
            default:
                break;
        }
    }

    private void everyRegister() {
        showProgressDialog();
        String token = GetTokenUtils.getToken(this, ApiStores.API_SERVER_EVERY_REGISTER);
        addSubscription(apiStores().everyRegister(token, mRegisterRefereeEt.getText().toString().trim(), mRegisterUsernameEt.getText().toString().trim(),
                mRegisterPhoneNumberEt.getText().toString().trim(), mRegisterVerificationCodeEt.getText().toString().trim(),
                mRegisterPasswordEt.getText().toString().trim()), new ApiCallback<String>() {
            @Override
            public void onSuccess(String model) {
                try {
                    JSONObject result = new JSONObject(model);
                    if (result.getInt("code") == 1) {
                        toastShow(result.getString("msg"));
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

    private void showRegisterAgreeAbstract() {
        RegisterAgreeAbstractDialog dialog = RegisterAgreeAbstractDialog.newInstance();
        dialog.setCancelable(false);
        dialog.showDialog(getSupportFragmentManager());
        dialog.setOnDialogListener(new RegisterAgreeAbstractDialog.OnDialogListener() {
            @Override
            public void onCancel() {
                onBackPressed();
            }

            @Override
            public void onSure() {

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
            if (TextUtils.isEmpty(mRegisterPhoneNumberEt.getText().toString().trim())
                    && TextUtils.isEmpty(mRegisterVerificationCodeEt.getText().toString().trim())
                    && TextUtils.isEmpty(mRegisterPasswordEt.getText().toString().trim())
                    && mRegisterSubmission.isClickable()) {

                mRegisterSubmission.setClickable(false);
                mRegisterSubmission.setAlpha(0.5f);
            } else if (!mRegisterSubmission.isClickable()) {
                mRegisterSubmission.setClickable(true);
                mRegisterSubmission.setAlpha(1f);
            }
        }
    };
}
