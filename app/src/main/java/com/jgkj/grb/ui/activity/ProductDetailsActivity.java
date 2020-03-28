package com.jgkj.grb.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.CardView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jgkj.grb.R;
import com.jgkj.grb.base.BaseActivity;
import com.jgkj.grb.glide.GlideApp;
import com.jgkj.grb.itemdecoration.GridDividerItemDecoration;
import com.jgkj.grb.onclick.RxView;
import com.jgkj.grb.retrofit.ApiCallback;
import com.jgkj.grb.retrofit.ApiStores;
import com.jgkj.grb.ui.adapter.IndexChildContentAdapter;
import com.jgkj.grb.ui.bean.SpecsModel;
import com.jgkj.grb.ui.dialog.CouponBottomDialog;
import com.jgkj.grb.ui.dialog.ShareWeChatDialog;
import com.jgkj.grb.ui.dialog.SpecsBottomDialog;
import com.jgkj.grb.ui.mvp.product.ProductDetailsModel;
import com.jgkj.grb.utils.Logger;
import com.jgkj.grb.utils.ScreenUtils;
import com.jgkj.grb.view.datepicker.DateFormatUtils;
import com.jgkj.grb.view.viewpageadapter.ProductDetailsPagerAdapter;
import com.jgkj.utils.token.GetTokenUtils;
import com.tencent.smtt.sdk.WebView;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMWeb;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import butterknife.BindView;
import de.hdodenhof.circleimageview.CircleImageView;
import io.reactivex.Observable;

/**
 * 商品详情：普通商品、限时抢购商品、区域商品
 */
public class ProductDetailsActivity extends BaseActivity {

    public static void start(Context context) {
        Intent intent = new Intent(context, ProductDetailsActivity.class);
        context.startActivity(intent);
    }

    public static void start(Context context, String pd_id) {
        Intent intent = new Intent(context, ProductDetailsActivity.class);
        intent.putExtra("pd_id", pd_id);
        context.startActivity(intent);
    }

    // 顶部图片
    @BindView(R.id.details_viewpager)
    ViewPager mDetailsViewPager;
    // 底部图片角标
    @BindView(R.id.page_num)
    CardView mPageNum;
    @BindView(R.id.page_num_tv)
    TextView mPageNumTv;

    // 限时抢购商品：倒计时
    @BindView(R.id.countdown)
    RelativeLayout mCountdown;
    @BindView(R.id.countdown_title)
    TextView mCountdownTitle;
    @BindView(R.id.countdown_hour_tv)
    TextView mCountdownHourTv;
    @BindView(R.id.countdown_min_tv)
    TextView mCountdownMinTv;
    @BindView(R.id.countdown_second_tv)
    TextView mCountdownSecondTv;
    CountDownTimer mCountDownTimer;
    boolean initCountDown = false;

    // 商品基本信息
    @BindView(R.id.shop_name)
    TextView mShopName;
    @BindView(R.id.total_top)
    TextView mTotalTop;
    @BindView(R.id.total_market)
    TextView mTotalMarket;
    @BindView(R.id.total_bottom)
    TextView mTotalBottom;
    @BindView(R.id.total_bottom1)
    TextView mTotalBottom1;
    @BindView(R.id.total_bottom2)
    TextView mTotalBottom2;
    @BindView(R.id.monthly_sales)
    TextView mMonthlySales;

    // 领券
    @BindView(R.id.details_securities)
    FrameLayout mDetailsSecurities;
    @BindView(R.id.details_securities_tv)
    TextView mDetailsSecuritiesTv;

    // 选择商品规格
    @BindView(R.id.detaild_select_specs)
    FrameLayout mDetaildSelectSpecs;
    @BindView(R.id.detaild_select_specs_tv)
    TextView mDetaildSelectSpecsTv;

    // 区域商品：商品产地
    @BindView(R.id.detaild_place_of_origin)
    FrameLayout mDetaildPlaceOfOrigin;
    @BindView(R.id.detaild_place_of_origin_tv)
    TextView mDetaildPlaceOfOriginTv;

