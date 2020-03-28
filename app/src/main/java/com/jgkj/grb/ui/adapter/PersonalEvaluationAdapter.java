package com.jgkj.grb.ui.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.jgkj.grb.R;
import com.jgkj.grb.base.BaseViewHolder;
import com.jgkj.grb.glide.GlideApp;
import com.jgkj.grb.glide.RoundTransformation;
import com.jgkj.grb.itemdecoration.SpaceItemDecoration;
import com.jgkj.grb.onclick.OnItemClickListener;
import com.jgkj.grb.retrofit.ApiStores;
import com.jgkj.grb.ui.mvp.personal.PersonalEvaluationModel;
import com.jgkj.grb.utils.ScreenUtils;

import java.util.List;

import butterknife.BindView;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * 我的评价
 * Created by brightpoplar@163.com on 2019/8/21.
 */
public class PersonalEvaluationAdapter extends RecyclerView.Adapter {
    private Context mContext;
    private List<PersonalEvaluationModel.DataBean> mList;
    private LayoutInflater mLayoutInflater;
    private OnItemActionListener mOnItemActionListener;

    public void setOnItemActionListener(OnItemActionListener mOnItemActionListener) {
        this.mOnItemActionListener = mOnItemActionListener;
    }

    public PersonalEvaluationAdapter(Context mContext, List<PersonalEvaluationModel.DataBean> mList) {
        this.mContext = mContext;
        this.mList = mList;
        this.mLayoutInflater = LayoutInflater.from(mContext);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new PersonalEvaluation(mLayoutInflater.inflate(R.layout.layout_item_personal_evaluation, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        if (viewHolder instanceof PersonalEvaluation) {
            ((PersonalEvaluation) viewHolder).bindView(mList.get(i), i);
        }
    }

    @Override
    public int getItemCount() {
        return null == mList ? 0 : mList.size();
    }

    class PersonalEvaluation extends BaseViewHolder {
        @BindView(R.id.evaluation_info)
        FrameLayout evaluationInfo;
        @BindView(R.id.evaluation_userhead)
        CircleImageView userhead;
        @BindView(R.id.evaluation_username)
        TextView username;
        @BindView(R.id.evaluate_level)
        TextView evaluateLevel;
        @BindView(R.id.evaluate_delete)
        FrameLayout evaluateDelete;
        @BindView(R.id.evaluate_date)
        TextView evaluateDate;
        @BindView(R.id.evaluate_desc)
        TextView evaluateDesc;
        @BindView(R.id.recycler_view)
        RecyclerView recyclerView;
        ProductDetailsAdapter adapter;

        @BindView(R.id.shop_info)
        FrameLayout shopInfo;
        @BindView(R.id.shop_iv)
        ImageView shopIv;
        @BindView(R.id.shop_name)
        TextView shopName;
        @BindView(R.id.shop_specs)
        TextView shopSpecs;

        PersonalEvaluationModel.DataBean data;
        int position;

        public PersonalEvaluation(View itemView) {
            super(itemView);
            recyclerView.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false));
            recyclerView.addItemDecoration(new SpaceItemDecoration(ScreenUtils.dp2px(mContext, 5)));
        }

        public void bindView(PersonalEvaluationModel.DataBean bean, int pos) {
            data = bean;
            position = pos;
            getItemView().setOnClickListener(v -> {
            });
            evaluationInfo.setOnClickListener(v -> {
                if (null != mOnItemActionListener) {
                    mOnItemActionListener.onItemEvaluationClick(v, position);
                }
            });
            evaluateDelete.setOnClickListener(v -> {
                if (null != mOnItemActionListener) {
                    mOnItemActionListener.onItemDeleteClick(v, position);
                }
            });
            shopInfo.setOnClickListener(v -> {
                if (null != mOnItemActionListener) {
                    mOnItemActionListener.onItemShopClick(v, position);
                }
            });

            adapter = new ProductDetailsAdapter(mContext, data.getPic(), 3);
            recyclerView.setAdapter(adapter);

            if (!TextUtils.isEmpty(data.getUs_head_pic()))
                GlideApp.with(mContext)
                        .load(data.getUs_head_pic().startsWith("http:") || data.getUs_head_pic().startsWith("https:")
                                ? data.getUs_head_pic().replaceAll("\\\\", "/")
                                : ApiStores.API_SERVER_URL + data.getUs_head_pic().replaceAll("\\\\", "/"))
                        .centerCrop()
                        .into(userhead);
            username.setText(data.getUs_nickname());
            // level 评价等级：1好评，2中评，3差评
            if (data.getLevel() == 1) {
                evaluateLevel.setText(R.string.personal_evaluation_level_1);
                evaluateLevel.setTextColor(Color.parseColor("#FF4343"));
                evaluateLevel.setCompoundDrawablesWithIntrinsicBounds(R.mipmap.ic_product_evaluate_good, 0, 0, 0);
            } else if (data.getLevel() == 2) {
                evaluateLevel.setText(R.string.personal_evaluation_level_2);
                evaluateLevel.setTextColor(Color.parseColor("#666666"));
                evaluateLevel.setCompoundDrawablesWithIntrinsicBounds(R.mipmap.ic_product_evaluate_bad_gray, 0, 0, 0);
            } else if (data.getLevel() == 3) {
                evaluateLevel.setText(R.string.personal_evaluation_level_3);
                evaluateLevel.setTextColor(Color.parseColor("#666666"));
                evaluateLevel.setCompoundDrawablesWithIntrinsicBounds(R.mipmap.ic_product_evaluate_bad_gray, 0, 0, 0);
            }
            evaluateDate.setText(data.getAdd_time());
            evaluateDesc.setText(data.getContent());
            if (!TextUtils.isEmpty(data.getPd_pic()))
                GlideApp.with(mContext)
                        .load(data.getPd_pic().startsWith("http:") || data.getPd_pic().startsWith("https:")
                                ? data.getPd_pic().replaceAll("\\\\", "/")
                                : ApiStores.API_SERVER_URL + data.getPd_pic().replaceAll("\\\\", "/"))
                        .transform(new CenterCrop(), new RoundTransformation(mContext, 5))
                        .into(shopIv);
            shopName.setText(data.getPd_name());
            shopSpecs.setText(data.getPd_spec());
        }
    }

    public interface OnItemActionListener extends OnItemClickListener {
        void onItemEvaluationClick(View v, int postion);

        void onItemDeleteClick(View v, int postion);

        void onItemShopClick(View v, int postion);
    }
}
