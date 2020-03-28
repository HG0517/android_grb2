package com.jgkj.grb.ui.mvp.personal;

import com.jgkj.grb.ui.mvp.index.IndexCatesChildPageModel;

import java.util.List;

/**
 * 个人中心：
 * Created by brightpoplar@163.com on 2019/9/18.
 */
public class PersonalHeadModel {

    /**
     * code : 1
     * msg : 成功
     * time : 1568798158
     * data : {"us_head_pic":"/uploads/20190615/892e9d9a312c1225520bb0421c1a3b02.jpg","us_nickname":"初始测试员","out":1,"level_name":"一星玩家","cash":"0.00","grb_cash":727,"hots":[{"id":5,"pd_name":"私密套盒3","pd_price":"4000.00000","pd_status":5,"pd_inventory":10000,"pd_sales":0,"pd_pic":"/uploads/20190612/84c92d08847dd6e9fd419371d34897e2.jpg,,/uploads/20190612/84c92d08847dd6e9fd419371d34897e2.jpg","pd_content":"私密","delete_time":null,"pd_add_time":"2019-06-12 09:28:15","ca_id":5,"pd_agency_price":"0.00","agency_status":2,"agency_num":10,"pd_detail":"","pd_province":null,"pd_place":"郑州","take_fee":"0.00","pd_color":null,"pd_date":"10年","pd_band":null,"pd_spec":null,"pd_pd_com":null,"st_id":0,"st_status":1,"pd_head_pic":"/uploads/20190612/8a9dd55f9fd79ebc8412cdd350d98390.jpg","pd_code":null,"pd_pv":"3000.00000","limitime":null,"last":0,"paytype":1,"advance":"0000-00-00 00:00:00","p_id":0,"ca_name":"水果","ca_status":1,"ca_add_time":"2018-08-24 10:20:10","ca_sort":20,"ca_pic":"/uploads/20190527/5a0ee270c5c7566ae3583ecba2d6863b.png","ca_ca":0},{"id":28,"pd_name":"私密套盒2","pd_price":"3000.00000","pd_status":5,"pd_inventory":9999,"pd_sales":1,"pd_pic":"/uploads/20190618/14373d28956032e03d583d1bb8722366.jpg,/uploads/20190618/31e1df9e71dacbd3086368d8339a6b41.jpg","pd_content":"","delete_time":null,"pd_add_time":"2019-08-29 16:16:19","ca_id":28,"pd_agency_price":"0.00","agency_status":2,"agency_num":10,"pd_detail":"","pd_province":null,"pd_place":"郑州","take_fee":"0.00","pd_color":null,"pd_date":"10年","pd_band":null,"pd_spec":null,"pd_pd_com":null,"st_id":0,"st_status":1,"pd_head_pic":"/uploads/20190612/39cafc249542b0c53a37834b1730dfcb.png","pd_code":null,"pd_pv":"2000.00000","limitime":"0","last":0,"paytype":1,"advance":"0000-00-00 00:00:00","p_id":27,"ca_name":"专供","ca_status":1,"ca_add_time":"2019-08-20 17:51:53","ca_sort":1,"ca_pic":"/uploads/20190820/a951a1d36432d0811e041e4ac5262062.png","ca_ca":0},{"id":21,"pd_name":"西红柿新鲜番茄自然熟非 有机水果","pd_price":"19.60000","pd_status":5,"pd_inventory":100,"pd_sales":0,"pd_pic":"/uploads/20190828/05f416920c63bb4e7715151eee78f1ff.jpg,,/uploads/20190828/05f416920c63bb4e7715151eee78f1ff.jpg","pd_content":"","delete_time":null,"pd_add_time":"2019-08-28 10:40:56","ca_id":21,"pd_agency_price":"0.00","agency_status":2,"agency_num":10,"pd_detail":"","pd_province":null,"pd_place":"郑州","take_fee":"0.00","pd_color":null,"pd_date":null,"pd_band":null,"pd_spec":null,"pd_pd_com":null,"st_id":0,"st_status":1,"pd_head_pic":"/uploads/20190828/0e9c0634a9ecfe5aeb30ba9d43d14929.jpg","pd_code":null,"pd_pv":"969.00000","limitime":null,"last":0,"paytype":1,"advance":"0000-00-00 00:00:00","p_id":20,"ca_name":"果菜","ca_status":1,"ca_add_time":"2019-07-31 16:02:03","ca_sort":1,"ca_pic":"/uploads/20190828/3edecc3c2c37252b8e56d83269ce0108.jpg","ca_ca":0},{"id":21,"pd_name":"新鲜水果小黄瓜5斤整箱 脆嫩荷兰小青瓜","pd_price":"26.30000","pd_status":5,"pd_inventory":100,"pd_sales":0,"pd_pic":"/uploads/20190828/edd376293aabe6dae3e2b306002927e2.jpg,,/uploads/20190828/edd376293aabe6dae3e2b306002927e2.jpg","pd_content":"","delete_time":null,"pd_add_time":"2019-08-28 10:47:55","ca_id":21,"pd_agency_price":"0.00","agency_status":2,"agency_num":10,"pd_detail":"","pd_province":null,"pd_place":"郑州","take_fee":"0.00","pd_color":null,"pd_date":null,"pd_band":null,"pd_spec":null,"pd_pd_com":null,"st_id":0,"st_status":1,"pd_head_pic":"/uploads/20190828/94e31a86de680378a40198e8b42b4cb5.jpg","pd_code":null,"pd_pv":"953.80000","limitime":null,"last":0,"paytype":1,"advance":"0000-00-00 00:00:00","p_id":20,"ca_name":"果菜","ca_status":1,"ca_add_time":"2019-07-31 16:02:03","ca_sort":1,"ca_pic":"/uploads/20190828/3edecc3c2c37252b8e56d83269ce0108.jpg","ca_ca":0}],"coupon_num":12}
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
         * us_head_pic : /uploads/20190615/892e9d9a312c1225520bb0421c1a3b02.jpg
         * us_nickname : 初始测试员
         * out : 1
         * level_name : 一星玩家
         * level : 2
         * cash : 0.00
         * grb_cash : 727
         * hots : [{"id":5,"pd_name":"私密套盒3","pd_price":"4000.00000","pd_status":5,"pd_inventory":10000,"pd_sales":0,"pd_pic":"/uploads/20190612/84c92d08847dd6e9fd419371d34897e2.jpg,,/uploads/20190612/84c92d08847dd6e9fd419371d34897e2.jpg","pd_content":"私密","delete_time":null,"pd_add_time":"2019-06-12 09:28:15","ca_id":5,"pd_agency_price":"0.00","agency_status":2,"agency_num":10,"pd_detail":"","pd_province":null,"pd_place":"郑州","take_fee":"0.00","pd_color":null,"pd_date":"10年","pd_band":null,"pd_spec":null,"pd_pd_com":null,"st_id":0,"st_status":1,"pd_head_pic":"/uploads/20190612/8a9dd55f9fd79ebc8412cdd350d98390.jpg","pd_code":null,"pd_pv":"3000.00000","limitime":null,"last":0,"paytype":1,"advance":"0000-00-00 00:00:00","p_id":0,"ca_name":"水果","ca_status":1,"ca_add_time":"2018-08-24 10:20:10","ca_sort":20,"ca_pic":"/uploads/20190527/5a0ee270c5c7566ae3583ecba2d6863b.png","ca_ca":0},{"id":28,"pd_name":"私密套盒2","pd_price":"3000.00000","pd_status":5,"pd_inventory":9999,"pd_sales":1,"pd_pic":"/uploads/20190618/14373d28956032e03d583d1bb8722366.jpg,/uploads/20190618/31e1df9e71dacbd3086368d8339a6b41.jpg","pd_content":"","delete_time":null,"pd_add_time":"2019-08-29 16:16:19","ca_id":28,"pd_agency_price":"0.00","agency_status":2,"agency_num":10,"pd_detail":"","pd_province":null,"pd_place":"郑州","take_fee":"0.00","pd_color":null,"pd_date":"10年","pd_band":null,"pd_spec":null,"pd_pd_com":null,"st_id":0,"st_status":1,"pd_head_pic":"/uploads/20190612/39cafc249542b0c53a37834b1730dfcb.png","pd_code":null,"pd_pv":"2000.00000","limitime":"0","last":0,"paytype":1,"advance":"0000-00-00 00:00:00","p_id":27,"ca_name":"专供","ca_status":1,"ca_add_time":"2019-08-20 17:51:53","ca_sort":1,"ca_pic":"/uploads/20190820/a951a1d36432d0811e041e4ac5262062.png","ca_ca":0},{"id":21,"pd_name":"西红柿新鲜番茄自然熟非 有机水果","pd_price":"19.60000","pd_status":5,"pd_inventory":100,"pd_sales":0,"pd_pic":"/uploads/20190828/05f416920c63bb4e7715151eee78f1ff.jpg,,/uploads/20190828/05f416920c63bb4e7715151eee78f1ff.jpg","pd_content":"","delete_time":null,"pd_add_time":"2019-08-28 10:40:56","ca_id":21,"pd_agency_price":"0.00","agency_status":2,"agency_num":10,"pd_detail":"","pd_province":null,"pd_place":"郑州","take_fee":"0.00","pd_color":null,"pd_date":null,"pd_band":null,"pd_spec":null,"pd_pd_com":null,"st_id":0,"st_status":1,"pd_head_pic":"/uploads/20190828/0e9c0634a9ecfe5aeb30ba9d43d14929.jpg","pd_code":null,"pd_pv":"969.00000","limitime":null,"last":0,"paytype":1,"advance":"0000-00-00 00:00:00","p_id":20,"ca_name":"果菜","ca_status":1,"ca_add_time":"2019-07-31 16:02:03","ca_sort":1,"ca_pic":"/uploads/20190828/3edecc3c2c37252b8e56d83269ce0108.jpg","ca_ca":0},{"id":21,"pd_name":"新鲜水果小黄瓜5斤整箱 脆嫩荷兰小青瓜","pd_price":"26.30000","pd_status":5,"pd_inventory":100,"pd_sales":0,"pd_pic":"/uploads/20190828/edd376293aabe6dae3e2b306002927e2.jpg,,/uploads/20190828/edd376293aabe6dae3e2b306002927e2.jpg","pd_content":"","delete_time":null,"pd_add_time":"2019-08-28 10:47:55","ca_id":21,"pd_agency_price":"0.00","agency_status":2,"agency_num":10,"pd_detail":"","pd_province":null,"pd_place":"郑州","take_fee":"0.00","pd_color":null,"pd_date":null,"pd_band":null,"pd_spec":null,"pd_pd_com":null,"st_id":0,"st_status":1,"pd_head_pic":"/uploads/20190828/94e31a86de680378a40198e8b42b4cb5.jpg","pd_code":null,"pd_pv":"953.80000","limitime":null,"last":0,"paytype":1,"advance":"0000-00-00 00:00:00","p_id":20,"ca_name":"果菜","ca_status":1,"ca_add_time":"2019-07-31 16:02:03","ca_sort":1,"ca_pic":"/uploads/20190828/3edecc3c2c37252b8e56d83269ce0108.jpg","ca_ca":0}]
         * coupon_num : 12
         * grb_integral : 0.00000
         * gra : 5.00000
         */

