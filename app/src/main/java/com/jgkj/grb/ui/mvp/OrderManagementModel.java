package com.jgkj.grb.ui.mvp;

import java.io.Serializable;
import java.util.List;

/**
 * 我的订单：订单列表数据
 * Created by brightpoplar@163.com on 2019/9/10.
 */
public class OrderManagementModel {

    /**
     * code : 1
     * msg : 成功
     * time : 1568096816
     * data : [{"id":86,"or_status":0,"total":10,"or_num":"20190908181","expire":1,"add_time":"2019-09-08 18:16:47","take_fee":10,"all_pv":300.2,"detail":[{"id":89,"or_id":86,"ca_id":21,"pd_id":131,"or_pd_name":"新鲜水果小黄瓜5斤整箱 脆嫩荷兰小青瓜","or_pd_pic":["/uploads/20190828/edd376293aabe6dae3e2b306002927e2.jpg","","/uploads/20190828/edd376293aabe6dae3e2b306002927e2.jpg"],"or_pd_pv":"300.20000","or_pd_price":"200.00000","or_pd_total":null,"delete_time":null,"or_num":null,"or_pd_num":1,"or_pd_content":"","skuCate":"默认","pd_spec":"6斤","or_sku_id":17,"paytype":1,"pd_head_pic":"/uploads/20190828/94e31a86de680378a40198e8b42b4cb5.jpg"}]},{"id":85,"or_status":0,"total":10,"or_num":"20190908181","expire":1,"add_time":"2019-09-08 18:16:12","take_fee":10,"all_pv":300.2,"detail":[{"id":88,"or_id":85,"ca_id":21,"pd_id":131,"or_pd_name":"新鲜水果小黄瓜5斤整箱 脆嫩荷兰小青瓜","or_pd_pic":["/uploads/20190828/edd376293aabe6dae3e2b306002927e2.jpg","","/uploads/20190828/edd376293aabe6dae3e2b306002927e2.jpg"],"or_pd_pv":"300.20000","or_pd_price":"200.00000","or_pd_total":null,"delete_time":null,"or_num":null,"or_pd_num":1,"or_pd_content":"","skuCate":"默认","pd_spec":"6斤","or_sku_id":17,"paytype":1,"pd_head_pic":"/uploads/20190828/94e31a86de680378a40198e8b42b4cb5.jpg"}]},{"id":84,"or_status":0,"total":10,"or_num":"20190908181","expire":1,"add_time":"2019-09-08 18:15:25","take_fee":10,"all_pv":300.2,"detail":[{"id":87,"or_id":84,"ca_id":21,"pd_id":131,"or_pd_name":"新鲜水果小黄瓜5斤整箱 脆嫩荷兰小青瓜","or_pd_pic":["/uploads/20190828/edd376293aabe6dae3e2b306002927e2.jpg","","/uploads/20190828/edd376293aabe6dae3e2b306002927e2.jpg"],"or_pd_pv":"300.20000","or_pd_price":"200.00000","or_pd_total":null,"delete_time":null,"or_num":null,"or_pd_num":1,"or_pd_content":"","skuCate":"默认","pd_spec":"6斤","or_sku_id":17,"paytype":1,"pd_head_pic":"/uploads/20190828/94e31a86de680378a40198e8b42b4cb5.jpg"}]},{"id":83,"or_status":0,"total":10,"or_num":"20190908180","expire":1,"add_time":"2019-09-08 18:09:12","take_fee":10,"all_pv":496.4,"detail":[{"id":86,"or_id":83,"ca_id":21,"pd_id":133,"or_pd_name":"贝贝南瓜板栗味日本小南 瓜带箱6斤","or_pd_pic":["/uploads/20190828/2a420e4bedbe3bdc95298b285d082b23.jpg","","/uploads/20190828/2a420e4bedbe3bdc95298b285d082b23.jpg"],"or_pd_pv":"124.10000","or_pd_price":"60.00000","or_pd_total":null,"delete_time":null,"or_num":null,"or_pd_num":4,"or_pd_content":"","skuCate":"默认","pd_spec":"3斤装","or_sku_id":15,"paytype":1,"pd_head_pic":"/uploads/20190828/715987cb00d9e8ebad5bb364865b4dd2.jpg"}]},{"id":82,"or_status":0,"total":10,"or_num":"20190908180","expire":1,"add_time":"2019-09-08 18:08:19","take_fee":10,"all_pv":50.23,"detail":[{"id":85,"or_id":82,"ca_id":21,"pd_id":132,"or_pd_name":"新鲜现摘芸豆带箱3斤装 山东特产","or_pd_pic":["/uploads/20190828/e348aa3b52e801d0f31a87b02032a81f.jpg","","/uploads/20190828/e348aa3b52e801d0f31a87b02032a81f.jpg"],"or_pd_pv":"50.23000","or_pd_price":"25.00000","or_pd_total":null,"delete_time":null,"or_num":null,"or_pd_num":1,"or_pd_content":"","skuCate":"默认","pd_spec":"5斤装","or_sku_id":16,"paytype":1,"pd_head_pic":"/uploads/20190828/23ad70290b597055aa1815bff167daa0.jpg"}]},{"id":81,"or_status":0,"total":10,"or_num":"20190908180","expire":1,"add_time":"2019-09-08 18:07:40","take_fee":10,"all_pv":50.23,"detail":[{"id":84,"or_id":81,"ca_id":21,"pd_id":132,"or_pd_name":"新鲜现摘芸豆带箱3斤装 山东特产","or_pd_pic":["/uploads/20190828/e348aa3b52e801d0f31a87b02032a81f.jpg","","/uploads/20190828/e348aa3b52e801d0f31a87b02032a81f.jpg"],"or_pd_pv":"50.23000","or_pd_price":"25.00000","or_pd_total":null,"delete_time":null,"or_num":null,"or_pd_num":1,"or_pd_content":"","skuCate":"默认","pd_spec":"5斤装","or_sku_id":16,"paytype":1,"pd_head_pic":"/uploads/20190828/23ad70290b597055aa1815bff167daa0.jpg"}]},{"id":80,"or_status":0,"total":0,"or_num":"20190908180","expire":1,"add_time":"2019-09-08 18:03:07","take_fee":0,"all_pv":50.23,"detail":[{"id":83,"or_id":80,"ca_id":21,"pd_id":132,"or_pd_name":"新鲜现摘芸豆带箱3斤装 山东特产","or_pd_pic":["/uploads/20190828/e348aa3b52e801d0f31a87b02032a81f.jpg","","/uploads/20190828/e348aa3b52e801d0f31a87b02032a81f.jpg"],"or_pd_pv":"50.23000","or_pd_price":"25.00000","or_pd_total":null,"delete_time":null,"or_num":null,"or_pd_num":1,"or_pd_content":"","skuCate":"默认","pd_spec":"5斤装","or_sku_id":16,"paytype":1,"pd_head_pic":"/uploads/20190828/23ad70290b597055aa1815bff167daa0.jpg"}]},{"id":79,"or_status":0,"total":0,"or_num":"20190908175","expire":1,"add_time":"2019-09-08 17:59:52","take_fee":0,"all_pv":50.23,"detail":[{"id":82,"or_id":79,"ca_id":21,"pd_id":132,"or_pd_name":"新鲜现摘芸豆带箱3斤装 山东特产","or_pd_pic":["/uploads/20190828/e348aa3b52e801d0f31a87b02032a81f.jpg","","/uploads/20190828/e348aa3b52e801d0f31a87b02032a81f.jpg"],"or_pd_pv":"50.23000","or_pd_price":"25.00000","or_pd_total":null,"delete_time":null,"or_num":null,"or_pd_num":1,"or_pd_content":"","skuCate":"默认","pd_spec":"5斤装","or_sku_id":16,"paytype":1,"pd_head_pic":"/uploads/20190828/23ad70290b597055aa1815bff167daa0.jpg"}]},{"id":78,"or_status":0,"total":0,"or_num":"20190908175","expire":1,"add_time":"2019-09-08 17:59:27","take_fee":0,"all_pv":0,"detail":[]},{"id":77,"or_status":0,"total":0,"or_num":"20190908175","expire":1,"add_time":"2019-09-08 17:52:02","take_fee":0,"all_pv":150.69,"detail":[{"id":81,"or_id":77,"ca_id":21,"pd_id":132,"or_pd_name":"新鲜现摘芸豆带箱3斤装 山东特产","or_pd_pic":["/uploads/20190828/e348aa3b52e801d0f31a87b02032a81f.jpg","","/uploads/20190828/e348aa3b52e801d0f31a87b02032a81f.jpg"],"or_pd_pv":"50.23000","or_pd_price":"25.00000","or_pd_total":null,"delete_time":null,"or_num":null,"or_pd_num":3,"or_pd_content":"","skuCate":"默认","pd_spec":"5斤装","or_sku_id":16,"paytype":1,"pd_head_pic":"/uploads/20190828/23ad70290b597055aa1815bff167daa0.jpg"}]}]
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
         * id : 86
         * or_status : 0
         * total : 10
         * or_num : 20190908181
         * expire : 1
         * add_time : 2019-09-08 18:16:47
         * take_fee : 10
         * all_pv : 300.2
         * detail : [{"id":89,"or_id":86,"ca_id":21,"pd_id":131,"or_pd_name":"新鲜水果小黄瓜5斤整箱 脆嫩荷兰小青瓜","or_pd_pic":["/uploads/20190828/edd376293aabe6dae3e2b306002927e2.jpg","","/uploads/20190828/edd376293aabe6dae3e2b306002927e2.jpg"],"or_pd_pv":"300.20000","or_pd_price":"200.00000","or_pd_total":null,"delete_time":null,"or_num":null,"or_pd_num":1,"or_pd_content":"","skuCate":"默认","pd_spec":"6斤","or_sku_id":17,"paytype":1,"pd_head_pic":"/uploads/20190828/94e31a86de680378a40198e8b42b4cb5.jpg"}]
         */

