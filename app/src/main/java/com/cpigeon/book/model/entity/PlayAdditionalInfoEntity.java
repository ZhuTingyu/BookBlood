package com.cpigeon.book.model.entity;

/**
 * Created by Administrator on 2018/9/5.
 */

public class PlayAdditionalInfoEntity {


    /**
     * PigeonID : 45
     * InfoListID : 3
     * FootRingID : 666147
     * MatchInfo : 这是一条测试的附加信
     */

    private int PigeonID;
    private int InfoListID;
    private int FootRingID;
    private String MatchInfo;

    public int getPigeonID() {
        return PigeonID;
    }

    public void setPigeonID(int PigeonID) {
        this.PigeonID = PigeonID;
    }

    public int getInfoListID() {
        return InfoListID;
    }

    public void setInfoListID(int InfoListID) {
        this.InfoListID = InfoListID;
    }

    public int getFootRingID() {
        return FootRingID;
    }

    public void setFootRingID(int FootRingID) {
        this.FootRingID = FootRingID;
    }

    public String getMatchInfo() {
        return MatchInfo;
    }

    public void setMatchInfo(String MatchInfo) {
        this.MatchInfo = MatchInfo;
    }
}
