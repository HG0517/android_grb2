package com.jgkj.grb.ui.activity;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.TextView;

import com.jgkj.grb.R;
import com.jgkj.grb.base.BaseActivity;
import com.jgkj.grb.glide.GlideApp;
import com.jgkj.grb.onclick.RxView;
import com.jgkj.grb.retrofit.ApiCallback;
import com.jgkj.grb.retrofit.ApiStores;
import com.jgkj.grb.ui.dialog.LuckDrawDialog;
import com.jgkj.grb.ui.mvp.luckdraw.LuckDrawIndexModel;
import com.jgkj.grb.utils.Logger;
import com.jgkj.utils.token.GetTokenUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Locale;

import butterknife.BindView;

/**
 * GRB抽奖
 */
public class LuckDrawActivity extends BaseActivity {

    public static void start(Context context) {
        Intent intent = new Intent(context, LuckDrawActivity.class);
        context.startActivity(intent);
    }

    @BindView(R.id.luck_draw_message_iv)
    ImageView mLuckDrawMessageIv;
    @BindView(R.id.luck_draw_message_cv)
    CardView mLuckDrawMessageCv;
    @BindView(R.id.luck_draw_message_tv)
    TextView mLuckDrawMessageTv;
    @BindView(R.id.luck_draw_mine)
    ImageView mLuckDrawMine;
    @BindView(R.id.luck_draw_rotate_0)
    ImageView mLuckDrawRotateBg;
    @BindView(R.id.luck_draw_rotate_1)
    ImageView mLuckDrawRotateGo;
    @BindView(R.id.luck_draw_rotate_tip)
    ImageView mLuckDrawRotateTip;
    @BindView(R.id.luck_draw_rotate_tip_tv)
    TextView mLuckDrawRotateTipTv;
    @BindView(R.id.luck_draw_details_cv)
    CardView mLuckDrawDetailsCv;
    @BindView(R.id.luck_draw_details_tv)
    TextView mLuckDrawDetailsTv;
    @BindView(R.id.luck_draw_times)
    TextView mLuckDrawTimes;

    private ObjectAnimator rotateAnimator;
    private boolean isLucking = false;

