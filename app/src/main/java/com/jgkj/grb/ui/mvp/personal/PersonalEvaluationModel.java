package com.jgkj.grb.ui.mvp.personal;

import java.io.Serializable;
import java.util.List;

/**
 * 个人中心：我的评价
 * Created by brightpoplar@163.com on 2019/9/19.
 */
public class PersonalEvaluationModel {

    /**
     * code : 1
     * msg : 成功
     * time : 1568864823
     * data : [{"id":1,"p_id":119,"us_id":1,"or_id":47,"level":1,"content":"这个商品好啊，真耐用","add_time":"2019-08-13 14:34:09","pic":["/uploads/20190803/9dcf5469dbb26831f3ca9b5bf01dcdae.png"],"anonymous":1,"or_son_id":72,"pd_pic":"/uploads/20190816/5cd98d83551a83e3225957a6acd99b65.png","pd_name":"大米 东北大米","pd_spec":"","us_head_pic":"/uploads/20190615/892e9d9a312c1225520bb0421c1a3b02.jpg","us_nickname":"初始测试员"},{"id":2,"p_id":119,"us_id":1,"or_id":48,"level":1,"content":"这是第二次购买这个商品了，非常好用，建议大家购买","add_time":"2019-08-28 14:36:29","pic":["/uploads/20190803/9dcf5469dbb26831f3ca9b5bf01dcdae.png"],"anonymous":1,"or_son_id":73,"pd_pic":"/uploads/20190816/5cd98d83551a83e3225957a6acd99b65.png","pd_name":"大米 东北大米","pd_spec":"","us_head_pic":"/uploads/20190615/892e9d9a312c1225520bb0421c1a3b02.jpg","us_nickname":"初始测试员"},{"id":3,"p_id":119,"us_id":1,"or_id":72,"level":1,"content":"新鲜","add_time":"2019-09-19 10:19:48","pic":["/uploads/20190919/6b4ebbf9d554b22e089b03c7878ccbc5.png"],"anonymous":1,"or_son_id":76,"pd_pic":"/uploads/20190828/2a420e4bedbe3bdc95298b285d082b23.jpg","pd_name":"贝贝南瓜板栗味日本小南 瓜带箱6斤","pd_spec":"3斤装","us_head_pic":"/uploads/20190615/892e9d9a312c1225520bb0421c1a3b02.jpg","us_nickname":"初始测试员"},{"id":5,"p_id":132,"us_id":1,"or_id":81,"level":2,"content":"芸豆不是豆！","add_time":"2019-09-19 10:45:47","pic":["/uploads/20190919/49cddaa7cf2e50ccec243bdfc6a0f86a.jpg","/uploads/20190919/d486422a845702f667bed07c8df55618.jpg","/uploads/20190919/762168a8d9f3606e455465e6be39df6c.jpg"],"anonymous":1,"or_son_id":84,"pd_pic":"/uploads/20190828/e348aa3b52e801d0f31a87b02032a81f.jpg","pd_name":"新鲜现摘芸豆带箱3斤装 山东特产","pd_spec":"5斤装","us_head_pic":"/uploads/20190615/892e9d9a312c1225520bb0421c1a3b02.jpg","us_nickname":"初始测试员"}]
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
         * p_id : 119
         * us_id : 1
         * or_id : 47
         * level : 1
         * content : 这个商品好啊，真耐用
         * add_time : 2019-08-13 14:34:09
         * pic : ["/uploads/20190803/9dcf5469dbb26831f3ca9b5bf01dcdae.png"]
         * anonymous : 1
         * or_son_id : 72
         * pd_pic : /uploads/20190816/5cd98d83551a83e3225957a6acd99b65.png
         * pd_name : 大米 东北大米
         * pd_spec :
         * us_head_pic : /uploads/20190615/892e9d9a312c1225520bb0421c1a3b02.jpg
         * us_nickname : 初始测试员
         * pd_price : 500.00000
         * pd_pv : 200.00000
         * pd_grb : 0.00000
         * pd_total : 0.00
         * pd_grc : 0.00000
         * paytype : 1
         * paytype1 : 0
         * paytype2 : 0
         */

        private int id;
        private int p_id;
        private int us_id;
        private int or_id;
        private int level;
        private String content;
        private String add_time;
        private int anonymous;
        private int or_son_id;
        private String pd_pic;
        private String pd_name;
        private String pd_spec;
        private String us_head_pic;
        private String us_nickname;
        private List<String> pic;
        private String pd_price;
        private String pd_pv;
        private String pd_grb;
        private String pd_total;
        private String pd_grc;
        private int paytype;
        private int paytype1;
        private int paytype2;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getP_id() {
            return p_id;
        }

        public void setP_id(int p_id) {
            this.p_id = p_id;
        }

        public int getUs_id() {
            return us_id;
        }

        public void setUs_id(int us_id) {
            this.us_id = us_id;
        }

        public int getOr_id() {
            return or_id;
        }

        public void setOr_id(int or_id) {
            this.or_id = or_id;
        }

        public int getLevel() {
            return level;
        }

        public void setLevel(int level) {
            this.level = level;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getAdd_time() {
            return add_time;
        }

        public void setAdd_time(String add_time) {
            this.add_time = add_time;
        }

        public int getAnonymous() {
            return anonymous;
        }

        public void setAnonymous(int anonymous) {
            this.anonymous = anonymous;
        }

        public int getOr_son_id() {
            return or_son_id;
        }

        public void setOr_son_id(int or_son_id) {
            this.or_son_id = or_son_id;
        }

        public String getPd_pic() {
            return pd_pic;
        }

        public void setPd_pic(String pd_pic) {
            this.pd_pic = pd_pic;
        }

        public String getPd_name() {
            return pd_name;
        }

        public void setPd_name(String pd_name) {
            this.pd_name = pd_name;
        }

        public String getPd_spec() {
            return pd_spec;
        }

        public void setPd_spec(String pd_spec) {
            this.pd_spec = pd_spec;
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

        public List<String> getPic() {
            return pic;
        }

        public void setPic(List<String> pic) {
            this.pic = pic;
        }

        public String getPd_price() {
            return pd_price;
        }

        public void setPd_price(String pd_price) {
            this.pd_price = pd_price;
        }

        public String getPd_pv() {
            return pd_pv;
        }

        public void setPd_pv(String pd_pv) {
            this.pd_pv = pd_pv;
        }

        public String getPd_grb() {
            return pd_grb;
        }

        public void setPd_grb(String pd_grb) {
            this.pd_grb = pd_grb;
        }

        public String getPd_total() {
            return pd_total;
        }

        public void setPd_total(String pd_total) {
            this.pd_total = pd_total;
        }

        public String getPd_grc() {
            return pd_grc;
        }

        public void setPd_grc(String pd_grc) {
            this.pd_grc = pd_grc;
        }

        public int getPaytype() {
            return paytype;
        }

        public void setPaytype(int paytype) {
            this.paytype = paytype;
        }

        public int getPaytype1() {
            return paytype1;
        }

        public void setPaytype1(int paytype1) {
            this.paytype1 = paytype1;
        }

        public int getPaytype2() {
            return paytype2;
        }

        public void setPaytype2(int paytype2) {
            this.paytype2 = paytype2;
        }
    }
}
