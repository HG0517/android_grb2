package com.jgkj.grb.ui.mvp.product;

import android.os.Parcel;
import android.os.Parcelable;

import com.jgkj.grb.ui.mvp.index.IndexCatesChildPageModel;

import java.io.Serializable;
import java.util.List;

/**
 * 商品详情：普通商品、限时抢购商品、区域商品
 * Created by brightpoplar@163.com on 2019/8/29.
 */
public class ProductDetailsModel {

    /**
     * code : 1
     * msg : 成功
     * time : 1567043095
     * data : {"id":119,"pd_name":"大米 东北大米","pd_price":"500.00","pd_status":2,"pd_inventory":100,"pd_sales":658,"pd_pic":["/uploads/20190816/5cd98d83551a83e3225957a6acd99b65.png"],"pd_content":"持久留香，洁净皮肤的同时轻盈呵护，滋润保湿。","delete_time":null,"pd_add_time":"2019-08-28 14:23:20","ca_id":4,"pd_agency_price":"249.00","agency_status":1,"agency_num":1,"pd_detail":"<p style=\"text-align:center\"><img src=\"http://192.168.1.103:8004http://127.0.0.3:8004/ueditor/php/upload/image/20190816/1565939212.png\" title=\"1565939212.png\" alt=\"detail.png\"/><\/p><p><br/><\/p>","pd_place":"郑州","take_fee":"0.00","pd_color":"经典红 珍珠白 磨砂黑","pd_date":"10年","pd_band":"超级无敌","pd_spec":"无敌型","pd_pd_com":"无敌公司","st_id":0,"st_status":1,"pd_head_pic":"/uploads/20190816/181e427911c72e589a76881a3e8ac2b9.png","pd_code":"20180831asddsa","pd_pv":"200.00","limitime":1566973380,"last":0,"ca_name":"农药","skus":[{"id":2,"pd_id":119,"sku_name":"32盒108包","sku_cate_id":4,"sku_price":"4500.00","sku_pv":"4000.00","sku_num":0},{"id":3,"pd_id":119,"sku_name":"黑彩","sku_cate_id":0,"sku_price":"400.00","sku_pv":"0.01","sku_num":0}],"comment":[{"id":1,"p_id":119,"us_id":1,"or_id":47,"level":1,"content":"这个商品好啊，真耐用","add_time":"2019-08-13 14:34:09","pic":"/uploads/20190803/9dcf5469dbb26831f3ca9b5bf01dcdae.png,,/uploads/20190803/9dcf5469dbb26831f3ca9b5bf01dcdae.png","name":"初始测试员","head_pic":"/uploads/20190615/892e9d9a312c1225520bb0421c1a3b02.jpg"},{"id":2,"p_id":119,"us_id":1,"or_id":48,"level":1,"content":"这是第二次购买这个商品了，非常好用，建议大家购买","add_time":"2019-08-28 14:36:29","pic":"/uploads/20190803/9dcf5469dbb26831f3ca9b5bf01dcdae.png,,/uploads/20190803/9dcf5469dbb26831f3ca9b5bf01dcdae.png","name":"初始测试员","head_pic":"/uploads/20190615/892e9d9a312c1225520bb0421c1a3b02.jpg"}],"count":2,"coupon":[{"id":1,"p_id":119,"start":"2019-08-10 15:55:56","end":"2019-08-12 15:56:00","price":68,"bouns":5},{"id":2,"p_id":119,"start":"2019-08-10 15:56:46","end":"2019-08-13 15:56:50","price":100,"bouns":10},{"id":3,"p_id":119,"start":"2019-08-14 10:17:18","end":"2019-08-16 10:17:21","price":200,"bouns":20},{"id":5,"p_id":119,"start":"2019-08-16 16:18:00","end":"2019-08-17 16:18:00","price":68,"bouns":5}],"collection":0}
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

    public static class DataBean implements Parcelable {
        /**
         * id : 119
         * pd_name : 大米 东北大米
         * pd_price : 500.00
         * pd_status : 2
         * pd_inventory : 100
         * pd_sales : 658
         * pd_pic : ["/uploads/20190816/5cd98d83551a83e3225957a6acd99b65.png"]
         * pd_content : 持久留香，洁净皮肤的同时轻盈呵护，滋润保湿。
         * delete_time : null
         * pd_add_time : 2019-08-28 14:23:20
         * ca_id : 4
         * pd_agency_price : 249.00
         * agency_status : 1
         * agency_num : 1
         * pd_detail : <p style="text-align:center"><img src="http://192.168.1.103:8004http://127.0.0.3:8004/ueditor/php/upload/image/20190816/1565939212.png" title="1565939212.png" alt="detail.png"/></p><p><br/></p>
         * pd_place : 郑州
         * take_fee : 0.00
         * pd_color : 经典红 珍珠白 磨砂黑
         * pd_date : 10年
         * pd_band : 超级无敌
         * pd_spec : 无敌型
         * pd_pd_com : 无敌公司
         * st_id : 0
         * st_status : 1
         * pd_head_pic : /uploads/20190816/181e427911c72e589a76881a3e8ac2b9.png
         * pd_code : 20180831asddsa
         * pd_pv : 200.00
         * limitime : 1566973380
         * last : 0
         * ca_name : 农药
         * skus : [{"id":2,"pd_id":119,"sku_name":"32盒108包","sku_cate_id":4,"sku_price":"4500.00","sku_pv":"4000.00","sku_num":0},{"id":3,"pd_id":119,"sku_name":"黑彩","sku_cate_id":0,"sku_price":"400.00","sku_pv":"0.01","sku_num":0}]
         * comment : [{"id":1,"p_id":119,"us_id":1,"or_id":47,"level":1,"content":"这个商品好啊，真耐用","add_time":"2019-08-13 14:34:09","pic":"/uploads/20190803/9dcf5469dbb26831f3ca9b5bf01dcdae.png,,/uploads/20190803/9dcf5469dbb26831f3ca9b5bf01dcdae.png","name":"初始测试员","head_pic":"/uploads/20190615/892e9d9a312c1225520bb0421c1a3b02.jpg"},{"id":2,"p_id":119,"us_id":1,"or_id":48,"level":1,"content":"这是第二次购买这个商品了，非常好用，建议大家购买","add_time":"2019-08-28 14:36:29","pic":"/uploads/20190803/9dcf5469dbb26831f3ca9b5bf01dcdae.png,,/uploads/20190803/9dcf5469dbb26831f3ca9b5bf01dcdae.png","name":"初始测试员","head_pic":"/uploads/20190615/892e9d9a312c1225520bb0421c1a3b02.jpg"}]
         * count : 2
         * coupon : [{"id":1,"p_id":119,"start":"2019-08-10 15:55:56","end":"2019-08-12 15:56:00","price":68,"bouns":5},{"id":2,"p_id":119,"start":"2019-08-10 15:56:46","end":"2019-08-13 15:56:50","price":100,"bouns":10},{"id":3,"p_id":119,"start":"2019-08-14 10:17:18","end":"2019-08-16 10:17:21","price":200,"bouns":20},{"id":5,"p_id":119,"start":"2019-08-16 16:18:00","end":"2019-08-17 16:18:00","price":68,"bouns":5}]
         * collection : 0
         * pd_market_price : 0.00
         * pd_grc : 0.00000
         * pd_grb : 0.00000
         * paytype2 : 0
         * paytype1 : 0
         * pd_total : 2000.00000
         * or_remark : 留言
         */