    LuckDrawIndexModel mLuckDrawIndexModel;
    CountDownTimer mCountDownTimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_luck_draw);

        Toolbar toolbar = initToolBar(getString(R.string.activity_title_luck_draw));

        RxView.setOnClickListeners(this, mLuckDrawMine, mLuckDrawRotateGo, mLuckDrawRotateTip, mLuckDrawDetailsCv);
        mLuckDrawMessageTv.setSelected(true);
        mLuckDrawDetailsCv.setClickable(false);
        onLazyLoad();
    }

    /**
     * 抽奖总信息
     */
    private void onLazyLoad() {
        showProgressDialog();
        String token = GetTokenUtils.getToken(this, ApiStores.API_SERVER_LOTTERY_INDEX);
        addSubscription(apiStores().lotteryIndex(token), new ApiCallback<LuckDrawIndexModel>() {
            @Override
            public void onSuccess(LuckDrawIndexModel model) {
                mLuckDrawIndexModel = model;
                if (model.getCode() == 1) {
                    GlideApp.with(mActivity)
                            .load(model.getData().getBackground().startsWith("http:") || model.getData().getBackground().startsWith("https:")
                                    ? model.getData().getBackground() : ApiStores.API_SERVER_URL + model.getData().getBackground())
                            .into(mLuckDrawRotateBg);
                    mLuckDrawTimes.setText(String.format(getString(R.string.luck_draw_chance), mLuckDrawIndexModel.getData().getDraw_num()));
                    mLuckDrawRotateTipTv.setText(String.format(getString(R.string.luck_draw_tip), model.getData().getNeed()));
                    lotteryGetnews(mLuckDrawIndexModel.getData().getNew_num());
                } else {
                    toastShow(model.getMsg());
                    lotteryGetnews(0);
                }
            }

            @Override
            public void onFailure(String msg) {
                toastShow(msg);
                lotteryGetnews(0);
            }

            @Override
            public void onFinish() {
                dismissProgressDialog();
            }
        });
    }

    /**
     * 获取抽奖消息
     */
    private void lotteryGetnews(int newNum) {
        if (newNum > 0)
            newNum = Math.min(newNum, newNum - 1);
        String token = GetTokenUtils.getToken(this, ApiStores.API_SERVER_LOTTERY_GETNEWS);
        addSubscription(apiStores().lotteryGetnews(token, newNum), new ApiCallback<String>() {
            @Override
            public void onSuccess(String model) {
                // {"code":1,"msg":"成功","time":1568942894,"data":{"news":"恭喜初始测试员获得了50GRB","new_num":59}}
                // {"code":1,"msg":"成功","time":1568943074,"data":"没有新消息"}
                try {
                    JSONObject result = new JSONObject(model);
                    if (result.optInt("code", 0) == 1) {
                        JSONObject data = result.optJSONObject("data");
                        String news = data.optString("news", "");
                        int new_num = data.optInt("new_num", -1);
                        if (!TextUtils.isEmpty(news)) {
                            mLuckDrawMessageTv.setText(news);
                        }
                        if (new_num >= 0 && null != mLuckDrawIndexModel) {
                            mLuckDrawIndexModel.getData().setNew_num(new_num);
                        }
                    } else {
                        Logger.i("TAG_LotteryGetnews", "onSuccess error msg = " + result.optString("msg", ""));
                    }
                } catch (JSONException e) {
                }
            }

            @Override
            public void onFailure(String msg) {
                Logger.i("TAG_LotteryGetnews", "onFailure error msg = " + msg);
            }

            @Override
            public void onFinish() {
                Logger.i("TAG_LotteryGetnews", "onFinish");
            }
        });

        if (null != mCountDownTimer)
            mCountDownTimer.cancel();
        int finalNewNum = newNum > 0 ? newNum + 1 : newNum;
        mCountDownTimer = new CountDownTimer(30 * 1000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                Logger.i("TAG_CountDownTimer", String.format(Locale.getDefault(), "millisUntil = %dS", millisUntilFinished / 1000));
            }

            @Override
            public void onFinish() {
                lotteryGetnews(null == mLuckDrawIndexModel ? finalNewNum : mLuckDrawIndexModel.getData().getNew_num());
            }
        };
        mCountDownTimer.start();
    }

    /**
     * 抽奖获奖位置信息
     */
    private void requestLuckDrawPosition() {
        showProgressDialog();
        String token = GetTokenUtils.getToken(this, ApiStores.API_SERVER_LOTTERY_EDIT);
        addSubscription(apiStores().lotteryEdit(token), new ApiCallback<LuckDrawIndexModel.DataBean.DataResultBean>() {
            @Override
            public void onSuccess(LuckDrawIndexModel.DataBean.DataResultBean model) {
                if (model.getCode() == 1) {
                    mLuckDrawIndexModel.getData().setResult(model);
                    mLuckDrawIndexModel.getData().setDraw_num(model.getData().getDraw_num());
                    // 抽奖动画，动画借结束，展示中奖
                    rotate(mLuckDrawIndexModel.getData().getList().size(), mLuckDrawIndexModel.getData().getResult().getData());
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
     * 支付 GRB 获取抽奖次数
     */
    private void lotteryGetNum() {
        showProgressDialog();
        String token = GetTokenUtils.getToken(this, ApiStores.API_SERVER_LOTTERY_GETNUM);
        addSubscription(apiStores().lotteryGetNum(token), new ApiCallback<String>() {
            @Override
            public void onSuccess(String model) {
                //{"code":1,"msg":"成功","time":1567480276,"data":{"draw_num":2}}
                try {
                    JSONObject result = new JSONObject(model);
                    if (result.getInt("code") == 1) {
                        JSONObject data = result.getJSONObject("data");
                        mLuckDrawIndexModel.getData().setDraw_num(data.getInt("draw_num"));
                        mLuckDrawTimes.setText(String.format(getString(R.string.luck_draw_chance), mLuckDrawIndexModel.getData().getDraw_num()));
                        toastShow(result.getString("msg"));
                    } else if (result.getInt("code") == 405) {
                        // 余额不足，是否需要处理
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

    @Override
    public void onClick(Object o) {
        View v = (View) o;
        switch (v.getId()) {
            case R.id.luck_draw_mine:
                LuckDrawMineActivity.start(mActivity);
                break;
            case R.id.luck_draw_rotate_tip:
                if (isLucking || null == mLuckDrawIndexModel || (null != rotateAnimator && (rotateAnimator.isRunning() || rotateAnimator.isStarted())))
                    return;
                LuckDrawDialog dialog = new LuckDrawDialog(this);
                dialog.setCancelable(false);
                dialog.show();
                dialog.setDialogTitle(String.format(getString(R.string.luck_draw_chance_buy_tip), mLuckDrawIndexModel.getData().getNeed()));
                dialog.setOnActionClickListener(new LuckDrawDialog.OnActionClickListener() {
                    @Override
                    public void onCancel() {
                    }

                    @Override
                    public void onSure() {
                        lotteryGetNum();
                    }
                });
                break;
            case R.id.luck_draw_rotate_1:
                if (isLucking || null == mLuckDrawIndexModel || (null != rotateAnimator && (rotateAnimator.isRunning() || rotateAnimator.isStarted())))
                    return;
                if (mLuckDrawIndexModel.getData().getDraw_num() > 0) {
                    requestLuckDrawPosition();
                } else {
                    toastShow(R.string.luck_draw_chance_outof_tip);
                }
                break;
            case R.id.luck_draw_details_cv:
                toastShow("Luck Draw Details");
                break;
            default:
                break;
        }
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
    private void rotate(final int rotateCycles, final LuckDrawIndexModel.DataBean.DataResultBean.ResultBean resultBean) {
        if (isLucking || null == mLuckDrawIndexModel || (null != rotateAnimator && (rotateAnimator.isRunning() || rotateAnimator.isStarted())))
            return;

        // int rotateToPosition = 360 / mCount * (mCount - resultBean.getId()); // 逆时针排列奖项
        float rotateToPosition = 360f / rotateCycles * resultBean.getId(); // 顺时针排列奖项
        float toDegree = 360f * rotateCycles + rotateToPosition;

        rotateAnimator = ObjectAnimator.ofFloat(mLuckDrawRotateGo, "rotation", 0, toDegree);
        rotateAnimator.setDuration(rotateCycles * 1000);
        rotateAnimator.setRepeatCount(0);
        rotateAnimator.setInterpolator(new DecelerateInterpolator());
        rotateAnimator.setAutoCancel(true);
        rotateAnimator.start();
        rotateAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                isLucking = true;
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                isLucking = false;
                handleResult(resultBean.getMsg());
            }

            @Override
            public void onAnimationCancel(Animator animation) {
            }

            @Override
            public void onAnimationRepeat(Animator animation) {
            }
        });
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
    private void handleResult(String resultMsg) {
        if (null == mLuckDrawDetailsTv)
            return;
        if (TextUtils.isEmpty(resultMsg)) {
            resultMsg = getString(R.string.luck_draw_regret_tip);
        }
        mLuckDrawDetailsTv.setText(resultMsg);
        mLuckDrawTimes.setText(String.format(getString(R.string.luck_draw_chance), mLuckDrawIndexModel.getData().getDraw_num()));
        ObjectAnimator scaleXBefore = ObjectAnimator.ofFloat(mLuckDrawDetailsCv, "scaleX", 0.3f, 1f);
        ObjectAnimator scaleYBefore = ObjectAnimator.ofFloat(mLuckDrawDetailsCv, "scaleY", 0.3f, 1f);
        ObjectAnimator alphaBefore = ObjectAnimator.ofFloat(mLuckDrawDetailsCv, "alpha", 0.3f, 1f);
        ObjectAnimator scaleX = ObjectAnimator.ofFloat(mLuckDrawDetailsCv, "scaleX", 1f, 0f);
        ObjectAnimator scaleY = ObjectAnimator.ofFloat(mLuckDrawDetailsCv, "scaleY", 1f, 0f);
        ObjectAnimator alpha = ObjectAnimator.ofFloat(mLuckDrawDetailsCv, "alpha", 1f, 0f);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.setDuration(1500);
        animatorSet.setInterpolator(new DecelerateInterpolator());
        animatorSet.play(scaleX).with(scaleY).with(alpha)
                .after(scaleXBefore).after(scaleYBefore).after(alphaBefore);
        animatorSet.start();
    }

    @Override
    protected void onDestroy() {
        if (null != mCountDownTimer)
            mCountDownTimer.cancel();
        super.onDestroy();
    }
}
