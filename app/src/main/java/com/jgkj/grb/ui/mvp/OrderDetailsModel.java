package com.jgkj.grb.ui.mvp;

import com.jgkj.grb.ui.mvp.index.IndexCatesChildPageModel;
import com.jgkj.grb.ui.mvp.shopcart.CartIndexBean;

import java.util.List;

/**
 * 订单详情
 * Created by brightpoplar@163.com on 2019/9/10.
 */
public class OrderDetailsModel {

    /**
     * code : 1
     * msg : 成功
     * time : 1568108374
     * data : {"id":87,"or_num":"20190910051","delete_time":null,"us_id":1,"addr_id":36,"or_add_time":"2019-09-10 17:19:47","or_status":0,"or_finish_time":"0000-00-00 00:00:00","or_express":"","or_express_num":"","deliver_time":"0000-00-00 00:00:00","or_remark":null,"or_voucher":null,"or_express_content":null,"st_id":0,"pay_type":1,"ca_id":20,"or_remind_times":0,"or_remind_time":null,"type":1,"list":[{"id":87,"addr_id":36,"pd_id":133,"or_pd_name":"贝贝南瓜板栗味日本小南 瓜带箱6斤","or_pd_num":1,"or_num":"20190910051","or_pd_price":"60.00000","or_pd_pic":"/uploads/20190828/2a420e4bedbe3bdc95298b285d082b23.jpg,,/uploads/20190828/2a420e4bedbe3bdc95298b285d082b23.jpg","or_pd_pv":"124.10000","skuCate":null,"pd_spec":"3斤装","province":"甘肃","city":"兰州","area":"","addr_detail":"zhongguoguochandang","addr_tel":"15238036423","addr_receiver":"Chen Jin lei","pd_head_pic":"/uploads/20190828/715987cb00d9e8ebad5bb364865b4dd2.jpg"},{"id":87,"addr_id":36,"pd_id":131,"or_pd_name":"新鲜水果小黄瓜5斤整箱 脆嫩荷兰小青瓜","or_pd_num":1,"or_num":"20190910051","or_pd_price":"200.00000","or_pd_pic":"/uploads/20190828/edd376293aabe6dae3e2b306002927e2.jpg,,/uploads/20190828/edd376293aabe6dae3e2b306002927e2.jpg","or_pd_pv":"300.20000","skuCate":null,"pd_spec":"6斤","province":"甘肃","city":"兰州","area":"","addr_detail":"zhongguoguochandang","addr_tel":"15238036423","addr_receiver":"Chen Jin lei","pd_head_pic":"/uploads/20190828/94e31a86de680378a40198e8b42b4cb5.jpg"},{"id":87,"addr_id":36,"pd_id":130,"or_pd_name":"西红柿新鲜番茄自然熟非 有机水果","or_pd_num":2,"or_num":"20190910051","or_pd_price":"450.00000","or_pd_pic":"/uploads/20190828/05f416920c63bb4e7715151eee78f1ff.jpg,,/uploads/20190828/05f416920c63bb4e7715151eee78f1ff.jpg","or_pd_pv":"523.24000","skuCate":null,"pd_spec":"300斤","province":"甘肃","city":"兰州","area":"","addr_detail":"zhongguoguochandang","addr_tel":"15238036423","addr_receiver":"Chen Jin lei","pd_head_pic":"/uploads/20190828/0e9c0634a9ecfe5aeb30ba9d43d14929.jpg"}],"take_addr":"甘肃兰州皋兰县zhongguoguochandang","take_name":"Chen Jin lei","take_tel":"15238036423","total":10,"fee":10,"all_pv":1470.78,"hots":[{"id":128,"pd_name":"私密套盒2","pd_price":"3000.00000","pd_status":5,"pd_inventory":9999,"pd_sales":1,"pd_pic":"/uploads/20190618/14373d28956032e03d583d1bb8722366.jpg,/uploads/20190618/31e1df9e71dacbd3086368d8339a6b41.jpg","pd_content":"","delete_time":null,"pd_add_time":"2019-08-29 16:16:19","ca_id":28,"pd_agency_price":"0.00","agency_status":2,"agency_num":10,"pd_detail":"","pd_province":null,"pd_place":"郑州","take_fee":"0.00","pd_color":null,"pd_date":"10年","pd_band":null,"pd_spec":null,"pd_pd_com":null,"st_id":0,"st_status":1,"pd_head_pic":"/uploads/20190612/39cafc249542b0c53a37834b1730dfcb.png","pd_code":null,"pd_pv":"2000.00000","limitime":"0","last":0,"paytype":1},{"id":133,"pd_name":"贝贝南瓜板栗味日本小南 瓜带箱6斤","pd_price":"66.60000","pd_status":5,"pd_inventory":100,"pd_sales":0,"pd_pic":"/uploads/20190828/2a420e4bedbe3bdc95298b285d082b23.jpg,,/uploads/20190828/2a420e4bedbe3bdc95298b285d082b23.jpg","pd_content":"","delete_time":null,"pd_add_time":"2019-08-28 11:07:35","ca_id":21,"pd_agency_price":"0.00","agency_status":2,"agency_num":10,"pd_detail":"<p style=\"text-align:center\"><img src=\"/ueditor/php/upload/image/20190828/1566961650.png\" title=\"1566961650.png\" alt=\"detail.png\"/><\/p><p><br/><\/p>","pd_province":null,"pd_place":"郑州","take_fee":"0.00","pd_color":null,"pd_date":null,"pd_band":null,"pd_spec":null,"pd_pd_com":null,"st_id":0,"st_status":1,"pd_head_pic":"/uploads/20190828/715987cb00d9e8ebad5bb364865b4dd2.jpg","pd_code":null,"pd_pv":"951.20000","limitime":null,"last":0,"paytype":1},{"id":132,"pd_name":"新鲜现摘芸豆带箱3斤装 山东特产","pd_price":"45.80000","pd_status":5,"pd_inventory":100,"pd_sales":0,"pd_pic":"/uploads/20190828/e348aa3b52e801d0f31a87b02032a81f.jpg,,/uploads/20190828/e348aa3b52e801d0f31a87b02032a81f.jpg","pd_content":"","delete_time":null,"pd_add_time":"2019-08-28 10:49:13","ca_id":21,"pd_agency_price":"0.00","agency_status":2,"agency_num":10,"pd_detail":"","pd_province":null,"pd_place":"郑州","take_fee":"0.00","pd_color":null,"pd_date":null,"pd_band":null,"pd_spec":null,"pd_pd_com":null,"st_id":0,"st_status":1,"pd_head_pic":"/uploads/20190828/23ad70290b597055aa1815bff167daa0.jpg","pd_code":null,"pd_pv":"952.10000","limitime":null,"last":0,"paytype":1},{"id":131,"pd_name":"新鲜水果小黄瓜5斤整箱 脆嫩荷兰小青瓜","pd_price":"26.30000","pd_status":5,"pd_inventory":100,"pd_sales":0,"pd_pic":"/uploads/20190828/edd376293aabe6dae3e2b306002927e2.jpg,,/uploads/20190828/edd376293aabe6dae3e2b306002927e2.jpg","pd_content":"","delete_time":null,"pd_add_time":"2019-08-28 10:47:55","ca_id":21,"pd_agency_price":"0.00","agency_status":2,"agency_num":10,"pd_detail":"","pd_province":null,"pd_place":"郑州","take_fee":"0.00","pd_color":null,"pd_date":null,"pd_band":null,"pd_spec":null,"pd_pd_com":null,"st_id":0,"st_status":1,"pd_head_pic":"/uploads/20190828/94e31a86de680378a40198e8b42b4cb5.jpg","pd_code":null,"pd_pv":"953.80000","limitime":null,"last":0,"paytype":1}]}
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
         * id : 87
         * or_num : 20190910051
         * delete_time : null
         * us_id : 1
         * addr_id : 36
         * or_add_time : 2019-09-10 17:19:47
         * or_status : 0
         * or_finish_time : 0000-00-00 00:00:00
         * or_express :
         * or_express_num :
         * deliver_time : 0000-00-00 00:00:00
         * or_remark : null
         * or_voucher : null
         * or_express_content : null
         * st_id : 0
         * pay_type : 1
         * ca_id : 20
         * or_remind_times : 0
         * or_remind_time : null
         * type : 1
         * type2 : 1
         * type3 : 1
         * type4 : 2
         * list : [{"id":87,"addr_id":36,"pd_id":133,"or_pd_name":"贝贝南瓜板栗味日本小南 瓜带箱6斤","or_pd_num":1,"or_num":"20190910051","or_pd_price":"60.00000","or_pd_pic":"/uploads/20190828/2a420e4bedbe3bdc95298b285d082b23.jpg,,/uploads/20190828/2a420e4bedbe3bdc95298b285d082b23.jpg","or_pd_pv":"124.10000","skuCate":null,"pd_spec":"3斤装","province":"甘肃","city":"兰州","area":"","addr_detail":"zhongguoguochandang","addr_tel":"15238036423","addr_receiver":"Chen Jin lei","pd_head_pic":"/uploads/20190828/715987cb00d9e8ebad5bb364865b4dd2.jpg"},{"id":87,"addr_id":36,"pd_id":131,"or_pd_name":"新鲜水果小黄瓜5斤整箱 脆嫩荷兰小青瓜","or_pd_num":1,"or_num":"20190910051","or_pd_price":"200.00000","or_pd_pic":"/uploads/20190828/edd376293aabe6dae3e2b306002927e2.jpg,,/uploads/20190828/edd376293aabe6dae3e2b306002927e2.jpg","or_pd_pv":"300.20000","skuCate":null,"pd_spec":"6斤","province":"甘肃","city":"兰州","area":"","addr_detail":"zhongguoguochandang","addr_tel":"15238036423","addr_receiver":"Chen Jin lei","pd_head_pic":"/uploads/20190828/94e31a86de680378a40198e8b42b4cb5.jpg"},{"id":87,"addr_id":36,"pd_id":130,"or_pd_name":"西红柿新鲜番茄自然熟非 有机水果","or_pd_num":2,"or_num":"20190910051","or_pd_price":"450.00000","or_pd_pic":"/uploads/20190828/05f416920c63bb4e7715151eee78f1ff.jpg,,/uploads/20190828/05f416920c63bb4e7715151eee78f1ff.jpg","or_pd_pv":"523.24000","skuCate":null,"pd_spec":"300斤","province":"甘肃","city":"兰州","area":"","addr_detail":"zhongguoguochandang","addr_tel":"15238036423","addr_receiver":"Chen Jin lei","pd_head_pic":"/uploads/20190828/0e9c0634a9ecfe5aeb30ba9d43d14929.jpg"}]
         * take_addr : 甘肃兰州皋兰县zhongguoguochandang
         * take_name : Chen Jin lei
         * take_tel : 15238036423
         * total : 10
         * fee : 10
         * all_pv : 1470.78
         * hots : [{"id":128,"pd_name":"私密套盒2","pd_price":"3000.00000","pd_status":5,"pd_inventory":9999,"pd_sales":1,"pd_pic":"/uploads/20190618/14373d28956032e03d583d1bb8722366.jpg,/uploads/20190618/31e1df9e71dacbd3086368d8339a6b41.jpg","pd_content":"","delete_time":null,"pd_add_time":"2019-08-29 16:16:19","ca_id":28,"pd_agency_price":"0.00","agency_status":2,"agency_num":10,"pd_detail":"","pd_province":null,"pd_place":"郑州","take_fee":"0.00","pd_color":null,"pd_date":"10年","pd_band":null,"pd_spec":null,"pd_pd_com":null,"st_id":0,"st_status":1,"pd_head_pic":"/uploads/20190612/39cafc249542b0c53a37834b1730dfcb.png","pd_code":null,"pd_pv":"2000.00000","limitime":"0","last":0,"paytype":1},{"id":133,"pd_name":"贝贝南瓜板栗味日本小南 瓜带箱6斤","pd_price":"66.60000","pd_status":5,"pd_inventory":100,"pd_sales":0,"pd_pic":"/uploads/20190828/2a420e4bedbe3bdc95298b285d082b23.jpg,,/uploads/20190828/2a420e4bedbe3bdc95298b285d082b23.jpg","pd_content":"","delete_time":null,"pd_add_time":"2019-08-28 11:07:35","ca_id":21,"pd_agency_price":"0.00","agency_status":2,"agency_num":10,"pd_detail":"<p style=\"text-align:center\"><img src=\"/ueditor/php/upload/image/20190828/1566961650.png\" title=\"1566961650.png\" alt=\"detail.png\"/><\/p><p><br/><\/p>","pd_province":null,"pd_place":"郑州","take_fee":"0.00","pd_color":null,"pd_date":null,"pd_band":null,"pd_spec":null,"pd_pd_com":null,"st_id":0,"st_status":1,"pd_head_pic":"/uploads/20190828/715987cb00d9e8ebad5bb364865b4dd2.jpg","pd_code":null,"pd_pv":"951.20000","limitime":null,"last":0,"paytype":1},{"id":132,"pd_name":"新鲜现摘芸豆带箱3斤装 山东特产","pd_price":"45.80000","pd_status":5,"pd_inventory":100,"pd_sales":0,"pd_pic":"/uploads/20190828/e348aa3b52e801d0f31a87b02032a81f.jpg,,/uploads/20190828/e348aa3b52e801d0f31a87b02032a81f.jpg","pd_content":"","delete_time":null,"pd_add_time":"2019-08-28 10:49:13","ca_id":21,"pd_agency_price":"0.00","agency_status":2,"agency_num":10,"pd_detail":"","pd_province":null,"pd_place":"郑州","take_fee":"0.00","pd_color":null,"pd_date":null,"pd_band":null,"pd_spec":null,"pd_pd_com":null,"st_id":0,"st_status":1,"pd_head_pic":"/uploads/20190828/23ad70290b597055aa1815bff167daa0.jpg","pd_code":null,"pd_pv":"952.10000","limitime":null,"last":0,"paytype":1},{"id":131,"pd_name":"新鲜水果小黄瓜5斤整箱 脆嫩荷兰小青瓜","pd_price":"26.30000","pd_status":5,"pd_inventory":100,"pd_sales":0,"pd_pic":"/uploads/20190828/edd376293aabe6dae3e2b306002927e2.jpg,,/uploads/20190828/edd376293aabe6dae3e2b306002927e2.jpg","pd_content":"","delete_time":null,"pd_add_time":"2019-08-28 10:47:55","ca_id":21,"pd_agency_price":"0.00","agency_status":2,"agency_num":10,"pd_detail":"","pd_province":null,"pd_place":"郑州","take_fee":"0.00","pd_color":null,"pd_date":null,"pd_band":null,"pd_spec":null,"pd_pd_com":null,"st_id":0,"st_status":1,"pd_head_pic":"/uploads/20190828/94e31a86de680378a40198e8b42b4cb5.jpg","pd_code":null,"pd_pv":"953.80000","limitime":null,"last":0,"paytype":1}]
         */

