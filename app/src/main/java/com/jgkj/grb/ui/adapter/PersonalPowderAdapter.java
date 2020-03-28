package com.jgkj.grb.ui.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jgkj.grb.R;
import com.jgkj.grb.base.BaseViewHolder;
import com.jgkj.grb.glide.GlideApp;
import com.jgkj.grb.onclick.OnItemClickListener;
import com.jgkj.grb.retrofit.ApiStores;
import com.jgkj.grb.ui.mvp.personal.PowderModel;

import java.util.List;

import anet.channel.util.StringUtils;
import butterknife.BindView;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * 我的宝粉
 * Created by brightpoplar@163.com on 2019/8/21.
 */
public class PersonalPowderAdapter extends RecyclerView.Adapter {
    private Context mContext;
    private List<PowderModel.DataBean> mList;
    private LayoutInflater mLayoutInflater;
    private OnItemClickListener mOnItemClickListener;

    public void setOnItemActionListener(OnItemClickListener mOnItemClickListener) {
        this.mOnItemClickListener = mOnItemClickListener;
    }

    public PersonalPowderAdapter(Context mContext, List<PowderModel.DataBean> mList) {
        this.mContext = mContext;
        this.mList = mList;
        this.mLayoutInflater = LayoutInflater.from(mContext);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new PersonalPowderViewHolder(mLayoutInflater.inflate(R.layout.layout_item_personal_powder, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        if (viewHolder instanceof PersonalPowderViewHolder) {
            ((PersonalPowderViewHolder) viewHolder).bindView(mList.get(i), i);
        }
    }

    @Override
    public int getItemCount() {
        return null == mList ? 0 : mList.size();
    }

    class PersonalPowderViewHolder extends BaseViewHolder {
        @BindView(R.id.userhead)
        CircleImageView mUserhead;
        @BindView(R.id.username)
        TextView mUsername;
        @BindView(R.id.user_achievement)
        TextView mUserAchievement;
        @BindView(R.id.user_right)
        TextView mUserRight;

        PowderModel.DataBean data;
        int position;

        public PersonalPowderViewHolder(View itemView) {
            super(itemView);
        }

        public void bindView(PowderModel.DataBean bean, int pos) {
            data = bean;
            position = pos;
            getItemView().setOnClickListener(v -> {
            });
            if (!TextUtils.isEmpty(data.getUs_head_pic()))
                GlideApp.with(mContext)
                        .load(data.getUs_head_pic().startsWith("http:") || data.getUs_head_pic().startsWith("https:")
                                ? data.getUs_head_pic().replaceAll("\\\\", "/")
                                : ApiStores.API_SERVER_URL + data.getUs_head_pic().replaceAll("\\\\", "/"))
                        .centerCrop()
                        .into(mUserhead);
            mUserAchievement.setText(String.format(mContext.getString(R.string.total_top_text), data.getSum()));
            mUsername.setText(data.getUs_nickname());
            mUserRight.setText(data.getUs_tel());

            /*if (!TextUtils.isEmpty(data.getUs_nickname())) {
                mUsername.setText(new StringBuilder(data.getUs_nickname()).replace(1, 2, "*"));
            } else {
                mUsername.setText("***");
            }
            if (!TextUtils.isEmpty(data.getUs_account()) && data.getUs_account().length() >= 3) {
                mUserRight.setText(new StringBuilder(data.getUs_account()).replace(3, 7, "****"));
            } else {
                mUserRight.setText("1***********");
            }*/
        }
    }
}
