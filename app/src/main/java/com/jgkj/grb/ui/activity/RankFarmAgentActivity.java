package com.jgkj.grb.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.jgkj.grb.R;
import com.jgkj.grb.base.BaseActivity;
import com.jgkj.grb.glide.GlideApp;
import com.jgkj.grb.retrofit.ApiCallback;
import com.jgkj.grb.retrofit.ApiStores;
import com.jgkj.grb.ui.mvp.personal.RankFarmAgentModel;
import com.jgkj.utils.token.GetTokenUtils;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * 个人中心：农场代理等级
 */
public class RankFarmAgentActivity extends BaseActivity {

    public static void start(Context context) {
        Intent intent = new Intent(context, RankFarmAgentActivity.class);
        context.startActivity(intent);
    }

    @BindView(R.id.userhead)
    CircleImageView mUserhead;
    @BindView(R.id.username)
    TextView mUsername;
    @BindView(R.id.user_agent_level)
    ImageView mUserAgentLevel;
    @BindView(R.id.benefits_received_left)
    TextView mBenefitsReceivedLeft;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rank_farm_agent);

        Toolbar toolbar = initToolBar("");
        setBackgroundColor(toolbar, Color.TRANSPARENT);

        initUserView();
        onLazyLoad();
    }

    private void initUserView() {
        // us_level 农场等级：0：游客，1：体验会员，2：一星代理，3：二星代理，4：三星代理，5：四星代理，6：五星代理
        try {
            String userStr = sharedPreferencesHelper.getSharedPreference("user", "").toString();
            JSONObject user = new JSONObject(userStr);
            String us_head_pic = user.getString("us_head_pic");
            if (!TextUtils.isEmpty(us_head_pic)) {
                GlideApp.with(this)
                        .load(us_head_pic.startsWith("http:") || us_head_pic.startsWith("https:")
                                ? us_head_pic.replaceAll("\\\\", "/")
                                : ApiStores.API_SERVER_URL + us_head_pic.replaceAll("\\\\", "/"))
                        .centerCrop()
                        .into(mUserhead);
            }
            String us_nickname = user.getString("us_nickname");
            mUsername.setText(TextUtils.isEmpty(us_nickname) || TextUtils.equals("null", us_nickname) ? "" : us_nickname);
            int us_level = user.getInt("us_level");
            if (us_level <= 0) {
                mUserAgentLevel.setVisibility(View.GONE);
            } else {
                mUserAgentLevel.setVisibility(View.VISIBLE);
                GlideApp.with(this)
                        .load(getUserAgentLevelRes(us_level))
                        .into(mUserAgentLevel);
            }
        } catch (JSONException e) {
        }
    }

    private Integer getUserAgentLevelRes(int us_level) {
        switch (us_level) {
            case 1:
                return R.mipmap.ic_user_agent_level_0;
            case 2:
                return R.mipmap.ic_user_agent_level_1;
            case 3:
                return R.mipmap.ic_user_agent_level_2;
            case 4:
                return R.mipmap.ic_user_agent_level_3;
            case 5:
                return R.mipmap.ic_user_agent_level_4;
            case 6:
                return R.mipmap.ic_user_agent_level_5;
            default:
                return null;
        }
    }

    private void onLazyLoad() {
        showProgressDialog();
        String token = GetTokenUtils.getToken(this, ApiStores.API_SERVER_INDEX_AGENCYLEVEL);
        addSubscription(apiStores().indexAgencylevel(token), new ApiCallback<RankFarmAgentModel>() {
            @Override
            public void onSuccess(RankFarmAgentModel model) {
                if (model.getCode() == 1) {
                    for (int i = 0; i < model.getData().size(); i++) {
                        RankFarmAgentModel.DataBean dataBean = model.getData().get(i);
                        String benefitsReceived = "";
                        switch (i) {
                            case 0:
                                mBenefitsReceivedLeft.setText(dataBean.getName());
                                break;
                            case 1:
                                mBenefitsReceivedLeft0.setText(dataBean.getName());
                                for (int j = 0; j < dataBean.getList().size(); j++) {
                                    benefitsReceived += dataBean.getList().get(j) + "\n";
                                }
                                mBenefitsReceived0.setText(benefitsReceived.trim());
                                break;
                            case 2:
                                mBenefitsReceivedLeft1.setText(dataBean.getName());
                                for (int j = 0; j < dataBean.getList().size(); j++) {
                                    benefitsReceived += dataBean.getList().get(j) + "\n";
                                }
                                mBenefitsReceived1.setText(benefitsReceived.trim());
                                break;
                            case 3:
                                mBenefitsReceivedLeft2.setText(dataBean.getName());
                                for (int j = 0; j < dataBean.getList().size(); j++) {
                                    benefitsReceived += dataBean.getList().get(j) + "\n";
                                }
                                mBenefitsReceived2.setText(benefitsReceived.trim());
                                break;
                            case 4:
                                mBenefitsReceivedLeft3.setText(dataBean.getName());
                                for (int j = 0; j < dataBean.getList().size(); j++) {
                                    benefitsReceived += dataBean.getList().get(j) + "\n";
                                }
                                mBenefitsReceived3.setText(benefitsReceived.trim());
                                break;
                            case 5:
                                mBenefitsReceivedLeft4.setText(dataBean.getName());
                                for (int j = 0; j < dataBean.getList().size(); j++) {
                                    benefitsReceived += dataBean.getList().get(j) + "\n";
                                }
                                mBenefitsReceived4.setText(benefitsReceived.trim());
                                break;
                            case 6:
                                mBenefitsReceivedLeft5.setText(dataBean.getName());
                                for (int j = 0; j < dataBean.getList().size(); j++) {
                                    benefitsReceived += dataBean.getList().get(j) + "\n";
                                }
                                mBenefitsReceived5.setText(benefitsReceived.trim());
                                break;
                            default:
                                break;
                        }
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
}
