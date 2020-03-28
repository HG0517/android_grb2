package com.jgkj.grb.ui.gujujin.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.jgkj.grb.R;
import com.jgkj.grb.base.BaseActivity;
import com.jgkj.grb.itemdecoration.GridDividerItemDecoration;
import com.jgkj.grb.onclick.RxView;
import com.jgkj.grb.retrofit.ApiCallback;
import com.jgkj.grb.retrofit.ApiStores;
import com.jgkj.grb.ui.activity.ApplyResultActivity;
import com.jgkj.grb.ui.adapter.ProductDetailsAdapter;
import com.jgkj.grb.utils.Logger;
import com.jgkj.utils.token.GetTokenUtils;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.runtime.Permission;
import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;
import com.zhihu.matisse.engine.impl.GlideEngine;
import com.zhihu.matisse.internal.entity.CaptureStrategy;

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
 * 谷聚金：上传凭证
 */
public class GujujinUploadCredentialsActivity extends BaseActivity {

    public static void start(Context context, String orId) {
        Intent intent = new Intent(context, GujujinUploadCredentialsActivity.class);
        intent.putExtra("orId", orId);
        context.startActivity(intent);
    }

    @BindView(R.id.order_num)
    TextView mOrderNum;
    @BindView(R.id.order_price)
    TextView mOrderPrice;
    @BindView(R.id.order_payment)
    RadioGroup mOrderPayment;
    @BindView(R.id.order_payment_0)
    RadioButton mOrderPayment0;
    @BindView(R.id.order_payment_1)
    RadioButton mOrderPayment1;
    @BindView(R.id.order_payment_2)
    RadioButton mOrderPayment2;
    @BindView(R.id.order_payment_account)
    EditText mOrderPaymentAccount;

    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;
    ProductDetailsAdapter mAdapter;
    List<String> mListImg = new ArrayList<>();
    int maxSelectable = 1;

    @BindView(R.id.refund_sure)
    CardView mSure;

    int mOrderPaymentInt = 0;
    String mOrId = "";
    String mOrNum = "";
    String mOrTotal = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gujujin_upload_credentials);

        Toolbar toolbar = initToolBar(getString(R.string.activity_title_gujujin_upload_credentials));
        Intent intent = getIntent();
        if (intent.hasExtra("orId")) {
            mOrId = intent.getStringExtra("orId");
        }
        if (TextUtils.isEmpty(mOrId)) {
            toastShow(R.string.open_activity_error_option);
            onBackPressed();
            return;
        }
        RxView.setOnClickListeners(this, mSure);

        initRecyclerView();
        mOrderPayment.setOnCheckedChangeListener((group, checkedId) -> {
            switch (checkedId) {
                case R.id.order_payment_0: // 支付宝
                    mOrderPaymentInt = 3;
                    break;
                case R.id.order_payment_1: // 微信
                    mOrderPaymentInt = 4;
                    break;
                case R.id.order_payment_2: // 银行卡
                    mOrderPaymentInt = 5;
                    break;
                default:
                    break;
            }
        });

        indexOrderTotal();
    }

    private void indexOrderTotal() {
        showProgressDialog();
        String token = GetTokenUtils.getToken(this, ApiStores.API_SERVER_ORDER_TOTAL);
        addSubscription(apiStores().indexOrderTotal(token, mOrId), new ApiCallback<String>() {
            @Override
            public void onSuccess(String model) {
                // {"code":1,"msg":"成功","time":1570864958,"data":{"or_num":"20191010461","total":5060}}
                try {
                    JSONObject result = new JSONObject(model);
                    if (result.optInt("code", 0) == 1) {
                        mOrNum = result.optJSONObject("data").optString("or_num", "");
                        mOrTotal = result.optJSONObject("data").optString("total", "");

                        mOrderNum.setText(mOrNum);
                        mOrderPrice.setText(String.format(getString(R.string.total_top_text), mOrTotal));
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

    @Override
    public void onClick(Object o) {
        View v = (View) o;
        switch (v.getId()) {
            case R.id.refund_sure:
                /*if (mOrderPaymentInt <= 0) {
                    toastShow(R.string.payment_order_error_payment);
                    return;
                }
                if (TextUtils.isEmpty(mOrderPaymentAccount.getText().toString().trim())) {
                    toastShow(R.string.gujujin_payment_account_tip);
                    return;
                }*/
                if (mListImg.size() <= 1) {
                    toastShow(R.string.gujujin_upload_credentials_pic_tip);
                    return;
                }
                indexOrderUpload(mListImg.get(1));
                break;
            default:
                break;
        }
    }

    private void indexOrderUpload(String filePath) {
        showProgressDialog();

        File file = new File(filePath);
        Logger.i("TAG_UpLoad", file.getAbsolutePath());
        MultipartBody.Builder bodyBuilder = new MultipartBody.Builder()
                .setType(MultipartBody.FORM) // 表单类型
                .addFormDataPart("or_id", mOrId)
                .addFormDataPart("or_num", mOrNum)
                .addFormDataPart("or_voucher", file.getName(), RequestBody.create(file, MediaType.parse("multipart/form-data")));
        List<MultipartBody.Part> parts = bodyBuilder.build().parts();

        String token = GetTokenUtils.getToken(this, ApiStores.API_SERVER_ORDER_UPLOAD);
        addSubscription(apiStores().indexOrderUpload(token, parts), new ApiCallback<String>() {
            @Override
            public void onSuccess(String model) {
                try {
                    JSONObject result = new JSONObject(model);
                    if (result.getInt("code") == 1) {
                        toastShow(result.getString("msg"));
                        ApplyResultActivity.start(mActivity, 4);
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

    private void initRecyclerView() {
        mRecyclerView.setLayoutManager(new GridLayoutManager(mActivity, 4, GridLayoutManager.VERTICAL, false));
        mRecyclerView.setNestedScrollingEnabled(false);
        mRecyclerView.addItemDecoration(new GridDividerItemDecoration(10, Color.TRANSPARENT));
        mAdapter = new ProductDetailsAdapter(mActivity, mListImg, 5);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener((view, position) -> {
            if (getMaxSelectable() > 0) {
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
                                    .maxSelectable(getMaxSelectable())
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
            }
        });

        // 3 - 1 + 1 = 3
        // 3 - 2 + 1 = 2
        // 3 - 3 + 1 = 1
        // 3 - 4 + 1 = 0
        mListImg.add("addSelect");
    }

    private int getMaxSelectable() {
        return maxSelectable - mListImg.size() + 1;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == 10001) {
                if (null != data) {
                    List<String> mSelected = Matisse.obtainPathResult(data);
                    if (null != mSelected && !mSelected.isEmpty()) {
                        mListImg.addAll(mSelected);
                        mAdapter.notifyDataSetChanged();
                    }
                }
            }
        }
    }

}
