package com.cpigeon.book.model.entity;

import java.io.Serializable;

/**
 * Created by Administrator on 2018/9/4.
 */

public class PigeonEntryEntity implements Serializable{


    /**
     * FootRingNum : 2018-01-3136464
     * PigeonID : 56
     * PigeonMoney : 5
     * FootRingID : 667134
     */

    private String FootRingNum;
    private String PigeonID;
    private String PigeonMoney;
    private String FootRingID;
    private String MatchInfoID;
    private String MatchInfo;
    private String PigeonMatchID;

    private PigeonEntryEntity(Builder builder) {
        setFootRingNum(builder.FootRingNum);
        setPigeonID(builder.PigeonID);
        setPigeonMoney(builder.PigeonMoney);
        setFootRingID(builder.FootRingID);
        setMatchInfoID(builder.MatchInfoID);
        setMatchInfo(builder.MatchInfo);
        setPigeonMatchID(builder.PigeonMatchID);
    }

    public String getFootRingNum() {
        return FootRingNum;
    }

    public void setFootRingNum(String footRingNum) {
        FootRingNum = footRingNum;
    }

    public String getPigeonID() {
        return PigeonID;
    }

    public void setPigeonID(String pigeonID) {
        PigeonID = pigeonID;
    }

    public String getPigeonMoney() {
        return PigeonMoney;
    }

    public void setPigeonMoney(String pigeonMoney) {
        PigeonMoney = pigeonMoney;
    }

    public String getFootRingID() {
        return FootRingID;
    }

    public void setFootRingID(String footRingID) {
        FootRingID = footRingID;
    }

    public String getMatchInfoID() {
        return MatchInfoID;
    }

    public void setMatchInfoID(String matchInfoID) {
        MatchInfoID = matchInfoID;
    }

    public String getMatchInfo() {
        return MatchInfo;
    }

    public void setMatchInfo(String matchInfo) {
        MatchInfo = matchInfo;
    }

    public String getPigeonMatchID() {
        return PigeonMatchID;
    }

    public void setPigeonMatchID(String pigeonMatchID) {
        PigeonMatchID = pigeonMatchID;
    }


    public static final class Builder {
        private String FootRingNum;
        private String PigeonID;
        private String PigeonMoney;
        private String FootRingID;
        private String MatchInfoID;
        private String MatchInfo;
        private String PigeonMatchID;

        public Builder() {
        }

        public Builder FootRingNum(String val) {
            FootRingNum = val;
            return this;
        }

        public Builder PigeonID(String val) {
            PigeonID = val;
            return this;
        }

        public Builder PigeonMoney(String val) {
            PigeonMoney = val;
            return this;
        }

        public Builder FootRingID(String val) {
            FootRingID = val;
            return this;
        }

        public Builder MatchInfoID(String val) {
            MatchInfoID = val;
            return this;
        }

        public Builder MatchInfo(String val) {
            MatchInfo = val;
            return this;
        }

        public Builder PigeonMatchID(String val) {
            PigeonMatchID = val;
            return this;
        }

        public PigeonEntryEntity build() {
            return new PigeonEntryEntity(this);
        }
    }
}
