package com.jgkj.grb.ui.mvp.personal;

import java.io.Serializable;
import java.util.List;

/**
 * 个人中心：GRB，收益明细：主界面，农场玩家
 * Created by brightpoplar@163.com on 2019/9/20.
 */
public class PersonalIncomeModel {

    /**
     * code : 1
     * msg : 成功
     * time : 1568961318
     * data : [{"name":"分享奖","type":3,"today":0,"total":0},{"name":"管理奖","type":4,"today":0,"total":0},{"name":"关爱奖","type":5,"today":0,"total":132.8125},{"name":"感恩奖","type":2,"today":0,"total":0},{"name":"农场丰收","type":1,"today":0,"total":1960}]
     * data : {"sum":0,"farm":0,"profit":0}
     */

    private int code;
    private String msg;
    private int time;
    private DataModelBean data;

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

    public DataModelBean getData() {
        return data;
    }

    public void setData(DataModelBean data) {
        this.data = data;
    }

    public static class DataBean implements Serializable {
        /**
         * name : 分享奖
         * type : 3
         * today : 0
         * total : 0
         */

        private String name;
        private int type;
        private String today;
        private String total;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public String getToday() {
            return today;
        }

        public void setToday(String today) {
            this.today = today;
        }

        public String getTotal() {
            return total;
        }

        public void setTotal(String total) {
            this.total = total;
        }
    }

    public static class DataModelBean {
        /**
         * sum : 0
         * farm : 0
         * profit : 0
         */

        private String sum;
        private String farm;
        private String profit;
        private List<DataBean> list;

        public String getSum() {
            return sum;
        }

        public void setSum(String sum) {
            this.sum = sum;
        }

        public String getFarm() {
            return farm;
        }

        public void setFarm(String farm) {
            this.farm = farm;
        }

        public String getProfit() {
            return profit;
        }

        public void setProfit(String profit) {
            this.profit = profit;
        }

        public List<DataBean> getList() {
            return list;
        }

        public void setList(List<DataBean> list) {
            this.list = list;
        }
    }
}
