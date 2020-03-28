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
import com.jgkj.grb.ui.mvp.index.IndexCatesBean;

import java.util.List;

import butterknife.BindView;

/**
 * 首页：pager，顶部小分类
 * Created by brightpoplar@163.com on 2019/8/15.
 */
public class IndexChildTopAdapter extends RecyclerView.Adapter {
    private Context mContext;
    private List<IndexCatesBean> mList;
    private LayoutInflater mLayoutInflater;
    private OnItemClickListener mOnItemClickListener;

    public void setOnItemClickListener(OnItemClickListener mOnItemClickListener) {
        this.mOnItemClickListener = mOnItemClickListener;
    }

    public IndexChildTopAdapter(Context mContext, List<IndexCatesBean> mList) {
        this.mContext = mContext;
        this.mList = mList;
        this.mLayoutInflater = LayoutInflater.from(mContext);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new IndexChildTopViewHolder(mLayoutInflater.inflate(R.layout.layout_item_index_child_top, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        if (viewHolder instanceof IndexChildTopViewHolder) {
            ((IndexChildTopViewHolder) viewHolder).bindView(mList.get(i), i);
        }
    }

    @Override
    public int getItemCount() {
        return null == mList ? 0 : mList.size();
    }

    class IndexChildTopViewHolder extends BaseViewHolder {
        @BindView(R.id.index_child_iv)
        ImageView mIndexChildIv;
        @BindView(R.id.index_child_tv)
        TextView mIndexChildTv;
        IndexCatesBean bean;
        int position;

        public IndexChildTopViewHolder(View itemView) {
            super(itemView);
        }

        public void bindView(IndexCatesBean data, int pos) {
            bean = data;
            position = pos;
            getItemView().setOnClickListener(v -> {
                if (null != mOnItemClickListener) {
                    mOnItemClickListener.onItemClick(v, position);
                }
            });
            mIndexChildTv.setSelected(bean.isSelect());
            if (!TextUtils.isEmpty(bean.getCa_pic()))
                GlideApp.with(mContext)
                        .load(bean.getCa_pic().startsWith("http:") || bean.getCa_pic().startsWith("https:")
                                ? bean.getCa_pic()
                                : ApiStores.API_SERVER_URL + bean.getCa_pic())
                        .into(mIndexChildIv);
            if (!TextUtils.isEmpty(bean.getCa_name())) {
                mIndexChildTv.setText(bean.getCa_name());
            }
        }
    }
}
