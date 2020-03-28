package com.jgkj.grb.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.jgkj.grb.R;
import com.jgkj.grb.base.BaseActivity;
import com.jgkj.grb.eventbus.RefreshUserInfo;
import com.jgkj.grb.glide.GlideApp;
import com.jgkj.grb.onclick.RxView;
import com.jgkj.grb.retrofit.ApiCallback;
import com.jgkj.grb.retrofit.ApiStores;
import com.jgkj.grb.utils.Logger;
import com.jgkj.grb.view.datepicker.DateFormatUtils;
import com.jgkj.utils.token.GetTokenUtils;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.runtime.Permission;
import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;
import com.zhihu.matisse.engine.impl.GlideEngine;
import com.zhihu.matisse.internal.entity.CaptureStrategy;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.List;

import butterknife.BindView;
import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * 个人资料
 */
public class SettingPersonalActivity extends BaseActivity {

    public static void startActivityForResult(Activity context) {
        Intent intent = new Intent(context, SettingPersonalActivity.class);
        context.startActivityForResult(intent, 10011);
    }

    @BindView(R.id.personal_userhead_fl)
    FrameLayout mPersonalUserheadFl;
    @BindView(R.id.userhead)
    CircleImageView mPersonalUserhead;

    @BindView(R.id.personal_username_fl)
    FrameLayout mPersonalUsernameFl;
    @BindView(R.id.personal_username_right)
    TextView mPersonalUsernameTv;

    @BindView(R.id.personal_userid_fl)
    FrameLayout mPersonalUserIDFl;
    @BindView(R.id.personal_userid_right)
    TextView mPersonalUserIDTv;

    @BindView(R.id.personal_realname_fl)
    FrameLayout mPersonalRealnameFl;
    @BindView(R.id.personal_realname_right)
    TextView mPersonalRealnameTv;

    @BindView(R.id.personal_recommender_fl)
    FrameLayout mPersonalRecommenderFl;
    @BindView(R.id.personal_recommender_right)
    TextView mPersonalRecommenderTv;

    @BindView(R.id.personal_regtime_fl)
    FrameLayout mPersonalRegtimeFl;
    @BindView(R.id.personal_regtime_right)
    TextView mPersonalRegtimeTv;

    @BindView(R.id.personal_days_added_fl)
    FrameLayout mPersonalDaysAddedFl;
    @BindView(R.id.personal_days_added_right)
    TextView mPersonalDaysAddedTv;

    @BindView(R.id.personal_farm_grade_fl)
    FrameLayout mPersonalFarmGradeFl;
    @BindView(R.id.personal_farm_grade_right)
    TextView mPersonalFarmGradeTv;

