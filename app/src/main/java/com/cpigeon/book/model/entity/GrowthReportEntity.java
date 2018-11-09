package com.cpigeon.book.model.entity;

/**
 * 成长记录
 * Created by Administrator on 2018/9/27 0027.
 */

public class GrowthReportEntity {


    /**
     * FootRingNum : 2018-01-3636633
     * LayNum : 0
     * TypeName : 配对
     * TypeID : 4
     * UseTime : 2018-09-27 00:00:00
     * Name :
     * MatchCount : 0
     * MatchNumber : 0
     * Fraction : 0
     * Weather : null
     * Temperature : null
     * Bodytemper :
     * PigeonPlumeName : 雷米
     * PigeonBloodName : 科技
     * Info : 多云
     */
    private String BloodName;
    private String PlumeName;
    private String FootRingNum;
    private String Name;
    private String Info;
    private int Count;
    private int MatchNumber;
    private int LayNum;
    private String TypeName;
    private int TypeID;
    private String UseTime;
    private int MatchCount;
    private String Fraction;
    private String Weather;
    private String Temperature = "0";
    private String Bodytemper;
    private String PigeonPlumeName;
    private String PigeonBloodName;
    private int RetureFly;
    private int Number;
    private int ReturnFly;
    private String Remark;
;

    public String getBloodName() {
        return BloodName;
    }

    public void setBloodName(String bloodName) {
        BloodName = bloodName;
    }

    public String getPlumeName() {
        return PlumeName;
    }

    public void setPlumeName(String plumeName) {
        PlumeName = plumeName;
    }

    public int getCount() {
        return Count;
    }

    public void setCount(int count) {
        Count = count;
    }

    public int getNumber() {
        return Number;
    }

    public void setNumber(int number) {
        Number = number;
    }

    public int getReturnFly() {
        return ReturnFly;
    }

    public void setReturnFly(int returnFly) {
        ReturnFly = returnFly;
    }

    public String getRemark() {
        return Remark;
    }

    public void setRemark(String remark) {
        Remark = remark;
    }

    public int getRetureFly() {
        return RetureFly;
    }

    public void setRetureFly(int retureFly) {
        RetureFly = retureFly;
    }

    public String getFootRingNum() {
        return FootRingNum;
    }

    public void setFootRingNum(String footRingNum) {
        FootRingNum = footRingNum;
    }

    public int getLayNum() {
        return LayNum;
    }

    public void setLayNum(int layNum) {
        LayNum = layNum;
    }

    public String getTypeName() {
        return TypeName;
    }

    public void setTypeName(String typeName) {
        TypeName = typeName;
    }

    public int getTypeID() {
        return TypeID;
    }

    public void setTypeID(int typeID) {
        TypeID = typeID;
    }

    public String getUseTime() {
        return UseTime;
    }

    public void setUseTime(String useTime) {
        UseTime = useTime;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public int getMatchCount() {
        return MatchCount;
    }

    public void setMatchCount(int matchCount) {
        MatchCount = matchCount;
    }

    public int getMatchNumber() {
        return MatchNumber;
    }

    public void setMatchNumber(int matchNumber) {
        MatchNumber = matchNumber;
    }

    public String getFraction() {
        return Fraction;
    }

    public void setFraction(String fraction) {
        Fraction = fraction;
    }

    public String getWeather() {
        return Weather;
    }

    public void setWeather(String weather) {
        Weather = weather;
    }

    public String getTemperature() {
        return Temperature;
    }

    public void setTemperature(String temperature) {
        Temperature = temperature;
    }

    public String getBodytemper() {
        return Bodytemper;
    }

    public void setBodytemper(String bodytemper) {
        Bodytemper = bodytemper;
    }

    public String getPigeonPlumeName() {
        return PigeonPlumeName;
    }

    public void setPigeonPlumeName(String pigeonPlumeName) {
        PigeonPlumeName = pigeonPlumeName;
    }

    public String getPigeonBloodName() {
        return PigeonBloodName;
    }

    public void setPigeonBloodName(String pigeonBloodName) {
        PigeonBloodName = pigeonBloodName;
    }

    public String getInfo() {
        return Info;
    }

    public void setInfo(String info) {
        Info = info;
    }
}
