package com.jgkj.grb.ui.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.jgkj.grb.R;
import com.jgkj.grb.base.BaseViewHolder;
import com.jgkj.grb.glide.GlideApp;
import com.jgkj.grb.glide.RoundTransformation;
import com.jgkj.grb.onclick.OnItemClickListener;
import com.jgkj.grb.retrofit.ApiStores;
import com.jgkj.grb.utils.ScreenUtils;

import java.util.List;

import butterknife.BindView;

/**
 * 商品详情：图片列表，详情主页(垂直布局)、评价列表(水平布局)、评价详情(垂直布局)
 * Created by brightpoplar@163.com on 2019/8/6.
 */
public class ProductDetailsAdapter extends RecyclerView.Adapter {

    private Context mContext;
    private List<String> mList;
    private LayoutInflater mLayoutInflater;
    private OnItemClickListener mOnItemClickListener;

    public void setOnItemClickListener(OnItemClickListener mOnItemClickListener) {
        this.mOnItemClickListener = mOnItemClickListener;
    }

    private static final int ITEM_TYPE_PRODUCT_DETAILS = 0; // 商品详情
    private static final int ITEM_TYPE_PRODUCT_EVALUA = 1; // 商品评价
    private static final int ITEM_TYPE_PRODUCT_EVALUA_DETAILS = 2; // 商品评价详情
    private static final int ITEM_TYPE_PRODUCT_EVALUA_MINE = 3; // 我的评价
    private static final int ITEM_TYPE_PRODUCT_EVALUA_PUBLISH = 4; // 发表评价
    private static final int ITEM_TYPE_PRODUCT_EVALUA_AFTER = 5; // 申请售后
    private int mItemType = ITEM_TYPE_PRODUCT_DETAILS;

