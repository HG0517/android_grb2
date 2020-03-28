package com.jgkj.grb.ui.gujujin.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.jgkj.grb.BuildConfig;
import com.jgkj.grb.R;
import com.jgkj.grb.base.BaseActivity;
import com.jgkj.grb.glide.GlideApp;
import com.jgkj.grb.onclick.RxView;
import com.jgkj.grb.retrofit.ApiStores;
import com.jgkj.grb.ui.activity.ServiceHelpWebViewActivity;
import com.jgkj.grb.ui.gujujin.adapter.GujujinAgentIndexAdapter;
import com.jgkj.grb.ui.gujujin.bean.GujujinAgentBean;
import com.jgkj.grb.ui.gujujin.dialog.GujujinAgentAgreeAbstractDialog;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * 谷聚金：代理主页
 */
public class GujujinAgentActivity extends BaseActivity {

    public static void start(Context context) {
        Intent intent = new Intent(context, GujujinAgentActivity.class);
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
    @BindView(R.id.user_daili_level_)
    ImageView mUserDailiLevel_;
    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;
    GujujinAgentIndexAdapter mAdapter;
    List<GujujinAgentBean> mList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gujujin_agent);

        Toolbar toolbar = initToolBar("");
        setBackgroundColor(toolbar, Color.TRANSPARENT);

        RxView.setOnClickListeners(this, mUserDailiLevel_);

        setUserInfo();
        initRecyclerView();
        onLazyLoad();
    }

    private void showGujujinAgentAgreeAbstractDialog() {
        GujujinAgentAgreeAbstractDialog dialog = GujujinAgentAgreeAbstractDialog.newInstance();
        dialog.setCancelable(false);
        dialog.showDialog(getSupportFragmentManager());
        dialog.setOnDialogListener(new GujujinAgentAgreeAbstractDialog.OnDialogListener() {
            @Override
            public void onCancel() {
                onBackPressed();
            }

            @Override
            public void onSure() {
            }
        });
    }

    private void initRecyclerView() {
        mRecyclerView.setNestedScrollingEnabled(false);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new GujujinAgentIndexAdapter(this, mList);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener((view, position) -> {
            switch (mList.get(position).getType()) {
                case 0: // 谷聚金介绍
                    ServiceHelpWebViewActivity.start(mActivity, "24");
                    break;
                case 1: // 成为代理
                    if (BuildConfig.DEBUG)
                        GujujinBecomeAgentActivity.startActivityForResult(mActivity);
                    else
                        showBecomeAgent();
                    break;
                case 2: // 进货
                    showPurchase();
                    break;
                case 3: // 订单，2 区/县代理、1 个人代理
                    showOrder();
                    break;
                case 4: // 发货，2 区/县代理可查看订单
                    showDeliverGoods();
                    break;
                case 5: // 奖金明细
                    showIncomeDetail();
                    break;
            }
        });
    }

    private void showIncomeDetail() {
        try {
            JSONObject userModel = new JSONObject(sharedPreferencesHelper.getSharedPreference("user", "").toString());
            int us_agent = userModel.optInt("us_agent", 0);
            if (us_agent <= 0) {
                toastShow(R.string.personal_daili_un);
            } else {
                GujujinIncomeDetailActivity.start(mActivity);
            }
        } catch (JSONException e) {
        }
    }

    private void showDeliverGoods() {
        try {
            JSONObject userModel = new JSONObject(sharedPreferencesHelper.getSharedPreference("user", "").toString());
            int us_agent = userModel.optInt("us_agent", 0);
            if (us_agent <= 0) {
                toastShow(R.string.personal_daili_un);
            } else if (us_agent == 2) {
                GujujinDeliverGoodsActivity.start(mActivity);
            } else {
                toastShow(R.string.personal_daili_3);
            }
        } catch (JSONException e) {
        }
    }

    private void showOrder() {
        try {
            JSONObject userModel = new JSONObject(sharedPreferencesHelper.getSharedPreference("user", "").toString());
            int us_agent = userModel.optInt("us_agent", 0);
            if (us_agent <= 0) {
                toastShow(R.string.personal_daili_un);
            } else if (us_agent > 2) {
                toastShow(R.string.personal_daili_12);
            } else {
                GujujinOrderActivity.start(mActivity, us_agent);
            }
        } catch (JSONException e) {
        }
    }

    private void showPurchase() {
        try {
            JSONObject userModel = new JSONObject(sharedPreferencesHelper.getSharedPreference("user", "").toString());
            int us_agent = userModel.optInt("us_agent", 0);
            if (us_agent <= 0) {
                toastShow(R.string.personal_daili_un);
            } else if (us_agent > 2) {
                toastShow(R.string.personal_daili_12);
            } else {
                GujujinPurchaseActivity.start(mActivity);
            }
        } catch (JSONException e) {
        }
    }

    private void showBecomeAgent() {
        try {
            JSONObject userModel = new JSONObject(sharedPreferencesHelper.getSharedPreference("user", "").toString());
            int us_agent = userModel.optInt("us_agent", 0);
            if (us_agent > 0) {
                if (us_agent == 1)
                    toastShow(R.string.personal_daili_level_1);
                else if (us_agent == 2)
                    toastShow(R.string.personal_daili_level_2);
                else if (us_agent == 3)
                    toastShow(R.string.personal_daili_level_3);
                else if (us_agent == 4)
                    toastShow(R.string.personal_daili_level_4);
            } else {
                GujujinBecomeAgentActivity.startActivityForResult(mActivity);
            }
        } catch (JSONException e) {
        }
    }

    @Override
    public void onClick(Object o) {
        View v = (View) o;
        switch (v.getId()) {
            case R.id.user_daili_level_: // 谷聚金等级制度
                GujujinAgentLevelActivity.start(this);
                break;
            default:
                break;
        }
    }

    private void onLazyLoad() {
        mList.add(new GujujinAgentBean(0, R.mipmap.ic_gujvjin_daili_pic_0, "", getString(R.string.gujvjin_index_00), getString(R.string.gujvjin_index_01)));
        mList.add(new GujujinAgentBean(1, R.mipmap.ic_gujvjin_daili_pic_1, "", getString(R.string.gujvjin_index_10), getString(R.string.gujvjin_index_11)));
        mList.add(new GujujinAgentBean(2, R.mipmap.ic_gujvjin_daili_pic_2, "", getString(R.string.gujvjin_index_20), getString(R.string.gujvjin_index_21)));
        mList.add(new GujujinAgentBean(3, R.mipmap.ic_gujvjin_daili_pic_3, "", getString(R.string.gujvjin_index_30), getString(R.string.gujvjin_index_31)));
        mList.add(new GujujinAgentBean(4, R.mipmap.ic_gujvjin_daili_pic_4, "", getString(R.string.gujvjin_index_40), getString(R.string.gujvjin_index_41)));
        mList.add(new GujujinAgentBean(5, R.mipmap.ic_gujvjin_daili_pic_5, "", getString(R.string.gujvjin_index_50), getString(R.string.gujvjin_index_51)));
        mAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == 10013) {
                setUserInfo();
            }
        }
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
                showGujujinAgentAgreeAbstractDialog();
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

}
