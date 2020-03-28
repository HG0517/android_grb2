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
 * 个人中心：GRA，明细
 * Created by brightpoplar@163.com on 2019/10/22.
 */
public class PersonalGraAdapter extends RecyclerView.Adapter {
    private Context mContext;
    private List<PersonalGRBCashModel.GRBCashBean> mList;
    private LayoutInflater mLayoutInflater;
    private OnItemClickListener mOnItemClickListener;

    public void setOnItemClickListener(OnItemClickListener mOnItemClickListener) {
        this.mOnItemClickListener = mOnItemClickListener;
    }

    public PersonalGraAdapter(Context mContext, List<PersonalGRBCashModel.GRBCashBean> mList) {
        this.mContext = mContext;
        this.mList = mList;
        this.mLayoutInflater = LayoutInflater.from(mContext);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new GraViewHolder(mLayoutInflater.inflate(R.layout.layout_item_personal_gra, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        if (viewHolder instanceof GraViewHolder) {
            ((GraViewHolder) viewHolder).bindView(mList.get(i), i);
        }
    }

    @Override
    public int getItemCount() {
        return null == mList ? 0 : mList.size();
    }

    class GraViewHolder extends BaseViewHolder {
        @BindView(R.id.personal_circulation_name)
        TextView personalCirculationName;
        @BindView(R.id.personal_circulation_date)
        TextView personalCirculationDate;
        @BindView(R.id.personal_circulation_money)
        TextView personalCirculationMoney;

        PersonalGRBCashModel.GRBCashBean data;

        public GraViewHolder(View itemView) {
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
            personalCirculationMoney.setText(String.format("%sGRA", data.getWa_num()));
        }
    }
}
