package com.cpigeon.book.model.entity;

/**
 * Created by Zhu TingYu on 2018/9/21.
 */

public class PigeonTrainDetailsEntity {

    /**
     * SortRank : 排名
     * Score : 积分
     * Fraction : 风速
     * ReturnCount  : 归巢羽数
     * FootRingNum : 足环号码
     * FlyCount  : 放飞羽数
     * Distance  : 最新训练空距
     * PigeonTrainCountID  : 最新训练次数id
     * Time  : 最新训练时间
     */

    private String SortRank;
    private double Score;
    private String Fraction;
    private String ReturnCount;
    private String FootRingNum;
    private String FlyCount;
    private String Distance;
    private String PigeonTrainCountID;
    private String Time;

    public String getSortRank() {
        return SortRank;
    }

    public void setSortRank(String SortRank) {
        this.SortRank = SortRank;
    }

    public double getScore() {
        return Score;
    }

    public void setScore(double Score) {
        this.Score = Score;
    }

    public String getFraction() {
        return Fraction;
    }

    public void setFraction(String Fraction) {
        this.Fraction = Fraction;
    }

    public String getReturnCount() {
        return ReturnCount;
    }

    public void setReturnCount(String ReturnCount) {
        this.ReturnCount = ReturnCount;
    }

    public String getFootRingNum() {
        return FootRingNum;
    }

    public void setFootRingNum(String FootRingNum) {
        this.FootRingNum = FootRingNum;
    }

    public String getFlyCount() {
        return FlyCount;
    }

    public void setFlyCount(String FlyCount) {
        this.FlyCount = FlyCount;
    }

    public String getDistance() {
        return Distance;
    }

    public void setDistance(String Distance) {
        this.Distance = Distance;
    }

    public String getPigeonTrainCountID() {
        return PigeonTrainCountID;
    }

    public void setPigeonTrainCountID(String PigeonTrainCountID) {
        this.PigeonTrainCountID = PigeonTrainCountID;
    }

    public String getTime() {
        return Time;
    }

    public void setTime(String Time) {
        this.Time = Time;
    }
}
