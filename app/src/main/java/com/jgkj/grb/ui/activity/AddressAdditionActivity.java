package com.jgkj.grb.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.Switch;
import android.widget.TextView;

import com.google.gson.Gson;
import com.jgkj.grb.R;
import com.jgkj.grb.base.BaseActivity;
import com.jgkj.grb.onclick.RxView;
import com.jgkj.grb.retrofit.ApiCallback;
import com.jgkj.grb.retrofit.ApiStores;
import com.jgkj.grb.ui.mvp.personal.AddresManagementModel;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import io.reactivex.Observable;

/**
 * 地址添加
 */
public class AddressAdditionActivity extends BaseActivity {

    public static void startActivityForResult(Activity activity, AddresManagementModel.DataBean addr, int position) {
        Intent intent = new Intent(activity, AddressAdditionActivity.class);
        if (null != addr && position >= 0) {
            Bundle bundle = new Bundle();
            bundle.putSerializable("edit", addr);
            intent.putExtra("bundle", bundle);
            intent.putExtra("position", position);
        }
        activity.startActivityForResult(intent, 10007);
    }

    @BindView(R.id.username_et)
    EditText mUserNameEt;
    @BindView(R.id.userphone_et)
    EditText mUserPhoneEt;
    @BindView(R.id.userarea_fl)
    FrameLayout mUserAreaFl;
    @BindView(R.id.userarea_et)
    TextView mUserAreaEt;
    @BindView(R.id.useraddress_et)
    EditText mUserAddressEt;
    @BindView(R.id.useraddress_default_fl)
    FrameLayout mUserAddressDefaultFl;
    @BindView(R.id.useraddress_default)
    Switch mUserAddressDefault;
    @BindView(R.id.address_sure)
    CardView mUserAddressSure;
    @BindView(R.id.address_sure_tv)
    TextView mUserAddressSureTv;
    @BindView(R.id.address_delete)
    CardView mUserAddressDelete;

    private CityPickerView mCityPickerView = new CityPickerView();
    private String defaultProvinceName = "";
    private String defaultCityName = "";
    private String defaultDistrict = "";
    private List<String> addrCode = new ArrayList<>();

