package com.cpigeon.book.model.entity;

/**
 * Created by Zhu TingYu on 2018/9/25.
 */

public class TrainAnalyseEntity {

    /**
     * Score : 积分
     * Fraction : 分数
     * PigeonID : 信鸽id
     * FootRingID : 足环ID
     * FootRingNum : 足环号码
     * Plume : 羽色
     */

    private double Score;
    private double Fraction;
    private String PigeonID;
    private String FootRingID;
    private String FootRingNum;
    private String Plume;
    private String PigeonBloodName;

    public String getPigeonBloodName() {
        return PigeonBloodName;
    }

    public void setPigeonBloodName(String pigeonBloodName) {
        PigeonBloodName = pigeonBloodName;
    }

    public double getScore() {
        return Score;
    }

    public void setScore(double Score) {
        this.Score = Score;
    }

    public double getFraction() {
        return Fraction;
    }

    public void setFraction(double Fraction) {
        this.Fraction = Fraction;
    }

    public String getPigeonID() {
        return PigeonID;
    }

    public void setPigeonID(String PigeonID) {
        this.PigeonID = PigeonID;
    }

    public String getFootRingID() {
        return FootRingID;
    }

    public void setFootRingID(String FootRingID) {
        this.FootRingID = FootRingID;
    }

    public String getFootRingNum() {
        return FootRingNum;
    }

    public void setFootRingNum(String FootRingNum) {
        this.FootRingNum = FootRingNum;
    }

    public String getPlume() {
        return Plume;
    }

    public void setPlume(String Plume) {
        this.Plume = Plume;
    }
}
