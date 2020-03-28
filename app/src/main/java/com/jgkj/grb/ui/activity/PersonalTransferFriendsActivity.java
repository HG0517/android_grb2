package com.jgkj.grb.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.jgkj.grb.R;
import com.jgkj.grb.base.BaseActivity;
import com.jgkj.grb.eventbus.RefreshUserInfo;
import com.jgkj.grb.onclick.RxView;
import com.jgkj.grb.retrofit.ApiCallback;
import com.jgkj.grb.retrofit.ApiStores;
import com.jgkj.grb.ui.bean.PersonalTransferFriendsModel;
import com.jgkj.grb.ui.dialog.PaymentNowDialog;
import com.jgkj.utils.token.GetTokenUtils;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;

/**
 * 个人中心：GRB，好友转让
 */
public class PersonalTransferFriendsActivity extends BaseActivity implements RadioGroup.OnCheckedChangeListener {

    public static void start(Context context) {
        Intent intent = new Intent(context, PersonalTransferFriendsActivity.class);
        context.startActivity(intent);
    }

    // 参考价格
    @BindView(R.id.num_0)
    TextView mNum_0; // GRB
    @BindView(R.id.num_1)
    TextView mNum_1; // GRA

    // 兑换：兑换额度
    @BindView(R.id.rg_3)
    RadioGroup mRg_3;
    @BindView(R.id.rg_3_0)
    RadioButton mRg_3_0;
    @BindView(R.id.rg_3_1)
    RadioButton mRg_3_1;
    @BindView(R.id.rg_3_2)
    RadioButton mRg_3_2;
    // 兑换：汇率、手续费
    @BindView(R.id.title_1_grb)
    TextView mTitle_1_grb;
    @BindView(R.id.title_1_gra)
    TextView mTitle_1_gra;
    @BindView(R.id.title_1_fee)
    TextView mTitle_1_fee;
    // 兑换：份数、账号
    @BindView(R.id.num_2)
    EditText mNum_2;
    @BindView(R.id.num_3)
    EditText mNum_3;
    @BindView(R.id.sure_3)
    Button mSure_3;
    int mTransferType_3 = 0;

    // 兑换：兑换额度
    @BindView(R.id.rg_6)
    RadioGroup mRg_6;
    @BindView(R.id.rg_6_0)
    RadioButton mRg_6_0;
    @BindView(R.id.rg_6_1)
    RadioButton mRg_6_1;
    @BindView(R.id.rg_6_2)
    RadioButton mRg_6_2;
    // 兑换：手续费
    @BindView(R.id.title_10_grb)
    TextView mTitle_10_cash;
    @BindView(R.id.title_10_gra)
    TextView mTitle_10_gra;
    @BindView(R.id.title_10_fee)
    TextView mTitle_10_fee;
    // 兑换：份数、账号
    @BindView(R.id.num_9)
    EditText mNum_9;
    @BindView(R.id.num_10)
    EditText mNum_10;
    @BindView(R.id.sure_6)
    Button mSure_6;
    int mTransferType_6 = 0;

    // 好友转让：份数、账号
    @BindView(R.id.num_5)
    EditText mNum_5;
    @BindView(R.id.num_6)
    EditText mNum_6;
    // 好友转让：通证类别
    @BindView(R.id.rg_4)
    RadioGroup mRg_4;
    @BindView(R.id.rg_4_0)
    RadioButton mRg_4_0;
    @BindView(R.id.rg_4_1)
    RadioButton mRg_4_1;
    @BindView(R.id.rg_4_2)
    RadioButton mRg_4_2;
    @BindView(R.id.sure_4)
    Button mSure_4;
    int mTransferType_4 = 0;

    // 转给总部：份数、账号<11166668888>
    @BindView(R.id.num_7)
    EditText mNum_7;
    @BindView(R.id.num_8)
    EditText mNum_8;
    // 转给总部：通证类别
    @BindView(R.id.rg_5)
    RadioGroup mRg_5;
    @BindView(R.id.rg_5_0)
    RadioButton mRg_5_0;
    @BindView(R.id.rg_5_1)
    RadioButton mRg_5_1;
    @BindView(R.id.rg_5_2)
    RadioButton mRg_5_2;
    @BindView(R.id.sure_5)
    Button mSure_5;
    int mTransferType_5 = 0;

