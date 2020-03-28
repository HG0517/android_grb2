package com.jgkj.grb.ui.mvp.index;

import java.io.Serializable;
import java.util.List;

/**
 * 首页：子分类下的商品数据列表
 * Created by brightpoplar@163.com on 2019/9/2.
 */
public class IndexCatesChildPageModel {

    /**
     * code : 1
     * msg : 成功
     * time : 1567395032
     * data : [{"id":130,"pd_name":"西红柿新鲜番茄自然熟非 有机水果","pd_price":"19.60","pd_status":5,"pd_inventory":100,"pd_sales":0,"pd_pic":"/uploads/20190828/05f416920c63bb4e7715151eee78f1ff.jpg,,/uploads/20190828/05f416920c63bb4e7715151eee78f1ff.jpg","pd_content":"","delete_time":null,"pd_add_time":"2019-08-28 10:40:56","ca_id":21,"pd_agency_price":"0.00","agency_status":2,"agency_num":10,"pd_detail":"","pd_province":null,"pd_place":null,"take_fee":"0.00","pd_color":null,"pd_date":null,"pd_band":null,"pd_spec":null,"pd_pd_com":null,"st_id":0,"st_status":1,"pd_head_pic":"/uploads/20190828/0e9c0634a9ecfe5aeb30ba9d43d14929.jpg","pd_code":null,"pd_pv":"969.00","limitime":null,"last":0},{"id":131,"pd_name":"新鲜水果小黄瓜5斤整箱 脆嫩荷兰小青瓜","pd_price":"26.30","pd_status":5,"pd_inventory":100,"pd_sales":0,"pd_pic":"/uploads/20190828/edd376293aabe6dae3e2b306002927e2.jpg,,/uploads/20190828/edd376293aabe6dae3e2b306002927e2.jpg","pd_content":"","delete_time":null,"pd_add_time":"2019-08-28 10:47:55","ca_id":21,"pd_agency_price":"0.00","agency_status":2,"agency_num":10,"pd_detail":"","pd_province":null,"pd_place":null,"take_fee":"0.00","pd_color":null,"pd_date":null,"pd_band":null,"pd_spec":null,"pd_pd_com":null,"st_id":0,"st_status":1,"pd_head_pic":"/uploads/20190828/94e31a86de680378a40198e8b42b4cb5.jpg","pd_code":null,"pd_pv":"953.80","limitime":null,"last":0},{"id":132,"pd_name":"新鲜现摘芸豆带箱3斤装 山东特产","pd_price":"45.80","pd_status":5,"pd_inventory":100,"pd_sales":0,"pd_pic":"/uploads/20190828/e348aa3b52e801d0f31a87b02032a81f.jpg,,/uploads/20190828/e348aa3b52e801d0f31a87b02032a81f.jpg","pd_content":"","delete_time":null,"pd_add_time":"2019-08-28 10:49:13","ca_id":21,"pd_agency_price":"0.00","agency_status":2,"agency_num":10,"pd_detail":"","pd_province":null,"pd_place":null,"take_fee":"0.00","pd_color":null,"pd_date":null,"pd_band":null,"pd_spec":null,"pd_pd_com":null,"st_id":0,"st_status":1,"pd_head_pic":"/uploads/20190828/23ad70290b597055aa1815bff167daa0.jpg","pd_code":null,"pd_pv":"952.10","limitime":null,"last":0},{"id":133,"pd_name":"贝贝南瓜板栗味日本小南 瓜带箱6斤","pd_price":"66.60","pd_status":5,"pd_inventory":100,"pd_sales":0,"pd_pic":"/uploads/20190828/2a420e4bedbe3bdc95298b285d082b23.jpg,,/uploads/20190828/2a420e4bedbe3bdc95298b285d082b23.jpg","pd_content":"","delete_time":null,"pd_add_time":"2019-08-28 11:07:35","ca_id":21,"pd_agency_price":"0.00","agency_status":2,"agency_num":10,"pd_detail":"<p style=\"text-align:center\"><img src=\"/ueditor/php/upload/image/20190828/1566961650.png\" title=\"1566961650.png\" alt=\"detail.png\"/><\/p><p><br/><\/p>","pd_province":null,"pd_place":null,"take_fee":"0.00","pd_color":null,"pd_date":null,"pd_band":null,"pd_spec":null,"pd_pd_com":null,"st_id":0,"st_status":1,"pd_head_pic":"/uploads/20190828/715987cb00d9e8ebad5bb364865b4dd2.jpg","pd_code":null,"pd_pv":"951.20","limitime":null,"last":0}]
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
         * id : 130
         * pd_name : 西红柿新鲜番茄自然熟非 有机水果
         * pd_price : 19.60
         * pd_status : 5
         * pd_inventory : 100
         * pd_sales : 0
         * pd_pic : /uploads/20190828/05f416920c63bb4e7715151eee78f1ff.jpg,,/uploads/20190828/05f416920c63bb4e7715151eee78f1ff.jpg
         * pd_content :
         * delete_time : null
         * pd_add_time : 2019-08-28 10:40:56
         * ca_id : 21
         * pd_agency_price : 0.00
         * agency_status : 2
         * agency_num : 10
         * pd_detail :
         * pd_province : null
         * pd_place : null
         * take_fee : 0.00
         * pd_color : null
         * pd_date : null
         * pd_band : null
         * pd_spec : null
         * pd_pd_com : null
         * st_id : 0
         * st_status : 1
         * pd_head_pic : /uploads/20190828/0e9c0634a9ecfe5aeb30ba9d43d14929.jpg
         * pd_code : null
         * pd_pv : 969.00
         * limitime : null
         * last : 0
         * paytype: 1
         * advance : 0000-00-00 00:00:00
         * p_id : 0
         * ca_name : 水果
         * ca_status : 1
         * ca_add_time : 2018-08-24 10:20:10
         * ca_sort : 20
         * ca_pic : /uploads/20190527/5a0ee270c5c7566ae3583ecba2d6863b.png
         * ca_ca : 0
         * pd_market_price : 0.00
         * pd_grc : 0.00000
         * pd_grb : 0.00000
         * paytype2 : 0
         * paytype1 : 0
         * pd_total :
         */

