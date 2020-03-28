package com.jgkj.grb.ui.mvp;

import com.jgkj.grb.ui.mvp.personal.AddresManagementModel;
import com.jgkj.grb.ui.mvp.shopcart.CartIndexBean;

import java.util.List;

/**
 * 结算
 * Created by brightpoplar@163.com on 2019/9/6.
 */
public class SettlementModel {

    /**
     * code : 1
     * msg : 成功
     * time : 1567766083
     * data : {"total":17100,"all_pv":16185.92,"list":[{"id":19,"us_id":1,"pd_id":119,"pd_num":3,"pd_pv":"4000.00000","pd_name":"大米 东北大米","pd_price":"4500.00000","pd_pic":"/uploads/20190816/5cd98d83551a83e3225957a6acd99b65.png","st_id":0,"st_name":"","pd_content":"持久留香，洁净皮肤的同时轻盈呵护，滋润保湿。","pd_color":"","pd_spec":"两盒24包","delete_time":null,"take_fee":"0.00","add_time":"2019-09-05 16:33:48","pd_head_pic":"/uploads/20190816/181e427911c72e589a76881a3e8ac2b9.png","ca_pid":0,"sku_id":2},{"id":20,"us_id":1,"pd_id":130,"pd_num":8,"pd_pv":"523.24000","pd_name":"西红柿新鲜番茄自然熟非 有机水果","pd_price":"450.00000","pd_pic":"/uploads/20190828/05f416920c63bb4e7715151eee78f1ff.jpg,,/uploads/20190828/05f416920c63bb4e7715151eee78f1ff.jpg","st_id":0,"st_name":"","pd_content":"","pd_color":"","pd_spec":"300斤","delete_time":null,"take_fee":"0.00","add_time":"2019-09-06 10:29:48","pd_head_pic":"/uploads/20190828/0e9c0634a9ecfe5aeb30ba9d43d14929.jpg","ca_pid":20,"sku_id":18}],"status":1,"addr":{"id":36,"addr_detail":"zhongguoguochandang","addr_default":1,"us_id":1,"delete_time":null,"add_time":"2019-09-04 14:16:25","addr_tel":"15238036423","addr_receiver":"Chen Jin lei","addr_code":"5,62,604","province":"甘肃","town":"皋兰县","city":"兰州","area":""}}
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
         * total : 17100
         * all_pv : 16185.92
         * list : [{"id":19,"us_id":1,"pd_id":119,"pd_num":3,"pd_pv":"4000.00000","pd_name":"大米 东北大米","pd_price":"4500.00000","pd_pic":"/uploads/20190816/5cd98d83551a83e3225957a6acd99b65.png","st_id":0,"st_name":"","pd_content":"持久留香，洁净皮肤的同时轻盈呵护，滋润保湿。","pd_color":"","pd_spec":"两盒24包","delete_time":null,"take_fee":"0.00","add_time":"2019-09-05 16:33:48","pd_head_pic":"/uploads/20190816/181e427911c72e589a76881a3e8ac2b9.png","ca_pid":0,"sku_id":2},{"id":20,"us_id":1,"pd_id":130,"pd_num":8,"pd_pv":"523.24000","pd_name":"西红柿新鲜番茄自然熟非 有机水果","pd_price":"450.00000","pd_pic":"/uploads/20190828/05f416920c63bb4e7715151eee78f1ff.jpg,,/uploads/20190828/05f416920c63bb4e7715151eee78f1ff.jpg","st_id":0,"st_name":"","pd_content":"","pd_color":"","pd_spec":"300斤","delete_time":null,"take_fee":"0.00","add_time":"2019-09-06 10:29:48","pd_head_pic":"/uploads/20190828/0e9c0634a9ecfe5aeb30ba9d43d14929.jpg","ca_pid":20,"sku_id":18}]
         * status : 1
         * sku_id : 26
         * pd_num : 120
         * agent_status : 0
         * addr : {"id":36,"addr_detail":"zhongguoguochandang","addr_default":1,"us_id":1,"delete_time":null,"add_time":"2019-09-04 14:16:25","addr_tel":"15238036423","addr_receiver":"Chen Jin lei","addr_code":"5,62,604","province":"甘肃","town":"皋兰县","city":"兰州","area":""}
         * cost : 60
         * grb : 0
         * grc : 0
         */

        private String total;
        private String all_pv;
        private String all_price;
        private int status;
        private String sku_id;
        private String pd_num;
        private int agent_status;
        private AddresManagementModel.DataBean addr;
        private List<CartIndexBean.CartBean> list;
        private String cost;
        private String grb;
        private String grc;

        public String getTotal() {
            return total;
        }

        public void setTotal(String total) {
            this.total = total;
        }

        public String getAll_pv() {
            return all_pv;
        }

        public void setAll_pv(String all_pv) {
            this.all_pv = all_pv;
        }

        public String getAll_price() {
            return all_price;
        }

        public void setAll_price(String all_price) {
            this.all_price = all_price;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public AddresManagementModel.DataBean getAddr() {
            return addr;
        }

        public void setAddr(AddresManagementModel.DataBean addr) {
            this.addr = addr;
        }

        public List<CartIndexBean.CartBean> getList() {
            return list;
        }

        public void setList(List<CartIndexBean.CartBean> list) {
            this.list = list;
        }

        public String getSku_id() {
            return sku_id;
        }

        public void setSku_id(String sku_id) {
            this.sku_id = sku_id;
        }

        public String getPd_num() {
            return pd_num;
        }

        public void setPd_num(String pd_num) {
            this.pd_num = pd_num;
        }

        public int getAgent_status() {
            return agent_status;
        }

        public void setAgent_status(int agent_status) {
            this.agent_status = agent_status;
        }

        public String getCost() {
            return cost;
        }

        public void setCost(String cost) {
            this.cost = cost;
        }

        public String getGrb() {
            return grb;
        }

        public void setGrb(String grb) {
            this.grb = grb;
        }

        public String getGrc() {
            return grc;
        }

        public void setGrc(String grc) {
            this.grc = grc;
        }
    }
}
