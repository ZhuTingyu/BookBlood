package com.cpigeon.book.model.entity;

/**
 * Created by Administrator on 2018/9/14 0014.
 */

public class ServerReportEntity {


    /**
     * score : 鸽币
     * shijian : 服务续费、开通操作时间
     * num : 服务期限或数量
     * sname : 服务名称
     * danwei : 单位：年，或次
     * sid : 服务所引ID
     * price : 价格
     * other : 其他信息
     */

    private String score;
    private String shijian;
    private String num;
    private String sname;
    private String danwei;
    private String sid;
    private String price;
    private String other;

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public String getShijian() {
        return shijian;
    }

    public void setShijian(String shijian) {
        this.shijian = shijian;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public String getSname() {
        return sname;
    }

    public void setSname(String sname) {
        this.sname = sname;
    }

    public String getDanwei() {
        return danwei;
    }

    public void setDanwei(String danwei) {
        this.danwei = danwei;
    }

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getOther() {
        return other;
    }

    public void setOther(String other) {
        this.other = other;
    }
}
