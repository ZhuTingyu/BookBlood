package com.cpigeon.book.model.entity;

/**
 * Created by Zhu TingYu on 2018/9/25.
 */

public class LeagueDetailsEntity {

    /**
     * FootRingID : 足环ID
     * MatchCount : 比赛羽数
     * MatchInterval : 比赛空距
     * MatchName : 比赛名称
     * MatchNumber : 比赛名次
     * MatchTime : 比赛时间
     * PigeonID : 鸽子id
     * PigeonMatchID : 赛绩id
     * ConnectUrl : 导入地址
     * BitUpdate : 1:添加,2:导入
     */

    private String FootRingID;
    private String MatchCount;
    private String MatchInterval;
    private String MatchName;
    private String MatchNumber;
    private String MatchTime;
    private String PigeonID;
    private String PigeonMatchID;
    private String ConnectUrl;
    private String BitUpdate;

    public String getFootRingID() {
        return FootRingID;
    }

    public void setFootRingID(String FootRingID) {
        this.FootRingID = FootRingID;
    }

    public String getMatchCount() {
        return MatchCount;
    }

    public void setMatchCount(String MatchCount) {
        this.MatchCount = MatchCount;
    }

    public String getMatchInterval() {
        return MatchInterval;
    }

    public void setMatchInterval(String MatchInterval) {
        this.MatchInterval = MatchInterval;
    }

    public String getMatchName() {
        return MatchName;
    }

    public void setMatchName(String MatchName) {
        this.MatchName = MatchName;
    }

    public String getMatchNumber() {
        return MatchNumber;
    }

    public void setMatchNumber(String MatchNumber) {
        this.MatchNumber = MatchNumber;
    }

    public String getMatchTime() {
        return MatchTime;
    }

    public void setMatchTime(String MatchTime) {
        this.MatchTime = MatchTime;
    }

    public String getPigeonID() {
        return PigeonID;
    }

    public void setPigeonID(String PigeonID) {
        this.PigeonID = PigeonID;
    }

    public String getPigeonMatchID() {
        return PigeonMatchID;
    }

    public void setPigeonMatchID(String PigeonMatchID) {
        this.PigeonMatchID = PigeonMatchID;
    }

    public String getConnectUrl() {
        return ConnectUrl;
    }

    public void setConnectUrl(String ConnectUrl) {
        this.ConnectUrl = ConnectUrl;
    }

    public String getBitUpdate() {
        return BitUpdate;
    }

    public void setBitUpdate(String BitUpdate) {
        this.BitUpdate = BitUpdate;
    }
}
