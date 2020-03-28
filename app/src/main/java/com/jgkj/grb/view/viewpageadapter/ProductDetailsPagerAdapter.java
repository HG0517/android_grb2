package com.jgkj.grb.view.viewpageadapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import com.jgkj.grb.R;
import com.jgkj.grb.glide.GlideApp;
import com.jgkj.grb.retrofit.ApiStores;

import java.util.List;

/**
 * 商品详情顶部 ViewPager 数据适配器
 * ViewPagerAdapter
 * Created by brightpoplar@163.com on 2019/8/8.
 */
public class ProductDetailsPagerAdapter extends AbstractViewPagerAdapter<String> {

    public ProductDetailsPagerAdapter(Context context, List<String> data) {
        super(context, data);
    }

    @Override
    public View newView(String bean, int position) {
        View itemView = View.inflate(mContext, R.layout.layout_item_product_details, null);
        ImageView itemImage = itemView.findViewById(R.id.item_image);
        GlideApp.with(mContext).load(bean.startsWith("http:") || bean.startsWith("https:")
                ? bean : ApiStores.API_SERVER_URL + bean).into(itemImage);
        return itemView;
    }
}
