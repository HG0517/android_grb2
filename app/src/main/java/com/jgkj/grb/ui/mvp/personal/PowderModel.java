package com.jgkj.grb.ui.mvp.personal;

import java.util.List;

/**
 * 个人中心：我的宝粉
 * Created by brightpoplar@163.com on 2019/9/18.
 */
public class PowderModel {

    /**
     * code : 1
     * msg : 成功
     * time : 1568789177
     * data : [{"us_nickname":"测试二号","us_account":"SZ2018000002","us_head_pic":"/static/admin/img/head1.jpg"},{"us_nickname":"测试三号","us_account":"SZ2018000003","us_head_pic":"/static/admin/img/head1.jpg"},{"us_nickname":"测试四号","us_account":"SZ2018000004","us_head_pic":"/uploads/20180908/14a84ee10ba2261f6835535495503c33.png"},{"us_nickname":"测试8号","us_account":"SZ2018000005","us_head_pic":"/static/admin/img/head1.jpg"},{"us_nickname":"测试09号","us_account":"SZ2018000006","us_head_pic":"/static/admin/img/head1.jpg"},{"us_nickname":"测试009号","us_account":"SZ2018000007","us_head_pic":"/static/admin/img/head1.jpg"},{"us_nickname":"123","us_account":"SZ2018000008","us_head_pic":"/uploads/20180908/63c522c6a9fab87efde4bc418fa32355.jpg"},{"us_nickname":"暗无天日","us_account":"SZ2018000009","us_head_pic":"/static/admin/img/head1.jpg"},{"us_nickname":"你大哥","us_account":"SZ2018000010","us_head_pic":"/static/admin/img/head1.jpg"},{"us_nickname":"倪宝强","us_account":"SZ2018000011","us_head_pic":"/static/admin/img/head1.jpg"},{"us_nickname":"周先华","us_account":"SZ2018000012","us_head_pic":"/static/admin/img/head1.jpg"},{"us_nickname":"王麟","us_account":"00000014","us_head_pic":"/uploads/20190613/80ea64e5179e95ebdb846959cf6065e0.png"},{"us_nickname":"李集","us_account":"00000015","us_head_pic":"/static/admin/img/head1.jpg"},{"us_nickname":"张四飞","us_account":"00000018","us_head_pic":"/static/admin/img/head1.jpg"},{"us_nickname":"张五飞","us_account":"00000019","us_head_pic":"/static/admin/img/head1.jpg"},{"us_nickname":"张启飞","us_account":"00000021","us_head_pic":"/static/admin/img/head1.jpg"},{"us_nickname":"刘亮","us_account":"00000022","us_head_pic":"/static/admin/img/alipay1.jpg"},{"us_nickname":"test","us_account":"00000023","us_head_pic":"/uploads/20190916/5761f201c0aecbcfadff6c70d584b087.png"},{"us_nickname":"321","us_account":"00000024","us_head_pic":"/uploads/20190916/a99a46cb22ad2f54746183130f068425.png"}]
     */

    private int code;
    private String msg;
    private int time;
    private PowderDataBean data;

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

    public PowderDataBean getData() {
        return data;
    }

    public void setData(PowderDataBean data) {
        this.data = data;
    }

    public static class PowderDataBean {

        /**
         * zt_total : 215,435,435.00
         * list :
         * money_sum : 2500
         * count : 10
         */

        private String zt_total;
        private String money_sum;
        private String count;
        private List<DataBean> list;

        public List<DataBean> getList() {
            return list;
        }

        public void setList(List<DataBean> list) {
            this.list = list;
        }

        public String getZt_total() {
            return zt_total;
        }

        public void setZt_total(String zt_total) {
            this.zt_total = zt_total;
        }

        public String getMoney_sum() {
            return money_sum;
        }

        public void setMoney_sum(String money_sum) {
            this.money_sum = money_sum;
        }

        public String getCount() {
            return count;
        }

        public void setCount(String count) {
            this.count = count;
        }
    }

    public static class DataBean {
        /**
         * us_nickname : 测试二号
         * us_account : SZ2018000002
         * us_head_pic : /static/admin/img/head1.jpg
         * zt_money : 0.00
         * us_tel : 146***64
         * id : 22
         * sum : 0
         */

        private String us_nickname;
        private String us_account;
        private String us_head_pic;
        private String zt_money;
        private String us_tel;
        private int id;
        private String sum;

        public String getUs_nickname() {
            return us_nickname;
        }

        public void setUs_nickname(String us_nickname) {
            this.us_nickname = us_nickname;
        }

        public String getUs_account() {
            return us_account;
        }

        public void setUs_account(String us_account) {
            this.us_account = us_account;
        }

        public String getUs_head_pic() {
            return us_head_pic;
        }

        public void setUs_head_pic(String us_head_pic) {
            this.us_head_pic = us_head_pic;
        }

        public String getZt_money() {
            return zt_money;
        }

        public void setZt_money(String zt_money) {
            this.zt_money = zt_money;
        }

        public String getUs_tel() {
            return us_tel;
        }

        public void setUs_tel(String us_tel) {
            this.us_tel = us_tel;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getSum() {
            return sum;
        }

        public void setSum(String sum) {
            this.sum = sum;
        }
    }
}
