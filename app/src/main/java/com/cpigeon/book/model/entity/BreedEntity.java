package com.cpigeon.book.model.entity;

import java.io.Serializable;

/**
 * Created by Administrator on 2018/11/2 0002.
 */

public class BreedEntity implements Serializable {
    private String MenPigeonPlumeName;
    private String WoPigeonID;
    private String MenFootRingNum;
    private String MenPigeonBloodName;
    private String WoFootRingID;
    private String WoFootRingNum;
    private String MenFootRingID;
    private String MenPigeonID;
    private String WoPigeonPlumeName;
    private String WoPigeonBloodName;
    private String PigeonBreedID;
    private int BitTogether;//配对是否是分居（1是同居，0（或则是其他）是分居

    private BreedEntity(Builder builder) {
        setWoPigeonPlumeName(MenPigeonPlumeName);
        setWoPigeonID(builder.WoPigeonID);
        setMenFootRingNum(builder.MenFootRingNum);
        setMenPigeonBloodName(builder.MenPigeonBloodName);
        setWoFootRingID(builder.WoFootRingID);
        setWoPigeonPlumeName(builder.WoPigeonPlumeName);
        setWoPigeonBloodName(builder.WoPigeonBloodName);
        setWoFootRingNum(builder.WoFootRingNum);
        setMenFootRingID(builder.MenFootRingID);
        setMenPigeonID(builder.MenPigeonID);
        setPigeonBreedID(builder.PigeonBreedID);
    }

    public boolean isTogether(){
        return BitTogether == 1;
    }

    public String getMenPigeonPlumeName() {
        return MenPigeonPlumeName;
    }

    public void setMenPigeonPlumeName(String menPigeonPlumeName) {
        MenPigeonPlumeName = menPigeonPlumeName;
    }

    public String getWoPigeonID() {
        return WoPigeonID;
    }

    public void setWoPigeonID(String woPigeonID) {
        WoPigeonID = woPigeonID;
    }

    public String getMenFootRingNum() {
        return MenFootRingNum;
    }

    public void setMenFootRingNum(String menFootRingNum) {
        MenFootRingNum = menFootRingNum;
    }

    public String getMenPigeonBloodName() {
        return MenPigeonBloodName;
    }

    public void setMenPigeonBloodName(String menPigeonBloodName) {
        MenPigeonBloodName = menPigeonBloodName;
    }

    public String getWoFootRingID() {
        return WoFootRingID;
    }

    public void setWoFootRingID(String woFootRingID) {
        WoFootRingID = woFootRingID;
    }

    public String getWoFootRingNum() {
        return WoFootRingNum;
    }

    public void setWoFootRingNum(String woFootRingNum) {
        WoFootRingNum = woFootRingNum;
    }

    public String getMenFootRingID() {
        return MenFootRingID;
    }

    public void setMenFootRingID(String menFootRingID) {
        MenFootRingID = menFootRingID;
    }

    public String getMenPigeonID() {
        return MenPigeonID;
    }

    public void setMenPigeonID(String menPigeonID) {
        MenPigeonID = menPigeonID;
    }

    public String getWoPigeonPlumeName() {
        return WoPigeonPlumeName;
    }

    public void setWoPigeonPlumeName(String woPigeonPlumeName) {
        WoPigeonPlumeName = woPigeonPlumeName;
    }

    public String getWoPigeonBloodName() {
        return WoPigeonBloodName;
    }

    public void setWoPigeonBloodName(String woPigeonBloodName) {
        WoPigeonBloodName = woPigeonBloodName;
    }

    public String getPigeonBreedID() {
        return PigeonBreedID;
    }

    public void setPigeonBreedID(String pigeonBreedID) {
        PigeonBreedID = pigeonBreedID;
    }

    public static final class Builder {
        private String MenPigeonPlumeName;
        private String WoPigeonID;
        private String MenFootRingNum;
        private String MenPigeonBloodName;
        private String WoFootRingID;
        private String WoFootRingNum;
        private String MenFootRingID;
        private String MenPigeonID;
        private String WoPigeonPlumeName;
        private String WoPigeonBloodName;
        private String PigeonBreedID;

        public Builder() {
        }

        public Builder WoPigeonPlumeName(String val) {
            WoPigeonPlumeName = val;
            return this;
        }

        public Builder MenPigeonPlumeName(String val) {
            MenPigeonPlumeName = val;
            return this;
        }

        public Builder WoPigeonID(String val) {
            WoPigeonID = val;
            return this;
        }

        public Builder MenFootRingNum(String val) {
            MenFootRingNum = val;
            return this;
        }

        public Builder MenPigeonBloodName(String val) {
            MenPigeonBloodName = val;
            return this;
        }

        public Builder WoFootRingID(String val) {
            WoFootRingID = val;
            return this;
        }


        public Builder WoPigeonBloodName(String val) {
            WoPigeonBloodName = val;
            return this;
        }

        public Builder WoFootRingNum(String val) {
            WoFootRingNum = val;
            return this;
        }

        public Builder MenFootRingID(String val) {
            MenFootRingID = val;
            return this;
        }

        public Builder MenPigeonID(String val) {
            MenPigeonID = val;
            return this;
        }


        public Builder PigeonBreedID(String val) {
            PigeonBreedID = val;
            return this;
        }


        public BreedEntity build() {
            return new BreedEntity(this);
        }
    }
}