        private String us_head_pic;
        private String us_nickname;
        private int out;
        private String level_name;
        private int level;
        private int is_realname;
        private int us_agent;
        private int us_agent2;
        private String cash;
        private String gra;
        private String grb_cash;
        private String grb_integral;
        private int coupon_num;
        private List<IndexCatesChildPageModel.DataBean> hots;

        public int getUs_agent() {
            return us_agent;
        }

        public void setUs_agent(int us_agent) {
            this.us_agent = us_agent;
        }

        public int getUs_agent2() {
            return us_agent2;
        }

        public void setUs_agent2(int us_agent2) {
            this.us_agent2 = us_agent2;
        }

        public String getUs_head_pic() {
            return us_head_pic;
        }

        public void setUs_head_pic(String us_head_pic) {
            this.us_head_pic = us_head_pic;
        }

        public String getUs_nickname() {
            return us_nickname;
        }

        public void setUs_nickname(String us_nickname) {
            this.us_nickname = us_nickname;
        }

        public int getOut() {
            return out;
        }

        public void setOut(int out) {
            this.out = out;
        }

        public String getLevel_name() {
            return level_name;
        }

        public void setLevel_name(String level_name) {
            this.level_name = level_name;
        }

        public String getCash() {
            return cash;
        }

        public void setCash(String cash) {
            this.cash = cash;
        }

        public String getGrb_cash() {
            return grb_cash;
        }

        public void setGrb_cash(String grb_cash) {
            this.grb_cash = grb_cash;
        }

        public int getCoupon_num() {
            return coupon_num;
        }

        public void setCoupon_num(int coupon_num) {
            this.coupon_num = coupon_num;
        }

        public List<IndexCatesChildPageModel.DataBean> getHots() {
            return hots;
        }

        public void setHots(List<IndexCatesChildPageModel.DataBean> hots) {
            this.hots = hots;
        }

        public String getGrb_integral() {
            return grb_integral;
        }

        public void setGrb_integral(String grb_integral) {
            this.grb_integral = grb_integral;
        }

        public int getLevel() {
            return level;
        }

        public void setLevel(int level) {
            this.level = level;
        }

        public int getIs_realname() {
            return is_realname;
        }

        public void setIs_realname(int is_realname) {
            this.is_realname = is_realname;
        }

        public String getGra() {
            return gra;
        }

        public void setGra(String gra) {
            this.gra = gra;
        }
    }
}
