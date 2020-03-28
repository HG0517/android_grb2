package com.jgkj.grb.base;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import butterknife.ButterKnife;

/**
 * RecyclerView  BaseViewHolder ：绑定 ButterKnife
 * Created by jugekeji on 2018/10/9.
 */

public class BaseViewHolder extends RecyclerView.ViewHolder {
    View itemView;

    public BaseViewHolder(View itemView) {
        super(itemView);
        this.itemView = itemView;
        ButterKnife.bind(this, itemView);
    }

    public View getItemView() {
        return itemView;
    }
}
