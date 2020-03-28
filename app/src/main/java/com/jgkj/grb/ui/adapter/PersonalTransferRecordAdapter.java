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
import com.jgkj.grb.ui.mvp.personal.PersonalTransferRecordModel;

import java.util.List;

import butterknife.BindView;

/**
 * 个人中心：GRB，转让记录
 * Created by brightpoplar@163.com on 2019/8/27..
 */
public class PersonalTransferRecordAdapter extends RecyclerView.Adapter {
    private Context mContext;
    private List<PersonalTransferRecordModel.DataBean.ListBean> mList;
    private LayoutInflater mLayoutInflater;
    private OnItemClickListener mOnItemClickListener;

    public void setOnItemClickListener(OnItemClickListener mOnItemClickListener) {
        this.mOnItemClickListener = mOnItemClickListener;
    }

    public PersonalTransferRecordAdapter(Context mContext, List<PersonalTransferRecordModel.DataBean.ListBean> mList) {
        this.mContext = mContext;
        this.mList = mList;
        this.mLayoutInflater = LayoutInflater.from(mContext);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new TransferRecordViewHolder(mLayoutInflater.inflate(R.layout.layout_item_personal_transfer_record, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        if (viewHolder instanceof TransferRecordViewHolder) {
            ((TransferRecordViewHolder) viewHolder).bindView(mList.get(i), i);
        }
    }

    @Override
    public int getItemCount() {
        return null == mList ? 0 : mList.size();
    }

    class TransferRecordViewHolder extends BaseViewHolder {
        @BindView(R.id.transfer_record_name)
        TextView transferRecordName;
        @BindView(R.id.transfer_record_date)
        TextView transferRecordDate;
        @BindView(R.id.transfer_record_money)
        TextView transferRecordMoney;
        @BindView(R.id.transfer_record_status)
        TextView transferRecordStatus;

        PersonalTransferRecordModel.DataBean.ListBean data;

        public TransferRecordViewHolder(View itemView) {
            super(itemView);
        }

        public void bindView(PersonalTransferRecordModel.DataBean.ListBean bean, int position) {
            data = bean;
            getItemView().setOnClickListener(v -> {
                if (null != mOnItemClickListener) {
                    mOnItemClickListener.onItemClick(v, position);
                }
            });
            transferRecordName.setText(String.format("%s-%s", data.getNote(), data.getName()));
            transferRecordDate.setText(data.getAdd_time());
            if (data.getType() == 1) {
                transferRecordMoney.setText(String.format(getFormat1(data.getType2()), data.getNum()));
            } else {
                transferRecordMoney.setText(String.format("兑换份数：%sGRB\n手续费：%sGRA\n实际到账：%sGRA", data.getNum(), data.getDeduct2(), data.getGra()));
            }
            transferRecordStatus.setText(data.getText());
        }

        private String getFormat1(int type2) {
            // 1：GRC 2：GRB 3：现金  4 GRA
            switch (type2) {
                case 1:
                    return "%sGRC";
                case 0:
                case 2:
                    return "%sGRB";
                case 3:
                    return "%s现金";
                case 4:
                    return "%sGRA";
            }
            return "%s";
        }
    }
}
