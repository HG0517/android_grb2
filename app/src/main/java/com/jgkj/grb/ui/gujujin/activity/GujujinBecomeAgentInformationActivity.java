package com.jgkj.grb.ui.gujujin.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.jgkj.grb.R;
import com.jgkj.grb.base.BaseActivity;
import com.jgkj.grb.onclick.RxView;
import com.jgkj.grb.retrofit.ApiCallback;
import com.jgkj.grb.retrofit.ApiStores;
import com.jgkj.grb.ui.gujujin.dialog.BecomeAgentGenderDialog;
import com.jgkj.grb.view.datepicker.DateFormatUtils;
import com.jgkj.grb.view.datepicker.DatePickerSingleMonth;
import com.jgkj.utils.token.GetTokenUtils;
import com.lljjcoder.Interface.OnCityItemClickListener;
import com.lljjcoder.bean.CityBean;
import com.lljjcoder.bean.DistrictBean;
import com.lljjcoder.bean.ProvinceBean;
import com.lljjcoder.citywheel.CityConfig;
import com.lljjcoder.style.citypickerview.CityPickerView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;

/**
 * 谷聚金：成为代理商，填写资料
 */
public class GujujinBecomeAgentInformationActivity extends BaseActivity {

    public static void startActivityForResult(Activity context, int level) {
        Intent intent = new Intent(context, GujujinBecomeAgentInformationActivity.class);
        intent.putExtra("level", level);
        context.startActivityForResult(intent, 10013);
    }

    @BindView(R.id.become_agent_speed_tv_0)
    TextView mBecomeAgentSpeedTv0;
    @BindView(R.id.become_agent_speed_tv_1)
    TextView mBecomeAgentSpeedTv1;
    @BindView(R.id.become_agent_speed_tv_2)
    TextView mBecomeAgentSpeedTv2;
    @BindView(R.id.become_agent_speed_tv_3)
    TextView mBecomeAgentSpeedTv3;
    @BindView(R.id.become_agent_speed_line_0)
    View mBecomeagentspeedline0;
    @BindView(R.id.become_agent_speed_line_1)
    View mBecomeagentspeedline1;
    @BindView(R.id.become_agent_speed_line_2)
    View mBecomeagentspeedline2;
    @BindView(R.id.become_agent_speed_iv_0)
    ImageView mBecomeAgentSpeedIv0;
    @BindView(R.id.become_agent_speed_iv_1)
    ImageView mBecomeAgentSpeedIv1;
    @BindView(R.id.become_agent_speed_iv_2)
    ImageView mBecomeAgentSpeedIv2;
    @BindView(R.id.become_agent_speed_iv_3)
    ImageView mBecomeAgentSpeedIv3;

    @BindView(R.id.phone_input)
    EditText mPhoneInput;
    @BindView(R.id.phone_input_code)
    EditText mPhoneInputCode;
    @BindView(R.id.verification_code_right)
    FrameLayout mVerificationCodeRight;
    @BindView(R.id.verification_code_tv)
    TextView mVerificationCodeTv;
    @BindView(R.id.name_input)
    EditText mNameInput;
    @BindView(R.id.cardid_input)
    EditText mCardidInput;
    @BindView(R.id.wechat_input)
    EditText mWechatInput;
    @BindView(R.id.become_agent_basic_data_gender)
    FrameLayout mBecomeAgentBasicDataGender;
    @BindView(R.id.gender_input)
    TextView mGenderInput;
    @BindView(R.id.become_agent_basic_data_birth)
    FrameLayout mBecomeAgentBasicDataBirth;
    @BindView(R.id.birth_input)
    TextView mBirthInput;
    @BindView(R.id.become_agent_basic_data_region)
    FrameLayout mBecomeAgentBasicDataRegion;
    @BindView(R.id.region_input)
    TextView mRegionInput;

    @BindView(R.id.become_agent_already_exist)
    TextView mBecomeAgentAlreadyExist;
    @BindView(R.id.submission)
    CardView mSubmission;

