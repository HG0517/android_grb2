package com.jgkj.grb.version;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.support.v4.content.FileProvider;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.jgkj.grb.R;
import com.jgkj.grb.utils.Logger;
import com.jgkj.grb.utils.NetworkUtils;
import com.jgkj.grb.utils.ToastHelper;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.FileCallBack;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;

import okhttp3.Call;
import okhttp3.Request;

/**
 * 版本更新帮助类
 */
public class VersionUpdateHelp {
    private static String TAG = "TAG_" + VersionUpdateHelp.class.getSimpleName();
    public static int OS_TYPE = 1;

    /**
     * 获取版本更新数据对象
     */
    public static JSONObject getVersion(String datas) {
        try {
            return new JSONObject(datas);
        } catch (JSONException e) {
            return null;
        }
    }

    /**
     * 对比是否更新，判断 versionName：1.0.0
     *
     * @param currentVersion 当前版本
     * @param newVersion     最新版本
     * @return 是否更新
     */
    protected static boolean isUpdateVersion(String currentVersion, String newVersion) {
        if (currentVersion.equals(newVersion))
            return false;

        String[] currentV = currentVersion.split("\\.");
        String[] newV = newVersion.split("\\.");

        if (Integer.valueOf(newV[0]) > Integer.valueOf(currentV[0])) {
            return true;
        } else if (Integer.valueOf(newV[0]) < Integer.valueOf(currentV[0])) {
            return false;
        } else if (Integer.valueOf(newV[0]) == Integer.valueOf(currentV[0])) {

            if (Integer.valueOf(newV[1]) > Integer.valueOf(currentV[1])) {
                return true;
            } else if (Integer.valueOf(newV[1]) < Integer.valueOf(currentV[1])) {
                return false;
            } else if (Integer.valueOf(newV[1]) == Integer.valueOf(currentV[1])) {

                if (Integer.valueOf(newV[2]) > Integer.valueOf(currentV[2])) {
                    return true;
                } else if (Integer.valueOf(newV[2]) <= Integer.valueOf(currentV[2])) {
                    return false;
                }
            }
        }
        return false;
    }

    /**
     * 对比是否更新，判断 versionCode：1
     *
     * @param currentVersion 当前版本
     * @param newVersion     最新版本
     * @return 是否更新
     */
    protected static boolean isUpdateVersion(int currentVersion, String newVersion) {
        if (TextUtils.isEmpty(newVersion))
            return false;

        try {
            Integer.valueOf(newVersion);
        } catch (NumberFormatException e) {
            return false;
        }

        if (currentVersion <= 0 || Integer.valueOf(newVersion) <= 0)
            return false;

        if (Integer.valueOf(newVersion) > currentVersion)
            return true;

        return false;
    }

    /**
     * 对比是否更新，判断 versionCode：1  或者 versionName：1.0.0 都可以
     * <p>
     * compareTo() 的返回值是int, 它是先比较对应字符的大小(ASCII码顺序)
     * compareTo() 方法参考链接：https://blog.csdn.net/qq_25827845/article/details/53870329
     * <p>
     * 1、如果字符串相等返回值 0
     * 2、如果第一个字符和参数的第一个字符不等,结束比较,返回他们之间的差值（ascii码值）（负值前字符串的值小于后字符串，正值前字符串大于后字符串）
     * 3、如果第一个字符和参数的第一个字符相等,则以第二个字符和参数的第二个字符做比较,以此类推,直至比较的字符或被比较的字符有一方全比较完,这时就比较字符的长度.
     *
     * @param currentVersion 当前版本
     * @param newVersion     最新版本
     * @return 是否更新
     */
    protected static boolean isUpdateVersionCompare(String currentVersion, String newVersion) {
        if (TextUtils.isEmpty(currentVersion) || TextUtils.isEmpty(newVersion))
            return false;
        return newVersion.compareTo(currentVersion) > 0 ? true : false;
    }

