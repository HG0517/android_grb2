package com.jgkj.grb.ui.mvp;

import java.io.Serializable;
import java.util.List;

/**
 * 限时抢购：标签列表
 * Created by brightpoplar@163.com on 2019/8/30.
 */
public class FlashSaleModel {

    /**
     * code : 1
     * msg : 成功
     * time : 1567144117
     * data : [{"id":1,"time":"8:00","start":"08:00:00","end":1567126800,"status":"已结束"},{"id":3,"time":"10:00","start":"10:00:00","end":1567134000,"status":"已结束"},{"id":4,"time":"11:00","start":"11:00:00","end":1567137600,"status":"已结束"},{"id":5,"time":"12:00","start":"12:00:00","end":1567141200,"status":"已结束"},{"id":7,"time":"14:00","start":"14:00:00","end":1567148400,"status":"即将开始"}]
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

    public static class DataBean implements Serializable {
        /**
         * id : 1
         * time : 8:00
         * start : 08:00:00
         * end : 1567126800
         * status : 已结束
         * type : 0结束，1正在，2即将
         */

        private int id;
        private String time;
        private String start;
        private int end;
        private String status;
        private int type;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public String getStart() {
            return start;
        }

        public void setStart(String start) {
            this.start = start;
        }

        public int getEnd() {
            return end;
        }

        public void setEnd(int end) {
            this.end = end;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }
    }
}
