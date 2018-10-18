package com.cpigeon.book.model.entity;

/**
 * Created by Administrator on 2018/9/29 0029.
 */

public class OrderEntity {


    /**
     * id : 订单索引ID
     * gb : 鸽币
     * ddbh : 订单编号
     * item : 订单项目
     * state : 状态：0待支付1已支付2已过期
     * rmb : 订单金额
     * datetime : 订单时间
     */

    private String id;
    private String gb;
    private String ddbh;
    private String item;
    private String state;
    private String rmb;
    private String datetime;
    private String ly;

    public String getLy() {
        return ly;
    }

    public void setLy(String ly) {
        this.ly = ly;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getGb() {
        return gb;
    }

    public void setGb(String gb) {
        this.gb = gb;
    }

    public String getDdbh() {
        return ddbh;
    }

    public void setDdbh(String ddbh) {
        this.ddbh = ddbh;
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getRmb() {
        return rmb;
    }

    public void setRmb(String rmb) {
        this.rmb = rmb;
    }

    public String getDatetime() {
        return datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }
}
