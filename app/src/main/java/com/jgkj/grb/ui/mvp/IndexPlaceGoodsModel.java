package com.jgkj.grb.ui.mvp;

import com.jgkj.grb.ui.mvp.index.GoodBeanModel;

import java.util.List;

/**
 * 首页：基地，商品
 * Created by brightpoplar@163.com on 2019/9/12.
 */
public class IndexPlaceGoodsModel {

    /**
     * code : 1
     * msg : 成功
     * time : 1568270214
     * data : [{"id":120,"pd_name":"2套私密套盒","pd_price":"2000.00000","pd_status":4,"pd_inventory":100,"pd_sales":0,"pd_pic":"/uploads/20181208/ef229c3b159173f155b524fdfa1bd135.jpg,/uploads/20181208/5b6f1c30cdc0f24fd3378262ce977987.jpg,/uploads/20181208/ef229c3b159173f155b524fdfa1bd135.jpg,/uploads/20181208/5b6f1c30cdc0f24fd3378262ce977987.jpg","pd_content":"清香洗发露清香洗发露清香洗发露清香洗发露清香洗发露清香洗发露清香洗发露清香洗发露","delete_time":null,"pd_add_time":"2019-09-08 14:58:34","ca_id":31,"pd_agency_price":"1000.00","agency_status":2,"agency_num":10,"pd_detail":"<p style=\"text-align:center\"><img src=\"/ueditor/php/upload/image/20190908/1567925908.png\" title=\"1567925908.png\" alt=\"detail.png\"/><\/p><p><br/><\/p>","pd_province":4,"pd_place":"郑州","take_fee":"0.00","pd_color":"无","pd_date":"10年","pd_band":"无","pd_spec":"无","pd_pd_com":"无","st_id":0,"st_status":1,"pd_head_pic":"/uploads/20180831/b531fe71995d52a6a82b4c1327a8348a.png","pd_code":"20180831asddsa","pd_pv":"1500.00000","limitime":"0","last":0,"paytype":1}]
     */

    private int code;
    private String msg;
    private int time;
    private List<GoodBeanModel> data;

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

    public List<GoodBeanModel> getData() {
        return data;
    }

    public void setData(List<GoodBeanModel> data) {
        this.data = data;
    }

}
