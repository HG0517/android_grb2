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
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jgkj.grb.R;
import com.jgkj.grb.base.BaseActivity;
import com.jgkj.grb.itemdecoration.GridDividerItemDecoration;
import com.jgkj.grb.onclick.RxView;
import com.jgkj.grb.ui.adapter.ProductDetailsAdapter;
import com.jgkj.grb.ui.bean.RefundReasonsBean;
import com.jgkj.grb.ui.dialog.ApplyAfterSpecsBottomDialog;
import com.jgkj.grb.ui.dialog.RefundReasonsDialog;
import com.jgkj.grb.ui.bean.SpecsModel;
import com.jgkj.grb.ui.mvp.personal.AddresManagementModel;
import com.jgkj.grb.ui.mvp.product.ProductDetailsModel;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.runtime.Permission;
import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;
import com.zhihu.matisse.engine.impl.GlideEngine;
import com.zhihu.matisse.internal.entity.CaptureStrategy;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 申请售后：申请方式 → 换货
 */
public class ApplyAfterExchangeActivity extends BaseActivity {

    public static void start(Context context) {
        Intent intent = new Intent(context, ApplyAfterExchangeActivity.class);
        context.startActivity(intent);
    }

    @BindView(R.id.shop_iv)
    ImageView mShopIv;
    @BindView(R.id.shop_name)
    TextView mShopName;

    @BindView(R.id.exchange_reasons)
    FrameLayout mExchangeReasons;
    @BindView(R.id.exchange_reasons_tv)
    TextView mExchangeReasonsTv;

    @BindView(R.id.total_num_cut)
    FrameLayout mExchangeNumCut;
    @BindView(R.id.total_num_add)
    FrameLayout mExchangeNumAdd;
    @BindView(R.id.total_num)
    TextView mExchangeNum;
    @BindView(R.id.exchange_total_max)
    TextView mExchangeNumMax;
    int mExchangeTotalMax = 2;
    int mExchangeNumInput = 1;

    @BindView(R.id.select_address)
    FrameLayout mExchangeSelectAddress;
    @BindView(R.id.select_address_tip)
    TextView mExchangeSelectAddressTip;
    @BindView(R.id.select_address_content)
    RelativeLayout mExchangeSelectAddressContent;
    @BindView(R.id.username)
    TextView mExchangeSelectAddressUsername;
    @BindView(R.id.user_phone)
    TextView mExchangeSelectAddressUserphone;
    @BindView(R.id.select_address_detail)
    TextView mExchangeSelectAddressDetail;

    @BindView(R.id.exchange_specs)
    FrameLayout mExchangeSpecs;
    @BindView(R.id.exchange_specs_tv)
    TextView mExchangeSpecsTv;

    @BindView(R.id.exchange_note_et)
    EditText mExchangeNoteEt;
    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;

    @BindView(R.id.exchange_sure)
    FrameLayout mExchangeSure;

    List<RefundReasonsBean> mListReason = new ArrayList<>();

    ProductDetailsAdapter mAdapter;
    List<String> mListImg = new ArrayList<>();
    int maxSelectable = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apply_after_exchange);

        Toolbar toolbar = initToolBar("");
        toolbar.setTitle(R.string.activity_title_apply_after_exchange);

        RxView.setOnClickListeners(this, mExchangeReasons, mExchangeNumCut, mExchangeNumAdd, mExchangeSelectAddress, mExchangeSpecs, mExchangeSure);

        String[] refundReasons = getResources().getStringArray(R.array.after_exchange_reasons);
        for (String refundReason : refundReasons) {
            mListReason.add(new RefundReasonsBean(refundReason));
        }
        initRecyclerView();

        mExchangeNumMax.setText(String.format(getString(R.string.after_exchange_total_max), mExchangeTotalMax));
        mExchangeNum.setText(String.valueOf(mExchangeNumInput));
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
            } else if (requestCode == 10007) {
                if (null != data && data.hasExtra("bundle")) {
                    Bundle bundle = data.getBundleExtra("bundle");
                    AddresManagementModel.DataBean dataBean = (AddresManagementModel.DataBean) bundle.getSerializable("addr");
                    if (null != dataBean) {
                        mExchangeSelectAddressTip.setVisibility(View.GONE);
                        mExchangeSelectAddressContent.setVisibility(View.VISIBLE);
                        mExchangeSelectAddressUsername.setText(dataBean.getAddr_receiver());
                        mExchangeSelectAddressUserphone.setText(dataBean.getAddr_tel());
                        mExchangeSelectAddressDetail.setText(String.format("%s%s%s%s", dataBean.getProvince(), dataBean.getCity(), dataBean.getTown(), dataBean.getAddr_detail()));
                    }
                }
            }
        }
    }

    @Override
    public void onClick(Object o) {
        View v = (View) o;
        switch (v.getId()) {
            case R.id.exchange_reasons:
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
                        mExchangeReasonsTv.setText(mListReason.get(position).getReasons());
                    }
                });
                mDialog.setDialogTitle(getString(R.string.apply_after_exchange_title));
                mDialog.setRecyclerViewDatas(mListReason);
                break;
            case R.id.total_num_cut:
                mExchangeNumInput--;
                if (mExchangeNumInput < 1) {
                    mExchangeNumInput = 1;
                }
                mExchangeNum.setText(String.valueOf(mExchangeNumInput));
                break;
            case R.id.total_num_add:
                mExchangeNumInput++;
                if (mExchangeNumInput > mExchangeTotalMax) {
                    mExchangeNumInput = mExchangeTotalMax;
                }
                mExchangeNum.setText(String.valueOf(mExchangeNumInput));
                break;
            case R.id.select_address:
                AddressManagementActivity.startActivityForResult(this);
                break;
            case R.id.exchange_specs:
                getSpecs();
                break;
            case R.id.exchange_sure:
                ApplyResultActivity.start(mActivity);
                onBackPressed();
                break;
            default:
                break;
        }
    }

    private void getSpecs() {
        SpecsModel specsModel = new SpecsModel();
        List<SpecsModel.SpecsBean> list = new ArrayList<>();

        SpecsModel.SpecsBean specsBean = new SpecsModel.SpecsBean();
        specsBean.setId(0);
        specsBean.setName(getString(R.string.apply_after_exchange_specs));
        List<ProductDetailsModel.DataBean.SkusBean> listChild = new ArrayList<>();
        specsBean.setList(listChild);
        list.add(specsBean);

        specsModel.setList(list);

        ApplyAfterSpecsBottomDialog dialog = ApplyAfterSpecsBottomDialog.newInstance(R.mipmap.ic_close_circle_black, specsModel);
        dialog.showDialog(getSupportFragmentManager());
        dialog.setOnBottomDialogListener(new ApplyAfterSpecsBottomDialog.BottomDialogListener() {
            @Override
            public void onConfirm() {
            }

            @Override
            public void onBuyClickListener() {
            }

            @Override
            public void OnSelectedClickListener(View view, int pIndex, int cIndex) {
            }
        });
    }

}
