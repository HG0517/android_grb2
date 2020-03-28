package com.jgkj.grb.ui.mvp.index;

import java.io.Serializable;

/**
 * 首页：分类，主分类，子分类
 * Created by brightpoplar@163.com on 2019/8/29.
 */
public class IndexCatesBean implements Serializable {
    /**
     * id : 28
     * ca_name : 专供
     * ca_pic : /uploads/20190820/a951a1d36432d0811e041e4ac5262062.png
     */

    private int id;
    private String ca_name;
    private String ca_pic;
    private String turn_pic;
    private boolean select;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCa_name() {
        return ca_name;
    }

    public void setCa_name(String ca_name) {
        this.ca_name = ca_name;
    }

    public String getCa_pic() {
        return ca_pic;
    }

    public void setCa_pic(String ca_pic) {
        this.ca_pic = ca_pic;
    }

    public String getTurn_pic() {
        return turn_pic;
    }

    public void setTurn_pic(String turn_pic) {
        this.turn_pic = turn_pic;
    }

    public boolean isSelect() {
        return select;
    }

    public void setSelect(boolean select) {
        this.select = select;
    }
}
