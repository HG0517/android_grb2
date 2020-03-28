package com.jgkj.grb.ui.gujujin.bean;

/**
 * 谷聚金代理主页
 * Created by brightpoplar@163.com on 2019/9/25.
 */
public class GujujinAgentBean {
    int type;
    int picRes;
    String picUrl;
    String name;
    String desc;

    public GujujinAgentBean() {
    }

    public GujujinAgentBean(int type, int picRes, String picUrl, String name, String desc) {
        this.type = type;
        this.picRes = picRes;
        this.picUrl = picUrl;
        this.name = name;
        this.desc = desc;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getPicRes() {
        return picRes;
    }

    public void setPicRes(int picRes) {
        this.picRes = picRes;
    }

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
