package com.jgkj.grb.ui.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.jgkj.grb.R;
import com.jgkj.grb.base.BaseViewHolder;
import com.jgkj.grb.onclick.OnItemClickListener;
import com.jgkj.grb.ui.mvp.personal.AddresManagementModel;

import java.util.List;

import butterknife.BindView;

/**
 * 地址管理
 * Created by brightpoplar@163.com on 2019/8/19.
 */
public class AddressManagementAdapter extends RecyclerView.Adapter {
    private Context mContext;
    private List<AddresManagementModel.DataBean> mList;
    private LayoutInflater mLayoutInflater;
    private OnItemActionClickListener mOnItemClickListener;

    public void setOnItemClickListener(OnItemActionClickListener mOnItemClickListener) {
        this.mOnItemClickListener = mOnItemClickListener;
    }

    public AddressManagementAdapter(Context mContext, List<AddresManagementModel.DataBean> mList) {
        this.mContext = mContext;
        this.mList = mList;
        this.mLayoutInflater = LayoutInflater.from(mContext);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new AddressViewHolder(mLayoutInflater.inflate(R.layout.layout_item_address_management, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        if (viewHolder instanceof AddressViewHolder) {
            ((AddressViewHolder) viewHolder).bindView(mList.get(i), i);
        }
    }

    @Override
    public int getItemCount() {
        return null == mList ? 0 : mList.size();
    }

    class AddressViewHolder extends BaseViewHolder {
        @BindView(R.id.username)
        TextView username;
        @BindView(R.id.user_phone)
        TextView userphone;
        @BindView(R.id.address_edit)
        CardView addressEdit;
        @BindView(R.id.address_detail)
        TextView addressDetail;
        @BindView(R.id.select_address)
        ImageView selectAddress;

        AddresManagementModel.DataBean bean;
        int position;

        public AddressViewHolder(View itemView) {
            super(itemView);
        }

        public void bindView(AddresManagementModel.DataBean data, int pos) {
            bean = data;
            position = pos;
            getItemView().setOnClickListener(v -> {
                if (null != mOnItemClickListener) {
                    mOnItemClickListener.onItemClick(v, position);
                }
            });
            addressEdit.setOnClickListener(v -> {
                if (null != mOnItemClickListener) {
                    mOnItemClickListener.onEditItemClick(v, position);
                }
            });

            username.setText(bean.getAddr_receiver());
            userphone.setText(bean.getAddr_tel());
            addressDetail.setText(String.format("%s%s%s%s", bean.getProvince(), bean.getCity(), bean.getTown(), bean.getAddr_detail()));
            selectAddress.setVisibility(bean.getAddr_default() == 1 ? View.VISIBLE : View.GONE);
        }
    }

    public interface OnItemActionClickListener extends OnItemClickListener {
        void onEditItemClick(View v, int position);
    }
}
