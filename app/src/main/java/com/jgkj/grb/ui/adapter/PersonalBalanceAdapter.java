package com.jgkj.grb.ui.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jgkj.grb.R;
import com.jgkj.grb.base.BaseViewHolder;
import com.jgkj.grb.ui.mvp.personal.PersonalGRBCashModel;

import java.util.List;

import butterknife.BindView;

/**
 * 个人中心：账户余额，账单明细
 * Created by brightpoplar@163.com on 2019/8/26.
 */
public class PersonalBalanceAdapter extends RecyclerView.Adapter {
    private Context mContext;
    private List<PersonalGRBCashModel.GRBCashBean> mList;
    private LayoutInflater mLayoutInflater;

    public PersonalBalanceAdapter(Context mContext, List<PersonalGRBCashModel.GRBCashBean> mList) {
        this.mContext = mContext;
        this.mList = mList;
        this.mLayoutInflater = LayoutInflater.from(mContext);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new PersonalBalanceViewHolder(mLayoutInflater.inflate(R.layout.layout_item_personal_balance, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        if (viewHolder instanceof PersonalBalanceViewHolder) {
            ((PersonalBalanceViewHolder) viewHolder).bindView(mList.get(i), i);
        }
    }

    @Override
    public int getItemCount() {
        return null == mList ? 0 : mList.size();
    }

    class PersonalBalanceViewHolder extends BaseViewHolder {
        @BindView(R.id.personal_balance_name)
        TextView personalBalanceName;
        @BindView(R.id.personal_balance_date)
        TextView personalBalanceDate;
        @BindView(R.id.personal_balance_number)
        TextView personalBalanceNumber;

        PersonalGRBCashModel.GRBCashBean bean;
        int position;

        public PersonalBalanceViewHolder(View itemView) {
            super(itemView);
        }

        public void bindView(PersonalGRBCashModel.GRBCashBean data, int pos) {
            bean = data;
            position = pos;
            getItemView().setOnClickListener(v -> {
            });
            personalBalanceName.setText(data.getWa_note());
            personalBalanceDate.setText(data.getAdd_time());
            if (data.getStatus() == 1) {
                personalBalanceNumber.setText(String.format("+%s", data.getWa_num()));
                personalBalanceNumber.setTextColor(Color.parseColor("#333333"));
            } else {
                personalBalanceNumber.setText(String.format("-%s", data.getWa_num()));
                personalBalanceNumber.setTextColor(Color.parseColor("#F53A81"));
            }
        }
    }
}