    // 商品评价
    @BindView(R.id.detaild_evaluation_all)
    FrameLayout mDetaildEvaluationAll;
    @BindView(R.id.detaild_evaluation_tv)
    TextView mDetaildEvaluationTv;
    @BindView(R.id.detaild_evaluation_all_tv)
    TextView mDetaildEvaluationAllTv;
    @BindView(R.id.detaild_evaluation_)
    RelativeLayout mDetaildEvaluation_;
    @BindView(R.id.detaild_evaluation_userhead)
    CircleImageView mDetaildEvaluationUserhead;
    @BindView(R.id.detaild_evaluation_username)
    TextView mDetaildEvaluationUsername;
    @BindView(R.id.detaild_evaluation_desc)
    TextView mDetaildEvaluationDesc;

    // 商品详情：商品图片
    @BindView(R.id.product_details)
    WebView mProductDetails;
    // 推荐
    @BindView(R.id.bottomHot)
    LinearLayout mBottomHot;
    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;

    // 客服
    @BindView(R.id.customer_service)
    FrameLayout mCustomerService;
    // 购物车
    @BindView(R.id.shop_cart)
    FrameLayout mShopCart;
    // 添加到购物车
    @BindView(R.id.add_to_cart)
    FrameLayout mAddToCart;
    // 立即购买
    @BindView(R.id.buy_now)
    FrameLayout mBuyNow;

    String mPdId = "";

