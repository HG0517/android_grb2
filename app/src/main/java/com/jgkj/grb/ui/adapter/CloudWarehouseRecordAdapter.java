package com.jgkj.grb.ui.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.jgkj.grb.R;
import com.jgkj.grb.base.BaseViewHolder;
import com.jgkj.grb.glide.GlideApp;
import com.jgkj.grb.glide.RoundTransformation;
import com.jgkj.grb.retrofit.ApiStores;
import com.jgkj.grb.ui.mvp.cloud.CloudWarehouseRecordModel;

import java.util.List;
import java.util.Locale;

import butterknife.BindView;

/**
 * 云仓库：提货记录
 * Created by brightpoplar@163.com on 2019/8/20.
 */
public class CloudWarehouseRecordAdapter extends RecyclerView.Adapter {
    private Context mContext;
    private List<CloudWarehouseRecordModel.DataBean> mList;
    private LayoutInflater mLayoutInflater;

    public CloudWarehouseRecordAdapter(Context mContext, List<CloudWarehouseRecordModel.DataBean> mList) {
        this.mContext = mContext;
        this.mList = mList;
        this.mLayoutInflater = LayoutInflater.from(mContext);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new CloudWarehouseRecordViewHolder(mLayoutInflater.inflate(R.layout.layout_item_cloud_warehouse_record, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        if (viewHolder instanceof CloudWarehouseRecordViewHolder) {
            ((CloudWarehouseRecordViewHolder) viewHolder).bindView(mList.get(i), i);
        }
    }

    @Override
    public int getItemCount() {
        return null == mList ? 0 : mList.size();
    }

    class CloudWarehouseRecordViewHolder extends BaseViewHolder {
        @BindView(R.id.cloud_order_num)
        TextView cloudOrderNum;
        @BindView(R.id.cloud_order_status_tv)
        TextView cloudOrderStatusTv;
        @BindView(R.id.cloud_order_iv)
        ImageView cloudOrderIv;
        @BindView(R.id.cloud_order_name)
        TextView cloudOrderName;
        @BindView(R.id.cloud_order_specs)
        TextView cloudOrderSpecs;
        @BindView(R.id.total_top)
        TextView cloudOrderTotalTop;
        //@BindView(R.id.total_bottom)
        //TextView cloudOrderTotalBottom;
        @BindView(R.id.cloud_order_number)
        TextView cloudOrderNumber;

        CloudWarehouseRecordModel.DataBean data;
        int position;

        public CloudWarehouseRecordViewHolder(View itemView) {
            super(itemView);
        }

        public void bindView(CloudWarehouseRecordModel.DataBean bean, int pos) {
            data = bean;
            position = pos;
            getItemView().setOnClickListener(v -> {
            });
            cloudOrderNum.setText(String.format(mContext.getString(R.string.order_view_num), data.getOr_num()));

            CloudWarehouseRecordModel.DataBean.DetailBean detailBean = data.getDetail().get(0);
            if (!TextUtils.isEmpty(detailBean.getPd_head_pic()))
                GlideApp.with(mContext)
                        .load(detailBean.getPd_head_pic().startsWith("htts:") || detailBean.getPd_head_pic().startsWith("https:")
                                ? detailBean.getPd_head_pic().replaceAll("\\\\", "/")
                                : ApiStores.API_SERVER_URL + detailBean.getPd_head_pic().replaceAll("\\\\", "/"))
                        .transform(new CenterCrop(), new RoundTransformation(mContext, 5))
                        .into(cloudOrderIv);
            cloudOrderName.setText(detailBean.getOr_pd_name());
            cloudOrderSpecs.setText(detailBean.getPd_spec());
            cloudOrderTotalTop.setText(String.format(mContext.getString(R.string.total_top_text), detailBean.getOr_pd_total()));
            //cloudOrderTotalBottom.setText(String.format(mContext.getString(R.string.total_bottom_text), detailBean.getOr_pd_pv()));
            cloudOrderNumber.setText(String.format(Locale.getDefault(), mContext.getString(R.string.cloud_warehouse_record_num), detailBean.getOr_pd_num()));
        }
    }
}
