package com.cpigeon.book.module.findblood;

/**
 * Created by Zhu TingYu on 2018/11/22.
 */

public class BloodFindRecordEntity {
    private String headUrl;
    private int count;
    private boolean isSelect;

    public BloodFindRecordEntity(String headUrl, int count){
        this.headUrl = headUrl;
        this.count = count;
    }

    public String getHeadUrl() {
        return headUrl;
    }

    public int getCount() {
        return count;
    }

    public boolean isSelect() {
        return isSelect;
    }

    public void setSelect(boolean select) {
        isSelect = select;
    }
}
