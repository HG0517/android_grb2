package com.jgkj.grb.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.jgkj.grb.R;
import com.jgkj.grb.base.BaseActivity;
import com.jgkj.grb.glide.GlideApp;
import com.jgkj.grb.retrofit.ApiCallback;
import com.jgkj.grb.retrofit.ApiStores;
import com.jgkj.grb.ui.mvp.message.MessageDetailModel;
import com.jgkj.grb.ui.mvp.message.MessageModel;
import com.jgkj.grb.view.webview.WebViewTool;
import com.jgkj.utils.token.GetTokenUtils;
import com.tencent.smtt.sdk.WebChromeClient;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;

import butterknife.BindView;

/**
 * 消息：消息详情
 */
public class MessageDetailsWebViewActivity extends BaseActivity {

    public static void start(Context context, String id) {
        Intent intent = new Intent(context, MessageDetailsWebViewActivity.class);
        intent.putExtra("id", id);
        context.startActivity(intent);
    }

    WebView mWebView;
    @BindView(R.id.message_detail_title)
    TextView mMessageDetailTitle;
    @BindView(R.id.message_type_icon)
    ImageView mMessageTypeIcon;
    @BindView(R.id.message_type_title)
    TextView mMessageTypeTitle;
    @BindView(R.id.message_type_time)
    TextView mMessageTypeTime;
    @BindView(R.id.contentPanel)
    FrameLayout mContentPanel;

    String mId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_details_webview);

        Intent intent = getIntent();
        if (intent.hasExtra("id")) {
            mId = intent.getStringExtra("id");
        }
        if (TextUtils.isEmpty(mId)) {
            toastShow(R.string.open_activity_error_option);
            onBackPressed();
            return;
        }

        Toolbar toolbar = initToolBar("");

        onLazyLoad();
    }

    private void onLazyLoad() {
        showProgressDialog();
        String token = GetTokenUtils.getToken(mActivity, ApiStores.API_SERVER_INDEX_NEWSDETAIL);
        addSubscription(apiStores().indexNewsDetail(token, mId), new ApiCallback<MessageDetailModel>() {
            @Override
            public void onSuccess(MessageDetailModel model) {
                if (model.getCode() == 1) {
                    initWebView(model.getData());
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

    private void initWebView(MessageModel.MessageBean data) {
        if (data.getType() == 1) {
            GlideApp.with(mActivity)
                    .load(R.mipmap.ic_message_system_item)
                    .into(mMessageTypeIcon);
            mMessageTypeTitle.setText(R.string.message_management_systom);
        } else if (data.getType() == 2) {
            GlideApp.with(mActivity)
                    .load(R.mipmap.ic_message_mall_item)
                    .into(mMessageTypeIcon);
            mMessageTypeTitle.setText(R.string.message_management_mall);
        } else if (data.getType() == 3) {
            GlideApp.with(mActivity)
                    .load(R.mipmap.ic_message_proxy_item)
                    .into(mMessageTypeIcon);
            mMessageTypeTitle.setText(R.string.message_management_proxy);
        }
        mMessageDetailTitle.setText(data.getTitle());
        mMessageTypeTime.setText(data.getAdd_time());

        mWebView = new WebView(this);
        mContentPanel.removeAllViews();
        mContentPanel.addView(mWebView);
        WebViewTool.webSettings(this, mWebView);
        mWebView.loadDataWithBaseURL(null, data.getContent()
                        .replaceAll("<img", "<img style=\"width:100%;height:auto;\"")
                        .replaceAll("<video", "<video style=\"width:100%;height:auto;\""),
                "text/html", "UTF-8", null);
        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
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
            }
        });
        mWebView.setDownloadListener((s0, s1, s2, s3, l) -> {
            Intent intent = new Intent();
            intent.setAction("android.intent.action.VIEW");
            intent.setData(Uri.parse(s0));
            startActivity(intent);
        });
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
