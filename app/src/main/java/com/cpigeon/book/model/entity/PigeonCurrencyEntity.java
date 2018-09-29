package com.cpigeon.book.model.entity;

import java.util.List;

/**
 * Created by Administrator on 2018/9/13.
 */

public class PigeonCurrencyEntity {


    /**
     * getgb : [{"sname":"今日签到","sgb":"+5/每日一次","isdo":"1"},{"sname":"录入足环","sgb":"+5/每日首次","isdo":"1"},{"sname":"录入种鸽","sgb":"+5/每日首次","isdo":"0"},{"sname":"录入赛鸽","sgb":"+5/每日首次","isdo":"0"},{"sname":"养鸽记录","sgb":"+5/每日首次","isdo":"0"},{"sname":"信鸽拍照","sgb":"+5/每日首次","isdo":"0"}]
     * gb : 250
     */

    private String gb;
    private List<GetgbBean> getgb;
    /**
     * item : 签到
     * datetime : 2018-09-15 08:50:04
     * id : 28039
     */

    private String item;
    private String datetime;
    private String id;

    public String getGb() {
        return gb;
    }

    public void setGb(String gb) {
        this.gb = gb;
    }

    public List<GetgbBean> getGetgb() {
        return getgb;
    }

    public void setGetgb(List<GetgbBean> getgb) {
        this.getgb = getgb;
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public String getDatetime() {
        return datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


    public static class GetgbBean {
        /**
         * sname : 今日签到
         * sgb : +5/每日一次
         * isdo : 1
         */

        private String sname;
        private String sgb;
        private String isdo;

        public String getSname() {
            return sname;
        }

        public void setSname(String sname) {
            this.sname = sname;
        }

        public String getSgb() {
            return sgb;
        }

        public void setSgb(String sgb) {
            this.sgb = sgb;
        }

        public String getIsdo() {
            return isdo;
        }

        public void setIsdo(String isdo) {
            this.isdo = isdo;
        }
    }
}
