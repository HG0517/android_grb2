package com.jgkj.grb.ui.mvp;

import java.util.List;

/**
 * 我的收藏
 * Created by brightpoplar@163.com on 2019/8/30.
 */
public class CollectModel {

    /**
     * code : 1
     * msg : 成功
     * time : 1567142409
     * data : [{"id":16,"us_id":1,"pd_id":119,"time":"2019-08-29","status":1,"pd_name":"大米 东北大米","pd_price":"500.00","pd_pv":"200.00","pd_head_pic":"/uploads/20190816/181e427911c72e589a76881a3e8ac2b9.png","num":1},{"id":12,"us_id":1,"pd_id":129,"time":"2019-08-20","status":1,"pd_name":"稻香大米","pd_price":"500.00","pd_pv":"200.00","pd_head_pic":"/uploads/20190803/71ec784f5edb7cbc20deda7eb1dbfc1d.png","num":1}]
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
         * id : 16
         * us_id : 1
         * pd_id : 119
         * time : 2019-08-29
         * status : 1
         * pd_name : 大米 东北大米
         * pd_price : 500.00
         * pd_pv : 200.00
         * pd_head_pic : /uploads/20190816/181e427911c72e589a76881a3e8ac2b9.png
         * num : 1
         */

        private int id;
        private int us_id;
        private int pd_id;
        private String time;
        private int status;
        private String pd_name;
        private String pd_price;
        private String pd_pv;
        private String pd_head_pic;
        private int num;
        private String pd_total;
        private String pd_market_price;
        private String pd_grc;
        private String pd_grb;
        private int paytype;
        private int paytype2;
        private int paytype1;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getUs_id() {
            return us_id;
        }

        public void setUs_id(int us_id) {
            this.us_id = us_id;
        }

        public int getPd_id() {
            return pd_id;
        }

        public void setPd_id(int pd_id) {
            this.pd_id = pd_id;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
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

        public String getPd_pv() {
            return pd_pv;
        }

        public void setPd_pv(String pd_pv) {
            this.pd_pv = pd_pv;
        }

        public String getPd_head_pic() {
            return pd_head_pic;
        }

        public void setPd_head_pic(String pd_head_pic) {
            this.pd_head_pic = pd_head_pic;
        }

        public int getNum() {
            return num;
        }

        public void setNum(int num) {
            this.num = num;
        }

        public String getPd_total() {
            return pd_total;
        }

        public void setPd_total(String pd_total) {
            this.pd_total = pd_total;
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

        public int getPaytype() {
            return paytype;
        }

        public void setPaytype(int paytype) {
            this.paytype = paytype;
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
    }
}
