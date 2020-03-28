package com.jgkj.grb.ui.adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
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
import com.jgkj.grb.ui.mvp.CollectModel;
import com.jgkj.grb.ui.mvp.index.GoodBeanModel;

import java.util.List;

import butterknife.BindView;

/**
 * 我的收藏
 * Created by brightpoplar@163.com on 2019/8/24.
 */
public class PersonalCollectAdapter extends RecyclerView.Adapter {
    private Context mContext;
    private List<CollectModel.DataBean> mList;
    private LayoutInflater mLayoutInflater;
    private OnItemActionListener mOnItemActionListener;

    public void setOnItemActionListener(OnItemActionListener mOnItemActionListener) {
        this.mOnItemActionListener = mOnItemActionListener;
    }

    public PersonalCollectAdapter(Context mContext, List<CollectModel.DataBean> mList) {
        this.mContext = mContext;
        this.mList = mList;
        this.mLayoutInflater = LayoutInflater.from(mContext);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new PersonalCollectViewHolder(mLayoutInflater.inflate(R.layout.layout_item_personal_collect, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        if (viewHolder instanceof PersonalCollectViewHolder) {
            ((PersonalCollectViewHolder) viewHolder).bindView(mList.get(i), i);
        }
    }

    @Override
    public int getItemCount() {
        return null == mList ? 0 : mList.size();
    }

    class PersonalCollectViewHolder extends BaseViewHolder {
        @BindView(R.id.shop_iv)
        ImageView shopIv;
        @BindView(R.id.shop_name)
        TextView shopName;
        @BindView(R.id.shop_collect)
        TextView shopCollect;
        @BindView(R.id.total_top)
        TextView totalTop;
        @BindView(R.id.total_bottom)
        TextView totalBottom;
        @BindView(R.id.shop_action)
        ImageView shopAction;
        CollectModel.DataBean data;
        int position;

        public PersonalCollectViewHolder(View itemView) {
            super(itemView);
        }

        public void bindView(CollectModel.DataBean bean, int pos) {
            data = bean;
            position = pos;
            getItemView().setOnClickListener(v -> {
                if (null != mOnItemActionListener) {
                    mOnItemActionListener.onItemClick(v, position);
                }
            });
            shopAction.setOnClickListener(v -> {
                if (null != mOnItemActionListener) {
                    mOnItemActionListener.onActionClick(v, position);
                }
            });
            if (!TextUtils.isEmpty(data.getPd_head_pic())) {
                GlideApp.with(mContext)
                        .load(data.getPd_head_pic().startsWith("http:") || data.getPd_head_pic().startsWith("https:")
                                ? data.getPd_head_pic() : ApiStores.API_SERVER_URL + data.getPd_head_pic())
                        .into(shopIv);
            } else {
                shopIv.setImageDrawable(new ColorDrawable(Color.WHITE));
            }
            shopName.setText(data.getPd_name());
            shopCollect.setText(String.format(mContext.getString(R.string.personal_collect_num), data.getNum()));
            totalTop.setText(String.format(mContext.getResources().getString(R.string.total_top_text), data.getPd_total()));
            totalBottom.setText(getTotalBottom(data));
        }

        private String getTotalBottom(CollectModel.DataBean model) {
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
