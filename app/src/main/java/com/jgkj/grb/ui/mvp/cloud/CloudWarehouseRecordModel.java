package com.jgkj.grb.ui.mvp.cloud;

import java.util.List;

/**
 * 云仓库
 * Created by brightpoplar@163.com on 2019/8/20.
 */
public class CloudWarehouseRecordModel {

    /**
     * code : 1
     * msg : 成功
     * time : 1568945994
     * data : [{"id":102,"or_status":0,"total":0,"or_num":"20190919170","expire":1,"add_time":"2019-09-19 19:05:34","take_fee":0,"all_pv":124.1,"detail":[{"id":107,"or_id":102,"ca_id":21,"pd_id":133,"or_pd_name":"贝贝南瓜板栗味日本小南 瓜带箱6斤","or_pd_pic":["/uploads/20190828/715987cb00d9e8ebad5bb364865b4dd2.jpg"],"or_pd_pv":"124.10000","or_pd_price":"60.00000","or_pd_total":null,"delete_time":null,"or_num":null,"or_pd_num":1,"or_pd_content":"","skuCate":"默认","pd_spec":"3斤装","or_sku_id":15,"paytype":1,"back":0,"pd_head_pic":"/uploads/20190828/715987cb00d9e8ebad5bb364865b4dd2.jpg"}]}]
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
         * id : 102
         * or_status : 0
         * total : 0
         * or_num : 20190919170
         * expire : 1
         * add_time : 2019-09-19 19:05:34
         * take_fee : 0
         * all_pv : 124.1
         * detail : [{"id":107,"or_id":102,"ca_id":21,"pd_id":133,"or_pd_name":"贝贝南瓜板栗味日本小南 瓜带箱6斤","or_pd_pic":["/uploads/20190828/715987cb00d9e8ebad5bb364865b4dd2.jpg"],"or_pd_pv":"124.10000","or_pd_price":"60.00000","or_pd_total":null,"delete_time":null,"or_num":null,"or_pd_num":1,"or_pd_content":"","skuCate":"默认","pd_spec":"3斤装","or_sku_id":15,"paytype":1,"back":0,"pd_head_pic":"/uploads/20190828/715987cb00d9e8ebad5bb364865b4dd2.jpg"}]
         */

        private int id;
        private int or_status;
        private int total;
        private String or_num;
        private int expire;
        private String add_time;
        private int take_fee;
        private double all_pv;
        private List<DetailBean> detail;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getOr_status() {
            return or_status;
        }

        public void setOr_status(int or_status) {
            this.or_status = or_status;
        }

        public int getTotal() {
            return total;
        }

        public void setTotal(int total) {
            this.total = total;
        }

        public String getOr_num() {
            return or_num;
        }

        public void setOr_num(String or_num) {
            this.or_num = or_num;
        }

        public int getExpire() {
            return expire;
        }

        public void setExpire(int expire) {
            this.expire = expire;
        }

        public String getAdd_time() {
            return add_time;
        }

        public void setAdd_time(String add_time) {
            this.add_time = add_time;
        }

        public int getTake_fee() {
            return take_fee;
        }

        public void setTake_fee(int take_fee) {
            this.take_fee = take_fee;
        }

        public double getAll_pv() {
            return all_pv;
        }

        public void setAll_pv(double all_pv) {
            this.all_pv = all_pv;
        }

        public List<DetailBean> getDetail() {
            return detail;
        }

        public void setDetail(List<DetailBean> detail) {
            this.detail = detail;
        }

