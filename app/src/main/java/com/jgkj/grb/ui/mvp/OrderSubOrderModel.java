package com.jgkj.grb.ui.mvp;

import android.text.TextUtils;

/**
 * 支付订单：立即下单
 * Created by brightpoplar@163.com on 2019/9/8.
 */
public class OrderSubOrderModel {

    /**
     * code : 1
     * msg : 成功
     * time : 1567935790
     * data : {"cash":"0.00","grb":740,"total":0,"fee":0,"all_pv":600.4}
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
         * us_cash : 25976.76
         * us_grb : 96400.00000
         * us_grc : 9969921471.99999
         * total : 0
         * fee : 10
         * grb : 0
         * grc : 0
         * const : 70
         */

        private String cash;
        private String grb;
        private String total;
        private String fee;
        private String all_pv;
        private String us_cash;
        private String us_grb;
        private String us_grc;
        private String grc;

        public String getCash() {
            return cash;
        }

        public void setCash(String cash) {
            this.cash = cash;
        }

        public String getGrb() {
            return grb;
        }

        public void setGrb(String grb) {
            this.grb = grb;
        }

        public String getTotal() {
            return total;
        }

        public void setTotal(String total) {
            this.total = total;
        }

        public String getFee() {
            return fee;
        }

        public void setFee(String fee) {
            this.fee = fee;
        }

        public String getAll_pv() {
            return all_pv;
        }

        public void setAll_pv(String all_pv) {
            this.all_pv = all_pv;
        }

        public String getUs_cash() {
            return us_cash;
        }

        public void setUs_cash(String us_cash) {
            this.us_cash = us_cash;
        }

        public String getUs_grb() {
            return us_grb;
        }

        public void setUs_grb(String us_grb) {
            this.us_grb = us_grb;
        }

        public String getUs_grc() {
            return us_grc;
        }

        public void setUs_grc(String us_grc) {
            this.us_grc = us_grc;
        }

        public String getGrc() {
            return grc;
        }

        public void setGrc(String grc) {
            this.grc = grc;
        }
    }
}
