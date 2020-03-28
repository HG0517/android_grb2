package com.jgkj.grb.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.jgkj.grb.R;
import com.jgkj.grb.base.BaseActivity;
import com.jgkj.grb.glide.GlideApp;
import com.jgkj.grb.onclick.RxView;
import com.jgkj.grb.retrofit.ApiCallback;
import com.jgkj.grb.retrofit.ApiStores;
import com.jgkj.grb.ui.mvp.BankCardInfoModel;
import com.jgkj.grb.utils.BankCardUtil;
import com.jgkj.utils.token.GetTokenUtils;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;

/**
 * 绑定银行卡
 */
public class BindingBankCardsActivity extends BaseActivity {

    public static void startActivityForResult(Activity activity) {
        Intent intent = new Intent(activity, BindingBankCardsActivity.class);
        activity.startActivityForResult(intent, 10008);
    }

    @BindView(R.id.bank_card_realname_et)
    EditText mBankCardRealnameEt;
    @BindView(R.id.bank_card_cardid_et)
    EditText mBankCardIDEt;
    @BindView(R.id.bank_card_num_et)
    EditText mBankCardNumEt;
    @BindView(R.id.bank_card_nametype_fl)
    FrameLayout mBankCardNameType;
    @BindView(R.id.bank_card_nametype_iv)
    ImageView mBankCardNameTypeIv;
    @BindView(R.id.bank_card_nametype_tv)
    TextView mBankCardNameTypeTv;
    @BindView(R.id.bank_card_phone_et)
    EditText mBankCardPhoneEt;
    @BindView(R.id.bank_card_verification_code_et)
    EditText mBankCardCodeEt;
    @BindView(R.id.bank_card_verification_code_right)
    FrameLayout mBankCardCodeAction;
    @BindView(R.id.bank_card_verification_code_tv)
    TextView mBankCardCodeTv;
    @BindView(R.id.binding_submission)
    CardView mBankCardAction;

