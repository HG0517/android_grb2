package com.jgkj.grb.ui.mvp.index;

import java.util.List;

/**
 * 首页：搜索
 * Created by brightpoplar@163.com on 2019/9/17.
 */
public class IndexSearchModel {

    /**
     * code : 1
     * msg : 成功
     * time : 1568712269
     * data : {"total":1,"per_page":15,"current_page":"1","last_page":1,"data":[{"id":133,"pd_name":"贝贝南瓜板栗味日本小南 瓜带箱6斤","pd_price":"66.60000","pd_status":5,"pd_inventory":100,"pd_sales":0,"pd_pic":["/uploads/20190828/2a420e4bedbe3bdc95298b285d082b23.jpg","","/uploads/20190828/2a420e4bedbe3bdc95298b285d082b23.jpg"],"pd_content":"","delete_time":null,"pd_add_time":"2019-08-28 11:07:35","ca_id":21,"pd_agency_price":"0.00","agency_status":2,"agency_num":10,"pd_detail":"<p style=\"text-align:center\"><img src=\"/ueditor/php/upload/image/20190828/1566961650.png\" title=\"1566961650.png\" alt=\"detail.png\"/><\/p><p><br/><\/p>","pd_province":null,"pd_place":"郑州","take_fee":"0.00","pd_color":null,"pd_date":null,"pd_band":null,"pd_spec":null,"pd_pd_com":null,"st_id":0,"st_status":1,"pd_head_pic":"/uploads/20190828/715987cb00d9e8ebad5bb364865b4dd2.jpg","pd_code":null,"pd_pv":"951.20000","limitime":null,"last":0,"paytype":1,"ca_name":"果菜","p_id":20}]}
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
        /**
         * total : 1
         * per_page : 15
         * current_page : 1
         * last_page : 1
         * data : [{"id":133,"pd_name":"贝贝南瓜板栗味日本小南 瓜带箱6斤","pd_price":"66.60000","pd_status":5,"pd_inventory":100,"pd_sales":0,"pd_pic":["/uploads/20190828/2a420e4bedbe3bdc95298b285d082b23.jpg","","/uploads/20190828/2a420e4bedbe3bdc95298b285d082b23.jpg"],"pd_content":"","delete_time":null,"pd_add_time":"2019-08-28 11:07:35","ca_id":21,"pd_agency_price":"0.00","agency_status":2,"agency_num":10,"pd_detail":"<p style=\"text-align:center\"><img src=\"/ueditor/php/upload/image/20190828/1566961650.png\" title=\"1566961650.png\" alt=\"detail.png\"/><\/p><p><br/><\/p>","pd_province":null,"pd_place":"郑州","take_fee":"0.00","pd_color":null,"pd_date":null,"pd_band":null,"pd_spec":null,"pd_pd_com":null,"st_id":0,"st_status":1,"pd_head_pic":"/uploads/20190828/715987cb00d9e8ebad5bb364865b4dd2.jpg","pd_code":null,"pd_pv":"951.20000","limitime":null,"last":0,"paytype":1,"ca_name":"果菜","p_id":20}]
         */

        private int total;
        private int per_page;
        private String current_page;
        private int last_page;
        private List<GoodBeanModel> data;

        public int getTotal() {
            return total;
        }

        public void setTotal(int total) {
            this.total = total;
        }

        public int getPer_page() {
            return per_page;
        }

        public void setPer_page(int per_page) {
            this.per_page = per_page;
        }

        public String getCurrent_page() {
            return current_page;
        }

        public void setCurrent_page(String current_page) {
            this.current_page = current_page;
        }

        public int getLast_page() {
            return last_page;
        }

        public void setLast_page(int last_page) {
            this.last_page = last_page;
        }

        public List<GoodBeanModel> getData() {
            return data;
        }

        public void setData(List<GoodBeanModel> data) {
            this.data = data;
        }
    }
}
