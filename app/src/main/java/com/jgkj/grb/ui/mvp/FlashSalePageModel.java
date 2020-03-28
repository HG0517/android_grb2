package com.jgkj.grb.ui.mvp;

import java.util.List;

/**
 * 限时抢购：page 页列表数据
 * Created by brightpoplar@163.com on 2019/8/30.
 */
public class FlashSalePageModel {

    /**
     * code : 1
     * msg : 成功
     * time : 1567147071
     * data : [{"id":129,"pd_name":"稻香大米","pd_price":"500.00","pd_status":2,"pd_inventory":100,"pd_sales":0,"pd_pic":"/uploads/20190817/5b1c81a8aa03614dafce6f92aaabeef2.png","pd_content":"稻香大米，大米中的战斗米","delete_time":null,"pd_add_time":"2019-08-28 15:13:25","ca_id":32,"pd_agency_price":"0.00","agency_status":2,"agency_num":10,"pd_detail":"<p style=\"text-align:center\"><\/p><p style=\"text-align:center\"><img src=\"/ueditor/php/upload/image/20190828/1566976400.png\" title=\"1566976400.png\" alt=\"detail.png\"/><\/p><p><br/><\/p>","pd_place":null,"take_fee":"0.00","pd_color":null,"pd_date":null,"pd_band":null,"pd_spec":null,"pd_pd_com":null,"st_id":0,"st_status":1,"pd_head_pic":"/uploads/20190803/71ec784f5edb7cbc20deda7eb1dbfc1d.png","pd_code":null,"pd_pv":"200.00","limitime":"1","last":3},{"id":135,"pd_name":"哈密瓜","pd_price":"130.00","pd_status":2,"pd_inventory":100,"pd_sales":0,"pd_pic":"/uploads/20190829/c9a16eaf95beb051dcb7ce545c64e913.jpg,,/uploads/20190829/c9a16eaf95beb051dcb7ce545c64e913.jpg","pd_content":"","delete_time":null,"pd_add_time":"2019-08-29 18:39:01","ca_id":32,"pd_agency_price":"0.00","agency_status":2,"agency_num":10,"pd_detail":"<p style=\"text-align:center\"><img src=\"/ueditor/php/upload/image/20190829/1567075136.png\" title=\"1567075136.png\" alt=\"detail.png\"/><\/p><p><br/><\/p>","pd_place":null,"take_fee":"0.00","pd_color":null,"pd_date":null,"pd_band":null,"pd_spec":null,"pd_pd_com":null,"st_id":0,"st_status":1,"pd_head_pic":"/uploads/20190829/c8d5a034b2f77f0cc089736accb8f6af.jpg","pd_code":null,"pd_pv":"200.00","limitime":"1","last":2}]
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

    public static class DataBean {
        /**
         * id : 129
         * pd_name : 稻香大米
         * pd_price : 500.00
         * pd_status : 2
         * pd_inventory : 100
         * pd_sales : 0
         * pd_pic : /uploads/20190817/5b1c81a8aa03614dafce6f92aaabeef2.png
         * pd_content : 稻香大米，大米中的战斗米
         * delete_time : null
         * pd_add_time : 2019-08-28 15:13:25
         * ca_id : 32
         * pd_agency_price : 0.00
         * agency_status : 2
         * agency_num : 10
         * pd_detail : <p style="text-align:center"></p><p style="text-align:center"><img src="/ueditor/php/upload/image/20190828/1566976400.png" title="1566976400.png" alt="detail.png"/></p><p><br/></p>
         * pd_place : null
         * take_fee : 0.00
         * pd_color : null
         * pd_date : null
         * pd_band : null
         * pd_spec : null
         * pd_pd_com : null
         * st_id : 0
         * st_status : 1
         * pd_head_pic : /uploads/20190803/71ec784f5edb7cbc20deda7eb1dbfc1d.png
         * pd_code : null
         * pd_pv : 200.00
         * limitime : 1
         * last : 3
         * pd_market_price : 0.00
         * pd_grc : 0.00000
         * pd_grb : 0.00000
         * pd_total : 200.00000
         * paytype2 : 0
         * paytype1 : 0
         * paytype : 1
         */

        private int id;
        private String pd_name;
        private String pd_price;
        private int pd_status;
        private int pd_inventory;
        private int pd_sales;
        private String pd_pic;
        private String pd_content;
        private Object delete_time;
        private String pd_add_time;
        private int ca_id;
        private String pd_agency_price;
        private int agency_status;
        private int agency_num;
        private String pd_detail;
        private Object pd_place;
        private String take_fee;
        private Object pd_color;
        private Object pd_date;
        private Object pd_band;
        private Object pd_spec;
        private Object pd_pd_com;
        private int st_id;
        private int st_status;
        private String pd_head_pic;
        private Object pd_code;
        private String pd_pv;
        private String limitime;
        private int last;
        private String pd_market_price;
        private String pd_grc;
        private String pd_grb;
        private String pd_total;
        private int paytype2;
        private int paytype1;
        private int paytype;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getPd_name() {
            return pd_name;
        }

        public void setPd_name(String pd_name) {
            this.pd_name = pd_name;
        }

