package com.jgkj.grb.ui.mvp.index;

import java.util.List;

/**
 * 首页：子分类列表，子分类对应的数据，顶部图片、子分类列表
 * Created by brightpoplar@163.com on 2019/8/29.
 */
public class IndexCatesChildListModel {

    /**
     * code : 1
     * msg : 成功
     * time : 1567067786
     * data : {"shuffling":["/uploads/20190828\\18e179d0577dca34d6d50edc8b35d8a5.jpg"],"sons":[{"id":28,"ca_name":"专供","ca_pic":"/uploads/20190820/a951a1d36432d0811e041e4ac5262062.png"},{"id":29,"ca_name":"预售","ca_pic":"/uploads/20190820/3f6013d0389ccee5eaedf19944b49406.png"},{"id":30,"ca_name":"代理","ca_pic":"/uploads/20190820/78c67d39ce10b884fc18adb08772a4d1.jpg"},{"id":31,"ca_name":"基地","ca_pic":"/uploads/20190820/eb2c40ae5626a61dc91a9f1821c13e68.png"},{"id":32,"ca_name":"拼团","ca_pic":"/uploads/20190820/32d7c9b28c01c43ba7e648b441270b36.png"}],"hots":[{"id":128,"pd_name":"私密套盒2","pd_price":"3000.00","pd_status":5,"pd_inventory":9999,"pd_sales":1,"pd_pic":["/uploads/20190618/14373d28956032e03d583d1bb8722366.jpg","/uploads/20190618/31e1df9e71dacbd3086368d8339a6b41.jpg"],"pd_content":"","delete_time":null,"pd_add_time":"2019-08-29 16:16:19","ca_id":28,"pd_agency_price":"0.00","agency_status":2,"agency_num":10,"pd_detail":"","pd_place":"郑州","take_fee":"0.00","pd_color":null,"pd_date":"10年","pd_band":null,"pd_spec":null,"pd_pd_com":null,"st_id":0,"st_status":1,"pd_head_pic":"/uploads/20190612/39cafc249542b0c53a37834b1730dfcb.png","pd_code":null,"pd_pv":"2000.00","limitime":"0","last":0,"ca_name":"专供","p_id":27}],"meeting":[{"id":1,"title":"最新商品发布会","content":"新品大米上市，大家赶紧来抢购，先到先得，来即优惠。","tel":"15538927152","address":"河南省郑州市桐柏路27号","time":"2019-08-30","add_time":"2019-08-14 17:12:04","coordinate":"354313.45545,5466.545153","radius":1}],"background":"/uploads/20190828/59bf262e678bd55f30fded4a338efc81.png","limit":"/uploads/20190828/c308c5f86e925096890b2cdcb4aa72e6.png","limit1":"/uploads/20190829/f37ea7db9d97e6c1d25f0be780ee9d63.png","limit2":"/uploads/20190829/2a1dbd017f23d9c4d44f4a518d96823f.png","limit3":"/uploads/20190829/24a8ff040c92d50c875219a037800e09.jpg"}
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
         * shuffling : ["/uploads/20190828\\18e179d0577dca34d6d50edc8b35d8a5.jpg"]
         * sons : [{"id":28,"ca_name":"专供","ca_pic":"/uploads/20190820/a951a1d36432d0811e041e4ac5262062.png"},{"id":29,"ca_name":"预售","ca_pic":"/uploads/20190820/3f6013d0389ccee5eaedf19944b49406.png"},{"id":30,"ca_name":"代理","ca_pic":"/uploads/20190820/78c67d39ce10b884fc18adb08772a4d1.jpg"},{"id":31,"ca_name":"基地","ca_pic":"/uploads/20190820/eb2c40ae5626a61dc91a9f1821c13e68.png"},{"id":32,"ca_name":"拼团","ca_pic":"/uploads/20190820/32d7c9b28c01c43ba7e648b441270b36.png"}]
         * hots : [{"id":128,"pd_name":"私密套盒2","pd_price":"3000.00","pd_status":5,"pd_inventory":9999,"pd_sales":1,"pd_pic":["/uploads/20190618/14373d28956032e03d583d1bb8722366.jpg","/uploads/20190618/31e1df9e71dacbd3086368d8339a6b41.jpg"],"pd_content":"","delete_time":null,"pd_add_time":"2019-08-29 16:16:19","ca_id":28,"pd_agency_price":"0.00","agency_status":2,"agency_num":10,"pd_detail":"","pd_place":"郑州","take_fee":"0.00","pd_color":null,"pd_date":"10年","pd_band":null,"pd_spec":null,"pd_pd_com":null,"st_id":0,"st_status":1,"pd_head_pic":"/uploads/20190612/39cafc249542b0c53a37834b1730dfcb.png","pd_code":null,"pd_pv":"2000.00","limitime":"0","last":0,"ca_name":"专供","p_id":27}]
         * meeting : [{"id":1,"title":"最新商品发布会","content":"新品大米上市，大家赶紧来抢购，先到先得，来即优惠。","tel":"15538927152","address":"河南省郑州市桐柏路27号","time":"2019-08-30","add_time":"2019-08-14 17:12:04","coordinate":"354313.45545,5466.545153","radius":1}]
         * background : /uploads/20190828/59bf262e678bd55f30fded4a338efc81.png
         * limit : /uploads/20190828/c308c5f86e925096890b2cdcb4aa72e6.png
         * limit1 : {"limit1":{"pic":"/uploads/20190829/f37ea7db9d97e6c1d25f0be780ee9d63.png","id":"134"},"limit2":{"pic":"/uploads/20190829/2a1dbd017f23d9c4d44f4a518d96823f.png","id":"127"},"limit3":{"pic":"/uploads/20190829/24a8ff040c92d50c875219a037800e09.jpg","id":"127"}}
         * limit2 : 1571727600
         */

