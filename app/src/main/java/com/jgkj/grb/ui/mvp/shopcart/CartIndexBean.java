package com.jgkj.grb.ui.mvp.shopcart;

import java.util.List;

/**
 * 购物车
 * Created by brightpoplar@163.com on 2019/9/6.
 */
public class CartIndexBean {

    private String st_name;
    private List<CartBean> list;
    private boolean select;
    private boolean invalid;

    public String getSt_name() {
        return st_name;
    }

    public void setSt_name(String st_name) {
        this.st_name = st_name;
    }

    public List<CartBean> getList() {
        return list;
    }

    public void setList(List<CartBean> list) {
        this.list = list;
    }

    public boolean isSelect() {
        return select;
    }

    public void setSelect(boolean select) {
        this.select = select;
    }

    public boolean isInvalid() {
        return invalid;
    }

    public void setInvalid(boolean invalid) {
        this.invalid = invalid;
    }

    public static class CartBean {
        /**
         * id : 18
         * us_id : 1
         * pd_id : 119
         * pd_num : 2
         * pd_pv : 0.01000
         * pd_name : 大米 东北大米
         * pd_price : 400.00000
         * pd_pic : /uploads/20190816/5cd98d83551a83e3225957a6acd99b65.png
         * st_id : 0
         * st_name :
         * pd_content : 持久留香，洁净皮肤的同时轻盈呵护，滋润保湿。
         * pd_color :
         * pd_spec : 两盒24包
         * delete_time : null
         * take_fee : 0.00
         * add_time : 2019-09-05 16:33:38
         * pd_head_pic : /uploads/20190816/181e427911c72e589a76881a3e8ac2b9.png
         * ca_pid : 0
         * sku_id : 3
         * paytype : 1    // 1 现金不相加，2 现金相加
         * max_num : 100
         * addr_id : 36
         * or_pd_name : 贝贝南瓜板栗味日本小南 瓜带箱6斤
         * or_pd_num : 1
         * or_num : 20190910051
         * or_pd_price : 60.00000
         * or_pd_pic : /uploads/20190828/2a420e4bedbe3bdc95298b285d082b23.jpg,,/uploads/20190828/2a420e4bedbe3bdc95298b285d082b23.jpg
         * or_pd_pv : 124.10000
         * skuCate : null
         * province : 甘肃
         * city : 兰州
         * area :
         * addr_detail : zhongguoguochandang
         * addr_tel : 15238036423
         * addr_receiver : Chen Jin lei
         * pd_market_price : 0.00
         * pd_grc : 0.00000
         * pd_grb : 0.00000
         * pd_total : 969.00000
         * paytype2 : 0
         * paytype1 : 0
         * ca_id : 28
         * or_pd_total : 100.00
         */

        private int id;
        private int us_id;
        private int pd_id;
        private int pd_num;
        private String pd_pv;
        private String pd_name;
        private String pd_price;
        private String pd_pic;
        private int st_id;
        private String st_name;
        private String pd_content;
        private String pd_color;
        private String pd_spec;
        private Object delete_time;
        private String take_fee;
        private String add_time;
        private String pd_head_pic;
        private int ca_pid;
        private int sku_id;
        private int paytype;
        private int max_num;
        private boolean select;
        private int addr_id;
        private String or_pd_name;
        private int or_pd_num;
        private String or_num;
        private String or_pd_price;
        private String or_pd_total;
        private String or_pd_pic;
        private String or_pd_pv;
        private Object skuCate;
        private String province;
        private String city;
        private String area;
        private String addr_detail;
        private String addr_tel;
        private String addr_receiver;
        private String pd_market_price;
        private String pd_grc;
        private String pd_grb;
        private String pd_total;
        private int paytype2;
        private int paytype1;
        private int type4;
        private int ca_id;

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

        public int getPd_id() {
            return pd_id;
        }

        public void setPd_id(int pd_id) {
            this.pd_id = pd_id;
        }

        public int getPd_num() {
            return pd_num;
        }

        public void setPd_num(int pd_num) {
            this.pd_num = pd_num;
        }

        public String getPd_pv() {
            return pd_pv;
        }

        public void setPd_pv(String pd_pv) {
            this.pd_pv = pd_pv;
        }

        public String getPd_name() {
            return pd_name;
        }

        public void setPd_name(String pd_name) {
            this.pd_name = pd_name;
        }

        public String getPd_price() {
            return pd_price;
        }

        public void setPd_price(String pd_price) {
            this.pd_price = pd_price;
        }

        public String getPd_pic() {
            return pd_pic;
        }

        public void setPd_pic(String pd_pic) {
            this.pd_pic = pd_pic;
        }

        public int getSt_id() {
            return st_id;
        }

        public void setSt_id(int st_id) {
            this.st_id = st_id;
        }

        public String getSt_name() {
            return st_name;
        }

