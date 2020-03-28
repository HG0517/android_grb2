package com.jgkj.grb.ui.bean;

/**
 * 申请退款：退款原因
 * Created by brightpoplar@163.com on 2019/8/22.
 */
public class RefundReasonsBean {
    private String reasons;
    private boolean select;

    public RefundReasonsBean() {
    }

    public RefundReasonsBean(String reasons) {
        this.reasons = reasons;
    }

    public RefundReasonsBean(String reasons, boolean select) {
        this.reasons = reasons;
        this.select = select;
    }

    public String getReasons() {
        return reasons;
    }

    public void setReasons(String reasons) {
        this.reasons = reasons;
    }

    public boolean isSelect() {
        return select;
    }

    public void setSelect(boolean select) {
        this.select = select;
    }
}
