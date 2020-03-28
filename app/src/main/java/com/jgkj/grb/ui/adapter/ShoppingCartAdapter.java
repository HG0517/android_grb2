package com.jgkj.grb.ui.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.jgkj.grb.R;
import com.jgkj.grb.base.BaseViewHolder;
import com.jgkj.grb.itemdecoration.SpaceItemDecoration;
import com.jgkj.grb.onclick.OnItemClickListener;
import com.jgkj.grb.onclick.RxView;
import com.jgkj.grb.ui.mvp.shopcart.CartIndexBean;
import com.jgkj.grb.utils.ScreenUtils;

import java.util.List;

import butterknife.BindView;

/**
 * 购物车：主列表
 * Created by brightpoplar@163.com on 2019/7/29.
 */
public class ShoppingCartAdapter extends RecyclerView.Adapter {

    private Context mContext;
    private List<CartIndexBean> mList;
    private LayoutInflater mLayoutInflater;
    private OnItemCheckedChangeListener mOnItemCheckedChangeListener;

    public void setOnItemCheckedChangeListener(OnItemCheckedChangeListener mOnItemClickListener) {
        this.mOnItemCheckedChangeListener = mOnItemClickListener;
    }

    public interface OnItemCheckedChangeListener extends OnItemClickListener {
        void onCheckedChange(int parentPos, int childPos, int num, float price, float pv);

        void onNumCut(int parentPos, int childPos, int num, float price, float pv);

        void onNumAdd(int parentPos, int childPos, int num, float price, float pv);

        void onSecurities(int position);

        void onSpecs(int position, int childPos);

        void onItemClick(View v, int position, int childPos);
    }

    public ShoppingCartAdapter(Context mContext, List<CartIndexBean> mList) {
        this.mContext = mContext;
        this.mList = mList;
        this.mLayoutInflater = LayoutInflater.from(mContext);
    }

