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

import com.jgkj.grb.R;
import com.jgkj.grb.base.BaseViewHolder;
import com.jgkj.grb.onclick.OnItemClickListener;
import com.jgkj.grb.onclick.RxView;
import com.jgkj.grb.ui.mvp.luckdraw.LuckDrawRecordBean;

import java.util.List;

import butterknife.BindView;

/**
 * GRB抽奖：我的奖品
 * Created by brightpoplar@163.com on 2019/8/17.
 */
public class LuckDrawMineAdapter extends RecyclerView.Adapter {
    private Context mContext;
    private List<LuckDrawRecordBean> mList;
    private LayoutInflater mLayoutInflater;
    private OnItemClickListener mOnItemClickListener;

    public void setOnItemClickListener(OnItemClickListener mOnItemClickListener) {
        this.mOnItemClickListener = mOnItemClickListener;
    }

    public LuckDrawMineAdapter(Context mContext, List<LuckDrawRecordBean> mList) {
        this.mContext = mContext;
        this.mList = mList;
        this.mLayoutInflater = LayoutInflater.from(mContext);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new LuckDrawMineViewHolder(mLayoutInflater.inflate(R.layout.layout_item_luck_draw_mine, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        if (viewHolder instanceof LuckDrawMineViewHolder) {
            ((LuckDrawMineViewHolder) viewHolder).bindView(mList.get(i), i);
        }
    }

    @Override
    public int getItemCount() {
        return null == mList ? 0 : mList.size();
    }

    class LuckDrawMineViewHolder extends BaseViewHolder implements RxView.Action1 {
        @BindView(R.id.luck_draw_details)
        TextView luckDrawDetails;
        @BindView(R.id.luck_draw_time)
        TextView luckDrawTime;
        @BindView(R.id.luck_draw_receive)
        ImageView luckDrawReceive;
        @BindView(R.id.luck_draw_status)
        TextView luckDrawStatus;
        LuckDrawRecordBean data;
        int position;

        public LuckDrawMineViewHolder(View itemView) {
            super(itemView);
            RxView.setOnClickListeners(this, luckDrawReceive);
        }

        public void bindView(LuckDrawRecordBean bean, int pos) {
            data = bean;
            position = pos;
            getItemView().setOnClickListener(v -> {
            });

            luckDrawDetails.setText(data.getContent());
            luckDrawTime.setText(TextUtils.isEmpty(data.getTime()) ? "" : data.getTime());
            // 1：已领取，2：待领取，3：已发放
            if (data.getStatus() == 1) {
                luckDrawReceive.setVisibility(View.GONE);
                luckDrawStatus.setVisibility(View.VISIBLE);
                luckDrawStatus.setText(R.string.luck_draw_mine_received);
            } else if (data.getStatus() == 2) {
                luckDrawReceive.setVisibility(View.VISIBLE);
                luckDrawStatus.setVisibility(View.GONE);
            } else if (data.getStatus() == 3) {
                luckDrawReceive.setVisibility(View.GONE);
                luckDrawStatus.setVisibility(View.VISIBLE);
                luckDrawStatus.setText(R.string.luck_draw_mine_provided);
            }
        }

        @Override
        public void onClick(Object o) {
            View v = (View) o;
            switch (v.getId()) {
                case R.id.luck_draw_receive:
                    if (null != mOnItemClickListener && null != data && data.getStatus() == 2) {
                        mOnItemClickListener.onItemClick(v, position);
                    }
                    break;
                default:
                    break;
            }
        }
    }
}
