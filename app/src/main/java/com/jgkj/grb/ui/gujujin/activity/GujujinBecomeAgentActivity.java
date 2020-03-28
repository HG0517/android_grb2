package com.jgkj.grb.ui.gujujin.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.jgkj.grb.R;
import com.jgkj.grb.base.BaseActivity;
import com.jgkj.grb.glide.GlideApp;
import com.jgkj.grb.onclick.RxView;
import com.jgkj.grb.retrofit.ApiCallback;
import com.jgkj.grb.retrofit.ApiStores;
import com.jgkj.grb.ui.gujujin.dialog.GujujinAgentInviterDialog;
import com.jgkj.utils.token.GetTokenUtils;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * 谷聚金：成为代理商
 */
public class GujujinBecomeAgentActivity extends BaseActivity {

    public static void startActivityForResult(Activity context) {
        Intent intent = new Intent(context, GujujinBecomeAgentActivity.class);
        context.startActivityForResult(intent, 10013);
    }

    @BindView(R.id.userhead)
    CircleImageView mUserhead;
    @BindView(R.id.username)
    TextView mUsername;
    @BindView(R.id.userphone)
    EditText mUserphone;
    @BindView(R.id.submission)
    CardView mSubmission;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gujujin_become_agent);
        Toolbar toolbar = initToolBar("");
        toolbar.setTitle(R.string.activity_title_gujujin_become_agent);

        RxView.setOnClickListeners(this, mSubmission);

        setUserInfo();
    }

    private void setUserInfo() {
        try {
            JSONObject userModel = new JSONObject(sharedPreferencesHelper.getSharedPreference("user", "").toString());
            GlideApp.with(mActivity)
                    .load(ApiStores.API_SERVER_URL + userModel.optString("us_head_pic", null))
                    .into(mUserhead);
            mUsername.setText(userModel.optString("us_nickname", ""));
        } catch (JSONException e) {
        }
    }

    @Override
    public void onClick(Object o) {
        View v = (View) o;
        switch (v.getId()) {
            case R.id.submission:
                if (TextUtils.isEmpty(mUserphone.getText().toString().trim())) {
                    toastShow(R.string.gujujin_become_agent_phone_tip);
                    return;
                }
                getGujujinAgentInviter(mUserphone.getText().toString().trim());
                break;
            default:
                break;
        }
    }

    private void getGujujinAgentInviter(String phone) {
        showProgressDialog();
        String token = GetTokenUtils.getToken(this, ApiStores.API_SERVER_VALLEY_GETNAME);
        addSubscription(apiStores().indexValleyGetName(token, phone), new ApiCallback<String>() {
            @Override
            public void onSuccess(String model) {
                try {
                    JSONObject result = new JSONObject(model);
                    if (result.optInt("code", 0) == 1) {
                        showGujujinAgentInviter(result.optJSONObject("data").optString("us_nickname", getString(R.string.gujujin_purchase_settlement_deliverer_headquarters)));
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

    private void showGujujinAgentInviter(String name) {
        GujujinAgentInviterDialog dialog = GujujinAgentInviterDialog.newInstance(name);
        dialog.setCancelable(false);
        dialog.showDialog(getSupportFragmentManager());
        dialog.setOnDialogListener(new GujujinAgentInviterDialog.OnDialogListener() {
            @Override
            public void onCancel() {
                onBackPressed();
            }

            @Override
            public void onSure() {
                GujujinBecomeAgentSelectionLevelActivity.startActivityForResult(mActivity);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == 10013) {
                setResult(Activity.RESULT_OK);
                onBackPressed();
            }
        }
    }
}