        private String background;
        private String limit;
        private List<String> shuffling;
        private List<IndexCatesBean> sons;
        private List<GoodBeanModel> hots;
        private List<MeetingBean> meeting;
        private Limit1Bean limit1;
        private String limit2;

        public String getBackground() {
            return background;
        }

        public void setBackground(String background) {
            this.background = background;
        }

        public String getLimit() {
            return limit;
        }

        public void setLimit(String limit) {
            this.limit = limit;
        }

        public List<String> getShuffling() {
            return shuffling;
        }

        public void setShuffling(List<String> shuffling) {
            this.shuffling = shuffling;
        }

        public List<IndexCatesBean> getSons() {
            return sons;
        }

        public void setSons(List<IndexCatesBean> sons) {
            this.sons = sons;
        }

        public List<GoodBeanModel> getHots() {
            return hots;
        }

        public void setHots(List<GoodBeanModel> hots) {
            this.hots = hots;
        }

        public List<MeetingBean> getMeeting() {
            return meeting;
        }

        public void setMeeting(List<MeetingBean> meeting) {
            this.meeting = meeting;
        }

        public Limit1Bean getLimit1() {
            return limit1;
        }

        public void setLimit1(Limit1Bean limit1) {
            this.limit1 = limit1;
        }

        public String getLimit2() {
            return limit2;
        }

        public void setLimit2(String limit2) {
            this.limit2 = limit2;
        }

        public static class MeetingBean {
            /**
             * id : 1
             * title : 最新商品发布会
             * content : 新品大米上市，大家赶紧来抢购，先到先得，来即优惠。
             * tel : 15538927152
             * address : 河南省郑州市桐柏路27号
             * time : 2019-08-30
             * add_time : 2019-08-14 17:12:04
             * coordinate : 354313.45545,5466.545153
             * radius : 1
             */

            private int id;
            private String title;
            private String content;
            private String tel;
            private String address;
            private String time;
            private String add_time;
            private String coordinate;
            private int radius;

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

            public String getTel() {
                return tel;
            }

            public void setTel(String tel) {
                this.tel = tel;
            }

            public String getAddress() {
                return address;
            }

            public void setAddress(String address) {
                this.address = address;
            }

            public String getTime() {
                return time;
            }

            public void setTime(String time) {
                this.time = time;
            }

            public String getAdd_time() {
                return add_time;
            }

            public void setAdd_time(String add_time) {
                this.add_time = add_time;
            }

            public String getCoordinate() {
                return coordinate;
            }

            public void setCoordinate(String coordinate) {
                this.coordinate = coordinate;
            }

            public int getRadius() {
                return radius;
            }

            public void setRadius(int radius) {
                this.radius = radius;
            }
        }

        public static class Limit1Bean {
            /**
             * limit1 : {"pic":"/uploads/20190829/f37ea7db9d97e6c1d25f0be780ee9d63.png","id":"134"}
             * limit2 : {"pic":"/uploads/20190829/2a1dbd017f23d9c4d44f4a518d96823f.png","id":"127"}
             * limit3 : {"pic":"/uploads/20190829/24a8ff040c92d50c875219a037800e09.jpg","id":"127"}
             */

            private Limit1DataBean limit1;
            private Limit1DataBean limit2;
            private Limit1DataBean limit3;

            public Limit1DataBean getLimit1() {
                return limit1;
            }

            public void setLimit1(Limit1DataBean limit1) {
                this.limit1 = limit1;
            }

            public Limit1DataBean getLimit2() {
                return limit2;
            }

            public void setLimit2(Limit1DataBean limit2) {
                this.limit2 = limit2;
            }

            public Limit1DataBean getLimit3() {
                return limit3;
            }

            public void setLimit3(Limit1DataBean limit3) {
                this.limit3 = limit3;
            }

            public static class Limit1DataBean {
                /**
                 * pic : /uploads/20190829/f37ea7db9d97e6c1d25f0be780ee9d63.png
                 * id : 134
                 */

                private String pic;
                private String id;

                public String getPic() {
                    return pic;
                }

                public void setPic(String pic) {
                    this.pic = pic;
                }

                public String getId() {
                    return id;
                }

                public void setId(String id) {
                    this.id = id;
                }
            }
        }
    }
}
