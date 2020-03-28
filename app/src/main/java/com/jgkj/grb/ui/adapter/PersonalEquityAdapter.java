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
 * 个人中心：GRB，GRC 明细
 * Created by brightpoplar@163.com on 2019/8/27..
 */
public class PersonalEquityAdapter extends RecyclerView.Adapter {
    private Context mContext;
    private List<PersonalGRBCashModel.GRBCashBean> mList;
    private LayoutInflater mLayoutInflater;
    private OnItemClickListener mOnItemClickListener;

    public void setOnItemClickListener(OnItemClickListener mOnItemClickListener) {
        this.mOnItemClickListener = mOnItemClickListener;
    }

    public PersonalEquityAdapter(Context mContext, List<PersonalGRBCashModel.GRBCashBean> mList) {
        this.mContext = mContext;
        this.mList = mList;
        this.mLayoutInflater = LayoutInflater.from(mContext);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new EquityViewHolder(mLayoutInflater.inflate(R.layout.layout_item_personal_equity, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        if (viewHolder instanceof EquityViewHolder) {
            ((EquityViewHolder) viewHolder).bindView(mList.get(i), i);
        }
    }

    @Override
    public int getItemCount() {
        return null == mList ? 0 : mList.size();
    }

    class EquityViewHolder extends BaseViewHolder {
        @BindView(R.id.personal_equity_name)
        TextView personalEquityName;
        @BindView(R.id.personal_equity_date)
        TextView personalEquityDate;
        @BindView(R.id.personal_equity_money)
        TextView personalEquityMoney;

        PersonalGRBCashModel.GRBCashBean data;

        public EquityViewHolder(View itemView) {
            super(itemView);
        }

        public void bindView(PersonalGRBCashModel.GRBCashBean bean, int position) {
            data = bean;
            getItemView().setOnClickListener(v -> {
                if (null != mOnItemClickListener) {
                    mOnItemClickListener.onItemClick(v, position);
                }
            });
            personalEquityName.setText(data.getWa_note());
            personalEquityDate.setText(data.getAdd_time());
            personalEquityMoney.setText(String.format("%sGRC", data.getWa_num()));
        }
    }
}
