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

import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.jgkj.grb.R;
import com.jgkj.grb.base.BaseViewHolder;
import com.jgkj.grb.glide.GlideApp;
import com.jgkj.grb.glide.RoundTransformation;
import com.jgkj.grb.onclick.OnItemClickListener;
import com.jgkj.grb.retrofit.ApiStores;
import com.jgkj.grb.ui.mvp.find.FindPageModel;

import java.util.List;

import butterknife.BindView;

/**
 * 发现
 * Created by brightpoplar@163.com on 2019/8/16.
 */
public class FragmentFindAdapter extends RecyclerView.Adapter {
    private Context mContext;
    private List<FindPageModel.DataBean> mList;
    private LayoutInflater mLayoutInflater;
    private OnItemClickListener mOnItemClickListener;

    public void setOnItemClickListener(OnItemClickListener mOnItemClickListener) {
        this.mOnItemClickListener = mOnItemClickListener;
    }

    public FragmentFindAdapter(Context mContext, List<FindPageModel.DataBean> mList) {
        this.mContext = mContext;
        this.mList = mList;
        this.mLayoutInflater = LayoutInflater.from(mContext);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new FragmentFindViewHolder(mLayoutInflater.inflate(R.layout.layout_item_fragment_find, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        if (viewHolder instanceof FragmentFindViewHolder) {
            ((FragmentFindViewHolder) viewHolder).bindView(mList.get(i), i);
        }
    }

    @Override
    public int getItemCount() {
        return null == mList ? 0 : mList.size();
    }

    class FragmentFindViewHolder extends BaseViewHolder {
        @BindView(R.id.find_iv)
        ImageView findIv;
        @BindView(R.id.find_tv)
        TextView findTv;
        FindPageModel.DataBean bean;
        int position;

        public FragmentFindViewHolder(View itemView) {
            super(itemView);
        }

        public void bindView(FindPageModel.DataBean data, int pos) {
            bean = data;
            position = pos;
            getItemView().setOnClickListener(v -> {
                if (null != mOnItemClickListener) {
                    mOnItemClickListener.onItemClick(v, position);
                }
            });
            findTv.setText(bean.getTitle());
            if (!TextUtils.isEmpty(bean.getPic())) {
                GlideApp.with(mContext)
                        .load(bean.getPic().startsWith("http:") || bean.getPic().startsWith("https:")
                                ? bean.getPic().replaceAll("\\\\", "/") : ApiStores.API_SERVER_URL + bean.getPic().replaceAll("\\\\", "/"))
                        .transform(new CenterCrop(), new RoundTransformation(mContext, 5))
                        .into(findIv);
            } else {
                findIv.setImageDrawable(new ColorDrawable(Color.WHITE));
            }
        }
    }
}
