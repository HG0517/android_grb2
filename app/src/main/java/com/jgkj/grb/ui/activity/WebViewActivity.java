package com.jgkj.grb.ui.activity;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.JavascriptInterface;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.jgkj.grb.R;
import com.jgkj.grb.base.BaseActivity;
import com.jgkj.grb.eventbus.RefreshUserInfo;
import com.jgkj.grb.retrofit.ApiStores;
import com.jgkj.grb.ui.dialog.ShareWeChatDialog;
import com.jgkj.grb.utils.CameraProvider;
import com.jgkj.grb.utils.Logger;
import com.jgkj.grb.view.webview.WebViewTool;
import com.jgkj.utils.token.utils.statusbar.StatusBarUtil;
import com.tencent.smtt.sdk.ValueCallback;
import com.tencent.smtt.sdk.WebChromeClient;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMWeb;
import com.xgr.easypay.callback.IPayCallback;
import com.yanzhenjie.permission.AndPermission;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import butterknife.BindView;

public class WebViewActivity extends BaseActivity {

    public static void start(Context context, String url, String title) {
        Intent intent = new Intent(context, WebViewActivity.class);
        intent.putExtra("url", url);
        intent.putExtra("title", title);
        context.startActivity(intent);
    }

    WebView mWebView;
    @BindView(R.id.contentPanel)
    FrameLayout mContentPanel;

    private ValueCallback<Uri[]> uploadMessageAboveL;
    private final static int FILE_CHOOSER_RESULT_CODE = 128;
    private final static int FILE_CAMERA_RESULT_CODE = 129;
    private String cameraFielPath = "";

