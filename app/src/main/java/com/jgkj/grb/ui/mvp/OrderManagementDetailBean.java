package com.jgkj.grb.ui.mvp;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by brightpoplar@163.com on 2019/9/11.
 */
public class OrderManagementDetailBean implements Serializable {
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
     */

    private int id;
    private int or_id;
    private int ca_id;
    private int pd_id;
    private String or_pd_name;
    private String or_pd_pv;
    private String or_pd_price;
    private Object or_pd_total;
    private Object delete_time;
    private Object or_num;
    private int or_pd_num;
    private String or_pd_content;
    private String skuCate;
    private String pd_spec;
    private int or_sku_id;
    private int paytype;
    private String pd_head_pic;
    private List<String> or_pd_pic;
    /**
     * or_pd_pic : /uploads/20190828/2a420e4bedbe3bdc95298b285d082b23.jpg,,/uploads/20190828/2a420e4bedbe3bdc95298b285d082b23.jpg
     */

    @SerializedName("or_pd_pic")
    private String or_pd_picX;

    public String getOr_pd_picX() {
        return or_pd_picX;
    }

    public void setOr_pd_picX(String or_pd_picX) {
        this.or_pd_picX = or_pd_picX;
    }

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

    public Object getOr_pd_total() {
        return or_pd_total;
    }

    public void setOr_pd_total(Object or_pd_total) {
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

}
