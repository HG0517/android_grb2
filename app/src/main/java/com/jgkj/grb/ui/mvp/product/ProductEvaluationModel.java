package com.jgkj.grb.ui.mvp.product;

import com.jgkj.grb.ui.mvp.personal.PersonalEvaluationModel;

import java.util.List;

/**
 * 商品评价
 * Created by brightpoplar@163.com on 2019/8/6.
 */
public class ProductEvaluationModel {

    /**
     * code : 1
     * msg : 成功
     * time : 1568894340
     * data : {"count1":1,"count2":0,"count3":0,"list":[{"id":4,"p_id":132,"us_id":0,"or_id":82,"level":1,"content":"芸豆是啥豆！","add_time":"2019-09-19 10:45:38","pic":["/uploads/20190919/77714e3d4b01c86f015cd1b6e4951223.jpg","/uploads/20190919/b0b3e03887b8236d77b65b4b00a6e9e7.jpg","/uploads/20190919/42cd8104e8ea6df9a51dda49f37fe4c1.jpg"],"anonymous":2,"or_son_id":85,"us_nickname":null,"us_head_pic":null}]}
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
         * count1 : 1
         * count2 : 0
         * count3 : 0
         */

        private int count1;
        private int count2;
        private int count3;
        private List<PersonalEvaluationModel.DataBean> list;

        public int getCount1() {
            return count1;
        }

        public void setCount1(int count1) {
            this.count1 = count1;
        }

        public int getCount2() {
            return count2;
        }

        public void setCount2(int count2) {
            this.count2 = count2;
        }

        public int getCount3() {
            return count3;
        }

        public void setCount3(int count3) {
            this.count3 = count3;
        }

        public List<PersonalEvaluationModel.DataBean> getList() {
            return list;
        }

        public void setList(List<PersonalEvaluationModel.DataBean> list) {
            this.list = list;
        }

    }
}
