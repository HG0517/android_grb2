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
import com.jgkj.grb.eventbus.RefreshUserInfo;
import com.jgkj.grb.onclick.RxView;
import com.jgkj.grb.retrofit.ApiCallback;
import com.jgkj.grb.retrofit.ApiStores;
import com.jgkj.grb.utils.Logger;
import com.jgkj.grb.view.inputfilter.MaxEditTextWatcher;
import com.jgkj.utils.token.GetTokenUtils;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;

/**
 * 个人资料：修改昵称
 */
public class SettingNickActivity extends BaseActivity {

    public static void startActivityForResult(Activity activity, String oldNick) {
        Bundle bundle = new Bundle();
        bundle.putString("oldNick", oldNick);
        Intent intent = new Intent(activity, SettingNickActivity.class);
        intent.putExtras(bundle);
        activity.startActivityForResult(intent, 10000);
    }

    @BindView(R.id.nick_input)
    EditText mNickInput;
    @BindView(R.id.nick_input_submission)
    CardView mNickInputSubmission;

    String oldNick = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_personal_nick);

        Toolbar toolbar = initToolBar("");
        toolbar.setTitle(R.string.activity_title_setting_personal_nick);

        RxView.setOnClickListeners(this, mNickInputSubmission);

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        if (null != extras) {
            oldNick = extras.getString("oldNick", "");
        }
        mNickInput.addTextChangedListener(
                new MaxEditTextWatcher(this,
                        MaxEditTextWatcher.CHINESE_TWO, 16,
                        mNickInput, null,
                        editable -> {
                            Logger.i("TAG_TextChanged", editable.toString());
                        })
        );
        mNickInput.setText(oldNick);
    }

    @Override
    public void onClick(Object o) {
        View v = (View) o;
        switch (v.getId()) {
            case R.id.nick_input_submission:
                if (TextUtils.isEmpty(mNickInput.getText().toString().trim())) {
                    toastShow(R.string.nick_inpute_hint);
                    return;
                }
                userChangeNick(mNickInput.getText().toString().trim());
                break;
            default:
                break;
        }
    }

    private void userChangeNick(String nickname) {
        showProgressDialog();
        String token = GetTokenUtils.getToken(this, ApiStores.API_SERVER_USER_CHANGENICK);
        addSubscription(apiStores().userChangeNick(token, nickname), new ApiCallback<String>() {
            @Override
            public void onSuccess(String model) {
                try {
                    JSONObject result = new JSONObject(model);
                    if (result.optInt("code", 0) == 1) {
                        toastShow(result.optString("msg", ""));

                        JSONObject userModel = new JSONObject(sharedPreferencesHelper.getSharedPreference("user", "").toString());
                        userModel.put("us_nickname", nickname);
                        sharedPreferencesHelper.putApply("user", userModel.toString());

                        Intent intent = getIntent();
                        Bundle extras = intent.getExtras();
                        if (null == extras) {
                            extras = new Bundle();
                        }
                        String newNick = mNickInput.getText().toString().trim();
                        extras.putString("newNick", TextUtils.equals(newNick, oldNick) ? "" : newNick);
                        intent.putExtras(extras);
                        setResult(Activity.RESULT_OK, intent);
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
