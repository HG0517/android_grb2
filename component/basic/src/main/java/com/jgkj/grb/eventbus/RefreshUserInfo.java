package com.jgkj.grb.eventbus;

/**
 * 个人中心：刷新用户信息
 * Created by brightpoplar@163.com on 2019/8/28.
 */
public class RefreshUserInfo {
    public boolean isLogin;

    public RefreshUserInfo() {
    }

    public RefreshUserInfo(boolean isLogin) {
        this.isLogin = isLogin;
    }
}
