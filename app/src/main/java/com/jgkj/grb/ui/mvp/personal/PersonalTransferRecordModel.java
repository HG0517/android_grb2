package com.jgkj.grb.ui.mvp.personal;

import java.util.List;

/**
 * 个人中心：GRB，转让记录
 * Created by brightpoplar@163.com on 2019/9/7.
 */
public class PersonalTransferRecordModel {

    /**
     * code : 1
     * msg : 成功
     * time : 1567819849
     * data : {"total":"30.0000","list":[{"id":119,"us_id":1,"num":"10.00000","type":1,"note":"转出","add_time":"2019-09-03 19:10:16","delete_time":null,"or_id":null,"status":0,"t_id":2,"name":"测试二号"},{"id":121,"us_id":1,"num":"10.00000","type":1,"note":"转出","add_time":"2019-09-04 08:57:59","delete_time":null,"or_id":null,"status":0,"t_id":2,"name":"测试二号"},{"id":123,"us_id":1,"num":"10.00000","type":1,"note":"转出","add_time":"2019-09-04 08:58:48","delete_time":null,"or_id":null,"status":0,"t_id":2,"name":"测试二号"}]}
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
         * total : 30.0000
         * list : [{"id":119,"us_id":1,"num":"10.00000","type":1,"note":"转出","add_time":"2019-09-03 19:10:16","delete_time":null,"or_id":null,"status":0,"t_id":2,"name":"测试二号"},{"id":121,"us_id":1,"num":"10.00000","type":1,"note":"转出","add_time":"2019-09-04 08:57:59","delete_time":null,"or_id":null,"status":0,"t_id":2,"name":"测试二号"},{"id":123,"us_id":1,"num":"10.00000","type":1,"note":"转出","add_time":"2019-09-04 08:58:48","delete_time":null,"or_id":null,"status":0,"t_id":2,"name":"测试二号"}]
         */

        private String total;
        private List<ListBean> list;

        public String getTotal() {
            return total;
        }

        public void setTotal(String total) {
            this.total = total;
        }

        public List<ListBean> getList() {
            return list;
        }

        public void setList(List<ListBean> list) {
            this.list = list;
        }

        public static class ListBean {
            /**
             * id : 119
             * us_id : 1
             * num : 10.00000
             * type : 1
             * type2 : 1    // 1：GRC 2：GRB 3：现金  4 GRA
             * note : 转出
             * add_time : 2019-09-03 19:10:16
             * delete_time : null
             * or_id : null
             * status : 0
             * t_id : 2
             * name : 测试二号
             * deduct2 （手续费），单位GRB，    gra（实际到账） 单位GRA
             */

            private int id;
            private int us_id;
            private String num;
            private String deduct2;
            private String gra;
            private int type;
            private int type2;
            private String note;
            private String add_time;
            private String delete_time;
            private String or_id;
            private int status;
            private String text;
            private int t_id;
            private String name;

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

            public String getNum() {
                return num;
            }

            public void setNum(String num) {
                this.num = num;
            }

            public String getDeduct2() {
                return deduct2;
            }

            public void setDeduct2(String deduct2) {
                this.deduct2 = deduct2;
            }

            public String getGra() {
                return gra;
            }

            public void setGra(String gra) {
                this.gra = gra;
            }

            public int getType() {
                return type;
            }

            public void setType(int type) {
                this.type = type;
            }

            public int getType2() {
                return type2;
            }

            public void setType2(int type2) {
                this.type2 = type2;
            }

            public String getNote() {
                return note;
            }

            public void setNote(String note) {
                this.note = note;
            }

            public String getAdd_time() {
                return add_time;
            }

            public void setAdd_time(String add_time) {
                this.add_time = add_time;
            }

            public String getDelete_time() {
                return delete_time;
            }

            public void setDelete_time(String delete_time) {
                this.delete_time = delete_time;
            }

            public String getOr_id() {
                return or_id;
            }

            public void setOr_id(String or_id) {
                this.or_id = or_id;
            }

            public int getStatus() {
                return status;
            }

            public void setStatus(int status) {
                this.status = status;
            }

            public String getText() {
                return text;
            }

            public void setText(String text) {
                this.text = text;
            }

            public int getT_id() {
                return t_id;
            }

            public void setT_id(int t_id) {
                this.t_id = t_id;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }
        }
    }
}
