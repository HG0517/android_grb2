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
import com.jgkj.grb.ui.mvp.personal.PersonalIncomeRecordModel;

import java.util.List;

import butterknife.BindView;

/**
 * 个人中心：GRB，收益明细：农场玩家，收益记录
 * Created by brightpoplar@163.com on 2019/8/27..
 */
public class PersonalIncomeRecordAdapter extends RecyclerView.Adapter {
    private Context mContext;
    private String mName;
    private List<PersonalIncomeRecordModel.DataBean> mList;
    private LayoutInflater mLayoutInflater;
    private OnItemClickListener mOnItemClickListener;

    public void setOnItemClickListener(OnItemClickListener mOnItemClickListener) {
        this.mOnItemClickListener = mOnItemClickListener;
    }

    public PersonalIncomeRecordAdapter(Context mContext, List<PersonalIncomeRecordModel.DataBean> mList, String name) {
        this.mContext = mContext;
        this.mName = name;
        this.mList = mList;
        this.mLayoutInflater = LayoutInflater.from(mContext);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new IncomeRecordViewHolder(mLayoutInflater.inflate(R.layout.layout_item_personal_income_record, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        if (viewHolder instanceof IncomeRecordViewHolder) {
            ((IncomeRecordViewHolder) viewHolder).bindView(mList.get(i), i);
        }
    }

    @Override
    public int getItemCount() {
        return null == mList ? 0 : mList.size();
    }

    class IncomeRecordViewHolder extends BaseViewHolder {
        @BindView(R.id.income_record_name)
        TextView incomeRecordName;
        @BindView(R.id.income_record_date)
        TextView incomeRecordDate;
        @BindView(R.id.income_record_money)
        TextView incomeRecordMoney;

        PersonalIncomeRecordModel.DataBean data;

        public IncomeRecordViewHolder(View itemView) {
            super(itemView);
        }

        public void bindView(PersonalIncomeRecordModel.DataBean bean, int position) {
            data = bean;
            getItemView().setOnClickListener(v -> {
                if (null != mOnItemClickListener) {
                    mOnItemClickListener.onItemClick(v, position);
                }
            });
            incomeRecordName.setText(String.format(mContext.getString(R.string.personal_income_record_tip), mName));
            incomeRecordMoney.setText(String.format("+%s", data.getWa_num()));
            incomeRecordDate.setText(data.getAdd_time());
        }
    }
}