        private int id;
        private String or_num;
        private String delete_time;
        private int us_id;
        private int addr_id;
        private String or_add_time;
        private int or_status;
        private String or_finish_time;
        private String or_express;
        private String or_express_num;
        private String deliver_time;
        private String or_remark;
        private String or_voucher;
        private String or_express_content;
        private int st_id;
        private String pay_type;
        private int ca_id;
        private int or_remind_times;
        private String or_remind_time;
        private int type;
        private int type2;
        private int type3;
        private int type4;
        private String take_addr;
        private String take_name;
        private String take_tel;
        private int total;
        private int fee;
        private double all_pv;
        private List<CartIndexBean.CartBean> list;
        private List<IndexCatesChildPageModel.DataBean> hots;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getOr_num() {
            return or_num;
        }

        public void setOr_num(String or_num) {
            this.or_num = or_num;
        }

        public String getDelete_time() {
            return delete_time;
        }

        public void setDelete_time(String delete_time) {
            this.delete_time = delete_time;
        }

        public int getUs_id() {
            return us_id;
        }

        public void setUs_id(int us_id) {
            this.us_id = us_id;
        }

        public int getAddr_id() {
            return addr_id;
        }

        public void setAddr_id(int addr_id) {
            this.addr_id = addr_id;
        }

