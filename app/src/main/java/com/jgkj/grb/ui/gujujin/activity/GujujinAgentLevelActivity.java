package com.jgkj.grb.ui.gujujin.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;

import com.jgkj.grb.R;
import com.jgkj.grb.base.BaseActivity;
import com.jgkj.grb.glide.GlideApp;
import com.jgkj.grb.retrofit.ApiCallback;
import com.jgkj.grb.retrofit.ApiStores;
import com.jgkj.grb.ui.gujujin.bean.AgentLevelModel;
import com.jgkj.utils.token.GetTokenUtils;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * 谷聚金代理等级制度
 */
public class GujujinAgentLevelActivity extends BaseActivity {

    public static void start(Context context) {
        Intent intent = new Intent(context, GujujinAgentLevelActivity.class);
        context.startActivity(intent);
    }

    @BindView(R.id.userhead)
    CircleImageView mUserhead;
    @BindView(R.id.username)
    TextView mUsername;
    @BindView(R.id.user_daili_type)
    ImageView mUserDailiType;
    @BindView(R.id.user_daili_level)
    ImageView mUserDailiLevel;

    @BindView(R.id.benefits_received_left_0)
    TextView mBenefitsReceivedLeft0;
    @BindView(R.id.benefits_received_0)
    TextView mBenefitsReceived0;
    @BindView(R.id.benefits_received_left_1)
    TextView mBenefitsReceivedLeft1;
    @BindView(R.id.benefits_received_1)
    TextView mBenefitsReceived1;
    @BindView(R.id.benefits_received_left_2)
    TextView mBenefitsReceivedLeft2;
    @BindView(R.id.benefits_received_2)
    TextView mBenefitsReceived2;
    @BindView(R.id.benefits_received_left_3)
    TextView mBenefitsReceivedLeft3;
    @BindView(R.id.benefits_received_3)
    TextView mBenefitsReceived3;
    @BindView(R.id.benefits_received_left_4)
    TextView mBenefitsReceivedLeft4;
    @BindView(R.id.benefits_received_4)
    TextView mBenefitsReceived4;
    @BindView(R.id.benefits_received_left_5)
    TextView mBenefitsReceivedLeft5;
    @BindView(R.id.benefits_received_5)
    TextView mBenefitsReceived5;
    @BindView(R.id.benefits_received_left_6)
    TextView mBenefitsReceivedLeft6;
    @BindView(R.id.benefits_received_6)
    TextView mBenefitsReceived6;
    @BindView(R.id.benefits_received_left_7)
    TextView mBenefitsReceivedLeft7;
    @BindView(R.id.benefits_received_7)
    TextView mBenefitsReceived7;
    @BindView(R.id.benefits_received_left_8)
    TextView mBenefitsReceivedLeft8;
    @BindView(R.id.benefits_received_8)
    TextView mBenefitsReceived8;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gujujin_agent_level);

        Toolbar toolbar = initToolBar("");
        setBackgroundColor(toolbar, Color.TRANSPARENT);

        setUserInfo();
        onLazyLoad();
    }

    private void setUserInfo() {
        try {
            JSONObject userModel = new JSONObject(sharedPreferencesHelper.getSharedPreference("user", "").toString());
            GlideApp.with(mActivity)
                    .load(ApiStores.API_SERVER_URL + userModel.optString("us_head_pic", null))
                    .into(mUserhead);
            mUsername.setText(userModel.optString("us_nickname", ""));
            // us_agent  0:无代理，1，个人代理，2县代理，3市代 4 省代
            // us_agent2  0:普通；1：一星代理；2 二星代理； 3 三星代理 4 四星代理
            GlideApp.with(this)
                    .load(getUsAgent(userModel.optInt("us_agent", 0)))
                    .into(mUserDailiType);
            GlideApp.with(this)
                    .load(getUsAgent2(userModel.optInt("us_agent2", 0)))
                    .into(mUserDailiLevel);
        } catch (JSONException e) {
        }
    }

    private int getUsAgent2(int us_agent2) {
        switch (us_agent2) {
            case 0:
                return 0;
            case 1:
                return R.mipmap.ic_gujvjin_daili_level_1;
            case 2:
                return R.mipmap.ic_gujvjin_daili_level_2;
            case 3:
                return R.mipmap.ic_gujvjin_daili_level_3;
            case 4:
                return R.mipmap.ic_gujvjin_daili_level_4;
            case 5:
                return R.mipmap.ic_gujvjin_daili_level_5;
            default:
                return 0;
        }
    }

    private int getUsAgent(int us_agent) {
        switch (us_agent) {
            case 0:
                return 0;
            case 1:
                return R.mipmap.ic_gujvjin_daili_0;
            case 2:
                return R.mipmap.ic_gujvjin_daili_1;
            case 3:
                return R.mipmap.ic_gujvjin_daili_2;
            case 4:
                return R.mipmap.ic_gujvjin_daili_3;
            default:
                return 0;
        }
    }

    private void onLazyLoad() {
        showProgressDialog();
        String token = GetTokenUtils.getToken(this, ApiStores.API_SERVER_VALLEY_AGENT);
        addSubscription(apiStores().indexValleyAgent(token), new ApiCallback<AgentLevelModel>() {
            @Override
            public void onSuccess(AgentLevelModel model) {
                if (model.getCode() == 1) {
                    for (int i = 0; i < model.getData().size(); i++) {
                        showView(i, model.getData().get(i));
                    }
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

    private void showView(int position, AgentLevelModel.DataBean data) {
        switch (position) {
            case 0:
                showView(data, mBenefitsReceivedLeft0, mBenefitsReceived0);
                break;
            case 1:
                showView(data, mBenefitsReceivedLeft1, mBenefitsReceived1);
                break;
            case 2:
                showView(data, mBenefitsReceivedLeft2, mBenefitsReceived2);
                break;
            case 3:
                showView(data, mBenefitsReceivedLeft3, mBenefitsReceived3);
                break;
            case 4:
                showView(data, mBenefitsReceivedLeft4, mBenefitsReceived4);
                break;
            case 5:
                showView(data, mBenefitsReceivedLeft5, mBenefitsReceived5);
                break;
            case 6:
                showView(data, mBenefitsReceivedLeft6, mBenefitsReceived6);
                break;
            case 7:
                showView(data, mBenefitsReceivedLeft7, mBenefitsReceived7);
                break;
            case 8:
                showView(data, mBenefitsReceivedLeft8, mBenefitsReceived8);
                break;
            default:
                break;
        }
    }

    private void showView(AgentLevelModel.DataBean data, TextView mBenefitsReceivedLeft, TextView mBenefitsReceived) {
        mBenefitsReceivedLeft.setText(data.getAgent_name());
        mBenefitsReceived.setText(data.getContent());
    }
}
