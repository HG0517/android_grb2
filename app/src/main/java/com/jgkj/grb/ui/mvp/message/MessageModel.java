package com.jgkj.grb.ui.mvp.message;

import java.util.List;

/**
 * 消息
 * Created by brightpoplar@163.com on 2019/8/20.
 */
public class MessageModel {

    /**
     * code : 1
     * msg : 成功
     * time : 1568619583
     * data : [{"id":14,"add_time":"2018-08-09 17:30:45","title":"第2条新闻","pic":"/uploads/20180809\\6400d9bdf01d0cb6a7d088aa10ad62c9.png"},{"id":15,"add_time":"2018-08-09 17:31:36","title":"表彰大会","pic":"/uploads/20190826\\8a7a5f222586d1067bebf94990678d0d.png"}]
     */

    private int code;
    private String msg;
    private int time;
    private List<MessageBean> data;

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

    public List<MessageBean> getData() {
        return data;
    }

    public void setData(List<MessageBean> data) {
        this.data = data;
    }

    public static class MessageBean {
        /**
         * id : 14
         * add_time : 2018-08-09 17:30:45
         * title : 第2条新闻
         * pic : /uploads/20180809\6400d9bdf01d0cb6a7d088aa10ad62c9.png
         */

        private int id;
        private String add_time;
        private String title;
        private String pic;
        /**
         * content : <p>大萨达撒阿德萨打打<br/></p><p><img src="http://192.168.1.103:8004/ueditor/php/upload/image/20180809/1533807042.png" title="1533807042.png" alt="sc10.png"/></p>
         * simple : 代理商卡惊呆了空间撒的来看撒娇大立科技撒了肯德基流口水惊呆了空间撒来得快进口量撒娇镰刀快撒娇的来看撒娇的流口水甲氨蝶呤跨界
         * read : 2
         * type : 1
         * author : 谷聚金
         */

        private String content;
        private String simple;
        private int read;
        private int type;
        private String author;

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

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getSimple() {
            return simple;
        }

        public void setSimple(String simple) {
            this.simple = simple;
        }

        public int getRead() {
            return read;
        }

        public void setRead(int read) {
            this.read = read;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public String getAuthor() {
            return author;
        }

        public void setAuthor(String author) {
            this.author = author;
        }
    }
}
