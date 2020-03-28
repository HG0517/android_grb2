package com.jgkj.grb.ui.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.jgkj.grb.R;
import com.jgkj.grb.base.BaseViewHolder;
import com.jgkj.grb.onclick.OnItemClickListener;
import com.jgkj.grb.ui.mvp.shopcart.CartIndexBean;

import java.util.List;

import butterknife.BindView;

/**
 * 购物车：子列表，失效商品
 * Created by brightpoplar@163.com on 2019/7/29.
 */
public class ShoppingCartInvalidAdapter extends RecyclerView.Adapter {

    private Context mContext;
    private List<CartIndexBean.CartBean> mList;
    private LayoutInflater mLayoutInflater;
    private OnItemClickListener mOnItemClickListener;

    public void setOnItemClickListener(OnItemClickListener mOnItemClickListener) {
        this.mOnItemClickListener = mOnItemClickListener;
    }

    public ShoppingCartInvalidAdapter(Context mContext, List<CartIndexBean.CartBean> mList) {
        this.mContext = mContext;
        this.mList = mList;
        this.mLayoutInflater = LayoutInflater.from(mContext);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        return new ShoppingCartChildInvalidViewHolder(mLayoutInflater.inflate(R.layout.layout_item_shop_cart_child_invalid, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {
        if (viewHolder instanceof ShoppingCartChildInvalidViewHolder) {
            if (null != mOnItemClickListener) {
                viewHolder.itemView.setOnClickListener(v -> {
                    mOnItemClickListener.onItemClick(v, position);
                });
            }
        }
    }

    @Override
    public int getItemCount() {
        return null == mList ? 0 : mList.size();
    }

    class ShoppingCartChildInvalidViewHolder extends BaseViewHolder {
        @BindView(R.id.cart_shop_invalid)
        ImageView mCartShopInvalid;
        @BindView(R.id.cart_shop_iv)
        ImageView mCartShopIv;
        @BindView(R.id.cart_shop_name)
        TextView mCartShopName;
        @BindView(R.id.cart_shop_invalid_desc)
        CardView mCartShopInvalid_desc;
        @BindView(R.id.cart_shop_invalid_tv)
        TextView mCartShopInvalidTv;

        public ShoppingCartChildInvalidViewHolder(View itemView) {
            super(itemView);
        }
    }

}
