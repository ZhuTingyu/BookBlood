package com.cpigeon.book.model.entity;

/**
 * Created by Zhu TingYu on 2018/11/20.
 */

public class PigeonScoreItemEntity {


    /**
     * items : 评分项目
     * tscore : 该项目评分
     * itemid : 评分项目ID
     * id : 该鸽评分ID
     * pscore : 该鸽当前评分
     */

    private String items;
    private double tscore;
    private String itemid;
    private String id;
    private double pscore;

    public String getItems() {
        return items;
    }

    public void setItems(String items) {
        this.items = items;
    }

    public double getTscore() {
        return tscore;
    }

    public void setTscore(double tscore) {
        this.tscore = tscore;
    }

    public String getItemid() {
        return itemid;
    }

    public void setItemid(String itemid) {
        this.itemid = itemid;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public double getPscore() {
        return pscore;
    }

    public void setPscore(double pscore) {
        this.pscore = pscore;
    }
}
