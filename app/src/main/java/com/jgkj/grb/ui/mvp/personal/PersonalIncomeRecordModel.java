package com.jgkj.grb.ui.mvp.personal;

import java.util.List;

/**
 * 个人中心：GRB，收益明细：农场玩家，收益记录
 * Created by brightpoplar@163.com on 2019/9/20.
 */
public class PersonalIncomeRecordModel {

    /**
     * code : 1
     * msg : 成功
     * time : 1568965750
     * data : [{"id":174,"us_id":6,"wa_num":"62.50000","wa_type":2,"wa_note":"关爱奖：62.5GRC","add_time":"2019-09-17 09:16:36","delete_time":null,"or_id":null,"type":5,"status":1,"t_id":4,"get_bi":null},{"id":178,"us_id":6,"wa_num":"15.62500","wa_type":2,"wa_note":"关爱奖：15.625GRC","add_time":"2019-09-17 09:16:38","delete_time":null,"or_id":null,"type":5,"status":1,"t_id":4,"get_bi":null},{"id":183,"us_id":6,"wa_num":"6.25000","wa_type":2,"wa_note":"关爱奖：6.25GRC","add_time":"2019-09-17 09:53:42","delete_time":null,"or_id":null,"type":5,"status":1,"t_id":4,"get_bi":null},{"id":187,"us_id":6,"wa_num":"1.56250","wa_type":2,"wa_note":"关爱奖：1.5625GRC","add_time":"2019-09-17 09:53:44","delete_time":null,"or_id":null,"type":5,"status":1,"t_id":4,"get_bi":null},{"id":191,"us_id":6,"wa_num":"6.25000","wa_type":2,"wa_note":"关爱奖：6.25GRC","add_time":"2019-09-17 09:56:30","delete_time":null,"or_id":null,"type":5,"status":1,"t_id":4,"get_bi":null},{"id":195,"us_id":6,"wa_num":"1.56250","wa_type":2,"wa_note":"关爱奖：1.5625GRC","add_time":"2019-09-17 09:56:32","delete_time":null,"or_id":null,"type":5,"status":1,"t_id":4,"get_bi":null},{"id":199,"us_id":6,"wa_num":"6.25000","wa_type":2,"wa_note":"关爱奖：6.25GRC","add_time":"2019-09-17 10:00:36","delete_time":null,"or_id":null,"type":5,"status":1,"t_id":4,"get_bi":null},{"id":203,"us_id":6,"wa_num":"1.56250","wa_type":2,"wa_note":"关爱奖：1.5625GRC","add_time":"2019-09-17 10:00:38","delete_time":null,"or_id":null,"type":5,"status":1,"t_id":4,"get_bi":null},{"id":207,"us_id":6,"wa_num":"6.25000","wa_type":2,"wa_note":"关爱奖：6.25GRC","add_time":"2019-09-17 10:10:24","delete_time":null,"or_id":null,"type":5,"status":1,"t_id":4,"get_bi":null},{"id":211,"us_id":6,"wa_num":"1.56250","wa_type":2,"wa_note":"关爱奖：1.5625GRC","add_time":"2019-09-17 10:10:28","delete_time":null,"or_id":null,"type":5,"status":1,"t_id":4,"get_bi":null},{"id":215,"us_id":6,"wa_num":"12.50000","wa_type":2,"wa_note":"关爱奖：12.5GRC","add_time":"2019-09-17 10:13:06","delete_time":null,"or_id":null,"type":5,"status":1,"t_id":4,"get_bi":null},{"id":219,"us_id":6,"wa_num":"3.12500","wa_type":2,"wa_note":"关爱奖：3.125GRC","add_time":"2019-09-17 10:13:07","delete_time":null,"or_id":null,"type":5,"status":1,"t_id":4,"get_bi":null},{"id":223,"us_id":6,"wa_num":"6.25000","wa_type":2,"wa_note":"关爱奖：6.25GRC","add_time":"2019-09-17 10:18:31","delete_time":null,"or_id":null,"type":5,"status":1,"t_id":4,"get_bi":null},{"id":227,"us_id":6,"wa_num":"1.56250","wa_type":2,"wa_note":"关爱奖：1.5625GRC","add_time":"2019-09-17 10:18:38","delete_time":null,"or_id":null,"type":5,"status":1,"t_id":4,"get_bi":null}]
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
         * id : 174
         * us_id : 6
         * wa_num : 62.50000
         * wa_type : 2
         * wa_note : 关爱奖：62.5GRC
         * add_time : 2019-09-17 09:16:36
         * delete_time : null
         * or_id : null
         * type : 5
         * status : 1
         * t_id : 4
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
