package com.jgkj.grb.ui.mvp;

import java.util.List;

/**
 * 退款/售后：查看详情
 * Created by brightpoplar@163.com on 2019/9/11.
 */
public class ApplyAfterDetailModel {

    /**
     * code : 1
     * msg : 成功
     * time : 1568194330
     * data : {"id":5,"us_id":1,"or_id":77,"status":1,"num":"2019091115295500000023469","add_time":"2019-09-11 15:29:55","end_time":"0000-00-00 00:00:00","note":"其他","examine_time":null,"type":1,"or_son_id":81,"content":"就是想退了他！！","express_name":null,"express_num":null,"pd_num":null,"pic":null,"detail":[{"id":81,"or_id":77,"ca_id":21,"pd_id":132,"or_pd_name":"新鲜现摘芸豆带箱3斤装 山东特产","or_pd_pic":["/uploads/20190828/e348aa3b52e801d0f31a87b02032a81f.jpg"],"or_pd_pv":"50.23000","or_pd_price":"25.00000","or_pd_total":null,"delete_time":null,"or_num":null,"or_pd_num":3,"or_pd_content":"","skuCate":"默认","pd_spec":"5斤装","or_sku_id":16,"paytype":1}]}
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
         * id : 5
         * us_id : 1
         * or_id : 77
         * status : 1
         * num : 2019091115295500000023469
         * add_time : 2019-09-11 15:29:55
         * end_time : 0000-00-00 00:00:00
         * note : 其他
         * examine_time : null
         * type : 1
         * or_son_id : 81
         * content : 就是想退了他！！
         * express_name : null
         * express_num : null
         * pd_num : null
         * pic : null
         * detail : [{"id":81,"or_id":77,"ca_id":21,"pd_id":132,"or_pd_name":"新鲜现摘芸豆带箱3斤装 山东特产","or_pd_pic":["/uploads/20190828/e348aa3b52e801d0f31a87b02032a81f.jpg"],"or_pd_pv":"50.23000","or_pd_price":"25.00000","or_pd_total":null,"delete_time":null,"or_num":null,"or_pd_num":3,"or_pd_content":"","skuCate":"默认","pd_spec":"5斤装","or_sku_id":16,"paytype":1}]
         */

        private int id;
        private int us_id;
        private int or_id;
        private int status;
        private String num;
        private String add_time;
        private String end_time;
        private String note;
        private Object examine_time;
        private int type;
        private int or_son_id;
        private String content;
        private Object express_name;
        private Object express_num;
        private Object pd_num;
        private Object pic;
        private List<DetailBean> detail;

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

        public int getOr_id() {
            return or_id;
        }

        public void setOr_id(int or_id) {
            this.or_id = or_id;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public String getNum() {
            return num;
        }

        public void setNum(String num) {
            this.num = num;
        }

        public String getAdd_time() {
            return add_time;
        }

        public void setAdd_time(String add_time) {
            this.add_time = add_time;
        }

        public String getEnd_time() {
            return end_time;
        }

        public void setEnd_time(String end_time) {
            this.end_time = end_time;
        }

        public String getNote() {
            return note;
        }

        public void setNote(String note) {
            this.note = note;
        }

        public Object getExamine_time() {
            return examine_time;
        }

        public void setExamine_time(Object examine_time) {
            this.examine_time = examine_time;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public int getOr_son_id() {
            return or_son_id;
        }

        public void setOr_son_id(int or_son_id) {
            this.or_son_id = or_son_id;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public Object getExpress_name() {
            return express_name;
        }

        public void setExpress_name(Object express_name) {
            this.express_name = express_name;
        }

        public Object getExpress_num() {
            return express_num;
        }

        public void setExpress_num(Object express_num) {
            this.express_num = express_num;
        }

        public Object getPd_num() {
            return pd_num;
        }

        public void setPd_num(Object pd_num) {
            this.pd_num = pd_num;
        }

        public Object getPic() {
            return pic;
        }

        public void setPic(Object pic) {
            this.pic = pic;
        }

        public List<DetailBean> getDetail() {
            return detail;
        }

        public void setDetail(List<DetailBean> detail) {
            this.detail = detail;
        }

        public static class DetailBean {
            /**
             * id : 81
             * or_id : 77
             * ca_id : 21
             * pd_id : 132
             * or_pd_name : 新鲜现摘芸豆带箱3斤装 山东特产
             * or_pd_pic : ["/uploads/20190828/e348aa3b52e801d0f31a87b02032a81f.jpg"]
             * or_pd_pv : 50.23000
             * or_pd_price : 25.00000
             * or_pd_total : null
             * delete_time : null
             * or_num : null
             * or_pd_num : 3
             * or_pd_content :
             * skuCate : 默认
             * pd_spec : 5斤装
             * or_sku_id : 16
             * paytype : 1
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
            private List<String> or_pd_pic;

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

            public List<String> getOr_pd_pic() {
                return or_pd_pic;
            }

            public void setOr_pd_pic(List<String> or_pd_pic) {
                this.or_pd_pic = or_pd_pic;
            }
        }
    }
}
