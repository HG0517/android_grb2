package com.jgkj.grb.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.FrameLayout;

import com.bumptech.glide.Glide;
import com.jgkj.grb.R;
import com.jgkj.grb.base.BaseActivity;
import com.jgkj.grb.onclick.RxView;
import com.jgkj.grb.retrofit.ApiCallback;
import com.jgkj.grb.retrofit.ApiStores;
import com.jgkj.grb.ui.dialog.ServiceAndHelpWechatDialog;
import com.jgkj.grb.ui.dialog.ShareWeChatDialog;
import com.jgkj.grb.utils.Logger;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.ExecutionException;

import butterknife.BindView;

/**
 * 客服与帮助
 */
public class ServiceAndHelpActivity extends BaseActivity {

    public static void start(Context context) {
        Intent intent = new Intent(context, ServiceAndHelpActivity.class);
        context.startActivity(intent);
    }

    @BindView(R.id.service_help_online)
    FrameLayout mServiceHelpOnline;
    @BindView(R.id.service_help_telephone)
    FrameLayout mServiceHelpTelephone;
    @BindView(R.id.service_help_wechat)
    FrameLayout mServiceHelpWechat;

    @BindView(R.id.service_help_intro_agree_top)
    FrameLayout mServiceHelpIntroAgreeTop;
    @BindView(R.id.service_help_intro_agree_bottom)
    FrameLayout mServiceHelpIntroAgreeBottom;

    @BindView(R.id.service_help_after_sale_top)
    FrameLayout mServiceHelpAfterSaleTop;
    @BindView(R.id.service_help_after_sale_bottom)
    FrameLayout mServiceHelpAfterSaleBottom;

    @BindView(R.id.service_help_order_logistics_top)
    FrameLayout mServiceHelpOrderLogisticsTop;
    @BindView(R.id.service_help_order_logistics_bottom)
    FrameLayout mServiceHelpOrderLogisticsBottom;

    @BindView(R.id.service_help_agency_system_top)
    FrameLayout mServiceHelpAgencySystemTop;
    @BindView(R.id.service_help_agency_system_bottom)
    FrameLayout mServiceHelpAgencySystemBottom;

    @BindView(R.id.service_help_notes_top)
    FrameLayout mServiceHelpNotesTop;
    @BindView(R.id.service_help_notes_bottom)
    FrameLayout mServiceHelpNotesBottom;

