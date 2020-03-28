package com.jgkj.grb.ui.mvp.personal;

import java.util.List;

/**
 * 个人中心：我的优惠券
 * Created by brightpoplar@163.com on 2019/9/18.
 */
public class CouponModel {

    /**
     * code : 1
     * msg : 成功
     * time : 1568792408
     * data : {"list":[{"p_id":119,"id":1,"start":"2019-08-10","end":"2019-08-12","price":68,"bouns":5},{"p_id":0,"id":6,"start":"2019-09-01","end":"2019-09-01","price":1000,"bouns":200},{"p_id":0,"id":6,"start":"2019-09-01","end":"2019-09-01","price":1000,"bouns":200},{"p_id":0,"id":6,"start":"2019-09-01","end":"2019-09-01","price":1000,"bouns":200},{"p_id":0,"id":6,"start":"2019-09-01","end":"2019-09-01","price":1000,"bouns":200},{"p_id":0,"id":6,"start":"2019-09-01","end":"2019-09-01","price":1000,"bouns":200},{"p_id":0,"id":6,"start":"2019-09-01","end":"2019-09-01","price":1000,"bouns":200},{"p_id":0,"id":6,"start":"2019-09-01","end":"2019-09-01","price":1000,"bouns":200},{"p_id":0,"id":6,"start":"2019-09-01","end":"2019-09-01","price":1000,"bouns":200},{"p_id":0,"id":6,"start":"2019-09-01","end":"2019-09-01","price":1000,"bouns":200}]}
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
        private List<ListBean> list;

        public List<ListBean> getList() {
            return list;
        }

        public void setList(List<ListBean> list) {
            this.list = list;
        }

        public static class ListBean {
            /**
             * p_id : 119
             * id : 1
             * start : 2019-08-10
             * end : 2019-08-12
             * price : 68
             * bouns : 5
             */

            private int p_id;
            private int id;
            private String start;
            private String end;
            private int price;
            private int bouns;

            public int getP_id() {
                return p_id;
            }

            public void setP_id(int p_id) {
                this.p_id = p_id;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getStart() {
                return start;
            }

            public void setStart(String start) {
                this.start = start;
            }

            public String getEnd() {
                return end;
            }

            public void setEnd(String end) {
                this.end = end;
            }

            public int getPrice() {
                return price;
            }

            public void setPrice(int price) {
                this.price = price;
            }

            public int getBouns() {
                return bouns;
            }

            public void setBouns(int bouns) {
                this.bouns = bouns;
            }
        }
    }
}
