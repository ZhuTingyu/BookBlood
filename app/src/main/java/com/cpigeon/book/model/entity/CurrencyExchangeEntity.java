package com.cpigeon.book.model.entity;

/**
 * 鸽币兑换
 * Created by Administrator on 2018/9/29 0029.
 */

public class CurrencyExchangeEntity {


    /**
     * sid : 1
     * sname : 天下鸽谱开通
     * gb : 18888
     * number : 1
     * danwei : 年
     */

    private String sid;
    private String sname;
    private String gb;
    private String number;
    private String danwei;

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public String getSname() {
        return sname;
    }

    public void setSname(String sname) {
        this.sname = sname;
    }

    public String getGb() {
        return gb;
    }

    public void setGb(String gb) {
        this.gb = gb;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getDanwei() {
        return danwei;
    }

    public void setDanwei(String danwei) {
        this.danwei = danwei;
    }
}
