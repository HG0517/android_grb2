package com.jgkj.grb.ui.mvp.find;

import java.util.List;

/**
 * 主界面：发现
 * Created by brightpoplar@163.com on 2019/8/30.
 */
public class FindModel {

    /**
     * code : 1
     * msg : 成功
     * time : 1567149988
     * data : {"hots":[{"id":16,"add_time":"2019-08-27 15:05:00","title":"最新热门话题","pic":"/uploads/20190827\\f5f43388201911ebb86688ff65c2aad3.png"}],"cate":[{"id":4,"cate_name":"大事记"},{"id":5,"cate_name":"知产品"},{"id":6,"cate_name":"爱分享"},{"id":20,"cate_name":"招商方案"}]}
     */

    private int code;
    private String msg;
    private int time;
    private DataBean data;

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

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        private List<HotsBean> hots;
        private List<CateBean> cate;

        public List<HotsBean> getHots() {
            return hots;
        }

        public void setHots(List<HotsBean> hots) {
            this.hots = hots;
        }

        public List<CateBean> getCate() {
            return cate;
        }

        public void setCate(List<CateBean> cate) {
            this.cate = cate;
        }

        public static class HotsBean {
            /**
             * id : 16
             * add_time : 2019-08-27 15:05:00
             * title : 最新热门话题
             * pic : /uploads/20190827\f5f43388201911ebb86688ff65c2aad3.png
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

        public static class CateBean {
            /**
             * id : 4
             * cate_name : 大事记
             */

            private int id;
            private String cate_name;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getCate_name() {
                return cate_name;
            }

            public void setCate_name(String cate_name) {
                this.cate_name = cate_name;
            }
        }
    }
}
