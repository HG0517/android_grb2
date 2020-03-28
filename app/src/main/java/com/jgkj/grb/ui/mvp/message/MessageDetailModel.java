package com.jgkj.grb.ui.mvp.message;

/**
 * 消息：详情
 * Created by brightpoplar@163.com on 2019/9/16.
 */
public class MessageDetailModel {

    /**
     * code : 1
     * msg : 成功
     * time : 1568621342
     * data : {"id":14,"title":"第2条新闻","content":"<p>大萨达撒阿德萨打打<br/><\/p><p><img src=\"http://192.168.1.103:8004/ueditor/php/upload/image/20180809/1533807042.png\" title=\"1533807042.png\" alt=\"sc10.png\"/><\/p>","add_time":"2018-08-09 17:30:45","simple":"代理商卡惊呆了空间撒的来看撒娇大立科技撒了肯德基流口水惊呆了空间撒来得快进口量撒娇镰刀快撒娇的来看撒娇的流口水甲氨蝶呤跨界","pic":"/uploads/20180809\\6400d9bdf01d0cb6a7d088aa10ad62c9.png","read":2,"type":1,"author":"谷聚金"}
     */

    private int code;
    private String msg;
    private int time;
    private MessageModel.MessageBean data;

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

    public MessageModel.MessageBean getData() {
        return data;
    }

    public void setData(MessageModel.MessageBean data) {
        this.data = data;
    }

}
