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
import android.widget.TextView;

import com.jgkj.grb.R;
import com.jgkj.grb.base.BaseViewHolder;
import com.jgkj.grb.glide.GlideApp;
import com.jgkj.grb.itemdecoration.SpaceItemDecoration;
import com.jgkj.grb.onclick.OnItemClickListener;
import com.jgkj.grb.retrofit.ApiStores;
import com.jgkj.grb.ui.mvp.personal.PersonalEvaluationModel;
import com.jgkj.grb.utils.ScreenUtils;

import java.util.List;

import butterknife.BindView;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * 商品评价
 * Created by brightpoplar@163.com on 2019/8/6.
 */
public class ProductEvaluationAdapter extends RecyclerView.Adapter {
    private Context mContext;
    private List<PersonalEvaluationModel.DataBean> mList;
    private LayoutInflater mLayoutInflater;
    private OnItemClickListener mOnItemClickListener;

    public void setOnItemClickListener(OnItemClickListener mOnItemClickListener) {
        this.mOnItemClickListener = mOnItemClickListener;
    }

    public ProductEvaluationAdapter(Context mContext, List<PersonalEvaluationModel.DataBean> mList) {
        this.mContext = mContext;
        this.mList = mList;
        this.mLayoutInflater = LayoutInflater.from(mContext);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ProductEvaluationViewHolder(mLayoutInflater.inflate(R.layout.layout_item_product_evaluation, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        if (viewHolder instanceof ProductEvaluationViewHolder) {
            ((ProductEvaluationViewHolder) viewHolder).bindView(mList.get(i), i);
        }
    }

    @Override
    public int getItemCount() {
        return null == mList ? 0 : mList.size();
    }

    class ProductEvaluationViewHolder extends BaseViewHolder {
        @BindView(R.id.evaluation_userhead)
        CircleImageView evaluationUserhead;
        @BindView(R.id.evaluation_username)
        TextView evaluationUsername;
        @BindView(R.id.evaluate_level)
        TextView evaluateLevel;
        @BindView(R.id.evaluate_date)
        TextView evaluateDate;
        @BindView(R.id.evaluate_desc)
        TextView evaluateDesc;
        @BindView(R.id.recycler_view)
        RecyclerView recyclerView;

        PersonalEvaluationModel.DataBean bean;
        int position;
        ProductDetailsAdapter adapter;

        public ProductEvaluationViewHolder(View itemView) {
            super(itemView);
            recyclerView.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false));
            recyclerView.addItemDecoration(new SpaceItemDecoration(ScreenUtils.dp2px(mContext, 15)));
        }

        public void bindView(PersonalEvaluationModel.DataBean data, int pos) {
            bean = data;
            position = pos;
            getItemView().setOnClickListener(v -> {
                if (null != mOnItemClickListener) {
                    mOnItemClickListener.onItemClick(v, position);
                }
            });

            adapter = new ProductDetailsAdapter(mContext, bean.getPic(), 1);
            recyclerView.setAdapter(adapter);

            if (!TextUtils.isEmpty(bean.getUs_head_pic()))
                GlideApp.with(mContext)
                        .load(bean.getAnonymous() == 1 ? R.mipmap.ic_default_head
                                : bean.getUs_head_pic().startsWith("http:") || bean.getUs_head_pic().startsWith("https:")
                                ? bean.getUs_head_pic().replaceAll("\\\\", "/")
                                : ApiStores.API_SERVER_URL + bean.getUs_head_pic().replaceAll("\\\\", "/"))
                        .centerCrop()
                        .into(evaluationUserhead);
            evaluationUsername.setText(bean.getAnonymous() == 1 ? mContext.getString(R.string.product_evaluate_anonymous) : bean.getUs_nickname());
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
            evaluateDate.setText(bean.getAdd_time());
            evaluateDesc.setText(bean.getContent());
        }
    }
}
