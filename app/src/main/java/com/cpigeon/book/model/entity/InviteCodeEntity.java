package com.cpigeon.book.model.entity;

/**
 * Created by Administrator on 2018/8/30.
 */

public class InviteCodeEntity {


    /**
     * yqm : 邀请码
     * url : 分享页面地址
     */

    private String yqm;
    private String url;

    public String getYqm() {
        return yqm;
    }

    public void setYqm(String yqm) {
        this.yqm = yqm;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
