package com.jgkj.grb.ui.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jgkj.grb.R;
import com.jgkj.grb.base.BaseViewHolder;
import com.jgkj.grb.onclick.OnItemClickListener;
import com.jgkj.grb.ui.activity.PersonalRechargeActivity;

import java.util.List;

import butterknife.BindView;

/**
 * 个人中心：账户充值，金额选项
 * Created by brightpoplar@163.com on 2019/8/26.
 */
public class PersonalRechargeAdapter extends RecyclerView.Adapter {

    private Context mContext;
    private List<PersonalRechargeActivity.PersonalRechargeBean> mList;
    private LayoutInflater mLayoutInflater;
    private OnItemClickListener mOnItemClickListener;

    public void setOnItemClickListener(OnItemClickListener mOnItemClickListener) {
        this.mOnItemClickListener = mOnItemClickListener;
    }

    public PersonalRechargeAdapter(Context mContext, List<PersonalRechargeActivity.PersonalRechargeBean> mList) {
        this.mContext = mContext;
        this.mList = mList;
        this.mLayoutInflater = LayoutInflater.from(mContext);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new PersonalRechargeViewHolder(mLayoutInflater.inflate(R.layout.layout_item_personal_recharge_select, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        if (viewHolder instanceof PersonalRechargeViewHolder) {
            ((PersonalRechargeViewHolder) viewHolder).bindView(mList.get(i), i);
        }
    }

    @Override
    public int getItemCount() {
        return null == mList ? 0 : mList.size();
    }

    class PersonalRechargeViewHolder extends BaseViewHolder {

        @BindView(R.id.recharge_select_ll)
        LinearLayout rechargeSelectLl;
        @BindView(R.id.recharge_select_top)
        TextView rechargeSelectTop;
        // @BindView(R.id.recharge_select_bottom)
        // TextView rechargeSelectBottom;

        PersonalRechargeActivity.PersonalRechargeBean bean;
        int position;

        public PersonalRechargeViewHolder(View itemView) {
            super(itemView);
        }

        public void bindView(PersonalRechargeActivity.PersonalRechargeBean data, int pos) {
            bean = data;
            position = pos;
            getItemView().setOnClickListener(v -> {
                if (null != mOnItemClickListener) {
                    mOnItemClickListener.onItemClick(v, position);
                }
            });
            rechargeSelectLl.setSelected(bean.isSelect());
            rechargeSelectTop.setText(bean.getMoneyString());
            //rechargeSelectBottom.setText(bean.getMoneyRatio());
            if (bean.isSelect()) {
                rechargeSelectTop.setTextColor(Color.WHITE);
                // rechargeSelectBottom.setTextColor(Color.WHITE);
            } else {
                rechargeSelectTop.setTextColor(Color.parseColor("#333333"));
                // rechargeSelectBottom.setTextColor(Color.parseColor("#F9567E"));
            }
        }
    }
}
