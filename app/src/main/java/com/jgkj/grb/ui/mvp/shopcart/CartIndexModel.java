package com.jgkj.grb.ui.mvp.shopcart;

import java.util.List;

/**
 * 购物车列表
 * Created by brightpoplar@163.com on 2019/9/6.
 */
public class CartIndexModel {

    /**
     * code : 1
     * msg : 成功
     * time : 1567754209
     * data : [{"st_name":"公让宝专营","list":[{"id":18,"us_id":1,"pd_id":119,"pd_num":2,"pd_pv":"0.01000","pd_name":"大米 东北大米","pd_price":"400.00000","pd_pic":"/uploads/20190816/5cd98d83551a83e3225957a6acd99b65.png","st_id":0,"st_name":"","pd_content":"持久留香，洁净皮肤的同时轻盈呵护，滋润保湿。","pd_color":"","pd_spec":"两盒24包","delete_time":null,"take_fee":"0.00","add_time":"2019-09-05 16:33:38","pd_head_pic":"/uploads/20190816/181e427911c72e589a76881a3e8ac2b9.png","ca_pid":0,"sku_id":3,"max_num":100},{"id":20,"us_id":1,"pd_id":130,"pd_num":8,"pd_pv":"523.24000","pd_name":"西红柿新鲜番茄自然熟非 有机水果","pd_price":"450.00000","pd_pic":"/uploads/20190828/05f416920c63bb4e7715151eee78f1ff.jpg,,/uploads/20190828/05f416920c63bb4e7715151eee78f1ff.jpg","st_id":0,"st_name":"","pd_content":"","pd_color":"","pd_spec":"300斤","delete_time":null,"take_fee":"0.00","add_time":"2019-09-06 10:29:48","pd_head_pic":"/uploads/20190828/0e9c0634a9ecfe5aeb30ba9d43d14929.jpg","ca_pid":20,"sku_id":18,"max_num":100},{"id":19,"us_id":1,"pd_id":119,"pd_num":3,"pd_pv":"4000.00000","pd_name":"大米 东北大米","pd_price":"4500.00000","pd_pic":"/uploads/20190816/5cd98d83551a83e3225957a6acd99b65.png","st_id":0,"st_name":"","pd_content":"持久留香，洁净皮肤的同时轻盈呵护，滋润保湿。","pd_color":"","pd_spec":"两盒24包","delete_time":null,"take_fee":"0.00","add_time":"2019-09-05 16:33:48","pd_head_pic":"/uploads/20190816/181e427911c72e589a76881a3e8ac2b9.png","ca_pid":0,"sku_id":2,"max_num":100},{"id":21,"us_id":1,"pd_id":133,"pd_num":6,"pd_pv":"124.10000","pd_name":"贝贝南瓜板栗味日本小南 瓜带箱6斤","pd_price":"60.00000","pd_pic":"/uploads/20190828/2a420e4bedbe3bdc95298b285d082b23.jpg,,/uploads/20190828/2a420e4bedbe3bdc95298b285d082b23.jpg","st_id":0,"st_name":"","pd_content":"","pd_color":"","pd_spec":"3斤装","delete_time":null,"take_fee":"0.00","add_time":"2019-09-06 11:06:06","pd_head_pic":"/uploads/20190828/715987cb00d9e8ebad5bb364865b4dd2.jpg","ca_pid":20,"sku_id":15,"max_num":100},{"id":22,"us_id":1,"pd_id":132,"pd_num":2,"pd_pv":"50.23000","pd_name":"新鲜现摘芸豆带箱3斤装 山东特产","pd_price":"25.00000","pd_pic":"/uploads/20190828/e348aa3b52e801d0f31a87b02032a81f.jpg,,/uploads/20190828/e348aa3b52e801d0f31a87b02032a81f.jpg","st_id":0,"st_name":"","pd_content":"","pd_color":"","pd_spec":"5斤装","delete_time":null,"take_fee":"0.00","add_time":"2019-09-06 15:16:35","pd_head_pic":"/uploads/20190828/23ad70290b597055aa1815bff167daa0.jpg","ca_pid":20,"sku_id":16,"max_num":100}]}]
     */

    private int code;
    private String msg;
    private int time;
    private List<CartIndexBean> data;

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

    public List<CartIndexBean> getData() {
        return data;
    }

    public void setData(List<CartIndexBean> data) {
        this.data = data;
    }

}