        private int id;
        private String pd_name;
        private String pd_price;
        private int pd_status;
        private int pd_inventory;
        private int pd_sales;
        private String pd_content;
        private String delete_time;
        private String pd_add_time;
        private int ca_id;
        private String pd_agency_price;
        private int agency_status;
        private int agency_num;
        private String pd_detail;
        private String pd_place;
        private String take_fee;
        private String pd_color;
        private String pd_date;
        private String pd_band;
        private String pd_spec;
        private String pd_pd_com;
        private int st_id;
        private int st_status;
        private String pd_head_pic;
        private String pd_code;
        private String pd_pv;
        private String limitime;
        private int last;
        private String ca_name;
        private int count;
        private int collection;
        private List<String> pd_pic;
        private List<SkusBean> skus;
        private List<CommentBean> comment;
        private List<CouponBean> coupon;
        private List<IndexCatesChildPageModel.DataBean> hots;
        private String pd_province;
        private int paytype;
        private String advance;
        private String secend_price;
        private int store_id;
        private String pd_market_price;
        private String pd_grc;
        private String pd_grb;
        private int paytype2;
        private int paytype1;
        private String pd_total;
        private String or_remark;

        protected DataBean(Parcel in) {
            id = in.readInt();
            pd_name = in.readString();
            pd_price = in.readString();
            pd_status = in.readInt();
            pd_inventory = in.readInt();
            pd_sales = in.readInt();
            pd_content = in.readString();
            delete_time = in.readString();
            pd_add_time = in.readString();
            ca_id = in.readInt();
            pd_agency_price = in.readString();
            agency_status = in.readInt();
            agency_num = in.readInt();
            pd_detail = in.readString();
            pd_place = in.readString();
            take_fee = in.readString();
            pd_color = in.readString();
            pd_date = in.readString();
            pd_band = in.readString();
            pd_spec = in.readString();
            pd_pd_com = in.readString();
            st_id = in.readInt();
            st_status = in.readInt();
            pd_head_pic = in.readString();
            pd_code = in.readString();
            pd_pv = in.readString();
            limitime = in.readString();
            last = in.readInt();
            ca_name = in.readString();
            count = in.readInt();
            collection = in.readInt();
            pd_pic = in.createStringArrayList();
            skus = in.createTypedArrayList(SkusBean.CREATOR);
            comment = in.createTypedArrayList(CommentBean.CREATOR);
            coupon = in.createTypedArrayList(CouponBean.CREATOR);
            pd_province = in.readString();
            paytype = in.readInt();
            advance = in.readString();
            secend_price = in.readString();
            store_id = in.readInt();
            pd_market_price = in.readString();
            pd_grc = in.readString();
            pd_grb = in.readString();
            paytype2 = in.readInt();
            paytype1 = in.readInt();
        }