    PersonalTransferFriendsModel mModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_transfer_friends);

        Toolbar toolbar = initToolBar(getString(R.string.activity_title_personal_transfer_friends));

        RxView.setOnClickListeners(this, mSure_3, mSure_4, mSure_5, mSure_6);
        mRg_3.setOnCheckedChangeListener(this);
        mRg_4.setOnCheckedChangeListener(this);
        mRg_5.setOnCheckedChangeListener(this);
        mRg_6.setOnCheckedChangeListener(this);

        mNum_2.addTextChangedListener(mTextWatcher3);
        mNum_3.addTextChangedListener(mTextWatcher3);
        mNum_5.addTextChangedListener(mTextWatcher4);
        mNum_6.addTextChangedListener(mTextWatcher4);
        mNum_7.addTextChangedListener(mTextWatcher5);
        mNum_8.addTextChangedListener(mTextWatcher5);
        mNum_9.addTextChangedListener(mTextWatcher6);
        mNum_10.addTextChangedListener(mTextWatcher6);

        mRg_4_0.setChecked(true);
        mRg_5_0.setChecked(true);
        mTransferType_5 = 4;
        onLazyLoad();
    }

    private void onLazyLoad() {
        mRg_3_0.setChecked(false);
        mRg_3_1.setChecked(false);
        mRg_3_2.setChecked(false);
        mRg_6_0.setChecked(false);
        mRg_6_1.setChecked(false);
        mRg_6_2.setChecked(false);
        mTransferType_3 = 0;
        mTransferType_6 = 0;
        mNum_0.setText("0.00");
        mNum_1.setText("0.00");
        mNum_2.setText("");
        mTitle_1_grb.setText(String.format("%sGRB", "0"));
        mTitle_1_gra.setText(String.format("%sGRA", "0"));
        mTitle_1_fee.setText(String.format("(手续费%sGRB)", "0"));
        mTitle_10_cash.setText(String.format("%s现金币", "0"));
        mTitle_10_gra.setText(String.format("%sGRA", "0"));
        mTitle_10_fee.setText("");

        showProgressDialog();
        String token = GetTokenUtils.getToken(this, ApiStores.API_SERVER_USER_EXCHANGE);
        addSubscription(apiStores().userExchange(token), new ApiCallback<PersonalTransferFriendsModel>() {
            @Override
            public void onSuccess(PersonalTransferFriendsModel model) {
                if (model.getCode() == 1) {
                    mModel = model;
                    mNum_0.setText(model.getData().getGrbfee());
                    mNum_1.setText(model.getData().getGrafee());
                    mTitle_1_grb.setText(String.format("%sGRB", "1"));
                    mTitle_1_gra.setText(String.format("%sGRA", model.getData().getChange()));
                    mTitle_1_fee.setText(String.format("(手续费%sGRB)", model.getData().getFee()));
                    mTitle_10_cash.setText(String.format("%s现金币", "1"));
                    mTitle_10_gra.setText(String.format("%sGRA", model.getData().getChange2()));
                    mTitle_10_fee.setText(String.format("(手续费%sGRB)", model.getData().getCashfee()));
                    mRg_3_0.setChecked(true);
                    mRg_6_0.setChecked(true);
                } else {
                    toastShow(model.getMsg());
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
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId) {
            case R.id.rg_3_0:
                if (null != mModel) {
                    mNum_2.setText(mModel.getData().getGra1());
                    mTransferType_3 = 1;
                } else {
                    onLazyLoad();
                }
                break;
            case R.id.rg_3_1:
                if (null != mModel) {
                    mNum_2.setText(mModel.getData().getGra2());
                    mTransferType_3 = 2;
                } else {
                    onLazyLoad();
                }
                break;
            case R.id.rg_3_2:
                if (null != mModel) {
                    mNum_2.setText(mModel.getData().getGra3());
                    mTransferType_3 = 3;
                } else {
                    onLazyLoad();
                }
                break;
            case R.id.rg_6_0:
                if (null != mModel) {
                    mNum_9.setText(mModel.getData().getGra4());
                    mTransferType_6 = 1;
                } else {
                    onLazyLoad();
                }
                break;
            case R.id.rg_6_1:
                if (null != mModel) {
                    mNum_9.setText(mModel.getData().getGra5());
                    mTransferType_6 = 2;
                } else {
                    onLazyLoad();
                }
                break;
            case R.id.rg_6_2:
                if (null != mModel) {
                    mNum_9.setText(mModel.getData().getGra6());
                    mTransferType_6 = 3;
                } else {
                    onLazyLoad();
                }
                break;
            case R.id.rg_4_0:
                mTransferType_4 = 4;
                break;
            case R.id.rg_4_1:
                mTransferType_4 = 2;
                break;
            case R.id.rg_4_2:
                mTransferType_4 = 1;
                break;
            case R.id.rg_5_0:
                mTransferType_5 = 4;
                break;
            case R.id.rg_5_1:
                mTransferType_5 = 2;
                break;
            case R.id.rg_5_2:
                mTransferType_5 = 1;
                break;
            default:
                break;
        }
    }

    @Override
    public void onClick(Object o) {
        View v = (View) o;
        switch (v.getId()) {
            case R.id.sure_3:
                showPaymentDialog(3, mNum_2.getText().toString().trim(), mNum_3.getText().toString().trim(), mTransferType_3);
                break;
            case R.id.sure_4:
                showPaymentDialog(4, mNum_5.getText().toString().trim(), mNum_6.getText().toString().trim(), mTransferType_4);
                break;
            case R.id.sure_5:
                showPaymentDialog(5, mNum_7.getText().toString().trim(), mNum_8.getText().toString().trim(), mTransferType_5);
                break;
            case R.id.sure_6:
                showPaymentDialog(6, mNum_9.getText().toString().trim(), mNum_10.getText().toString().trim(), mTransferType_6);
                break;
            default:
                break;
        }
    }

    /**
     * @param type      3 兑换，4 好友转让，5 转给总部
     * @param trNumber  转让份数
     * @param trAccount 转入账号
     * @param trType    通证类别
     */
    private void showPaymentDialog(int type, String trNumber, String trAccount, int trType) {
        PaymentNowDialog dialog = new PaymentNowDialog(this);
        dialog.setCancelable(false);
        dialog.show();
        dialog.setOnFinishListener(new PaymentNowDialog.OnActionClickListener() {
            @Override
            public void onClose() {
            }

            @Override
            public void onInputFinish(String password) {
                if (type == 3) {
                    userTurntoExchange(trNumber, trAccount, trType, password);
                } else if (type == 4 || type == 5) {
                    userTurnto(trNumber, trAccount, trType, password);
                } else if (type == 6) {
                    userExchangeCashToGRA(trNumber, trAccount, trType, password);
                }
            }
        });
    }

    /**
     * 兑换
     *
     * @param trNumber  份数
     * @param trAccount 转入账号
     * @param trType    兑换额度：1：500 2：1000 3：2500
     * @param password  交易密码
     */
    private void userTurntoExchange(String trNumber, String trAccount, int trType, String password) {
        showProgressDialog();
        String token = GetTokenUtils.getToken(mActivity, ApiStores.API_SERVER_USER_GRBTOGRA);
        addSubscription(apiStores().userExchangeGRBToGRA(token, trType, trAccount, password),
                new ApiCallback<String>() {
                    @Override
                    public void onSuccess(String model) {
                        try {
                            JSONObject result = new JSONObject(model);
                            if (result.optInt("code", 0) == 1) {
                                toastShow(result.optString("msg"));
                                EventBus.getDefault().post(new RefreshUserInfo(isLogin()));
                                onBackPressed();
                            } else {
                                toastShow(result.optString("msg"));
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

    /**
     * 兑换
     *
     * @param trNumber  份数
     * @param trAccount 转入账号
     * @param trType    兑换额度：1：500 2：1000 3：2500
     * @param password  交易密码
     */
    private void userExchangeCashToGRA(String trNumber, String trAccount, int trType, String password) {
        showProgressDialog();
        String token = GetTokenUtils.getToken(mActivity, ApiStores.API_SERVER_USER_CASHTOGRA);
        addSubscription(apiStores().userExchangeCashToGRA(token, trType, trAccount, password),
                new ApiCallback<String>() {
                    @Override
                    public void onSuccess(String model) {
                        try {
                            JSONObject result = new JSONObject(model);
                            if (result.optInt("code", 0) == 1) {
                                toastShow(result.optString("msg"));
                                EventBus.getDefault().post(new RefreshUserInfo(isLogin()));
                                onBackPressed();
                            } else {
                                toastShow(result.optString("msg"));
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

    /**
     * 转让
     *
     * @param trNumber  份数
     * @param trAccount 转入账号
     * @param trType    通证类别：4：GRA 2：GRB 1：GRC 3：现金
     * @param password  交易密码
     */
    private void userTurnto(String trNumber, String trAccount, int trType, String password) {
        showProgressDialog();
        String token = GetTokenUtils.getToken(mActivity, ApiStores.API_SERVER_USER_TURNTO);
        addSubscription(apiStores().userTurnto(token, trType, trNumber, trAccount, password),
                new ApiCallback<String>() {
                    @Override
                    public void onSuccess(String model) {
                        try {
                            JSONObject result = new JSONObject(model);
                            if (result.optInt("code", 0) == 1) {
                                toastShow(result.optString("msg"));
                                EventBus.getDefault().post(new RefreshUserInfo(isLogin()));
                                onBackPressed();
                            } else {
                                toastShow(result.optString("msg"));
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

    private TextWatcher mTextWatcher3 = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
        }

        @Override
        public void afterTextChanged(Editable s) {
            mSure_3.setEnabled(!(TextUtils.isEmpty(mNum_2.getText().toString().trim())
                    || TextUtils.isEmpty(mNum_3.getText().toString().trim())));
        }
    };
    private TextWatcher mTextWatcher4 = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
        }

        @Override
        public void afterTextChanged(Editable s) {
            mSure_4.setEnabled(!(TextUtils.isEmpty(mNum_5.getText().toString().trim())
                    || TextUtils.isEmpty(mNum_6.getText().toString().trim())));
        }
    };
    private TextWatcher mTextWatcher5 = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
        }

        @Override
        public void afterTextChanged(Editable s) {
            mSure_5.setEnabled(!(TextUtils.isEmpty(mNum_7.getText().toString().trim())
                    || TextUtils.isEmpty(mNum_8.getText().toString().trim())));
        }
    };
    private TextWatcher mTextWatcher6 = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
        }

        @Override
        public void afterTextChanged(Editable s) {
            mSure_6.setEnabled(!(TextUtils.isEmpty(mNum_9.getText().toString().trim())
                    || TextUtils.isEmpty(mNum_10.getText().toString().trim())));
        }
    };
}
