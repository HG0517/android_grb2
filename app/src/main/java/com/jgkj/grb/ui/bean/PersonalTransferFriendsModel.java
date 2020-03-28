package com.jgkj.grb.ui.bean;

/**
 * 个人中心：GRB，好友转让
 * Created by brightpoplar@163.com on 2019/11/7.
 */
public class PersonalTransferFriendsModel {

    /**
     * code : 1
     * msg : 成功
     * time : 1573115462
     * data : {"grbfee":"1","grafee":1.1204,"change":"0.8925","gra1":"560.2241","gra2":"1,120.4482","gra3":"2,801.1204"}
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
         * grbfee : 1           //grb  价格
         * grafee : 1.1204      //gra  价格
         * change : 0.8925      //一个grb 等于多少gra
         * fee : 10             //手续费
         * cashfee : 10             //手续费
         * gra1 : 560.2241
         * gra2 : 1,120.4482
         * gra3 : 2,801.1204
         * gra4 : 560.2241
         * gra5 : 1,120.4482
         * gra6 : 2,801.1204
         */

        private String grbfee;
        private String grafee;
        private String change;
        private String fee;
        private String gra1;
        private String gra2;
        private String gra3;
        private String change2;
        private String cashfee;
        private String gra4;
        private String gra5;
        private String gra6;

        public String getGrbfee() {
            return grbfee;
        }

        public void setGrbfee(String grbfee) {
            this.grbfee = grbfee;
        }

        public String getGrafee() {
            return grafee;
        }

        public void setGrafee(String grafee) {
            this.grafee = grafee;
        }

        public String getChange() {
            return change;
        }

        public void setChange(String change) {
            this.change = change;
        }

        public String getFee() {
            return fee;
        }

        public void setFee(String fee) {
            this.fee = fee;
        }

        public String getGra1() {
            return gra1;
        }

        public void setGra1(String gra1) {
            this.gra1 = gra1;
        }

        public String getGra2() {
            return gra2;
        }

        public void setGra2(String gra2) {
            this.gra2 = gra2;
        }

        public String getGra3() {
            return gra3;
        }

        public void setGra3(String gra3) {
            this.gra3 = gra3;
        }

        public String getChange2() {
            return change2;
        }

        public void setChange2(String change2) {
            this.change2 = change2;
        }

        public String getCashfee() {
            return cashfee;
        }

        public void setCashfee(String cashfee) {
            this.cashfee = cashfee;
        }

        public String getGra4() {
            return gra4;
        }

        public void setGra4(String gra4) {
            this.gra4 = gra4;
        }

        public String getGra5() {
            return gra5;
        }

        public void setGra5(String gra5) {
            this.gra5 = gra5;
        }

        public String getGra6() {
            return gra6;
        }

        public void setGra6(String gra6) {
            this.gra6 = gra6;
        }
    }
}