    String mUrl = "";
    String mTitle = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);

        Intent intent = getIntent();
        if (intent.hasExtra("url")) {
            mUrl = intent.getStringExtra("url");
        } else {
            toastShow("打开链接错误，浏览页面失败！");
            onBackPressed();
            return;
        }
        if (intent.hasExtra("title")) {
            mTitle = intent.getStringExtra("title");
        }
        if (mUrl.startsWith(ApiStores.API_SERVER_URL_GAME)) {
            //StatusBarUtil.setStatusBarDarkIconMode(this, false);
            StatusBarUtil.setStatusBar(this, Color.TRANSPARENT, 0, false);
            findViewById(R.id.topPanel).setVisibility(View.GONE);
        } else {
            initToolBar(mTitle);
        }

        initWebView();
    }

    private void initWebView() {
        mWebView = new WebView(this);
        mContentPanel.removeAllViews();
        mContentPanel.addView(mWebView);
        WebViewTool.webSettings(this, mWebView);
        mWebView.loadUrl(mUrl);
        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                Logger.i("TAG", "onPageStarted: url = " + url);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                Logger.i("TAG", "onPageFinished: url = " + url);
                if (!mWebView.getSettings().getLoadsImagesAutomatically()) {
                    mWebView.getSettings().setLoadsImagesAutomatically(true);
                }
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String s) {
                Logger.i("TAG", "shouldOverrideUrlLoading: url = " + s);
                // 从 APP 外回来
                if (s.startsWith("appopen://grb.com")) {
                    onBackPressed();
                    return true;
                }
                // 淘宝、QQ
                if (s.startsWith("tbopen://") || s.startsWith("mqqwpa://")) {
                    try {
                        final Intent intent = Intent.parseUri(s, Intent.URI_INTENT_SCHEME);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                        intent.addCategory("android.intent.category.BROWSABLE");
                        intent.setComponent(null);
                        if (null != intent) {
                            startActivity(intent);
                        }
                        return true;
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                if (s.toLowerCase().endsWith(".apk") && (s.toLowerCase().startsWith("http://") || s.toLowerCase().startsWith("https://"))) {
                    Uri uri = Uri.parse(s);
                    Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                    startActivity(intent);
                    return true;
                }

                if (s.startsWith("tel:")) {
                    // 直接调出界面，不需要权限
                    Intent sendIntent = new Intent(Intent.ACTION_DIAL, Uri.parse(s));
                    startActivity(sendIntent);
                    return true;
                }

                if (!(s.startsWith("http") || s.startsWith("https"))) {
                    return true;
                }
                view.loadUrl(s);
                return true;
            }
        });
        mWebView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                Logger.i("TAG", "onProgressChanged: newProgress = " + newProgress);
            }

            @Override
            public boolean onShowFileChooser(WebView webView, ValueCallback<Uri[]> filePathCallback, FileChooserParams fileChooserParams) {
                if (null != uploadMessageAboveL) {
                    uploadMessageAboveL.onReceiveValue(null);
                    uploadMessageAboveL = null;
                }
                uploadMessageAboveL = filePathCallback;
                openImageChooserActivity();
                return true;
            }
        });
        mWebView.setDownloadListener((s0, s1, s2, s3, l) -> {
            Logger.i("TAG", "setDownloadListener: s0 = " + s0 + " , s1 = " + s1 + " , s2 = " + s2 + " , s3 = " + s3 + " , long = " + l);
            Intent intent = new Intent();
            intent.setAction("android.intent.action.VIEW");
            intent.setData(Uri.parse(s0));
            startActivity(intent);
        });
    }

    /**
     * 拍照、选图上传
     */
    private void openImageChooserActivity() {
        if (!checkSDcard())
            return;

        String[] item = {"相机", "相册"};
        new AlertDialog.Builder(this)
                .setItems(item, (dialog, which) -> {
                    if (which == 0) {
                        requestOpenCamera();
                    } else if (which == 1) {
                        requestOpenAlbum();
                    }
                })
                .setPositiveButton(R.string.search_cancel, (dialog, which) -> {
                    resetLoadMessage();
                }).setCancelable(false).create().show();
    }

    /**
     * 打开相机
     */
    private void requestOpenCamera() {
        AndPermission.with(this)
                .runtime().permission(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .onGranted(data -> takeCamera())
                .onDenied(data -> toastShow(R.string.permission_ondenied))
                .start();
    }

    /**
     * 打开图库
     */
    private void requestOpenAlbum() {
        AndPermission.with(this)
                .runtime().permission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .onGranted(data -> takePhoto())
                .onDenied(data -> toastShow(R.string.permission_ondenied))
                .start();
    }

    /**
     * 拍照
     */
    private void takeCamera() {
        if (CameraProvider.isCameraCanUse()) {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            cameraFielPath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM)
                    + "/Camera/" + System.currentTimeMillis() + ".jpeg";
            File outputImage = new File(cameraFielPath);
            Uri uriForFile = Uri.fromFile(outputImage);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) { // 7.0
                uriForFile = FileProvider.getUriForFile(WebViewActivity.this,
                        WebViewActivity.this.getApplicationContext().getPackageName() + ".fileprovider", outputImage);
            }
            intent.putExtra(MediaStore.EXTRA_OUTPUT, uriForFile);
            try {
                startActivityForResult(intent, FILE_CAMERA_RESULT_CODE);
            } catch (Exception e) {
                // Permission Denial   with revoked permission android.permission.CAMERA
                Logger.i("TAG", "takeCamera: " + e.getMessage());
                if (e.getMessage().contains("Permission Denial") && e.getMessage().contains("with revoked permission android.permission.CAMERA")) {
                    toastShow(R.string.permission_ondenied);
                }
            }
        } else {
            resetLoadMessage();
        }
    }

    /**
     * 选择图片
     */
    private void takePhoto() {
        Intent i = new Intent(Intent.ACTION_GET_CONTENT);
        i.addCategory(Intent.CATEGORY_OPENABLE);
        i.setType("image/*");
        startActivityForResult(Intent.createChooser(i, "Image Chooser"), FILE_CHOOSER_RESULT_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
        if (requestCode == FILE_CAMERA_RESULT_CODE) { // 拍照结果
            if (null == uploadMessageAboveL)
                return;
            if (resultCode != RESULT_OK) {
                resetLoadMessage();
                return;
            }
            Uri result = null;
            if (null != data && null != data.getData()) {
                result = data.getData();
            }
            if (result == null && hasFile(cameraFielPath)) {
                result = Uri.fromFile(new File(cameraFielPath));
            }
            uploadMessageAboveL.onReceiveValue(new Uri[]{result});
            uploadMessageAboveL = null;
        } else if (requestCode == FILE_CHOOSER_RESULT_CODE) { // 选择图片结果
            if (null == uploadMessageAboveL)
                return;
            if (resultCode != RESULT_OK) {
                resetLoadMessage();
                return;
            }
            if (data != null) {
                onActivityResultAboveL(data);
            }
        }
    }

    /**
     * 选图结果 Android >= 5.0
     *
     * @param intent
     */
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void onActivityResultAboveL(Intent intent) {
        Uri[] results = null;
        if (intent != null) {
            String dataString = intent.getDataString();
            ClipData clipData = intent.getClipData();
            if (clipData != null) {
                results = new Uri[clipData.getItemCount()];
                for (int i = 0; i < clipData.getItemCount(); i++) {
                    ClipData.Item item = clipData.getItemAt(i);
                    results[i] = item.getUri();
                }
            }
            if (dataString != null)
                results = new Uri[]{Uri.parse(dataString)};
        }
        uploadMessageAboveL.onReceiveValue(results);
        uploadMessageAboveL = null;
    }

    /**
     * 释放选图资源
     */
    private void resetLoadMessage() {
        if (uploadMessageAboveL != null) {
            uploadMessageAboveL.onReceiveValue(null);
            uploadMessageAboveL = null;
        }
    }

    /**
     * 检查SD卡是否可用
     *
     * @return
     */
    public final boolean checkSDcard() {
        boolean flag = Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
        if (!flag) {
            toastShow("请插入手机存储卡再使用本功能");
        }
        return flag;
    }

    /**
     * 判断文件是否存在
     */
    public static boolean hasFile(String path) {
        try {
            File f = new File(path);
            return hasFile(f);
        } catch (Exception e) {
            return false;
        }
    }

    public static boolean hasFile(File f) {
        try {
            if (null == f || !f.exists()) {
                return false;
            }
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    /**
     * 支付
     *
     * @param payment 支付方式：3 支付宝，4 微信，5 云闪付
     * @param model   支付数据
     */
    @JavascriptInterface
    public void payment(int payment, String model) {
        Logger.i("TAG_payment", payment + "\n" + model);
        try {
            JSONObject result = new JSONObject(model);
            if (result.optInt("code", 0) == 1) {
                if (payment == 4) { // 微信支付
                    JSONObject data = result.optJSONObject("data");
                    wxpay(data, payCallback);
                } else if (payment == 3) { // 支付宝支付
                    alipay(result.optString("data", ""), payCallback);
                } else if (payment == 5) { // 云闪付支付
                    toastShow(R.string.not_yet_open_tip);
                }
            } else {
                toastShow(result.optString("msg", ""));
            }
        } catch (JSONException e) {
        }
    }

    /**
     * 支付回调
     */
    private IPayCallback payCallback = new IPayCallback() {
        @Override
        public void success() {
            showPayResult("支付成功");
            Gson gson = new Gson();
            Map<String, Object> map = new HashMap<>();
            map.put("status", true);
            map.put("statusCode", "101");
            map.put("message", "支付成功");
            webViewLoadJavaScript("paymentResult", gson.toJson(map));
        }

        @Override
        public void failed(int code, String message) {
            showPayResult("支付失败");
            Gson gson = new Gson();
            Map<String, Object> map = new HashMap<>();
            map.put("status", false);
            map.put("statusCode", "102");
            map.put("message", "支付失败");
            webViewLoadJavaScript("paymentResult", gson.toJson(map));
        }

        @Override
        public void cancel() {
            showPayResult("支付取消");
            Gson gson = new Gson();
            Map<String, Object> map = new HashMap<>();
            map.put("status", false);
            map.put("statusCode", "100");
            map.put("message", "支付取消");
            webViewLoadJavaScript("paymentResult", gson.toJson(map));
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
     * GRB -->> GRA
     */
    @JavascriptInterface
    public void personalTransferPublic() {
        PersonalGRBTransferPublicSecondActivity.start(this, 2);
        /*// is_realname  实名情况：0:未认证  1认证中 2已认证
        int is_realname = isRealname();
        if (is_realname == 2) {
            PersonalGRBTransferPublicSecondActivity.start(this, 2);
        } else if (is_realname == 1) {
            ApplyResultActivity.start(mActivity, 2);
        } else if (is_realname == 3) {
            showUnrealNameDialog(getString(R.string.real_name_rejected));
        } else {
            showUnrealNameDialog();
        }*/
    }

    /**
     * 分享弹出框
     */
    @JavascriptInterface
    public void showShareWeChatDialog() {
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

    /**
     * 分享图片到微信，选择微信好友或者朋友圈在 js 界面处理
     *
     * @param share_media 0 微信好友，1 微信朋友圈
     * @param img         图片数据
     */
    @JavascriptInterface
    public void shareWeChatWithImg(int share_media, String img) {
        if (share_media == 0) {
            shareWeChatWithImg(SHARE_MEDIA.WEIXIN, img);
        } else if (share_media == 1) {
            shareWeChatWithImg(SHARE_MEDIA.WEIXIN_CIRCLE, img);
        }
    }

    /**
     * 分享图片给好友
     *
     * @param img 图片数据
     */
    @JavascriptInterface
    public void shareWeixinWithImg(String img) {
        Logger.e("TAG", "shareWeixinWithImg in android");
        shareWeChatWithImg(SHARE_MEDIA.WEIXIN, img);
    }

    /**
     * 分享图片到朋友圈
     *
     * @param img 图片数据
     */
    @JavascriptInterface
    public void shareWeixinCircleWithImg(String img) {
        Logger.e("TAG", "shareWeixinCircleWithImg in android");
        shareWeChatWithImg(SHARE_MEDIA.WEIXIN_CIRCLE, img);
    }

    /**
     * 分享图片到微信
     *
     * @param img 图片数据
     */
    @JavascriptInterface
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
        new Thread(() -> {
            try {
                Bitmap resource = Glide.with(WebViewActivity.this).asBitmap().load(img).submit().get();
                if (resource != null) {
                    final UMImage umImage = new UMImage(WebViewActivity.this, resource);
                    // final UMImage umThumb = new UMImage(WebViewActivity.this, R.mipmap.ic_launcher);
                    Bitmap bitmap = ThumbnailUtils.extractThumbnail(resource, resource.getWidth() / 10, resource.getHeight() / 10);
                    final UMImage umThumb = new UMImage(WebViewActivity.this, bitmap);
                    umImage.setThumb(umThumb);

                    runOnUiThread(() -> {
                        final ShareAction shareAction = new ShareAction(WebViewActivity.this);
                        shareAction.setPlatform(share_media)
                                .withText(getResources().getString(R.string.app_name))
                                .withMedia(umImage)
                                .setCallback(shareListener)
                                .share();
                    });
                } else {
                    Toast.makeText(WebViewActivity.this, "分享异常，重新分享试试。", Toast.LENGTH_LONG).show();
                }
            } catch (InterruptedException e) {
                Toast.makeText(WebViewActivity.this, "分享异常，重新分享试试。", Toast.LENGTH_LONG).show();
            } catch (ExecutionException e) {
                Toast.makeText(WebViewActivity.this, "分享异常，重新分享试试。", Toast.LENGTH_LONG).show();
            }
        }).start();
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

    private void webViewLoadJavaScript(final String function, final String result) {
        Logger.e("TAG", "function = " + function + " ,result = " + result);
        runOnUiThread(() -> mWebView.loadUrl("javascript:" + function + "(" + "'" + result + "'" + ")"));
    }

    @Override
    protected void onDestroy() {
        if (mWebView != null) {
            // 加载null内容
            mWebView.loadDataWithBaseURL(null, "", "text/html", "utf-8", null);
            // 清除历史记录
            mWebView.clearHistory();
            // 移除WebView
            ((ViewGroup) mWebView.getParent()).removeView(mWebView);
            // 销毁VebView
            mWebView.destroy();
            // WebView置为null
            mWebView = null;
        }
        super.onDestroy();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (null != mWebView && mWebView.canGoBack()) {
                mWebView.goBack();
            } else {
                return super.onKeyDown(keyCode, event);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