    public ProductDetailsAdapter(Context mContext, List<String> mList, int itemType) {
        this.mContext = mContext;
        this.mList = mList;
        this.mLayoutInflater = LayoutInflater.from(mContext);
        this.mItemType = itemType;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        switch (mItemType) {
            case ITEM_TYPE_PRODUCT_DETAILS:
                return new ProductDetailsViewHolder(mLayoutInflater.inflate(R.layout.layout_item_product_details, viewGroup, false));
            case ITEM_TYPE_PRODUCT_EVALUA:
                return new ProductEvaluaViewHolder(mLayoutInflater.inflate(R.layout.layout_item_product_details, viewGroup, false));
            case ITEM_TYPE_PRODUCT_EVALUA_DETAILS:
                return new ProductEvaluaDetailsViewHolder(mLayoutInflater.inflate(R.layout.layout_item_product_details, viewGroup, false));
            case ITEM_TYPE_PRODUCT_EVALUA_MINE:
                return new ProductMyEvaluaViewHolder(mLayoutInflater.inflate(R.layout.layout_item_product_details, viewGroup, false));
            case ITEM_TYPE_PRODUCT_EVALUA_PUBLISH:
                return new ProductPublishViewHolder(mLayoutInflater.inflate(R.layout.layout_item_product_details, viewGroup, false));
            case ITEM_TYPE_PRODUCT_EVALUA_AFTER:
                return new ProductAfterViewHolder(mLayoutInflater.inflate(R.layout.layout_item_product_details, viewGroup, false));
            default:
                return null;
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        if (viewHolder instanceof ProductDetailsViewHolder) {
            ((ProductDetailsViewHolder) viewHolder).bindView(mList.get(i));
        } else if (viewHolder instanceof ProductEvaluaViewHolder) {
            ((ProductEvaluaViewHolder) viewHolder).bindView(mList.get(i));
        } else if (viewHolder instanceof ProductEvaluaDetailsViewHolder) {
            ((ProductEvaluaDetailsViewHolder) viewHolder).bindView(mList.get(i));
        } else if (viewHolder instanceof ProductMyEvaluaViewHolder) {
            ((ProductMyEvaluaViewHolder) viewHolder).bindView(mList.get(i));
        } else if (viewHolder instanceof ProductPublishViewHolder) {
            ((ProductPublishViewHolder) viewHolder).bindView(mList.get(i), i);
        } else if (viewHolder instanceof ProductAfterViewHolder) {
            ((ProductAfterViewHolder) viewHolder).bindView(mList.get(i), i);
        }
    }

    @Override
    public int getItemCount() {
        return null == mList ? 0 : mList.size();
    }

    class ProductDetailsViewHolder extends BaseViewHolder {

        @BindView(R.id.item_image)
        ImageView mItemImage;

        public ProductDetailsViewHolder(View itemView) {
            super(itemView);
        }

        public void bindView(Object data) {
            GlideApp.with(mContext).load(data).into(mItemImage);
        }
    }

    class ProductEvaluaViewHolder extends BaseViewHolder {

        @BindView(R.id.item_image)
        ImageView mItemImage;

        public ProductEvaluaViewHolder(View itemView) {
            super(itemView);
        }

        public void bindView(String data) {

            ViewGroup.LayoutParams layoutParams = mItemImage.getLayoutParams();
            layoutParams.width = ScreenUtils.dp2px(mContext, 93);
            layoutParams.height = ScreenUtils.dp2px(mContext, 93);
            mItemImage.setLayoutParams(layoutParams);

            GlideApp.with(mContext)
                    .load(data.startsWith("http:") || data.startsWith("http:")
                            ? data.replaceAll("\\\\", "/") : ApiStores.API_SERVER_URL + data.replaceAll("\\\\", "/"))
                    .transform(new CenterCrop(), new RoundTransformation(mContext, 5))
                    .into(mItemImage);
        }
    }

    class ProductMyEvaluaViewHolder extends BaseViewHolder {

        @BindView(R.id.item_image)
        ImageView mItemImage;

        public ProductMyEvaluaViewHolder(View itemView) {
            super(itemView);
        }

        public void bindView(String data) {

            ViewGroup.LayoutParams layoutParams = mItemImage.getLayoutParams();
            layoutParams.width = ScreenUtils.dp2px(mContext, 65);
            layoutParams.height = ScreenUtils.dp2px(mContext, 65);
            mItemImage.setLayoutParams(layoutParams);

            if (!TextUtils.isEmpty(data))
                GlideApp.with(mContext)
                        .load(data.startsWith("http:") || data.startsWith("http:")
                                ? data.replaceAll("\\\\", "/") : ApiStores.API_SERVER_URL + data.replaceAll("\\\\", "/"))
                        .transform(new CenterCrop(), new RoundTransformation(mContext, 5))
                        .into(mItemImage);
        }
    }

    class ProductPublishViewHolder extends BaseViewHolder {

        @BindView(R.id.item_image)
        ImageView mItemImage;

        public ProductPublishViewHolder(View itemView) {
            super(itemView);
        }

        public void bindView(Object data, int position) {

            ViewGroup.LayoutParams layoutParams = mItemImage.getLayoutParams();
            // int imgSize = (ScreenUtils.getScreenWidth((Activity) mContext) - ScreenUtils.dp2px(mContext, (20 + 12) * 2 + 10 * 3)) / 4;
            layoutParams.width = ScreenUtils.dp2px(mContext, 60);
            layoutParams.height = ScreenUtils.dp2px(mContext, 60);
            mItemImage.setLayoutParams(layoutParams);

            if (TextUtils.equals("addSelect", data.toString())) {
                getItemView().setOnClickListener(v -> {
                    if (null != mOnItemClickListener) {
                        mOnItemClickListener.onItemClick(v, position);
                    }
                });

                GlideApp.with(mContext)
                        .load(R.mipmap.ic_product_publish_select_img)
                        .transform(new CenterCrop(), new RoundTransformation(mContext, 5))
                        .into(mItemImage);
            } else {
                GlideApp.with(mContext)
                        .load(data)
                        .transform(new CenterCrop(), new RoundTransformation(mContext, 5))
                        .into(mItemImage);
            }

        }
    }

    class ProductAfterViewHolder extends BaseViewHolder {

        @BindView(R.id.item_image)
        ImageView mItemImage;

        public ProductAfterViewHolder(View itemView) {
            super(itemView);
        }

        public void bindView(Object data, int position) {

            ViewGroup.LayoutParams layoutParams = mItemImage.getLayoutParams();
            // int imgSize = (ScreenUtils.getScreenWidth((Activity) mContext) - ScreenUtils.dp2px(mContext, (20 + 12) * 2 + 10 * 3)) / 4;
            layoutParams.width = ScreenUtils.dp2px(mContext, 75);
            layoutParams.height = ScreenUtils.dp2px(mContext, 75);
            mItemImage.setLayoutParams(layoutParams);

            if (TextUtils.equals("addSelect", data.toString())) {
                getItemView().setOnClickListener(v -> {
                    if (null != mOnItemClickListener) {
                        mOnItemClickListener.onItemClick(v, position);
                    }
                });

                GlideApp.with(mContext)
                        .load(R.mipmap.ic_apply_after_select_img)
                        .transform(new CenterCrop(), new RoundTransformation(mContext, 5))
                        .into(mItemImage);
            } else {
                GlideApp.with(mContext)
                        .load(data)
                        .transform(new CenterCrop(), new RoundTransformation(mContext, 5))
                        .into(mItemImage);
            }

        }
    }

    class ProductEvaluaDetailsViewHolder extends BaseViewHolder {

        @BindView(R.id.item_image)
        ImageView mItemImage;

        public ProductEvaluaDetailsViewHolder(View itemView) {
            super(itemView);
        }

        public void bindView(String data) {
            GlideApp.with(mContext).load(data.startsWith("http:") || data.startsWith("http:")
                    ? data.replaceAll("\\\\", "/") : ApiStores.API_SERVER_URL + data.replaceAll("\\\\", "/"))
                    .into(mItemImage);
        }
    }
}
