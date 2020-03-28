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
import com.jgkj.grb.ui.mvp.ConventionCentreModel;

import java.util.List;

import butterknife.BindView;

/**
 * 会议中心：会议内容
 * Created by brightpoplar@163.com on 2019/8/8.
 */
public class CentreContentsAdapter extends RecyclerView.Adapter {
    private Context mContext;
    private List<ConventionCentreModel.DataBean.DetailBean> mList;
    private LayoutInflater mLayoutInflater;

    public CentreContentsAdapter(Context mContext, List<ConventionCentreModel.DataBean.DetailBean> mList) {
        this.mContext = mContext;
        this.mList = mList;
        this.mLayoutInflater = LayoutInflater.from(mContext);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new CentreContentViewHolder(mLayoutInflater.inflate(R.layout.layout_item_centre_content, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        if (viewHolder instanceof CentreContentViewHolder) {
            ((CentreContentViewHolder) viewHolder).bindView(mList.get(i), i);
        }
    }

    @Override
    public int getItemCount() {
        return null == mList ? 0 : mList.size();
    }

    class CentreContentViewHolder extends BaseViewHolder {
        @BindView(R.id.centre_content_time)
        TextView centreContentTime;
        @BindView(R.id.centre_content_iv)
        ImageView centreContentIv;
        @BindView(R.id.centre_content_title)
        TextView centreContentTitle;
        @BindView(R.id.centre_content_desc)
        TextView centreContentDesc;

        ConventionCentreModel.DataBean.DetailBean bean;
        int position;

        public CentreContentViewHolder(View itemView) {
            super(itemView);
        }

        public void bindView(ConventionCentreModel.DataBean.DetailBean data, int pos) {
            bean = data;
            position = pos;
            getItemView().setOnClickListener(v -> {

            });

            centreContentTime.setText(bean.getTime());
            if (!TextUtils.isEmpty(bean.getPic()))
                GlideApp.with(mContext)
                        .load(bean.getPic().startsWith("http:") || bean.getPic().startsWith("https:")
                                ? bean.getPic().replaceAll("\\\\", "/")
                                : ApiStores.API_SERVER_URL + bean.getPic().replaceAll("\\\\", "/"))
                        .centerCrop()
                        .into(centreContentIv);
            centreContentTitle.setText(bean.getTitle());
            centreContentDesc.setText(bean.getContent());
        }
    }
}