    int mLevel = 0;
    int mGender = 0;
    private CityPickerView mCityPickerView = new CityPickerView();
    private String defaultProvinceName = "";
    private String defaultCityName = "";
    private String defaultDistrict = "";
    private List<String> addrCode = new ArrayList<>(Arrays.asList("", "", ""));

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gujujin_become_agent_information);

        Toolbar toolbar = initToolBar("");
        toolbar.setTitle(R.string.activity_title_gujujin_become_agent_information);

        Intent intent = getIntent();
        if (intent.hasExtra("level")) {
            mLevel = intent.getIntExtra("level", 0);
        }
        if (mLevel <= 0) {
            toastShow(R.string.gujujin_become_agent_no_level);
            onBackPressed();
            return;
        }

        RxView.setOnClickListeners(this, mVerificationCodeRight, mBecomeAgentBasicDataGender,
                mBecomeAgentBasicDataBirth, mBecomeAgentBasicDataRegion, mSubmission);
        initSpeed();
        new Thread(() -> mCityPickerView.init(mActivity)).start();
    }

    @Override
    public void onClick(Object o) {
        View v = (View) o;
        switch (v.getId()) {
            case R.id.verification_code_right:
                if (TextUtils.isEmpty(mPhoneInput.getText().toString().trim())) {
                    toastShow(R.string.password_phone_number_tip);
                    return;
                }
                everyGetRegion(mVerificationCodeRight, mVerificationCodeTv, getString(R.string.password_verification_code_action), mPhoneInput.getText().toString().trim());
                break;
            case R.id.become_agent_basic_data_gender:
                showGender();
                break;
            case R.id.become_agent_basic_data_birth:
                showDatePickerSingleMonth();
                break;
            case R.id.become_agent_basic_data_region:
                wheel();
                break;
            case R.id.submission:
                if (TextUtils.isEmpty(mPhoneInput.getText().toString().trim())) {
                    toastShow(R.string.password_phone_number_tip);
                    return;
                }
                if (TextUtils.isEmpty(mPhoneInputCode.getText().toString().trim())) {
                    toastShow(R.string.password_verification_code_tip);
                    return;
                }
                if (TextUtils.isEmpty(mNameInput.getText().toString().trim())) {
                    toastShow(R.string.luck_draw_mine_receive_dialog_name_tip);
                    return;
                }
                if (TextUtils.isEmpty(mCardidInput.getText().toString().trim())) {
                    toastShow(R.string.binding_bank_card_cardid_tip);
                    return;
                }
                if (TextUtils.isEmpty(mWechatInput.getText().toString().trim())) {
                    toastShow(R.string.gujujin_become_agent_information_wechat_tip);
                    return;
                }
                if (TextUtils.isEmpty(mGenderInput.getText().toString().trim())) {
                    toastShow(R.string.gujujin_become_agent_information_gender_tip);
                    return;
                }
                if (TextUtils.isEmpty(mBirthInput.getText().toString().trim())) {
                    toastShow(R.string.gujujin_become_agent_information_birth_tip);
                    return;
                }
                if (TextUtils.isEmpty(mRegionInput.getText().toString().trim())) {
                    toastShow(R.string.gujujin_become_agent_information_region_tip);
                    return;
                }
                indexValleyApply(mLevel, mPhoneInput.getText().toString().trim(), mPhoneInputCode.getText().toString().trim(),
                        mNameInput.getText().toString().trim(), mCardidInput.getText().toString().trim(), mWechatInput.getText().toString().trim(),
                        String.valueOf(mGender), mBirthInput.getText().toString().trim(),
                        defaultProvinceName, defaultCityName, defaultDistrict/*addrCode.get(0), addrCode.get(1), addrCode.get(2)*/);
                break;
            default:
                break;
        }
    }

    /**
     * 提交资料
     */
    private void indexValleyApply(int type, String usTel, String code, String name, String cardId,
                                  String wechat, String sex, String birthday, String provinceCode,
                                  String cityCode, String townCode) {
        showProgressDialog();
        String token = GetTokenUtils.getToken(this, ApiStores.API_SERVER_VALLEY_APPLY);
        addSubscription(apiStores().indexValleyApply(token, type, usTel, code, name, cardId,
                wechat, sex, birthday, provinceCode, cityCode, townCode), new ApiCallback<String>() {
            @Override
            public void onSuccess(String model) {
                try {
                    JSONObject result = new JSONObject(model);
                    if (result.optInt("code", 0) == 1) {
                        GujujinBecomeAgentQualificationsActivity.startActivityForResult(mActivity, result.optInt("data", -1));
                    } else {
                        toastShow(result.optString("msg", ""));
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

    /**
     * 性别
     */
    private void showGender() {
        BecomeAgentGenderDialog dialog = BecomeAgentGenderDialog.newInstance();
        dialog.setCancelable(false);
        dialog.showDialog(getSupportFragmentManager());
        dialog.setOnDialogListener(new BecomeAgentGenderDialog.OnDialogListener() {
            @Override
            public void onCancel() {
            }

            @Override
            public void onSure(int gender) {
                mGender = gender;
                mGenderInput.setText(getString(mGender == 1 ? R.string.gujujin_become_agent_information_gender_male : R.string.gujujin_become_agent_information_gender_female));
            }
        });
    }

    /**
     * 日期
     */
    private void showDatePickerSingleMonth() {
        DatePickerSingleMonth matePicker = new DatePickerSingleMonth(this, timestamp -> {
            // timestamp <= 0 不选择时间
            if (timestamp > 0) {
                mBirthInput.setText(DateFormatUtils.long2Str(timestamp, "yyyy-MM"));
            }
        }, DateFormatUtils.str2Long("2000-01-01 00:00:00", DateFormatUtils.DATE_FORMAT_PATTERN_YMD_HMS), System.currentTimeMillis());
        // 点击屏幕或物理返回键关闭
        matePicker.setCancelable(true);
        // 滚动动画
        matePicker.setCanShowAnim(true);
        // 显示时和分
        matePicker.setCanShowPreciseTime(false);
        matePicker.setCanShowDay(false);
        matePicker.setCanShowMonth(true);
        // 循环滚动
        matePicker.setScrollLoop(true);
        matePicker.show(System.currentTimeMillis());
    }

    /**
     * 弹出选择器：地区
     */
    private void wheel() {
        CityConfig cityConfig = new CityConfig.Builder()
                .visibleItemsCount(5)
                .setCityWheelType(CityConfig.WheelType.PRO_CITY_DIS)
                .showBackground(true)
                .setShowGAT(true)
                .provinceCyclic(false)
                .cityCyclic(false)
                .districtCyclic(false)
                .cancelTextColor("#999999")
                .confirTextColor("#FE283C")
                .setLineColor("#DDDDDD")
                .province(defaultProvinceName)
                .city(defaultCityName)
                .district(defaultDistrict)
                .build();

        mCityPickerView.setConfig(cityConfig);
        mCityPickerView.setOnCityItemClickListener(new OnCityItemClickListener() {
            @Override
            public void onSelected(ProvinceBean province, CityBean city, DistrictBean district) {
                StringBuilder sb = new StringBuilder();
                for (int i = 0; i < addrCode.size(); i++) {
                    addrCode.set(i, "");
                }
                if (province != null) {
                    sb.append(province.getName()).append("，");
                    defaultProvinceName = province.getName();
                    addrCode.set(0, province.getId());
                }

                if (city != null) {
                    sb.append(city.getName()).append("，");
                    defaultCityName = city.getName();
                    addrCode.set(1, city.getId());
                }

                if (district != null) {
                    sb.append(district.getName());
                    defaultDistrict = district.getName();
                    addrCode.set(2, district.getId());
                }

                mRegionInput.setText(sb.toString());
            }

            @Override
            public void onCancel() {
            }
        });
        mCityPickerView.showCityPicker();
    }

    private void initSpeed() {
        mBecomeAgentSpeedTv0.setSelected(true);
        mBecomeAgentSpeedIv0.setSelected(true);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == 10013) {
                setResult(Activity.RESULT_OK);
                onBackPressed();
            }
        }
    }

}
