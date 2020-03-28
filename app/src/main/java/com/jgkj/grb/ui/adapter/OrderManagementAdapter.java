package com.jgkj.grb.ui.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.jgkj.grb.R;
import com.jgkj.grb.base.BaseViewHolder;
import com.jgkj.grb.glide.GlideApp;
import com.jgkj.grb.glide.RoundTransformation;
import com.jgkj.grb.itemdecoration.SpaceItemDecoration;
import com.jgkj.grb.onclick.OnItemClickListener;
import com.jgkj.grb.onclick.RxView;
import com.jgkj.grb.retrofit.ApiStores;
import com.jgkj.grb.ui.mvp.OrderManagementModel;
import com.jgkj.grb.utils.ScreenUtils;

import java.util.List;
import java.util.Locale;

import butterknife.BindView;

/**
 * 我的订单
 * type   ：1 商城订单，2 云仓库订单，3 抢购订单，6 云仓库，7 退款/售后订单，4：谷聚金平台订单，5：谷聚金区/县订单，9 谷聚金发货
 * status ：0 待付款，3 待发货，4 待收货，5 待评价
 * Created by brightpoplar@163.com on 2019/8/20.
 */
public class OrderManagementAdapter extends RecyclerView.Adapter {
    private Context mContext;
    private List<OrderManagementModel.DataBean> mList;
    private LayoutInflater mLayoutInflater;
    private OnItemActionListener mOnItemActionListener;
    private int mType;

    public void setOnItemClickListener(OnItemActionListener mOnItemActionListener) {
        this.mOnItemActionListener = mOnItemActionListener;
    }

