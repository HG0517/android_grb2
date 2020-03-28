package com.jgkj.grb.ui.mvp.personal;

/**
 * 个人中心：GRB
 * Created by brightpoplar@163.com on 2019/9/2.
 */
public class PersonalGRBModel {

    /**
     * code : 1
     * msg : 成功
     * time : 1567407765
     * data : {"grb_cash":"180.00000","grb_integral":"0.00000","total":"180.00000","all":"12.50000","grb":"1.0000","grbtime":"2019-08-31 15:53:13"}
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
         * grb_cash : 180.00000
         * grb_integral : 0.00000
         * total : 180.00000
         * all : 12.50000
         * grb : 1.0000
         * grbtime : 2019-08-31 15:53:13
         * gra :
         */

        private String grb_cash;
        private String grb_integral;
        private String total;
        private String all;
        private String grb;
        private String grbtime;
        private String gra;

        public String getGrb_cash() {
            return grb_cash;
        }

        public void setGrb_cash(String grb_cash) {
            this.grb_cash = grb_cash;
        }

        public String getGrb_integral() {
            return grb_integral;
        }

        public void setGrb_integral(String grb_integral) {
            this.grb_integral = grb_integral;
        }

        public String getTotal() {
            return total;
        }

        public void setTotal(String total) {
            this.total = total;
        }

        public String getAll() {
            return all;
        }

        public void setAll(String all) {
            this.all = all;
        }

        public String getGrb() {
            return grb;
        }

        public void setGrb(String grb) {
            this.grb = grb;
        }

        public String getGrbtime() {
            return grbtime;
        }

        public void setGrbtime(String grbtime) {
            this.grbtime = grbtime;
        }

        public String getGra() {
            return gra;
        }

        public void setGra(String gra) {
            this.gra = gra;
        }
    }
}
