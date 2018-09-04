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

    private PigeonEntryEntity(Builder builder) {
        FootRingNum = builder.FootRingNum;
        PigeonID = builder.PigeonID;
        PigeonMoney = builder.PigeonMoney;
        FootRingID = builder.FootRingID;
    }


    public static final class Builder {
        private String FootRingNum;
        private String PigeonID;
        private String PigeonMoney;
        private String FootRingID;

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

        public PigeonEntryEntity build() {
            return new PigeonEntryEntity(this);
        }
    }


}