        public String getOr_add_time() {
            return or_add_time;
        }

        public void setOr_add_time(String or_add_time) {
            this.or_add_time = or_add_time;
        }

        public int getOr_status() {
            return or_status;
        }

        public void setOr_status(int or_status) {
            this.or_status = or_status;
        }

        public String getOr_finish_time() {
            return or_finish_time;
        }

        public void setOr_finish_time(String or_finish_time) {
            this.or_finish_time = or_finish_time;
        }

        public String getOr_express() {
            return or_express;
        }

        public void setOr_express(String or_express) {
            this.or_express = or_express;
        }

        public String getOr_express_num() {
            return or_express_num;
        }

        public void setOr_express_num(String or_express_num) {
            this.or_express_num = or_express_num;
        }

        public String getDeliver_time() {
            return deliver_time;
        }

        public void setDeliver_time(String deliver_time) {
            this.deliver_time = deliver_time;
        }

        public String getOr_remark() {
            return or_remark;
        }

        public void setOr_remark(String or_remark) {
            this.or_remark = or_remark;
        }

        public String getOr_voucher() {
            return or_voucher;
        }

        public void setOr_voucher(String or_voucher) {
            this.or_voucher = or_voucher;
        }

        public String getOr_express_content() {
            return or_express_content;
        }

