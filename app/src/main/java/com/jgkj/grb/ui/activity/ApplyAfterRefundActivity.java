package com.jgkj.grb.ui.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Base64;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.jgkj.grb.R;
import com.jgkj.grb.base.BaseActivity;
import com.jgkj.grb.glide.GlideApp;
import com.jgkj.grb.itemdecoration.GridDividerItemDecoration;
import com.jgkj.grb.onclick.RxView;
import com.jgkj.grb.retrofit.ApiCallback;
import com.jgkj.grb.retrofit.ApiStores;
import com.jgkj.grb.ui.adapter.ProductDetailsAdapter;
import com.jgkj.grb.ui.bean.RefundReasonsBean;
import com.jgkj.grb.ui.dialog.RefundReasonsDialog;
import com.jgkj.grb.ui.mvp.OrderManagementModel;
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
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * 申请售后：申请方式 → 退货退款
 */
public class ApplyAfterRefundActivity extends BaseActivity {

    public static void start(Context context, OrderManagementModel.DataBean.DetailBean data) {
        Intent intent = new Intent(context, ApplyAfterRefundActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("data", data);
        intent.putExtra("bundle", bundle);
        context.startActivity(intent);
    }

    @BindView(R.id.shop_iv)
    ImageView mShopIv;
    @BindView(R.id.shop_name)
    TextView mShopName;

    @BindView(R.id.refund_reasons)
    FrameLayout mRefundReasons;
    @BindView(R.id.refund_reasons_tv)
    TextView mRefundReasonsTv;

    @BindView(R.id.total_top)
    TextView mRefundTotalTop;
    @BindView(R.id.total_bottom)
    TextView mRefundTotalBottom;

    @BindView(R.id.refund_note_et)
    EditText mRefundNoteEt;
    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;

    @BindView(R.id.refund_sure)
    FrameLayout mRefundSure;

    OrderManagementModel.DataBean.DetailBean mData;
    List<RefundReasonsBean> mListReason = new ArrayList<>();

    ProductDetailsAdapter mAdapter;
    List<String> mListImg = new ArrayList<>();
    int maxSelectable = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apply_after_refund);

        Intent intent = getIntent();
        if (!intent.hasExtra("bundle")) {
            toastShow(R.string.open_activity_apply_after_error_option);
            onBackPressed();
            return;
        }
        Bundle bundle = intent.getBundleExtra("bundle");
        if (null == bundle) {
            toastShow(R.string.open_activity_apply_after_error_option);
            onBackPressed();
            return;
        }
        mData = (OrderManagementModel.DataBean.DetailBean) bundle.getSerializable("data");
        if (null == mData) {
            toastShow(R.string.open_activity_apply_after_error_option);
            onBackPressed();
            return;
        }

        Toolbar toolbar = initToolBar("");
        toolbar.setTitle(R.string.activity_title_apply_after_refund);

        RxView.setOnClickListeners(this, mRefundReasons, mRefundSure);

