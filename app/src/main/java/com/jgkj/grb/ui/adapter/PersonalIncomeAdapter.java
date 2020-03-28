package com.jgkj.grb.ui.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jgkj.grb.R;
import com.jgkj.grb.base.BaseViewHolder;
import com.jgkj.grb.onclick.OnItemClickListener;
import com.jgkj.grb.ui.mvp.personal.PersonalIncomeModel;

import java.util.List;

import butterknife.BindView;

/**
 * 个人中心：GRB，收益明细：主界面，农场玩家
 * Created by brightpoplar@163.com on 2019/8/27.
 */
public class PersonalIncomeAdapter extends RecyclerView.Adapter {
    private Context mContext;
    private List<PersonalIncomeModel.DataBean> mList;
    private LayoutInflater mLayoutInflater;
    private OnItemClickListener mOnItemClickListener;

    public void setOnItemClickListener(OnItemClickListener mOnItemClickListener) {
        this.mOnItemClickListener = mOnItemClickListener;
    }

    public PersonalIncomeAdapter(Context mContext, List<PersonalIncomeModel.DataBean> mList) {
        this.mContext = mContext;
        this.mList = mList;
        this.mLayoutInflater = LayoutInflater.from(mContext);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new IncomeViewHolder(mLayoutInflater.inflate(R.layout.layout_item_personal_income, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        if (viewHolder instanceof IncomeViewHolder) {
            ((IncomeViewHolder) viewHolder).bindView(mList.get(i), i);
        }
    }

    @Override
    public int getItemCount() {
        return null == mList ? 0 : mList.size();
    }

    class IncomeViewHolder extends BaseViewHolder {

        @BindView(R.id.personal_income_type)
        TextView personalIncomeType;
        @BindView(R.id.personal_income_record)
        CardView personalIncomeRecord;
        @BindView(R.id.personal_income_record_tv)
        TextView personalIncomeRecordTv;

        @BindView(R.id.personal_income_details)
        ConstraintLayout personalIncomeDetails;
        @BindView(R.id.personal_income_today)
        TextView personalIncomeToday;
        @BindView(R.id.personal_income_left_total)
        TextView personalIncomeLeftTotal;
        @BindView(R.id.personal_income_total)
        TextView personalIncomeTotal;

        @BindView(R.id.personal_income_unopened)
        ConstraintLayout personalIncomeUnopened;
        @BindView(R.id.personal_income_unopened_tip)
        TextView personalIncomeUnopenedTip;

        PersonalIncomeModel.DataBean data;
        int position;

        public IncomeViewHolder(View itemView) {
            super(itemView);
        }

        public void bindView(PersonalIncomeModel.DataBean bean, int pos) {
            data = bean;
            position = pos;
            personalIncomeRecord.setOnClickListener(v -> {
                if (null != mOnItemClickListener) {
                    mOnItemClickListener.onItemClick(v, position);
                }
            });

            personalIncomeType.setText(bean.getName());
            personalIncomeLeftTotal.setText(String.format(mContext.getString(R.string.personal_income_left_total_tip), bean.getName()));
            //personalIncomeDetails.setVisibility(View.VISIBLE);
            //personalIncomeUnopened.setVisibility(View.GONE);
            personalIncomeToday.setText(String.format("%sGRB", String.valueOf(bean.getToday())));
            personalIncomeTotal.setText(String.format("%sGRB", String.valueOf(bean.getTotal())));
            switch (bean.getType()) {
                case 1:
                    personalIncomeRecord.setCardBackgroundColor(Color.parseColor("#FFE4E4"));
                    personalIncomeRecordTv.setTextColor(Color.parseColor("#FB355C"));
                    break;
                case 2:
                    personalIncomeRecord.setCardBackgroundColor(Color.parseColor("#E7F8FF"));
                    personalIncomeRecordTv.setTextColor(Color.parseColor("#74B9E2"));
                    break;
                case 3:
                    personalIncomeRecord.setCardBackgroundColor(Color.parseColor("#FFF4E6"));
                    personalIncomeRecordTv.setTextColor(Color.parseColor("#F49137"));
                    break;
                case 4:
                    personalIncomeRecord.setCardBackgroundColor(Color.parseColor("#E7FFF3"));
                    personalIncomeRecordTv.setTextColor(Color.parseColor("#83CCBD"));
                    break;
                case 5:
                    personalIncomeRecord.setCardBackgroundColor(Color.parseColor("#E7EAFF"));
                    personalIncomeRecordTv.setTextColor(Color.parseColor("#808EF3"));
                    break;
                default:
                    break;
            }
        }
    }
}
