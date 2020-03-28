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
import com.jgkj.grb.ui.bean.CentreRecordModel;
import com.jgkj.grb.view.datepicker.DateFormatUtils;

import java.util.List;

import butterknife.BindView;

/**
 * 会议中心：报名记录
 * Created by brightpoplar@163.com on 2019/10/22.
 */
public class ConventionCentreRecordAdapter extends RecyclerView.Adapter {
    private Context mContext;
    private List<CentreRecordModel.DataBean> mList;
    private LayoutInflater mLayoutInflater;
    private OnItemClickListener mOnItemClickListener;

    public void setOnItemClickListener(OnItemClickListener mOnItemClickListener) {
        this.mOnItemClickListener = mOnItemClickListener;
    }

    public ConventionCentreRecordAdapter(Context mContext, List<CentreRecordModel.DataBean> mList) {
        this.mContext = mContext;
        this.mList = mList;
        this.mLayoutInflater = LayoutInflater.from(mContext);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ConventionCentreRecordViewHolder(mLayoutInflater.inflate(R.layout.layout_item_centre_record, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        if (viewHolder instanceof ConventionCentreRecordViewHolder) {
            ((ConventionCentreRecordViewHolder) viewHolder).bindView(mList.get(i), i);
        }
    }

    @Override
    public int getItemCount() {
        return null == mList ? 0 : mList.size();
    }

    class ConventionCentreRecordViewHolder extends BaseViewHolder {

        @BindView(R.id.record_number)
        TextView recordNumber;
        @BindView(R.id.record_status)
        TextView recordStatus;
        @BindView(R.id.record_time)
        TextView recordTime;
        @BindView(R.id.record_time1)
        TextView recordTime1;
        @BindView(R.id.record_addr)
        TextView recordAddr;
        @BindView(R.id.record_tel)
        TextView recordTel;

        CentreRecordModel.DataBean data;
        int position;

        public ConventionCentreRecordViewHolder(View itemView) {
            super(itemView);
        }

        public void bindView(CentreRecordModel.DataBean bean, int pos) {
            data = bean;
            position = pos;
            getItemView().setOnClickListener(v -> {
            });
            recordNumber.setText(String.format("入场编号-%s", data.getNum_id()));
            recordTime.setText(data.getAdd_time());
            recordTime1.setText(String.format("活动时间：%s", DateFormatUtils.secondToDate(data.getTime(), "yyyy年MM月dd日 HH:mm")));
            recordAddr.setText(String.format("地址：%s", data.getAddress()));
            recordTel.setText(String.format("电话：%s", data.getTel()));
            recordStatus.setText(data.getStatus() == 1 ? "报名成功" : "支付报名费");
            recordStatus.setOnClickListener(v -> {
                if (data.getStatus() == 0 && null != mOnItemClickListener) {
                    mOnItemClickListener.onItemClick(v, position);
                }
            });
        }
    }
}
