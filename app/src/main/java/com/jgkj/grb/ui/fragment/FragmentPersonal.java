package com.jgkj.grb.ui.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jgkj.grb.R;
import com.jgkj.grb.base.BaseFragment;
import com.jgkj.grb.glide.GlideApp;
import com.jgkj.grb.itemdecoration.GridDividerItemDecoration;
import com.jgkj.grb.onclick.RxView;
import com.jgkj.grb.push.JExampleUtil;
import com.jgkj.grb.retrofit.ApiCallback;
import com.jgkj.grb.retrofit.ApiStores;
import com.jgkj.grb.ui.activity.AddressManagementActivity;
import com.jgkj.grb.ui.activity.CloudWarehouseActivity;
import com.jgkj.grb.ui.activity.MessageManagementActivity;
import com.jgkj.grb.ui.activity.OrderManagementActivity;
import com.jgkj.grb.ui.activity.OrderManagementRefundAfterSaleActivity;
import com.jgkj.grb.ui.activity.PersonalBalanceActivity;
import com.jgkj.grb.ui.activity.PersonalCollectActivity;
import com.jgkj.grb.ui.activity.PersonalCouponActivity;
import com.jgkj.grb.ui.activity.PersonalEvaluationActivity;
import com.jgkj.grb.ui.activity.PersonalGRBActivity;
import com.jgkj.grb.ui.activity.PersonalPowderActivity;
import com.jgkj.grb.ui.activity.PersonalPromotionActivity;
import com.jgkj.grb.ui.activity.PersonalRechargeActivity;
import com.jgkj.grb.ui.activity.PersonalTracksActivity;
import com.jgkj.grb.ui.activity.PersonalTransferFriendsActivity;
import com.jgkj.grb.ui.activity.ProductDetailsActivity;
import com.jgkj.grb.ui.activity.RankFarmAgentActivity;
import com.jgkj.grb.ui.activity.ServiceAndHelpActivity;
import com.jgkj.grb.ui.activity.SettingActivity;
import com.jgkj.grb.ui.adapter.IndexChildContentAdapter;
import com.jgkj.grb.ui.mvp.index.IndexCatesChildPageModel;
import com.jgkj.grb.ui.mvp.personal.PersonalHeadModel;
import com.jgkj.grb.utils.Logger;
import com.jgkj.utils.token.GetTokenUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import butterknife.BindView;
import cn.bingoogolapple.badgeview.BGABadgeFrameLayout;
import cn.bingoogolapple.badgeview.BGABadgeViewHelper;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * 我的
 * Created by brightpoplar@163.com on 2019/7/27.
 */
public class FragmentPersonal extends BaseFragment {

    public static FragmentPersonal newInstance() {
        return new FragmentPersonal();
    }

    @BindView(R.id.topPanelMain)
    FrameLayout mTopPanelMain;
    @BindView(R.id.actionSetting)
    FrameLayout actionSetting;
    BGABadgeFrameLayout actionMsg;

    @BindView(R.id.userhead)
    CircleImageView mPersonalHead;
    @BindView(R.id.userout)
    ImageView mPersonalOut;
    @BindView(R.id.username)
    TextView mPersonalName;
    @BindView(R.id.personal_top_level)
    ImageView mPersonalAgentLevel0;
    @BindView(R.id.user_agent_level)
    ImageView mPersonalAgentLevel1;
    @BindView(R.id.user_identity)
    ImageView mPersonalIdentity;

    @BindView(R.id.personal_order_all)
    TextView mPersonalOrderAll;
    @BindView(R.id.personal_order_obligations)
    FrameLayout mPersonalOrderObligations;
    @BindView(R.id.personal_order_tobe_shipped)
    FrameLayout mPersonalOrderTobeShipped;
    @BindView(R.id.personal_order_tobe_received)
    FrameLayout mPersonalOrderTobeReceived;
    @BindView(R.id.personal_order_tobe_evaluated)
    FrameLayout mPersonalOrderTobeEvaluated;
    @BindView(R.id.personal_order_refund_after_sale)
    FrameLayout mPersonalOrderRefundAfterSale;

