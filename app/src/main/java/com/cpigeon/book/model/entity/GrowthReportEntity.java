package com.cpigeon.book.model.entity;

/**
 * 成长记录
 * Created by Administrator on 2018/9/27 0027.
 */

public class GrowthReportEntity {


    /**
     * FootRingNum : 2018-01-6433161
     * LayNum : 0
     * TypeName : 繁育
     * UseTime : 2018-09-21 00:00:00
     * Name :
     * MatchCount : 0
     * MatchNumber : 0
     * Fraction : 0
     * Bodytemper :
     * PigeonPlumeName : 红（绛）
     * PigeonBloodName : 詹森
     * Info : 多云
     */

    private String FootRingNum;
    private int LayNum;
    private String TypeName;
    private String UseTime;
    private String Name;
    private int MatchCount;
    private int MatchNumber;
    private int Fraction;
    private String Bodytemper;
    private String PigeonPlumeName;
    private String PigeonBloodName;
    private String Info;

    public String getFootRingNum() {
        return FootRingNum;
    }

    public void setFootRingNum(String FootRingNum) {
        this.FootRingNum = FootRingNum;
    }

    public int getLayNum() {
        return LayNum;
    }

    public void setLayNum(int LayNum) {
        this.LayNum = LayNum;
    }

    public String getTypeName() {
        return TypeName;
    }

    public void setTypeName(String TypeName) {
        this.TypeName = TypeName;
    }

    public String getUseTime() {
        return UseTime;
    }

    public void setUseTime(String UseTime) {
        this.UseTime = UseTime;
    }

    public String getName() {
        return Name;
    }

    public void setName(String Name) {
        this.Name = Name;
    }

    public int getMatchCount() {
        return MatchCount;
    }

    public void setMatchCount(int MatchCount) {
        this.MatchCount = MatchCount;
    }

    public int getMatchNumber() {
        return MatchNumber;
    }

    public void setMatchNumber(int MatchNumber) {
        this.MatchNumber = MatchNumber;
    }

    public int getFraction() {
        return Fraction;
    }

    public void setFraction(int Fraction) {
        this.Fraction = Fraction;
    }

    public String getBodytemper() {
        return Bodytemper;
    }

    public void setBodytemper(String Bodytemper) {
        this.Bodytemper = Bodytemper;
    }

    public String getPigeonPlumeName() {
        return PigeonPlumeName;
    }

    public void setPigeonPlumeName(String PigeonPlumeName) {
        this.PigeonPlumeName = PigeonPlumeName;
    }

    public String getPigeonBloodName() {
        return PigeonBloodName;
    }

    public void setPigeonBloodName(String PigeonBloodName) {
        this.PigeonBloodName = PigeonBloodName;
    }

    public String getInfo() {
        return Info;
    }

    public void setInfo(String Info) {
        this.Info = Info;
    }
}