    @Override
    public int getItemViewType(int position) {
        return 0;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        if (viewType == 0) {
            return new ShoppingCartViewHolder(mLayoutInflater.inflate(R.layout.layout_item_shop_cart, viewGroup, false));
        } else if (viewType == 1) {
            return new ShoppingCartInvalidViewHolder(mLayoutInflater.inflate(R.layout.layout_item_shop_cart_invalid, viewGroup, false));
        }
        return new ShoppingCartViewHolder(mLayoutInflater.inflate(R.layout.layout_item_shop_cart, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {
        if (viewHolder instanceof ShoppingCartViewHolder) {
            ((ShoppingCartViewHolder) viewHolder).bindView(mList.get(position), position);
        } else if (viewHolder instanceof ShoppingCartInvalidViewHolder) {
            if (null != mOnItemCheckedChangeListener) {
                viewHolder.itemView.setOnClickListener(v -> {
                    mOnItemCheckedChangeListener.onItemClick(v, position);
                });
            }

            ((ShoppingCartInvalidViewHolder) viewHolder).bindView(mList.get(position), position);
        }
    }

    @Override
    public int getItemCount() {
        return null == mList ? 0 : mList.size();
    }

    /**
     * 正常购物车数据
     */
    class ShoppingCartViewHolder extends BaseViewHolder implements RxView.Action1 {
        @BindView(R.id.cart_shop_cb)
        CheckBox mCartShopCb;
        @BindView(R.id.cart_shop_name)
        TextView mCartShopName;
        @BindView(R.id.cart_shop_securities)
        TextView mCartShopSecurities;
        @BindView(R.id.recycler_view)
        RecyclerView mRecyclerView;
        ShoppingCartChildAdapter mAdapter;
        CartIndexBean data;
        int position;

        public ShoppingCartViewHolder(View itemView) {
            super(itemView);
            mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
            mRecyclerView.addItemDecoration(new SpaceItemDecoration(ScreenUtils.dp2px(mContext, 1)));
        }

        public void bindView(CartIndexBean bean, int pos) {
            data = bean;
            position = pos;

            mAdapter = new ShoppingCartChildAdapter(mContext, data.getList());
            mRecyclerView.setAdapter(mAdapter);
            mAdapter.setOnItemClickListener(new ShoppingCartChildAdapter.OnItemOperationListener() {
                @Override
                public void onCheckedChange(int pos, int num, float price, float pv) {
                    if (null != mOnItemCheckedChangeListener) {
                        mOnItemCheckedChangeListener.onCheckedChange(position, pos, num, price, pv);
                    }
                    int size = data.getList().size();
                    int selectCount = 0;
                    for (int i = 0; i < size; i++) {
                        if (data.getList().get(i).isSelect()) {
                            ++selectCount;
                        }
                    }
                    data.setSelect(size == selectCount);
                    notifyDataSetChanged();
                }

                @Override
                public void onNumCut(int pos, int num, float price, float pv) {
                    if (null != mOnItemCheckedChangeListener) {
                        mOnItemCheckedChangeListener.onNumCut(position, pos, num, price, pv);
                    }
                }

                @Override
                public void onNumAdd(int pos, int num, float price, float pv) {
                    if (null != mOnItemCheckedChangeListener) {
                        mOnItemCheckedChangeListener.onNumAdd(position, pos, num, price, pv);
                    }
                }

                @Override
                public void onSpecs(int childPos) {
                    if (null != mOnItemCheckedChangeListener) {
                        //mOnItemCheckedChangeListener.onSpecs(mPosition, childPos);
                    }
                }

                @Override
                public void onItemClick(View view, int postion) {
                    if (null != mOnItemCheckedChangeListener) {
                        mOnItemCheckedChangeListener.onItemClick(view, position, postion);
                    }
                }
            });

            RxView.setOnClickListeners(this, mCartShopCb, mCartShopSecurities);

            mCartShopCb.setChecked(data.isSelect());
            mCartShopName.setText(data.getSt_name());
        }

        @Override
        public void onClick(Object o) {
            View v = (View) o;
            switch (v.getId()) {
                case R.id.cart_shop_cb:
                    int size = data.getList().size();
                    int num = 0;
                    float price = 0f;
                    float pv = 0f;
                    if (!data.isSelect()) { // 全选：全部选中，已选的要跳过计算
                        for (int i = 0; i < size; i++) {
                            if (data.getList().get(i).isSelect())
                                continue;
                            data.getList().get(i).setSelect(true);
                            if (data.getList().get(i).getPaytype() == 2)
                                price = price + data.getList().get(i).getPd_num() * Float.parseFloat(/*data.getList().get(i).getPd_price()*/"0"); // num * price
                            pv = pv + data.getList().get(i).getPd_num() * Float.parseFloat(/*data.getList().get(i).getPd_pv()*/"0"); // num * price
                            num++;
                        }
                    } else { // 反全选：全部取消选中
                        for (int i = 0; i < size; i++) {
                            data.getList().get(i).setSelect(false);
                            if (data.getList().get(i).getPaytype() == 2)
                                price = price + data.getList().get(i).getPd_num() * Float.parseFloat(/*data.getList().get(i).getPd_price()*/"0"); // num * price
                            pv = pv + data.getList().get(i).getPd_num() * Float.parseFloat(/*data.getList().get(i).getPd_pv()*/"0"); // num * price
                            num++;
                        }
                    }
                    data.setSelect(mCartShopCb.isChecked());
                    if (null != mOnItemCheckedChangeListener) {
                        if (!data.isSelect()) {
                            num = -num;
                            price = -price;
                            pv = -pv;
                        }
                        mOnItemCheckedChangeListener.onCheckedChange(position, -1, num, price, pv);
                    }
                    mAdapter.notifyDataSetChanged();
                    break;
                case R.id.cart_shop_securities:
                    if (null != mOnItemCheckedChangeListener) {
                        mOnItemCheckedChangeListener.onSecurities(position);
                    }
                    break;
                default:
                    break;
            }
        }
    }

    /**
     * 失效购物车数据
     */
    class ShoppingCartInvalidViewHolder extends BaseViewHolder {
        @BindView(R.id.cart_shop_invalid_num)
        TextView mCartShopInvalidNum;
        @BindView(R.id.cart_shop_emptying_failure)
        TextView mCartShopEmptyingFailure;
        @BindView(R.id.recycler_view)
        RecyclerView mRecyclerView;
        ShoppingCartInvalidAdapter mAdapter;

        public ShoppingCartInvalidViewHolder(View itemView) {
            super(itemView);
            mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
            mRecyclerView.addItemDecoration(new SpaceItemDecoration(ScreenUtils.dp2px(mContext, 1)));
        }

        public void bindView(CartIndexBean data, int postion) {
            mAdapter = new ShoppingCartInvalidAdapter(mContext, data.getList());
            mRecyclerView.setAdapter(mAdapter);
        }
    }

}
