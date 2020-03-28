package com.jgkj.grb.ui.mvp.personal;

import java.util.List;

/**
 * 个人中心：GRB，明细
 * Created by brightpoplar@163.com on 2019/9/4.
 */
public class PersonalGRBCashModel {

    /**
     * code : 1
     * msg : 成功
     * time : 1567569030
     * data : {"total":"0.0000","list":[]}
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
         * total : 0.0000   // 总额
         * list : []
         */

        private String total;
        private List<GRBCashBean> list;

        public String getTotal() {
            return total;
        }

        public void setTotal(String total) {
            this.total = total;
        }

        public List<GRBCashBean> getList() {
            return list;
        }

        public void setList(List<GRBCashBean> list) {
            this.list = list;
        }
    }

    public static class GRBCashBean {

        /**
         * id : 58
         * us_id : 6
         * wa_num : -10.00000   // 金额
         * wa_type : 2
         * wa_note : 购买10个铲子，消耗10   // 消费来源
         * add_time : 2019-08-26 15:35:23   // 消费时间
         * delete_time : null
         * or_id : null
         * type : 7
         * status : 0   // 0：支出  1：收入
         * t_id : 0
         * get_bi : null
         */

        private int id;
        private int us_id;
        private String wa_num;
        private int wa_type;
        private String wa_note;
        private String add_time;
        private Object delete_time;
        private Object or_id;
        private int type;
        private int status;
        private int t_id;
        private Object get_bi;

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

        public String getWa_num() {
            return wa_num;
        }

        public void setWa_num(String wa_num) {
            this.wa_num = wa_num;
        }

        public int getWa_type() {
            return wa_type;
        }

        public void setWa_type(int wa_type) {
            this.wa_type = wa_type;
        }

        public String getWa_note() {
            return wa_note;
        }

        public void setWa_note(String wa_note) {
            this.wa_note = wa_note;
        }

        public String getAdd_time() {
            return add_time;
        }

        public void setAdd_time(String add_time) {
            this.add_time = add_time;
        }

        public Object getDelete_time() {
            return delete_time;
        }

        public void setDelete_time(Object delete_time) {
            this.delete_time = delete_time;
        }

        public Object getOr_id() {
            return or_id;
        }

        public void setOr_id(Object or_id) {
            this.or_id = or_id;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public int getT_id() {
            return t_id;
        }

        public void setT_id(int t_id) {
            this.t_id = t_id;
        }

        public Object getGet_bi() {
            return get_bi;
        }

        public void setGet_bi(Object get_bi) {
            this.get_bi = get_bi;
        }
    }
}
