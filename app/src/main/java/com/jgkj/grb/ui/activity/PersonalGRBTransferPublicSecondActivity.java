package com.jgkj.grb.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.jgkj.grb.R;
import com.jgkj.grb.base.BaseActivity;
import com.jgkj.grb.eventbus.RefreshUserInfo;
import com.jgkj.grb.onclick.RxView;
import com.jgkj.grb.retrofit.ApiCallback;
import com.jgkj.grb.retrofit.ApiStores;
import com.jgkj.grb.ui.bean.UserRatioModel;
import com.jgkj.utils.token.GetTokenUtils;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;

/**
 * 个人中心：GRB，公共市场转让
 */
public class PersonalGRBTransferPublicSecondActivity extends BaseActivity {

    public static void start(Context context, int type) {
        Intent intent = new Intent(context, PersonalGRBTransferPublicSecondActivity.class);
        intent.putExtra("type", type);
        context.startActivity(intent);
    }

    @BindView(R.id.public_second_num_et)
    EditText mPublicSecondNumEt;
    @BindView(R.id.public_second_num_max)
    TextView mPublicSecondNumMax;
    @BindView(R.id.public_second_addr_et)
    EditText mPublicSecondAddrEt;
    @BindView(R.id.public_second_tip)
    TextView mPublicSecondTip;
    @BindView(R.id.public_second)
    CardView mPublicSecond;

    int mType = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_grbtransfer_public_second);

        Intent intent = getIntent();
        if (intent.hasExtra("type")) {
            mType = intent.getIntExtra("type", 0);
        }
        String title = "";
        String tip = "";
        if (mType == 0) {
            title = getString(R.string.personal_grb_transfer_public_0);
            tip = getString(R.string.transfer_public_second_tip);
        } else if (mType == 1) {
            title = getString(R.string.personal_grb_transfer_public_1);
            tip = getString(R.string.transfer_public_second_tip);
        } else if (mType == 2) {
            title = getString(R.string.personal_grb_transfer_public_2);
            tip = getString(R.string.transfer_public_second_tip);
        }
        Toolbar toolbar = initToolBar(title);
        mPublicSecondTip.setText(tip);

        RxView.setOnClickListeners(this, mPublicSecond);

        userRatio();
    }

    @Override
    public void onClick(Object o) {
        View v = (View) o;
        switch (v.getId()) {
            case R.id.public_second:
                if (TextUtils.isEmpty(mPublicSecondNumEt.getText().toString().trim())) {
                    toastShow(R.string.transfer_public_second_num_input);
                    return;
                }
                if (TextUtils.isEmpty(mPublicSecondAddrEt.getText().toString().trim())) {
                    toastShow(R.string.transfer_public_second_addr_input);
                    return;
                }
                // 0xD60D4826d55E4879fe457703f6eC25Ff8c93B69A
                if (mPublicSecondAddrEt.getText().toString().trim().length() != 42) {
                    toastShow(R.string.transfer_public_second_addr_input_tip);
                    return;
                }
                userWithdraw(mPublicSecondNumEt.getText().toString(), mPublicSecondAddrEt.getText().toString());
                break;
            default:
                break;
        }
    }

    /**
     * 获取 GRB 可兑换 GRA 金额
     */
    private void userRatio() {
        showProgressDialog();
        String token = GetTokenUtils.getToken(this, ApiStores.API_SERVER_USER_RATIO);
        addSubscription(apiStores().userRatio(token), new ApiCallback<UserRatioModel>() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onSuccess(UserRatioModel model) {
                if (model.getCode() == 1) {
                    mPublicSecondNumMax.setText(model.getData().getMsg());
                    mPublicSecondAddrEt.setText(TextUtils.isEmpty(model.getData().getAddress()) ? "" : model.getData().getAddress());
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

    /**
     * 公共市场转出：
     *
     * @param money   转出的 GRA 数量
     * @param address 钱包地址
     */
    private void userWithdraw(String money, String address) {
        showProgressDialog();
        String token = GetTokenUtils.getToken(this, ApiStores.API_SERVER_USER_WITHDRAW);
        addSubscription(apiStores().userWithdraw(token, money, address), new ApiCallback<String>() {
            @Override
            public void onSuccess(String model) {
                try {
                    JSONObject result = new JSONObject(model);
                    if (result.optInt("code", 0) == 1) {
                        toastShow(result.optString("msg", ""));
                        EventBus.getDefault().post(new RefreshUserInfo(isLogin()));
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
}
