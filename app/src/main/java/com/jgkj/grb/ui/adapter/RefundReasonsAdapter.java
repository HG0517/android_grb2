package com.jgkj.grb.ui.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.jgkj.grb.R;
import com.jgkj.grb.base.BaseViewHolder;
import com.jgkj.grb.onclick.OnItemClickListener;
import com.jgkj.grb.ui.bean.RefundReasonsBean;

import java.util.List;

import butterknife.BindView;

/**
 * 申请退款：退款原因
 * Created by brightpoplar@163.com on 2019/8/22.
 */
public class RefundReasonsAdapter extends RecyclerView.Adapter {

    private Context mContext;
    private List<RefundReasonsBean> mList;
    private LayoutInflater mLayoutInflater;
    private OnItemClickListener mOnItemClickListener;

    public void setOnItemClickListener(OnItemClickListener mOnItemClickListener) {
        this.mOnItemClickListener = mOnItemClickListener;
    }

    public RefundReasonsAdapter(Context mContext, List<RefundReasonsBean> mList) {
        this.mContext = mContext;
        this.mList = mList;
        this.mLayoutInflater = LayoutInflater.from(mContext);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new RefundReasonViewHolder(mLayoutInflater.inflate(R.layout.layout_item_refund_reason, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        if (viewHolder instanceof RefundReasonViewHolder) {
            ((RefundReasonViewHolder) viewHolder).bindView(mList.get(i), i);
        }
    }

    @Override
    public int getItemCount() {
        return null == mList ? 0 : mList.size();
    }

    class RefundReasonViewHolder extends BaseViewHolder {

        @BindView(R.id.refund_reason_tv)
        TextView refundReasonTv;
        @BindView(R.id.refund_reason_iv)
        CheckBox refundReasonIv;

        RefundReasonsBean bean;
        int position;

        public RefundReasonViewHolder(View itemView) {
            super(itemView);
        }

        public void bindView(RefundReasonsBean data, int pos) {
            bean = data;
            position = pos;
            getItemView().setOnClickListener(v -> {
                if (null != mOnItemClickListener) {
                    mOnItemClickListener.onItemClick(v, position);
                }
            });
            refundReasonTv.setText(bean.getReasons());
            refundReasonIv.setChecked(bean.isSelect());
        }
    }
}