    AddresManagementModel.DataBean mAddress;
    boolean isEdit = false; // false 添加地址，true 编辑地址
    int mPosition = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address_addition);


        RxView.setOnClickListeners(this, mUserAreaFl, mUserAddressDefaultFl, mUserAddressSure, mUserAddressDelete);

        Intent intent = getIntent();
        if (intent.hasExtra("bundle")) {
            Bundle bundle = intent.getBundleExtra("bundle");
            if (null != bundle) {
                mAddress = (AddresManagementModel.DataBean) bundle.getSerializable("edit");
            }
        }
        if (intent.hasExtra("position")) {
            mPosition = intent.getIntExtra("position", -1);
        }
        initEditAddress();

        mCityPickerView.init(mActivity);
        //onLazyLoad();
    }

    private void onLazyLoad() {
        showProgressDialog();
        addSubscription(apiStores().everyGetRegion(), new ApiCallback<String>() {
            @Override
            public void onSuccess(String model) {
                mCityPickerView.init(mActivity, model);
            }

            @Override
            public void onFailure(String msg) {
                mCityPickerView.init(mActivity);
            }

            @Override
            public void onFinish() {
                dismissProgressDialog();
            }
        });
    }

    private void initEditAddress() {
        if (null == mAddress) {
            mUserAddressDelete.setVisibility(View.GONE);
            initToolBar(getString(R.string.activity_title_address_add));
            mUserAddressSureTv.setText(R.string.address_useraddress_add_sure);
            return;
        }
        initToolBar(getString(R.string.activity_title_address_edit));
        mUserAddressSureTv.setText(R.string.address_useraddress_edit_sure);
        isEdit = true;

        mUserAddressDelete.setVisibility(View.VISIBLE);
        mUserNameEt.setText(mAddress.getAddr_receiver());
        mUserPhoneEt.setText(mAddress.getAddr_tel());
        mUserAreaEt.setText(String.format("%s %s %s", mAddress.getProvince(), mAddress.getCity(), mAddress.getTown()));
        mUserAddressEt.setText(mAddress.getAddr_detail());
        mUserAddressDefault.setChecked(mAddress.getAddr_default() == 1);

        defaultProvinceName = mAddress.getProvince();
        defaultCityName = mAddress.getCity();
        defaultDistrict = mAddress.getTown();
        addrCode = Arrays.asList(mAddress.getAddr_code().split(","));
    }

    @Override
    public void onClick(Object o) {
        View v = (View) o;
        switch (v.getId()) {
            case R.id.userarea_fl:
                hideInputSoft();
                wheel();
                break;
            case R.id.useraddress_default_fl:
                mUserAddressDefault.setChecked(!mUserAddressDefault.isChecked());
                break;
            case R.id.address_sure:
                if (TextUtils.isEmpty(mUserNameEt.getText().toString().trim())) {
                    toastShow(R.string.address_username_input_tip);
                    return;
                }
                if (TextUtils.isEmpty(mUserPhoneEt.getText().toString().trim())) {
                    toastShow(R.string.address_userphone_input_tip);
                    return;
                }
                if (TextUtils.isEmpty(mUserAreaEt.getText().toString().trim())) {
                    toastShow(R.string.address_userarea_tip);
                    return;
                }
                if (TextUtils.isEmpty(mUserAddressEt.getText().toString().trim())) {
                    toastShow(R.string.address_useraddress_tip);
                    return;
                }
                addressAdd();
                break;
            case R.id.address_delete:
                if (null == mAddress || !isEdit) {
                    return;
                }
                addressDelete();
                break;
            default:
                break;
        }
    }

    /**
     * 添加
     */
    private void addressAdd() {
        mUserAddressDefault.setEnabled(false);
        showProgressDialog();
        String token = GetTokenUtils.getToken(this, isEdit ? ApiStores.API_SERVER_USER_ADD_EDIT : ApiStores.API_SERVER_USER_ADD_ADDR);
        Map<String, Object> body = new HashMap<>();
        body.put("addr_receiver", mUserNameEt.getText().toString().trim());
        body.put("addr_tel", mUserPhoneEt.getText().toString().trim());
        body.put("province", defaultProvinceName.trim());
        body.put("city", defaultCityName.trim());
        body.put("town", defaultDistrict.trim());
        body.put("addr_detail", mUserAddressEt.getText().toString().trim());
        body.put("addr_default", mUserAddressDefault.isChecked() ? 1 : 0);
        if (isEdit)
            body.put("id", mAddress.getId());
        Observable<String> addObservable = apiStores().userAddAddr(token, body, addrCode);
        Observable<String> editObservable = apiStores().userAddEdit(token, body, addrCode);
        addSubscription(isEdit ? editObservable : addObservable, new ApiCallback<String>() {
            @Override
            public void onSuccess(String model) {
                try {
                    JSONObject result = new JSONObject(model);
                    if (result.getInt("code") == 1) {
                        if (!isEdit) { // 添加
                            setResult(Activity.RESULT_OK);

                        } else { // 编辑
                            mAddress.setAddr_receiver(mUserNameEt.getText().toString().trim());
                            mAddress.setAddr_tel(mUserPhoneEt.getText().toString().trim());
                            mAddress.setProvince(defaultProvinceName.trim());
                            mAddress.setCity(defaultCityName.trim());
                            mAddress.setTown(defaultDistrict.trim());
                            mAddress.setAddr_detail(mUserAddressEt.getText().toString().trim());
                            mAddress.setAddr_addr(defaultProvinceName.trim() + defaultCityName.trim() + defaultDistrict.trim() + mUserAddressEt.getText().toString().trim());
                            mAddress.setAddr_code(new Gson().toJson(addrCode).replaceAll("\\[", "").replaceAll("\\]", "").replaceAll("\"", ""));
                            mAddress.setAddr_default(mUserAddressDefault.isChecked() ? 1 : 0);

                            Intent intent = getIntent();
                            Bundle bundle = new Bundle();
                            bundle.putSerializable("result", mAddress);
                            bundle.putInt("position", mPosition);
                            intent.putExtra("bundle", bundle);
                            setResult(Activity.RESULT_OK, intent);
                        }
                        onBackPressed();
                    }
                    toastShow(result.getString("msg"));
                } catch (JSONException e) {
                }
            }

            @Override
            public void onFailure(String msg) {
                toastShow(msg);
            }

            @Override
            public void onFinish() {
                mUserAddressDefault.setEnabled(true);
                dismissProgressDialog();
            }
        });
    }

    /**
     * 删除
     */
    private void addressDelete() {
        showProgressDialog();
        String token = GetTokenUtils.getToken(this, ApiStores.API_SERVER_USER_ADD_DELETE);
        addSubscription(apiStores().userAddDelete(token, mAddress.getId()), new ApiCallback<String>() {
            @Override
            public void onSuccess(String model) {
                try {
                    JSONObject result = new JSONObject(model);
                    if (result.getInt("code") == 1) {
                        Intent intent = getIntent();
                        Bundle bundle = new Bundle();
                        bundle.putInt("delete", 1);
                        bundle.putInt("position", mPosition);
                        intent.putExtra("bundle", bundle);
                        setResult(Activity.RESULT_OK, intent);

                        onBackPressed();
                    }
                    toastShow(result.getString("msg"));
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

                mUserAreaEt.setText(sb.toString());
            }

            @Override
            public void onCancel() {
            }
        });
        mCityPickerView.showCityPicker();
    }
}
