package com.jgkj.grb.base;

import android.app.Application;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.support.multidex.MultiDex;
import android.text.TextUtils;

import com.finddreams.languagelib.MultiLanguageUtil;
import com.hyphenate.chat.ChatClient;
import com.hyphenate.helpdesk.easeui.UIProvider;
import com.jgkj.grb.BuildConfig;
import com.jgkj.grb.R;
import com.jgkj.grb.utils.Logger;
import com.jgkj.grb.view.refresh.CustomClassicsFooter;
import com.jgkj.grb.view.refresh.CustomClassicsHeader;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.constant.SpinnerStyle;
import com.tencent.smtt.export.external.TbsCoreSettings;
import com.tencent.smtt.sdk.QbSdk;
import com.umeng.analytics.MobclickAgent;
import com.umeng.commonsdk.UMConfigure;
import com.umeng.socialize.PlatformConfig;

import java.util.HashMap;

import cn.jpush.android.api.JPushInterface;
import cn.testin.analysis.data.TestinDataApi;
import cn.testin.analysis.data.TestinDataConfig;

/**
 * GRBApplication
 * Created by brightpoplar@163.com on 2019/3/8.
 */
public class GRBApplication extends Application {

    // 全局设置使用指定的 Header 和 Footer ，优先级是最低的，将会被 XML 写法<优先级是中等>、代码动态写法<优先级最高> 方法取代
    static { // static 代码段可以防止内存泄露
        // 设置全局的 Header 构建器
        SmartRefreshLayout.setDefaultRefreshHeaderCreator((context, layout) -> {
            // 全局设置主题颜色：背景，文本
            layout.setPrimaryColorsId(R.color.srlPrimaryColor, R.color.srlAccentColor);
            // 指定为经典 Header，默认是 贝塞尔雷达 Header
            return new CustomClassicsHeader(context).setSpinnerStyle(SpinnerStyle.Translate);
        });
        // 设置全局的 Footer 构建器
        SmartRefreshLayout.setDefaultRefreshFooterCreator((context, layout) -> {
            // 全局设置主题颜色：背景，文本
            layout.setPrimaryColorsId(R.color.srlPrimaryColor, R.color.srlAccentColor);
            // 指定为经典 Footer，默认是 BallPulseFooter
            return new CustomClassicsFooter(context).setSpinnerStyle(SpinnerStyle.Translate);
        });
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    @Override
    public void onCreate() {
        super.onCreate();

        // 初始化多语言
        MultiLanguageUtil.init(this);

        // 浏览服务
        initX5();
        // 初始化 UMeng 基础库
        initUMenegCommon();
        // 初始化 UMeng 分享库
        initUMenegShare();

        // 环信
        initKeFu();
        // testin.bugout
        initTestinDataApi();
        // 极光推送
        initJPush();

        // 预先加载一级列表所有城市的数据
        //CityListLoader.getInstance().loadCityData(this);
        // 预先加载三级列表显示省市区的数据
        //CityListLoader.getInstance().loadProData(this);
    }

    /**
     * 极光推送
     */
    private void initJPush() {
        JPushInterface.setDebugMode(BuildConfig.DEBUG);
        JPushInterface.setChannel(this, getChannelValue(this));
        JPushInterface.init(this);
    }

    /**
     * testin.bugout APP 崩溃信息收集
     */
    private void initTestinDataApi() {
        String channelValue = getChannelValue(this);
        // 是否发布上线版本
        boolean isVerGRB = !BuildConfig.DEBUG && TextUtils.equals("ver_grb", channelValue);
        // 设置启动参数
        TestinDataConfig testinDataConfig = new TestinDataConfig()
                .setChannel(channelValue) // 设置 app 渠道
                .openShake(false) // 设置是否打开摇一摇反馈 bug 功能
                .setShakeSpeed(1500) // 设置摇一摇触发阈值，默认 1500，数值越低越灵敏
                .setScreenshot(false) // 设置是否开启崩溃截图功能
                .collectANR(isVerGRB) // 设置是否收集 ANR 异常信息
                .collectCrash(isVerGRB) // 设置是否收集 app 崩溃信息
                .collectNDKCrash(isVerGRB) // 设置收集 NDK 异常，需集成 bugout-ndk
                .collectLogCat(isVerGRB) // 设置是否收集 logcat 系统日志
                .collectUserSteps(isVerGRB); // 设置是否收集用户操作步骤
        // SDK 初始化
        TestinDataApi.init(this, "e92c506d11994486a11daf3c1f5b2b2a", testinDataConfig);
    }

    /**
     * 当前渠道
     */
    private String getChannelValue(Context ctx) {
        String channel = "";
        try {
            PackageManager packageManager = ctx.getPackageManager();
            if (packageManager != null) {
                ApplicationInfo applicationInfo = packageManager.getApplicationInfo(ctx.getPackageName(), PackageManager.GET_META_DATA);
                if (applicationInfo != null && applicationInfo.metaData != null) {
                    channel = String.valueOf(applicationInfo.metaData.get("TESTIN_DATA_UTM_SOURCE"));
                    Logger.i("TAG_GRBApplication", "当前的渠道为：" + channel);
                }
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            Logger.e("TAG_GRBApplication", "获取渠道失败：" + e.getMessage());
        }
        return channel;
    }

    /**
     * 环信客服
     */
    private void initKeFu() {
        ChatClient.Options options = new ChatClient.Options();
        options.setAppkey(Constants.KEFU_CHANNEL_APP_ID);//(必填项) appkey 获取地址：console.easemob.com
        options.setTenantId(Constants.KEFU_CHANNEL_TENANT_ID);//(必填项) tenantId 获取地址：kefu.easemob.com
        options.setConsoleLog(BuildConfig.DEBUG); // 设置为 true 后，将打印日志到 logcat, 发布 APP 时应关闭该选项

        // Kefu SDK 初始化
        if (!ChatClient.getInstance().init(this, options)) {
            return;
        }
        // Kefu EaseUI 的初始化
        UIProvider.getInstance().init(this);
        // 后面可以设置其他属性
    }

    /**
     * 多语言：横屏会重置语言，进行设置矫正
     *
     * @param newConfig Configuration
     */
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        MultiLanguageUtil.getInstance().setConfiguration();
    }

    private void initUMenegCommon() {
        // 初始化 UMeneg common 库
        // Context context, String appkey, String channel, int deviceType, String pushSecret
        UMConfigure.init(this, getString(R.string.umeng_appid), getString(R.string.umeng_channel), UMConfigure.DEVICE_TYPE_PHONE, getString(R.string.umeng_pushsecret));

        /**
         * 设置组件化的Log开关
         * 参数: boolean 默认为false，如需查看LOG设置为true
         */
        UMConfigure.setLogEnabled(true);
        /**
         * 设置日志加密
         * 参数：boolean 默认为false（不加密）
         */
        UMConfigure.setEncryptEnabled(true);
        MobclickAgent.setPageCollectionMode(MobclickAgent.PageMode.AUTO);
    }

    private void initUMenegShare() {
        // 配置微信到 UMeng
        PlatformConfig.setWeixin(getString(R.string.weixin_appid), getString(R.string.weixin_appsecret));
        // 配置 QQ 到 UMeng
        PlatformConfig.setQQZone(getString(R.string.qq_appid), getString(R.string.qq_appsecret));
        // 配置新浪到 UMeng ：授权回调页面地址一定要和平台上的一样，否则登录授权失败
        // PlatformConfig.setSinaWeibo(getString(R.string.sina_appid), getString(R.string.sina_appsecret), getString(R.string.sina_oauth_url));


        // 豆瓣、人人、腾讯微博，目前只能在服务器端配置 Appkey
        //        PlatformConfig.setAlipay("2015111700822536"); // Appkey
        //        PlatformConfig.setDing("dingoalmlnohc0wggfedpk"); // Fail
        //        PlatformConfig.setYixin("yxc0614e80c9304c11b0391514d09f13bf");
        //        PlatformConfig.setTwitter("3aIN7fuF685MuZ7jtXkQxalyi", "MK6FEYG63eWcpDFgRYw4w9puJhzDl0tyuqWjZ3M7XJuuG7mMbO");
        //        PlatformConfig.setKakao("e4f60e065048eb031e235c806b31c70f");
        //        PlatformConfig.setLaiwang("laiwangd497e70d4", "d497e70d4c3e4efeab1381476bac4c5e");
        //        PlatformConfig.setPinterest("1439206");
        //        PlatformConfig.setVKontakte("5764965","5My6SNliAaLxEm3Lyd9J");
        //        PlatformConfig.setDropbox("oz8v5apet3arcdy","h7p2pjbzkkxt02a");


        // 全局设置每次登录拉取确认界面：目前 SDK 默认设置为在 Token 有效期内登录不进行二次授权
        // 也可以界面内分别设置
        //        UMShareConfig config = new UMShareConfig();
        //        config.isNeedAuthOnGetUserInfo(true);
        //        UMShareAPI.get(this).setShareConfig(config);
    }

    private void initX5() {
        // 搜集本地tbs内核信息并上报服务器，服务器返回结果决定使用哪个内核。
        QbSdk.PreInitCallback cb = new QbSdk.PreInitCallback() {

            @Override
            public void onViewInitFinished(boolean arg0) {
                // x5內核初始化完成的回调，为true表示x5内核加载成功，否则表示x5内核加载失败，会自动切换到系统内核。
                Logger.i("TAG_APPAplication", " init X5 is " + arg0);
            }

            @Override
            public void onCoreInitFinished() {
            }
        };
        // 设置开启优化方案：多进程方案，在调用 TBS 初始化之前
        HashMap<String, Object> map = new HashMap<>();
        map.put(TbsCoreSettings.TBS_SETTINGS_USE_SPEEDY_CLASSLOADER, true);
        // 允许使用流量下载
        QbSdk.setDownloadWithoutWifi(true);
        QbSdk.initTbsSettings(map);
        // x5内核初始化接口
        QbSdk.initX5Environment(getApplicationContext(), cb);
    }
}
