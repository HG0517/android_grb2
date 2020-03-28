package com.jgkj.grb.ui.fragment;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jgkj.grb.R;
import com.jgkj.grb.base.BaseFragment;
import com.jgkj.grb.itemdecoration.SpaceItemDecoration;
import com.jgkj.grb.push.JExampleUtil;
import com.jgkj.grb.retrofit.ApiCallback;
import com.jgkj.grb.retrofit.ApiStores;
import com.jgkj.grb.ui.activity.ProductDetailsActivity;
import com.jgkj.grb.ui.adapter.FlashSaleAdapter;
import com.jgkj.grb.ui.mvp.FlashSaleModel;
import com.jgkj.grb.ui.mvp.FlashSalePageModel;
import com.jgkj.grb.utils.Logger;
import com.jgkj.grb.view.datepicker.DateFormatUtils;
import com.jgkj.utils.token.GetTokenUtils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import cn.jpush.android.api.JPushInterface;

/**
 * 限时抢购
 * Created by brightpoplar@163.com on 2019/8/3.
 */
public class FragmentFlashSale extends BaseFragment {

    public static FragmentFlashSale newInstance(FlashSaleModel.DataBean data) {
        FragmentFlashSale fragmentFlashSale = new FragmentFlashSale();
        Bundle bundle = new Bundle();
        bundle.putSerializable("data", data);
        fragmentFlashSale.setArguments(bundle);
        return fragmentFlashSale;
    }

    @BindView(R.id.countdown)
    RelativeLayout mCountdown;
    @BindView(R.id.countdown_title)
    TextView mCountdownTitle;
    @BindView(R.id.countdown_status)
    TextView mCountdownStatus;
    @BindView(R.id.countdown_hour_tv)
    TextView mCountdownHourTv;
    @BindView(R.id.countdown_min_tv)
    TextView mCountdownMinTv;
    @BindView(R.id.countdown_second_tv)
    TextView mCountdownSecondTv;

    @BindView(R.id.smart_refresh_layout)
    SmartRefreshLayout mSmartRefreshLayout;
    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;

    private FlashSaleAdapter mAdapter;
    private FlashSalePageModel mDatas;

    FlashSaleModel.DataBean mData;
    List<FlashSalePageModel.DataBean> mDataList = new ArrayList<>();

    private int page = 1;
    private int size = 10;

