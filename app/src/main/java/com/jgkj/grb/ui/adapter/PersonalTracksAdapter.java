package com.jgkj.grb.ui.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jgkj.grb.R;
import com.jgkj.grb.base.BaseViewHolder;
import com.jgkj.grb.onclick.OnItemClickListener;
import com.jgkj.grb.ui.mvp.personal.PersonalTracksModel;

import java.util.List;

import butterknife.BindView;

/**
 * 我的足迹
 * Created by brightpoplar@163.com on 2019/8/21.
 */
public class PersonalTracksAdapter extends RecyclerView.Adapter {
    private Context mContext;
    private List<PersonalTracksModel.DataBean> mList;
    private LayoutInflater mLayoutInflater;
    private OnItemActionListener mOnItemActionListener;

    public void setOnItemActionListener(OnItemActionListener mOnItemActionListener) {
        this.mOnItemActionListener = mOnItemActionListener;
    }

    public PersonalTracksAdapter(Context mContext, List<PersonalTracksModel.DataBean> mList) {
        this.mContext = mContext;
        this.mList = mList;
        this.mLayoutInflater = LayoutInflater.from(mContext);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new PersonalTracksViewHolder(mLayoutInflater.inflate(R.layout.layout_item_personal_tracks, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        if (viewHolder instanceof PersonalTracksViewHolder) {
            ((PersonalTracksViewHolder) viewHolder).bindView(mList.get(i), i);
        }
    }

    @Override
    public int getItemCount() {
        return null == mList ? 0 : mList.size();
    }

    class PersonalTracksViewHolder extends BaseViewHolder {
        @BindView(R.id.tracks_time)
        TextView tracksTime;
        @BindView(R.id.recycler_view)
        RecyclerView recyclerView;
        PersonalTracksChildAdapter adapter;
        PersonalTracksModel.DataBean data;
        int position;

        public PersonalTracksViewHolder(View itemView) {
            super(itemView);
            recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        }

        public void bindView(PersonalTracksModel.DataBean bean, int pos) {
            data = bean;
            position = pos;
            getItemView().setOnClickListener(v -> {
            });

            tracksTime.setText(data.getTime());
            adapter = new PersonalTracksChildAdapter(mContext, data.getGoods());
            recyclerView.setAdapter(adapter);
            adapter.setOnItemClickListener((view, position) -> {
                if (null != mOnItemActionListener) {
                    mOnItemActionListener.onItemClick(view, pos, position);
                }
            });
        }
    }

    public interface OnItemActionListener extends OnItemClickListener {
        void onItemClick(View v, int postion, int posChild);
    }
}