    ProductDetailsModel mModel;
    ProductDetailsModel.DataBean.SkusBean mSkusBean;
    int mSelectNum = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);
        Intent intent = getIntent();
        if (intent.hasExtra("pd_id")) {
            mPdId = intent.getStringExtra("pd_id");
        }
        if (TextUtils.isEmpty(mPdId)) {
            toastShow(R.string.open_activity_error_option);
            onBackPressed();
            return;
        }

        Toolbar toolbar = initToolBar("");
        setBackgroundColor(toolbar, Color.TRANSPARENT);
        toolbar.setNavigationIcon(R.mipmap.back_navi_icon_white_circle);

        // viewpager
        ViewGroup.LayoutParams layoutParams = mDetailsViewPager.getLayoutParams();
        layoutParams.width = ScreenUtils.getScreenWidth(mActivity);
        layoutParams.height = ScreenUtils.getScreenWidth(mActivity);
        mDetailsViewPager.setLayoutParams(layoutParams);
        // 抗锯齿、设置中划线并加清晰
        mTotalMarket.getPaint().setAntiAlias(true);
        mTotalMarket.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG | Paint.ANTI_ALIAS_FLAG);
        mTotalMarket.getPaint().setFakeBoldText(true);

        RxView.setOnClickListeners(this, mDetailsSecurities, mDetaildSelectSpecs,
                mDetaildEvaluationAll, mCustomerService, mShopCart, mAddToCart, mBuyNow);

        goodsDetail();
    }

    /**
     * 获取商品详情
     */
    private void goodsDetail() {
        showProgressDialog();
        String userStr = sharedPreferencesHelper.getSharedPreference("user", "").toString();
        String usid = "";
        try {
            JSONObject jsonObject = new JSONObject(userStr);
            usid = jsonObject.getString("id");
        } catch (JSONException e) {
        }
        addSubscription(apiStores().goodsDetail(mPdId, usid), new ApiCallback<ProductDetailsModel>() {
            @Override
            public void onSuccess(ProductDetailsModel model) {
                mModel = model;
                if (model.getCode() == 1) {
                    initTopViewPager(model.getData().getPd_pic());
                    initDetails(model);
                    initRecyclerView(model);
                    invalidateOptionsMenu();
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
     * 商品详情：顶部图片，ViewPager
     */
    private void initTopViewPager(List<String> list) {
        if (null != list && list.size() > 0) {
            mDetailsViewPager.setVisibility(View.VISIBLE);
            mPageNum.setVisibility(View.VISIBLE);
            mPageNumTv.setText(String.format(getString(R.string.page_num_text), 1, list.size()));
            mDetailsViewPager.setAdapter(new ProductDetailsPagerAdapter(this, list));
            mDetailsViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int i, float v, int i1) {
                }

                @Override
                public void onPageSelected(int i) {
                    mPageNumTv.setText(String.format(getString(R.string.page_num_text), i + 1, list.size()));
                }

                @Override
                public void onPageScrollStateChanged(int i) {
                }
            });
        } else {
            mDetailsViewPager.setVisibility(View.GONE);
            mPageNum.setVisibility(View.GONE);
            mPageNumTv.setText(String.format(getString(R.string.page_num_text), 0, 0));
        }
    }

    /**
     * 商品详情：商品信息
     */
    private void initDetails(ProductDetailsModel model) {
        // ca_id  32  限时抢购，31 区域商品
        if (model.getData().getCa_id() == 32) {
            mCountdown.setVisibility(View.VISIBLE);
            mDetaildPlaceOfOrigin.setVisibility(View.GONE);
            mShopCart.setVisibility(View.GONE);
            mAddToCart.setVisibility(View.GONE);
            initCountdown();
        } else if (model.getData().getCa_id() == 31) {
            mCountdown.setVisibility(View.GONE);
            mDetaildPlaceOfOrigin.setVisibility(View.VISIBLE);
            mShopCart.setVisibility(View.VISIBLE);
            mAddToCart.setVisibility(View.VISIBLE);
            mDetaildPlaceOfOriginTv.setText(model.getData().getPd_place());
        } else {
            mCountdown.setVisibility(View.GONE);
            mDetaildPlaceOfOrigin.setVisibility(View.GONE);
            mShopCart.setVisibility(View.VISIBLE);
            mAddToCart.setVisibility(View.VISIBLE);
        }
        if (null != mModel && mModel.getData().getCoupon().size() > 0) {
            mDetailsSecurities.setVisibility(View.VISIBLE);
            mDetailsSecuritiesTv.setText(String.format(Locale.getDefault(), getString(R.string.product_details_coupon_tip),
                    mModel.getData().getCoupon().get(0).getPrice(), mModel.getData().getCoupon().get(0).getBouns()));
        } else {
            mDetailsSecurities.setVisibility(View.GONE);
        }
        mShopName.setText(model.getData().getPd_name());
        mTotalTop.setText(String.format(getString(R.string.total_top_text), model.getData().getPd_total()));
        mTotalMarket.setText(String.format(getString(R.string.total_top_text), model.getData().getPd_market_price()));
        mTotalBottom.setText(String.format(getString(R.string.total_top_text), model.getData().getPd_price()));
        mTotalBottom1.setText(String.format("GRB%s", model.getData().getPd_grb()));
        mTotalBottom2.setText(String.format("GRC%s", model.getData().getPd_grc()));
        mTotalBottom.setCompoundDrawablesWithIntrinsicBounds(model.getData().getPaytype() == 1 ? R.mipmap.ic_product_details_paytype_1 : R.mipmap.ic_product_details_paytype_0, 0, 0, 0);
        mTotalBottom1.setCompoundDrawablesWithIntrinsicBounds(model.getData().getPaytype1() == 1 ? R.mipmap.ic_product_details_paytype_1 : R.mipmap.ic_product_details_paytype_0, 0, 0, 0);
        mTotalBottom2.setCompoundDrawablesWithIntrinsicBounds(model.getData().getPaytype2() == 1 ? R.mipmap.ic_product_details_paytype_1 : R.mipmap.ic_product_details_paytype_0, 0, 0, 0);
        mMonthlySales.setText(String.format(getString(R.string.monthly_sales_text), model.getData().getPd_sales()));
        mDetaildEvaluationTv.setText(String.format(getString(R.string.detaild_evaluation_text), model.getData().getCount()));
        mDetaildEvaluationAll.setEnabled(model.getData().getCount() > 0);
        if (model.getData().getComment().size() > 0) {
            GlideApp.with(mActivity).load(ApiStores.API_SERVER_URL + model.getData().getComment().get(0).getHead_pic()).into(mDetaildEvaluationUserhead);
            mDetaildEvaluationUsername.setText(model.getData().getComment().get(0).getName());
            mDetaildEvaluationDesc.setText(model.getData().getComment().get(0).getContent());
            mDetaildEvaluation_.setVisibility(View.VISIBLE);
        } else {
            mDetaildEvaluation_.setVisibility(View.GONE);
        }
        mProductDetails.loadDataWithBaseURL(null, model.getData().getPd_detail()
                        .replaceAll("<img", "<img style=\"width:100%;height:auto;\"")
                        .replaceAll("<video", "<video style=\"width:100%;height:auto;\""),
                "text/html", "UTF-8", null);
    }

    private String getTotalBottom(ProductDetailsModel model) {
        // "paytype2": 是否有grc支付；
        // "paytype1": 是否有grb支付；,
        // "paytype": 是否有现金支付,
        String payType = "";
        /*if (model.getData().getPaytype() == 1) {
            payType += model.getData().getPd_price() + "现金";
        }*/
        if (model.getData().getPaytype1() == 1) {
            payType += /*"\n" + */model.getData().getPd_grb() + "份GRB";
        }
        if (model.getData().getPaytype2() == 1) {
            payType += (model.getData().getPaytype1() == 1 ? "\n" : "") + model.getData().getPd_grc() + "份GRC";
        }
        return payType;
    }

    private String getTotalBottom(ProductDetailsModel model, ProductDetailsModel.DataBean.SkusBean modelSkus) {
        // "paytype2": 是否有grc支付；
        // "paytype1": 是否有grb支付；,
        // "paytype": 是否有现金支付,
        String payType = "";
        /*if (model.getData().getPaytype() == 1) {
            payType += modelSkus.getSku_price() + "现金";
        }*/
        if (model.getData().getPaytype1() == 1) {
            payType += /*"\n" + */modelSkus.getSku_grb() + "份GRB";
        }
        if (model.getData().getPaytype2() == 1) {
            payType += (model.getData().getPaytype1() == 1 ? "\n" : "") + modelSkus.getSku_grc() + "份GRC";
        }
        return payType;
    }

    /**
     * 推荐
     */
    private void initRecyclerView(ProductDetailsModel model) {
        mRecyclerView.setNestedScrollingEnabled(false);
        mRecyclerView.setLayoutManager(new GridLayoutManager(mActivity, 2, GridLayoutManager.VERTICAL, false));
        mRecyclerView.addItemDecoration(new GridDividerItemDecoration(dp2px(7), Color.TRANSPARENT));
        IndexChildContentAdapter mContentAdapter = new IndexChildContentAdapter(mActivity, model.getData().getHots());
        mRecyclerView.setAdapter(mContentAdapter);
        mContentAdapter.setOnItemClickListener((view, position) -> {
            ProductDetailsActivity.start(mActivity, String.valueOf(model.getData().getHots().get(position).getId()));
        });
    }

    private void initCountdown() {
        if (null != mCountDownTimer) {
            mCountDownTimer.cancel();
        }
        initCountDown = true;

        long currentTime = System.currentTimeMillis();
        String secondToDate = DateFormatUtils.secondToDate(TextUtils.isEmpty(mModel.getData().getLimitime()) ? currentTime : Long.parseLong(mModel.getData().getLimitime()),
                DateFormatUtils.DATE_FORMAT_PATTERN_YMD_HMS);
        long secondTime = DateFormatUtils.str2Long(secondToDate, DateFormatUtils.DATE_FORMAT_PATTERN_YMD_HMS);
        Logger.i("TAG_ProductDetail", DateFormatUtils.long2Str(currentTime, DateFormatUtils.DATE_FORMAT_PATTERN_YMD_HMS));
        Logger.i("TAG_ProductDetail", secondToDate);
        long diff = currentTime - secondTime;
        if (diff > 0) { // 已结束
            mCountdownHourTv.setText("00");
            mCountdownMinTv.setText("00");
            mCountdownSecondTv.setText("00");
            return;
        }

        mCountDownTimer = new CountDownTimer(Math.abs(diff), 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                String[] strings = DateFormatUtils.millisDiff(millisUntilFinished);
                Logger.i("TAG_ProductDetail_onTick", millisUntilFinished + " , " + strings[0] + " , " + strings[1] + " , " + strings[2] + " , " + strings[3]);
                mCountdownHourTv.setText(strings[1]);
                mCountdownMinTv.setText(strings[2]);
                mCountdownSecondTv.setText(strings[3]);
            }

            @Override
            public void onFinish() {
                Logger.i("TAG_CountDownTimer", "onFinish");
            }
        };
        mCountDownTimer.start();
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

    /**
     * 修改收藏状态
     */
    private void updateCollection() {
        showProgressDialog();
        String userStr = sharedPreferencesHelper.getSharedPreference("user", "").toString();
        String usid = "";
        try {
            JSONObject jsonObject = new JSONObject(userStr);
            usid = jsonObject.getString("id");
        } catch (JSONException e) {
        }
        Observable<String> collection = apiStores().goodsCollection(mPdId, usid);// 添加收藏
        Observable<String> collectionCancel = apiStores().goodsCollectionCancel(mPdId, usid);// 取消收藏
        addSubscription(mModel.getData().getCollection() == 1 ? collectionCancel : collection, new ApiCallback<String>() {
            @Override
            public void onSuccess(String model) {
                try {
                    JSONObject result = new JSONObject(model);
                    if (result.optInt("code", 0) == 1) {
                        if (null != mModel) {
                            mModel.getData().setCollection(mModel.getData().getCollection() == 1 ? 0 : 1);
                        }
                        invalidateOptionsMenu();
                    }
                    toastShow(result.optJSONObject("data").optString("msg", result.optString("msg", "")));
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
            case R.id.details_securities: // 领券
                getSecurities();
                break;
            case R.id.detaild_select_specs: // 选择商品规格
                getSpecs(0);
                break;
            case R.id.detaild_evaluation_all: // 商品评价：查看全部
                ProductEvaluationActivity.start(mActivity, mModel.getData(), mSkusBean, mSelectNum);
                break;
            case R.id.customer_service: // 客服
                huanXinKeFu();
                break;
            case R.id.shop_cart: // 购物车
                ShoppingCartActivity.start(mActivity);
                break;
            case R.id.add_to_cart: // 添加到购物车
                if (isLogin())
                    userCartAdd();
                break;
            case R.id.buy_now: // 立即购买
                if (isLogin())
                    userBuyNow();
                break;
            default:
                break;
        }
    }

    /**
     * 立即购买
     */
    private void userBuyNow() {
        if (null == mSkusBean) {
            getSpecs(2);
            toastShow(R.string.product_details_spec_tip);
            return;
        }
        SettlementActivity.start(mActivity, String.valueOf(mModel.getData().getId()), String.valueOf(mSkusBean.getId()), String.valueOf(mSelectNum));
        /*// is_realname  实名情况：0:未认证  1认证中 2已认证
        int is_realname = isRealname();
        if (is_realname == 2) {
            SettlementActivity.start(mActivity, String.valueOf(mModel.getData().getId()), String.valueOf(mSkusBean.getId()), String.valueOf(mSelectNum));
        } else if (is_realname == 1) {
            ApplyResultActivity.start(mActivity, 2);
        } else if (is_realname == 3) {
            showUnrealNameDialog(getString(R.string.real_name_rejected));
        } else {
            showUnrealNameDialog();
        }*/
    }

    /**
     * 添加到购物车：pd_id 商品id，sku_id 规格id，num 商品数量，pd_spec  规格名称
     */
    private void userCartAdd() {
        if (null == mModel) {
            toastShow(R.string.product_details_cart_add_error_tip);
            return;
        }
        if (null == mSkusBean) {
            getSpecs(1);
            toastShow(R.string.product_details_spec_tip);
            return;
        }

        showProgressDialog();
        String token = GetTokenUtils.getToken(this, ApiStores.API_SERVER_CART_ADD);
        Map<String, Object> body = new HashMap<>();
        body.put("pd_id", mModel.getData().getId());
        body.put("sku_id", mSkusBean.getId());
        body.put("num", Math.max(mSelectNum, 1));
        body.put("pd_spec", mSkusBean.getSku_name());
        addSubscription(apiStores().userCartAdd(token, body), new ApiCallback<String>() {
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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_collect: // 收藏
                if (isLogin())
                    updateCollection();
                break;
            case R.id.menu_share: // 分享
                showShareWeChatDialog();
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        menu.findItem(R.id.menu_collect).setIcon(null != mModel && mModel.getData().getCollection() == 1 ? R.mipmap.ic_product_details_collected : R.mipmap.ic_product_details_collect);
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_product_details, menu);
        return true;
    }

    /**
     * @param share_media SHARE_MEDIA.WEIXIN，好友；SHARE_MEDIA.WEIXIN_CIRCLE，朋友圈
     * @param title       标题 - text
     * @param desc        简述 - text
     * @param thumb       缩略图 - url  or  res
     * @param web         链接 - url
     */
    private void shareWeChatWithWeb(SHARE_MEDIA share_media, String title, String desc, String thumb, String web) {
        final ShareAction shareAction = new ShareAction(this);
        shareAction.setPlatform(share_media)
                .withMedia(createUMWeb(title, desc, thumb, web))
                .setCallback(shareListener)
                .share();
    }

    // 创建分享内容：UMWeb
    @NonNull
    private UMWeb createUMWeb(String title, String desc, String thumb, String web) {
        UMImage umImage = TextUtils.isEmpty(thumb) ? new UMImage(this, R.mipmap.ic_launcher) : new UMImage(this, thumb);
        UMWeb umWeb = new UMWeb(web); // 当前内容指向的链接地址
        umWeb.setTitle(title); // 当前内容的标题
        umWeb.setThumb(umImage); // 当前内容的缩略图
        umWeb.setDescription(desc); // 当前内容的描述
        return umWeb;
    }

    // 分享回调监听
    protected UMShareListener shareListener = new UMShareListener() {
        @Override
        public void onStart(SHARE_MEDIA share_media) {
            Logger.i("TAG", "分享开始：" + share_media);
        }

        @Override
        public void onResult(SHARE_MEDIA share_media) {
            Logger.i("TAG", "分享完成：" + share_media);
        }

        @Override
        public void onError(SHARE_MEDIA share_media, Throwable throwable) {
            Logger.i("TAG", "分享失败：" + share_media + "，" + throwable.getMessage());
        }

        @Override
        public void onCancel(SHARE_MEDIA share_media) {
            // 微信貌似没有
            Logger.i("TAG", "分享取消：" + share_media);
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
    }

    /**
     * 领券弹出框
     */
    private void getSecurities() {
        if (null != mModel && mModel.getData().getCoupon().size() > 0) {
            CouponBottomDialog bottomListDialog = CouponBottomDialog.newInstance((ArrayList<ProductDetailsModel.DataBean.CouponBean>) mModel.getData().getCoupon());
            bottomListDialog.showDialog(getSupportFragmentManager());
            bottomListDialog.setOnBottomDialogListener(new CouponBottomDialog.BottomDialogListener() {
                @Override
                public void onConfirm() {
                }

                @Override
                public void onItemClick(int position) {
                    if (!mModel.getData().getCoupon().get(position).isSecurity()) {
                        mModel.getData().getCoupon().get(position).setSecurity(true);
                        bottomListDialog.getRecyclerView().getAdapter().notifyDataSetChanged();
                    }
                }
            });
        }
    }

    /**
     * 选择规格弹出框：0 选择，1 添加到购物车， 2 立即购买
     */
    private void getSpecs(int type) {
        if (null != mModel.getData() && null != mModel.getData().getSkus() && mModel.getData().getSkus().size() > 0) {
            SpecsModel specsModel = new SpecsModel();
            List<SpecsModel.SpecsBean> list = new ArrayList<>();
            SpecsModel.SpecsBean specsBean = new SpecsModel.SpecsBean();
            specsBean.setId(0);
            specsBean.setName(getString(R.string.apply_after_exchange_specs));
            specsBean.setList(mModel.getData().getSkus());
            list.add(specsBean);
            specsModel.setList(list);

            SpecsBottomDialog dialog = SpecsBottomDialog.newInstance(R.mipmap.ic_close_circle_white, specsModel, mModel.getData());
            dialog.showDialog(getSupportFragmentManager());
            dialog.setOnBottomDialogListener(new SpecsBottomDialog.BottomDialogListener() {
                @Override
                public void onCancel() {
                }

                @Override
                public void onBuyClickListener() {
                    Logger.i("TAG_", "选择规格 onConfirm：" + type);
                    if (type == 1) {
                        userCartAdd();
                    } else if (type == 2) {
                        userBuyNow();
                    }
                }

                @Override
                public void onNumChangedListener(int count) {
                    mSelectNum = count;
                }

                @Override
                public void OnSelectedClickListener(View view, int pIndex, int cIndex) {
                    if (cIndex == -1) {
                        mTotalTop.setText(String.format(getString(R.string.total_top_text), mModel.getData().getPd_total()));
                        mTotalBottom.setText(String.format(getString(R.string.total_top_text), mModel.getData().getPd_price()));
                        mTotalBottom1.setText(String.format("GRB%s", mModel.getData().getPd_grb()));
                        mTotalBottom2.setText(String.format("GRC%s", mModel.getData().getPd_grc()));
                        mTotalBottom.setCompoundDrawablesWithIntrinsicBounds(mModel.getData().getPaytype() == 1 ? R.mipmap.ic_product_details_paytype_1 : R.mipmap.ic_product_details_paytype_0, 0, 0, 0);
                        mTotalBottom1.setCompoundDrawablesWithIntrinsicBounds(mModel.getData().getPaytype1() == 1 ? R.mipmap.ic_product_details_paytype_1 : R.mipmap.ic_product_details_paytype_0, 0, 0, 0);
                        mTotalBottom2.setCompoundDrawablesWithIntrinsicBounds(mModel.getData().getPaytype2() == 1 ? R.mipmap.ic_product_details_paytype_1 : R.mipmap.ic_product_details_paytype_0, 0, 0, 0);
                        dialog.setTotalTop(mModel.getData().getPd_total());
                        dialog.setTotalBottom(dialog.getTotalBottom(mModel.getData()));
                        mDetaildSelectSpecsTv.setText(getString(R.string.select_specs_text));
                    } else {
                        mSkusBean = mModel.getData().getSkus().get(cIndex);
                        mTotalTop.setText(String.format(getString(R.string.total_top_text), mSkusBean.getSku_total()));
                        mTotalBottom.setText(String.format(getString(R.string.total_top_text), mSkusBean.getSku_price()));
                        mTotalBottom1.setText(String.format("GRB%s", mSkusBean.getSku_grb()));
                        mTotalBottom2.setText(String.format("GRC%s", mSkusBean.getSku_grc()));
                        mTotalBottom.setCompoundDrawablesWithIntrinsicBounds(mModel.getData().getPaytype() == 1 ? R.mipmap.ic_product_details_paytype_1 : R.mipmap.ic_product_details_paytype_0, 0, 0, 0);
                        mTotalBottom1.setCompoundDrawablesWithIntrinsicBounds(mModel.getData().getPaytype1() == 1 ? R.mipmap.ic_product_details_paytype_1 : R.mipmap.ic_product_details_paytype_0, 0, 0, 0);
                        mTotalBottom2.setCompoundDrawablesWithIntrinsicBounds(mModel.getData().getPaytype2() == 1 ? R.mipmap.ic_product_details_paytype_1 : R.mipmap.ic_product_details_paytype_0, 0, 0, 0);
                        dialog.setTotalTop(mSkusBean.getSku_total());
                        dialog.setTotalBottom(dialog.getTotalBottom(mModel.getData(), mSkusBean));
                        mDetaildSelectSpecsTv.setText(mSkusBean.getSku_name());
                    }
                }
            });
        }
    }

    /**
     * 分享弹出框
     */
    private void showShareWeChatDialog() {
        ShareWeChatDialog dialog = new ShareWeChatDialog(this);
        dialog.show();
        dialog.setOnActionClickListener(new ShareWeChatDialog.OnActionClickListener() {
            @Override
            public void onCancel() {
            }

            @Override
            public void onShareWeChatCircle() {
                shareWeChatWithWeb(SHARE_MEDIA.WEIXIN_CIRCLE,
                        getString(R.string.personal_promotion_share_title),
                        getString(R.string.personal_promotion_share_desc),
                        getString(R.string.personal_promotion_share_thumb),
                        ApiStores.API_SERVER_URL_SHARE);
            }

            @Override
            public void onShareWeChat() {
                shareWeChatWithWeb(SHARE_MEDIA.WEIXIN,
                        getString(R.string.personal_promotion_share_title),
                        getString(R.string.personal_promotion_share_desc),
                        getString(R.string.personal_promotion_share_thumb),
                        ApiStores.API_SERVER_URL_SHARE);
            }
        });
    }
}
