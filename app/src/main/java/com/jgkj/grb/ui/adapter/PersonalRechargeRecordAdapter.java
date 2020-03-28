package com.jgkj.grb.ui.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jgkj.grb.R;
import com.jgkj.grb.base.BaseViewHolder;
import com.jgkj.grb.ui.mvp.personal.PersonalRechargeRecordModel;

import java.util.List;

import butterknife.BindView;

/**
 * 个人中心：账户充值，金额选项
 * Created by brightpoplar@163.com on 2019/8/26.
 */
public class PersonalRechargeRecordAdapter extends RecyclerView.Adapter {

    private Context mContext;
    private List<PersonalRechargeRecordModel.DataBean> mList;
    private LayoutInflater mLayoutInflater;

    public PersonalRechargeRecordAdapter(Context mContext, List<PersonalRechargeRecordModel.DataBean> mList) {
        this.mContext = mContext;
        this.mList = mList;
        this.mLayoutInflater = LayoutInflater.from(mContext);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new PersonalRechargeRecordViewHolder(mLayoutInflater.inflate(R.layout.layout_item_personal_recharge_record, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        if (viewHolder instanceof PersonalRechargeRecordViewHolder) {
            ((PersonalRechargeRecordViewHolder) viewHolder).bindView(mList.get(i), i);
        }
    }

    @Override
    public int getItemCount() {
        return null == mList ? 0 : mList.size();
    }

    class PersonalRechargeRecordViewHolder extends BaseViewHolder {

        @BindView(R.id.recharge_record_time_0)
        TextView rechargeRecordTime0;
        @BindView(R.id.recharge_record_time_1)
        TextView rechargeRecordTime1;
        @BindView(R.id.recharge_record_money_0)
        TextView rechargeRecordMoney0;
        @BindView(R.id.recharge_record_money_1)
        TextView rechargeRecordMoney1;
        @BindView(R.id.recharge_record_money_2)
        TextView rechargeRecordMoney2;
        @BindView(R.id.recharge_record_status)
        TextView rechargeRecordStatus;

        PersonalRechargeRecordModel.DataBean bean;
        int position;

        public PersonalRechargeRecordViewHolder(View itemView) {
            super(itemView);
        }

        public void bindView(PersonalRechargeRecordModel.DataBean data, int pos) {
            bean = data;
            position = pos;
            getItemView().setOnClickListener(v -> {
            });

            if (!TextUtils.isEmpty(data.getAdd_time())) {
                String[] s = data.getAdd_time().split(" ");
                rechargeRecordTime0.setText(s[0]);
                rechargeRecordTime1.setText(s[1]);
            }
            rechargeRecordMoney0.setText(String.format(mContext.getString(R.string.total_top_text), String.valueOf(data.getRecharges())));
            rechargeRecordMoney1.setText(String.format(mContext.getString(R.string.total_top_text), data.getNum()));
            // rechargeRecordMoney2.setText(String.format("%sGRC", data.getGrc()));
            if (data.getStatus() == 0) {
                rechargeRecordStatus.setText(R.string.personal_recharge_record_status_0);
                rechargeRecordStatus.setTextColor(Color.parseColor("#F53C5E"));
            } else if (data.getStatus() == 1) {
                rechargeRecordStatus.setText(R.string.personal_recharge_record_status_1);
                rechargeRecordStatus.setTextColor(Color.parseColor("#666666"));
            }
        }
    }
}
