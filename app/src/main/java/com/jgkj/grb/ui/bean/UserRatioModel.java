package com.jgkj.grb.ui.bean;

/**
 * Created by brightpoplar@163.com on 2019/10/17.
 */
public class UserRatioModel {

    /**
     * code : 1
     * msg : 成功
     * time : 1571279189
     * data : {"grb_sum":"1001010.00000","grb_gra":2.03415972363E7}
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
         * grb_sum : 1001010.00000
         * grb_gra : 20341597.2363
         */

        private String grb_sum;
        private String grb_gra;
        private String msg;
        private String address;

        public String getGrb_sum() {
            return grb_sum;
        }

        public void setGrb_sum(String grb_sum) {
            this.grb_sum = grb_sum;
        }

        public String getGrb_gra() {
            return grb_gra;
        }

        public void setGrb_gra(String grb_gra) {
            this.grb_gra = grb_gra;
        }

        public String getMsg() {
            return msg;
        }

        public void setMsg(String msg) {
            this.msg = msg;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }
    }
}