        public static final Creator<DataBean> CREATOR = new Creator<DataBean>() {
            @Override
            public DataBean createFromParcel(Parcel in) {
                return new DataBean(in);
            }

            @Override
            public DataBean[] newArray(int size) {
                return new DataBean[size];
            }
        };

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
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

        public int getPd_status() {
            return pd_status;
        }

        public void setPd_status(int pd_status) {
            this.pd_status = pd_status;
        }

        public int getPd_inventory() {
            return pd_inventory;
        }

        public void setPd_inventory(int pd_inventory) {
            this.pd_inventory = pd_inventory;
        }

        public int getPd_sales() {
            return pd_sales;
        }

        public void setPd_sales(int pd_sales) {
            this.pd_sales = pd_sales;
        }

        public String getPd_content() {
            return pd_content;
        }

        public void setPd_content(String pd_content) {
            this.pd_content = pd_content;
        }

        public String getDelete_time() {
            return delete_time;
        }

        public void setDelete_time(String delete_time) {
            this.delete_time = delete_time;
        }

        public String getPd_add_time() {
            return pd_add_time;
        }

        public void setPd_add_time(String pd_add_time) {
            this.pd_add_time = pd_add_time;
        }

        public int getCa_id() {
            return ca_id;
        }

        public void setCa_id(int ca_id) {
            this.ca_id = ca_id;
        }

        public String getPd_agency_price() {
            return pd_agency_price;
        }

        public void setPd_agency_price(String pd_agency_price) {
            this.pd_agency_price = pd_agency_price;
        }

        public int getAgency_status() {
            return agency_status;
        }

        public void setAgency_status(int agency_status) {
            this.agency_status = agency_status;
        }

        public int getAgency_num() {
            return agency_num;
        }

        public void setAgency_num(int agency_num) {
            this.agency_num = agency_num;
        }

        public String getPd_detail() {
            return pd_detail;
        }

        public void setPd_detail(String pd_detail) {
            this.pd_detail = pd_detail;
        }

        public String getPd_place() {
            return pd_place;
        }

        public void setPd_place(String pd_place) {
            this.pd_place = pd_place;
        }

        public String getTake_fee() {
            return take_fee;
        }

        public void setTake_fee(String take_fee) {
            this.take_fee = take_fee;
        }

