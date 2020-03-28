package com.jgkj.grb.ui.mvp.index;

import java.util.List;

/**
 * 首页：主分类列表：推荐......
 * Created by brightpoplar@163.com on 2019/8/29.
 */
public class IndexCatesListModel {

    /**
     * code : 1
     * msg : 成功
     * time : 1567066786
     * data : {"cates":[{"id":20,"ca_name":"蔬菜"},{"id":27,"ca_name":"推荐"},{"id":4,"ca_name":"农药"},{"id":5,"ca_name":"水果"},{"id":6,"ca_name":"粮油"}]}
     */

    private int code;
    private String msg;
    private int time;
    private DataBean data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        private List<IndexCatesBean> cates;

        public List<IndexCatesBean> getCates() {
            return cates;
        }

        public void setCates(List<IndexCatesBean> cates) {
            this.cates = cates;
        }
    }
}
