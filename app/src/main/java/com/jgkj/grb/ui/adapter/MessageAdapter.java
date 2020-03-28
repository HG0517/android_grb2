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
import com.jgkj.grb.ui.mvp.message.MessageModel;

import java.util.List;

import butterknife.BindView;

/**
 * 消息
 * Created by brightpoplar@163.com on 2019/8/20.
 */
public class MessageAdapter extends RecyclerView.Adapter {
    private Context mContext;
    private int mType = 1;
    private List<MessageModel.MessageBean> mList;
    private LayoutInflater mLayoutInflater;
    private OnItemClickListener mOnItemClickListener;

    public void setOnItemClickListener(OnItemClickListener mOnItemClickListener) {
        this.mOnItemClickListener = mOnItemClickListener;
    }

    public MessageAdapter(Context mContext, int type, List<MessageModel.MessageBean> mList) {
        this.mContext = mContext;
        this.mType = type;
        this.mList = mList;
        this.mLayoutInflater = LayoutInflater.from(mContext);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new MessageSystemViewHolder(mLayoutInflater.inflate(R.layout.layout_item_message, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        if (viewHolder instanceof MessageSystemViewHolder) {
            ((MessageSystemViewHolder) viewHolder).bindView(mList.get(i), i);
        }
    }

    @Override
    public int getItemCount() {
        return null == mList ? 0 : mList.size();
    }

    class MessageSystemViewHolder extends BaseViewHolder {
        @BindView(R.id.message_type_icon)
        ImageView messageTypeIcon;
        @BindView(R.id.message_type_title)
        TextView messageTypeTitle;
        @BindView(R.id.message_type_desc)
        TextView messageTypeDesc;
        @BindView(R.id.message_type_iv)
        ImageView messageTypeIv;
        @BindView(R.id.message_type_time)
        TextView messageTypeIime;

        MessageModel.MessageBean data;
        int position;

        public MessageSystemViewHolder(View itemView) {
            super(itemView);
        }

        public void bindView(MessageModel.MessageBean bean, int pos) {
            data = bean;
            position = pos;
            getItemView().setOnClickListener(v -> {
                if (null != mOnItemClickListener) {
                    mOnItemClickListener.onItemClick(v, position);
                }
            });
            if (mType == 1) {
                GlideApp.with(mContext)
                        .load(R.mipmap.ic_message_system_item)
                        .into(messageTypeIcon);
                messageTypeTitle.setText(R.string.message_management_systom);
            } else if (mType == 2) {
                GlideApp.with(mContext)
                        .load(R.mipmap.ic_message_mall_item)
                        .into(messageTypeIcon);
                messageTypeTitle.setText(R.string.message_management_mall);
            } else if (mType == 3) {
                GlideApp.with(mContext)
                        .load(R.mipmap.ic_message_proxy_item)
                        .into(messageTypeIcon);
                messageTypeTitle.setText(R.string.message_management_proxy);
            }
            if (!TextUtils.isEmpty(data.getPic()))
                GlideApp.with(mContext)
                        .load(data.getPic().startsWith("http:") || data.getPic().startsWith("https:")
                                ? data.getPic().replaceAll("\\\\", "/")
                                : ApiStores.API_SERVER_URL + data.getPic().replaceAll("\\\\", "/"))
                        .transform(new CenterCrop(), new RoundTransformation(mContext, 5))
                        .into(messageTypeIv);
            messageTypeDesc.setText(data.getTitle());
            messageTypeIime.setText(data.getAdd_time());
        }
    }

}
