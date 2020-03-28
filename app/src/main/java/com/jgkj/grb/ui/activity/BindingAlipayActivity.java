package com.jgkj.grb.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

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
 * 绑定支付宝
 */
public class BindingAlipayActivity extends BaseActivity {

    public static void startActivityForResult(Activity activity) {
        Intent intent = new Intent(activity, BindingAlipayActivity.class);
        activity.startActivityForResult(intent, 10008);
    }

    @BindView(R.id.binding_input_et)
    EditText mBindingInputEt;
    @BindView(R.id.binding_sure)
    CardView mBindingSure;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_binding_alipay);

        Toolbar toolbar = initToolBar(getString(R.string.activity_title_binding_alipay));

        RxView.setOnClickListeners(this, mBindingSure);
    }

    @Override
    public void onClick(Object o) {
        View v = (View) o;
        switch (v.getId()) {
            case R.id.binding_sure:
                if (TextUtils.isEmpty(mBindingInputEt.getText().toString().trim())) {
                    toastShow(R.string.binding_alipay_tip_input);
                    return;
                }
                userBindAlipay();
                break;
            default:
                break;
        }
    }

    private void userBindAlipay() {
        showProgressDialog();
        String token = GetTokenUtils.getToken(this, ApiStores.API_SERVER_USER_BINDALIPAY);
        addSubscription(apiStores().userBindAlipay(token, mBindingInputEt.getText().toString().trim()), new ApiCallback<String>() {
            @Override
            public void onSuccess(String model) {
                try {
                    JSONObject result = new JSONObject(model);
                    if (result.getInt("code") == 1) {
                        toastShow(result.getString("msg"));

                        String userStr = sharedPreferencesHelper.getSharedPreference("user", "").toString();
                        JSONObject user = new JSONObject(userStr);
                        user.put("ali_account", mBindingInputEt.getText().toString().trim());
                        sharedPreferencesHelper.putApply("user", user.toString());

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
}