    public OrderManagementAdapter(Context mContext, List<OrderManagementModel.DataBean> mList, int type) {
        this.mContext = mContext;
        this.mList = mList;
        this.mLayoutInflater = LayoutInflater.from(mContext);
        this.mType = type;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        if (mType == 9) {
            return new OrderDeliveryViewHolder(mLayoutInflater.inflate(R.layout.layout_item_order_delivery, viewGroup, false));
        } else if (mType == 4 || mType == 5) {
            return new GujvjinOrderViewHolder(mLayoutInflater.inflate(R.layout.layout_item_gujvjin_order, viewGroup, false));
        }
        return new CloudWarehouseViewHolder(mLayoutInflater.inflate(R.layout.layout_item_cloud_warehouse, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        if (viewHolder instanceof OrderDeliveryViewHolder) {
            ((OrderDeliveryViewHolder) viewHolder).bindView(mList.get(i), i);
        } else if (viewHolder instanceof GujvjinOrderViewHolder) {
            ((GujvjinOrderViewHolder) viewHolder).bindView(mList.get(i), i);
        } else if (viewHolder instanceof CloudWarehouseViewHolder) {
            ((CloudWarehouseViewHolder) viewHolder).bindView(mList.get(i), i);
        }
    }

    @Override
    public int getItemCount() {
        return null == mList ? 0 : mList.size();
    }

    class CloudWarehouseViewHolder extends BaseViewHolder implements RxView.Action1 {
        @BindView(R.id.cloud_order_num)
        TextView cloudOrderNum;
        @BindView(R.id.cloud_order_status)
        CardView cloudOrderStatus;
        @BindView(R.id.cloud_order_status_tv)
        TextView cloudOrderStatusTv;
        @BindView(R.id.recycler_view)
        RecyclerView mRecyclerView;

        @BindView(R.id.cloud_order_rush_tip)
        TextView cloudOrderRushTip;
        @BindView(R.id.refund_after_sale_desc)
        TextView refundAfterSaleDesc;
        @BindView(R.id.cloud_order_action_red)
        FrameLayout cloudOrderActionRed;
        @BindView(R.id.cloud_order_action_red_tv)
        TextView cloudOrderActionRedTv;
        @BindView(R.id.cloud_order_action_gray)
        FrameLayout cloudOrderActionGray;
        @BindView(R.id.cloud_order_action_gray_tv)
        TextView cloudOrderActionGrayTv;

        OrderManagementModel.DataBean data;
        int mPosition;

        public CloudWarehouseViewHolder(View itemView) {
            super(itemView);
            mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
            mRecyclerView.addItemDecoration(new SpaceItemDecoration(ScreenUtils.dp2px(mContext, 1)));

            RxView.setOnClickListeners(this, cloudOrderActionRed, cloudOrderActionGray);
        }

        @Override
        public void onClick(Object o) {
            View v = (View) o;
            switch (v.getId()) {
                case R.id.cloud_order_action_red:
                    if (null != mOnItemActionListener) {
                        if ((mType == 1 || mType == 2 || mType == 3) && data.getOr_status() == 0) {
                            mOnItemActionListener.onImmediatePayment(v, mPosition, 0); // 订单：立即支付
                        } else if ((mType == 1 || mType == 2 || mType == 3) && data.getOr_status() == 3) {
                            mOnItemActionListener.onContactService(v, mPosition, 0); // 订单：联系客服
                        } else if ((mType == 1 || mType == 2 || mType == 3) && data.getOr_status() == 4) {
                            mOnItemActionListener.onConfirmReceipt(v, mPosition, 0); // 订单：确认收货
                        } else if (mType == 6) {
                            mOnItemActionListener.onImmediateDelivery(v, mPosition, 0); // 云仓库：立即提货
                        } else if (mType == 7) {
                            mOnItemActionListener.onSeeDetails(v, mPosition, 0); // 退款/售后：查看详情
                        }
                    }
                    break;
                case R.id.cloud_order_action_gray:
                    if (null != mOnItemActionListener) {
                        if ((mType == 1 || mType == 2 || mType == 3) && data.getOr_status() == 0) {
                            mOnItemActionListener.onCancelAnOrder(v, mPosition, 0); // 订单：取消订单
                        } else if ((mType == 1 || mType == 2 || mType == 3) && data.getOr_status() == 4) {
                            mOnItemActionListener.onViewLogistics(v, mPosition, 0); // 订单：查看物流
                        } else if (mType == 7 && data.getStatus() == 1 && data.getType() != 1) { // 售后中 && 退款退货 || 换货
                            mOnItemActionListener.onFillInLogistics(v, mPosition, 0); // 退款/售后：填写物流
                        }
                    }
                    break;
                default:
                    break;
            }
        }

        public void bindView(OrderManagementModel.DataBean bean, int pos) {
            data = bean;
            mPosition = pos;
            getItemView().setOnClickListener(v -> {
                if (null != mOnItemActionListener) {
                    mOnItemActionListener.onItemClick(v, mPosition);
                }
            });

            cloudOrderRushTip.setVisibility(mType == 3 ? View.VISIBLE : View.GONE);
            if (mType == 1 || mType == 2 || mType == 3) {
                cloudOrderNum.setText(String.format(mContext.getString(R.string.order_view_num), data.getOr_num()));
                if (data.getOr_status() == 0) {
                    cloudOrderStatus.setCardBackgroundColor(Color.parseColor("#FFE4E4"));
                    cloudOrderStatusTv.setText(R.string.order_management_mall_status_0);
                    cloudOrderStatusTv.setTextColor(Color.parseColor("#FB355C"));

                    cloudOrderActionGrayTv.setText(R.string.order_management_cancel);
                    cloudOrderActionGray.setVisibility(View.VISIBLE);
                    cloudOrderActionRedTv.setText(R.string.pay_now_text);
                    cloudOrderActionRed.setVisibility(View.VISIBLE);
                } else if (data.getOr_status() == 3) {
                    cloudOrderStatus.setCardBackgroundColor(Color.parseColor("#E7F8FF"));
                    cloudOrderStatusTv.setText(R.string.order_management_mall_status_3);
                    cloudOrderStatusTv.setTextColor(Color.parseColor("#74B9E2"));

                    cloudOrderActionGrayTv.setText(R.string.activity_title_apply_refund);
                    cloudOrderActionGray.setVisibility(View.GONE);
                    cloudOrderActionRedTv.setText(R.string.order_management_contact_service);
                    cloudOrderActionRed.setVisibility(View.VISIBLE);
                } else if (data.getOr_status() == 4) {
                    cloudOrderStatus.setCardBackgroundColor(Color.parseColor("#FFF4E6"));
                    cloudOrderStatusTv.setText(R.string.order_management_mall_status_4);
                    cloudOrderStatusTv.setTextColor(Color.parseColor("#F49137"));

                    cloudOrderActionGrayTv.setText(R.string.order_management_view_logistics);
                    cloudOrderActionGray.setVisibility(View.VISIBLE);
                    cloudOrderActionRedTv.setText(R.string.order_management_confirm_receipt);
                    cloudOrderActionRed.setVisibility(View.VISIBLE);
                } else if (data.getOr_status() == 5) {
                    cloudOrderStatus.setCardBackgroundColor(Color.parseColor("#E7FFF3"));
                    cloudOrderStatusTv.setText(R.string.order_management_mall_status_5);
                    cloudOrderStatusTv.setTextColor(Color.parseColor("#78C8B9"));

                    cloudOrderActionGrayTv.setText(R.string.order_management_apply_after_sale);
                    cloudOrderActionGray.setVisibility(View.GONE);
                    cloudOrderActionRedTv.setText(R.string.order_management_evaluation);
                    cloudOrderActionRed.setVisibility(mType == 1 ? View.GONE : View.VISIBLE);
                }
            } else if (mType == 7) {
                cloudOrderNum.setText(String.format(mContext.getString(R.string.order_view_num), data.getNum()));
                cloudOrderActionRedTv.setText(R.string.order_management_view_details);
                cloudOrderActionRed.setVisibility(View.VISIBLE);
                refundAfterSaleDesc.setVisibility(View.VISIBLE);

                if (data.getStatus() == 1) { // 1 售后中
                    cloudOrderStatus.setCardBackgroundColor(Color.parseColor("#FFE4E4"));
                    cloudOrderStatusTv.setText(R.string.order_management_after_sale_status_1);
                    cloudOrderStatusTv.setTextColor(Color.parseColor("#FB355C"));
                    if (data.getType() == 1) { // 1 退款
                        cloudOrderActionGrayTv.setText("");
                        cloudOrderActionGray.setVisibility(View.GONE);
                        refundAfterSaleDesc.setText(R.string.order_management_after_sale_status_11);
                    } else if (data.getType() == 2) { // 2 退款退货
                        cloudOrderActionGrayTv.setText(R.string.order_management_fillin_logistics);
                        cloudOrderActionGray.setVisibility(View.VISIBLE);
                        refundAfterSaleDesc.setText(R.string.order_management_after_sale_status_12);
                    } else if (data.getType() == 3) { // 3 换货
                        cloudOrderActionGrayTv.setText(R.string.order_management_fillin_logistics);
                        if (TextUtils.isEmpty(data.getExpress_name()) && TextUtils.isEmpty(data.getExpress_num())) {
                            cloudOrderActionGray.setVisibility(View.VISIBLE);
                        } else {
                            cloudOrderActionGray.setVisibility(View.GONE);
                        }
                        refundAfterSaleDesc.setText(R.string.order_management_after_sale_status_13);
                    }
                } else if (data.getStatus() == 5) { // 2 已完成
                    cloudOrderStatus.setCardBackgroundColor(Color.parseColor("#FFE4E4"));
                    cloudOrderStatusTv.setText(R.string.order_management_after_sale_status_5);
                    cloudOrderStatusTv.setTextColor(Color.parseColor("#FB355C"));
                    cloudOrderActionGrayTv.setText("");
                    cloudOrderActionGray.setVisibility(View.GONE);
                    if (data.getType() == 1) { // 1 退款
                        refundAfterSaleDesc.setText(R.string.order_management_after_sale_status_51);
                    } else if (data.getType() == 2) { // 2 退款退货
                        refundAfterSaleDesc.setText(R.string.order_management_after_sale_status_52);
                    } else if (data.getType() == 3) { // 3 换货
                        refundAfterSaleDesc.setText(R.string.order_management_after_sale_status_53);
                    }
                } else if (data.getStatus() == 6) { // 3 申请失败
                    cloudOrderStatus.setCardBackgroundColor(Color.parseColor("#EEEEEE"));
                    cloudOrderStatusTv.setText(R.string.order_management_after_sale_status_6);
                    cloudOrderStatusTv.setTextColor(Color.parseColor("#666666"));
                    cloudOrderActionGrayTv.setText("");
                    cloudOrderActionGray.setVisibility(View.GONE);
                    if (data.getType() == 1) { // 1 退款
                        refundAfterSaleDesc.setText(R.string.order_management_after_sale_status_61);
                    } else if (data.getType() == 2) { // 2 退款退货
                        refundAfterSaleDesc.setText(R.string.order_management_after_sale_status_62);
                    } else if (data.getType() == 3) { // 3 换货
                        refundAfterSaleDesc.setText(R.string.order_management_after_sale_status_63);
                    }
                }
                if (data.getType() == 1) { // 1 退款
                    refundAfterSaleDesc.setCompoundDrawablesWithIntrinsicBounds(R.mipmap.ic_apply_after_sale_0, 0, 0, 0);
                } else if (data.getType() == 2) { // 2 退款退货
                    refundAfterSaleDesc.setCompoundDrawablesWithIntrinsicBounds(R.mipmap.ic_apply_after_sale_1, 0, 0, 0);
                } else if (data.getType() == 3) { // 3 换货
                    refundAfterSaleDesc.setCompoundDrawablesWithIntrinsicBounds(R.mipmap.ic_apply_after_sale_2, 0, 0, 0);
                }
            } else if (mType == 6) {
                cloudOrderNum.setText(String.format(mContext.getString(R.string.order_view_num), data.getOr_num()));
                cloudOrderStatus.setCardBackgroundColor(Color.parseColor("#FFE4E4"));
                cloudOrderStatusTv.setText(R.string.order_management_cloud_status);
                cloudOrderStatusTv.setTextColor(Color.parseColor("#FB355C"));

                cloudOrderActionGrayTv.setText("");
                cloudOrderActionGray.setVisibility(View.GONE);
                cloudOrderActionRedTv.setText(R.string.order_management_prompt_delivery);
                cloudOrderActionRed.setVisibility(View.VISIBLE);
            }

            OrderManagementChildAdapter adapter = new OrderManagementChildAdapter(mContext, data.getDetail(), mType, data.getOr_status());
            mRecyclerView.setAdapter(adapter);
            adapter.setOnItemActionListener(new OrderManagementChildAdapter.OnItemActionListener() {
                @Override
                public void onImmediateDelivery(View view, int childPos) {
                    if (null != mOnItemActionListener) {
                        mOnItemActionListener.onImmediateDelivery(view, mPosition, childPos); // 云仓库：立即提货
                    }
                }

                @Override
                public void onApplyRefund(View view, int childPos) {
                    // 云仓库暂取消此功能
                    if (null != mOnItemActionListener) {
                        mOnItemActionListener.onApplyRefund(view, mPosition, childPos); // 订单：申请退款：商品
                    }
                }

                @Override
                public void onCancelAnOrder(View view, int childPos) {
                    if (null != mOnItemActionListener) {
                        mOnItemActionListener.onCancelAnOrder(view, mPosition, childPos); // 订单：取消订单
                    }
                }

                @Override
                public void onViewLogistics(View view, int childPos) {
                    if (null != mOnItemActionListener) {
                        mOnItemActionListener.onViewLogistics(view, mPosition, childPos); // 订单：查看物流
                    }
                }

                @Override
                public void onApplyAfterSale(View view, int childPos) {
                    if (null != mOnItemActionListener) {
                        mOnItemActionListener.onApplyAfterSale(view, mPosition, childPos); // 订单：申请售后：商品
                    }
                }

                @Override
                public void onImmediateEvaluation(View view, int childPos) {
                    if (null != mOnItemActionListener) {
                        mOnItemActionListener.onImmediateEvaluation(view, mPosition, childPos); // 订单：立即评价：商品
                    }
                }

                @Override
                public void onContactService(View view, int childPos) {
                    if (null != mOnItemActionListener) {
                        mOnItemActionListener.onContactService(view, mPosition, childPos); // 订单：联系客服
                    }
                }

                @Override
                public void onImmediatePayment(View view, int childPos) {
                    if (null != mOnItemActionListener) {
                        mOnItemActionListener.onImmediatePayment(view, mPosition, childPos); // 订单：立即支付
                    }
                }

                @Override
                public void onConfirmReceipt(View view, int childPos) {
                    if (null != mOnItemActionListener) {
                        mOnItemActionListener.onConfirmReceipt(view, mPosition, childPos); // 订单：确认收货
                    }
                }

                @Override
                public void onSeeDetails(View view, int childPos) {
                    if (null != mOnItemActionListener) {
                        mOnItemActionListener.onSeeDetails(view, mPosition, childPos); // 退款/售后：查看详情
                    }
                }

                @Override
                public void onFillInLogistics(View view, int childPos) {
                    if (null != mOnItemActionListener) {
                        mOnItemActionListener.onFillInLogistics(view, mPosition, childPos); // 退款/售后：填写物流
                    }
                }

                @Override
                public void onItemClick(View view, int position) {
                    if (null != mOnItemActionListener) {
                        if (mType == 1 || mType == 2 || mType == 3) {
                            mOnItemActionListener.onItemClick(view, mPosition);
                        }
                    }
                }
            });
        }
    }

    class OrderDeliveryViewHolder extends BaseViewHolder implements RxView.Action1 {

        @BindView(R.id.order_num)
        TextView orderNum;
        @BindView(R.id.order_date)
        TextView orderDate;
        @BindView(R.id.order_iv)
        ImageView orderIv;
        @BindView(R.id.order_name)
        TextView orderName;
        @BindView(R.id.order_number)
        TextView orderNumber;
        @BindView(R.id.order_price)
        TextView orderPrice;
        @BindView(R.id.order_receiver_name)
        TextView orderReceiverName;
        @BindView(R.id.order_receiver_phone)
        TextView orderReceiverPhone;
        @BindView(R.id.order_receiver_addr)
        TextView orderReceiverAddr;
        @BindView(R.id.cloud_order_action_red)
        FrameLayout cloudOrderActionRed;
        @BindView(R.id.cloud_order_action_red_tv)
        TextView cloudOrderActionRedTv;

        OrderManagementModel.DataBean data;
        int position;

        public OrderDeliveryViewHolder(View itemView) {
            super(itemView);

            RxView.setOnClickListeners(this, cloudOrderActionRed);
        }

        @Override
        public void onClick(Object o) {
            View v = (View) o;
            switch (v.getId()) {
                case R.id.cloud_order_action_red:
                    if (null != mOnItemActionListener) {
                        if (data.getOr_status() == 3) {
                            mOnItemActionListener.onDeliverGoods(v, position, 0); // 谷聚金发货：发货
                        }
                    }
                    break;
                default:
                    break;
            }
        }

        public void bindView(OrderManagementModel.DataBean bean, int pos) {
            data = bean;
            position = pos;
            getItemView().setOnClickListener(v -> {
                if (null != mOnItemActionListener) {
                    mOnItemActionListener.onItemClick(v, pos);
                }
            });

            orderNum.setText(String.format(mContext.getString(R.string.order_view_num), data.getOr_num()));
            orderDate.setText(data.getAdd_time());
            if (null != data.getDetail() && data.getDetail().size() > 0) {
                OrderManagementModel.DataBean.DetailBean detailBean = data.getDetail().get(0);
                if (!TextUtils.isEmpty(detailBean.getPd_head_pic())) {
                    GlideApp.with(mContext)
                            .load(detailBean.getPd_head_pic().startsWith("http:") || detailBean.getPd_head_pic().startsWith("https")
                                    ? detailBean.getPd_head_pic().replaceAll("\\\\", "/")
                                    : ApiStores.API_SERVER_URL + detailBean.getPd_head_pic().replaceAll("\\\\", "/"))
                            .transform(new CenterCrop(), new RoundTransformation(mContext, 5))
                            .into(orderIv);
                }
                orderName.setText(detailBean.getOr_pd_name());
                orderNumber.setText(String.format(Locale.getDefault(), "X %d", detailBean.getOr_pd_num()));
                orderPrice.setText(String.format(mContext.getString(R.string.total_top_text),
                        detailBean.getType4() == 1 ? detailBean.getOr_pd_price() : detailBean.getOr_pd_total()));
            }
            orderReceiverName.setText(data.getAddr_receiver());
            orderReceiverPhone.setText(data.getAddr_tel());
            orderReceiverAddr.setText(data.getAddr());

            if (data.getOr_status() == 3) {
                cloudOrderActionRedTv.setText(R.string.gujujin_order_delivery_action);
                cloudOrderActionRed.setVisibility(View.VISIBLE);
            } else if (data.getOr_status() == 4) {
                cloudOrderActionRed.setVisibility(View.GONE);
            }
        }
    }

    class GujvjinOrderViewHolder extends BaseViewHolder implements RxView.Action1 {

        @BindView(R.id.order_num)
        TextView orderNum;
        @BindView(R.id.cloud_order_status)
        CardView cloudOrderStatus;
        @BindView(R.id.cloud_order_status_tv)
        TextView cloudOrderStatusTv;
        @BindView(R.id.order_iv)
        ImageView orderIv;
        @BindView(R.id.order_name)
        TextView orderName;
        @BindView(R.id.order_number)
        TextView orderNumber;
        @BindView(R.id.order_price)
        TextView orderPrice;
        @BindView(R.id.cloud_order_action_red)
        FrameLayout cloudOrderActionRed;
        @BindView(R.id.cloud_order_action_red_tv)
        TextView cloudOrderActionRedTv;
        @BindView(R.id.cloud_order_action_gray)
        FrameLayout cloudOrderActionGray;
        @BindView(R.id.cloud_order_action_gray_tv)
        TextView cloudOrderActionGrayTv;

        OrderManagementModel.DataBean data;
        int position;

        public GujvjinOrderViewHolder(View itemView) {
            super(itemView);

            RxView.setOnClickListeners(this, cloudOrderActionRed, cloudOrderActionGray);
        }

        @Override
        public void onClick(Object o) {
            View v = (View) o;
            switch (v.getId()) {
                case R.id.cloud_order_action_red:
                    if (null != mOnItemActionListener) {
                        if (data.getOr_status() == 0) {
                            mOnItemActionListener.onImmediatePayment(v, position, 0); // 谷聚金订单：立即支付
                        } else if (data.getOr_status() == 3) {
                            mOnItemActionListener.onContactService(v, position, 0); // 谷聚金订单：联系客服
                        } else if (data.getOr_status() == 4) {
                            mOnItemActionListener.onConfirmReceipt(v, position, 0); // 谷聚金订单：确认收货
                        } else {
                            mOnItemActionListener.onUpload(v, position, 0); // 谷聚金订单：上传凭证
                        }
                    }
                    break;
                case R.id.cloud_order_action_gray:
                    if (null != mOnItemActionListener) {
                        if (data.getOr_status() == 0) {
                            mOnItemActionListener.onCancelAnOrder(v, position, 0); // 谷聚金订单：取消订单
                        } else if (data.getOr_status() == 4) {
                            mOnItemActionListener.onViewLogistics(v, position, 0); // 谷聚金订单：查看物流
                        }
                    }
                    break;
                default:
                    break;
            }
        }

        public void bindView(OrderManagementModel.DataBean bean, int pos) {
            data = bean;
            position = pos;
            getItemView().setOnClickListener(v -> {
                if (null != mOnItemActionListener) {
                    mOnItemActionListener.onItemClick(v, pos);
                }
            });

            orderNum.setText(String.format(mContext.getString(R.string.order_view_num), data.getOr_num()));
            if (null != data.getDetail() && data.getDetail().size() > 0) {
                OrderManagementModel.DataBean.DetailBean detailBean = data.getDetail().get(0);
                if (!TextUtils.isEmpty(detailBean.getPd_head_pic())) {
                    GlideApp.with(mContext)
                            .load(detailBean.getPd_head_pic().startsWith("http:") || detailBean.getPd_head_pic().startsWith("https")
                                    ? detailBean.getPd_head_pic().replaceAll("\\\\", "/")
                                    : ApiStores.API_SERVER_URL + detailBean.getPd_head_pic().replaceAll("\\\\", "/"))
                            .transform(new CenterCrop(), new RoundTransformation(mContext, 5))
                            .into(orderIv);
                }
                orderName.setText(detailBean.getOr_pd_name());
                orderNumber.setText(String.format(Locale.getDefault(), "X %d", detailBean.getOr_pd_num()));
                orderPrice.setText(String.format(mContext.getString(R.string.total_top_text),
                        detailBean.getType4() == 1 ? detailBean.getOr_pd_price() : detailBean.getOr_pd_total()));
            }

            if (data.getOr_status() == 0) {
                cloudOrderStatus.setCardBackgroundColor(Color.parseColor("#FFE4E4"));
                cloudOrderStatusTv.setText(R.string.order_management_mall_status_0);
                cloudOrderStatusTv.setTextColor(Color.parseColor("#FB355C"));

                cloudOrderActionGrayTv.setText(R.string.order_management_cancel);
                cloudOrderActionGray.setVisibility(View.VISIBLE);
                cloudOrderActionRedTv.setText(R.string.pay_now_text);
                cloudOrderActionRed.setVisibility(View.VISIBLE);
            } else if (data.getOr_status() == 3) {
                cloudOrderStatus.setCardBackgroundColor(Color.parseColor("#E7F8FF"));
                cloudOrderStatusTv.setText(R.string.order_management_mall_status_3);
                cloudOrderStatusTv.setTextColor(Color.parseColor("#7ABBE3"));

                cloudOrderActionGrayTv.setText("");
                cloudOrderActionGray.setVisibility(View.GONE);
                cloudOrderActionRedTv.setText(R.string.order_management_contact_service);
                cloudOrderActionRed.setVisibility(View.VISIBLE);
            } else if (data.getOr_status() == 4) {
                cloudOrderStatus.setCardBackgroundColor(Color.parseColor("#FFF4E6"));
                cloudOrderStatusTv.setText(R.string.order_management_mall_status_4);
                cloudOrderStatusTv.setTextColor(Color.parseColor("#F6A965"));

                cloudOrderActionGrayTv.setText(R.string.order_management_view_logistics);
                cloudOrderActionGray.setVisibility(View.VISIBLE);
                cloudOrderActionRedTv.setText(R.string.order_management_confirm_receipt);
                cloudOrderActionRed.setVisibility(View.VISIBLE);
            } else if (data.getOr_status() == 5) {
                cloudOrderStatus.setCardBackgroundColor(Color.parseColor("#E7FFF3"));
                cloudOrderStatusTv.setText(R.string.order_management_mall_status_5);
                cloudOrderStatusTv.setTextColor(Color.parseColor("#78C8B9"));

                cloudOrderActionGrayTv.setText("");
                cloudOrderActionGray.setVisibility(View.GONE);
                cloudOrderActionRedTv.setText("");
                cloudOrderActionRed.setVisibility(View.GONE);
            } else if (data.getOr_status() == 9) { // 上传凭证
                cloudOrderStatus.setCardBackgroundColor(Color.parseColor("#E7EAFF"));
                cloudOrderStatusTv.setText(R.string.order_management_to_be_uploaded);
                cloudOrderStatusTv.setTextColor(Color.parseColor("#808EF3"));

                cloudOrderActionGrayTv.setText("");
                cloudOrderActionGray.setVisibility(View.GONE);
                cloudOrderActionRedTv.setText(R.string.activity_title_gujujin_upload_credentials);
                cloudOrderActionRed.setVisibility(View.VISIBLE);
            }
        }
    }


    public interface OnItemActionListener extends OnItemClickListener {
        void onImmediateDelivery(View view, int parentPos, int childPos);

        void onApplyRefund(View view, int parentPos, int childPos);

        void onApplyAfterSale(View view, int parentPos, int childPos);

        void onCancelAnOrder(View view, int parentPos, int childPos);

        void onContactService(View view, int parentPos, int childPos);

        void onImmediatePayment(View view, int parentPos, int childPos);

        void onConfirmReceipt(View view, int parentPos, int childPos);

        void onViewLogistics(View view, int parentPos, int childPos);

        void onImmediateEvaluation(View view, int parentPos, int childPos);

        void onSeeDetails(View view, int parentPos, int childPos);

        void onFillInLogistics(View view, int parentPos, int childPos);

        void onDeliverGoods(View view, int parentPos, int childPos);

        void onUpload(View view, int parentPos, int childPos);
    }
}
