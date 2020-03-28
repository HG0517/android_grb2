package com.jgkj.grb.ui.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.jgkj.grb.R;
import com.jgkj.grb.base.BaseViewHolder;
import com.jgkj.grb.onclick.OnItemClickListener;
import com.jgkj.grb.ui.activity.ChooseBankCardActivity;

import java.util.List;

import butterknife.BindView;

/**
 * Created by brightpoplar@163.com on 2019/8/14.
 */
public class ChooseBankCardAdapter extends RecyclerView.Adapter {
    private Context mContext;
    private List<ChooseBankCardActivity.BankCardBean> mList;
    private LayoutInflater mLayoutInflater;
    private OnItemClickListener mOnItemClickListener;

    public ChooseBankCardAdapter(Context mContext, List<ChooseBankCardActivity.BankCardBean> mList) {
        this.mContext = mContext;
        this.mList = mList;
        this.mLayoutInflater = LayoutInflater.from(mContext);
    }

    public void setOnItemClickListener(OnItemClickListener mOnItemClickListener) {
        this.mOnItemClickListener = mOnItemClickListener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ChooseBankCardViewHolder(mLayoutInflater.inflate(R.layout.layout_item_choose_bank_card, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        if (viewHolder instanceof ChooseBankCardViewHolder) {
            ((ChooseBankCardViewHolder) viewHolder).bindView(mList.get(i), i);
        }
    }

    @Override
    public int getItemCount() {
        return null == mList ? 0 : mList.size();
    }

    class ChooseBankCardViewHolder extends BaseViewHolder {
        @BindView(R.id.bank_card_nametype_iv)
        ImageView nametypeIv;
        @BindView(R.id.bank_card_nametype_tv)
        TextView nametypeTv;
        @BindView(R.id.bank_card_nametype_right)
        ImageView nametypeRight;
        ChooseBankCardActivity.BankCardBean bean;
        int position;

        public ChooseBankCardViewHolder(View itemView) {
            super(itemView);
        }

        public void bindView(ChooseBankCardActivity.BankCardBean data, int pos) {
            bean = data;
            position = pos;
            getItemView().setOnClickListener(v -> {
                if (null != mOnItemClickListener) {
                    mOnItemClickListener.onItemClick(v, position);
                }
            });
            nametypeRight.setVisibility(data.getChoose() == 1 ? View.VISIBLE : View.GONE);
        }
    }
}
