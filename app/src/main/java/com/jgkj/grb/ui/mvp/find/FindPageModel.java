package com.jgkj.grb.ui.mvp.find;

import java.util.List;

/**
 * 主界面：发现，标签页列表
 * Created by brightpoplar@163.com on 2019/8/30.
 */
public class FindPageModel {

    /**
     * code : 1
     * msg : 成功
     * time : 1567151161
     * data : [{"id":12,"add_time":"2018-07-26 09:47:44","title":"新年大吉，前10位消费的用户可享受5折优惠","pic":"/uploads/20180809\\b94dd7e112382421994951e1a87ee019.png"}]
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
         * id : 12
         * add_time : 2018-07-26 09:47:44
         * title : 新年大吉，前10位消费的用户可享受5折优惠
         * pic : /uploads/20180809\b94dd7e112382421994951e1a87ee019.png
         */

        private int id;
        private String add_time;
        private String title;
        private String pic;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getAdd_time() {
            return add_time;
        }

        public void setAdd_time(String add_time) {
            this.add_time = add_time;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getPic() {
            return pic;
        }

        public void setPic(String pic) {
            this.pic = pic;
        }
    }
}
