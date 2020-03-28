package com.jgkj.grb.ui.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.ImageView;

import com.jgkj.grb.R;
import com.jgkj.grb.base.BaseActivity;
import com.jgkj.grb.glide.GlideApp;
import com.jgkj.grb.utils.Logger;
import com.jgkj.utils.token.utils.statusbar.StatusBarUtil;

import butterknife.BindView;

/**
 * 应用启动过渡界面
 * Created by jugekeji on 2019/9/21.
 */
public class SplashActivity extends BaseActivity implements Handler.Callback {
    private static final String TAG = "TAG_" + SplashActivity.class.getSimpleName();

    @BindView(R.id.ivSplash)
    ImageView ivSplash;
    private Handler handler;
    private String ivSplashUrl = "";
    private int timerCount = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 避免从桌面启动程序后，会重新实例化入口类的 activitymm
        if ((getIntent().getFlags() & Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT) != 0) {
            Logger.i("TAG_LAUNCHER", "0");
            finish();
            return;
        }
        if (!this.isTaskRoot()) { //判断该Activity是不是任务空间的源Activity，“非”也就是说是被系统重新实例化出来
            //如果你就放在launcher Activity中话，这里可以直接return了
            Intent mainIntent = getIntent();
            String action = mainIntent.getAction();
            Logger.i("TAG_LAUNCHER", "10");
            if (mainIntent.hasCategory(Intent.CATEGORY_LAUNCHER) && Intent.ACTION_MAIN.equals(action)) {
                Logger.i("TAG_LAUNCHER", "11");
                finish();
                return;//finish()之后该活动会继续执行后面的代码，你可以logCat验证，加return避免可能的exception
            }
        }

        setContentView(R.layout.activity_splash);
        //StatusBarUtil.setStatusBarDarkIconMode(this, false);
        StatusBarUtil.setStatusBar(this, Color.TRANSPARENT, 0, false);
        handler = new Handler(this);

        getSplashData();
    }

    private void getSplashData() {
        GlideApp.with(SplashActivity.this)
                .load(ivSplashUrl)
                .placeholder(R.mipmap.ic_splash)
                .error(R.mipmap.ic_splash)
                .fallback(R.mipmap.ic_splash)
                .into(ivSplash);
    }

    @Override
    protected void onResume() {
        if (null != handler) {
            Logger.i(TAG, "SplashActivity onResume");
            handler.sendEmptyMessageDelayed(timerCount, 200);
        }
        super.onResume();
    }

    @Override
    protected void onPause() {
        GlideApp.with(this).pauseRequests();
        if (null != handler) {
            handler.removeMessages(0);
        }
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        Logger.i(TAG, "SplashActivity Destroy");
        if (null != handler) {
            handler.removeCallbacksAndMessages(null);
            handler = null;
        }
        super.onDestroy();
    }

    @Override
    public boolean handleMessage(Message msg) {
        if (msg.what > 0) {
            handler.sendEmptyMessageDelayed(--timerCount, 1000);
        } else {
            startApp();
            finish();
        }
        // overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        return true;
    }

    private void startApp() {
        startActivity(new Intent(this, MainActivity.class));
    }

    @Override
    public void onBackPressed() {
        // 启动页面屏蔽返回按键
        // super.onBackPressed();
    }
}