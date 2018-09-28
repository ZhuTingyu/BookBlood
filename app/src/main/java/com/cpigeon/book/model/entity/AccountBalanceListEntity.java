package com.cpigeon.book.model.entity;

/**
 * 充值明细列表
 * Created by Administrator on 2018/8/24.
 */

public class AccountBalanceListEntity {


    /**
     * shijian : 充值时间
     * money : 充值金额
     */

    private String shijian;
    private String money;

    public String getShijian() {
        return shijian;
    }

    public void setShijian(String shijian) {
        this.shijian = shijian;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }
}