        private int id;
        private String pd_name;
        private String pd_price;
        private int pd_status;
        private int pd_inventory;
        private int pd_sales;
        private String pd_pic;
        private String pd_content;
        private String delete_time;
        private String pd_add_time;
        private int ca_id;
        private String pd_agency_price;
        private int agency_status;
        private int agency_num;
        private String pd_detail;
        private String pd_province;
        private String pd_place;
        private String take_fee;
        private String pd_color;
        private String pd_date;
        private String pd_band;
        private String pd_spec;
        private String pd_pd_com;
        private int st_id;
        private int st_status;
        private String pd_head_pic;
        private String pd_code;
        private String pd_pv;
        private String limitime;
        private int last;
        private int paytype;
        private String advance;
        private int p_id;
        private String ca_name;
        private int ca_status;
        private String ca_add_time;
        private int ca_sort;
        private String ca_pic;
        private int ca_ca;
        private String pd_market_price;
        private String pd_grc;
        private String pd_grb;
        private int paytype2;
        private int paytype1;
        private String pd_total;

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

        public String getDelete_time() {
            return delete_time;
        }

        public void setDelete_time(String delete_time) {
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

        public String getPd_province() {
            return pd_province;
        }

        public void setPd_province(String pd_province) {
            this.pd_province = pd_province;
        }

        public String getPd_place() {
            return pd_place;
        }

        public void setPd_place(String pd_place) {
            this.pd_place = pd_place;
        }

        public String getTake_fee() {
            return take_fee;
        }

        public void setTake_fee(String take_fee) {
            this.take_fee = take_fee;
        }

        public String getPd_color() {
            return pd_color;
        }

        public void setPd_color(String pd_color) {
            this.pd_color = pd_color;
        }

        public String getPd_date() {
            return pd_date;
        }

        public void setPd_date(String pd_date) {
            this.pd_date = pd_date;
        }

        public String getPd_band() {
            return pd_band;
        }

        public void setPd_band(String pd_band) {
            this.pd_band = pd_band;
        }

        public String getPd_spec() {
            return pd_spec;
        }

        public void setPd_spec(String pd_spec) {
            this.pd_spec = pd_spec;
        }

        public String getPd_pd_com() {
            return pd_pd_com;
        }

        public void setPd_pd_com(String pd_pd_com) {
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

        public String getPd_code() {
            return pd_code;
        }

        public void setPd_code(String pd_code) {
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

        public int getPaytype() {
            return paytype;
        }

        public void setPaytype(int paytype) {
            this.paytype = paytype;
        }

        public String getAdvance() {
            return advance;
        }

        public void setAdvance(String advance) {
            this.advance = advance;
        }

        public int getP_id() {
            return p_id;
        }

        public void setP_id(int p_id) {
            this.p_id = p_id;
        }

        public String getCa_name() {
            return ca_name;
        }

        public void setCa_name(String ca_name) {
            this.ca_name = ca_name;
        }

        public int getCa_status() {
            return ca_status;
        }

        public void setCa_status(int ca_status) {
            this.ca_status = ca_status;
        }

        public String getCa_add_time() {
            return ca_add_time;
        }

        public void setCa_add_time(String ca_add_time) {
            this.ca_add_time = ca_add_time;
        }

        public int getCa_sort() {
            return ca_sort;
        }

        public void setCa_sort(int ca_sort) {
            this.ca_sort = ca_sort;
        }

        public String getCa_pic() {
            return ca_pic;
        }

        public void setCa_pic(String ca_pic) {
            this.ca_pic = ca_pic;
        }

        public int getCa_ca() {
            return ca_ca;
        }

        public void setCa_ca(int ca_ca) {
            this.ca_ca = ca_ca;
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

        public String getPd_total() {
            return pd_total;
        }

        public void setPd_total(String pd_total) {
            this.pd_total = pd_total;
        }
    }
}
