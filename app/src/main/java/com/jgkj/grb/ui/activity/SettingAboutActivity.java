package com.jgkj.grb.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.jgkj.grb.R;
import com.jgkj.grb.base.BaseActivity;
import com.jgkj.grb.onclick.RxView;
import com.jgkj.grb.retrofit.ApiStores;
import com.jgkj.grb.version.VersionUpdateHelp;

import butterknife.BindView;

/**
 * 设置：关于公让宝
 */
public class SettingAboutActivity extends BaseActivity {

    public static void start(Context context) {
        Intent intent = new Intent(context, SettingAboutActivity.class);
        context.startActivity(intent);
    }

    @BindView(R.id.setting_about_tip_0)
    TextView mSettingAboutTip0;
    @BindView(R.id.setting_about_tip_1)
    TextView mSettingAboutTip1;
    @BindView(R.id.setting_about_tip_20)
    FrameLayout mSettingAboutTip20;
    @BindView(R.id.setting_about_tip_21)
    TextView mSettingAboutTip21;
    @BindView(R.id.setting_about_tip_3)
    TextView mSettingAboutTip3;
    @BindView(R.id.setting_about_tip_4)
    TextView mSettingAboutTip4;

    PackageInfo packageInfo;
    int currentVersionCode;
    String currentVersionName;
    String appName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_about);

        Toolbar toolbar = initToolBar(getString(R.string.activity_title_setting_about));

        try {
            packageInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
            currentVersionCode = packageInfo.versionCode;
            currentVersionName = packageInfo.versionName;
            appName = packageInfo.applicationInfo.loadLabel(getPackageManager()).toString();

            /*if (currentVersionName.indexOf("_", 1) > -1)
                currentVersionName = currentVersionName.substring(0, currentVersionName.indexOf("_", 1));*/
            mSettingAboutTip1.setText(String.format(getString(R.string.setting_about_tip_1), currentVersionName));
            mSettingAboutTip21.setText(String.format(getString(R.string.setting_about_version_text), currentVersionName));
        } catch (PackageManager.NameNotFoundException e) {
        }

        RxView.setOnClickListeners(this, mSettingAboutTip20);
    }

    @Override
    public void onClick(Object o) {
        View v = (View) o;
        switch (v.getId()) {
            case R.id.setting_about_tip_20:
                VersionUpdateHelp.forceUpdate(this,
                        ApiStores.API_SERVER_URL + ApiStores.API_SERVER_APP_CONTROL,
                        currentVersionCode, "", appName);
                break;
            default:
                break;
        }
    }
}
