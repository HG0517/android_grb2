package com.jgkj.grb.ui.mvp.find;

import java.util.List;

/**
 * 主界面：发现，详情
 * Created by brightpoplar@163.com on 2019/8/31.
 */
public class FindDetailsModel {

    /**
     * code : 1
     * msg : 成功
     * time : 1567218582
     * data : {"news":[{"id":15,"title":"表彰大会","content":"<p style=\"text-align:center\"><img src=\"/ueditor/php/upload/image/20190826/1566803249.png\" title=\"1566803249.png\" alt=\"psd9dc.png\"/><\/p><p><br/><\/p>","add_time":"2018-08-09 17:31:36","simple":"金色梦想，荣耀绽放，谷聚金即公让宝表彰大会","pic":"/uploads/20190826\\8a7a5f222586d1067bebf94990678d0d.png","read":2441,"ca_id":4,"author":"谷聚金"}],"advisory":[{"id":1,"us_id":1,"coutent":"让农民种地不花钱，让人民吃上好食材！是我此生心愿","thumbs":14,"c_id":0,"a_id":15,"users":null,"us_nickname":"初始测试员","us_head_pic":"/uploads/20190615/892e9d9a312c1225520bb0421c1a3b02.jpg","secend":[{"id":4,"us_id":0,"coutent":"说的好，我们正在朝这个方向努力！","thumbs":0,"c_id":1,"a_id":0,"users":null}]},{"id":2,"us_id":2,"coutent":"让农民种地不花钱，让人民吃上好食材，改革开放40年让中国人吃饱，我们使命，让中国人吃好。","thumbs":0,"c_id":0,"a_id":15,"users":null,"us_nickname":"测试二号","us_head_pic":"/static/admin/img/head1.jpg","secend":[{"id":3,"us_id":0,"coutent":"为了中华民族的\u201c明天\u201d我们谷聚金的推广者任重道远。","thumbs":0,"c_id":2,"a_id":15,"users":null}]}]}
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
        private List<NewsBean> news;
        private List<AdvisoryBean> advisory;

        public List<NewsBean> getNews() {
            return news;
        }

        public void setNews(List<NewsBean> news) {
            this.news = news;
        }

        public List<AdvisoryBean> getAdvisory() {
            return advisory;
        }

        public void setAdvisory(List<AdvisoryBean> advisory) {
            this.advisory = advisory;
        }

        public static class NewsBean {
            /**
             * id : 15
             * title : 表彰大会
             * content : <p style="text-align:center"><img src="/ueditor/php/upload/image/20190826/1566803249.png" title="1566803249.png" alt="psd9dc.png"/></p><p><br/></p>
             * add_time : 2018-08-09 17:31:36
             * simple : 金色梦想，荣耀绽放，谷聚金即公让宝表彰大会
             * pic : /uploads/20190826\8a7a5f222586d1067bebf94990678d0d.png
             * read : 2441
             * ca_id : 4
             * author : 谷聚金
             */

            private int id;
            private String title;
            private String content;
            private String add_time;
            private String simple;
            private String pic;
            private int read;
            private int ca_id;
            private String author;
            private int sort;

            public int getSort() {
                return sort;
            }

            public void setSort(int sort) {
                this.sort = sort;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getContent() {
                return content;
            }

            public void setContent(String content) {
                this.content = content;
            }

            public String getAdd_time() {
                return add_time;
            }

            public void setAdd_time(String add_time) {
                this.add_time = add_time;
            }

            public String getSimple() {
                return simple;
            }

            public void setSimple(String simple) {
                this.simple = simple;
            }

            public String getPic() {
                return pic;
            }

            public void setPic(String pic) {
                this.pic = pic;
            }

            public int getRead() {
                return read;
            }

            public void setRead(int read) {
                this.read = read;
            }

            public int getCa_id() {
                return ca_id;
            }

            public void setCa_id(int ca_id) {
                this.ca_id = ca_id;
            }

            public String getAuthor() {
                return author;
            }

            public void setAuthor(String author) {
                this.author = author;
            }
        }

        public static class AdvisoryBean {
            /**
             * id : 1
             * us_id : 1
             * coutent : 让农民种地不花钱，让人民吃上好食材！是我此生心愿
             * thumbs : 14
             * c_id : 0
             * a_id : 15
             * users : null
             * us_nickname : 初始测试员
             * us_head_pic : /uploads/20190615/892e9d9a312c1225520bb0421c1a3b02.jpg
             * secend : [{"id":4,"us_id":0,"coutent":"说的好，我们正在朝这个方向努力！","thumbs":0,"c_id":1,"a_id":0,"users":null}]
             */

            private int id;
            private int us_id;
            private String coutent;
            private int thumbs;
            private int c_id;
            private int a_id;
            private List<String> users;
            private String us_nickname;
            private String us_head_pic;
            private AdvisoryBean secend;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public int getUs_id() {
                return us_id;
            }

            public void setUs_id(int us_id) {
                this.us_id = us_id;
            }

            public String getCoutent() {
                return coutent;
            }

            public void setCoutent(String coutent) {
                this.coutent = coutent;
            }

            public int getThumbs() {
                return thumbs;
            }

            public void setThumbs(int thumbs) {
                this.thumbs = thumbs;
            }

            public int getC_id() {
                return c_id;
            }

            public void setC_id(int c_id) {
                this.c_id = c_id;
            }

            public int getA_id() {
                return a_id;
            }

            public void setA_id(int a_id) {
                this.a_id = a_id;
            }

            public List<String> getUsers() {
                return users;
            }

            public void setUsers(List<String> users) {
                this.users = users;
            }

            public boolean itsMe(String uId) {
                return null != users && !users.isEmpty() && users.contains(uId);
            }

            public String getUs_nickname() {
                return us_nickname;
            }

            public void setUs_nickname(String us_nickname) {
                this.us_nickname = us_nickname;
            }

            public String getUs_head_pic() {
                return us_head_pic;
            }

            public void setUs_head_pic(String us_head_pic) {
                this.us_head_pic = us_head_pic;
            }

            public AdvisoryBean getSecend() {
                return secend;
            }

            public void setSecend(AdvisoryBean secend) {
                this.secend = secend;
            }
        }
    }
}