    CountDownTimer mCountDownTimer;
    boolean initCountDown = false;

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_flash_sale;
    }

    @Override
    public void init(Bundle savedInstanceState) {
        Bundle arguments = getArguments();
        if (null == arguments)
            return;
        mData = (FlashSaleModel.DataBean) arguments.getSerializable("data");
        if (null == mData)
            return;

        initSmartRefreshLayout();
        initRecyclerView();
    }

    // 0结束，1正在，2即将
    private void initCountDown() {
        initCountDown = true;
        switch (mData.getType()) {
            case 0:
                mCountdownStatus.setText(R.string.flash_sale_status_0);
                mCountdownHourTv.setText("00");
                mCountdownMinTv.setText("00");
                mCountdownSecondTv.setText("00");
                break;
            case 1:
                mCountdownStatus.setText(R.string.flash_sale_status_1);
                Logger.i("TAG_1", DateFormatUtils.long2Str(System.currentTimeMillis(), DateFormatUtils.DATE_FORMAT_PATTERN_YMD_HMS));
                Logger.i("TAG_1", DateFormatUtils.secondToDate(mData.getEnd(), DateFormatUtils.DATE_FORMAT_PATTERN_YMD_HMS));
                countDownShow(Math.abs(System.currentTimeMillis()
                                - DateFormatUtils.str2Long(DateFormatUtils.secondToDate(mData.getEnd(), DateFormatUtils.DATE_FORMAT_PATTERN_YMD_HMS), DateFormatUtils.DATE_FORMAT_PATTERN_YMD_HMS)),
                        mData.getType());
                break;
            case 2:
                mCountdownStatus.setText(R.string.flash_sale_status_2);
                Logger.i("TAG_2", DateFormatUtils.long2Str(System.currentTimeMillis(), DateFormatUtils.DATE_FORMAT_PATTERN_YMD_HMS));
                Logger.i("TAG_2", DateFormatUtils.secondToDate(mData.getEnd() - 60 * 60, DateFormatUtils.DATE_FORMAT_PATTERN_YMD_HMS));
                countDownShow(Math.abs(System.currentTimeMillis()
                                - DateFormatUtils.str2Long(DateFormatUtils.secondToDate(mData.getEnd() - 60 * 60, DateFormatUtils.DATE_FORMAT_PATTERN_YMD_HMS), DateFormatUtils.DATE_FORMAT_PATTERN_YMD_HMS)),
                        mData.getType());
                break;
            default:
                break;
        }
    }

    /**
     * 倒计时
     *
     * @param millisInFuture 总时
     */
    private void countDownShow(final long millisInFuture, int type) {
        onDetach();
        String[] strings = DateFormatUtils.millisDiff(millisInFuture);
        Logger.i("TAG_countDownShow", millisInFuture + " , " + strings[0] + " , " + strings[1] + " , " + strings[2] + " , " + strings[3]);

        mCountDownTimer = new CountDownTimer(millisInFuture, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                String[] strings = DateFormatUtils.millisDiff(millisUntilFinished);
                Logger.i("TAG_onTick", millisUntilFinished + " , " + strings[0] + " , " + strings[1] + " , " + strings[2] + " , " + strings[3]);
                if (null == mCountdownHourTv || null == mCountdownMinTv || null == mCountdownSecondTv)
                    return;
                mCountdownHourTv.setText(strings[1]);
                mCountdownMinTv.setText(strings[2]);
                mCountdownSecondTv.setText(strings[3]);
            }

            @Override
            public void onFinish() {
                Logger.i("TAG_onFinish", "onFinish");
                if (type == 2) {
                    mData.setType(1);
                } else if (type == 1) {
                    mData.setType(0);
                }
                initCountDown();
            }
        };
        mCountDownTimer.start();
    }

    @Override
    public void onDetach() {
        if (null != mCountDownTimer) {
            mCountDownTimer.cancel();
        }
        super.onDetach();
    }

    @Override
    public void onDestroyView() {
        onDetach();
        super.onDestroyView();
    }

    @Override
    public void onResume() {
        if (initCountDown) {
            initCountDown();
        }
        super.onResume();
    }

    @Override
    protected void onLazyLoad() {
        showProgressDialog();
        addSubscription(apiStores().limits(mData.getId()), new ApiCallback<FlashSalePageModel>() {
            @Override
            public void onSuccess(FlashSalePageModel model) {
                if (model.getCode() == 1) {
                    mDataList.addAll(model.getData());
                    mAdapter.notifyDataSetChanged();
                    initCountDown();
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

    private void initSmartRefreshLayout() {
        mSmartRefreshLayout.setEnableRefresh(false);
        mSmartRefreshLayout.setEnableLoadMore(false);
        mSmartRefreshLayout.setEnableAutoLoadMore(false);
        mSmartRefreshLayout.setOnRefreshListener(refreshLayout -> {
            // 刷新数据
            refreshLayout.setEnableLoadMore(true);
            page = 1;
            onLazyLoad();
        });
        mSmartRefreshLayout.setOnLoadMoreListener(refreshLayout -> {
            // 加载数据
            onLazyLoad();
        });
    }

    private void initRecyclerView() {
        mDatas = new FlashSalePageModel();
        mAdapter = new FlashSaleAdapter(mActivity, mDataList, mData.getType());
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mActivity));
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.addItemDecoration(new SpaceItemDecoration(dp2px(2)));
        mAdapter.setOnItemClickListener(new FlashSaleAdapter.OnItemActionListener() {
            @Override
            public void onActionClick(View v, int position) {
                if (isLogin()) {
                    newsRemind(mDataList.get(position));
                }
            }

            @Override
            public void onItemClick(View view, int position) {
                if (mData.getType() != 0)
                    ProductDetailsActivity.start(mActivity, String.valueOf(mDataList.get(position).getId()));
            }
        });
    }

    private void newsRemind(FlashSalePageModel.DataBean data) {
        showProgressDialog();
        String token = GetTokenUtils.getToken(mActivity, ApiStores.API_SERVER_NEWS_REMIND);
        String registrationID = sharedPreferencesConfig.getSharedPreference(JExampleUtil.KEY_REGISTRATION_ID,
                JPushInterface.getRegistrationID(mActivity)).toString();
        String usId = "";
        try {
            String userStr = sharedPreferencesHelper.getSharedPreference("user", "").toString();
            JSONObject user = new JSONObject(userStr);
            usId = user.optString("id", "");
        } catch (JSONException e) {
        }
        addSubscription(apiStores().newsRemind(token, registrationID, mData.getId(), usId), new ApiCallback<String>() {
            @Override
            public void onSuccess(String model) {
                try {
                    JSONObject result = new JSONObject(model);
                    if (result.optInt("code", 0) == 1) {
                        toastShow(result.optString("msg", "设置提醒成功"));
                    } else {
                        toastShow(result.optString("msg", "设置提醒失败"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
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