    @BindView(R.id.personal_balance)
    FrameLayout mPersonalBalance;
    @BindView(R.id.personal_balance_tv)
    TextView mPersonalBalanceTv;
    @BindView(R.id.personal_gra)
    FrameLayout mPersonalGRA;
    @BindView(R.id.personal_gra_tv)
    TextView mPersonalGRATv;
    @BindView(R.id.personal_grb)
    FrameLayout mPersonalGRB;
    @BindView(R.id.personal_grb_tv)
    TextView mPersonalGRBTv;
    @BindView(R.id.personal_grc)
    FrameLayout mPersonalGRC;
    @BindView(R.id.personal_grc_tv)
    TextView mPersonalGRCTv;
    @BindView(R.id.personal_coupon)
    FrameLayout mPersonalCoupon;
    @BindView(R.id.personal_coupon_tv)
    TextView mPersonalCouponTv;
    @BindView(R.id.personal_assets_recharge)
    FrameLayout mPersonalAssetsRecharge;
    @BindView(R.id.personal_assets_transfer)
    FrameLayout mPersonalAssetsTransfer;

    @BindView(R.id.personal_service_cloud_warehouse)
    FrameLayout mPersonalServiceCloudWarehouse;
    @BindView(R.id.personal_service_my_collect)
    FrameLayout mPersonalServiceMyConcern;
    @BindView(R.id.personal_service_my_tracks)
    FrameLayout mPersonalServiceMyTracks;
    @BindView(R.id.personal_service_my_evaluation)
    FrameLayout mPersonalServiceMyEvaluation;
    @BindView(R.id.personal_service_my_precious_powder)
    FrameLayout mPersonalServiceMyPreciousPowder;
    @BindView(R.id.personal_service_address_management)
    FrameLayout mPersonalServiceAddressManagement;
    @BindView(R.id.personal_service_my_promotion)
    FrameLayout mPersonalServiceMyPromotion;
    @BindView(R.id.personal_service_help)
    FrameLayout mPersonalServiceHelp;
    @BindView(R.id.personal_service_quantbroker)
    FrameLayout mPersonalServiceQuantBroker;

