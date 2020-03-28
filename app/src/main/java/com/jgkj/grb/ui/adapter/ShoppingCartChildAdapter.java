package com.jgkj.grb.ui.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.jgkj.grb.R;
import com.jgkj.grb.base.BaseViewHolder;
import com.jgkj.grb.glide.GlideApp;
import com.jgkj.grb.onclick.OnItemClickListener;
import com.jgkj.grb.onclick.RxView;
import com.jgkj.grb.retrofit.ApiStores;
import com.jgkj.grb.ui.mvp.ConventionCentreModel;
import com.jgkj.grb.ui.mvp.shopcart.CartIndexBean;
import com.jgkj.grb.utils.ToastHelper;

import java.util.List;

import butterknife.BindView;

/**
 * 购物车：子列表，正常商品
 * Created by brightpoplar@163.com on 2019/7/29.
 */
public class ShoppingCartChildAdapter extends RecyclerView.Adapter {

    private Context mContext;
    private List<CartIndexBean.CartBean> mList;
    private LayoutInflater mLayoutInflater;
    private OnItemOperationListener mOnItemOperationListener;

    public void setOnItemClickListener(OnItemOperationListener mOnItemClickListener) {
        this.mOnItemOperationListener = mOnItemClickListener;
    }

    public interface OnItemOperationListener extends OnItemClickListener {
        void onCheckedChange(int pos, int num, float price, float pv);

        void onNumCut(int pos, int num, float price, float pv);

        void onNumAdd(int pos, int num, float price, float pv);

        void onSpecs(int position);
    }

    public ShoppingCartChildAdapter(Context mContext, List<CartIndexBean.CartBean> mList) {
        this.mContext = mContext;
        this.mList = mList;
        this.mLayoutInflater = LayoutInflater.from(mContext);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        return new ShoppingCartChildViewHolder(mLayoutInflater.inflate(R.layout.layout_item_shop_cart_child, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {
        if (viewHolder instanceof ShoppingCartChildViewHolder) {
            ((ShoppingCartChildViewHolder) viewHolder).bindView(mList.get(position), position);
        }
    }

    @Override
    public int getItemCount() {
        return null == mList ? 0 : mList.size();
    }

    class ShoppingCartChildViewHolder extends BaseViewHolder implements RxView.Action1 {
        @BindView(R.id.cart_shop_cb)
        CheckBox mCartShopCb;
        @BindView(R.id.cart_shop_iv)
        ImageView mCartShopIv;
        @BindView(R.id.cart_shop_flash_sale)
        ImageView mCartShopFlashSale;
        @BindView(R.id.cart_shop_name)
        TextView mCartShopName;
        @BindView(R.id.cart_shop_select)
        CardView mCartShopSelect;
        @BindView(R.id.cart_shop_select_tv)
        TextView mCartShopSelectTv;
        @BindView(R.id.total_top)
        TextView mTotalTop;
        @BindView(R.id.total_bottom)
        TextView mTotalBottom;
        @BindView(R.id.total_num_cut)
        FrameLayout mTotalNumCut;
        @BindView(R.id.total_num_add)
        FrameLayout mTotalNumAdd;
        @BindView(R.id.total_num)
        TextView mTotalNum;
        CartIndexBean.CartBean data;
        int position;

        public ShoppingCartChildViewHolder(View itemView) {
            super(itemView);
        }

        public void bindView(CartIndexBean.CartBean bean, int pos) {
            data = bean;
            position = pos;
            if (null != mOnItemOperationListener) {
                getItemView().setOnClickListener(v -> {
                    mOnItemOperationListener.onItemClick(v, position);
                });
            }

            RxView.setOnClickListeners(this, mCartShopCb, mTotalNumCut, mTotalNumAdd, mCartShopSelect);

            mCartShopCb.setChecked(data.isSelect());
            if (!TextUtils.isEmpty(data.getPd_head_pic()))
                GlideApp.with(mContext)
                        .load(data.getPd_head_pic().startsWith("http:") || data.getPd_head_pic().startsWith("https:")
                                ? data.getPd_head_pic().replaceAll("\\\\", "/")
                                : ApiStores.API_SERVER_URL + data.getPd_head_pic().replaceAll("\\\\", "/"))
                        .into(mCartShopIv);
            // ca_id  32  限时抢购
            mCartShopFlashSale.setVisibility(View.GONE);
            mCartShopName.setText(data.getPd_name());
            mCartShopSelectTv.setText(data.getPd_spec());
            mTotalTop.setText(String.format(mContext.getResources().getString(R.string.total_top_text), data.getPd_total()));
            mTotalBottom.setText(getTotalBottom(data));
            mTotalNum.setText(String.valueOf(data.getPd_num()));
        }

        private String getTotalBottom(CartIndexBean.CartBean model) {
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

        @Override
        public void onClick(Object o) {
            View v = (View) o;
            switch (v.getId()) {
                case R.id.cart_shop_cb:
                    data.setSelect(mCartShopCb.isChecked());
                    if (null != mOnItemOperationListener) {
                        int num = data.getPd_num();
                        float price = data.getPaytype() == 2 ? data.getPd_num() * Float.parseFloat(/*data.getPd_price()*/"0") : 0; // num * price
                        float pv = data.getPd_num() * Float.parseFloat(/*data.getPd_pv()*/"0"); // num * pv
                        if (!data.isSelect()) {
                            num = -num;
                            price = -price;
                            pv = -pv;
                        }
                        mOnItemOperationListener.onCheckedChange(position, num, price, pv);
                    }
                    break;
                case R.id.total_num_cut:
                    if (data.getPd_num() > 1) {
                        if (null != mOnItemOperationListener) {
                            float price = data.getPaytype() == 2 ? -1 * Float.parseFloat(/*data.getPd_price()*/"0") : 0; // num * price
                            float pv = -1 * Float.parseFloat(/*data.getPd_pv()*/"0"); // num * pv
                            if (!data.isSelect()) {
                                price = 0;
                                pv = 0;
                            }
                            mOnItemOperationListener.onNumCut(position, -1, price, pv);
                        }
                    } else {
                        ToastHelper.showToast(mContext, R.string.select_num_cut_text, 1);
                    }
                    break;
                case R.id.total_num_add:
                    // 是否需要考虑库存
                    if (data.getPd_num() < data.getMax_num()) {
                        if (null != mOnItemOperationListener) {
                            float price = data.getPaytype() == 2 ? 1 * Float.parseFloat(/*data.getPd_price()*/"0") : 0; // num * price
                            float pv = 1 * Float.parseFloat(/*data.getPd_pv()*/"0"); // num * pv
                            if (!data.isSelect()) {
                                price = 0;
                                pv = 0;
                            }
                            mOnItemOperationListener.onNumAdd(position, 1, price, pv);
                        }
                    } else {
                        ToastHelper.showToast(mContext, R.string.select_num_add_text, 1);
                    }
                    break;
                case R.id.cart_shop_select:
                    if (null != mOnItemOperationListener) {
                        mOnItemOperationListener.onSpecs(position);
                    }
                    break;
                default:
                    break;
            }
        }
    }

}
