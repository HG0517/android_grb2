package com.jgkj.grb.ui.mvp.personal;

import java.util.List;

/**
 * 个人中心：账户充值，充值记录
 * Created by brightpoplar@163.com on 2019/9/19.
 */
public class PersonalRechargeRecordModel {

    /**
     * code : 1
     * msg : 成功
     * time : 1568875382
     * data : [{"id":1,"us_id":1,"grc":"10000.00000","num":"10000.00000","type":1,"voucher":"/static/admin/img/wechat.jpg","add_time":"2019-07-04 11:22:25","pass_time":"2019-07-11 10:22:28","status":1,"recharges":20000},{"id":2,"us_id":1,"grc":"200.00000","num":"200.00000","type":1,"voucher":null,"add_time":"2019-07-04 14:53:35","pass_time":"2019-07-11 10:22:36","status":1,"recharges":400},{"id":3,"us_id":1,"grc":"200.00000","num":"200.00000","type":1,"voucher":null,"add_time":"2019-07-04 14:59:12","pass_time":null,"status":1,"recharges":400},{"id":4,"us_id":1,"grc":"200.00000","num":"200.00000","type":1,"voucher":null,"add_time":"2019-07-04 15:08:35","pass_time":null,"status":0,"recharges":400},{"id":5,"us_id":1,"grc":"200.00000","num":"200.00000","type":2,"voucher":"","add_time":"2019-07-04 15:14:11","pass_time":null,"status":0,"recharges":400}]
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
         * id : 1
         * us_id : 1
         * grc : 10000.00000
         * num : 10000.00000
         * type : 1
         * voucher : /static/admin/img/wechat.jpg
         * add_time : 2019-07-04 11:22:25
         * pass_time : 2019-07-11 10:22:28
         * status : 1
         * recharges : 20000
         * or_num : 2019102138126
         * wa_type : null
         */

        private int id;
        private int us_id;
        private String grc;
        private String num;
        private int type;
        private String voucher;
        private String add_time;
        private String pass_time;
        private int status;
        private String recharges;
        private String or_num;
        private String wa_type;

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

        public String getGrc() {
            return grc;
        }

        public void setGrc(String grc) {
            this.grc = grc;
        }

        public String getNum() {
            return num;
        }

        public void setNum(String num) {
            this.num = num;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public String getVoucher() {
            return voucher;
        }

        public void setVoucher(String voucher) {
            this.voucher = voucher;
        }

        public String getAdd_time() {
            return add_time;
        }

        public void setAdd_time(String add_time) {
            this.add_time = add_time;
        }

        public String getPass_time() {
            return pass_time;
        }

        public void setPass_time(String pass_time) {
            this.pass_time = pass_time;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public String getRecharges() {
            return recharges;
        }

        public void setRecharges(String recharges) {
            this.recharges = recharges;
        }

        public String getOr_num() {
            return or_num;
        }

        public void setOr_num(String or_num) {
            this.or_num = or_num;
        }

        public String getWa_type() {
            return wa_type;
        }

        public void setWa_type(String wa_type) {
            this.wa_type = wa_type;
        }
    }
}