        public static class DetailBean {
            /**
             * id : 107
             * or_id : 102
             * ca_id : 21
             * pd_id : 133
             * or_pd_name : 贝贝南瓜板栗味日本小南 瓜带箱6斤
             * or_pd_pic : ["/uploads/20190828/715987cb00d9e8ebad5bb364865b4dd2.jpg"]
             * or_pd_pv : 124.10000
             * or_pd_price : 60.00000
             * or_pd_total : null
             * delete_time : null
             * or_num : null
             * or_pd_num : 1
             * or_pd_content :
             * skuCate : 默认
             * pd_spec : 3斤装
             * or_sku_id : 15
             * paytype : 1
             * back : 0
             * pd_head_pic : /uploads/20190828/715987cb00d9e8ebad5bb364865b4dd2.jpg
             * paytype1 : 0
             * paytype2 : 0
             */

            private int id;
            private int or_id;
            private int ca_id;
            private int pd_id;
            private String or_pd_name;
            private String or_pd_pv;
            private String or_pd_price;
            private String or_pd_total;
            private Object delete_time;
            private Object or_num;
            private int or_pd_num;
            private String or_pd_content;
            private String skuCate;
            private String pd_spec;
            private int or_sku_id;
            private int paytype;
            private int back;
            private String pd_head_pic;
            private List<String> or_pd_pic;
            private int paytype1;
            private int paytype2;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public int getOr_id() {
                return or_id;
            }

            public void setOr_id(int or_id) {
                this.or_id = or_id;
            }

            public int getCa_id() {
                return ca_id;
            }

            public void setCa_id(int ca_id) {
                this.ca_id = ca_id;
            }

            public int getPd_id() {
                return pd_id;
            }

            public void setPd_id(int pd_id) {
                this.pd_id = pd_id;
            }

            public String getOr_pd_name() {
                return or_pd_name;
            }

            public void setOr_pd_name(String or_pd_name) {
                this.or_pd_name = or_pd_name;
            }

            public String getOr_pd_pv() {
                return or_pd_pv;
            }

            public void setOr_pd_pv(String or_pd_pv) {
                this.or_pd_pv = or_pd_pv;
            }

            public String getOr_pd_price() {
                return or_pd_price;
            }

            public void setOr_pd_price(String or_pd_price) {
                this.or_pd_price = or_pd_price;
            }

            public String getOr_pd_total() {
                return or_pd_total;
            }

            public void setOr_pd_total(String or_pd_total) {
                this.or_pd_total = or_pd_total;
            }

            public Object getDelete_time() {
                return delete_time;
            }

            public void setDelete_time(Object delete_time) {
                this.delete_time = delete_time;
            }

            public Object getOr_num() {
                return or_num;
            }

            public void setOr_num(Object or_num) {
                this.or_num = or_num;
            }

            public int getOr_pd_num() {
                return or_pd_num;
            }

            public void setOr_pd_num(int or_pd_num) {
                this.or_pd_num = or_pd_num;
            }

            public String getOr_pd_content() {
                return or_pd_content;
            }

            public void setOr_pd_content(String or_pd_content) {
                this.or_pd_content = or_pd_content;
            }

            public String getSkuCate() {
                return skuCate;
            }

            public void setSkuCate(String skuCate) {
                this.skuCate = skuCate;
            }

            public String getPd_spec() {
                return pd_spec;
            }

            public void setPd_spec(String pd_spec) {
                this.pd_spec = pd_spec;
            }

            public int getOr_sku_id() {
                return or_sku_id;
            }

            public void setOr_sku_id(int or_sku_id) {
                this.or_sku_id = or_sku_id;
            }

            public int getPaytype() {
                return paytype;
            }

            public void setPaytype(int paytype) {
                this.paytype = paytype;
            }

            public int getBack() {
                return back;
            }

            public void setBack(int back) {
                this.back = back;
            }

            public String getPd_head_pic() {
                return pd_head_pic;
            }

            public void setPd_head_pic(String pd_head_pic) {
                this.pd_head_pic = pd_head_pic;
            }

            public List<String> getOr_pd_pic() {
                return or_pd_pic;
            }

            public void setOr_pd_pic(List<String> or_pd_pic) {
                this.or_pd_pic = or_pd_pic;
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
}