        public void setOr_express_content(String or_express_content) {
            this.or_express_content = or_express_content;
        }

        public int getSt_id() {
            return st_id;
        }

        public void setSt_id(int st_id) {
            this.st_id = st_id;
        }

        public String getPay_type() {
            return pay_type;
        }

        public void setPay_type(String pay_type) {
            this.pay_type = pay_type;
        }

        public int getCa_id() {
            return ca_id;
        }

        public void setCa_id(int ca_id) {
            this.ca_id = ca_id;
        }

        public int getOr_remind_times() {
            return or_remind_times;
        }

        public void setOr_remind_times(int or_remind_times) {
            this.or_remind_times = or_remind_times;
        }

        public String getOr_remind_time() {
            return or_remind_time;
        }

        public void setOr_remind_time(String or_remind_time) {
            this.or_remind_time = or_remind_time;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public String getTake_addr() {
            return take_addr;
        }

        public void setTake_addr(String take_addr) {
            this.take_addr = take_addr;
        }

        public String getTake_name() {
            return take_name;
        }

        public void setTake_name(String take_name) {
            this.take_name = take_name;
        }

        public String getTake_tel() {
            return take_tel;
        }

        public void setTake_tel(String take_tel) {
            this.take_tel = take_tel;
        }

        public int getTotal() {
            return total;
        }

        public void setTotal(int total) {
            this.total = total;
        }

        public int getFee() {
            return fee;
        }

        public void setFee(int fee) {
            this.fee = fee;
        }

        public double getAll_pv() {
            return all_pv;
        }

        public void setAll_pv(double all_pv) {
            this.all_pv = all_pv;
        }

        public List<CartIndexBean.CartBean> getList() {
            return list;
        }

        public void setList(List<CartIndexBean.CartBean> list) {
            this.list = list;
        }

        public List<IndexCatesChildPageModel.DataBean> getHots() {
            return hots;
        }

        public void setHots(List<IndexCatesChildPageModel.DataBean> hots) {
            this.hots = hots;
        }

        public int getType2() {
            return type2;
        }

        public void setType2(int type2) {
            this.type2 = type2;
        }

        public int getType3() {
            return type3;
        }

        public void setType3(int type3) {
            this.type3 = type3;
        }

        public int getType4() {
            return type4;
        }

        public void setType4(int type4) {
            this.type4 = type4;
        }
    }
}
