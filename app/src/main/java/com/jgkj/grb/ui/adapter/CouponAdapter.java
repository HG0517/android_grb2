package com.jgkj.grb.ui.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.jgkj.grb.R;
import com.jgkj.grb.base.BaseViewHolder;
import com.jgkj.grb.onclick.OnItemClickListener;
import com.jgkj.grb.ui.mvp.product.ProductDetailsModel;

import java.util.List;

import butterknife.BindView;

/**
 * 购物车：领券
 * Created by brightpoplar@163.com on 2019/7/31.
 */
public class CouponAdapter extends RecyclerView.Adapter {
    private Context mContext;
    private List<ProductDetailsModel.DataBean.CouponBean> mList;
    private LayoutInflater mLayoutInflater;
    private OnItemClickListener mOnItemClickListener;

    public void setOnItemClickListener(OnItemClickListener mOnItemClickListener) {
        this.mOnItemClickListener = mOnItemClickListener;
    }

    public CouponAdapter(Context mContext, List<ProductDetailsModel.DataBean.CouponBean> mList) {
        this.mContext = mContext;
        this.mList = mList;
        this.mLayoutInflater = LayoutInflater.from(mContext);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new SecuritiesViewHolder(mLayoutInflater.inflate(R.layout.layout_item_shop_cart_coupon, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        if (viewHolder instanceof SecuritiesViewHolder) {
            ((SecuritiesViewHolder) viewHolder).bindView(mList.get(i), i);
        }
    }

    @Override
    public int getItemCount() {
        return null == mList ? 0 : mList.size();
    }

    class SecuritiesViewHolder extends BaseViewHolder {
        @BindView(R.id.coupon_bg)
        ImageView mCouponBg;
        @BindView(R.id.coupon_tip)
        ImageView mCouponTip;
        @BindView(R.id.coupon_price)
        TextView mCouponPrice;
        @BindView(R.id.coupon_desc)
        TextView mCouponDesc;
        @BindView(R.id.coupon_name)
        TextView mCouponName;
        @BindView(R.id.coupon_date)
        TextView mCouponDate;
        ProductDetailsModel.DataBean.CouponBean data;

        public SecuritiesViewHolder(View itemView) {
            super(itemView);
        }

        public void bindView(ProductDetailsModel.DataBean.CouponBean bean, int position) {
            data = bean;
            getItemView().setOnClickListener(v -> {
                if (null != mOnItemClickListener) {
                    mOnItemClickListener.onItemClick(v, position);
                }
            });
            mCouponBg.setImageResource(data.isSecurity() ? R.mipmap.ic_shop_cart_securitied : R.mipmap.ic_shop_cart_coupon_normal);
            mCouponTip.setVisibility(data.isSecurity() ? View.GONE : View.VISIBLE);

            mCouponPrice.setText(String.format(mContext.getString(R.string.total_top_text), String.valueOf(data.getBouns())));
            mCouponDesc.setText(String.format(mContext.getString(R.string.personal_coupon_desc), String.valueOf(data.getPrice())));
            mCouponDate.setText(String.format(mContext.getString(R.string.personal_coupon_date), data.getStart(), data.getEnd()));
        }
    }
}
