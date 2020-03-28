package com.jgkj.grb.ui.bean;

import com.jgkj.grb.ui.mvp.product.ProductDetailsModel;

import java.io.Serializable;
import java.util.List;

/**
 * 购物车：规格
 * Created by brightpoplar@163.com on 2019/8/1.
 */
public class SpecsModel implements Serializable {
    private List<SpecsBean> list;

    public static class SpecsBean implements Serializable {
        private int id;
        private String name;
        private List<ProductDetailsModel.DataBean.SkusBean> list;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public List<ProductDetailsModel.DataBean.SkusBean> getList() {
            return list;
        }

        public void setList(List<ProductDetailsModel.DataBean.SkusBean> list) {
            this.list = list;
        }
    }

    public List<SpecsBean> getList() {
        return list;
    }

    public void setList(List<SpecsBean> list) {
        this.list = list;
    }
}
