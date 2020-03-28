package com.jgkj.grb.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.jgkj.grb.R;
import com.jgkj.grb.base.BaseActivity;
import com.jgkj.grb.onclick.RxView;
import com.jgkj.grb.retrofit.ApiCallback;
import com.jgkj.grb.retrofit.ApiStores;
import com.jgkj.grb.utils.Logger;
import com.jgkj.utils.token.GetTokenUtils;
import com.lljjcoder.Interface.OnCityItemClickListener;
import com.lljjcoder.bean.CityBean;
import com.lljjcoder.bean.DistrictBean;
import com.lljjcoder.bean.ProvinceBean;
import com.lljjcoder.citywheel.CityConfig;
import com.lljjcoder.style.citypickerview.CityPickerView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * 实名认证
 */
public class RealnameSecondActivity extends BaseActivity {

    public static void startActivityForResult(Activity activity, ArrayList<String> selected) {
        Intent intent = new Intent(activity, RealnameSecondActivity.class);
        intent.putStringArrayListExtra("selected", selected);
        activity.startActivityForResult(intent, 10008);
    }

    @BindView(R.id.realname_name_et)
    EditText mRealnameNameEt;
    @BindView(R.id.realname_cardid_et)
    EditText mRealnameCardidEt;
    @BindView(R.id.realname_region)
    FrameLayout mRealnameRegion;
    @BindView(R.id.realname_region_et)
    TextView mRealnameRegionEt;
    @BindView(R.id.realname_phone_et)
    EditText mRealnamePhoneEt;
    @BindView(R.id.realname_code_et)
    EditText mRealnameCodeEt;
    @BindView(R.id.realname_verification_code_right)
    FrameLayout mRealnameCodeAction;
    @BindView(R.id.realname_verification_code_tv)
    TextView mRealnameCodeActionTv;
    @BindView(R.id.realname_sure)
    CardView mRealnameSure;

    private CityPickerView mCityPickerView = new CityPickerView();
    private String defaultProvinceName = "";
    private String defaultCityName = "";
    private String defaultDistrict = "";
    private List<String> addrCode = new ArrayList<>();

