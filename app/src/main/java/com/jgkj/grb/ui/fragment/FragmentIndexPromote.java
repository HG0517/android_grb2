package com.jgkj.grb.ui.fragment;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.jgkj.grb.R;
import com.jgkj.grb.base.BaseFragment;
import com.jgkj.grb.glide.GlideApp;
import com.jgkj.grb.glide.RoundTransformation;
import com.jgkj.grb.onclick.RxView;
import com.jgkj.grb.retrofit.ApiCallback;
import com.jgkj.grb.retrofit.ApiStores;
import com.jgkj.grb.ui.activity.AdvanceSaleActivity;
import com.jgkj.grb.ui.activity.BaseInterfaceActivity;
import com.jgkj.grb.ui.activity.ConventionCentreActivity;
import com.jgkj.grb.ui.activity.FlashSaleActivity;
import com.jgkj.grb.ui.activity.LuckDrawActivity;
import com.jgkj.grb.ui.activity.ProductDetailsActivity;
import com.jgkj.grb.ui.activity.SpecialProvisionActivity;
import com.jgkj.grb.ui.adapter.IndexChildTopAdapter;
import com.jgkj.grb.ui.adapter.SearchResultAdapter;
import com.jgkj.grb.ui.gujujin.activity.GujujinAgentActivity;
import com.jgkj.grb.ui.mvp.BannerData;
import com.jgkj.grb.ui.mvp.index.IndexCatesBean;
import com.jgkj.grb.ui.mvp.index.IndexCatesChildListModel;
import com.jgkj.grb.utils.Logger;
import com.jgkj.grb.view.datepicker.DateFormatUtils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.stx.xhb.xbanner.XBanner;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 首页：pager 推荐
 * Created by brightpoplar@163.com on 2019/7/27.
 */
public class FragmentIndexPromote extends BaseFragment {

    public static FragmentIndexPromote newInstance(int index) {
        FragmentIndexPromote fragmentIndexPromote = new FragmentIndexPromote();
        Bundle bundle = new Bundle();
        bundle.putInt("index", index);
        fragmentIndexPromote.setArguments(bundle);
        return fragmentIndexPromote;
    }

    @BindView(R.id.smart_refresh_layout)
    SmartRefreshLayout mSmartRefreshLayout;

    @BindView(R.id.mXBanner)
    XBanner mXBanner;
    @BindView(R.id.recycler_view_top)
    RecyclerView mRecyclerViewTop;

    @BindView(R.id.convention_centre)
    ImageView mConventionCentre;

    @BindView(R.id.flash_sale)
    ConstraintLayout mFlashSale;
    @BindView(R.id.flash_sale_hour)
    TextView mFlashSaleHour;
    @BindView(R.id.flash_sale_minutes)
    TextView mFlashSaleMinutes;
    @BindView(R.id.flash_sale_seconds)
    TextView mFlashSaleSeconds;
    @BindView(R.id.flash_sale_iv)
    ImageView mFlashSaleIv;
    @BindView(R.id.flash_sale_iv_0)
    ImageView mFlashSaleIv0;
    @BindView(R.id.flash_sale_iv_1)
    ImageView mFlashSaleIv1;
    @BindView(R.id.flash_sale_iv_2)
    ImageView mFlashSaleIv2;

    @BindView(R.id.index_hot)
    ConstraintLayout mRecyclerViewHot;
    @BindView(R.id.recycler_view_content)
    RecyclerView mRecyclerViewContent;

    @BindView(R.id.luck_draw)
    ImageView mLuckDraw;

    List<BannerData> mXBannerDatas = new ArrayList<>();

    IndexChildTopAdapter mTopAdapter;

    SearchResultAdapter mContentAdapter;
    List<Object> mListContent = new ArrayList<>();
    int page = 1;
    int size = 10;

    int mIndex;
    IndexCatesChildListModel mIndexModel;

