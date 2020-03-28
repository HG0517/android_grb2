package com.jgkj.grb.ui.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.jgkj.grb.R;
import com.jgkj.grb.base.BaseViewHolder;
import com.jgkj.grb.glide.GlideApp;
import com.jgkj.grb.onclick.OnItemClickListener;
import com.jgkj.grb.onclick.RxView;
import com.jgkj.grb.retrofit.ApiStores;
import com.jgkj.grb.ui.mvp.OrderManagementModel;

import java.util.List;
import java.util.Locale;

import butterknife.BindView;

/**
 * 云仓库
 * type   ：1 商城订单，2 云仓库订单，3 抢购订单，6 云仓库，7 退款/售后订单
 * status ：0 待付款，3 待发货，4 待收货，5 待评价
 * Created by brightpoplar@163.com on 2019/9/10.
 */
public class OrderManagementChildAdapter extends RecyclerView.Adapter {

    private Context mContext;
    private List<OrderManagementModel.DataBean.DetailBean> mList;
    private LayoutInflater mLayoutInflater;
    private OnItemActionListener mOnItemActionListener;
    private int mType = -1;
    private int mOrStatus = -1;

    public void setOnItemActionListener(OnItemActionListener mOnItemActionListener) {
        this.mOnItemActionListener = mOnItemActionListener;
    }