    ArrayList<String> mSelected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_realname_second);

        Toolbar toolbar = initToolBar(getString(R.string.safety_center_real_name_binding_text));

        Intent intent = getIntent();
        if (intent.hasExtra("selected")) {
            mSelected = intent.getStringArrayListExtra("selected");
        }
        if (null == mSelected || mSelected.size() <= 0) {
            toastShow(R.string.realname_second_cardid_tip);
            onBackPressed();
            return;
        }
        for (int i = 0; i < mSelected.size(); i++) {
            if (TextUtils.isEmpty(mSelected.get(i))) {
                if (i == 0) {
                    toastShow(R.string.realname_first_cardid_front_tip);
                } else if (i == 1) {
                    toastShow(R.string.realname_first_cardid_back_tip);
                } else if (i == 2) {
                    toastShow(R.string.realname_first_cardid_positive_tip);
                }
                onBackPressed();
                return;
            }
        }

        RxView.setOnClickListeners(this, mRealnameRegion, mRealnameCodeAction, mRealnameSure);

        mCityPickerView.init(mActivity);
    }

    private void userGetreal() {
        showProgressDialog();

        String token = GetTokenUtils.getToken(this, ApiStores.API_SERVER_USER_GETREAL);
        MultipartBody.Builder bodyBuilder = new MultipartBody.Builder()
                .setType(MultipartBody.FORM) // 表单类型
                .addFormDataPart("us_name", mRealnameNameEt.getText().toString().trim())
                .addFormDataPart("us_id_card", mRealnameCardidEt.getText().toString().trim())
                .addFormDataPart("us_tel", mRealnamePhoneEt.getText().toString().trim())
                .addFormDataPart("code", mRealnameCodeEt.getText().toString().trim());
        for (int i = 0; i < addrCode.size(); i++) {
            Logger.i("TAG_addrCode", "addrCode = " + addrCode.get(i));
            bodyBuilder.addFormDataPart("us_addr_code[]", addrCode.get(i));
        }
        File file = new File(mSelected.get(0));
        Logger.i("TAG_mSelected", "mSelected = " + mSelected.get(0));
        bodyBuilder.addFormDataPart("us_card_front_pic", file.getName(), RequestBody.create(file, MediaType.parse("multipart/form-data")));
        file = new File(mSelected.get(1));
        Logger.i("TAG_mSelected", "mSelected = " + mSelected.get(1));
        bodyBuilder.addFormDataPart("us_card_reverse_pic", file.getName(), RequestBody.create(file, MediaType.parse("multipart/form-data")));
        file = new File(mSelected.get(2));
        Logger.i("TAG_mSelected", "mSelected = " + mSelected.get(2));
        bodyBuilder.addFormDataPart("us_card_pic", file.getName(), RequestBody.create(file, MediaType.parse("multipart/form-data")));
        List<MultipartBody.Part> parts = bodyBuilder.build().parts();

        addSubscription(apiStores().userGetreal(token, parts), new ApiCallback<String>() {
            @Override
            public void onSuccess(String model) {
                // {"code":2,"msg":"请输入绑定的手机号","time":1568686068,"data":""}
                try {
                    JSONObject result = new JSONObject(model);
                    if (result.getInt("code") == 1) {
                        toastShow(result.getString("msg"));

                        String userStr = sharedPreferencesHelper.getSharedPreference("user", "").toString();
                        JSONObject user = new JSONObject(userStr);
                        user.put("is_realname", 1);
                        user.put("us_name", mRealnameNameEt.getText().toString().trim());
                        sharedPreferencesHelper.putApply("user", user.toString());

                        ApplyResultActivity.start(mActivity, 2);
                        setResult(Activity.RESULT_OK);
                        onBackPressed();
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

    @Override
    public void onClick(Object o) {
        View v = (View) o;
        switch (v.getId()) {
            case R.id.realname_region:
                hideInputSoft();
                wheel();
                break;
            case R.id.realname_verification_code_right:
                if (TextUtils.isEmpty(mRealnamePhoneEt.getText().toString().trim())) {
                    toastShow(R.string.password_phone_number_tip);
                    return;
                }
                everyGetRegion(mRealnameCodeAction, mRealnameCodeActionTv, getString(R.string.password_verification_code_action), mRealnamePhoneEt.getText().toString().trim());
                break;
            case R.id.realname_sure:
                if (TextUtils.isEmpty(mRealnameNameEt.getText().toString().trim())) {
                    toastShow(R.string.realname_second_realname_name_input_tip);
                    return;
                }
                if (TextUtils.isEmpty(mRealnameCardidEt.getText().toString().trim())) {
                    toastShow(R.string.realname_second_realname_cardid_input_tip);
                    return;
                }
                if (TextUtils.isEmpty(mRealnameRegionEt.getText().toString().trim())) {
                    toastShow(R.string.realname_second_realname_region_input_tip);
                    return;
                }
                if (TextUtils.isEmpty(mRealnamePhoneEt.getText().toString().trim())) {
                    toastShow(R.string.realname_second_realname_phone_input_tip);
                    return;
                }
                if (TextUtils.isEmpty(mRealnameCodeEt.getText().toString().trim())) {
                    toastShow(R.string.password_verification_code_tip);
                    return;
                }
                userGetreal();
                break;
            default:
                break;
        }
    }

    /**
     * 弹出选择器
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
                addrCode.clear();
                if (province != null) {
                    sb.append(province.getName()).append(" ");
                    defaultProvinceName = province.getName();
                    addrCode.add(province.getId());
                }

                if (city != null) {
                    sb.append(city.getName()).append(" ");
                    defaultCityName = city.getName();
                    addrCode.add(city.getId());
                }

                if (district != null) {
                    sb.append(district.getName());
                    defaultDistrict = district.getName();
                    addrCode.add(district.getId());
                }

                mRealnameRegionEt.setText(sb.toString());
            }

            @Override
            public void onCancel() {
            }
        });
        mCityPickerView.showCityPicker();
    }

}
