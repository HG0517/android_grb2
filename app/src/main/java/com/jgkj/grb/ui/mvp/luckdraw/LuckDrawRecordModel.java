package com.jgkj.grb.ui.mvp.luckdraw;

import java.util.List;

/**
 * GRB 抽奖：抽奖记录
 * Created by brightpoplar@163.com on 2019/9/3.
 */
public class LuckDrawRecordModel {

    /**
     * code : 1
     * msg : 成功
     * time : 1567482240
     * data : {"list1":[{"id":12,"content":"iPhoneX一部","status":2,"time":"2019-09-01 16:09:07"},{"id":13,"content":"iPhoneX一部","status":2,"time":"2019-09-02 16:55:14"},{"id":14,"content":"iPhoneX一部","status":2,"time":"2019-09-02 16:55:33"},{"id":15,"content":"iPhoneX一部","status":2,"time":"2019-09-02 16:56:40"},{"id":16,"content":"iPhoneX一部","status":2,"time":"2019-09-02 16:59:02"}],"list2":[{"id":1,"content":"200优惠券","status":3,"time":"2019-09-01 14:57:26"},{"id":2,"content":"200优惠券","status":3,"time":"2019-09-01 15:04:19"},{"id":3,"content":"150GRB","status":3,"time":"2019-09-01 15:04:24"},{"id":11,"content":"50GRB","status":3,"time":"2019-09-01 15:30:58"},{"id":12,"content":"iPhoneX一部","status":2,"time":"2019-09-01 16:09:07"},{"id":13,"content":"iPhoneX一部","status":2,"time":"2019-09-02 16:55:14"},{"id":14,"content":"iPhoneX一部","status":2,"time":"2019-09-02 16:55:33"},{"id":15,"content":"iPhoneX一部","status":2,"time":"2019-09-02 16:56:40"},{"id":16,"content":"iPhoneX一部","status":2,"time":"2019-09-02 16:59:02"},{"id":18,"content":"50GRB","status":3,"time":"2019-09-02 17:00:01"},{"id":19,"content":"200优惠券","status":3,"time":"2019-09-02 17:00:04"},{"id":20,"content":"200优惠券","status":3,"time":"2019-09-02 17:00:05"},{"id":21,"content":"50GRB","status":3,"time":"2019-09-02 17:00:06"},{"id":26,"content":"200优惠券","status":3,"time":"2019-09-02 17:35:07"},{"id":29,"content":"200优惠券","status":3,"time":"2019-09-03 09:36:00"},{"id":32,"content":"50GRB","status":3,"time":"2019-09-03 09:40:26"},{"id":33,"content":"50GRB","status":3,"time":"2019-09-03 09:41:06"},{"id":35,"content":"100GRB","status":3,"time":"2019-09-03 09:43:16"},{"id":37,"content":"200优惠券","status":3,"time":"2019-09-03 09:46:22"},{"id":38,"content":"50GRB","status":3,"time":"2019-09-03 10:52:56"},{"id":40,"content":"100GRB","status":3,"time":"2019-09-03 10:54:44"},{"id":42,"content":"200优惠券","status":3,"time":"2019-09-03 10:55:04"},{"id":43,"content":"50GRB","status":3,"time":"2019-09-03 10:55:11"},{"id":44,"content":"200优惠券","status":3,"time":"2019-09-03 11:06:20"},{"id":46,"content":"200优惠券","status":3,"time":"2019-09-03 11:06:39"},{"id":47,"content":"150GRB","status":3,"time":"2019-09-03 11:06:48"},{"id":48,"content":"150GRB","status":3,"time":"2019-09-03 11:17:20"},{"id":50,"content":"150GRB","status":3,"time":"2019-09-03 11:21:44"},{"id":51,"content":"50GRB","status":3,"time":"2019-09-03 11:21:59"}]}
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
        private List<LuckDrawRecordBean> list1;
        private List<LuckDrawRecordBean> list2;

        public List<LuckDrawRecordBean> getList1() {
            return list1;
        }

        public void setList1(List<LuckDrawRecordBean> list1) {
            this.list1 = list1;
        }

        public List<LuckDrawRecordBean> getList2() {
            return list2;
        }

        public void setList2(List<LuckDrawRecordBean> list2) {
            this.list2 = list2;
        }
    }

}
