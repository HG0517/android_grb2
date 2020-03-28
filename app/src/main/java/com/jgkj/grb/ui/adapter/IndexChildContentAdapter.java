package com.jgkj.grb.ui.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.jgkj.grb.R;
import com.jgkj.grb.base.BaseViewHolder;
import com.jgkj.grb.glide.GlideApp;
import com.jgkj.grb.onclick.OnItemClickListener;
import com.jgkj.grb.retrofit.ApiStores;
import com.jgkj.grb.ui.mvp.index.GoodBeanModel;
import com.jgkj.grb.ui.mvp.index.IndexCatesChildPageModel;
import com.jgkj.grb.utils.ScreenUtils;

import java.util.List;

import butterknife.BindView;

/**
 * 首页：pager，数据列表
 * Created by brightpoplar@163.com on 2019/8/15.
 */
public class IndexChildContentAdapter extends RecyclerView.Adapter {
    private Context mContext;
    private List<IndexCatesChildPageModel.DataBean> mList;
    private LayoutInflater mLayoutInflater;
    private OnItemClickListener mOnItemClickListener;

    public void setOnItemClickListener(OnItemClickListener mOnItemClickListener) {
        this.mOnItemClickListener = mOnItemClickListener;
    }

    public IndexChildContentAdapter(Context mContext, List<IndexCatesChildPageModel.DataBean> mList) {
        this.mContext = mContext;
        this.mList = mList;
        this.mLayoutInflater = LayoutInflater.from(mContext);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new IndexChildContentViewHolder(mLayoutInflater.inflate(R.layout.layout_item_index_child_content, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        if (viewHolder instanceof IndexChildContentViewHolder) {
            ((IndexChildContentViewHolder) viewHolder).bindView(mList.get(i), i);
        }
    }

    @Override
    public int getItemCount() {
        return null == mList ? 0 : mList.size();
    }

    class IndexChildContentViewHolder extends BaseViewHolder {
        @BindView(R.id.shop_iv)
        ImageView mShopIv;
        @BindView(R.id.shop_name)
        TextView mShopName;
        @BindView(R.id.total_bottom)
        TextView mShopTotalBottom;
        @BindView(R.id.total_top)
        TextView mShopTotalTop;
        IndexCatesChildPageModel.DataBean bean;
        int position;

        public IndexChildContentViewHolder(View itemView) {
            super(itemView);
            int imgSize = (ScreenUtils.getScreenWidth((Activity) mContext) - ScreenUtils.dp2px(mContext, 15 * 2 + 7)) / 2;
            ConstraintLayout.LayoutParams layoutParams = new ConstraintLayout.LayoutParams(imgSize, imgSize);
            mShopIv.setLayoutParams(layoutParams);
        }

        public void bindView(IndexCatesChildPageModel.DataBean data, int pos) {
            bean = data;
            position = pos;
            getItemView().setOnClickListener(v -> {
                if (null != mOnItemClickListener) {
                    mOnItemClickListener.onItemClick(v, position);
                }
            });
            GlideApp.with(mContext)
                    .load(ApiStores.API_SERVER_URL+ bean.getPd_head_pic())
                    .into(mShopIv);
            mShopName.setText(bean.getPd_name());
            mShopTotalTop.setText(String.format(mContext.getResources().getString(R.string.total_top_text), bean.getPd_total()));
            mShopTotalBottom.setText(getTotalBottom(bean));
        }

        private String getTotalBottom(IndexCatesChildPageModel.DataBean model) {
            // "paytype2": 是否有grc支付；
            // "paytype1": 是否有grb支付；,
            // "paytype": 是否有现金支付,
            String payType = "(可抵扣:";
            /*if (model.getPaytype() == 1) {
                payType += model.getPd_price() + "现金";
            }*/
            if (model.getPaytype1() == 1) {
                payType += /*"\n" + */model.getPd_grb() + "份GRB";
            }
            if (model.getPaytype2() == 1) {
                payType += (model.getPaytype1() == 1 ? "\n" : "") + model.getPd_grc() + "份GRC";
            }
            return payType + ")";
        }
    }
}
