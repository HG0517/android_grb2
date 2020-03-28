package com.jgkj.grb.ui.mvp.personal;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * 个人中心：农场代理等级
 * Created by brightpoplar@163.com on 2019/9/18.
 */
public class RankFarmAgentModel {

    /**
     * code : 1
     * msg : 成功
     * time : 1568779243
     * data : [{"name":"体验玩家","0":["分享奖","农场丰收"]},{"name":"一星玩家","0":["分享奖","管理奖","农场丰收"]},{"name":"二星玩家","0":["分享奖","管理奖","关爱奖","感恩奖","农场丰收"]},{"name":"三星玩家","0":["分享奖","管理奖","关爱奖","感恩奖","农场丰收"]},{"name":"四星玩家","0":["分享奖","管理奖","关爱奖","感恩奖","农场丰收"]},{"name":"五星玩家","0":["分享奖","管理奖","关爱奖","感恩奖","农场丰收"]}]
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
         * name : 体验玩家
         * 0 : ["分享奖","农场丰收"]
         */

        private String name;
        @SerializedName("0")
        private List<String> list;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public List<String> getList() {
            return list;
        }

        public void setList(List<String> list) {
            this.list = list;
        }
    }
}
