package com.jgkj.grb.ui.mvp.luckdraw;

import java.io.Serializable;
import java.util.List;

/**
 * GRB 抽奖
 * Created by brightpoplar@163.com on 2019/9/2.
 */
public class LuckDrawIndexModel {

    /**
     * code : 1
     * msg : 成功
     * time : 1567414278
     * data : {"list":[{"id":0,"name":"iPhoneX一部","type":4,"num":1,"pic":"/uploads/20190831\\4657919784dd1358f60e479b9e79a789.jpg"},{"id":1,"name":"200优惠券","type":3,"num":1,"pic":"/uploads/20190831\\5c50bdc52c515ae2e0ba64201a357f99.jpg"},{"id":2,"name":"100GRB","type":2,"num":100,"pic":"/uploads/20190831\\1080916455c6e7bcd66396179e06fb7e.jpg"},{"id":3,"name":"谢谢参与","type":1,"num":1,"pic":"/uploads/20190831\\56c1b6d6bc51e94a7dae4562eb3b83a9.jpg"},{"id":4,"name":"50GRB","type":2,"num":50,"pic":"/uploads/20190831\\c2355e084e7ff86a9b1d3f9cacecccef.jpg"},{"id":5,"name":"150GRB","type":2,"num":150,"pic":"/uploads/20190831\\dcff71804bbe33d4a378d8998eb0ef6f.jpg"}],"background":"/uploads/20190902/7d54c772030171b02cbabf1cb655d927.png","draw_num":5,"new_num":12}
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
        /**
         * list : [{"id":0,"name":"iPhoneX一部","type":4,"num":1,"pic":"/uploads/20190831\\4657919784dd1358f60e479b9e79a789.jpg"},{"id":1,"name":"200优惠券","type":3,"num":1,"pic":"/uploads/20190831\\5c50bdc52c515ae2e0ba64201a357f99.jpg"},{"id":2,"name":"100GRB","type":2,"num":100,"pic":"/uploads/20190831\\1080916455c6e7bcd66396179e06fb7e.jpg"},{"id":3,"name":"谢谢参与","type":1,"num":1,"pic":"/uploads/20190831\\56c1b6d6bc51e94a7dae4562eb3b83a9.jpg"},{"id":4,"name":"50GRB","type":2,"num":50,"pic":"/uploads/20190831\\c2355e084e7ff86a9b1d3f9cacecccef.jpg"},{"id":5,"name":"150GRB","type":2,"num":150,"pic":"/uploads/20190831\\dcff71804bbe33d4a378d8998eb0ef6f.jpg"}]
         * background : /uploads/20190902/7d54c772030171b02cbabf1cb655d927.png
         * draw_num : 5
         * new_num : 12
         */

        private String background;
        private int draw_num;
        private int new_num;
        private int need;
        private List<ListBean> list;
        private DataResultBean result;

        public String getBackground() {
            return background;
        }

        public void setBackground(String background) {
            this.background = background;
        }

        public int getDraw_num() {
            return draw_num;
        }

        public void setDraw_num(int draw_num) {
            this.draw_num = draw_num;
        }

        public int getNew_num() {
            return new_num;
        }

        public void setNew_num(int new_num) {
            this.new_num = new_num;
        }

        public int getNeed() {
            return need;
        }

        public void setNeed(int need) {
            this.need = need;
        }

        public List<ListBean> getList() {
            return list;
        }

        public void setList(List<ListBean> list) {
            this.list = list;
        }

        public DataResultBean getResult() {
            return result;
        }

        public void setResult(DataResultBean result) {
            this.result = result;
        }

        public static class ListBean implements Serializable {
            /**
             * id : 0
             * name : iPhoneX一部
             * type : 4
             * num : 1
             * pic : /uploads/20190831\4657919784dd1358f60e479b9e79a789.jpg
             */

            private int id;
            private String name;
            private int type;
            private int num;
            private String pic;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public int getType() {
                return type;
            }

            public void setType(int type) {
                this.type = type;
            }

            public int getNum() {
                return num;
            }

            public void setNum(int num) {
                this.num = num;
            }

            public String getPic() {
                return pic;
            }

            public void setPic(String pic) {
                this.pic = pic;
            }
        }

        public static class DataResultBean {

            /**
             * code : 1
             * msg : 成功
             * time : 1567415510
             * data : {"msg":"谢谢参与","id":3}
             */

            private int code;
            private String msg;
            private int time;
            private ResultBean data;

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

            public ResultBean getData() {
                return data;
            }

            public void setData(ResultBean data) {
                this.data = data;
            }

            public static class ResultBean {
                /**
                 * msg : 谢谢参与
                 * id : 3
                 */

                private String msg;
                private int id;
                private int draw_num;

                public String getMsg() {
                    return msg;
                }

                public void setMsg(String msg) {
                    this.msg = msg;
                }

                public int getId() {
                    return id;
                }

                public void setId(int id) {
                    this.id = id;
                }

                public int getDraw_num() {
                    return draw_num;
                }

                public void setDraw_num(int draw_num) {
                    this.draw_num = draw_num;
                }
            }
        }
    }
}