    String mServiceAndHelpTel = "";
    String mServiceAndHelpWeChat = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_and_help);

        Toolbar toolbar = initToolBar(getString(R.string.activity_title_service_and_help));

        RxView.setOnClickListeners(this, mServiceHelpOnline, mServiceHelpTelephone, mServiceHelpWechat,
                mServiceHelpIntroAgreeTop, mServiceHelpIntroAgreeBottom, mServiceHelpAfterSaleTop,
                mServiceHelpAfterSaleBottom, mServiceHelpOrderLogisticsTop, mServiceHelpOrderLogisticsBottom,
                mServiceHelpAgencySystemTop, mServiceHelpAgencySystemBottom, mServiceHelpNotesTop, mServiceHelpNotesBottom);

        onLazyLoad();
    }

    private void onLazyLoad() {
        addSubscription(apiStores().appGetInfo(), new ApiCallback<String>() {
            @Override
            public void onSuccess(String model) {
                // {"code":1,"msg":"成功","time":1573455008,"data":{"tel":"8888888","wechat":"\/static\/admin\/img\/wechat3.jpg"}}
                try {
                    JSONObject result = new JSONObject(model);
                    if (result.optInt("code", 0) == 1) {
                        JSONObject data = result.optJSONObject("data");
                        mServiceAndHelpTel = data.optString("tel", "");
                        mServiceAndHelpWeChat = data.optString("wechat", "");
                        sharedPreferencesConfig.putApply("ServiceAndHelpTel", mServiceAndHelpTel);
                        sharedPreferencesConfig.putApply("ServiceAndHelpWeChat", mServiceAndHelpWeChat);
                    } else {
                        mServiceAndHelpTel = sharedPreferencesConfig.getSharedPreference("ServiceAndHelpWeChat", "").toString();
                        mServiceAndHelpWeChat = sharedPreferencesConfig.getSharedPreference("ServiceAndHelpWeChat", "").toString();
                    }
                } catch (JSONException e) {
                }
            }

            @Override
            public void onFailure(String msg) {
                mServiceAndHelpTel = sharedPreferencesConfig.getSharedPreference("ServiceAndHelpWeChat", "").toString();
                mServiceAndHelpWeChat = sharedPreferencesConfig.getSharedPreference("ServiceAndHelpWeChat", "").toString();
            }

            @Override
            public void onFinish() {
            }
        });
    }

    @Override
    public void onClick(Object o) {
        View v = (View) o;
        switch (v.getId()) {
            case R.id.service_help_online: // 在线咨询
                huanXinKeFu();
                break;
            case R.id.service_help_telephone: // 电话咨询
                if (TextUtils.isEmpty(mServiceAndHelpTel)) {
                    toastShow(R.string.not_yet_open_tip);
                    return;
                }
                Intent sendIntent = new Intent(Intent.ACTION_DIAL, Uri.parse(String.format("tel:%s", mServiceAndHelpTel)));
                startActivity(sendIntent);
                break;
            case R.id.service_help_wechat: // 微信咨询
                if (TextUtils.isEmpty(mServiceAndHelpWeChat)) {
                    toastShow(R.string.not_yet_open_tip);
                    return;
                }
                ServiceAndHelpWechatDialog dialog = new ServiceAndHelpWechatDialog(this);
                dialog.show();
                dialog.setDialogIv(mServiceAndHelpWeChat);
                dialog.setOnLongClickListener(v1 -> {
                    shareWeChatWithImg(SHARE_MEDIA.WEIXIN, TextUtils.isEmpty(mServiceAndHelpWeChat) ? null :
                            (mServiceAndHelpWeChat.startsWith("http:") || mServiceAndHelpWeChat.startsWith("https:") ? mServiceAndHelpWeChat : ApiStores.API_SERVER_URL + mServiceAndHelpWeChat));
                    return true;
                });
                break;
            case R.id.service_help_intro_agree_top: // 免责声明条款 14
                ServiceHelpWebViewActivity.start(this, String.valueOf(14));
                break;
            case R.id.service_help_intro_agree_bottom: // 隐私保护条款 15
                ServiceHelpWebViewActivity.start(this, String.valueOf(15));
                break;
            case R.id.service_help_after_sale_top: // 售后政策说明 12
                ServiceHelpWebViewActivity.start(this, String.valueOf(12));
                break;
            case R.id.service_help_after_sale_bottom: // 退货政策说明 13
                ServiceHelpWebViewActivity.start(this, String.valueOf(13));
                break;
            case R.id.service_help_order_logistics_top: // 各区运费价格规则 16
                ServiceHelpWebViewActivity.start(this, String.valueOf(16));
                break;
            case R.id.service_help_order_logistics_bottom: // 云仓库提货运费规则 17
                ServiceHelpWebViewActivity.start(this, String.valueOf(17));
                break;
            case R.id.service_help_agency_system_top: // 农场代理区制度 18
                ServiceHelpWebViewActivity.start(this, String.valueOf(18));
                break;
            case R.id.service_help_agency_system_bottom: // 谷聚金代理区制度 19
                ServiceHelpWebViewActivity.start(this, String.valueOf(19));
                break;
            case R.id.service_help_notes_top: // 商城币种介绍须知 20
                ServiceHelpWebViewActivity.start(this, String.valueOf(20));
                break;
            case R.id.service_help_notes_bottom: // 商城购物流程须知 21
                ServiceHelpWebViewActivity.start(this, String.valueOf(21));
                break;
            default:
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
    }

    public void shareWeChatWithImg(final String img) {
        ShareWeChatDialog dialog = new ShareWeChatDialog(this);
        dialog.show();
        dialog.setOnActionClickListener(new ShareWeChatDialog.OnActionClickListener() {
            @Override
            public void onCancel() {
            }

            @Override
            public void onShareWeChatCircle() {
                shareWeChatWithImg(SHARE_MEDIA.WEIXIN_CIRCLE, img);
            }

            @Override
            public void onShareWeChat() {
                shareWeChatWithImg(SHARE_MEDIA.WEIXIN, img);
            }
        });
    }

    // share_media：微信好友、微信朋友圈；img 图片数据
    private void shareWeChatWithImg(final SHARE_MEDIA share_media, final String img) {
        if (TextUtils.isEmpty(img))
            return;

        new Thread(() -> {
            try {
                Bitmap resource = Glide.with(ServiceAndHelpActivity.this).asBitmap().load(img).submit().get();
                if (resource != null) {
                    final UMImage umImage = new UMImage(ServiceAndHelpActivity.this, resource);
                    boolean isNeedExtract = resource.getWidth() > getScreenWidth() || resource.getHeight() > getScreenWidth();
                    Bitmap bitmap = ThumbnailUtils.extractThumbnail(resource, isNeedExtract ? resource.getWidth() / 2 : resource.getWidth(), isNeedExtract ? resource.getHeight() / 2 : resource.getHeight());
                    final UMImage umThumb = new UMImage(ServiceAndHelpActivity.this, bitmap);
                    umImage.setThumb(umThumb);

                    runOnUiThread(() -> {
                        final ShareAction shareAction = new ShareAction(ServiceAndHelpActivity.this);
                        shareAction.setPlatform(share_media)
                                .withText(getResources().getString(R.string.app_name))
                                .withMedia(umImage)
                                .setCallback(shareListener)
                                .share();
                    });
                } else {
                    toastShow("分享异常，重新分享试试。");
                }
            } catch (InterruptedException e) {
                toastShow("分享异常，重新分享试试。");
            } catch (ExecutionException e) {
                toastShow("分享异常，重新分享试试。");
            }
        }).start();
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
}