    CountDownTimer mCountDownTimer;
    boolean initCountDown = false;

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_index_promote;
    }

    @Override
    public void init(Bundle savedInstanceState) {
        Bundle arguments = getArguments();
        if (null == arguments)
            return;
        mIndex = arguments.getInt("index");

        initSmartRefreshLayout();

        RxView.setOnClickListeners(this, mConventionCentre, mFlashSale, mLuckDraw,
                mFlashSaleIv0, mFlashSaleIv1, mFlashSaleIv2);
    }

    private void initSmartRefreshLayout() {
        mSmartRefreshLayout.setEnableRefresh(true);
        mSmartRefreshLayout.setEnableLoadMore(false);
        mSmartRefreshLayout.setEnableAutoLoadMore(false);
        mSmartRefreshLayout.setOnRefreshListener(refreshLayout -> {
            // 刷新数据
            refreshLayout.setEnableLoadMore(true);
            mListContent.clear();
            page = 1;
            onLazyLoad();
        });
        mSmartRefreshLayout.setOnLoadMoreListener(refreshLayout -> {
            // 加载数据
            onLazyLoad();
        });
    }

    /**
     * 推荐商品
     */
    private void initRecyclerViewContent() {
        if (null == mIndexModel.getData().getHots() || mIndexModel.getData().getHots().size() <= 0) {
            mRecyclerViewHot.setVisibility(View.GONE);
        } else {
            mRecyclerViewHot.setVisibility(View.VISIBLE);
            mRecyclerViewContent.setNestedScrollingEnabled(false);
            mRecyclerViewContent.setLayoutManager(new LinearLayoutManager(mActivity));
            mContentAdapter = new SearchResultAdapter(mActivity, mIndexModel.getData().getHots(), 0);
            mRecyclerViewContent.setAdapter(mContentAdapter);
            mContentAdapter.setOnItemClickListener((view, position) -> {
                ProductDetailsActivity.start(mActivity, String.valueOf(mIndexModel.getData().getHots().get(position).getId()));
            });
        }
    }

    /**
     * 功能标签
     */
    private void initRecyclerViewTop() {
        mRecyclerViewTop.setNestedScrollingEnabled(false);
        if (null != mIndexModel && null != mIndexModel.getData()
                && null != mIndexModel.getData().getSons()
                && mIndexModel.getData().getSons().size() < 5) {
            int spanCount = mIndexModel.getData().getSons().size() > 0 ? mIndexModel.getData().getSons().size() : 5;
            mRecyclerViewTop.setLayoutManager(new GridLayoutManager(mActivity, spanCount, GridLayoutManager.VERTICAL, false));
        } else {
            mRecyclerViewTop.setLayoutManager(new GridLayoutManager(mActivity, 5, GridLayoutManager.VERTICAL, false));
        }
        mTopAdapter = new IndexChildTopAdapter(mActivity, mIndexModel.getData().getSons());
        mRecyclerViewTop.setAdapter(mTopAdapter);
        mTopAdapter.setOnItemClickListener((view, position) -> {
            IndexCatesBean indexCatesBean = mIndexModel.getData().getSons().get(position);
            if (indexCatesBean.getId() == 28 || TextUtils.equals("专供", indexCatesBean.getCa_name())) {
                SpecialProvisionActivity.start(mActivity);
            } else if (indexCatesBean.getId() == 29 || TextUtils.equals("预售", indexCatesBean.getCa_name())) {
                AdvanceSaleActivity.start(mActivity);
            } else if (indexCatesBean.getId() == 30 || TextUtils.equals("代理", indexCatesBean.getCa_name())) {
                if (ApiStores.API_SERVER_URL.startsWith("http://admin") || ApiStores.API_SERVER_URL.startsWith("https://admin")) {
                    toastShow(R.string.not_yet_open_tip);
                } else if (isLogin()) {
                    GujujinAgentActivity.start(mActivity);
                }
            } else if (indexCatesBean.getId() == 31 || TextUtils.equals("基地", indexCatesBean.getCa_name())) {
                BaseInterfaceActivity.start(mActivity);
            } else if (indexCatesBean.getId() == 32 || TextUtils.equals("拼团", indexCatesBean.getCa_name())) {
                FlashSaleActivity.start(mActivity);
            }
        });
    }

    /**
     * 轮播图
     */
    private void initXBanner() {
        if (null == mIndexModel.getData().getShuffling() || mIndexModel.getData().getShuffling().size() <= 0) {
            mXBanner.setVisibility(View.GONE);
        } else {
            mXBanner.setVisibility(View.VISIBLE);
            mXBanner.setOnItemClickListener((banner, model, view, position) -> {
                if (model instanceof BannerData) {
                    //ProductDetailsActivity.start(mActivity);
                }
            });
            mXBanner.loadImage((banner, model, view, position) -> {
                if (model instanceof BannerData) {
                    BannerData data = (BannerData) model;
                    ImageView img = (ImageView) view;
                    GlideApp.with(this)
                            .load(data.getBannerUrl().replaceAll("\\\\", "/"))
                            .transform(new CenterCrop(), new RoundTransformation(mActivity, 10))
                            .into(img);
                }
            });
            mXBannerDatas.clear();
            for (int i = 0; i < mIndexModel.getData().getShuffling().size(); i++) {
                mXBannerDatas.add(new BannerData(
                        mIndexModel.getData().getShuffling().get(i).startsWith("http:") || mIndexModel.getData().getShuffling().get(i).startsWith("https:")
                                ? mIndexModel.getData().getShuffling().get(i)
                                : ApiStores.API_SERVER_URL + mIndexModel.getData().getShuffling().get(i), ""));
            }
            mXBanner.setBannerData(mXBannerDatas);
        }
    }

    /**
     * 会议中心
     */
    private void initConventionCentre() {
        if (null == mIndexModel.getData().getMeeting() || mIndexModel.getData().getMeeting().size() <= 0) {
            mConventionCentre.setVisibility(View.GONE);
        } else {
            mConventionCentre.setVisibility(View.VISIBLE);
        }
    }

    /**
     * 限时抢购、拼团
     */
    private void initFlashSale() {
        if (!TextUtils.isEmpty(mIndexModel.getData().getLimit())) {
            mFlashSale.setVisibility(View.VISIBLE);
            GlideApp.with(mActivity)
                    .load(mIndexModel.getData().getLimit().startsWith("http:") || mIndexModel.getData().getLimit().startsWith("https:")
                            ? mIndexModel.getData().getLimit() : ApiStores.API_SERVER_URL + mIndexModel.getData().getLimit())
                    .into(mFlashSaleIv);
            IndexCatesChildListModel.DataBean.Limit1Bean limit1 = mIndexModel.getData().getLimit1();
            if (null == limit1) {
                initCountdown();
                mFlashSale.setVisibility(View.GONE);
                mFlashSaleIv0.setVisibility(View.GONE);
                mFlashSaleIv1.setVisibility(View.GONE);
                mFlashSaleIv2.setVisibility(View.GONE);
            } else {
                initCountdown();
                mFlashSale.setVisibility(View.VISIBLE);
                // 左
                if (null != limit1.getLimit1() && !TextUtils.isEmpty(limit1.getLimit1().getPic())) {
                    mFlashSaleIv0.setVisibility(View.VISIBLE);
                    GlideApp.with(mActivity)
                            .load(limit1.getLimit1().getPic().startsWith("http:") || limit1.getLimit1().getPic().startsWith("https:")
                                    ? limit1.getLimit1().getPic() : ApiStores.API_SERVER_URL + limit1.getLimit1().getPic())
                            .transform(new CenterCrop(), new RoundTransformation(mActivity, 5))
                            .into(mFlashSaleIv0);
                } else {
                    mFlashSaleIv0.setVisibility(View.GONE);
                }
                // 右上
                if (null != limit1.getLimit2() && !TextUtils.isEmpty(limit1.getLimit2().getPic())) {
                    mFlashSaleIv1.setVisibility(View.VISIBLE);
                    GlideApp.with(mActivity)
                            .load(limit1.getLimit2().getPic().startsWith("http:") || limit1.getLimit2().getPic().startsWith("https:")
                                    ? limit1.getLimit2().getPic() : ApiStores.API_SERVER_URL + limit1.getLimit2().getPic())
                            .transform(new CenterCrop(), new RoundTransformation(mActivity, 5))
                            .into(mFlashSaleIv1);
                } else {
                    mFlashSaleIv1.setVisibility(View.GONE);
                }
                // 右下
                if (null != limit1.getLimit3() && !TextUtils.isEmpty(limit1.getLimit3().getPic())) {
                    mFlashSaleIv2.setVisibility(View.VISIBLE);
                    GlideApp.with(mActivity)
                            .load(limit1.getLimit3().getPic().startsWith("http:") || limit1.getLimit3().getPic().startsWith("https:")
                                    ? limit1.getLimit3().getPic() : ApiStores.API_SERVER_URL + limit1.getLimit3().getPic())
                            .transform(new CenterCrop(), new RoundTransformation(mActivity, 5))
                            .into(mFlashSaleIv2);
                } else {
                    mFlashSaleIv2.setVisibility(View.GONE);
                }
            }
        } else {
            mFlashSale.setVisibility(View.GONE);
            initCountdown();
        }
    }

    private void initCountdown() {
        if (null != mCountDownTimer) {
            mCountDownTimer.cancel();
        }
        if (TextUtils.isEmpty(mIndexModel.getData().getLimit2()))
            return;
        initCountDown = true;

        long currentTime = System.currentTimeMillis();
        String secondToDate = DateFormatUtils.secondToDate(Long.parseLong(mIndexModel.getData().getLimit2()), DateFormatUtils.DATE_FORMAT_PATTERN_YMD_HMS);
        long secondTime = DateFormatUtils.str2Long(secondToDate, DateFormatUtils.DATE_FORMAT_PATTERN_YMD_HMS);
        Logger.i("TAG_ProductDetail", DateFormatUtils.long2Str(currentTime, DateFormatUtils.DATE_FORMAT_PATTERN_YMD_HMS));
        Logger.i("TAG_ProductDetail", secondToDate);
        long diff = currentTime - secondTime;
        if (diff > 0) { // 已结束
            mFlashSaleHour.setText("00");
            mFlashSaleMinutes.setText("00");
            mFlashSaleSeconds.setText("00");
            return;
        }

        mCountDownTimer = new CountDownTimer(Math.abs(diff), 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                String[] strings = DateFormatUtils.millisDiff(millisUntilFinished);
                Logger.i("TAG_ProductDetail_onTick", millisUntilFinished + " , " + strings[0] + " , " + strings[1] + " , " + strings[2] + " , " + strings[3]);
                mFlashSaleHour.setText(strings[1]);
                mFlashSaleMinutes.setText(strings[2]);
                mFlashSaleSeconds.setText(strings[3]);
            }

            @Override
            public void onFinish() {
                Logger.i("TAG_CountDownTimer", "onFinish");
            }
        };
        mCountDownTimer.start();
    }

    @Override
    protected void onLazyLoad() {
        showProgressDialog();
        addSubscription(apiStores().indexIndex(mIndex), new ApiCallback<IndexCatesChildListModel>() {
            @Override
            public void onSuccess(IndexCatesChildListModel model) {
                mIndexModel = model;
                if (mIndexModel.getCode() == 1) {
                    initXBanner();
                    initRecyclerViewTop();
                    initRecyclerViewContent();
                    initConventionCentre();
                    initFlashSale();
                } else {
                    toastShow(mIndexModel.getMsg());
                }
            }

            @Override
            public void onFailure(String msg) {
                toastShow(msg);
            }

            @Override
            public void onFinish() {
                dismissProgressDialog();
                mSmartRefreshLayout.finishRefresh(1, true);
                mSmartRefreshLayout.finishLoadMore(1, true, true);
            }
        });
    }

    @Override
    public void onClick(Object o) {
        View v = (View) o;
        switch (v.getId()) {
            case R.id.convention_centre:
                if (isLogin())
                    ConventionCentreActivity.start(mActivity);
                break;
            case R.id.flash_sale:
                FlashSaleActivity.start(mActivity);
                break;
            case R.id.luck_draw:
                //toastShow(R.string.not_yet_open_tip);
                if (isLogin())
                    LuckDrawActivity.start(mActivity);
                break;
            case R.id.flash_sale_iv_0:
                onClickFlashSaleIv(mIndexModel.getData().getLimit1().getLimit1().getId());
                break;
            case R.id.flash_sale_iv_1:
                onClickFlashSaleIv(mIndexModel.getData().getLimit1().getLimit2().getId());
                break;
            case R.id.flash_sale_iv_2:
                onClickFlashSaleIv(mIndexModel.getData().getLimit1().getLimit3().getId());
                break;
            default:
                break;
        }
    }

    private void onClickFlashSaleIv(String id) {
        if (TextUtils.isEmpty(id) || TextUtils.equals("-1", id)) {
            FlashSaleActivity.start(mActivity);
        } else {
            ProductDetailsActivity.start(mActivity, id);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (null != mXBanner)
            mXBanner.startAutoPlay();

        if (initCountDown) {
            initCountdown();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (null != mXBanner)
            mXBanner.stopAutoPlay();

        if (null != mCountDownTimer) {
            mCountDownTimer.cancel();
        }
    }
}
