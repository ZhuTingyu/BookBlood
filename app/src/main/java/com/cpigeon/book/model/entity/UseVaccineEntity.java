package com.cpigeon.book.model.entity;

/**
 * Created by Administrator on 2018/9/18 0018.
 */

public class UseVaccineEntity {


    /**
     * FootRingID : 1202849
     * Direction :
     * Reason : 呵呵哒
     * UseViccineTime : 2018-08-18
     * BodyTemper : 12
     * Temperature : 24
     * PigeonVaccineID : 7
     * FootRingNum : 2018-01-6433161
     * Remark :
     * Humidity :
     * PigeonID : 286
     * UserID : 5
     * Weather : 大雨
     * PigeonViccineName : 陪弄会
     */

    private int FootRingID;
    private String Direction;
    private String Reason;
    private String UseViccineTime;
    private String BodyTemper;
    private String Temperature;
    private int PigeonVaccineID;
    private String FootRingNum;
    private String Remark;
    private String Humidity;
    private int PigeonID;
    private int UserID;
    private String Weather;
    private String PigeonViccineName;
    private String ViccineNameID;

    public String getViccineNameID() {
        return ViccineNameID;
    }

    public void setViccineNameID(String viccineNameID) {
        ViccineNameID = viccineNameID;
    }

    public int getFootRingID() {
        return FootRingID;
    }

    public void setFootRingID(int FootRingID) {
        this.FootRingID = FootRingID;
    }

    public String getDirection() {
        return Direction;
    }

    public void setDirection(String Direction) {
        this.Direction = Direction;
    }

    public String getReason() {
        return Reason;
    }

    public void setReason(String Reason) {
        this.Reason = Reason;
    }

    public String getUseViccineTime() {
        return UseViccineTime;
    }

    public void setUseViccineTime(String UseViccineTime) {
        this.UseViccineTime = UseViccineTime;
    }

    public String getBodyTemper() {
        return BodyTemper;
    }

    public void setBodyTemper(String BodyTemper) {
        this.BodyTemper = BodyTemper;
    }

    public String getTemperature() {
        return Temperature;
    }

    public void setTemperature(String Temperature) {
        this.Temperature = Temperature;
    }

    public int getPigeonVaccineID() {
        return PigeonVaccineID;
    }

    public void setPigeonVaccineID(int PigeonVaccineID) {
        this.PigeonVaccineID = PigeonVaccineID;
    }

    public String getFootRingNum() {
        return FootRingNum;
    }

    public void setFootRingNum(String FootRingNum) {
        this.FootRingNum = FootRingNum;
    }

    public String getRemark() {
        return Remark;
    }

    public void setRemark(String Remark) {
        this.Remark = Remark;
    }

    public String getHumidity() {
        return Humidity;
    }

    public void setHumidity(String Humidity) {
        this.Humidity = Humidity;
    }

    public int getPigeonID() {
        return PigeonID;
    }

    public void setPigeonID(int PigeonID) {
        this.PigeonID = PigeonID;
    }

    public int getUserID() {
        return UserID;
    }

    public void setUserID(int UserID) {
        this.UserID = UserID;
    }

    public String getWeather() {
        return Weather;
    }

    public void setWeather(String Weather) {
        this.Weather = Weather;
    }

    public String getPigeonViccineName() {
        return PigeonViccineName;
    }

    public void setPigeonViccineName(String PigeonViccineName) {
        this.PigeonViccineName = PigeonViccineName;
    }
}
