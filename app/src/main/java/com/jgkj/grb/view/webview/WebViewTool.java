package com.jgkj.grb.view.webview;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.os.Build;

import com.tencent.smtt.sdk.WebSettings;
import com.tencent.smtt.sdk.WebView;

/**
 * Created by brightpoplar@163.com on 2019/8/28.
 */
public class WebViewTool {

    @SuppressLint("SetJavaScriptEnabled")
    public static void webSettings(Activity activity, WebView webview) {
        WebSettings webSettings = webview.getSettings();

        webSettings.setUserAgentString(Build.MODEL + "/Android " + Build.VERSION.RELEASE + "/" + webSettings.getUserAgentString());
        webSettings.setJavaScriptEnabled(true); //设置支持javascript脚本
        webview.addJavascriptInterface(activity, "android");
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true); //设置允许JS弹窗
        webSettings.setDomStorageEnabled(true); //是否开启本地DOM存储，鉴于它的安全特性（任何人都能读取到它，尽管有相应的限制，将敏感数据存储在这里依然不是明智之举），Android 默认是关闭该功能的。
        webSettings.setDatabaseEnabled(true);
        webSettings.setAppCacheEnabled(true);
        final String dir = activity.getDir("database", Context.MODE_PRIVATE).getPath();
        webSettings.setGeolocationDatabasePath(dir);
        webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            webSettings.setLoadsImagesAutomatically(true);//图片自动缩放 打开
        } else {
            webSettings.setLoadsImagesAutomatically(false);//图片自动缩放 关闭
        }
        webSettings.setSupportZoom(false);// 设置可以支持缩放
        webSettings.setBuiltInZoomControls(false);// 设置出现缩放工具 是否使用WebView内置的缩放组件，由浮动在窗口上的缩放控制和手势缩放控制组成，默认false
        webSettings.setDisplayZoomControls(false);//隐藏缩放工具
        webSettings.setUseWideViewPort(false);// 扩大比例的缩放
        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NORMAL);//自适应屏幕
        webSettings.setLoadWithOverviewMode(false);
        webSettings.setSavePassword(true);//保存密码
        webSettings.setSaveFormData(true); // 设置 webview 保存表单数据
        webSettings.setGeolocationEnabled(true);
        webSettings.setAllowFileAccess(true); // 设置可以访问文件
        webSettings.setSupportMultipleWindows(true);
        // 允许Https + Http混用  5.0
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            webSettings.setMixedContentMode(WebSettings.LOAD_DEFAULT);
        }

        final String dbPath = activity.getDir("web_db", Context.MODE_PRIVATE).getPath();
        webSettings.setDatabasePath(dbPath);
        final String cachePath = activity.getDir("web_cache", Context.MODE_PRIVATE).getPath();
        webSettings.setAppCachePath(cachePath);
        webSettings.setAppCacheMaxSize(5 * 1024 * 1024);
        webSettings.setAllowContentAccess(true);
        webSettings.setAllowFileAccessFromFileURLs(true);
        webSettings.setMediaPlaybackRequiresUserGesture(false); //是否需要用户手势来播放Media，默认true
    }
}
