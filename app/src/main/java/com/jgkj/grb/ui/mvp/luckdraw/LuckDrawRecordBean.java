package com.jgkj.grb.ui.mvp.luckdraw;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * GRB 抽奖：抽奖记录
 * Created by brightpoplar@163.com on 2019/9/3.
 */
public class LuckDrawRecordBean implements Parcelable {
    /**
     * id : 1
     * content : 200优惠券
     * status : 3
     * time : 2019-09-01 14:57:26
     */

    private int id;
    private String content;
    private int status;
    private String time;

    protected LuckDrawRecordBean(Parcel in) {
        id = in.readInt();
        content = in.readString();
        status = in.readInt();
        time = in.readString();
    }

    public static final Creator<LuckDrawRecordBean> CREATOR = new Creator<LuckDrawRecordBean>() {
        @Override
        public LuckDrawRecordBean createFromParcel(Parcel in) {
            return new LuckDrawRecordBean(in);
        }

        @Override
        public LuckDrawRecordBean[] newArray(int size) {
            return new LuckDrawRecordBean[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(content);
        dest.writeInt(status);
        dest.writeString(time);
    }
}