        int type;
        private int id;
        private int or_status;
        private String total;
        private String or_num;
        private int expire;
        private String add_time;
        private String take_fee;
        private String all_pv;
        private List<DetailBean> detail;
        /**
         * us_id : 1
         * or_id : 72
         * status : 1
         * num : 2019091015333100000008229
         * end_time : 0000-00-00 00:00:00
         * note : 7天无理由退款
         * examine_time : null
         * or_son_id : 76
         * content : 无需理由
         * express_name :
         * express_num :
         * pd_num : null
         * pic : null
         */

        private int us_id;
        private int or_id;
        private int status;
        private String num;
        private String end_time;
        private String note;
        private Object examine_time;
        private int or_son_id;
        private String content;
        private String express_name;
        private String express_num;
        private Object pd_num;
        private Object pic;
        /**
         * addr_tel : 15646161313
         * addr_receiver : 人防区分
         * addr : 四川眉山市东坡区规土委
         */

        private String addr_tel;
        private String addr_receiver;
        private String addr;

        public DataBean() {
        }

        public DataBean(int or_status) {
            this.or_status = or_status;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

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

        public String getTotal() {
            return total;
        }

        public void setTotal(String total) {
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

        public String getTake_fee() {
            return take_fee;
        }

        public void setTake_fee(String take_fee) {
            this.take_fee = take_fee;
        }

        public String getAll_pv() {
            return all_pv;
        }

        public void setAll_pv(String all_pv) {
            this.all_pv = all_pv;
        }

        public List<DetailBean> getDetail() {
            return detail;
        }

        public void setDetail(List<DetailBean> detail) {
            this.detail = detail;
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

        public String getExpress_name() {
            return express_name;
        }

        public void setExpress_name(String express_name) {
            this.express_name = express_name;
        }

        public String getExpress_num() {
            return express_num;
        }

        public void setExpress_num(String express_num) {
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

        public String getAddr() {
            return addr;
        }

        public void setAddr(String addr) {
            this.addr = addr;
        }

        public static class DetailBean implements Serializable {
            /**
             * id : 89
             * or_id : 86
             * ca_id : 21
             * pd_id : 131
             * or_pd_name : 新鲜水果小黄瓜5斤整箱 脆嫩荷兰小青瓜
             * or_pd_pic : ["/uploads/20190828/edd376293aabe6dae3e2b306002927e2.jpg","","/uploads/20190828/edd376293aabe6dae3e2b306002927e2.jpg"]
             * or_pd_pv : 300.20000
             * or_pd_price : 200.00000
             * or_pd_total : null
             * delete_time : null
             * or_num : null
             * or_pd_num : 1
             * or_pd_content :
             * skuCate : 默认
             * pd_spec : 6斤
             * or_sku_id : 17
             * paytype : 1
             * pd_head_pic : /uploads/20190828/94e31a86de680378a40198e8b42b4cb5.jpg
             * paytype1 : 0
             * paytype2 : 0
             * type4 :  1：显示or_pd_price; 2：显示or_pd_total
             */

            private int id;
            private int or_id;
            private int ca_id;
            private int pd_id;
            private String or_pd_name;
            private String or_pd_pv;
            private String or_pd_price;
            private String or_pd_total;
            private String delete_time;
            private String or_num;
            private int or_pd_num;
            private String or_pd_content;
            private String skuCate;
            private String pd_spec;
            private int or_sku_id;
            private int paytype;
            private String pd_head_pic;
            private List<String> or_pd_pic;
            private int paytype1;
            private int paytype2;
            private int type4;

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

            public String getDelete_time() {
                return delete_time;
            }

            public void setDelete_time(String delete_time) {
                this.delete_time = delete_time;
            }

            public String getOr_num() {
                return or_num;
            }

            public void setOr_num(String or_num) {
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

            public int getType4() {
                return type4;
            }

            public void setType4(int type4) {
                this.type4 = type4;
            }
        }
    }
}