        public void setSt_name(String st_name) {
            this.st_name = st_name;
        }

        public String getPd_content() {
            return pd_content;
        }

        public void setPd_content(String pd_content) {
            this.pd_content = pd_content;
        }

        public String getPd_color() {
            return pd_color;
        }

        public void setPd_color(String pd_color) {
            this.pd_color = pd_color;
        }

        public String getPd_spec() {
            return pd_spec;
        }

        public void setPd_spec(String pd_spec) {
            this.pd_spec = pd_spec;
        }

        public Object getDelete_time() {
            return delete_time;
        }

        public void setDelete_time(Object delete_time) {
            this.delete_time = delete_time;
        }

        public String getTake_fee() {
            return take_fee;
        }

        public void setTake_fee(String take_fee) {
            this.take_fee = take_fee;
        }

        public String getAdd_time() {
            return add_time;
        }

        public void setAdd_time(String add_time) {
            this.add_time = add_time;
        }

        public String getPd_head_pic() {
            return pd_head_pic;
        }

        public void setPd_head_pic(String pd_head_pic) {
            this.pd_head_pic = pd_head_pic;
        }

        public int getCa_pid() {
            return ca_pid;
        }

        public void setCa_pid(int ca_pid) {
            this.ca_pid = ca_pid;
        }

        public int getSku_id() {
            return sku_id;
        }

        public void setSku_id(int sku_id) {
            this.sku_id = sku_id;
        }

        public int getPaytype() {
            return paytype;
        }

        public void setPaytype(int paytype) {
            this.paytype = paytype;
        }

        public int getMax_num() {
            return max_num;
        }

        public void setMax_num(int max_num) {
            this.max_num = max_num;
        }

        public boolean isSelect() {
            return select;
        }

        public void setSelect(boolean select) {
            this.select = select;
        }

        public int getAddr_id() {
            return addr_id;
        }

        public void setAddr_id(int addr_id) {
            this.addr_id = addr_id;
        }

        public String getOr_pd_name() {
            return or_pd_name;
        }

        public void setOr_pd_name(String or_pd_name) {
            this.or_pd_name = or_pd_name;
        }

        public int getOr_pd_num() {
            return or_pd_num;
        }

        public void setOr_pd_num(int or_pd_num) {
            this.or_pd_num = or_pd_num;
        }

        public String getOr_num() {
            return or_num;
        }

        public void setOr_num(String or_num) {
            this.or_num = or_num;
        }

        public String getOr_pd_price() {
            return or_pd_price;
        }

        public void setOr_pd_price(String or_pd_price) {
            this.or_pd_price = or_pd_price;
        }

        public String getOr_pd_pic() {
            return or_pd_pic;
        }

        public void setOr_pd_pic(String or_pd_pic) {
            this.or_pd_pic = or_pd_pic;
        }

        public String getOr_pd_pv() {
            return or_pd_pv;
        }

        public void setOr_pd_pv(String or_pd_pv) {
            this.or_pd_pv = or_pd_pv;
        }

        public Object getSkuCate() {
            return skuCate;
        }

        public void setSkuCate(Object skuCate) {
            this.skuCate = skuCate;
        }

        public String getProvince() {
            return province;
        }

        public void setProvince(String province) {
            this.province = province;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getArea() {
            return area;
        }

        public void setArea(String area) {
            this.area = area;
        }

        public String getAddr_detail() {
            return addr_detail;
        }

        public void setAddr_detail(String addr_detail) {
            this.addr_detail = addr_detail;
        }

        public String getAddr_tel() {
            return addr_tel;
        }

        public void setAddr_tel(String addr_tel) {
            this.addr_tel = addr_tel;
        }

        public String getAddr_receiver() {
            return addr_receiver;
        }

        public void setAddr_receiver(String addr_receiver) {
            this.addr_receiver = addr_receiver;
        }

        public String getPd_market_price() {
            return pd_market_price;
        }

        public void setPd_market_price(String pd_market_price) {
            this.pd_market_price = pd_market_price;
        }

        public String getPd_grc() {
            return pd_grc;
        }

        public void setPd_grc(String pd_grc) {
            this.pd_grc = pd_grc;
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

        public int getPaytype2() {
            return paytype2;
        }

        public void setPaytype2(int paytype2) {
            this.paytype2 = paytype2;
        }

        public int getPaytype1() {
            return paytype1;
        }

        public void setPaytype1(int paytype1) {
            this.paytype1 = paytype1;
        }

        public int getCa_id() {
            return ca_id;
        }

        public void setCa_id(int ca_id) {
            this.ca_id = ca_id;
        }

        public String getOr_pd_total() {
            return or_pd_total;
        }

        public void setOr_pd_total(String or_pd_total) {
            this.or_pd_total = or_pd_total;
        }

        public int getType4() {
            return type4;
        }

        public void setType4(int type4) {
            this.type4 = type4;
        }
    }
}
