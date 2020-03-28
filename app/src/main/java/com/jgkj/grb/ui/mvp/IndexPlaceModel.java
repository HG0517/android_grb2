package com.jgkj.grb.ui.mvp;

import java.io.Serializable;
import java.util.List;

/**
 * 首页：基地，标签列表
 * Created by brightpoplar@163.com on 2019/9/12.
 */
public class IndexPlaceModel {

    /**
     * code : 1
     * msg : 成功
     * time : 1568269090
     * data : [{"area_id":4,"area_name":"福建"},{"area_id":2,"area_name":"北京"}]
     */

    private int code;
    private String msg;
    private int time;
    private List<DataBean> data;

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

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean implements Serializable {
        /**
         * area_id : 4
         * area_name : 福建
         */

        private int area_id;
        private String area_name;

        public int getArea_id() {
            return area_id;
        }

        public void setArea_id(int area_id) {
            this.area_id = area_id;
        }

        public String getArea_name() {
            return area_name;
        }

        public void setArea_name(String area_name) {
            this.area_name = area_name;
        }
    }
}
