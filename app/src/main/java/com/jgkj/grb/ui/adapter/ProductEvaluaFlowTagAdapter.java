package com.jgkj.grb.ui.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.jgkj.grb.R;
import com.jgkj.grb.ui.mvp.product.ProductDetailsModel;
import com.jgkj.grb.view.flowtag.OnInitSelectedPosition;

import java.util.ArrayList;
import java.util.List;

/**
 * 商品评价
 */
public class ProductEvaluaFlowTagAdapter<T> extends BaseAdapter implements OnInitSelectedPosition {

    private final Context mContext;
    private final List<T> mDataList;

    public ProductEvaluaFlowTagAdapter(Context context) {
        this.mContext = context;
        mDataList = new ArrayList<>();
    }

    public List<T> getDataList() {
        return mDataList;
    }

    @Override
    public int getCount() {
        return mDataList.size();
    }

    @Override
    public Object getItem(int position) {
        return mDataList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @SuppressLint("ViewHolder")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        convertView = LayoutInflater.from(mContext).inflate(R.layout.layout_flowtag_item_pro_evalua, null);

        TextView textView = convertView.findViewById(R.id.tv_tag);

        T t = mDataList.get(position);

        if (t instanceof String) {
            textView.setText((String) t);

        } else if (t instanceof ProductDetailsModel.DataBean.SkusBean) {
            textView.setText(((ProductDetailsModel.DataBean.SkusBean) t).getSku_name());
        }
        return convertView;
    }

    public void onlyAddAll(List<T> datas) {
        mDataList.addAll(datas);
        notifyDataSetChanged();
    }

    public void clearAndAddAll(List<T> datas) {
        mDataList.clear();
        onlyAddAll(datas);
    }

    @Override
    public boolean isSelectedPosition(int position) {
        // 添加初始化选中标签
        return position == 0;
    }
}
