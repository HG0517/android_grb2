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
import com.jgkj.grb.ui.mvp.FlashSalePageModel;
import com.jgkj.grb.ui.mvp.index.GoodBeanModel;

import java.util.List;

import butterknife.BindView;

/**
 * 限时抢购
 * Created by brightpoplar@163.com on 2019/8/5.
 */
public class FlashSaleAdapter extends RecyclerView.Adapter {
    private Context mContext;
    private List<FlashSalePageModel.DataBean> mList;
    private LayoutInflater mLayoutInflater;
    private OnItemActionListener mOnItemClickListener;
    private int mType;

    public void setOnItemClickListener(OnItemActionListener mOnItemClickListener) {
        this.mOnItemClickListener = mOnItemClickListener;
    }

    public FlashSaleAdapter(Context mContext, List<FlashSalePageModel.DataBean> mList, int type) {
        this.mContext = mContext;
        this.mList = mList;
        this.mLayoutInflater = LayoutInflater.from(mContext);
        this.mType = type;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new FlashSaleViewHolder(mLayoutInflater.inflate(R.layout.layout_item_flash_sale, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        if (viewHolder instanceof FlashSaleViewHolder) {
            ((FlashSaleViewHolder) viewHolder).bindView(mList.get(i), i);
        }
    }

    @Override
    public int getItemCount() {
        return null == mList ? 0 : mList.size();
    }

    class FlashSaleViewHolder extends BaseViewHolder {
        @BindView(R.id.shop_iv)
        ImageView shopIv;
        @BindView(R.id.shop_name)
        TextView shopName;
        @BindView(R.id.shop_accumulate_bg)
        ImageView shopAccumulateBg;
        @BindView(R.id.shop_accumulate_tv)
        TextView shopAccumulateTv;
        @BindView(R.id.total_top)
        TextView totalTop;
        @BindView(R.id.total_bottom)
        TextView totalBottom;
        @BindView(R.id.shop_action)
        ImageView shopAction;

        FlashSalePageModel.DataBean bean;
        int position;

        public FlashSaleViewHolder(View itemView) {
            super(itemView);
        }

        public void bindView(FlashSalePageModel.DataBean data, int pos) {
            bean = data;
            position = pos;
            getItemView().setOnClickListener(v -> {
                if (null != mOnItemClickListener) {
                    mOnItemClickListener.onItemClick(v, position);
                }
            });
            shopAction.setOnClickListener(v -> {
                if (null != mOnItemClickListener) {
                    mOnItemClickListener.onActionClick(v, position);
                }
            });
            if (!TextUtils.isEmpty(bean.getPd_head_pic()))
                GlideApp.with(mContext)
                        .load(bean.getPd_head_pic().startsWith("http:") || bean.getPd_head_pic().startsWith("https:")
                                ? bean.getPd_head_pic() : ApiStores.API_SERVER_URL + bean.getPd_head_pic())
                        .into(shopIv);
            shopName.setText(bean.getPd_name());
            if (mType == 0) {
                shopAccumulateBg.setVisibility(View.VISIBLE);
                shopAccumulateTv.setVisibility(View.VISIBLE);
                shopAccumulateTv.setText(String.valueOf(bean.getPd_sales()));
                GlideApp.with(mContext)
                        .load(R.mipmap.ic_flash_sale_jieshu)
                        .into(shopAction);
            } else if (mType == 1) {
                shopAccumulateBg.setVisibility(View.GONE);
                shopAccumulateTv.setVisibility(View.GONE);
                shopAccumulateTv.setText("");
                GlideApp.with(mContext)
                        .load(R.mipmap.ic_flash_sale_lijiqiang)
                        .into(shopAction);
            } else if (mType == 2) {
                shopAccumulateBg.setVisibility(View.GONE);
                shopAccumulateTv.setVisibility(View.GONE);
                shopAccumulateTv.setText("");
                GlideApp.with(mContext)
                        .load(R.mipmap.ic_flash_sale_tixingwo)
                        .into(shopAction);
            }

            totalTop.setText(String.format(mContext.getResources().getString(R.string.total_top_text), bean.getPd_total()));
            totalBottom.setText(getTotalBottom(bean));
        }

        private String getTotalBottom(FlashSalePageModel.DataBean model) {
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

    public interface OnItemActionListener extends OnItemClickListener {
        void onActionClick(View v, int position);
    }
}