        public String getPd_color() {
            return pd_color;
        }

        public void setPd_color(String pd_color) {
            this.pd_color = pd_color;
        }

        public String getPd_date() {
            return pd_date;
        }

        public void setPd_date(String pd_date) {
            this.pd_date = pd_date;
        }

        public String getPd_band() {
            return pd_band;
        }

        public void setPd_band(String pd_band) {
            this.pd_band = pd_band;
        }

        public String getPd_spec() {
            return pd_spec;
        }

        public void setPd_spec(String pd_spec) {
            this.pd_spec = pd_spec;
        }

        public String getPd_pd_com() {
            return pd_pd_com;
        }

        public void setPd_pd_com(String pd_pd_com) {
            this.pd_pd_com = pd_pd_com;
        }

        public int getSt_id() {
            return st_id;
        }

        public void setSt_id(int st_id) {
            this.st_id = st_id;
        }

        public int getSt_status() {
            return st_status;
        }

        public void setSt_status(int st_status) {
            this.st_status = st_status;
        }

        public String getPd_head_pic() {
            return pd_head_pic;
        }

        public void setPd_head_pic(String pd_head_pic) {
            this.pd_head_pic = pd_head_pic;
        }

        public String getPd_code() {
            return pd_code;
        }

        public void setPd_code(String pd_code) {
            this.pd_code = pd_code;
        }

        public String getPd_pv() {
            return pd_pv;
        }

        public void setPd_pv(String pd_pv) {
            this.pd_pv = pd_pv;
        }

        public String getLimitime() {
            return limitime;
        }

        public void setLimitime(String limitime) {
            this.limitime = limitime;
        }

        public int getLast() {
            return last;
        }

        public void setLast(int last) {
            this.last = last;
        }

        public String getCa_name() {
            return ca_name;
        }

