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

import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.jgkj.grb.R;
import com.jgkj.grb.base.BaseViewHolder;
import com.jgkj.grb.glide.GlideApp;
import com.jgkj.grb.glide.RoundTransformation;
import com.jgkj.grb.onclick.OnItemClickListener;
import com.jgkj.grb.retrofit.ApiStores;
import com.jgkj.grb.ui.mvp.index.GoodBeanModel;
import com.jgkj.grb.ui.mvp.personal.PersonalTracksModel;

import java.util.List;

import butterknife.BindView;

/**
 * 我的足迹
 * Created by brightpoplar@163.com on 2019/8/21.
 */
public class PersonalTracksChildAdapter extends RecyclerView.Adapter {
    private Context mContext;
    private List<PersonalTracksModel.DataBean.GoodsBean> mList;
    private LayoutInflater mLayoutInflater;
    private OnItemClickListener mOnItemClickListener;

    public void setOnItemClickListener(OnItemClickListener mOnItemClickListener) {
        this.mOnItemClickListener = mOnItemClickListener;
    }

    public PersonalTracksChildAdapter(Context mContext, List<PersonalTracksModel.DataBean.GoodsBean> mList) {
        this.mContext = mContext;
        this.mList = mList;
        this.mLayoutInflater = LayoutInflater.from(mContext);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new PersonalTracksChildViewHolder(mLayoutInflater.inflate(R.layout.layout_item_personal_tracks_child, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        if (viewHolder instanceof PersonalTracksChildViewHolder) {
            ((PersonalTracksChildViewHolder) viewHolder).bindView(mList.get(i), i);
        }
    }

    @Override
    public int getItemCount() {
        return null == mList ? 0 : mList.size();
    }

    class PersonalTracksChildViewHolder extends BaseViewHolder {
        @BindView(R.id.shop_iv)
        ImageView shopIv;
        @BindView(R.id.shop_name)
        TextView shopName;
        @BindView(R.id.total_top)
        TextView totalTop;
        @BindView(R.id.total_bottom)
        TextView totalBottom;

        PersonalTracksModel.DataBean.GoodsBean data;
        int position;

        public PersonalTracksChildViewHolder(View itemView) {
            super(itemView);
        }

        public void bindView(PersonalTracksModel.DataBean.GoodsBean bean, int pos) {
            data = bean;
            position = pos;
            getItemView().setOnClickListener(v -> {
                if (null != mOnItemClickListener) {
                    mOnItemClickListener.onItemClick(v, position);
                }
            });
            if (!TextUtils.isEmpty(data.getPd_head_pic()))
                GlideApp.with(mContext)
                        .load(data.getPd_head_pic().startsWith("http:") || data.getPd_head_pic().startsWith("https:")
                                ? data.getPd_head_pic().replaceAll("\\\\", "/")
                                : ApiStores.API_SERVER_URL + data.getPd_head_pic().replaceAll("\\\\", "/"))
                        .transform(new CenterCrop(), new RoundTransformation(mContext, 5))
                        .into(shopIv);
            shopName.setText(data.getPd_name());
            totalTop.setText(String.format(mContext.getResources().getString(R.string.total_top_text), data.getPd_total()));
            totalBottom.setText(getTotalBottom(data));
        }

        private String getTotalBottom(PersonalTracksModel.DataBean.GoodsBean model) {
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
