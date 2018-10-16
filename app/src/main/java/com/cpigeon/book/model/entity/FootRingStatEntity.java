package com.cpigeon.book.model.entity;

import com.base.util.Lists;

import java.util.List;

/**
 * Created by Zhu TingYu on 2018/9/25.
 */

public class FootRingStatEntity {

    /**
     * tebiCount : 0
     * qitaCount : 0
     * tongyiCount : 1
     * siCount : 0
     * trueCount : 0
     * falseCount : 0
     */

    private int tebiCount;
    private int qitaCount;
    private int tongyiCount;
    private int siCount;
    private int trueCount; // 已挂环
    private int falseCount;// 未挂环

    public int getTebiCount() {
        return tebiCount;
    }

    public void setTebiCount(int tebiCount) {
        this.tebiCount = tebiCount;
    }

    public int getQitaCount() {
        return qitaCount;
    }

    public void setQitaCount(int qitaCount) {
        this.qitaCount = qitaCount;
    }

    public int getTongyiCount() {
        return tongyiCount;
    }

    public void setTongyiCount(int tongyiCount) {
        this.tongyiCount = tongyiCount;
    }

    public int getSiCount() {
        return siCount;
    }

    public void setSiCount(int siCount) {
        this.siCount = siCount;
    }

    public int getTrueCount() {
        return trueCount;
    }

    public void setTrueCount(int trueCount) {
        this.trueCount = trueCount;
    }

    public int getFalseCount() {
        return falseCount;
    }

    public void setFalseCount(int falseCount) {
        this.falseCount = falseCount;
    }

    public int getMaxCount() {
        return trueCount + falseCount;
    }

    public List<Integer> getData() {
        return Lists.newArrayList(tebiCount, tongyiCount, qitaCount, siCount, trueCount, falseCount);
    }
}