    /**
     * 检查更新
     *
     * @param context
     * @param httpUrl            检测版本请求地址
     * @param currentVersionCode 主要是用于版本升级所用，是INT类型的，第一个版本定义为1，以后递增，这样只要判断该值就能确定是否需要升级，该值不显示给用户
     * @param currentVersion     当前的版本，这个是我们常说明的版本号，由三部分组成 <major>.<minor>.<point>，该值是个字符串，可以显示给用户
     * @param appName            应用名称，用于保存下载的新版本 APK 文件
     */
    public static void forceUpdate(final Context context, String httpUrl, final int currentVersionCode, final String currentVersion, final String appName, boolean isMain, OnUpdateListener onUpdateListener) {
        NetworkUtils.NetworkType networkType = NetworkUtils.getNetworkType();
        if (networkType == NetworkUtils.NetworkType.NETWORK_WIFI || networkType == NetworkUtils.NetworkType.NETWORK_4G) {
            OkHttpUtils.get().url(httpUrl).build().execute(new StringCallback() {
                @Override
                public void onError(Call call, Exception e, int id) {
                    if (isMain && null != onUpdateListener) {
                        onUpdateListener.onUpdate(false);
                    } else {
                        ToastHelper.showToast(context, R.string.setting_version_update_no, 0);
                    }
                }

                @Override
                public void onResponse(String response, int id) {
                    // 解析检测结果
                    JSONObject version = VersionUpdateHelp.getVersion(response);
                    if (null == version) {
                        if (isMain && null != onUpdateListener) {
                            onUpdateListener.onUpdate(false);
                        } else {
                            ToastHelper.showToast(context, R.string.setting_version_update_no, 0);
                        }
                        return;
                    }

                    if (version.optInt("code", 0) == 1) {
                        JSONObject versionData = version.optJSONObject("data");
                        String android_versions = versionData.optString("android_version", currentVersion);
                        String android_link = versionData.optString("android_url", "");
                        String app_msg = versionData.optString("android_msg", "");

                        // 对比版本
                        compareVersion(android_versions, android_link, app_msg, appName, currentVersionCode, context, isMain, onUpdateListener);
                    } else {
                        if (isMain && null != onUpdateListener) {
                            onUpdateListener.onUpdate(false);
                        } else {
                            ToastHelper.showToast(context, version.optString("msg", context.getResources().getString(R.string.setting_version_update_no)), 0);
                        }
                    }
                }
            });
        } else {
            if (isMain && null != onUpdateListener) {
                onUpdateListener.onUpdate(true);
            } else {
                ToastHelper.showToast(context, R.string.setting_version_update_no_network, 0);
            }
        }
    }

    public static void forceUpdate(final Context context, String httpUrl, final int currentVersionCode, final String currentVersion, final String appName) {
        forceUpdate(context, httpUrl, currentVersionCode, currentVersion, appName, false, null);
    }

    /**
     * 比较当前版本和服务器版本
     *
     * @param android_versions   服务器版本
     * @param android_link       安装包下载地址
     * @param app_msg            更新信息
     * @param currentVersionCode 当前版本
     * @param context
     */
    private static void compareVersion(String android_versions, final String android_link, String app_msg, final String appName, int currentVersionCode, final Context context, final boolean isMain, OnUpdateListener onUpdateListener) {
        Log.i("TAG_VersionUpdate", currentVersionCode + " , " + android_versions);
        if (VersionUpdateHelp.isUpdateVersion(currentVersionCode, android_versions)) {
            if (isMain && null != onUpdateListener) {
                onUpdateListener.onUpdate(true);
            }
            final VersionUpdateDialog updateDialog = new VersionUpdateDialog(context, 20, 500, isMain);
            updateDialog.setCanceledOnTouchOutside(false);
            updateDialog.show();
            updateDialog.getDialogMsg().setText(null == app_msg ? "" : app_msg);
            updateDialog.setOnBtnClickListener(() -> {
                if (!isMain)
                    updateDialog.cancel();

                if (android_link.endsWith(".apk")) {
                    // 直接下载安装文件
                    loadLocation(android_link, appName, context);
                } else {
                    loadWithWeb(android_link, context);
                }
            });
        } else {
            if (isMain && null != onUpdateListener) {
                onUpdateListener.onUpdate(false);
            } else {
                ToastHelper.showToast(context, R.string.setting_version_update_latest, 0);

            }
        }
    }

    /**
     * 浏览器打开，安装文件或者下载界面都可以
     *
     * @param android_link 安装包下载地址
     * @param context
     */
    private static void loadWithWeb(String android_link, Context context) {
        Uri uri = Uri.parse(android_link);
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        context.startActivity(intent);
    }