    @BindView(R.id.bottomPanel)
    LinearLayout mBottomPanel;
    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;
    List<IndexCatesChildPageModel.DataBean> mList = new ArrayList<>();
    IndexChildContentAdapter mContentAdapter;

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_personal;
    }

    @Override
    public void init(Bundle savedInstanceState) {
        mTopPanelMain.setPadding(0, getStatusBarHeight(), 0, 0);
        actionMsg = getRootView().findViewById(R.id.actionMsg);
        actionMsg.getBadgeViewHelper().setBadgeGravity(BGABadgeViewHelper.BadgeGravity.RightTop);
        actionMsg.getBadgeViewHelper().setBadgeVerticalMarginDp(2);
        actionMsg.getBadgeViewHelper().setBadgeHorizontalMarginDp(2);

        RxView.setOnClickListeners(this, actionSetting, actionMsg, mPersonalHead, mPersonalOut, mPersonalAgentLevel0, mPersonalAgentLevel1,
                mPersonalOrderAll, mPersonalOrderObligations, mPersonalOrderTobeShipped,
                mPersonalOrderTobeReceived, mPersonalOrderTobeEvaluated, mPersonalOrderRefundAfterSale,
                mPersonalBalance, mPersonalGRA, mPersonalGRB, mPersonalGRC, mPersonalCoupon, mPersonalAssetsRecharge, mPersonalAssetsTransfer,
                mPersonalServiceCloudWarehouse, mPersonalServiceMyConcern, mPersonalServiceMyTracks,
                mPersonalServiceMyEvaluation, mPersonalServiceMyPreciousPowder, mPersonalServiceAddressManagement,
                mPersonalServiceMyPromotion, mPersonalServiceHelp, mPersonalServiceQuantBroker);

        initRecyclerView();
        initUserInfo(isLogin());
    }

    private void initRecyclerView() {
        mRecyclerView.setNestedScrollingEnabled(false);
        mRecyclerView.setLayoutManager(new GridLayoutManager(mActivity, 2, GridLayoutManager.VERTICAL, false));
        mRecyclerView.addItemDecoration(new GridDividerItemDecoration(dp2px(7), Color.TRANSPARENT));
        mContentAdapter = new IndexChildContentAdapter(mActivity, mList);
        mRecyclerView.setAdapter(mContentAdapter);
        mContentAdapter.setOnItemClickListener((view, position) -> {
            ProductDetailsActivity.start(mActivity, String.valueOf(mList.get(position).getId()));
        });
    }

    // 推送消息
    public void initPushMsg(Map<String, Integer> map) {
        if (null == map || map.isEmpty()) {
            actionMsg.getBadgeViewHelper().hiddenBadge();
        } else {
            int sunMsg = 0;
            Set<Map.Entry<String, Integer>> entrySet = map.entrySet();
            for (Map.Entry entry : entrySet) {
                if (TextUtils.equals("4", entry.getKey().toString())) continue;
                sunMsg += Integer.valueOf(entry.getValue().toString());
            }

            if (sunMsg > 0) {
                actionMsg.getBadgeViewHelper().showTextBadge(String.valueOf(sunMsg));
            } else {
                actionMsg.getBadgeViewHelper().hiddenBadge();
            }
        }
    }

    @Override
    protected void onLazyLoad() {
    }

    @Override
    public void onClick(Object o) {
        View v = (View) o;
        switch (v.getId()) {
            case R.id.actionMsg:
                if (isLogin())
                    MessageManagementActivity.start(mActivity);
                break;
            case R.id.actionSetting:
            case R.id.userhead:
            case R.id.userout:
                if (isLogin())
                    SettingActivity.start(mActivity);
                break;
            case R.id.personal_top_level:
            case R.id.user_agent_level:
                RankFarmAgentActivity.start(mActivity);
                break;
            case R.id.personal_order_all:
            case R.id.personal_order_obligations:
            case R.id.personal_order_tobe_shipped:
            case R.id.personal_order_tobe_received:
            case R.id.personal_order_tobe_evaluated:
                if (isLogin()) {
                    OrderManagementActivity.start(mActivity);
                    /*if (BuildConfig.DEBUG) {
                        OrderManagementActivity.start(mActivity);
                    } else {
                        // is_realname  实名情况：0:未认证  1认证中 2已认证
                        int is_realname = isRealname();
                        if (is_realname == 2) {
                            OrderManagementActivity.start(mActivity);
                        } else if (is_realname == 1) {
                            ApplyResultActivity.start(mActivity, 2);
                        } else if (is_realname == 3) {
                            showUnrealNameDialog(getString(R.string.real_name_rejected));
                        } else {
                            showUnrealNameDialog();
                        }
                    }*/
                }
                break;
            case R.id.personal_order_refund_after_sale:
                if (isLogin()) {
                    OrderManagementRefundAfterSaleActivity.start(mActivity);
                    /*if (BuildConfig.DEBUG) {
                        OrderManagementRefundAfterSaleActivity.start(mActivity);
                    } else {
                        // is_realname  实名情况：0:未认证  1认证中 2已认证
                        int is_realname = isRealname();
                        if (is_realname == 2) {
                            OrderManagementRefundAfterSaleActivity.start(mActivity);
                        } else if (is_realname == 1) {
                            ApplyResultActivity.start(mActivity, 2);
                        } else if (is_realname == 3) {
                            showUnrealNameDialog(getString(R.string.real_name_rejected));
                        } else {
                            showUnrealNameDialog();
                        }
                    }*/
                }
                break;
            case R.id.personal_balance:
                if (isLogin())
                    PersonalBalanceActivity.start(mActivity);
                break;
            case R.id.personal_gra:
            case R.id.personal_grb:
            case R.id.personal_grc:
                if (isLogin())
                    PersonalGRBActivity.start(mActivity);
                break;
            case R.id.personal_coupon:
                if (isLogin())
                    PersonalCouponActivity.start(mActivity);
                break;
            case R.id.personal_assets_recharge:
                if (isLogin())
                    PersonalRechargeActivity.start(mActivity);
                break;
            case R.id.personal_assets_transfer:
                if (isLogin())
                    PersonalTransferFriendsActivity.start(mActivity);
                break;
            case R.id.personal_service_cloud_warehouse:
                if (isLogin())
                    CloudWarehouseActivity.start(mActivity);
                break;
            case R.id.personal_service_my_collect:
                if (isLogin())
                    PersonalCollectActivity.start(mActivity);
                break;
            case R.id.personal_service_my_tracks:
                if (isLogin())
                    PersonalTracksActivity.start(mActivity);
                break;
            case R.id.personal_service_my_evaluation:
                if (isLogin())
                    PersonalEvaluationActivity.start(mActivity);
                break;
            case R.id.personal_service_my_precious_powder:
                if (isLogin())
                    PersonalPowderActivity.start(mActivity);
                break;
            case R.id.personal_service_address_management:
                if (isLogin())
                    AddressManagementActivity.start(mActivity);
                break;
            case R.id.personal_service_my_promotion:
                if (isLogin())
                    PersonalPromotionActivity.start(mActivity);
                break;
            case R.id.personal_service_help:
                ServiceAndHelpActivity.start(mActivity);
                break;
            case R.id.personal_service_quantbroker:
                Uri uri = Uri.parse(ApiStores.API_SERVER_QUANTBROKER);
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
                break;
            default:
                break;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_setting: // 设置
                if (isLogin())
                    SettingActivity.start(mActivity);
                break;
            case R.id.menu_message: // 消息
                if (isLogin())
                    MessageManagementActivity.start(mActivity);
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        return true;
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        //getActivity().getMenuInflater().inflate(R.menu.menu_personal, menu);
    }

    public void initUserInfo(boolean isLogin) {
        if (isLogin) {
            userMyHead();
        } else {
            userLogout();
        }

        Map<String, Integer> extra = new Gson().fromJson(sharedPreferencesConfig.getSharedPreference(JExampleUtil.KEY_CUSTOM_EXTRA, "").toString(),
                new TypeToken<Map<String, Integer>>() {
                }.getType());
        initPushMsg(extra);
    }

    private void userLogout() {
        GlideApp.with(mActivity)
                .load(R.mipmap.ic_default_head)
                .into(mPersonalHead);
        mPersonalName.setText("");
        mPersonalOut.setVisibility(View.GONE);
        mPersonalAgentLevel1.setVisibility(View.GONE);
        mPersonalBalanceTv.setText(String.format(getString(R.string.total_top_text), "0"));
        mPersonalGRATv.setText("0");
        mPersonalGRBTv.setText("0");
        mPersonalGRCTv.setText("0");
        mPersonalCouponTv.setText(String.format(getString(R.string.personal_assets_31), String.valueOf(0)));
        mList.clear();
        mContentAdapter.notifyDataSetChanged();
    }

    private void userMyHead() {
        String token = GetTokenUtils.getToken(mActivity, ApiStores.API_SERVER_USER_MYHEAD);
        addSubscription(apiStores().userMyHead(token), new ApiCallback<PersonalHeadModel>() {
            @Override
            public void onSuccess(PersonalHeadModel model) {
                if (model.getCode() == 1) {
                    // hot
                    mList.clear();
                    mList.addAll(model.getData().getHots());
                    mContentAdapter.notifyDataSetChanged();
                    // user
                    GlideApp.with(mActivity)
                            .load(ApiStores.API_SERVER_URL + model.getData().getUs_head_pic())
                            .into(mPersonalHead);
                    mPersonalName.setText(model.getData().getUs_nickname());
                    if (model.getData().getLevel() <= 0) {
                        mPersonalAgentLevel1.setVisibility(View.GONE);
                    } else {
                        mPersonalAgentLevel1.setVisibility(View.VISIBLE);
                        GlideApp.with(mActivity)
                                .load(getUserAgentLevelRes(model.getData().getLevel()))
                                .into(mPersonalAgentLevel1);
                    }
                    // cash 余额；grb_integral    GRB ; grb  GRC ; coupon_num  优惠券数量
                    mPersonalOut.setVisibility(model.getData().getOut() == 1 ? View.VISIBLE : View.GONE);
                    mPersonalBalanceTv.setText(String.format(getString(R.string.total_top_text), model.getData().getCash()));
                    mPersonalGRATv.setText(model.getData().getGra());
                    mPersonalGRBTv.setText(model.getData().getGrb_cash());
                    mPersonalGRCTv.setText(model.getData().getGrb_integral());
                    mPersonalCouponTv.setText(String.format(getString(R.string.personal_assets_31), String.valueOf(model.getData().getCoupon_num())));

                    try {
                        JSONObject userModel = new JSONObject(sharedPreferencesHelper.getSharedPreference("user", "").toString());
                        userModel.put("us_head_pic", model.getData().getUs_head_pic());
                        userModel.put("us_nickname", model.getData().getUs_nickname());
                        userModel.put("us_level", model.getData().getLevel());
                        userModel.put("is_realname", model.getData().getIs_realname());
                        userModel.put("us_agent", model.getData().getUs_agent());
                        userModel.put("us_agent2", model.getData().getUs_agent2());
                        userModel.put("out", model.getData().getOut());
                        userModel.put("cash", model.getData().getCash());
                        userModel.put("grb_integral", model.getData().getGrb_integral());
                        userModel.put("grb_cash", model.getData().getGrb_cash());
                        userModel.put("coupon_num", model.getData().getCoupon_num());
                        sharedPreferencesHelper.putApply("user", userModel.toString());
                    } catch (JSONException e) {
                    }
                } else {
                    userLogout();
                }
            }

            @Override
            public void onFailure(String msg) {
                setUserInfo();
            }

            @Override
            public void onFinish() {
            }
        });
    }

    private void setUserInfo() {
        try {
            JSONObject userModel = new JSONObject(sharedPreferencesHelper.getSharedPreference("user", "").toString());
            GlideApp.with(mActivity)
                    .load(ApiStores.API_SERVER_URL + userModel.optString("us_head_pic", null))
                    .into(mPersonalHead);
            mPersonalName.setText(userModel.optString("us_nickname", ""));
            // us_level 农场等级：0：游客，1：体验会员，2：一星代理，3：二星代理，4：三星代理，5：四星代理，6：五星代理
            int us_level = userModel.optInt("us_level", 0);
            if (us_level <= 0) {
                mPersonalAgentLevel1.setVisibility(View.GONE);
            } else {
                mPersonalAgentLevel1.setVisibility(View.VISIBLE);
                GlideApp.with(this)
                        .load(getUserAgentLevelRes(us_level))
                        .into(mPersonalAgentLevel1);
            }
            // out ，1：出局，0：未出局
            int out = userModel.optInt("out", 0);
            Logger.i("TAG_OUT", "out = " + out);
            mPersonalOut.setVisibility(out == 1 ? View.VISIBLE : View.GONE);
            mPersonalBalanceTv.setText(String.format(getString(R.string.total_top_text), userModel.optString("cash", "0")));
            mPersonalGRATv.setText(userModel.optString("gra", "0"));
            mPersonalGRBTv.setText(userModel.optString("grb_cash", "0"));
            mPersonalGRCTv.setText(userModel.optString("grb_integral", "0"));
            mPersonalCouponTv.setText(String.format(getString(R.string.personal_assets_31), String.valueOf(userModel.optInt("coupon_num", 0))));
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

}
