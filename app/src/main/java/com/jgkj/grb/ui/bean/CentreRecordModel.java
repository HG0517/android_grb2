package com.jgkj.grb.ui.bean;

import java.util.List;

/**
 * 会议中心：报名记录
 * Created by brightpoplar@163.com on 2019/10/29.
 */
public class CentreRecordModel {

    /**
     * code : 1
     * msg : 成功
     * time : 1572336821
     * data : [{"id":4,"num_id":"00023","us_id":1,"name":"刘达","tel":"0377-514234","card_id":"411546462452","sex":1,"m_id":4,"add_time":"2019-08-21 10:23:44","sign_time":null,"address":"河南省郑州市金水区经八路街道建设厅家属院","time":1567958400}]
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
         * id : 4
         * num_id : 00023
         * us_id : 1
         * name : 刘达
         * tel : 0377-514234
         * card_id : 411546462452
         * sex : 1
         * m_id : 4
         * add_time : 2019-08-21 10:23:44
         * sign_time : null
         * address : 河南省郑州市金水区经八路街道建设厅家属院
         * time : 1567958400
         * status : 0 ：未支付   1 已支付
         */

        private String id;
        private String num_id;
        private int us_id;
        private String name;
        private String tel;
        private String card_id;
        private int sex;
        private int m_id;
        private String add_time;
        private String sign_time;
        private String m_num;
        private int status;
        private String address;
        private int time;
        private int grb;
        private int cash;
        private String fee;

        public String getM_num() {
            return m_num;
        }

        public void setM_num(String m_num) {
            this.m_num = m_num;
        }

        public int getGrb() {
            return grb;
        }

        public void setGrb(int grb) {
            this.grb = grb;
        }

        public int getCash() {
            return cash;
        }

        public void setCash(int cash) {
            this.cash = cash;
        }

        public String getFee() {
            return fee;
        }

        public void setFee(String fee) {
            this.fee = fee;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getNum_id() {
            return num_id;
        }

        public void setNum_id(String num_id) {
            this.num_id = num_id;
        }

        public int getUs_id() {
            return us_id;
        }

        public void setUs_id(int us_id) {
            this.us_id = us_id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getTel() {
            return tel;
        }

        public void setTel(String tel) {
            this.tel = tel;
        }

        public String getCard_id() {
            return card_id;
        }

        public void setCard_id(String card_id) {
            this.card_id = card_id;
        }

        public int getSex() {
            return sex;
        }

        public void setSex(int sex) {
            this.sex = sex;
        }

        public int getM_id() {
            return m_id;
        }

        public void setM_id(int m_id) {
            this.m_id = m_id;
        }

        public String getAdd_time() {
            return add_time;
        }

        public void setAdd_time(String add_time) {
            this.add_time = add_time;
        }

        public String getSign_time() {
            return sign_time;
        }

        public void setSign_time(String sign_time) {
            this.sign_time = sign_time;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public int getTime() {
            return time;
        }

        public void setTime(int time) {
            this.time = time;
        }
    }
}