    /**
     * 应用内下载
     *
     * @param android_link 安装包下载地址
     * @param appName      安装包文件保存名字
     * @param context
     */
    public static void loadLocation(String android_link, final String appName, final Context context) {
        OkHttpUtils.get().url(android_link).build().execute(new FileCallBack(getExternalStoragePath(), appName + ".apk") {
            final ProgressDialog pd = new ProgressDialog(context);

            @Override
            public void onBefore(Request request, int id) {
                super.onBefore(request, id);
                pd.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                pd.setMessage(context.getString(R.string.setting_version_update_loading));
                pd.setCancelable(true);
                pd.setCanceledOnTouchOutside(false);
                pd.show();
            }

            @Override
            public void onAfter(int id) {
                super.onAfter(id);
                pd.cancel();
            }

            @Override
            public void onError(Call call, Exception e, int id) {
                Logger.i(TAG, "onError: " + e.getMessage());
                ToastHelper.showToast(context, String.format(context.getString(R.string.setting_version_update_load_error), e.getMessage()), 0);
            }

            @Override
            public void inProgress(float progress, long total, int id) {
                super.inProgress(progress, total, id);
                Logger.i(TAG, "inProgress: progress = " + (int) (100 * progress) + ", total = " + total);
                pd.setProgress((int) (100 * progress));

            }

            @Override
            public void onResponse(File response, int id) {
                Logger.i(TAG, "onResponse: " + response.getAbsolutePath());
                ToastHelper.showToast(context, String.format(context.getString(R.string.setting_version_update_downloaded), response.getAbsolutePath()), 0);
                installApk(response, context);
            }
        });
    }

    /**
     * 安装Apk
     *
     * @param file
     * @param context
     */
    protected static void installApk(File file, Context context) {
        if (null == file || null == context || !file.exists()) {
            return;
        }

        /*if(Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.Q){ // 10
            //适配Android Q,注意mFilePath是通过ContentResolver得到的，上述有相关代码
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setDataAndType(Uri.parse(file.getAbsolutePath()) ,"application/vnd.android.package-archive");
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            context.startActivity(intent);
            return ;
        }*/

        Intent intent = new Intent(Intent.ACTION_VIEW);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) { // 7
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            Uri contentUri = FileProvider.getUriForFile(context.getApplicationContext(),
                    context.getApplicationContext().getPackageName() + ".fileprovider", file);
            intent.setDataAndType(contentUri, "application/vnd.android.package-archive");
        } else {
            intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        }
        context.startActivity(intent);
    }

    /**
     * 静默安装
     *
     * @param filePath
     * @return 安装成功/失败
     */
    protected static boolean installApkUseRoot(String filePath) {
        if (TextUtils.isEmpty(filePath))
            throw new IllegalArgumentException("Please check apk!");
        boolean result = false;
        Process process = null;
        OutputStream outputStream = null;
        BufferedReader errorStream = null;
        try {
            process = Runtime.getRuntime().exec("su");
            outputStream = process.getOutputStream();
            String command = "pm install -r " + filePath + "\n";
            outputStream.write(command.getBytes());
            outputStream.flush();
            outputStream.write("exit\n".getBytes());
            outputStream.flush();
            process.waitFor();
            errorStream = new BufferedReader(new InputStreamReader(process.getErrorStream()));
            StringBuilder msg = new StringBuilder();
            String line;
            while ((line = errorStream.readLine()) != null) {
                msg.append(line);
            }
            Logger.d(TAG, "install msg is " + msg);
            if (!msg.toString().contains("Failure")) {
                result = true;
            }
        } catch (Exception e) {
        } finally {
            try {
                if (outputStream != null) {
                    outputStream.close();
                }
                if (errorStream != null) {
                    errorStream.close();
                }
            } catch (IOException e) {
                outputStream = null;
                errorStream = null;
                process.destroy();
            }
        }
        return result;
    }

    private static class FullDialogForVersionUpdate extends Dialog {
        private boolean mCancelable = true;
        private boolean mCanceledOnTouchOutside = true;
        private Context mContext;

        public FullDialogForVersionUpdate(Context context, boolean canceledOnTouchOutside, boolean cancelable, View view) {
            super(context);
            this.mCanceledOnTouchOutside = canceledOnTouchOutside;
            this.mCancelable = cancelable;
            this.mContext = context;
            getWindow().requestFeature(Window.FEATURE_NO_TITLE);
            setCancelable(this.mCancelable);
            if (FullDialogForVersionUpdate.this.mCanceledOnTouchOutside && FullDialogForVersionUpdate.this.mCancelable) {
                view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        cancel();
                    }
                });
            } else {
                view.setOnClickListener(null);
            }
            setContentView(view);
        }

        @Override
        protected void onStart() {
            getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
        }

        @Override
        public boolean onKeyUp(int keyCode, KeyEvent event) {
            if (keyCode == KeyEvent.KEYCODE_BACK) {
                if (FullDialogForVersionUpdate.this.mCancelable)
                    cancel();
                return true;
            }
            return super.onKeyUp(keyCode, event);
        }
    }

    public static String getExternalStoragePath() {
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            return Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getAbsolutePath();
        } else {
            return Environment.getDataDirectory().getPath();
        }
    }

    public interface OnUpdateListener {
        void onUpdate(boolean needUpdate);
    }
}
