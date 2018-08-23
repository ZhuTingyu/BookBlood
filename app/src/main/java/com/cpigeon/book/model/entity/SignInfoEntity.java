package com.cpigeon.book.model.entity;

import java.util.List;

/**
 * Created by Administrator on 2018/8/22.
 */

public class SignInfoEntity {


    /**
     * giftdata : [{"items":"累计签到3日礼包","date":"2018-08-22"}]
     * giftSettings : [{"gid":"3","items":"累计签到7日礼包","gb":"20","date":"2018-08-28","dw":"鸽币"}]
     * signSetting : 5
     * signDays : 21,1,22
     * signsTotalDays : 3
     * signed : false
     */

    private String signSetting;
    private String signDays;
    private String signsTotalDays;
    private boolean signed;
    private List<GiftdataBean> giftdata;
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

    public List<GiftdataBean> getGiftdata() {
        return giftdata;
    }

    public void setGiftdata(List<GiftdataBean> giftdata) {
        this.giftdata = giftdata;
    }

    public List<GiftSettingsBean> getGiftSettings() {
        return giftSettings;
    }

    public void setGiftSettings(List<GiftSettingsBean> giftSettings) {
        this.giftSettings = giftSettings;
    }

    public static class GiftdataBean {
        /**
         * items : 累计签到3日礼包
         * date : 2018-08-22
         */

        private String items;
        private String date;

        public String getItems() {
            return items;
        }

        public void setItems(String items) {
            this.items = items;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }
    }

    public static class GiftSettingsBean {
        /**
         * gid : 3
         * items : 累计签到7日礼包
         * gb : 20
         * date : 2018-08-28
         * dw : 鸽币
         */

        private String gid;
        private String items;
        private String gb;
        private String date;
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

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public String getDw() {
            return dw;
        }

        public void setDw(String dw) {
            this.dw = dw;
        }
    }
}
