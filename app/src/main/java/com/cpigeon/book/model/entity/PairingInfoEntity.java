package com.cpigeon.book.model.entity;

import java.io.Serializable;

/**
 * Created by Administrator on 2018/9/11.
 */

public class PairingInfoEntity implements Serializable {

    /**
     * MatchNumber : 赛绩排名
     * FootRingID : 足环id
     * FootRingNum : 足环号码
     * PigeonBloodName : 血统
     * PigeonSexName : 性别
     * PigeonID : 鸽子id
     * PigeonSexID : 性别
     * PigeonBloodID : 血统
     * PigeonPlumeName : 羽色
     * PigeonPlumeID : 羽色ID
     */


    /**
     * WoPigeonID : 284
     * MenFootRingNum : 2018-01-1111111
     * MenPigeonBloodName : 林波尔
     * WoFootRingID : 1202846
     * LayNum : 0
     * WoPigeonBloodName : 詹森
     * WoFootRingNum : 2018-01-1111111
     * MenFootRingID : 1202852
     * MenPigeonID : 289
     * PigeonBreedTime : 2018-09-15 00:00:00
     * PigeonBreedID : 83
     */

    private String WoPigeonID;
    private String MenFootRingNum;
    private String MenPigeonBloodName;
    private String WoFootRingID;
    private String LayNum;
    private String WoPigeonBloodName;
    private String WoFootRingNum;
    private String MenFootRingID;
    private String MenPigeonID;
    private String PigeonBreedTime;
    private String PigeonBreedID;
    private String PigeonBreedCount;
    private String WoPigeonPlumeName;
    private String MenPigeonPlumeName;
    private String WoCoverPhotoUrl;
    private String MenCoverPhotoUrl;


    private String WoPigeonStateName;//雌鸽子状态名称"
    private String MenPigeonStateName;//雄鸽子状态名称",
    private String WoPigeonStateID;//雌鸽子状态ID",
    private String MenPigeonStateID;//雄鸽子状态ID"


    public String getWoPigeonStateName() {
        return WoPigeonStateName;
    }

    public String getMenPigeonStateName() {
        return MenPigeonStateName;
    }

    public String getWoPigeonStateID() {
        return WoPigeonStateID;
    }

    public String getMenPigeonStateID() {
        return MenPigeonStateID;
    }

    public String getWoPigeonPlumeName() {
        return WoPigeonPlumeName;
    }

    public void setWoPigeonPlumeName(String woPigeonPlumeName) {
        WoPigeonPlumeName = woPigeonPlumeName;
    }

    public String getMenPigeonPlumeName() {
        return MenPigeonPlumeName;
    }

    public void setMenPigeonPlumeName(String menPigeonPlumeName) {
        MenPigeonPlumeName = menPigeonPlumeName;
    }

    public String getWoCoverPhotoUrl() {
        return WoCoverPhotoUrl;
    }

    public void setWoCoverPhotoUrl(String woCoverPhotoUrl) {
        WoCoverPhotoUrl = woCoverPhotoUrl;
    }

    public String getMenCoverPhotoUrl() {
        return MenCoverPhotoUrl;
    }

    public void setMenCoverPhotoUrl(String menCoverPhotoUrl) {
        MenCoverPhotoUrl = menCoverPhotoUrl;
    }

    private PairingInfoEntity(Builder builder) {
        setWoPigeonID(builder.WoPigeonID);
        setMenFootRingNum(builder.MenFootRingNum);
        setMenPigeonBloodName(builder.MenPigeonBloodName);
        setWoFootRingID(builder.WoFootRingID);
        setLayNum(builder.LayNum);
        setWoPigeonBloodName(builder.WoPigeonBloodName);
        setWoFootRingNum(builder.WoFootRingNum);
        setMenFootRingID(builder.MenFootRingID);
        setMenPigeonID(builder.MenPigeonID);
        setPigeonBreedTime(builder.PigeonBreedTime);
        setPigeonBreedID(builder.PigeonBreedID);
        setPigeonBreedCount(builder.PigeonBreedCount);
        setWoPigeonID(builder.WoCoverPhotoUrl);
        setWoPigeonID(builder.MenCoverPhotoUrl);
        setWoPigeonID(builder.MenPigeonPlumeName);
        setWoPigeonID(builder.WoPigeonPlumeName);
    }


    public String getPigeonBreedCount() {
        return PigeonBreedCount;
    }

    public void setPigeonBreedCount(String pigeonBreedCount) {
        PigeonBreedCount = pigeonBreedCount;
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

    public String getLayNum() {
        return LayNum;
    }

    public void setLayNum(String layNum) {
        LayNum = layNum;
    }

    public String getWoPigeonBloodName() {
        return WoPigeonBloodName;
    }

    public void setWoPigeonBloodName(String woPigeonBloodName) {
        WoPigeonBloodName = woPigeonBloodName;
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

    public String getPigeonBreedTime() {
        return PigeonBreedTime;
    }

    public void setPigeonBreedTime(String pigeonBreedTime) {
        PigeonBreedTime = pigeonBreedTime;
    }

    public String getPigeonBreedID() {
        return PigeonBreedID;
    }

    public void setPigeonBreedID(String pigeonBreedID) {
        PigeonBreedID = pigeonBreedID;
    }


    public static final class Builder {
        private String WoPigeonID;
        private String MenFootRingNum;
        private String MenPigeonBloodName;
        private String WoFootRingID;
        private String LayNum;
        private String WoPigeonBloodName;
        private String WoFootRingNum;
        private String MenFootRingID;
        private String MenPigeonID;
        private String PigeonBreedTime;
        private String PigeonBreedID;
        private String PigeonBreedCount;
        private String WoPigeonPlumeName;
        private String MenPigeonPlumeName;
        private String WoCoverPhotoUrl;
        private String MenCoverPhotoUrl;


        public Builder() {
        }

        public Builder WoPigeonID(String val) {
            WoPigeonID = val;
            return this;
        }

        public Builder WoPigeonPlumeName(String val) {
            WoPigeonPlumeName = val;
            return this;
        }

        public Builder MenPigeonPlumeName(String val) {
            MenPigeonPlumeName = val;
            return this;
        }

        public Builder WoCoverPhotoUrl(String val) {
            WoCoverPhotoUrl = val;
            return this;
        }

        public Builder MenCoverPhotoUrl(String val) {
            MenCoverPhotoUrl = val;
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

        public Builder LayNum(String val) {
            LayNum = val;
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

        public Builder PigeonBreedTime(String val) {
            PigeonBreedTime = val;
            return this;
        }

        public Builder PigeonBreedID(String val) {
            PigeonBreedID = val;
            return this;
        }

        public Builder PigeonBreedCount(String val) {
            PigeonBreedCount = val;
            return this;
        }

        public PairingInfoEntity build() {
            return new PairingInfoEntity(this);
        }
    }
}
