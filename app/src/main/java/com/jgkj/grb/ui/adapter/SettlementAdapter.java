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
import com.jgkj.grb.retrofit.ApiStores;
import com.jgkj.grb.ui.mvp.shopcart.CartIndexBean;

import java.util.List;
import java.util.Locale;

import butterknife.BindView;

/**
 * 结算
 * Created by brightpoplar@163.com on 2019/8/2.
 */
public class SettlementAdapter extends RecyclerView.Adapter {
    private Context mContext;
    private List<CartIndexBean.CartBean> mList;
    private LayoutInflater mLayoutInflater;

    public SettlementAdapter(Context mContext, List<CartIndexBean.CartBean> mList) {
        this.mContext = mContext;
        this.mList = mList;
        this.mLayoutInflater = LayoutInflater.from(mContext);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new SettlementViewHolder(mLayoutInflater.inflate(R.layout.layout_item_settlement, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        if (viewHolder instanceof SettlementViewHolder) {
            ((SettlementViewHolder) viewHolder).bindView(mList.get(i), i);
        }
    }

    @Override
    public int getItemCount() {
        return null == mList ? 0 : mList.size();
    }

    class SettlementViewHolder extends BaseViewHolder {

        @BindView(R.id.cart_shop_iv)
        ImageView cartShopIv;
        @BindView(R.id.cart_shop_name)
        TextView cartShopName;
        @BindView(R.id.cart_shop_select_tv)
        TextView cartShopSelectTv;
        @BindView(R.id.total_num)
        TextView cartShopSelectNum;
        @BindView(R.id.total_top)
        TextView cartShopTotalTop;
        //@BindView(R.id.total_bottom)
        //TextView cartShopTotalBottom;

        CartIndexBean.CartBean data;
        int position;

        public SettlementViewHolder(View itemView) {
            super(itemView);
        }

        public void bindView(CartIndexBean.CartBean bean, int pos) {
            data = bean;
            position = pos;

            if (!TextUtils.isEmpty(data.getPd_head_pic()))
                GlideApp.with(mContext)
                        .load(data.getPd_head_pic().startsWith("http:") || data.getPd_head_pic().startsWith("https:")
                                ? data.getPd_head_pic().replaceAll("\\\\", "/")
                                : ApiStores.API_SERVER_URL + data.getPd_head_pic().replaceAll("\\\\", "/"))
                        .into(cartShopIv);
            cartShopName.setText(TextUtils.isEmpty(data.getPd_name()) ? data.getOr_pd_name() : data.getPd_name());
            cartShopSelectTv.setText(TextUtils.isEmpty(data.getPd_spec()) ? data.getPd_spec() : data.getPd_spec());
            cartShopSelectNum.setText(String.format(Locale.getDefault(), "X%d", data.getPd_num() == 0 ? data.getOr_pd_num() : data.getPd_num()));
            cartShopTotalTop.setText(String.format(mContext.getResources().getString(R.string.total_top_text), data.getType4() == 1 ? data.getOr_pd_price() : data.getOr_pd_total()));
            //cartShopTotalBottom.setText(String.format(mContext.getResources().getString(R.string.total_bottom_text), TextUtils.isEmpty(data.getPd_pv()) ? data.getOr_pd_pv() : data.getPd_pv()));
        }
    }
}
