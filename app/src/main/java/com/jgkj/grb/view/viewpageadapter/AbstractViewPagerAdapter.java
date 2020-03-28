package com.jgkj.grb.view.viewpageadapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * ViewPagerAdapter
 * Created by brightpoplar@163.com on 2019/8/8.
 */
public abstract class AbstractViewPagerAdapter<T> extends PagerAdapter {
    protected Context mContext;
    private List<T> mData;
    private SparseArray<View> mViews;

    public AbstractViewPagerAdapter(Context context, List<T> data) {
        mContext = context;
        mData = data;
        mViews = new SparseArray<View>(data.size());
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view = mViews.get(position);
        if (view == null) {
            view = newView(getItem(position), position);
            mViews.put(position, view);
        }
        container.addView(view);
        return view;
    }

    protected abstract View newView(T data, int position);

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(mViews.get(position));
    }

    private T getItem(int position) {
        return mData.get(position);
    }
}
