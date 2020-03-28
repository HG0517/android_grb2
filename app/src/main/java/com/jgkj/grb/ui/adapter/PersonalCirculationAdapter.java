package com.jgkj.grb.ui.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jgkj.grb.R;
import com.jgkj.grb.base.BaseViewHolder;
import com.jgkj.grb.onclick.OnItemClickListener;
import com.jgkj.grb.ui.mvp.personal.PersonalGRBCashModel;

import java.util.List;

import butterknife.BindView;

/**
 * 个人中心：GRB，明细
 * Created by brightpoplar@163.com on 2019/8/27..
 */
public class PersonalCirculationAdapter extends RecyclerView.Adapter {
    private Context mContext;
    private List<PersonalGRBCashModel.GRBCashBean> mList;
    private LayoutInflater mLayoutInflater;
    private OnItemClickListener mOnItemClickListener;

    public void setOnItemClickListener(OnItemClickListener mOnItemClickListener) {
        this.mOnItemClickListener = mOnItemClickListener;
    }

    public PersonalCirculationAdapter(Context mContext, List<PersonalGRBCashModel.GRBCashBean> mList) {
        this.mContext = mContext;
        this.mList = mList;
        this.mLayoutInflater = LayoutInflater.from(mContext);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new CirculationViewHolder(mLayoutInflater.inflate(R.layout.layout_item_personal_circulation, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        if (viewHolder instanceof CirculationViewHolder) {
            ((CirculationViewHolder) viewHolder).bindView(mList.get(i), i);
        }
    }

    @Override
    public int getItemCount() {
        return null == mList ? 0 : mList.size();
    }

    class CirculationViewHolder extends BaseViewHolder {
        @BindView(R.id.personal_circulation_name)
        TextView personalCirculationName;
        @BindView(R.id.personal_circulation_date)
        TextView personalCirculationDate;
        @BindView(R.id.personal_circulation_money)
        TextView personalCirculationMoney;

        PersonalGRBCashModel.GRBCashBean data;

        public CirculationViewHolder(View itemView) {
            super(itemView);
        }

        public void bindView(PersonalGRBCashModel.GRBCashBean bean, int position) {
            data = bean;
            getItemView().setOnClickListener(v -> {
                if (null != mOnItemClickListener) {
                    mOnItemClickListener.onItemClick(v, position);
                }
            });
            personalCirculationName.setText(data.getWa_note());
            personalCirculationDate.setText(data.getAdd_time());
            personalCirculationMoney.setText(String.format("%sGRB", data.getWa_num()));
        }
    }
}