    private int choose = -1;
    BankCardInfoModel mBankCardInfoModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_binding_bank_cards);

        Toolbar toolbar = initToolBar(getString(R.string.activity_title_binding_bank_cards));

        RxView.setOnClickListeners(this, /*mBankCardNameType, */mBankCardCodeAction, mBankCardAction);
        mBankCardAction.setClickable(false);
        mBankCardAction.setAlpha(0.5f);
        mBankCardNumEt.addTextChangedListener(textWatcher);
        mBankCardPhoneEt.addTextChangedListener(textWatcher);
        mBankCardCodeEt.addTextChangedListener(textWatcher);

        mBankCardNumEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(final Editable s) {
                String bankCardNum = s.toString().replace(" ", "");
                if (!TextUtils.isEmpty(bankCardNum) && bankCardNum.length() >= 16) {
                    validateAndCacheCardInfo(bankCardNum);
                }
            }
        });

        try {
            String userStr = sharedPreferencesHelper.getSharedPreference("user", "").toString();
            JSONObject user = new JSONObject(userStr);
            String us_name = user.getString("us_name");
            String us_id_card = user.getString("us_id_card");
            String us_tel = user.getString("us_tel");
            mBankCardRealnameEt.setText(TextUtils.isEmpty(us_name) || TextUtils.equals("null", us_name) ? "" : us_name);
            mBankCardIDEt.setText(TextUtils.isEmpty(us_id_card) || TextUtils.equals("null", us_id_card) ? "" : us_id_card);
            mBankCardPhoneEt.setText(TextUtils.isEmpty(us_tel) || TextUtils.equals("null", us_tel) ? "" : us_tel);
        } catch (JSONException e) {
        }
    }

    @Override
    public void onClick(Object o) {
        View v = (View) o;
        switch (v.getId()) {
            case R.id.bank_card_nametype_fl:
                ChooseBankCardActivity.startActivityForResult(mActivity, choose);
                break;
            case R.id.bank_card_verification_code_right:
                if (TextUtils.isEmpty(mBankCardPhoneEt.getText().toString().trim())) {
                    toastShow(R.string.password_phone_number_tip);
                    return;
                }
                everyGetRegion(mBankCardCodeAction, mBankCardCodeTv, getString(R.string.password_verification_code_action), mBankCardPhoneEt.getText().toString().trim());
                break;
            case R.id.binding_submission:
                if (TextUtils.isEmpty(mBankCardRealnameEt.getText().toString().trim())) {
                    toastShow(R.string.binding_bank_card_realname_tip);
                    return;
                }
                if (TextUtils.isEmpty(mBankCardIDEt.getText().toString().trim())) {
                    toastShow(R.string.binding_bank_card_cardid_tip);
                    return;
                }
                if (TextUtils.isEmpty(mBankCardNumEt.getText().toString().trim())) {
                    toastShow(R.string.binding_bank_card_num_tip);
                    return;
                }
                if (TextUtils.isEmpty(mBankCardPhoneEt.getText().toString().trim())) {
                    toastShow(R.string.password_phone_number_tip);
                    return;
                }
                if (TextUtils.isEmpty(mBankCardCodeEt.getText().toString().trim())) {
                    toastShow(R.string.binding_bank_card_code_tip);
                    return;
                }
                if (null == mBankCardInfoModel || !mBankCardInfoModel.isValidated()) {
                    String bankCardNum = mBankCardNumEt.getText().toString().trim();
                    if (!TextUtils.isEmpty(bankCardNum) && bankCardNum.length() >= 16) {
                        validateAndCacheCardInfo(bankCardNum);
                        toastShow(R.string.binding_bank_cards_empty_tip);
                    }
                    return;
                }
                userBindBank();
                break;
            default:
                break;
        }
    }

    private void userBindBank() {
        showProgressDialog();
        String token = GetTokenUtils.getToken(this, ApiStores.API_SERVER_USER_BINDBANK);
        addSubscription(apiStores().userBindBank(token, mBankCardRealnameEt.getText().toString().trim(), mBankCardIDEt.getText().toString().trim(),
                mBankCardNumEt.getText().toString().trim(), mBankCardInfoModel.getBank(),
                mBankCardPhoneEt.getText().toString().trim(), mBankCardCodeEt.getText().toString().trim()),
                new ApiCallback<String>() {
                    @Override
                    public void onSuccess(String model) {
                        try {
                            JSONObject result = new JSONObject(model);
                            if (result.getInt("code") == 1) {
                                toastShow(result.getString("msg"));
                                try {
                                    String userStr = sharedPreferencesHelper.getSharedPreference("user", "").toString();
                                    JSONObject user = new JSONObject(userStr);
                                    user.put("bank_account", mBankCardNumEt.getText().toString().trim());
                                    sharedPreferencesHelper.putApply("user", user.toString());
                                } catch (JSONException e) {
                                }
                                setResult(Activity.RESULT_OK);
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

    private void validateAndCacheCardInfo(String bankCardNum) {
        addSubscription(apiStores().cardInfo("utf-8", "true", bankCardNum),
                new ApiCallback<BankCardInfoModel>() {
                    @Override
                    public void onSuccess(BankCardInfoModel model) {
                        if (null != model && model.isValidated()) {
                            mBankCardInfoModel = model;
                            if (!TextUtils.isEmpty(mBankCardInfoModel.getBank()))
                                GlideApp.with(mActivity)
                                        .load(BankCardUtil.ALIPAY_CARD_LOGO + "?d=cashier&t=" + mBankCardInfoModel.getBank())
                                        .placeholder(new ColorDrawable(Color.WHITE))
                                        .error(new ColorDrawable(Color.WHITE))
                                        .fallback(new ColorDrawable(Color.WHITE))
                                        .into(mBankCardNameTypeIv);
                        } else {
                            mBankCardInfoModel = null;
                            GlideApp.with(mActivity)
                                    .load(BankCardUtil.ALIPAY_CARD_LOGO + "?d=cashier&t=")
                                    .placeholder(new ColorDrawable(Color.WHITE))
                                    .error(new ColorDrawable(Color.WHITE))
                                    .fallback(new ColorDrawable(Color.WHITE))
                                    .centerCrop()
                                    .into(mBankCardNameTypeIv);
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == 10003 && null != data) {
                Bundle extras = data.getExtras();
                if (null != extras) {
                    choose = extras.getInt("choose");
                    toastShow("choose：" + choose);
                }
            }
        }
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
            if (TextUtils.isEmpty(mBankCardNumEt.getText().toString().trim())
                    && TextUtils.isEmpty(mBankCardPhoneEt.getText().toString().trim())
                    && TextUtils.isEmpty(mBankCardCodeEt.getText().toString().trim())
                    && mBankCardAction.isClickable()) {

                mBankCardAction.setClickable(false);
                mBankCardAction.setAlpha(0.5f);
            } else if (!mBankCardAction.isClickable()) {
                mBankCardAction.setClickable(true);
                mBankCardAction.setAlpha(1f);
            }
        }
    };
}
