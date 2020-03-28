package com.jgkj.grb.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.jgkj.grb.R;
import com.jgkj.grb.base.BaseActivity;
import com.jgkj.grb.glide.GlideApp;
import com.jgkj.grb.onclick.RxView;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.runtime.Permission;
import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;
import com.zhihu.matisse.engine.impl.GlideEngine;
import com.zhihu.matisse.internal.entity.CaptureStrategy;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;

/**
 * 实名认证
 */
public class RealnameFirstActivity extends BaseActivity {

    public static void startActivityForResult(Activity activity) {
        Intent intent = new Intent(activity, RealnameFirstActivity.class);
        activity.startActivityForResult(intent, 10008);
    }

    @BindView(R.id.cardid_front_cv)
    CardView mCardidFrontCv;
    @BindView(R.id.cardid_front_top_iv)
    ImageView mCardidFrontTopIv;
    @BindView(R.id.cardid_front_tip)
    TextView mCardidFrontTip;
    @BindView(R.id.cardid_front_iv)
    ImageView mCardidFrontIv;

    @BindView(R.id.cardid_back_cv)
    CardView mCardidBackCv;
    @BindView(R.id.cardid_back_top_iv)
    ImageView mCardidBackTopIv;
    @BindView(R.id.cardid_back_tip)
    TextView mCardidBackTip;
    @BindView(R.id.cardid_back_iv)
    ImageView mCardidBackIv;

    @BindView(R.id.cardid_positive_cv)
    CardView mCardidPositiveCv;
    @BindView(R.id.cardid_positive_top_iv)
    ImageView mCardidPositiveTopIv;
    @BindView(R.id.cardid_positive_tip)
    TextView mCardidPositiveTip;
    @BindView(R.id.cardid_positive_iv)
    ImageView mCardidPositiveIv;

    @BindView(R.id.cardid_photo_next)
    CardView mCardidPhotoNext;

    String[] mSelected = new String[3];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_realname_first);

        Toolbar toolbar = initToolBar(getString(R.string.safety_center_real_name_binding_text));

        RxView.setOnClickListeners(this, mCardidFrontCv, mCardidBackCv, mCardidPositiveCv, mCardidPhotoNext);
    }

    @Override
    public void onClick(Object o) {
        View v = (View) o;
        switch (v.getId()) {
            case R.id.cardid_front_cv:
                chooseImage(10004);
                break;
            case R.id.cardid_back_cv:
                chooseImage(10005);
                break;
            case R.id.cardid_positive_cv:
                chooseImage(10006);
                break;
            case R.id.cardid_photo_next:
                for (int i = 0; i < mSelected.length; i++) {
                    if (TextUtils.isEmpty(mSelected[i])) {
                        if (i == 0) {
                            toastShow(R.string.realname_first_cardid_front_tip);
                        } else if (i == 1) {
                            toastShow(R.string.realname_first_cardid_back_tip);
                        } else if (i == 2) {
                            toastShow(R.string.realname_first_cardid_positive_tip);
                        }
                        return;
                    }
                }
                RealnameSecondActivity.startActivityForResult(mActivity, new ArrayList<>(Arrays.asList(mSelected)));
                break;
            default:
                break;
        }
    }

    private void chooseImage(int requestCode) {
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
                            .forResult(requestCode);
                })
                .start();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == 10004) {
                if (null != data) {
                    List<String> selected = Matisse.obtainPathResult(data);
                    if (null != selected && !selected.isEmpty()) {
                        mSelected[0] = selected.get(0);
                        mCardidFrontTopIv.setVisibility(View.INVISIBLE);
                        mCardidFrontTip.setVisibility(View.INVISIBLE);
                        mCardidFrontIv.setVisibility(View.VISIBLE);
                        GlideApp.with(this)
                                .load(selected.get(0))
                                .centerCrop()
                                .into(mCardidFrontIv);
                    } else {
                        mCardidFrontTopIv.setVisibility(View.VISIBLE);
                        mCardidFrontTip.setVisibility(View.VISIBLE);
                        mCardidFrontIv.setVisibility(View.INVISIBLE);
                    }
                }
            } else if (requestCode == 10005) {
                if (null != data) {
                    List<String> selected = Matisse.obtainPathResult(data);
                    if (null != selected && !selected.isEmpty()) {
                        mSelected[1] = selected.get(0);
                        mCardidBackTopIv.setVisibility(View.INVISIBLE);
                        mCardidBackTip.setVisibility(View.INVISIBLE);
                        mCardidBackIv.setVisibility(View.VISIBLE);
                        GlideApp.with(this)
                                .load(selected.get(0))
                                .centerCrop()
                                .into(mCardidBackIv);
                    } else {
                        mCardidBackTopIv.setVisibility(View.VISIBLE);
                        mCardidBackTip.setVisibility(View.VISIBLE);
                        mCardidBackIv.setVisibility(View.INVISIBLE);
                    }
                }
            } else if (requestCode == 10006) {
                if (null != data) {
                    List<String> selected = Matisse.obtainPathResult(data);
                    if (null != selected && !selected.isEmpty()) {
                        mSelected[2] = selected.get(0);
                        mCardidPositiveTopIv.setVisibility(View.INVISIBLE);
                        mCardidPositiveTip.setVisibility(View.INVISIBLE);
                        mCardidPositiveIv.setVisibility(View.VISIBLE);
                        GlideApp.with(this)
                                .load(selected.get(0))
                                .centerCrop()
                                .into(mCardidPositiveIv);
                    } else {
                        mCardidPositiveTopIv.setVisibility(View.VISIBLE);
                        mCardidPositiveTip.setVisibility(View.VISIBLE);
                        mCardidPositiveIv.setVisibility(View.INVISIBLE);
                    }
                }
            } else if (requestCode == 10008) {
                setResult(Activity.RESULT_OK);
                onBackPressed();
            }
        }
    }
}
