package com.cpigeon.book.model.entity;

import java.util.List;

/**
 * Created by Administrator on 2018/8/22.
 */

public class SignInfoEntity {


    /**
     * giftdata : []
     * giftSettings : [{"gid":"2","items":"累计签到3日礼包","gb":"10","dw":"鸽币"},{"gid":"3","items":"累计签到7日礼包","gb":"20","dw":"鸽币"},{"gid":"4","items":"累计签到15日礼包","gb":"30","dw":"鸽币"},{"gid":"5","items":"累计签到28日礼包","gb":"50","dw":"鸽币"}]
     * signSetting : 5
     * signDays : 21,1,11,22
     * signsTotalDays : 4
     * signed : true
     */

    private String signSetting;
    private String signDays;
    private String signsTotalDays;
    private boolean signed;
    private List<?> giftdata;
    private List<GiftSettingsBean> giftSettings;

    public String getSignSetting() {
        return signSetting;
    }

    public void setSignSetting(String signSetting) {
        this.signSetting = signSetting;
    }

    public String getSignDays() {
        return signDays;
    }

    public void setSignDays(String signDays) {
        this.signDays = signDays;
    }

    public String getSignsTotalDays() {
        return signsTotalDays;
    }

    public void setSignsTotalDays(String signsTotalDays) {
        this.signsTotalDays = signsTotalDays;
    }

    public boolean isSigned() {
        return signed;
    }

    public void setSigned(boolean signed) {
        this.signed = signed;
    }

    public List<?> getGiftdata() {
        return giftdata;
    }

    public void setGiftdata(List<?> giftdata) {
        this.giftdata = giftdata;
    }

    public List<GiftSettingsBean> getGiftSettings() {
        return giftSettings;
    }

    public void setGiftSettings(List<GiftSettingsBean> giftSettings) {
        this.giftSettings = giftSettings;
    }

    public static class GiftSettingsBean {
        /**
         * gid : 2
         * items : 累计签到3日礼包
         * gb : 10
         * dw : 鸽币
         */

        private String gid;
        private String items;
        private String gb;
        private String dw;

        public String getGid() {
            return gid;
        }

        public void setGid(String gid) {
            this.gid = gid;
        }

        public String getItems() {
            return items;
        }

        public void setItems(String items) {
            this.items = items;
        }

        public String getGb() {
            return gb;
        }

        public void setGb(String gb) {
            this.gb = gb;
        }

        public String getDw() {
            return dw;
        }

        public void setDw(String dw) {
            this.dw = dw;
        }
    }
}
