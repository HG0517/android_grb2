package com.jgkj.grb.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.jgkj.grb.R;
import com.jgkj.grb.base.BaseActivity;
import com.jgkj.grb.eventbus.RefreshUserInfo;
import com.jgkj.grb.glide.GlideApp;
import com.jgkj.grb.onclick.RxView;
import com.jgkj.grb.retrofit.ApiCallback;
import com.jgkj.grb.retrofit.ApiStores;
import com.jgkj.grb.ui.adapter.CentreActiveGoodsAdapter;
import com.jgkj.grb.ui.adapter.CentreContentsAdapter;
import com.jgkj.grb.ui.dialog.MeetingPaymentDialog;
import com.jgkj.grb.ui.dialog.MeetingSignUpDialog;
import com.jgkj.grb.ui.dialog.PaymentNowDialog;
import com.jgkj.grb.ui.mvp.ConventionCentreModel;
import com.jgkj.grb.utils.LocationManagerUtil;
import com.jgkj.grb.utils.Logger;
import com.jgkj.grb.view.datepicker.DateFormatUtils;
import com.jgkj.utils.token.GetTokenUtils;
import com.jgkj.utils.token.utils.statusbar.StatusBarUtil;
import com.xgr.easypay.callback.IPayCallback;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.runtime.Permission;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 会议中心
 */
public class ConventionCentreActivity extends BaseActivity {

    public static void start(Context context) {
        Intent intent = new Intent(context, ConventionCentreActivity.class);
        context.startActivity(intent);
    }

    @BindView(R.id.nestedScrollView)
    NestedScrollView mNestedScrollView;
    // 顶部的背景
    @BindView(R.id.center_top_iv)
    ImageView mCenterTopIv;

    // 会议时间
    @BindView(R.id.center_top)
    ConstraintLayout mCenterTop;
    @BindView(R.id.center_top_title)
    TextView mCenterTopTitle;
    @BindView(R.id.countdown_days)
    CardView mCountdownDays;
    @BindView(R.id.countdown_days_tv)
    TextView mCountdownDaysTv;
    @BindView(R.id.countdown_split_days)
    TextView mCountdownSplitDays;
    @BindView(R.id.countdown_hour)
    CardView mCountdownHour;
    @BindView(R.id.countdown_hour_tv)
    TextView mCountdownHourTv;
    @BindView(R.id.countdown_min)
    CardView mCountdownMin;
    @BindView(R.id.countdown_min_tv)
    TextView mCountdownMinTv;
    @BindView(R.id.countdown_second)
    CardView mCountdownSecond;
    @BindView(R.id.countdown_second_tv)
    TextView mCountdownSecondTv;
    @BindView(R.id.convention_center_sign)
    ImageView mConventionCenterSign; // 报名/签到
    CountDownTimer mCountDownTimer;
    boolean initCountDown = false;

    // 会议主题信息
    @BindView(R.id.centre_news)
    CardView mCentreNews;
    @BindView(R.id.centre_news_iv)
    ImageView mCentreNewsIv;
    @BindView(R.id.centre_news_title)
    TextView mCentreNewsTitle;
    @BindView(R.id.centre_news_desc)
    TextView mCentreNewsDesc;

    // 会议内容
    @BindView(R.id.centre_contents)
    CardView mCentreContents;
    @BindView(R.id.centre_contents_list)
    RecyclerView mCentreContentsList;

    // 活动商品
    @BindView(R.id.centre_active_goods)
    CardView mCentreActiveGoods;
    @BindView(R.id.centre_centre_active_goods_list)
    RecyclerView mCentreActiveGoodsList;

    // 活动报名
    @BindView(R.id.centre_active_signup)
    CardView mCentreActiveSignup;
    @BindView(R.id.centre_active_signup_name_input)
    EditText mCentreActiveSignupNameInput;
    @BindView(R.id.centre_active_signup_phone_input)
    EditText mCentreActiveSignupPhoneInput;
    @BindView(R.id.centre_active_signup_cardid_input)
    EditText mCentreActiveSignupCardideInput;
    @BindView(R.id.centre_gender)
    RadioGroup mCentreGender;
    @BindView(R.id.centre_gender_female)
    RadioButton mCentreGenderFemale;
    @BindView(R.id.centre_gender_male)
    RadioButton mCentreGenderMale;
    @BindView(R.id.centre_active_signup_action)
    CardView mCentreActiveSignupAction;

