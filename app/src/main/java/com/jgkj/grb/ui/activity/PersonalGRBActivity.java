package com.jgkj.grb.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.jgkj.grb.R;
import com.jgkj.grb.base.BaseActivity;
import com.jgkj.grb.eventbus.RefreshUserInfo;
import com.jgkj.grb.onclick.RxView;
import com.jgkj.grb.retrofit.ApiCallback;
import com.jgkj.grb.retrofit.ApiStores;
import com.jgkj.grb.ui.mvp.personal.PersonalGRBModel;
import com.jgkj.utils.token.GetTokenUtils;
import com.jgkj.utils.token.utils.statusbar.StatusBarUtil;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;

/**
 * 个人中心：GRB
 */
public class PersonalGRBActivity extends BaseActivity {

    public static void start(Context context) {
        Intent intent = new Intent(context, PersonalGRBActivity.class);
        context.startActivity(intent);
    }

    @BindView(R.id.personal_grb)
    TextView mPersonalGrb;

    @BindView(R.id.grb_realtime_reference_value)
    TextView mPersonalGrbRealtimeReferenceValue;
    @BindView(R.id.grb_realtime_reference_value_time)
    TextView mPersonalGrbRealtimeReferenceValueTime;

    @BindView(R.id.grb_total)
    TextView mPersonalGrbTotal;

    @BindView(R.id.grb_gra)
    FrameLayout mPersonalGrbGRA;
    @BindView(R.id.grb_gra_card)
    TextView mPersonalGrbGRACard;

    @BindView(R.id.grb_grb)
    FrameLayout mPersonalGrbGRB;
    @BindView(R.id.grb_grb_card)
    TextView mPersonalGrbGRBCard;

    @BindView(R.id.grb_grc)
    FrameLayout mPersonalGrbGRC;
    @BindView(R.id.grb_grc_card)
    TextView mPersonalGrbGRCCard;

    @BindView(R.id.personal_grb_luck_draw)
    FrameLayout mPersonalGrbLuckDraw;
    @BindView(R.id.personal_grb_transfer_public)
    FrameLayout mPersonalGrbTransferPublic;
    @BindView(R.id.personal_grb_transfer_friends)
    FrameLayout mPersonalGrbTransferFriends;
    @BindView(R.id.personal_grb_transfer_record)
    FrameLayout mPersonalGrbTransferRecord;
    @BindView(R.id.personal_grb_wallet_address)
    FrameLayout mPersonalGrbWalletAddress;
    @BindView(R.id.personal_grb_income_details)
    FrameLayout mPersonalGrbIncomeDetails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_grb);

        //StatusBarUtil.setStatusBarDarkIconMode(this, false);
        StatusBarUtil.setStatusBar(this, Color.TRANSPARENT, 0, false);
        Toolbar toolbar = initToolBar("");
        setBackgroundColor(toolbar, Color.TRANSPARENT);
        toolbar.setNavigationIcon(R.mipmap.back_navi_icon_white);

        RxView.setOnClickListeners(this, mPersonalGrbGRA, mPersonalGrbGRB, mPersonalGrbGRC,
                mPersonalGrbLuckDraw, mPersonalGrbTransferPublic, mPersonalGrbTransferFriends,
                mPersonalGrbTransferRecord, mPersonalGrbWalletAddress, mPersonalGrbIncomeDetails);

        onLazyLoad();
    }

    // 登录状态变化
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onReceiveEventBus(RefreshUserInfo event) {
        if (event.isLogin)
            onLazyLoad();
    }

    private void onLazyLoad() {
        showProgressDialog();
        String token = GetTokenUtils.getToken(this, ApiStores.API_SERVER_USER_GRB);
        addSubscription(apiStores().userGRB(token), new ApiCallback<PersonalGRBModel>() {
            @Override
            public void onSuccess(PersonalGRBModel model) {
                if (model.getCode() == 1) {
                    mPersonalGrb.setText(String.format(getString(R.string.total_bottom_text), model.getData().getTotal())); // 总份数
                    mPersonalGrbRealtimeReferenceValue.setText(model.getData().getGrb()); // 实时参考价值
                    mPersonalGrbRealtimeReferenceValueTime.setText(model.getData().getGrbtime());
                    mPersonalGrbTotal.setText(String.format(getString(R.string.total_bottom_text), model.getData().getAll())); // 总丰收
                    mPersonalGrbGRACard.setText(String.format(getString(R.string.total_bottom_text), model.getData().getGra())); // GRA
                    mPersonalGrbGRBCard.setText(String.format(getString(R.string.total_bottom_text), model.getData().getGrb_cash())); // GRB
                    mPersonalGrbGRCCard.setText(String.format(getString(R.string.total_bottom_text), model.getData().getGrb_integral())); // GRC
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
    public void onClick(Object o) {
        View v = (View) o;
        switch (v.getId()) {
            case R.id.grb_gra:
                // toastShow(R.string.not_yet_open_tip);
                PersonalGRBGraActivity.start(mActivity);
                break;
            case R.id.grb_grb:
                PersonalGRBGrbActivity.start(mActivity);
                break;
            case R.id.grb_grc:
                PersonalGRBGrcActivity.start(mActivity);
                break;
            case R.id.personal_grb_luck_draw:
                //toastShow(R.string.not_yet_open_tip);
                LuckDrawActivity.start(mActivity);
                break;
            case R.id.personal_grb_transfer_public:
                PersonalGRBTransferPublicSecondActivity.start(this, 2);
                /*// is_realname  实名情况：0:未认证  1认证中 2已认证
                int is_realname = isRealname();
                if (is_realname == 2) {
                    // PersonalGRBTransferPublicActivity.start(mActivity);
                    PersonalGRBTransferPublicSecondActivity.start(this, 2);
                } else if (is_realname == 1) {
                    ApplyResultActivity.start(mActivity, 2);
                } else if (is_realname == 3) {
                    showUnrealNameDialog(getString(R.string.real_name_rejected));
                } else {
                    showUnrealNameDialog();
                }*/
                break;
            case R.id.personal_grb_transfer_friends:
                PersonalTransferFriendsActivity.start(mActivity);
                break;
            case R.id.personal_grb_transfer_record:
                PersonalTransferRecordActivity.start(mActivity);
                break;
            case R.id.personal_grb_wallet_address:
                PersonalWalletAddressActivity.start(mActivity);
                break;
            case R.id.personal_grb_income_details:
                try {
                    JSONObject userModel = new JSONObject(sharedPreferencesHelper.getSharedPreference("user", "").toString());
                    // us_level 农场等级：0：游客，1：体验会员，2：一星代理，3：二星代理，4：三星代理，5：四星代理，6：五星代理
                    int us_level = userModel.optInt("us_level", 0);
                    if (us_level > 0) {
                        PersonalIncomeActivity.start(mActivity);
                    } else {
                        toastShow(R.string.personal_grb_income_details_low);
                    }
                } catch (JSONException e) {
                }
                break;
            default:
                break;
        }
    }
}
