package com.cpigeon.book.model.entity;

/**
 * Created by Administrator on 2018/9/4.
 */

public class PigeonPlayEntity {


    /**
     * MatchTime : 2016-05-12 14:09:55
     * MatchName : ceshi2
     * MatchNumber : 36
     * FootRingID : 0
     * MatchCount : 100
     * PigeonID : 6
     * MatchInterval : 0
     * PigeonMatchID : 7
     */

    private String MatchTime;
    private String MatchName;
    private int MatchNumber;
    private int FootRingID;
    private int MatchCount;
    private int PigeonID;
    private int MatchInterval;
    private int PigeonMatchID;
    /**
     * BitUpdate : 1
     * ConnectUrl : null
     */

    private int BitUpdate;
    private Object ConnectUrl;

    public String getMatchTime() {
        return MatchTime;
    }

    public void setMatchTime(String MatchTime) {
        this.MatchTime = MatchTime;
    }

    public String getMatchName() {
        return MatchName;
    }

    public void setMatchName(String MatchName) {
        this.MatchName = MatchName;
    }

    public int getMatchNumber() {
        return MatchNumber;
    }

    public void setMatchNumber(int MatchNumber) {
        this.MatchNumber = MatchNumber;
    }

    public int getFootRingID() {
        return FootRingID;
    }

    public void setFootRingID(int FootRingID) {
        this.FootRingID = FootRingID;
    }

    public int getMatchCount() {
        return MatchCount;
    }

    public void setMatchCount(int MatchCount) {
        this.MatchCount = MatchCount;
    }

    public int getPigeonID() {
        return PigeonID;
    }

    public void setPigeonID(int PigeonID) {
        this.PigeonID = PigeonID;
    }

    public int getMatchInterval() {
        return MatchInterval;
    }

    public void setMatchInterval(int MatchInterval) {
        this.MatchInterval = MatchInterval;
    }

    public int getPigeonMatchID() {
        return PigeonMatchID;
    }

    public void setPigeonMatchID(int PigeonMatchID) {
        this.PigeonMatchID = PigeonMatchID;
    }

    public int getBitUpdate() {
        return BitUpdate;
    }

    public void setBitUpdate(int BitUpdate) {
        this.BitUpdate = BitUpdate;
    }

    public Object getConnectUrl() {
        return ConnectUrl;
    }

    public void setConnectUrl(Object ConnectUrl) {
        this.ConnectUrl = ConnectUrl;
    }
}
