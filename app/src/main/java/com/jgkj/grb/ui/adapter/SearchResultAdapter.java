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
import com.jgkj.grb.ui.mvp.index.GoodBeanModel;
import com.jgkj.grb.ui.mvp.product.ProductDetailsModel;
import com.jgkj.grb.view.datepicker.DateFormatUtils;

import java.util.List;

import butterknife.BindView;

/**
 * 搜索数据展示
 * Created by brightpoplar@163.com on 2019/8/8.
 */
public class SearchResultAdapter extends RecyclerView.Adapter {
    private Context mContext;
    private List<GoodBeanModel> mList;
    private LayoutInflater mLayoutInflater;
    private OnItemClickListener mOnItemClickListener;
    private int mIndex;

    public void setOnItemClickListener(OnItemClickListener mOnItemClickListener) {
        this.mOnItemClickListener = mOnItemClickListener;
    }

    public SearchResultAdapter(Context mContext, List<GoodBeanModel> mList) {
        this.mContext = mContext;
        this.mList = mList;
        this.mLayoutInflater = LayoutInflater.from(mContext);
    }

    public SearchResultAdapter(Context mContext, List<GoodBeanModel> mList, int index) {
        this.mContext = mContext;
        this.mList = mList;
        this.mLayoutInflater = LayoutInflater.from(mContext);
        this.mIndex = index;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new CentreActiveGoodsViewHolder(mLayoutInflater.inflate(R.layout.layout_item_search_result, viewGroup, false));
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
        @BindView(R.id.shop_advance_sale)
        TextView shopAdvanceSale;
        @BindView(R.id.shop_name)
        TextView shopName;
        @BindView(R.id.total_top)
        TextView totalTop;
        @BindView(R.id.total_bottom)
        TextView totalBottom;
        @BindView(R.id.shop_action)
        ImageView shopAction;
        GoodBeanModel bean;
        int position;

        public CentreActiveGoodsViewHolder(View itemView) {
            super(itemView);
        }

//        pd_market_price  市场价格；pd_total 商品总价值；pd_price: 需现金支付金额；pd_grb:可抵扣grb数量；pd_grc:可抵扣grc数量
//        "paytype2": 是否有grc支付；
//        "paytype1": 是否有grb支付；,
//        "paytype": 是否有现金支付,
        public void bindView(GoodBeanModel data, int pos) {
            bean = data;
            position = pos;
            getItemView().setOnClickListener(v -> {
                if (null != mOnItemClickListener) {
                    mOnItemClickListener.onItemClick(v, position);
                }
            });
            if (!TextUtils.isEmpty(bean.getPd_head_pic()))
                GlideApp.with(mContext)
                        .load(bean.getPd_head_pic().startsWith("http:") || bean.getPd_head_pic().startsWith("https:")
                                ? bean.getPd_head_pic()
                                : ApiStores.API_SERVER_URL + bean.getPd_head_pic())
                        .centerCrop()
                        .into(shopIv);
            shopName.setText(bean.getPd_name());
            totalTop.setText(String.format(mContext.getResources().getString(R.string.total_top_text), bean.getPd_total()));
            totalBottom.setText(getTotalBottom(bean));

            if (mIndex == 0) {
                shopAdvanceSale.setVisibility(View.GONE);
                GlideApp.with(mContext)
                        .load(R.mipmap.ic_convention_center_buy_now)
                        .into(shopAction);
            } else {
                shopAdvanceSale.setVisibility(View.VISIBLE);
                long l = DateFormatUtils.str2Long(bean.getAdvance(), false);
                String s = DateFormatUtils.long2Str(l, mContext.getString(R.string.activity_title_advance_sale_start));
                shopAdvanceSale.setText(s);
                GlideApp.with(mContext)
                        .load(R.mipmap.ic_flash_sale_tixingwo)
                        .into(shopAction);
            }
        }

        private String getTotalBottom(GoodBeanModel model) {
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
