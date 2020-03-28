package com.jgkj.grb.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.jgkj.grb.R;
import com.jgkj.grb.base.BaseActivity;
import com.jgkj.grb.onclick.RxView;
import com.jgkj.grb.retrofit.ApiStores;
import com.jgkj.grb.ui.dialog.ShareWeChatDialog;
import com.jgkj.grb.utils.Logger;
import com.jgkj.utils.token.utils.statusbar.StatusBarUtil;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMWeb;
import com.yzq.zxinglibrary.encode.CodeCreator;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.ExecutionException;

import butterknife.BindView;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * 我的推广
 */
public class PersonalPromotionActivity extends BaseActivity {

    public static void start(Context context) {
        Intent intent = new Intent(context, PersonalPromotionActivity.class);
        context.startActivity(intent);
    }

    @BindView(R.id.userhead)
    CircleImageView mUserhead;
    @BindView(R.id.username)
    TextView mUsername;
    @BindView(R.id.personal_promotion_tip)
    TextView mPersonalPromotionTip;
    @BindView(R.id.personal_promotion_qrcode)
    ImageView mPersonalPromotionQRCode;
    @BindView(R.id.personal_promotion_action)
    ImageView mPersonalPromotionAction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_promotion);

        //StatusBarUtil.setStatusBarDarkIconMode(this, false);
        StatusBarUtil.setStatusBar(this, Color.TRANSPARENT, 0, false);
        Toolbar toolbar = initToolBar("");
        setBackgroundColor(toolbar, Color.TRANSPARENT);
        toolbar.setNavigationIcon(R.mipmap.back_navi_icon_white);

        RxView.setOnClickListeners(this, mPersonalPromotionAction);
        new Thread(() -> {
            try {
                JSONObject userModel = new JSONObject(sharedPreferencesHelper.getSharedPreference("user", "").toString());
                Bitmap userHead = Glide.with(mActivity).asBitmap()
                        .load(ApiStores.API_SERVER_URL + userModel.optString("us_head_pic", null))
                        .centerCrop()
                        .circleCrop()
                        .submit(300, 300).get();
                runOnUiThread(() -> {
                    mUsername.setText(userModel.optString("us_nickname", ""));
                    mUserhead.setImageBitmap(userHead);
                    Bitmap promotionQRCode = CodeCreator.createQRCode(ApiStores.API_SERVER_URL_SHARE, 300, 300, userHead);
                    mPersonalPromotionQRCode.setImageBitmap(promotionQRCode);
                });
            } catch (JSONException | ExecutionException | InterruptedException e) {
                runOnUiThread(() -> {
                    Bitmap promotionQRCode = CodeCreator.createQRCode(ApiStores.API_SERVER_URL_SHARE, 300, 300, null);
                    mPersonalPromotionQRCode.setImageBitmap(promotionQRCode);
                });
            }
        }).start();
    }

    @Override
    public void onClick(Object o) {
        View v = (View) o;
        switch (v.getId()) {
            case R.id.personal_promotion_action:
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
                break;
            default:
                break;
        }
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
}
