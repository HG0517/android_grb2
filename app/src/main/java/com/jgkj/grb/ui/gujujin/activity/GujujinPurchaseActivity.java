package com.jgkj.grb.ui.gujujin.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.jgkj.grb.R;
import com.jgkj.grb.base.BaseActivity;
import com.jgkj.grb.onclick.RxView;
import com.jgkj.grb.retrofit.ApiCallback;
import com.jgkj.grb.retrofit.ApiStores;
import com.jgkj.grb.ui.activity.WebViewActivity;
import com.jgkj.grb.ui.dialog.ShareWeChatDialog;
import com.jgkj.grb.ui.gujujin.dialog.GujujinPurchaseNumDialog;
import com.jgkj.grb.ui.mvp.product.ProductDetailsModel;
import com.jgkj.grb.utils.Logger;
import com.jgkj.grb.utils.ScreenUtils;
import com.jgkj.grb.view.viewpageadapter.ProductDetailsPagerAdapter;
import com.jgkj.utils.token.GetTokenUtils;
import com.tencent.smtt.sdk.WebView;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMWeb;

import java.util.List;

import butterknife.BindView;

/**
 * 谷聚金：进货，区/县、个人，省、市没有
 */
public class GujujinPurchaseActivity extends BaseActivity {

    public static void start(Context context) {
        Intent intent = new Intent(context, GujujinPurchaseActivity.class);
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

    // 商品基本信息
    @BindView(R.id.shop_name)
    TextView mShopName;
    @BindView(R.id.total_top)
    TextView mTotalTop;
    @BindView(R.id.total_bottom)
    TextView mTotalBottom;
    @BindView(R.id.monthly_sales)
    TextView mMonthlySales;
    @BindView(R.id.monthly_stock)
    TextView mMonthlyStock;

    // 商品详情
    @BindView(R.id.product_details)
    WebView mProductDetails;

    // 客服
    @BindView(R.id.customer_service)
    FrameLayout mCustomerService;
    // 立即购买
    @BindView(R.id.buy_now)
    FrameLayout mBuyNow;

    ProductDetailsModel mModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gujujin_purchase);
        Toolbar toolbar = initToolBar("");
        setBackgroundColor(toolbar, Color.TRANSPARENT);
        toolbar.setNavigationIcon(R.mipmap.back_navi_icon_white_circle);

        // viewpager
        ViewGroup.LayoutParams layoutParams = mDetailsViewPager.getLayoutParams();
        layoutParams.width = ScreenUtils.getScreenWidth(mActivity);
        layoutParams.height = ScreenUtils.getScreenWidth(mActivity);
        mDetailsViewPager.setLayoutParams(layoutParams);

        RxView.setOnClickListeners(this, mCustomerService, mBuyNow);
        goodsDetail();
    }

    /**
     * 获取商品详情
     */
    private void goodsDetail() {
        showProgressDialog();
        String token = GetTokenUtils.getToken(this, ApiStores.API_SERVER_VALLEY_GOODS);
        addSubscription(apiStores().indexValleyGoods(token), new ApiCallback<ProductDetailsModel>() {
            @Override
            public void onSuccess(ProductDetailsModel model) {
                if (model.getCode() == 1) {
                    mModel = model;
                    initTopViewPager(model.getData().getPd_pic());
                    initDetails(model);
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
        mShopName.setText(model.getData().getPd_name());
        mTotalTop.setText(String.format(getString(R.string.total_top_text), model.getData().getPd_price()));
        mTotalBottom.setText(String.format(getString(R.string.total_top_text), model.getData().getSecend_price()));
        mMonthlySales.setText(String.format(getString(R.string.gujujin_purchase_monthly_sales), String.valueOf(model.getData().getPd_sales())));
        mMonthlyStock.setText(String.format(getString(R.string.gujujin_purchase_stock), String.valueOf(model.getData().getPd_inventory())));
        mProductDetails.loadDataWithBaseURL(null, model.getData().getPd_detail()
                        .replaceAll("<img", "<img style=\"width:100%;height:auto;\"")
                        .replaceAll("<video", "<video style=\"width:100%;height:auto;\""),
                "text/html", "UTF-8", null);
    }

    @Override
    public void onClick(Object o) {
        View v = (View) o;
        switch (v.getId()) {
            case R.id.customer_service: // 客服
                huanXinKeFu();
                break;
            case R.id.buy_now: // 立即购买
                GujujinPurchaseNumDialog dialog = GujujinPurchaseNumDialog.newInstance();
                dialog.setCancelable(false);
                dialog.showDialog(getSupportFragmentManager());
                dialog.setOnDialogListener(new GujujinPurchaseNumDialog.OnDialogListener() {
                    @Override
                    public void onCancel() {
                    }

                    @Override
                    public void onSure(String num) {
                        Logger.i("TAG_PurchaseNum", "num = " + num);
                        // 进货结算
                        GujujinPurchaseSettlementActivity.start(mActivity,
                                String.valueOf(mModel.getData().getId()),
                                String.valueOf(mModel.getData().getSkus().get(0).getId()), num);
                    }
                });
                break;
            default:
                break;
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

    /**
     * @param share_media SHARE_MEDIA.WEIXIN，好友；SHARE_MEDIA.WEIXIN_CIRCLE，朋友圈
     * @param title       标题 - text
     * @param desc        简述 - text
     * @param thumb       缩略图 - url
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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
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
        menu.findItem(R.id.menu_collect).setVisible(false);
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_product_details, menu);
        return true;
    }

}
