package com.jgkj.grb.ui.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jgkj.grb.R;
import com.jgkj.grb.base.BaseViewHolder;
import com.jgkj.grb.glide.GlideApp;
import com.jgkj.grb.retrofit.ApiStores;
import com.jgkj.grb.ui.mvp.find.FindDetailsModel;
import com.jgkj.utils.token.utils.sp.SharedPreferencesHelper;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import butterknife.BindView;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * 发现：详情评论列表
 * Created by brightpoplar@163.com on 2019/8/16.
 */
public class FindDetailsCommentAdapter extends RecyclerView.Adapter {
    private Context mContext;
    private List<FindDetailsModel.DataBean.AdvisoryBean> mList;
    private LayoutInflater mLayoutInflater;
    private OnActionClickListener mOnActionClickListener;
    private SharedPreferencesHelper mSharedPreferencesHelper;

    public void setOnActionClickListener(OnActionClickListener mOnActionClickListener) {
        this.mOnActionClickListener = mOnActionClickListener;
    }

    public FindDetailsCommentAdapter(Context mContext, List<FindDetailsModel.DataBean.AdvisoryBean> mList) {
        this.mContext = mContext;
        this.mList = mList;
        this.mLayoutInflater = LayoutInflater.from(mContext);
        this.mSharedPreferencesHelper = new SharedPreferencesHelper(mContext);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new CommentViewHolder(mLayoutInflater.inflate(R.layout.layout_item_fragment_find_comment, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        if (viewHolder instanceof CommentViewHolder) {
            ((CommentViewHolder) viewHolder).bindView(mList.get(i), i);
        }
    }

    @Override
    public int getItemCount() {
        return null == mList ? 0 : mList.size();
    }

    class CommentViewHolder extends BaseViewHolder {
        @BindView(R.id.userhead)
        CircleImageView userhead;
        @BindView(R.id.username)
        TextView username;
        @BindView(R.id.post_comment_num)
        TextView postCommentNum;
        @BindView(R.id.post_comment_details)
        TextView postCommentDetails;
        @BindView(R.id.post_comment_reply)
        ConstraintLayout postCommentReply;
        @BindView(R.id.post_comment_reply_num)
        TextView postCommentReplyNum;
        @BindView(R.id.post_comment_details_reply)
        TextView postCommentDetailsReply;

        FindDetailsModel.DataBean.AdvisoryBean bean;
        int position;

        public CommentViewHolder(View itemView) {
            super(itemView);
        }

        public void bindView(FindDetailsModel.DataBean.AdvisoryBean data, int pos) {
            bean = data;
            position = pos;
            getItemView().setOnClickListener(v -> {
            });
            if (!TextUtils.isEmpty(bean.getUs_head_pic()))
                GlideApp.with(mContext)
                        .load(bean.getUs_head_pic().startsWith("http:") || bean.getUs_head_pic().startsWith("https:")
                                ? bean.getUs_head_pic().replaceAll("\\\\", "/")
                                : ApiStores.API_SERVER_URL + bean.getUs_head_pic().replaceAll("\\\\", "/"))
                        .into(userhead);
            username.setText(bean.getUs_nickname());
            postCommentNum.setText(String.valueOf(bean.getThumbs()));
            postCommentDetails.setText(bean.getCoutent());
            if (null != bean.getSecend() && !TextUtils.isEmpty(bean.getSecend().getCoutent())) {
                postCommentReply.setVisibility(View.VISIBLE);
                postCommentReplyNum.setText(String.valueOf(bean.getSecend().getThumbs()));
                postCommentDetailsReply.setText(bean.getSecend().getCoutent());

                boolean itsMeC = bean.getSecend().itsMe(getUserId());
                postCommentReplyNum.setCompoundDrawablesWithIntrinsicBounds(itsMeC ? R.mipmap.ic_comment_red : R.mipmap.ic_comment_normal, 0, 0, 0);
            } else {
                postCommentReply.setVisibility(View.GONE);
                postCommentReplyNum.setCompoundDrawablesWithIntrinsicBounds(R.mipmap.ic_comment_normal, 0, 0, 0);
            }

            boolean itsMeP = bean.itsMe(getUserId());
            postCommentNum.setCompoundDrawablesWithIntrinsicBounds(itsMeP ? R.mipmap.ic_comment_red : R.mipmap.ic_comment_normal, 0, 0, 0);
            postCommentNum.setOnClickListener(v -> {
                if (null != mOnActionClickListener) {
                    mOnActionClickListener.onClickGroup(position);
                }
            });
            postCommentReplyNum.setOnClickListener(v -> {
                if (null != mOnActionClickListener) {
                    mOnActionClickListener.onClickChild(position);
                }
            });
        }
    }

    public interface OnActionClickListener {
        void onClickGroup(int position);

        void onClickChild(int position);
    }

    protected String getUserId() {
        try {
            // is_realname  实名情况：0:未认证  1认证中 2已认证
            String userStr = mSharedPreferencesHelper.getSharedPreference("user", "").toString();
            JSONObject user = new JSONObject(userStr);
            return user.optString("id", null);
        } catch (JSONException e) {
            return null;
        }
    }
}