    // 会议时间、地址、电话
    @BindView(R.id.centre_info)
    CardView mCentreInfo;
    @BindView(R.id.centre_info_time)
    TextView mCentreInfoTime;
    @BindView(R.id.centre_info_address)
    TextView mCentreInfoAddress;
    @BindView(R.id.centre_info_tel)
    TextView mCentreInfoTel;
    @BindView(R.id.center_online_action)
    ImageView mCentreOnlineAction;

    @BindView(R.id.centre_entry_record)
    CardView mCentreEntryRecord;

    ConventionCentreModel mConventionCentreModel;

    private List<ConventionCentreModel.DataBean.DetailBean> mContentList = new ArrayList<>();
    private CentreContentsAdapter mContentsAdapter;
    private List<ConventionCentreModel.DataBean.GoodsBean> mActiveList = new ArrayList<>();
    private CentreActiveGoodsAdapter mActiveAdapter;

    private Location mLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_convention_centre);

        //StatusBarUtil.setStatusBarDarkIconMode(this, false);
        StatusBarUtil.setStatusBar(this, Color.TRANSPARENT, 0, false);
        Toolbar toolbar = initToolBar(getString(R.string.activity_title_convention_centre));
        setTitleTextColor(toolbar, Color.WHITE);
        setBackgroundColor(toolbar, Color.TRANSPARENT);
        toolbar.setNavigationIcon(R.mipmap.back_navi_icon_white);

        mCentreNewsDesc.setMovementMethod(ScrollingMovementMethod.getInstance());
        // 会议内容
        mCentreContentsList.setLayoutManager(new LinearLayoutManager(this));
        mCentreContentsList.setNestedScrollingEnabled(false);
        mContentsAdapter = new CentreContentsAdapter(this, mContentList);
        mCentreContentsList.setAdapter(mContentsAdapter);

        // 活动商品
        mCentreActiveGoodsList.setLayoutManager(new LinearLayoutManager(this));
        mCentreActiveGoodsList.setNestedScrollingEnabled(false);
        mActiveAdapter = new CentreActiveGoodsAdapter(this, mActiveList);
        mCentreActiveGoodsList.setAdapter(mActiveAdapter);
        mActiveAdapter.setOnItemClickListener((view, position) -> {
            ProductDetailsActivity.start(mActivity, String.valueOf(mActiveList.get(position).getPd_id()));
        });

        RxView.setOnClickListeners(this, mConventionCenterSign, mCentreActiveSignupAction, mCentreOnlineAction, mCentreEntryRecord);

        indexMeetingIndex();

        getLocation();
    }

    @Override
    public void onClick(Object o) {
        View v = (View) o;
        switch (v.getId()) {
            case R.id.convention_center_sign:
                if (null != mConventionCentreModel && isLogin()) {
                    if (mConventionCentreModel.getData().getSignin() == 1) { // 已签到
                        toastShow("已签到");
                    } else if (mConventionCentreModel.getData().getSignUp() == 1) { // 已报名，签到
                        if (null == mLocation) {
                            getLocation();
                            return;
                        }
                        indexMeetingSignIn();
                    } else { // 未报名，滑动到报名
                        int scrollTo = (int) mCentreActiveSignup.getY();
                        Logger.i("TAG_NestedScrollView", String.valueOf(scrollTo));
                        mNestedScrollView.smoothScrollTo(0, scrollTo);
                    }
                }
                break;
            case R.id.centre_active_signup_action: // 报名
                meetingSignUp();
                break;
            case R.id.center_online_action: // 联系：拨号盘
                if (null != mConventionCentreModel && !TextUtils.isEmpty(mConventionCentreModel.getData().getMeeting().getTel())) {
                    Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + mConventionCentreModel.getData().getMeeting().getTel()));
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                }
                break;
            case R.id.centre_entry_record: // 报名记录
                ConventionCentreRecordActivity.start(mActivity);
                break;
            default:
                break;
        }
    }

    /**
     * 会议签到
     */
    private void indexMeetingSignIn() {
        showProgressDialog();
        String token = GetTokenUtils.getToken(mActivity, ApiStores.API_SERVER_MEETING_SIGNIN);
        addSubscription(apiStores().indexMeetingSignIn(token, mConventionCentreModel.getData().getMeeting().getId(), mLocation.getLongitude() + "," + mLocation.getLatitude()),
                new ApiCallback<String>() {
                    @Override
                    public void onSuccess(String model) {
                        try {
                            JSONObject result = new JSONObject(model);
                            if (result.getInt("code") == 1) {
                                toastShow(result.getString("msg"));
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

    /**
     * 会议报名
     */
    private void meetingSignUp() {
        if (null != mConventionCentreModel && mConventionCentreModel.getData().getSignUp() == 0 && isLogin()) {
            if (TextUtils.isEmpty(mCentreActiveSignupNameInput.getText().toString().trim())) {
                toastShow(getString(R.string.centre_active_signup_tip_name_hint_text));
                return;
            }
            if (TextUtils.isEmpty(mCentreActiveSignupPhoneInput.getText().toString().trim())) {
                toastShow(getString(R.string.centre_active_signup_tip_phone_hint_text));
                return;
            }
            if (TextUtils.isEmpty(mCentreActiveSignupCardideInput.getText().toString().trim())) {
                toastShow(getString(R.string.centre_active_signup_tip_cardid_hint_text));
                return;
            }
            int sex = 0;
            if (mCentreGenderFemale.isChecked())
                sex = 2;
            if (mCentreGenderMale.isChecked())
                sex = 1;
            if (sex == 0) {
                toastShow(getString(R.string.centre_active_signup_tip_gender_text));
                return;
            }
            indexMeetingSignUp(mCentreActiveSignupNameInput.getText().toString().trim(),
                    mCentreActiveSignupPhoneInput.getText().toString().trim(),
                    mCentreActiveSignupCardideInput.getText().toString().trim(),
                    mConventionCentreModel.getData().getMeeting().getId(), sex);
        }
    }

    /**
     * 会议报名
     */
    private void indexMeetingSignUp(String name, String tel, String cardId, int mId, int sex) {
        showProgressDialog();
        String token = GetTokenUtils.getToken(mActivity, ApiStores.API_SERVER_MEETING_SIGNUP);
        addSubscription(apiStores().indexMeetingSignUp(token, name, tel, cardId, mId, sex/*, password, paymentType*/), new ApiCallback<String>() {
            @Override
            public void onSuccess(String model) {
                // {"code":1,"msg":"成功","time":1572688922,"data":{"num":"00014","id":"14"}}
                try {
                    JSONObject result = new JSONObject(model);
                    if (result.getInt("code") == 1) {
                        JSONObject data = result.optJSONObject("data");
                        showMeetingSignUp(data);
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

    /**
     * 会议报名：获得编号
     */
    private void showMeetingSignUp(JSONObject data) {
        MeetingSignUpDialog dialog = new MeetingSignUpDialog(mActivity);
        dialog.setCancelable(false);
        dialog.show();
        dialog.setDialogTip2(data.optString("num"));
        dialog.setOnActionClickListener(new MeetingSignUpDialog.OnActionClickListener() {
            @Override
            public void onCancel() {
            }

            @Override
            public void onSure() {
                showMeetingPaymentDialog(data.optString("num"), data.optString("id"));
            }
        });
    }

    /**
     * 报名支付：支付方式
     */
    private void showMeetingPaymentDialog(String num, String id) {
        MeetingPaymentDialog dialog = new MeetingPaymentDialog(this);
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
        dialog.setPaymentBalanceEnabled(mConventionCentreModel.getData().getMeeting().getCash() == 1);
        dialog.setPaymentGRBEnabled(mConventionCentreModel.getData().getMeeting().getGrb() == 1);
        dialog.setDialogTitle1(String.format(getString(R.string.total_top_text), mConventionCentreModel.getData().getMeeting().getFee()));
        dialog.setOnActionClickListener(new MeetingPaymentDialog.OnActionClickListener() {
            @Override
            public void onCancel() {
            }

            @Override
            public void onSure(ArrayList<Object> paymentType) {
                showPaymentNowDialog(num, id, paymentType);
            }
        });
    }

    /**
     * 报名支付：支付密码
     */
    private void showPaymentNowDialog(String num, String id, ArrayList<Object> paymentType) {
        PaymentNowDialog dialog = new PaymentNowDialog(mActivity);
        dialog.setCancelable(false);
        dialog.show();
        dialog.setOnFinishListener(new PaymentNowDialog.OnActionClickListener() {
            @Override
            public void onClose() {
            }

            @Override
            public void onInputFinish(String password) {
                indexMeetingPayFee(num, id, paymentType, password);
            }
        });
    }

    private void indexMeetingPayFee(String num, String id, ArrayList<Object> paymentType, String password) {
        showProgressDialog();
        String token = GetTokenUtils.getToken(mActivity, ApiStores.API_SERVER_MEETING_PAYFEE);
        addSubscription(apiStores().indexMeetingPayFee(token, id, paymentType, password), new ApiCallback<String>() {
            @Override
            public void onSuccess(String model) {
                try {
                    JSONObject result = new JSONObject(model);
                    if (result.optInt("code", 0) == 1) {
                        JSONObject data = result.optJSONObject("data");
                        int paytype = data.optInt("paytype", 0);
                        if (paytype == 0) { // 支付完成
                            toastShow(result.optString("msg", ""));
                            EventBus.getDefault().post(new RefreshUserInfo(true));
                        } else if (paytype == 4) { // 微信支付
                            JSONObject wechat = data.optJSONObject("wechat");
                            wxpay(wechat, payCallback);
                        } else if (paytype == 3) { // 支付宝支付
                            alipay(data.optString("alipay", ""), payCallback);
                        } else if (paytype == 5) { // 云闪付支付
                            JSONObject flash = data.optJSONObject("flash");
                            unionpay(flash, payCallback);
                        }
                    } else if (result.optInt("code", 0) == 3) {
                        showPaymentNowDialog(num, id, paymentType);
                        toastShow(result.optString("msg", ""));
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

    /**
     * 支付回调
     */
    private IPayCallback payCallback = new IPayCallback() {
        @Override
        public void success() {
            showPayResult("支付成功");
            mConventionCentreModel.getData().setSignUp(1);
            // 顶部会议信息
            mConventionCenterSign.setImageResource(mConventionCentreModel.getData().getSignUp() == 1 ? R.mipmap.ic_convention_center_sign_in : R.mipmap.ic_convention_center_sign_up);
            // 会议报名，信息填写、提交报名
            mCentreActiveSignup.setVisibility(mConventionCentreModel.getData().getSignUp() == 1 ? View.GONE : View.VISIBLE);
        }

        @Override
        public void failed(int code, String message) {
            showPayResult("支付失败");
        }

        @Override
        public void cancel() {
            showPayResult("支付取消");
        }
    };

    protected void showPayResult(String msg) {
        runOnUiThread(() -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setCancelable(false);
            builder.setTitle("支付结果通知");
            builder.setMessage(msg);
            builder.setNegativeButton("确定", (dialog, which) -> {
                        EventBus.getDefault().post(new RefreshUserInfo(true));
                        dialog.cancel();
                    }
            );
            builder.create().show();
        });
    }

    /**
     * 会议主信息
     */
    private void indexMeetingIndex() {
        showProgressDialog();
        String token = GetTokenUtils.getToken(mActivity, ApiStores.API_SERVER_MEETING_INDEX);
        addSubscription(apiStores().indexMeetingIndex(token), new ApiCallback<ConventionCentreModel>() {
            @Override
            public void onSuccess(ConventionCentreModel model) {
                mConventionCentreModel = model;
                if (model.getCode() == 1) {
                    // 顶部会议信息
                    mConventionCenterSign.setImageResource(model.getData().getSignUp() == 1 ? R.mipmap.ic_convention_center_sign_in : R.mipmap.ic_convention_center_sign_up);
                    if (!TextUtils.isEmpty(model.getData().getMeeting().getPic()))
                        GlideApp.with(mActivity)
                                .load(model.getData().getMeeting().getPic().startsWith("http:") || model.getData().getMeeting().getPic().startsWith("https:")
                                        ? model.getData().getMeeting().getPic().replaceAll("\\\\", "/")
                                        : ApiStores.API_SERVER_URL + model.getData().getMeeting().getPic().replaceAll("\\\\", "/"))
                                .into(mCentreNewsIv);
                    mCentreNewsTitle.setText(model.getData().getMeeting().getTitle());
                    mCentreNewsDesc.setText(model.getData().getMeeting().getContent());
                    // 会议内容列表
                    mContentList.addAll(model.getData().getDetail());
                    mContentsAdapter.notifyDataSetChanged();
                    // 活动商品列表
                    mActiveList.addAll(model.getData().getGoods());
                    mActiveAdapter.notifyDataSetChanged();
                    // 会议报名，信息填写、提交报名
                    mCentreActiveSignup.setVisibility(model.getData().getSignUp() == 1 ? View.GONE : View.VISIBLE);
                    // 底部会议信息
                    mCentreInfoTime.setText(String.format(getString(R.string.convention_centre_time), model.getData().getMeeting().getTime()));
                    mCentreInfoAddress.setText(String.format(getString(R.string.convention_centre_addr), model.getData().getMeeting().getAddress()));
                    mCentreInfoTel.setText(String.format(getString(R.string.convention_centre_tel), model.getData().getMeeting().getTel()));

                    // 会议计时
                    initCountdown();
                } else if (model.getCode() == 402) {
                    toastShow(model.getMsg());
                    onBackPressed();
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
     * 会议计时
     */
    private void initCountdown() {
        if (null != mCountDownTimer) {
            mCountDownTimer.cancel();
        }
        initCountDown = true;

        long currentTime = System.currentTimeMillis();
        long startTime = DateFormatUtils.str2Long(mConventionCentreModel.getData().getMeeting().getTime(), DateFormatUtils.DATE_FORMAT_PATTERN_YMD_HMS);
        String endToDate = DateFormatUtils.secondToDate(mConventionCentreModel.getData().getMeeting().getTimestrap(), DateFormatUtils.DATE_FORMAT_PATTERN_YMD_HMS);
        long endTime = DateFormatUtils.str2Long(endToDate, DateFormatUtils.DATE_FORMAT_PATTERN_YMD_HMS);

        Logger.i("TAG_ConventionCentre", DateFormatUtils.long2Str(currentTime, DateFormatUtils.DATE_FORMAT_PATTERN_YMD_HMS));
        Logger.i("TAG_ConventionCentre", mConventionCentreModel.getData().getMeeting().getTime());
        Logger.i("TAG_ConventionCentre", endToDate);

        if (currentTime < startTime) { // 未开始
            mCenterTopTitle.setText(getString(R.string.centre_ready_begin_text));
            long diff = currentTime - startTime;
            mCountDownTimer = new CountDownTimer(Math.abs(diff), 1000) {
                @Override
                public void onTick(long millisUntilFinished) {
                    String[] strings = DateFormatUtils.millisDiff(millisUntilFinished);
                    Logger.i("TAG_ConventionCentre_onTick", millisUntilFinished + " , " + strings[0] + " , " + strings[1] + " , " + strings[2] + " , " + strings[3]);
                    mCountdownDaysTv.setText(strings[0]);
                    mCountdownHourTv.setText(strings[1]);
                    mCountdownMinTv.setText(strings[2]);
                    mCountdownSecondTv.setText(strings[3]);
                }

                @Override
                public void onFinish() {
                    Logger.i("TAG_CountDownTimer", "onFinish");
                    initCountdown();
                }
            };
            mCountDownTimer.start();
        } else if (startTime < currentTime && currentTime < endTime) { // 正在进行
            mCenterTopTitle.setText(getString(R.string.centre_ready_end_text));
            long diff = currentTime - endTime;
            mCountDownTimer = new CountDownTimer(Math.abs(diff), 1000) {
                @Override
                public void onTick(long millisUntilFinished) {
                    String[] strings = DateFormatUtils.millisDiff(millisUntilFinished);
                    Logger.i("TAG_ConventionCentre_onTick", millisUntilFinished + " , " + strings[0] + " , " + strings[1] + " , " + strings[2] + " , " + strings[3]);
                    mCountdownDaysTv.setText(strings[0]);
                    mCountdownHourTv.setText(strings[1]);
                    mCountdownMinTv.setText(strings[2]);
                    mCountdownSecondTv.setText(strings[3]);
                }

                @Override
                public void onFinish() {
                    Logger.i("TAG_CountDownTimer", "onFinish");
                    initCountdown();
                }
            };
            mCountDownTimer.start();
        } else if (currentTime > endTime) { // 已结束
            mCenterTopTitle.setText(getString(R.string.centre_ready_end_text));
            mCountdownDaysTv.setText("00");
            mCountdownHourTv.setText("00");
            mCountdownMinTv.setText("00");
            mCountdownSecondTv.setText("00");
        }
    }

    /**
     * 获取定位
     */
    private void getLocation() {
        AndPermission.with(this)
                .runtime()
                .permission(Permission.Group.LOCATION)
                .onGranted(data -> {
                    // 已授权，获取位置信息
                    if (null == mLocation) {
                        if (LocationManagerUtil.isLocServiceEnable(this)) {
                            mLocation = LocationManagerUtil.getLocation(this);
                        } else {
                            LocationManagerUtil.locServiceUnenable(this, getString(R.string.permission_location_ondenied)).show();
                        }
                    }
                    if (null != mLocation)
                        Logger.i("TAG_location", mLocation.getLongitude() + " , " + mLocation.getLatitude());
                })
                .onDenied(data -> {
                    // 未授权
                    toastShow(R.string.permission_ondenied);
                })
                .start();
    }

    // 登录状态变化
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onReceiveEventBus(RefreshUserInfo event) {
        indexMeetingIndex();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == LocationManagerUtil.REQUEST_CODE_LOCATION_SETTING) {
            getLocation();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (null != mCountDownTimer) {
            mCountDownTimer.cancel();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (initCountDown) {
            initCountdown();
        }
    }
}