    public OrderManagementChildAdapter(Context mContext, List<OrderManagementModel.DataBean.DetailBean> mList, int type, int orStatus) {
        this.mContext = mContext;
        this.mList = mList;
        this.mLayoutInflater = LayoutInflater.from(mContext);
        this.mType = type;
        this.mOrStatus = orStatus;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new OrderManagementChildViewHolder(mLayoutInflater.inflate(R.layout.layout_item_order_management_child, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        if (viewHolder instanceof OrderManagementChildViewHolder) {
            ((OrderManagementChildViewHolder) viewHolder).bindView(mList.get(i), i);
        }
    }

    @Override
    public int getItemCount() {
        return null == mList ? 0 : mList.size();
    }

    class OrderManagementChildViewHolder extends BaseViewHolder implements RxView.Action1 {
        @BindView(R.id.cloud_order_iv)
        ImageView cloudOrderIv;
        @BindView(R.id.cloud_order_name)
        TextView cloudOrderName;
        @BindView(R.id.cloud_order_specs)
        TextView cloudOrderSpecs;
        @BindView(R.id.cloud_order_number)
        TextView cloudOrderNumber;
        @BindView(R.id.total_top)
        TextView cloudOrderTotalTop;
        //@BindView(R.id.total_bottom)
        //TextView cloudOrderTotalBottom;
        @BindView(R.id.cloud_order_rush_tip)
        ImageView cloudOrderRushTip;
        @BindView(R.id.cloud_order_action_red)
        FrameLayout cloudOrderActionRed;
        @BindView(R.id.cloud_order_action_red_tv)
        TextView cloudOrderActionRedTv;
        @BindView(R.id.cloud_order_action_gray)
        FrameLayout cloudOrderActionGray;
        @BindView(R.id.cloud_order_action_gray_tv)
        TextView cloudOrderActionGrayTv;

        OrderManagementModel.DataBean.DetailBean data;
        int position;

        public OrderManagementChildViewHolder(View itemView) {
            super(itemView);

            RxView.setOnClickListeners(this, cloudOrderActionRed, cloudOrderActionGray);
        }

        public void bindView(OrderManagementModel.DataBean.DetailBean bean, int pos) {
            data = bean;
            position = pos;

            cloudOrderRushTip.setVisibility(mType == 3 ? View.VISIBLE : View.GONE);
            if (mType == 1 || mType == 2 || mType == 3) {
                if (mOrStatus == 3) {
                    cloudOrderActionGrayTv.setText(R.string.order_management_apply_refund);
                    cloudOrderActionGray.setVisibility(mType == 1 ? View.VISIBLE : View.GONE);
                    cloudOrderActionRed.setVisibility(View.GONE);
                } else if (mOrStatus == 5) {
                    cloudOrderActionGrayTv.setText(R.string.order_management_apply_after_sale);
                    cloudOrderActionGray.setVisibility(mType == 1 ? View.VISIBLE : View.GONE);
                    cloudOrderActionRedTv.setText(R.string.order_management_evaluation);
                    cloudOrderActionRed.setVisibility(View.VISIBLE);
                }

                if (!TextUtils.isEmpty(data.getPd_head_pic()))
                    GlideApp.with(mContext)
                            .load(data.getPd_head_pic().startsWith("http:") || data.getPd_head_pic().startsWith("https:")
                                    ? data.getPd_head_pic().replaceAll("\\\\", "/")
                                    : ApiStores.API_SERVER_URL + data.getPd_head_pic().replaceAll("\\\\", "/"))
                            .centerCrop()
                            .into(cloudOrderIv);
                cloudOrderName.setText(data.getOr_pd_name());
                cloudOrderSpecs.setText(data.getPd_spec());
                cloudOrderNumber.setText(String.format(Locale.getDefault(), "X %d", data.getOr_pd_num()));
                cloudOrderTotalTop.setText(String.format(mContext.getResources().getString(R.string.total_top_text), data.getOr_pd_total()));
                //cloudOrderTotalBottom.setText(String.format(mContext.getResources().getString(R.string.total_bottom_text), data.getOr_pd_pv()));
            } else if (mType == 7) {
                if (null != data.getOr_pd_pic() && data.getOr_pd_pic().size() > 0) {
                    if (!TextUtils.isEmpty(data.getOr_pd_pic().get(0))) {
                        GlideApp.with(mContext)
                                .load(data.getOr_pd_pic().get(0).startsWith("http:") || data.getOr_pd_pic().get(0).startsWith("https:")
                                        ? data.getOr_pd_pic().get(0).replaceAll("\\\\", "/")
                                        : ApiStores.API_SERVER_URL + data.getOr_pd_pic().get(0).replaceAll("\\\\", "/"))
                                .centerCrop()
                                .into(cloudOrderIv);
                    }
                }
                cloudOrderName.setText(data.getOr_pd_name());
                cloudOrderSpecs.setText(data.getPd_spec());
                cloudOrderNumber.setText(String.format(Locale.getDefault(), "X %d", data.getOr_pd_num()));
                cloudOrderTotalTop.setText(String.format(mContext.getResources().getString(R.string.total_top_text), data.getOr_pd_total()));
                //cloudOrderTotalBottom.setText(String.format(mContext.getResources().getString(R.string.total_bottom_text), data.getOr_pd_pv()));

                cloudOrderActionRed.setVisibility(View.GONE);
                cloudOrderActionGray.setVisibility(View.GONE);
            } else if (mType == 6) {
                if (null != data.getOr_pd_pic() && data.getOr_pd_pic().size() > 0) {
                    if (!TextUtils.isEmpty(data.getOr_pd_pic().get(0))) {
                        GlideApp.with(mContext)
                                .load(data.getOr_pd_pic().get(0).startsWith("http:") || data.getOr_pd_pic().get(0).startsWith("https:")
                                        ? data.getOr_pd_pic().get(0).replaceAll("\\\\", "/")
                                        : ApiStores.API_SERVER_URL + data.getOr_pd_pic().get(0).replaceAll("\\\\", "/"))
                                .centerCrop()
                                .into(cloudOrderIv);
                    }
                }
                cloudOrderName.setText(data.getOr_pd_name());
                cloudOrderSpecs.setText(data.getPd_spec());
                cloudOrderNumber.setText(String.format(Locale.getDefault(), "X %d", data.getOr_pd_num()));
                cloudOrderTotalTop.setText(String.format(mContext.getResources().getString(R.string.total_top_text), data.getOr_pd_total()));
                //cloudOrderTotalBottom.setText(String.format(mContext.getResources().getString(R.string.total_bottom_text), data.getOr_pd_pv()));
            } else {
                cloudOrderActionRed.setVisibility(View.GONE);
                cloudOrderActionGray.setVisibility(View.GONE);
            }

            getItemView().setOnClickListener(v -> {
                if (null != mOnItemActionListener) {
                    mOnItemActionListener.onItemClick(v, pos);
                }
            });
        }

        @Override
        public void onClick(Object o) {
            View v = (View) o;
            switch (v.getId()) {
                case R.id.cloud_order_action_red:
                    if (null != mOnItemActionListener) {
                        if ((mType == 1 || mType == 2 || mType == 3) && mOrStatus == 5) {
                            mOnItemActionListener.onImmediateEvaluation(v, position); // 订单：立即评价
                        }
                    }
                    break;
                case R.id.cloud_order_action_gray:
                    if (null != mOnItemActionListener) {
                        if (mType == 1 && mOrStatus == 3) {
                            mOnItemActionListener.onApplyRefund(v, position); // 订单：申请退款
                        } else if (mType == 1 && mOrStatus == 5) {
                            mOnItemActionListener.onApplyAfterSale(v, position); // 订单：申请售后
                        }
                    }
                    break;
                default:
                    break;
            }
        }
    }

    public interface OnItemActionListener extends OnItemClickListener {
        void onImmediateDelivery(View view, int position);

        void onApplyRefund(View view, int position);

        void onApplyAfterSale(View view, int position);

        void onCancelAnOrder(View view, int position);

        void onContactService(View view, int position);

        void onImmediatePayment(View view, int position);

        void onConfirmReceipt(View view, int position);

        void onViewLogistics(View view, int position);

        void onImmediateEvaluation(View view, int position);

        void onSeeDetails(View view, int position);

        void onFillInLogistics(View view, int position);
    }
}