    @BindView(R.id.personal_gujujin_grade_fl)
    FrameLayout mPersonalGujujinGradeFl;
    @BindView(R.id.personal_gujujin_grade_right)
    TextView mPersonalGujujinGradeTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_personal);

        Toolbar toolbar = initToolBar("");
        toolbar.setTitle(R.string.activity_title_setting_personal);

        RxView.setOnClickListeners(this, mPersonalUserheadFl, mPersonalUsernameFl);
        /*mPersonalUserheadFl.setOnClickListener(this);
        mPersonalUsernameFl.setOnClickListener(this);*/

        initUserView();
    }

    private void initUserView() {
        // is_realname  实名情况：0:未认证  1认证中 2已认证
        // us_nickname  昵称 ； us_name  姓名 ；us_head_pic  图像；p_account  推荐人；us_add_time  注册时间
        // us_level 农场等级：0：游客，1：体验会员，2：一星代理，3：二星代理，4：三星代理，5：四星代理，6：五星代理
        // us_agent 谷聚金等级：0:无代理，1，个人代理，2县代理，3市代 4 省代

        String userStr = sharedPreferencesHelper.getSharedPreference("user", "").toString();
        try {
            JSONObject user = new JSONObject(userStr);
            String us_head_pic = user.getString("us_head_pic");
            if (!TextUtils.isEmpty(us_head_pic)) {
                GlideApp.with(this)
                        .load(us_head_pic.startsWith("http:") || us_head_pic.startsWith("https:")
                                ? us_head_pic.replaceAll("\\\\", "/")
                                : ApiStores.API_SERVER_URL + us_head_pic.replaceAll("\\\\", "/"))
                        .centerCrop()
                        .into(mPersonalUserhead);
            }
            String us_nickname = user.getString("us_nickname");
            mPersonalUsernameTv.setText(TextUtils.isEmpty(us_nickname) || TextUtils.equals("null", us_nickname) ? "" : us_nickname);
            int is_realname = user.getInt("is_realname");
            String us_name = user.getString("us_name");
            if (is_realname == 2) {
                mPersonalRealnameTv.setText(TextUtils.isEmpty(us_name) || TextUtils.equals("null", us_name) ? getString(R.string.safety_center_binding_certified_text) : us_name);
                mPersonalRealnameTv.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
            } else if (is_realname == 1) {
                mPersonalRealnameTv.setText(R.string.safety_center_binding_certification_text);
                mPersonalRealnameTv.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.mipmap.ic_unbound_red), null, null, null);
            } else if (is_realname == 3) {
                mPersonalRealnameTv.setText(R.string.real_name_rejected);
                mPersonalRealnameTv.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.mipmap.ic_unbound_red), null, null, null);
            } else {
                mPersonalRealnameTv.setText(getString(R.string.safety_center_binding_uncertified_text));
                mPersonalRealnameTv.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.mipmap.ic_unbound_red), null, null, null);
            }
            String us_account = user.getString("us_account");
            mPersonalUserIDTv.setText(TextUtils.isEmpty(us_account) || TextUtils.equals("null", us_account) ? "" : us_account);
            String p_account = user.getString("p_account");
            mPersonalRecommenderTv.setText(TextUtils.isEmpty(p_account) || TextUtils.equals("null", p_account) ? "" : p_account);
            String us_add_time = user.getString("us_add_time");
            if (!TextUtils.isEmpty(us_add_time)) {
                mPersonalRegtimeTv.setText(us_add_time);
                // 加入天数
                long usAddTime = DateFormatUtils.str2Long(us_add_time, DateFormatUtils.DATE_FORMAT_PATTERN_YMD_HMS);
                long currentTime = System.currentTimeMillis();
                String[] strings = DateFormatUtils.millisDiff(currentTime - usAddTime);
                mPersonalDaysAddedTv.setText(String.format("%s天", strings[0]));
            }
            int us_level = user.getInt("us_level");
            mPersonalFarmGradeTv.setText(getUsLevel(us_level));
            int us_agent = user.getInt("us_agent");
            mPersonalGujujinGradeTv.setText(getUsAgent(us_agent));
        } catch (JSONException e) {
        }
    }

    // 农场等级：0：游客，1：体验会员，2：一星代理，3：二星代理，4：三星代理，5：四星代理，6：五星代理
    private String getUsLevel(int us_level) {
        String usLevel = "游客";
        switch (us_level) {
            case 0:
                usLevel = "游客";
                break;
            case 1:
                usLevel = "体验会员";
                break;
            case 2:
                usLevel = "一星代理";
                break;
            case 3:
                usLevel = "二星代理";
                break;
            case 4:
                usLevel = "三星代理";
                break;
            case 5:
                usLevel = "四星代理";
                break;
            case 6:
                usLevel = "五星代理";
                break;
            default:
                usLevel = "游客";
                break;
        }
        return usLevel;
    }

    // 谷聚金等级：0：无代理，1：个人代理，2：县代理，3：市代理 4：省代理
    private String getUsAgent(int us_agent) {
        String usAgent = "无代理";
        switch (us_agent) {
            case 0:
                usAgent = "无代理";
                break;
            case 1:
                usAgent = "个人代理";
                break;
            case 2:
                usAgent = "县代理";
                break;
            case 3:
                usAgent = "市代理";
                break;
            case 4:
                usAgent = "省代理";
                break;
            default:
                usAgent = "无代理";
                break;
        }
        return usAgent;
    }

    @Override
    public void onClick(Object o) {
        View v = (View) o;
        switch (v.getId()) {
            case R.id.personal_userhead_fl:
                AndPermission.with(this)
                        .runtime()
                        .permission(Permission.Group.CAMERA, Permission.Group.STORAGE)
                        .onDenied(data -> {
                            toastShow(R.string.permission_ondenied);
                        })
                        .onGranted(data -> {
                            Matisse.from(this)
                                    .choose(MimeType.ofImage())
                                    .showSingleMediaType(true)
                                    .theme(R.style.Matisse_Zhihu)
                                    .countable(true)
                                    .maxSelectable(1)
                                    .capture(true)
                                    .captureStrategy(new CaptureStrategy(true, getApplication().getPackageName() + ".fileprovider"))
                                    .originalEnable(true)
                                    .maxOriginalSize(5 * 1024 * 1024)
                                    .autoHideToolbarOnSingleTap(true)
                                    .spanCount(3)
                                    .thumbnailScale(0.85f)
                                    .imageEngine(new GlideEngine())
                                    .forResult(10001);
                        })
                        .start();

                break;
            case R.id.personal_username_fl:
                SettingNickActivity.startActivityForResult(mActivity, mPersonalUsernameTv.getText().toString().trim());
                break;
            default:
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == 10001) {
                if (null != data) {
                    List<String> mSelected = Matisse.obtainPathResult(data);
                    if (null != mSelected && !mSelected.isEmpty()) {
                        userChangeHead(mSelected.get(0));
                    }
                }
            } else {
                if (null != data) {
                    Bundle extras = data.getExtras();
                    String newNick = "";
                    if (null != extras) {
                        newNick = extras.getString("newNick", "");
                    }
                    if (!TextUtils.isEmpty(newNick)) {
                        mPersonalUsernameTv.setText(newNick);
                    }
                }
            }
        }
    }

    private void userChangeHead(String filePath) {
        showProgressDialog();

        File file = new File(filePath);
        Logger.i("TAG_UpLoad", file.getAbsolutePath());
        MultipartBody.Builder bodyBuilder = new MultipartBody.Builder()
                .setType(MultipartBody.FORM) // 表单类型
                .addFormDataPart("us_head_pic", file.getName(), RequestBody.create(file, MediaType.parse("multipart/form-data")));
        List<MultipartBody.Part> parts = bodyBuilder.build().parts();

        String token = GetTokenUtils.getToken(this, ApiStores.API_SERVER_USER_CHANGEHEAD);
        addSubscription(apiStores().userChangeHead(token, parts), new ApiCallback<String>() {
            @Override
            public void onSuccess(String model) {
                try {
                    JSONObject result = new JSONObject(model);
                    if (result.getInt("code") == 1) {
                        toastShow(result.getString("msg"));

                        GlideApp.with(mActivity)
                                .load(filePath)
                                .centerCrop()
                                .into(mPersonalUserhead);
                        Intent intent = new Intent();
                        intent.putExtra("path", filePath);
                        setResult(Activity.RESULT_OK, intent);
                        EventBus.getDefault().post(new RefreshUserInfo(isLogin()));
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
}
