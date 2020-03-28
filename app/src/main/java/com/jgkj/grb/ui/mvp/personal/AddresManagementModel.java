package com.jgkj.grb.ui.mvp.personal;

import java.io.Serializable;
import java.util.List;

/**
 * 地址管理
 * Created by brightpoplar@163.com on 2019/9/2.
 */
public class AddresManagementModel {

    /**
     * code : 1
     * msg : 成功
     * time : 1567412454
     * data : [{"id":8,"addr_detail":"大萨达","addr_default":0,"us_id":1,"delete_time":null,"add_time":"2018-08-30 16:31:40","addr_tel":"13645454545","addr_receiver":"老王","addr_code":"1,2","province":"北京","city":"东城区","area":"","addr_addr":"北京东城区大萨达"},{"id":11,"addr_detail":"规土委","addr_default":0,"us_id":1,"delete_time":null,"add_time":"2018-09-04 10:58:35","addr_tel":"15646161313","addr_receiver":"人防区分","addr_code":"397,398,399","province":"江苏","city":"南京","area":"玄武区","addr_addr":"江苏南京玄武区规土委"},{"id":12,"addr_detail":"fawf t","addr_default":0,"us_id":1,"delete_time":null,"add_time":"2018-09-07 11:42:50","addr_tel":"15646163163","addr_receiver":"fqwt","addr_code":"1,20","province":"北京","city":"其他","area":"","addr_addr":"北京其他fawf t"},{"id":26,"addr_detail":"朱雀大街86号","addr_default":1,"us_id":1,"delete_time":null,"add_time":"2019-08-21 15:24:35","addr_tel":"1884547545","addr_receiver":"王铁柱","addr_code":"","province":"","city":"","area":"北京市朝阳区","addr_addr":"北京市朝阳区朱雀大街86号"},{"id":27,"addr_detail":"朱雀大街86号","addr_default":0,"us_id":1,"delete_time":null,"add_time":"2019-08-21 15:28:32","addr_tel":"1884547545","addr_receiver":"王铁柱","addr_code":"","province":"","city":"","area":"北京市朝阳区","addr_addr":"北京市朝阳区朱雀大街86号"}]
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

    public static class DataBean implements Serializable {
        /**
         * id : 33
         * addr_detail : 房间号2212
         * addr_default : 0
         * us_id : 1
         * delete_time : null
         * add_time : 2019-09-03 18:51:11
         * addr_tel : 13462467089
         * addr_receiver : 聚格科技
         * addr_code : 2,52,512
         * province : 北京
         * city : 北京
         * town : 昌平区
         * area :
         * addr_addr : 北京北京昌平区房间号2212
         * fee: 10
         */

        private int id;
        private String addr_detail;
        private int addr_default;
        private int us_id;
        private Object delete_time;
        private String add_time;
        private String addr_tel;
        private String addr_receiver;
        private String addr_code;
        private String province;
        private String town;
        private String city;
        private String area;
        private String addr_addr;
        private float fee;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getAddr_detail() {
            return addr_detail;
        }

        public void setAddr_detail(String addr_detail) {
            this.addr_detail = addr_detail;
        }

        public int getAddr_default() {
            return addr_default;
        }

        public void setAddr_default(int addr_default) {
            this.addr_default = addr_default;
        }

        public int getUs_id() {
            return us_id;
        }

        public void setUs_id(int us_id) {
            this.us_id = us_id;
        }

        public Object getDelete_time() {
            return delete_time;
        }

        public void setDelete_time(Object delete_time) {
            this.delete_time = delete_time;
        }

        public String getAdd_time() {
            return add_time;
        }

        public void setAdd_time(String add_time) {
            this.add_time = add_time;
        }

        public String getAddr_tel() {
            return addr_tel;
        }

        public void setAddr_tel(String addr_tel) {
            this.addr_tel = addr_tel;
        }

        public String getAddr_receiver() {
            return addr_receiver;
        }

        public void setAddr_receiver(String addr_receiver) {
            this.addr_receiver = addr_receiver;
        }

        public String getAddr_code() {
            return addr_code;
        }

        public void setAddr_code(String addr_code) {
            this.addr_code = addr_code;
        }

        public String getProvince() {
            return province;
        }

        public void setProvince(String province) {
            this.province = province;
        }

        public String getTown() {
            return town;
        }

        public void setTown(String town) {
            this.town = town;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getArea() {
            return area;
        }

        public void setArea(String area) {
            this.area = area;
        }

        public String getAddr_addr() {
            return addr_addr;
        }

        public void setAddr_addr(String addr_addr) {
            this.addr_addr = addr_addr;
        }

        public float getFee() {
            return fee;
        }

        public void setFee(float fee) {
            this.fee = fee;
        }
    }
}
