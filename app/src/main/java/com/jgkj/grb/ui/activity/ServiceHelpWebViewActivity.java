package com.jgkj.grb.ui.activity;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AlertDialog;
import android.view.KeyEvent;
import android.widget.FrameLayout;

import com.jgkj.grb.R;
import com.jgkj.grb.base.BaseActivity;
import com.jgkj.grb.retrofit.ApiCallback;
import com.jgkj.grb.retrofit.ApiStores;
import com.jgkj.grb.utils.CameraProvider;
import com.jgkj.grb.utils.Logger;
import com.jgkj.grb.view.webview.WebViewTool;
import com.jgkj.utils.token.GetTokenUtils;
import com.tencent.smtt.sdk.ValueCallback;
import com.tencent.smtt.sdk.WebChromeClient;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;
import com.yanzhenjie.permission.AndPermission;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;

import butterknife.BindView;

public class ServiceHelpWebViewActivity extends BaseActivity {

    public static void start(Context context, String id) {
        Intent intent = new Intent(context, ServiceHelpWebViewActivity.class);
        intent.putExtra("id", id);
        context.startActivity(intent);
    }

    WebView mWebView;
    @BindView(R.id.contentPanel)
    FrameLayout mContentPanel;

    private ValueCallback<Uri[]> uploadMessageAboveL;
    private final static int FILE_CHOOSER_RESULT_CODE = 128;
    private final static int FILE_CAMERA_RESULT_CODE = 129;
    private String cameraFielPath = "";

    String mId = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);

        Intent intent = getIntent();
        if (intent.hasExtra("id")) {
            mId = intent.getStringExtra("id");
        } else {
            toastShow(R.string.open_activity_error_option);
            onBackPressed();
            return;
        }
        initToolBar("");

        initWebView();
        onLazyLoad();
    }

    private void onLazyLoad() {
        showProgressDialog();
        String token = GetTokenUtils.getToken(this, ApiStores.API_SERVER_INDEX_HELP);
        addSubscription(apiStores().indexHelp(token, mId), new ApiCallback<String>() {
            @Override
            public void onSuccess(String model) {
                try {
                    JSONObject result = new JSONObject(model);
                    if (result.getInt("code") == 1) {
                        mWebView.loadDataWithBaseURL(null, result.getString("data")
                                        .replaceAll("<img", "<img style=\"width:100%;height:auto;\"")
                                        .replaceAll("<video", "<video style=\"width:100%;height:auto;\""),
                                "text/html", "UTF-8", null);
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

    private void initWebView() {
        mWebView = new WebView(this);
        mContentPanel.removeAllViews();
        mContentPanel.addView(mWebView);
        WebViewTool.webSettings(this, mWebView);
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
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                if (url.startsWith("http:") || url.startsWith("https:")) {
                    view.loadUrl(url);
                    return false;
                }
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

        String item[] = {"相机", "相册"};
        new AlertDialog.Builder(this)
                .setItems(item, (dialog, which) -> {
                    if (which == 0) {
                        requestOpenCamera();
                    } else if (which == 1) {
                        requestOpenAlbum();
                    }
                })
                .setPositiveButton(getString(R.string.search_cancel), (dialog, which) -> {
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
                uriForFile = FileProvider.getUriForFile(ServiceHelpWebViewActivity.this,
                        ServiceHelpWebViewActivity.this.getApplicationContext().getPackageName() + ".fileprovider", outputImage);
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
            if (uploadMessageAboveL != null) {
                uploadMessageAboveL.onReceiveValue(new Uri[]{result});
                uploadMessageAboveL = null;
            }
        } else if (requestCode == FILE_CHOOSER_RESULT_CODE) { // 选择图片结果
            if (null == uploadMessageAboveL)
                return;
            if (resultCode != RESULT_OK) {
                resetLoadMessage();
                return;
            }
            Uri result = null;
            if (data != null) {
                result = data.getData();
            }
            if (uploadMessageAboveL != null) {
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
