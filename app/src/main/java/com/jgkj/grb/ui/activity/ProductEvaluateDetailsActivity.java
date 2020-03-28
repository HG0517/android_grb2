package com.jgkj.grb.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.jgkj.grb.R;
import com.jgkj.grb.base.BaseActivity;
import com.jgkj.grb.glide.GlideApp;
import com.jgkj.grb.glide.RoundTransformation;
import com.jgkj.grb.itemdecoration.SpaceItemDecoration;
import com.jgkj.grb.onclick.RxView;
import com.jgkj.grb.retrofit.ApiStores;
import com.jgkj.grb.ui.adapter.ProductDetailsAdapter;
import com.jgkj.grb.ui.mvp.personal.PersonalEvaluationModel;

import butterknife.BindView;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * 商品评价：评价详情
 */
public class ProductEvaluateDetailsActivity extends BaseActivity {

    public static void start(Context context) {
        Intent intent = new Intent(context, ProductEvaluateDetailsActivity.class);
        context.startActivity(intent);
    }

    public static void start(Context context, PersonalEvaluationModel.DataBean data) {
        Intent intent = new Intent(context, ProductEvaluateDetailsActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("data", data);
        intent.putExtra("bundle", bundle);
        context.startActivity(intent);
    }

    @BindView(R.id.evaluation_userhead)
    CircleImageView mEvaluationUserhead;
    @BindView(R.id.evaluation_username)
    TextView mEvaluationUsername;
    @BindView(R.id.evaluate_level)
    TextView mEvaluateLevel;
    @BindView(R.id.evaluate_date)
    TextView mEvaluateDate;
    @BindView(R.id.evaluate_desc)
    TextView mEvaluateDesc;

    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;

    @BindView(R.id.shop_bottom)
    FrameLayout mShopBottom;
    @BindView(R.id.shop_iv)
    ImageView mShopIv;
    @BindView(R.id.shop_name)
    TextView mShopName;
    @BindView(R.id.shop_specs)
    TextView mShopSpecs;
    @BindView(R.id.total_top)
    TextView mTotalTop;
    @BindView(R.id.total_bottom)
    TextView mTotalBottom;

    PersonalEvaluationModel.DataBean mData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_evaluate_details);

        Toolbar toolbar = initToolBar(getString(R.string.activity_title_product_evaluate_details));
        Intent intent = getIntent();
        if (intent.hasExtra("bundle")) {
            Bundle bundle = intent.getBundleExtra("bundle");
            mData = (PersonalEvaluationModel.DataBean) bundle.getSerializable("data");
        }
        if (null == mData) {
            toastShow(R.string.open_activity_error_option);
            onBackPressed();
            return;
        }

        RxView.setOnClickListeners(this, mShopBottom);
        showView();
    }

    private void showView() {
        if (!TextUtils.isEmpty(mData.getUs_head_pic()))
            GlideApp.with(this)
                    .load(mData.getAnonymous() == 1 ? R.mipmap.ic_default_head
                            : mData.getUs_head_pic().startsWith("http:") || mData.getUs_head_pic().startsWith("https:")
                            ? mData.getUs_head_pic().replaceAll("\\\\", "/")
                            : ApiStores.API_SERVER_URL + mData.getUs_head_pic().replaceAll("\\\\", "/"))
                    .centerCrop()
                    .into(mEvaluationUserhead);
        mEvaluationUsername.setText(mData.getAnonymous() == 1 ? getString(R.string.product_evaluate_anonymous) : mData.getUs_nickname());
        if (mData.getLevel() == 1) {
            mEvaluateLevel.setText(R.string.personal_evaluation_level_1);
            mEvaluateLevel.setTextColor(Color.parseColor("#FF4343"));
            mEvaluateLevel.setCompoundDrawablesWithIntrinsicBounds(R.mipmap.ic_product_evaluate_good, 0, 0, 0);
        } else if (mData.getLevel() == 2) {
            mEvaluateLevel.setText(R.string.personal_evaluation_level_2);
            mEvaluateLevel.setTextColor(Color.parseColor("#666666"));
            mEvaluateLevel.setCompoundDrawablesWithIntrinsicBounds(R.mipmap.ic_product_evaluate_bad_gray, 0, 0, 0);
        } else if (mData.getLevel() == 3) {
            mEvaluateLevel.setText(R.string.personal_evaluation_level_3);
            mEvaluateLevel.setTextColor(Color.parseColor("#666666"));
            mEvaluateLevel.setCompoundDrawablesWithIntrinsicBounds(R.mipmap.ic_product_evaluate_bad_gray, 0, 0, 0);
        }
        mEvaluateDate.setText(mData.getAdd_time());
        mEvaluateDesc.setText(mData.getContent());

        mRecyclerView.setNestedScrollingEnabled(false);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.addItemDecoration(new SpaceItemDecoration(dp2px(8)));
        ProductDetailsAdapter adapter = new ProductDetailsAdapter(this, mData.getPic(), 2);
        mRecyclerView.setAdapter(adapter);

        if (!TextUtils.isEmpty(mData.getPd_pic()))
            GlideApp.with(this)
                    .load(mData.getPd_pic().startsWith("http:") || mData.getPd_pic().startsWith("https:")
                            ? mData.getPd_pic().replaceAll("\\\\", "/")
                            : ApiStores.API_SERVER_URL + mData.getPd_pic().replaceAll("\\\\", "/"))
                    .transform(new CenterCrop(), new RoundTransformation(this, 5))
                    .into(mShopIv);
        mShopName.setText(mData.getPd_name());
        mShopSpecs.setText(mData.getPd_spec());
        mTotalTop.setText(String.format(getString(R.string.total_top_text), mData.getPd_total()));
        //mTotalBottom.setText(getTotalBottom(mData));
    }

    private String getTotalBottom(PersonalEvaluationModel.DataBean model) {
        // "paytype2": 是否有grc支付；
        // "paytype1": 是否有grb支付；,
        // "paytype": 是否有现金支付,
        String payType = "";
        /*if (model.getPaytype() == 1) {
            payType += model.getPd_price() + "现金";
        }*/
        if (model.getPaytype1() == 1) {
            payType += /*"\n" + */model.getPd_grb() + "份GRB";
        }
        if (model.getPaytype2() == 1) {
            payType += (model.getPaytype1() == 1 ? "\n" : "") + model.getPd_grc() + "份GRC";
        }
        return payType;
    }

    @Override
    public void onClick(Object o) {
        View v = (View) o;
        switch (v.getId()) {
            case R.id.shop_bottom:
                ProductDetailsActivity.start(mActivity, String.valueOf(mData.getP_id()));
                break;
            default:
                break;
        }
    }
}
