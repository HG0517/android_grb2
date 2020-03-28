package com.jgkj.grb.eventbus;

import java.util.Map;

/**
 * 推送消息提示
 * Created by brightpoplar@163.com on 2019/11/26.
 */
public class RefreshPushMsg {
    public Map<String, Integer> map;

    public RefreshPushMsg() {
    }

    public RefreshPushMsg(Map<String, Integer> map) {
        this.map = map;
    }
}
