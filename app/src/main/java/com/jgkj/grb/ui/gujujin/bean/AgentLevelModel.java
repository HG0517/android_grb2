package com.jgkj.grb.ui.gujujin.bean;

import java.util.List;

/**
 * Created by brightpoplar@163.com on 2019/9/28.
 */
public class AgentLevelModel {

    /**
     * code : 1
     * msg : 成功
     * time : 1569662569
     * data : [{"id":1,"agent_name":"省代理","content":"1+1销售分红 在本代理区域每销售 一瓶2元分红,非区域 人员到本区域销售分 红1元 可推荐市代理,享受推 荐代理区域每瓶0."},{"id":2,"agent_name":"市代理","content":"2+2销售分红 在本代理区域,每销售 一瓶分红4元,非区域 人员到本区域销售分 红2元 可推荐市代理,享受推 荐代理区域每瓶0"},{"id":3,"agent_name":"区县代理","content":"在本代理区域每销售 一瓶分红10元，非本 区域人员到本区域或 本人跨区域销售分红 5元 可推荐市代理,享受推 荐代理区域每瓶0"},{"id":4,"agent_name":"个人代理","content":"在本代理区域每销售 一瓶分红10元，非本 区域人员到本区域或 本人跨区域销售分红 5元 可推荐市代理,享受推 荐代理区域每瓶0"},{"id":5,"agent_name":"一星  代理","content":"推荐5名个人代理和销 售业绩达到600瓶，晋 升为一星代理，可享受 团队个人代理新培业绩 每瓶一元的分红   "},{"id":6,"agent_name":"二星  代理","content":"团队产生4个一星和销 售业绩达到2400瓶， 晋升为二星代理，可享 受团队个人代理新增业 绩每瓶一元的分红    "},{"id":7,"agent_name":"三星  代理","content":"团队产生3个二星和销 售业绩达到7200瓶， 晋升为三星代理，可享 受团队个人代理新培业 绩每瓶一元的分红     "},{"id":8,"agent_name":"四星  代理","content":"团队产生3个三星和销 售业绩达21600瓶，晋 升为四星代理，可享受 团队个人代理新增业绩 每瓶一元的分红     "},{"id":9,"agent_name":"五星  代理","content":"团队产生3个四星和业绩 达到64800瓶，晋升为 五星代理，可享受团队个 人代理新增业绩每瓶一元 的分红 享受全球新增业绩每瓶"}]
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
         * id : 1
         * agent_name : 省代理
         * content : 1+1销售分红 在本代理区域每销售 一瓶2元分红,非区域 人员到本区域销售分 红1元 可推荐市代理,享受推 荐代理区域每瓶0.
         */

        private int id;
        private String agent_name;
        private String content;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getAgent_name() {
            return agent_name;
        }

        public void setAgent_name(String agent_name) {
            this.agent_name = agent_name;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }
    }
}
