package com.jgkj.grb.ui.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
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
import com.jgkj.grb.ui.mvp.ConventionCentreModel;

import java.util.List;

import butterknife.BindView;

/**
 * 会议中心：活动商品
 * Created by brightpoplar@163.com on 2019/8/8.
 */
public class CentreActiveGoodsAdapter extends RecyclerView.Adapter {
    private Context mContext;
    private List<ConventionCentreModel.DataBean.GoodsBean> mList;
    private LayoutInflater mLayoutInflater;

    private OnItemClickListener mOnItemClickListener;

    public void setOnItemClickListener(OnItemClickListener mOnItemClickListener) {
        this.mOnItemClickListener = mOnItemClickListener;
    }

    public CentreActiveGoodsAdapter(Context mContext, List<ConventionCentreModel.DataBean.GoodsBean> mList) {
        this.mContext = mContext;
        this.mList = mList;
        this.mLayoutInflater = LayoutInflater.from(mContext);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new CentreActiveGoodsViewHolder(mLayoutInflater.inflate(R.layout.layout_item_centre_active_good, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        if (viewHolder instanceof CentreActiveGoodsViewHolder) {
            ((CentreActiveGoodsViewHolder) viewHolder).bindView(mList.get(i), i);
        }
    }

    @Override
    public int getItemCount() {
        return null == mList ? 0 : mList.size();
    }

    class CentreActiveGoodsViewHolder extends BaseViewHolder {
        @BindView(R.id.shop_iv)
        ImageView shopIv;
        @BindView(R.id.shop_name)
        TextView shopName;
        @BindView(R.id.total_top)
        TextView totalTop;
        @BindView(R.id.total_bottom)
        TextView totalBottom;
        @BindView(R.id.buy_now)
        ImageView buyNow;

        ConventionCentreModel.DataBean.GoodsBean bean;
        int position;

        public CentreActiveGoodsViewHolder(View itemView) {
            super(itemView);
        }

        public void bindView(ConventionCentreModel.DataBean.GoodsBean data, int pos) {
            bean = data;
            position = pos;
            getItemView().setOnClickListener(v -> {
                if (null != mOnItemClickListener) {
                    mOnItemClickListener.onItemClick(v, pos);
                }
            });
            buyNow.setOnClickListener(v -> {
                if (null != mOnItemClickListener) {
                    mOnItemClickListener.onItemClick(v, pos);
                }
            });

            if (!TextUtils.isEmpty(bean.getPd_head_pic()))
                GlideApp.with(mContext)
                        .load(bean.getPd_head_pic().startsWith("http:") || bean.getPd_head_pic().startsWith("https:")
                                ? bean.getPd_head_pic().replaceAll("\\\\", "/")
                                : ApiStores.API_SERVER_URL + bean.getPd_head_pic().replaceAll("\\\\", "/"))
                        .centerCrop()
                        .into(shopIv);
            shopName.setText(bean.getPd_name());
            totalTop.setText(String.format(mContext.getResources().getString(R.string.total_top_text), String.valueOf(bean.getPrice())));
            totalBottom.setText(String.format(mContext.getResources().getString(R.string.total_bottom_text), String.valueOf(bean.getGrb())));
        }

        private String getTotalBottom(ConventionCentreModel.DataBean.GoodsBean model) {
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