        public String getPd_price() {
            return pd_price;
        }

        public void setPd_price(String pd_price) {
            this.pd_price = pd_price;
        }

        public int getPd_status() {
            return pd_status;
        }

        public void setPd_status(int pd_status) {
            this.pd_status = pd_status;
        }

        public int getPd_inventory() {
            return pd_inventory;
        }

        public void setPd_inventory(int pd_inventory) {
            this.pd_inventory = pd_inventory;
        }

        public int getPd_sales() {
            return pd_sales;
        }

        public void setPd_sales(int pd_sales) {
            this.pd_sales = pd_sales;
        }

        public String getPd_pic() {
            return pd_pic;
        }

        public void setPd_pic(String pd_pic) {
            this.pd_pic = pd_pic;
        }

        public String getPd_content() {
            return pd_content;
        }

        public void setPd_content(String pd_content) {
            this.pd_content = pd_content;
        }

        public Object getDelete_time() {
            return delete_time;
        }

        public void setDelete_time(Object delete_time) {
            this.delete_time = delete_time;
        }

        public String getPd_add_time() {
            return pd_add_time;
        }

        public void setPd_add_time(String pd_add_time) {
            this.pd_add_time = pd_add_time;
        }

        public int getCa_id() {
            return ca_id;
        }

        public void setCa_id(int ca_id) {
            this.ca_id = ca_id;
        }

        public String getPd_agency_price() {
            return pd_agency_price;
        }

        public void setPd_agency_price(String pd_agency_price) {
            this.pd_agency_price = pd_agency_price;
        }

        public int getAgency_status() {
            return agency_status;
        }

        public void setAgency_status(int agency_status) {
            this.agency_status = agency_status;
        }

        public int getAgency_num() {
            return agency_num;
        }

        public void setAgency_num(int agency_num) {
            this.agency_num = agency_num;
        }

        public String getPd_detail() {
            return pd_detail;
        }

        public void setPd_detail(String pd_detail) {
            this.pd_detail = pd_detail;
        }

        public Object getPd_place() {
            return pd_place;
        }

        public void setPd_place(Object pd_place) {
            this.pd_place = pd_place;
        }

        public String getTake_fee() {
            return take_fee;
        }

        public void setTake_fee(String take_fee) {
            this.take_fee = take_fee;
        }

        public Object getPd_color() {
            return pd_color;
        }

        public void setPd_color(Object pd_color) {
            this.pd_color = pd_color;
        }

        public Object getPd_date() {
            return pd_date;
        }

        public void setPd_date(Object pd_date) {
            this.pd_date = pd_date;
        }

        public Object getPd_band() {
            return pd_band;
        }

        public void setPd_band(Object pd_band) {
            this.pd_band = pd_band;
        }

        public Object getPd_spec() {
            return pd_spec;
        }

        public void setPd_spec(Object pd_spec) {
            this.pd_spec = pd_spec;
        }

        public Object getPd_pd_com() {
            return pd_pd_com;
        }

        public void setPd_pd_com(Object pd_pd_com) {
            this.pd_pd_com = pd_pd_com;
        }

        public int getSt_id() {
            return st_id;
        }

        public void setSt_id(int st_id) {
            this.st_id = st_id;
        }

        public int getSt_status() {
            return st_status;
        }

        public void setSt_status(int st_status) {
            this.st_status = st_status;
        }

        public String getPd_head_pic() {
            return pd_head_pic;
        }

        public void setPd_head_pic(String pd_head_pic) {
            this.pd_head_pic = pd_head_pic;
        }

        public Object getPd_code() {
            return pd_code;
        }

        public void setPd_code(Object pd_code) {
            this.pd_code = pd_code;
        }

        public String getPd_pv() {
            return pd_pv;
        }

        public void setPd_pv(String pd_pv) {
            this.pd_pv = pd_pv;
        }

        public String getLimitime() {
            return limitime;
        }

        public void setLimitime(String limitime) {
            this.limitime = limitime;
        }

        public int getLast() {
            return last;
        }

        public void setLast(int last) {
            this.last = last;
        }

        public String getPd_market_price() {
            return pd_market_price;
        }

        public void setPd_market_price(String pd_market_price) {
            this.pd_market_price = pd_market_price;
        }

        public String getPd_grc() {
            return pd_grc;
        }

        public void setPd_grc(String pd_grc) {
            this.pd_grc = pd_grc;
        }

        public String getPd_grb() {
            return pd_grb;
        }

        public void setPd_grb(String pd_grb) {
            this.pd_grb = pd_grb;
        }

        public String getPd_total() {
            return pd_total;
        }

        public void setPd_total(String pd_total) {
            this.pd_total = pd_total;
        }

        public int getPaytype2() {
            return paytype2;
        }

        public void setPaytype2(int paytype2) {
            this.paytype2 = paytype2;
        }

        public int getPaytype1() {
            return paytype1;
        }

        public void setPaytype1(int paytype1) {
            this.paytype1 = paytype1;
        }

        public int getPaytype() {
            return paytype;
        }

        public void setPaytype(int paytype) {
            this.paytype = paytype;
        }
    }
}