        public void setCa_name(String ca_name) {
            this.ca_name = ca_name;
        }

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }

        public int getCollection() {
            return collection;
        }

        public void setCollection(int collection) {
            this.collection = collection;
        }

        public List<String> getPd_pic() {
            return pd_pic;
        }

        public void setPd_pic(List<String> pd_pic) {
            this.pd_pic = pd_pic;
        }

        public List<SkusBean> getSkus() {
            return skus;
        }

        public void setSkus(List<SkusBean> skus) {
            this.skus = skus;
        }

        public List<CommentBean> getComment() {
            return comment;
        }

        public void setComment(List<CommentBean> comment) {
            this.comment = comment;
        }

        public List<CouponBean> getCoupon() {
            return coupon;
        }

        public void setCoupon(List<CouponBean> coupon) {
            this.coupon = coupon;
        }

        public List<IndexCatesChildPageModel.DataBean> getHots() {
            return hots;
        }

        public void setHots(List<IndexCatesChildPageModel.DataBean> hots) {
            this.hots = hots;
        }

        public String getPd_province() {
            return pd_province;
        }

        public void setPd_province(String pd_province) {
            this.pd_province = pd_province;
        }

        public int getPaytype() {
            return paytype;
        }

        public void setPaytype(int paytype) {
            this.paytype = paytype;
        }

        public String getAdvance() {
            return advance;
        }

        public void setAdvance(String advance) {
            this.advance = advance;
        }

        public String getSecend_price() {
            return secend_price;
        }

        public void setSecend_price(String secend_price) {
            this.secend_price = secend_price;
        }

        public int getStore_id() {
            return store_id;
        }

        public void setStore_id(int store_id) {
            this.store_id = store_id;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {

            dest.writeInt(id);
            dest.writeString(pd_name);
            dest.writeString(pd_price);
            dest.writeInt(pd_status);
            dest.writeInt(pd_inventory);
            dest.writeInt(pd_sales);
            dest.writeString(pd_content);
            dest.writeString(delete_time);
            dest.writeString(pd_add_time);
            dest.writeInt(ca_id);
            dest.writeString(pd_agency_price);
            dest.writeInt(agency_status);
            dest.writeInt(agency_num);
            dest.writeString(pd_detail);
            dest.writeString(pd_place);
            dest.writeString(take_fee);
            dest.writeString(pd_color);
            dest.writeString(pd_date);
            dest.writeString(pd_band);
            dest.writeString(pd_spec);
            dest.writeString(pd_pd_com);
            dest.writeInt(st_id);
            dest.writeInt(st_status);
            dest.writeString(pd_head_pic);
            dest.writeString(pd_code);
            dest.writeString(pd_pv);
            dest.writeString(limitime);
            dest.writeInt(last);
            dest.writeString(ca_name);
            dest.writeInt(count);
            dest.writeInt(collection);
            dest.writeStringList(pd_pic);
            dest.writeTypedList(skus);
            dest.writeTypedList(comment);
            dest.writeTypedList(coupon);
            dest.writeString(pd_province);
            dest.writeInt(paytype);
            dest.writeString(advance);
            dest.writeString(secend_price);
            dest.writeInt(store_id);
            dest.writeString(pd_market_price);
            dest.writeString(pd_grc);
            dest.writeString(pd_grb);
            dest.writeInt(paytype2);
            dest.writeInt(paytype1);
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

        public String getPd_total() {
            return pd_total;
        }

        public void setPd_total(String pd_total) {
            this.pd_total = pd_total;
        }

        public String getOr_remark() {
            return or_remark;
        }

        public void setOr_remark(String or_remark) {
            this.or_remark = or_remark;
        }

        public static class SkusBean implements Parcelable {
            /**
             * id : 2
             * pd_id : 119
             * sku_name : 32盒108包
             * sku_cate_id : 4
             * sku_price : 4500.00
             * sku_pv : 4000.00
             * sku_num : 0
             * sku_grc : 0.00000
             * sku_grb : 0.00000
             * sku_total : 0.00
             */

            private int id;
            private int pd_id;
            private String sku_name;
            private int sku_cate_id;
            private String sku_price;
            private String sku_pv;
            private int sku_num;
            private String sku_grc;
            private String sku_grb;
            private String sku_total;

            protected SkusBean(Parcel in) {
                id = in.readInt();
                pd_id = in.readInt();
                sku_name = in.readString();
                sku_cate_id = in.readInt();
                sku_price = in.readString();
                sku_pv = in.readString();
                sku_num = in.readInt();
                sku_grc = in.readString();
                sku_grb = in.readString();
                sku_total = in.readString();
            }

            public static final Creator<SkusBean> CREATOR = new Creator<SkusBean>() {
                @Override
                public SkusBean createFromParcel(Parcel in) {
                    return new SkusBean(in);
                }

                @Override
                public SkusBean[] newArray(int size) {
                    return new SkusBean[size];
                }
            };

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public int getPd_id() {
                return pd_id;
            }

            public void setPd_id(int pd_id) {
                this.pd_id = pd_id;
            }

            public String getSku_name() {
                return sku_name;
            }

            public void setSku_name(String sku_name) {
                this.sku_name = sku_name;
            }

            public int getSku_cate_id() {
                return sku_cate_id;
            }

            public void setSku_cate_id(int sku_cate_id) {
                this.sku_cate_id = sku_cate_id;
            }

            public String getSku_price() {
                return sku_price;
            }

            public void setSku_price(String sku_price) {
                this.sku_price = sku_price;
            }

            public String getSku_pv() {
                return sku_pv;
            }

            public void setSku_pv(String sku_pv) {
                this.sku_pv = sku_pv;
            }

            public int getSku_num() {
                return sku_num;
            }

            public void setSku_num(int sku_num) {
                this.sku_num = sku_num;
            }

            public String getSku_grc() {
                return sku_grc;
            }

            public void setSku_grc(String sku_grc) {
                this.sku_grc = sku_grc;
            }

            public String getSku_grb() {
                return sku_grb;
            }

            public void setSku_grb(String sku_grb) {
                this.sku_grb = sku_grb;
            }

            public String getSku_total() {
                return sku_total;
            }

            public void setSku_total(String sku_total) {
                this.sku_total = sku_total;
            }

            @Override
            public int describeContents() {
                return 0;
            }

            @Override
            public void writeToParcel(Parcel dest, int flags) {
                dest.writeInt(id);
                dest.writeInt(pd_id);
                dest.writeString(sku_name);
                dest.writeInt(sku_cate_id);
                dest.writeString(sku_price);
                dest.writeString(sku_pv);
                dest.writeInt(sku_num);
                dest.writeString(sku_grc);
                dest.writeString(sku_grb);
                dest.writeString(sku_total);
            }
        }

        public static class CommentBean implements Parcelable{
            /**
             * id : 1
             * p_id : 119
             * us_id : 1
             * or_id : 47
             * level : 1
             * content : 这个商品好啊，真耐用
             * add_time : 2019-08-13 14:34:09
             * pic : /uploads/20190803/9dcf5469dbb26831f3ca9b5bf01dcdae.png,,/uploads/20190803/9dcf5469dbb26831f3ca9b5bf01dcdae.png
             * name : 初始测试员
             * head_pic : /uploads/20190615/892e9d9a312c1225520bb0421c1a3b02.jpg
             */

            private int id;
            private int p_id;
            private int us_id;
            private int or_id;
            private int level;
            private String content;
            private String add_time;
            private List<String> pic;
            private String name;
            private String head_pic;

            protected CommentBean(Parcel in) {
                id = in.readInt();
                p_id = in.readInt();
                us_id = in.readInt();
                or_id = in.readInt();
                level = in.readInt();
                content = in.readString();
                add_time = in.readString();
                pic = in.createStringArrayList();
                name = in.readString();
                head_pic = in.readString();
            }

            public static final Creator<CommentBean> CREATOR = new Creator<CommentBean>() {
                @Override
                public CommentBean createFromParcel(Parcel in) {
                    return new CommentBean(in);
                }

                @Override
                public CommentBean[] newArray(int size) {
                    return new CommentBean[size];
                }
            };

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

            public List<String> getPic() {
                return pic;
            }

            public void setPic(List<String> pic) {
                this.pic = pic;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getHead_pic() {
                return head_pic;
            }

            public void setHead_pic(String head_pic) {
                this.head_pic = head_pic;
            }

            @Override
            public int describeContents() {
                return 0;
            }

            @Override
            public void writeToParcel(Parcel dest, int flags) {
                dest.writeInt(id);
                dest.writeInt(p_id);
                dest.writeInt(us_id);
                dest.writeInt(or_id);
                dest.writeInt(level);
                dest.writeString(content);
                dest.writeString(add_time);
                dest.writeStringList(pic);
                dest.writeString(name);
                dest.writeString(head_pic);
            }
        }

        public static class CouponBean implements Parcelable {
            /**
             * id : 1
             * p_id : 119
             * start : 2019-08-10 15:55:56
             * end : 2019-08-12 15:56:00
             * price : 68
             * bouns : 5
             */

            private int id;
            private int p_id;
            private String start;
            private String end;
            private int price;
            private int bouns;
            private boolean security;

            protected CouponBean(Parcel in) {
                id = in.readInt();
                p_id = in.readInt();
                start = in.readString();
                end = in.readString();
                price = in.readInt();
                bouns = in.readInt();
                security = in.readByte() != 0;
            }

            public static final Creator<CouponBean> CREATOR = new Creator<CouponBean>() {
                @Override
                public CouponBean createFromParcel(Parcel in) {
                    return new CouponBean(in);
                }

                @Override
                public CouponBean[] newArray(int size) {
                    return new CouponBean[size];
                }
            };

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

            public String getStart() {
                return start;
            }

            public void setStart(String start) {
                this.start = start;
            }

            public String getEnd() {
                return end;
            }

            public void setEnd(String end) {
                this.end = end;
            }

            public int getPrice() {
                return price;
            }

            public void setPrice(int price) {
                this.price = price;
            }

            public int getBouns() {
                return bouns;
            }

            public void setBouns(int bouns) {
                this.bouns = bouns;
            }

            public boolean isSecurity() {
                return security;
            }

            public void setSecurity(boolean security) {
                this.security = security;
            }

            @Override
            public int describeContents() {
                return 0;
            }

            @Override
            public void writeToParcel(Parcel dest, int flags) {
                dest.writeInt(id);
                dest.writeInt(p_id);
                dest.writeString(start);
                dest.writeString(end);
                dest.writeInt(price);
                dest.writeInt(bouns);
                dest.writeByte((byte) (security ? 1 : 0));
            }
        }
    }
}
