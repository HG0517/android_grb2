package com.jgkj.grb.ui.mvp.shopcart;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * 购物车：领券
 * Created by brightpoplar@163.com on 2019/7/31.
 */
public class CouponModel {
    private String message;
    private int statusCode;
    private boolean status;
    private int sum;
    private List<CouponBean> resource;

    public static class CouponBean implements Parcelable {
        private int icResId;
        private boolean isSecurity;

        public int getIcResId() {
            return icResId;
        }

        public void setIcResId(int icResId) {
            this.icResId = icResId;
        }

        public boolean isSecurity() {
            return isSecurity;
        }

        public void setSecurity(boolean security) {
            isSecurity = security;
        }

        public CouponBean() {
        }

        protected CouponBean(Parcel in) {
            icResId = in.readInt();
            isSecurity = in.readByte() != 0;
        }

        public static final Creator<CouponBean> CREATOR = new Creator<CouponBean>() {
            @Override
            public CouponBean createFromParcel(Parcel in) {
                return new CouponBean(in);
            }

            @Override
            public CouponBean[] newArray(int size) {
                return new CouponBean[size];
            }
        };

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(icResId);
            dest.writeByte((byte) (isSecurity ? 1 : 0));
        }
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public int getSum() {
        return sum;
    }

    public void setSum(int sum) {
        this.sum = sum;
    }

    public List<CouponBean> getResource() {
        return resource;
    }

    public void setResource(List<CouponBean> resource) {
        this.resource = resource;
    }
}
