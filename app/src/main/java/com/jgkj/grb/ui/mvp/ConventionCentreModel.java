package com.jgkj.grb.ui.mvp;

import java.util.List;

/**
 * 会议中心
 * Created by brightpoplar@163.com on 2019/9/9.
 */
public class ConventionCentreModel {

    /**
     * code : 1
     * msg : 成功
     * time : 1568001214
     * data : {"signUp":1,"signin":0,"meeting":{"id":4,"title":"新型玉米发布会","content":"新玉米上市的时候临储玉米基本停止供应，但是现在玉米库存压力仍然比较大，库存量还在亿吨以上，目前还处于去库存的过程中，我们建议后期关注政策上的变化","tel":"0377-514234","address":"河南省郑州市金水区经八路街道建设厅家属院","time":"2019-09-09 00:00:00","add_time":"0000-00-00 00:00:00","coordinate":"113.669467,34.76351","radius":1,"pic":"/uploads/20190819/82ba9f41a9b96fbe3653d61f5a82edfb.jpg","timestrap":1567958400},"goods":[{"id":1,"m_id":4,"pd_id":119,"price":99,"grb":100,"pd_name":"大米 东北大米","pd_head_pic":"/uploads/20190816/181e427911c72e589a76881a3e8ac2b9.png"},{"id":3,"m_id":4,"pd_id":129,"price":100,"grb":150,"pd_name":"稻香大米","pd_head_pic":"/uploads/20190803/71ec784f5edb7cbc20deda7eb1dbfc1d.png"}],"detail":[{"id":1,"m_id":4,"title":"专家讲解","content":"新型玉米的产生与发展","time":"2019-08-20 15:00:00","pic":"/uploads/20190819/82ba9f41a9b96fbe3653d61f5a82edfb.jpg"},{"id":2,"m_id":4,"title":"会议小活动","content":"各种活动","time":"2019-08-19 10:53:00","pic":"/uploads/20190819/82ba9f41a9b96fbe3653d61f5a82edfb.jpg"}]}
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
         * signUp : 1
         * signin : 0
         * meeting : {"id":4,"title":"新型玉米发布会","content":"新玉米上市的时候临储玉米基本停止供应，但是现在玉米库存压力仍然比较大，库存量还在亿吨以上，目前还处于去库存的过程中，我们建议后期关注政策上的变化","tel":"0377-514234","address":"河南省郑州市金水区经八路街道建设厅家属院","time":"2019-09-09 00:00:00","add_time":"0000-00-00 00:00:00","coordinate":"113.669467,34.76351","radius":1,"pic":"/uploads/20190819/82ba9f41a9b96fbe3653d61f5a82edfb.jpg","timestrap":1567958400}
         * goods : [{"id":1,"m_id":4,"pd_id":119,"price":99,"grb":100,"pd_name":"大米 东北大米","pd_head_pic":"/uploads/20190816/181e427911c72e589a76881a3e8ac2b9.png"},{"id":3,"m_id":4,"pd_id":129,"price":100,"grb":150,"pd_name":"稻香大米","pd_head_pic":"/uploads/20190803/71ec784f5edb7cbc20deda7eb1dbfc1d.png"}]
         * detail : [{"id":1,"m_id":4,"title":"专家讲解","content":"新型玉米的产生与发展","time":"2019-08-20 15:00:00","pic":"/uploads/20190819/82ba9f41a9b96fbe3653d61f5a82edfb.jpg"},{"id":2,"m_id":4,"title":"会议小活动","content":"各种活动","time":"2019-08-19 10:53:00","pic":"/uploads/20190819/82ba9f41a9b96fbe3653d61f5a82edfb.jpg"}]
         */

        private int signUp;
        private int signin;
        private MeetingBean meeting;
        private List<GoodsBean> goods;
        private List<DetailBean> detail;

        public int getSignUp() {
            return signUp;
        }

        public void setSignUp(int signUp) {
            this.signUp = signUp;
        }

        public int getSignin() {
            return signin;
        }

        public void setSignin(int signin) {
            this.signin = signin;
        }

        public MeetingBean getMeeting() {
            return meeting;
        }

        public void setMeeting(MeetingBean meeting) {
            this.meeting = meeting;
        }

        public List<GoodsBean> getGoods() {
            return goods;
        }

        public void setGoods(List<GoodsBean> goods) {
            this.goods = goods;
        }

        public List<DetailBean> getDetail() {
            return detail;
        }

        public void setDetail(List<DetailBean> detail) {
            this.detail = detail;
        }