        String[] refundReasons = getResources().getStringArray(R.array.after_refund_reasons);
        for (String refundReason : refundReasons) {
            mListReason.add(new RefundReasonsBean(refundReason));
        }
        initRecyclerView();
        initGoodsView();
    }

    private void initGoodsView() {
        if (!TextUtils.isEmpty(mData.getPd_head_pic()))
            GlideApp.with(this)
                    .load(mData.getPd_head_pic().startsWith("http:") || mData.getPd_head_pic().startsWith("https:")
                            ? mData.getPd_head_pic().replaceAll("\\\\", "/")
                            : ApiStores.API_SERVER_URL + mData.getPd_head_pic().replaceAll("\\\\", "/"))
                    .into(mShopIv);
        mShopName.setText(mData.getOr_pd_name());
        mRefundTotalTop.setText(String.format(getString(R.string.total_top_text), mData.getOr_pd_total()));
        // mRefundTotalBottom.setText(String.format(getString(R.string.total_bottom_text), mData.getOr_pd_pv()));
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

    @Override
    public void onClick(Object o) {
        View v = (View) o;
        switch (v.getId()) {
            case R.id.refund_reasons:
                hideInputSoft();
                RefundReasonsDialog mDialog = new RefundReasonsDialog(this);
                mDialog.show();
                mDialog.setOnActionClickListener(new RefundReasonsDialog.OnActionClickListener() {
                    @Override
                    public void onCancel() {
                    }

                    @Override
                    public void onItemSelect(int position) {
                        for (int i = 0; i < mListReason.size(); i++) {
                            mListReason.get(i).setSelect(false);
                        }
                        mListReason.get(position).setSelect(true);
                        mDialog.notifyDataSetChanged();
                        mRefundReasonsTv.setText(mListReason.get(position).getReasons());
                    }
                });
                mDialog.setDialogTitle(getString(R.string.apply_after_refund_reason));
                mDialog.setRecyclerViewDatas(mListReason);
                break;
            case R.id.refund_sure:
                if (TextUtils.isEmpty(mRefundReasonsTv.getText().toString().trim())) {
                    toastShow(R.string.apply_after_refund_reason_tip);
                    return;
                }
                if (TextUtils.isEmpty(mRefundNoteEt.getText().toString().trim())) {
                    toastShow(R.string.apply_after_refund_note_tip);
                    return;
                }
                //                if (mListImg.size() <= 1) {
                //                    toastShow("请选择图片...");
                //                    return;
                //                }
                indexOrderBackgoods();
                break;
            default:
                break;
        }
    }

    /**
     * 申请退款：提交申请
     */
    private void indexOrderBackgoods() {
        showProgressDialog();

        //1.创建MultipartBody.Builder对象
        MultipartBody.Builder bodyBuilder = new MultipartBody.Builder()
                .setType(MultipartBody.FORM) // 表单类型
                //2.调用MultipartBody.Builder的addFormDataPart()方法添加表单数据
                .addFormDataPart("or_id", String.valueOf(mData.getOr_id()))
                .addFormDataPart("or_son_id", String.valueOf(mData.getId()))
                .addFormDataPart("note", mRefundReasonsTv.getText().toString().trim())
                .addFormDataPart("content", mRefundNoteEt.getText().toString().trim())
                .addFormDataPart("type", String.valueOf(2));
        //3.获取图片，创建请求体，表单类型
        for (int i = 1; i < mListImg.size(); i++) {
            File file = new File(mListImg.get(i));
            Logger.i("TAG_UpLoad", file.getAbsolutePath());
            bodyBuilder.addFormDataPart("pic[]", file.getName(), RequestBody.create(file, MediaType.parse("multipart/form-data")));
        }
        // 4.创建List<MultipartBody.Part> 集合，
        //  调用MultipartBody.Builder的build()方法会返回一个新创建的MultipartBody
        //  再调用MultipartBody的parts()方法返回MultipartBody.Part集合
        List<MultipartBody.Part> parts = bodyBuilder.build().parts();

        String token = GetTokenUtils.getToken(this, ApiStores.API_SERVER_ORDER_BACKGOODS);
        addSubscription(apiStores().indexOrderBackgoods(token, parts), new ApiCallback<String>() {
            @Override
            public void onSuccess(String model) {
                // {"code":1,"msg":"成功","time":1568171708,"data":"申请成功"}
                try {
                    JSONObject result = new JSONObject(model);
                    if (result.getInt("code") == 1) {
                        ApplyResultActivity.start(mActivity, 1);
                        onBackPressed();
                        toastShow(result.getString("msg"));
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

    /**
     * 将图片转换成Base64编码的字符串
     */
    private String imageToBase64(String path) {
        if (TextUtils.isEmpty(path)) {
            return null;
        }
        InputStream is = null;
        byte[] data = null;
        String result = null;
        try {
            is = new FileInputStream(path);
            //创建一个字符流大小的数组。
            data = new byte[is.available()];
            //写入数组
            is.read(data);
            //用默认的编码格式进行编码
            result = Base64.encodeToString(data, Base64.DEFAULT);
        } catch (Exception e) {
        } finally {
            if (null != is) {
                try {
                    is.close();
                } catch (IOException e) {
                }
            }
        }
        return result;
    }

    /**
     * 将Base64编码转换为图片
     */
    private boolean base64ToFile(String base64Str, String path) {
        byte[] data = Base64.decode(base64Str, Base64.NO_WRAP);
        for (int i = 0; i < data.length; i++) {
            if (data[i] < 0) {
                //调整异常数据
                data[i] += 256;
            }
        }
        OutputStream os = null;
        try {
            os = new FileOutputStream(path);
            os.write(data);
            os.flush();
            os.close();
            return true;
        } catch (FileNotFoundException e) {
            return false;
        } catch (IOException e) {
            return false;
        }
    }

}
