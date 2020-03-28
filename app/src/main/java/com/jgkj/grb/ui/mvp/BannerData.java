package com.jgkj.grb.ui.mvp;

import com.stx.xhb.xbanner.entity.SimpleBannerInfo;

/**
 * 轮播图
 * Created by brightpoplar@163.com on 2019/8/29.
 */
public class BannerData extends SimpleBannerInfo {
    String bannerUrl;
    int bannerResId;
    String bannerTitle;
    boolean select;

    public BannerData() {
    }

    public BannerData(int bannerResId, String bannerTitle) {
        this.bannerResId = bannerResId;
        this.bannerTitle = bannerTitle;
    }

    public BannerData(String bannerUrl, String bannerTitle) {
        this.bannerUrl = bannerUrl;
        this.bannerTitle = bannerTitle;
    }

    public BannerData(String bannerUrl, String bannerTitle, boolean select) {
        this.bannerUrl = bannerUrl;
        this.bannerTitle = bannerTitle;
        this.select = select;
    }

    @Override
    public Object getXBannerUrl() {
        return bannerUrl;
    }

    @Override
    public String getXBannerTitle() {
        return bannerTitle;
    }

    public String getBannerUrl() {
        return bannerUrl;
    }

    public void setBannerUrl(String bannerUrl) {
        this.bannerUrl = bannerUrl;
    }

    public int getBannerResId() {
        return bannerResId;
    }

    public void setBannerResId(int bannerResId) {
        this.bannerResId = bannerResId;
    }

    public String getBannerTitle() {
        return bannerTitle;
    }

    public void setBannerTitle(String bannerTitle) {
        this.bannerTitle = bannerTitle;
    }

    public boolean isSelect() {
        return select;
    }

    public void setSelect(boolean select) {
        this.select = select;
    }
}