        public static class MeetingBean {
            /**
             * id : 4
             * title : 新型玉米发布会
             * content : 新玉米上市的时候临储玉米基本停止供应，但是现在玉米库存压力仍然比较大，库存量还在亿吨以上，目前还处于去库存的过程中，我们建议后期关注政策上的变化
             * tel : 0377-514234
             * address : 河南省郑州市金水区经八路街道建设厅家属院
             * time : 2019-09-09 00:00:00
             * add_time : 0000-00-00 00:00:00
             * coordinate : 113.669467,34.76351
             * radius : 1
             * pic : /uploads/20190819/82ba9f41a9b96fbe3653d61f5a82edfb.jpg
             * timestrap : 1567958400
             * delete_time : 0
             * grb : 0
             * cash : 0
             * fee : 0.00
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
            private String pic;
            private int timestrap;
            private String delete_time;
            private int grb;
            private int cash;
            private String fee;

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

            public String getPic() {
                return pic;
            }

            public void setPic(String pic) {
                this.pic = pic;
            }

            public int getTimestrap() {
                return timestrap;
            }

            public void setTimestrap(int timestrap) {
                this.timestrap = timestrap;
            }

            public String getDelete_time() {
                return delete_time;
            }

            public void setDelete_time(String delete_time) {
                this.delete_time = delete_time;
            }

            public int getGrb() {
                return grb;
            }

            public void setGrb(int grb) {
                this.grb = grb;
            }

            public int getCash() {
                return cash;
            }

            public void setCash(int cash) {
                this.cash = cash;
            }

            public String getFee() {
                return fee;
            }

            public void setFee(String fee) {
                this.fee = fee;
            }
        }

        public static class GoodsBean {
            /**
             * id : 1
             * m_id : 4
             * pd_id : 119
             * price : 99
             * grb : 100
             * pd_name : 大米 东北大米
             * pd_head_pic : /uploads/20190816/181e427911c72e589a76881a3e8ac2b9.png
             * pd_market_price : 0.00
             * pd_grc : 0.00000
             * pd_grb : 0.00000
             * pd_price : 19.60
             * paytype2 : 0
             * paytype1 : 0
             * paytype : 1
             * pd_total : 969.00000
             */

            private int id;
            private int m_id;
            private int pd_id;
            private int price;
            private int grb;
            private String pd_name;
            private String pd_head_pic;
            private String pd_market_price;
            private String pd_grc;
            private String pd_grb;
            private String pd_price;
            private int paytype2;
            private int paytype1;
            private int paytype;
            private String pd_total;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public int getM_id() {
                return m_id;
            }

            public void setM_id(int m_id) {
                this.m_id = m_id;
            }

            public int getPd_id() {
                return pd_id;
            }

            public void setPd_id(int pd_id) {
                this.pd_id = pd_id;
            }

            public int getPrice() {
                return price;
            }

            public void setPrice(int price) {
                this.price = price;
            }

            public int getGrb() {
                return grb;
            }

            public void setGrb(int grb) {
                this.grb = grb;
            }

            public String getPd_name() {
                return pd_name;
            }

            public void setPd_name(String pd_name) {
                this.pd_name = pd_name;
            }

            public String getPd_head_pic() {
                return pd_head_pic;
            }

            public void setPd_head_pic(String pd_head_pic) {
                this.pd_head_pic = pd_head_pic;
            }

            public String getPd_market_price() {
                return pd_market_price;
            }

            public void setPd_market_price(String pd_market_price) {
                this.pd_market_price = pd_market_price;
            }

            public String getPd_grc() {
                return pd_grc;
            }

            public void setPd_grc(String pd_grc) {
                this.pd_grc = pd_grc;
            }

            public String getPd_grb() {
                return pd_grb;
            }

            public void setPd_grb(String pd_grb) {
                this.pd_grb = pd_grb;
            }

            public String getPd_price() {
                return pd_price;
            }

            public void setPd_price(String pd_price) {
                this.pd_price = pd_price;
            }

            public int getPaytype2() {
                return paytype2;
            }

            public void setPaytype2(int paytype2) {
                this.paytype2 = paytype2;
            }

            public int getPaytype1() {
                return paytype1;
            }

            public void setPaytype1(int paytype1) {
                this.paytype1 = paytype1;
            }

            public int getPaytype() {
                return paytype;
            }

            public void setPaytype(int paytype) {
                this.paytype = paytype;
            }

            public String getPd_total() {
                return pd_total;
            }

            public void setPd_total(String pd_total) {
                this.pd_total = pd_total;
            }
        }

        public static class DetailBean {
            /**
             * id : 1
             * m_id : 4
             * title : 专家讲解
             * content : 新型玉米的产生与发展
             * time : 2019-08-20 15:00:00
             * pic : /uploads/20190819/82ba9f41a9b96fbe3653d61f5a82edfb.jpg
             */

            private int id;
            private int m_id;
            private String title;
            private String content;
            private String time;
            private String pic;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public int getM_id() {
                return m_id;
            }

            public void setM_id(int m_id) {
                this.m_id = m_id;
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

            public String getTime() {
                return time;
            }

            public void setTime(String time) {
                this.time = time;
            }

            public String getPic() {
                return pic;
            }

            public void setPic(String pic) {
                this.pic = pic;
            }
        }
    }
}
