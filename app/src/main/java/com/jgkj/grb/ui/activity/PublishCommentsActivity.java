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
import android.view.Menu;
import android.view.MenuItem;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.jgkj.grb.R;
import com.jgkj.grb.base.BaseActivity;
import com.jgkj.grb.glide.GlideApp;
import com.jgkj.grb.itemdecoration.GridDividerItemDecoration;
import com.jgkj.grb.retrofit.ApiCallback;
import com.jgkj.grb.retrofit.ApiStores;
import com.jgkj.grb.ui.adapter.ProductDetailsAdapter;
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
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * 发表评价
 */
public class PublishCommentsActivity extends BaseActivity {

    public static void start(Context context, OrderManagementModel.DataBean.DetailBean data) {
        Intent intent = new Intent(context, PublishCommentsActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("data", data);
        intent.putExtra("bundle", bundle);
        context.startActivity(intent);
    }

    @BindView(R.id.shop_iv)
    ImageView mPublishShopIv;
    @BindView(R.id.shop_name)
    TextView mPublishShopName;

    @BindView(R.id.publish_level)
    RadioGroup mPublishLevel;
    @BindView(R.id.publish_level_0)
    RadioButton mPublishLevel0;
    @BindView(R.id.publish_level_1)
    RadioButton mPublishLevel1;
    @BindView(R.id.publish_level_2)
    RadioButton mPublishLevel3;

    @BindView(R.id.publish_content_et)
    EditText mPublishContentEt;
    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;

    @BindView(R.id.publish_anonymous)
    CheckBox mPublishAnonymous;

    ProductDetailsAdapter mAdapter;
    List<String> mList = new ArrayList<>();

    OrderManagementModel.DataBean.DetailBean mData;
    int maxSelectable = 3;
    int publishLevel = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_publish_comments);

        Toolbar toolbar = initToolBar(getString(R.string.activity_title_publish_comments));

        Intent intent = getIntent();
        if (intent.hasExtra("bundle")) {
            Bundle bundle = intent.getBundleExtra("bundle");
            mData = (OrderManagementModel.DataBean.DetailBean) bundle.getSerializable("data");
        }
        if (null == mData) {
            toastShow(R.string.open_activity_error_option);
            onBackPressed();
            return;
        }

        initRadioButton();
        initRecyclerView();
        GlideApp.with(this)
                .load(mData.getPd_head_pic().startsWith("http:") || mData.getPd_head_pic().startsWith("https:")
                        ? mData.getPd_head_pic().replaceAll("\\\\", "/")
                        : ApiStores.API_SERVER_URL + mData.getPd_head_pic().replaceAll("\\\\", "/"))
                .centerCrop()
                .into(mPublishShopIv);
        mPublishShopName.setText(mData.getOr_pd_name());
    }

    private void orderComment() {
        showProgressDialog();
        String token = GetTokenUtils.getToken(this, ApiStores.API_SERVER_ORDER_COMMENT);
        MultipartBody.Builder bodyBuilder = new MultipartBody.Builder()
                .setType(MultipartBody.FORM) // 表单类型
                .addFormDataPart("or_id", String.valueOf(mData.getOr_id()))
                .addFormDataPart("or_son_id", String.valueOf(mData.getId()))
                .addFormDataPart("content", mPublishContentEt.getText().toString().trim())
                .addFormDataPart("anonymous", String.valueOf(mPublishAnonymous.isChecked() ? 1 : 2))
                .addFormDataPart("level", String.valueOf(publishLevel));
        for (int i = 1; i < mList.size(); i++) {
            File file = new File(mList.get(i));
            Logger.i("TAG_UpLoad", file.getAbsolutePath());
            bodyBuilder.addFormDataPart("pic[]", file.getName(), RequestBody.create(file, MediaType.parse("multipart/form-data")));
        }
        List<MultipartBody.Part> parts = bodyBuilder.build().parts();

        addSubscription(apiStores().orderComment(token, parts), new ApiCallback<String>() {
            @Override
            public void onSuccess(String model) {
                // {"code":2,"msg":"请输入绑定的手机号","time":1568686068,"data":""}
                try {
                    JSONObject result = new JSONObject(model);
                    if (result.getInt("code") == 1) {
                        toastShow(result.getString("msg"));
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

    private void initRadioButton() {
        mPublishLevel.setOnCheckedChangeListener((group, checkedId) -> {
            switch (checkedId) {
                case R.id.publish_level_0:
                    publishLevel = 1;
                    break;
                case R.id.publish_level_1:
                    publishLevel = 2;
                    break;
                case R.id.publish_level_2:
                    publishLevel = 3;
                    break;
                default:
                    break;
            }
        });
    }

    private void initRecyclerView() {
        mRecyclerView.setLayoutManager(new GridLayoutManager(mActivity, 4, GridLayoutManager.VERTICAL, false));
        mRecyclerView.setNestedScrollingEnabled(false);
        mRecyclerView.addItemDecoration(new GridDividerItemDecoration(10, Color.TRANSPARENT));
        mAdapter = new ProductDetailsAdapter(mActivity, mList, 4);
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
        mList.add("addSelect");
    }

    private int getMaxSelectable() {
        return maxSelectable - mList.size() + 1;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == 10001) {
                if (null != data) {
                    List<String> mSelected = Matisse.obtainPathResult(data);
                    if (null != mSelected && !mSelected.isEmpty()) {
                        mList.addAll(mSelected);
                        mAdapter.notifyDataSetChanged();
                    }
                }
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_publish:
                if (TextUtils.isEmpty(mPublishContentEt.getText().toString().trim())) {
                    toastShow(R.string.publish_comments_input_tip);
                    return true;
                }
                if (mList.size() <= 1) {
                    toastShow(R.string.publish_comments_pic_tip);
                    return true;
                }
                orderComment();
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_publish_comment, menu);
        return true;
    }
}
