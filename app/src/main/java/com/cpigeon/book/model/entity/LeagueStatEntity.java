package com.cpigeon.book.model.entity;

/**
 * Created by Zhu TingYu on 2018/9/25.
 */

public class LeagueStatEntity {

    /**
     * AllCount : 所有参赛鸽子
     * OneCount : 冠军羽数
     * TwoCount : 季军羽数
     * ThreeCount : 亚军羽数
     * TenCount : 10-20
     * TwentyCount : 20-50
     * FiftyCount : 50-150
     */

    private int AllCount;
    private String OneCount;
    private String TwoCount;
    private String ThreeCount;
    private int TenCount;
    private int TwentyCount;
    private int FiftyCount;

    public int getAllCount() {
        return AllCount;
    }

    public void setAllCount(int allCount) {
        AllCount = allCount;
    }

    public String getOneCount() {
        return OneCount;
    }

    public void setOneCount(String oneCount) {
        OneCount = oneCount;
    }

    public String getTwoCount() {
        return TwoCount;
    }

    public void setTwoCount(String twoCount) {
        TwoCount = twoCount;
    }

    public String getThreeCount() {
        return ThreeCount;
    }

    public void setThreeCount(String threeCount) {
        ThreeCount = threeCount;
    }

    public int getTenCount() {
        return TenCount;
    }

    public void setTenCount(int tenCount) {
        TenCount = tenCount;
    }

    public int getTwentyCount() {
        return TwentyCount;
    }

    public void setTwentyCount(int twentyCount) {
        TwentyCount = twentyCount;
    }

    public int getFiftyCount() {
        return FiftyCount;
    }

    public void setFiftyCount(int fiftyCount) {
        FiftyCount = fiftyCount;
    }
}
