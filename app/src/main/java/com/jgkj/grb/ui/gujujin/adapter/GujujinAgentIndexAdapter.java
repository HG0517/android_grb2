package com.jgkj.grb.ui.gujujin.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
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
import com.jgkj.grb.ui.gujujin.bean.GujujinAgentBean;

import java.util.List;

import butterknife.BindView;

/**
 * 谷聚金代理主页
 * Created by brightpoplar@163.com on 2019/9/25.
 */
public class GujujinAgentIndexAdapter extends RecyclerView.Adapter {
    private Context mContext;
    private List<GujujinAgentBean> mList;
    private LayoutInflater mLayoutInflater;
    private OnItemClickListener mOnItemClickListener;

    public void setOnItemClickListener(OnItemClickListener mOnItemClickListener) {
        this.mOnItemClickListener = mOnItemClickListener;
    }

    public GujujinAgentIndexAdapter(Context mContext, List<GujujinAgentBean> mList) {
        this.mContext = mContext;
        this.mList = mList;
        this.mLayoutInflater = LayoutInflater.from(mContext);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        if (i == 0)
            return new GujujinAgentIndexViewHolder(mLayoutInflater.inflate(R.layout.layout_item_gujujin_agent_index_0, viewGroup, false));
        else if (i == 1)
            return new GujujinAgentIndexViewHolder(mLayoutInflater.inflate(R.layout.layout_item_gujujin_agent_index_1, viewGroup, false));
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        if (viewHolder instanceof GujujinAgentIndexViewHolder) {
            ((GujujinAgentIndexViewHolder) viewHolder).bindView(mList.get(i), i);
        }
    }

    @Override
    public int getItemCount() {
        return null == mList ? 0 : mList.size();
    }

    @Override
    public int getItemViewType(int position) {
        // 0:大小，1:小大
        return position % 2 == 0 ? 0 : 1;
    }

    class GujujinAgentIndexViewHolder extends BaseViewHolder {

        @BindView(R.id.imageview)
        ImageView imageview;
        @BindView(R.id.daili_type_name)
        TextView dailiTypeName;
        @BindView(R.id.daili_type_desc)
        TextView dailiTypeDesc;
        @BindView(R.id.daili_type_action)
        ImageView dailiTypeAction;

        GujujinAgentBean bean;
        int position;

        public GujujinAgentIndexViewHolder(View itemView) {
            super(itemView);
        }

        public void bindView(GujujinAgentBean data, int pos) {
            bean = data;
            position = pos;
            getItemView().setOnClickListener(v -> {
                if (null != mOnItemClickListener) {
                    mOnItemClickListener.onItemClick(v, position);
                }
            });

            GlideApp.with(mContext)
                    .load(data.getPicRes())
                    .transform(new CenterCrop(), new RoundTransformation(mContext, 5))
                    .into(imageview);
            dailiTypeName.setText(data.getName());
            dailiTypeDesc.setText(data.getDesc());
            int dailiTypeActionRes = 0;
            switch (data.getType()) {
                case 0:
                    dailiTypeActionRes = R.mipmap.ic_gujvjin_daili_action_0;
                    break;
                case 1:
                    dailiTypeActionRes = R.mipmap.ic_gujvjin_daili_action_1;
                    break;
                case 2:
                    dailiTypeActionRes = R.mipmap.ic_gujvjin_daili_action_2;
                    break;
                case 4:
                    dailiTypeActionRes = R.mipmap.ic_gujvjin_daili_action_4;
                    break;
                case 3:
                case 5:
                    dailiTypeActionRes = R.mipmap.ic_gujvjin_daili_action_3;
                    break;
                default:
                    break;
            }
            GlideApp.with(mContext)
                    .load(dailiTypeActionRes)
                    .into(dailiTypeAction);
        }
    }
}
