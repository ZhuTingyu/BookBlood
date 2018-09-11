package com.cpigeon.book.model.entity;

import java.io.Serializable;

/**
 * Created by Administrator on 2018/9/4.
 */

public class BreedPigeonEntity implements Serializable {


    /**
     * CoverPhotoUrl :
     * PigeonPlume : 16
     * CoverPhotoID : 0
     * PigeonSex : 24
     * FootRingID : 666148
     * StateID : 51
     * StateName : 现役在棚
     * PigeonPlumeName : 黑
     * PigeonSexName : 雌
     * PigeonID : 46
     * FootRingNum : 2018-01-44
     */

    private String CoverPhotoUrl;
    private String CoverPhotoID;
    private String FootRingID;
    private String StateID;
    private String StateName;
    private String PigeonPlumeName;
    private String PigeonSexName;
    private String PigeonID;
    private String FootRingNum;
    /**
     * PigeonEye : 27
     * FootRingIDToNum :
     * TypeID : 8
     * FootRingTimeTo : 0001/1/1 0:00:00
     * SourceID : 44
     * PigeonName :
     * PigeonBreedID : 4
     * TypeName : 种鸽
     * OutShellTime : 2018/7/31 0:00:00
     * PigeonBlood : 62
     * PigeonScore : 0
     * FootRingTime : 2018/8/31 13:30:33
     * FootRingIDTo : 0
     * PigeonBloodName : 红（绛）
     * SourceName : 繁育
     * PigeonEyeName : 幼鸽未知
     */
    private String FootRingIDToNum;
    private String TypeID;
    private String FootRingTimeTo;
    private String SourceID;
    private String PigeonName;
    private String PigeonBreedID;
    private String TypeName;
    private String OutShellTime;//出壳日期
    private String PigeonScore;
    private String FootRingTime;
    private String FootRingIDTo;
    private String PigeonBloodName;//血统名字
    private String SourceName;
    private String PigeonEyeName;
    private String PigeonBloodID;//血统id
    private String PigeonPlumeID;
    private String Remark;
    private String PigeonEyeID;
    private String PigeonSexID;
    private String MenFootRingNum;//  父足环号码
    private String WoFootRingNum;//母足环号码
    private String FootCode;// 国家编码
    private String FootCodeID;// 国家id

    public BreedPigeonEntity(){};

    private BreedPigeonEntity(Builder builder) {
        setCoverPhotoUrl(builder.CoverPhotoUrl);
        setCoverPhotoID(builder.CoverPhotoID);
        setFootRingID(builder.FootRingID);
        setStateID(builder.StateID);
        setStateName(builder.StateName);
        setPigeonPlumeName(builder.PigeonPlumeName);
        setPigeonSexName(builder.PigeonSexName);
        setPigeonID(builder.PigeonID);
        setFootRingNum(builder.FootRingNum);
        setFootRingIDToNum(builder.FootRingIDToNum);
        setTypeID(builder.TypeID);
        setFootRingTimeTo(builder.FootRingTimeTo);
        setSourceID(builder.SourceID);
        setPigeonName(builder.PigeonName);
        setPigeonBreedID(builder.PigeonBreedID);
        setTypeName(builder.TypeName);
        setOutShellTime(builder.OutShellTime);
        setPigeonScore(builder.PigeonScore);
        setFootRingTime(builder.FootRingTime);
        setFootRingIDTo(builder.FootRingIDTo);
        setPigeonBloodName(builder.PigeonBloodName);
        setSourceName(builder.SourceName);
        setPigeonEyeName(builder.PigeonEyeName);
        setPigeonBloodID(builder.PigeonBloodID);
        setPigeonPlumeID(builder.PigeonPlumeID);
        setRemark(builder.Remark);
        setPigeonEyeID(builder.PigeonEyeID);
        setPigeonSexID(builder.PigeonSexID);
        setMenFootRingNum(builder.MenFootRingNum);
        setWoFootRingNum(builder.WoFootRingNum);
        setFootCode(builder.FootCode);
        setFootCodeID(builder.FootCodeID);
    }

    public String getCoverPhotoUrl() {
        return CoverPhotoUrl;
    }

    public void setCoverPhotoUrl(String coverPhotoUrl) {
        CoverPhotoUrl = coverPhotoUrl;
    }

    public String getCoverPhotoID() {
        return CoverPhotoID;
    }

    public void setCoverPhotoID(String coverPhotoID) {
        CoverPhotoID = coverPhotoID;
    }

    public String getFootRingID() {
        return FootRingID;
    }

    public void setFootRingID(String footRingID) {
        FootRingID = footRingID;
    }

    public String getStateID() {
        return StateID;
    }

    public void setStateID(String stateID) {
        StateID = stateID;
    }

    public String getStateName() {
        return StateName;
    }

    public void setStateName(String stateName) {
        StateName = stateName;
    }

    public String getPigeonPlumeName() {
        return PigeonPlumeName;
    }

    public void setPigeonPlumeName(String pigeonPlumeName) {
        PigeonPlumeName = pigeonPlumeName;
    }

    public String getPigeonSexName() {
        return PigeonSexName;
    }

    public void setPigeonSexName(String pigeonSexName) {
        PigeonSexName = pigeonSexName;
    }

    public String getPigeonID() {
        return PigeonID;
    }

    public void setPigeonID(String pigeonID) {
        PigeonID = pigeonID;
    }

    public String getFootRingNum() {
        return FootRingNum;
    }

    public void setFootRingNum(String footRingNum) {
        FootRingNum = footRingNum;
    }



    public String getFootRingIDToNum() {
        return FootRingIDToNum;
    }

    public void setFootRingIDToNum(String footRingIDToNum) {
        FootRingIDToNum = footRingIDToNum;
    }

    public String getTypeID() {
        return TypeID;
    }

    public void setTypeID(String typeID) {
        TypeID = typeID;
    }

    public String getFootRingTimeTo() {
        return FootRingTimeTo;
    }

    public void setFootRingTimeTo(String footRingTimeTo) {
        FootRingTimeTo = footRingTimeTo;
    }

    public String getSourceID() {
        return SourceID;
    }

    public void setSourceID(String sourceID) {
        SourceID = sourceID;
    }

    public String getPigeonName() {
        return PigeonName;
    }

    public void setPigeonName(String pigeonName) {
        PigeonName = pigeonName;
    }

    public String getPigeonBreedID() {
        return PigeonBreedID;
    }

    public void setPigeonBreedID(String pigeonBreedID) {
        PigeonBreedID = pigeonBreedID;
    }

    public String getTypeName() {
        return TypeName;
    }

    public void setTypeName(String typeName) {
        TypeName = typeName;
    }

    public String getOutShellTime() {
        return OutShellTime;
    }

    public void setOutShellTime(String outShellTime) {
        OutShellTime = outShellTime;
    }



    public String getPigeonScore() {
        return PigeonScore;
    }

    public void setPigeonScore(String pigeonScore) {
        PigeonScore = pigeonScore;
    }

    public String getFootRingTime() {
        return FootRingTime;
    }

    public void setFootRingTime(String footRingTime) {
        FootRingTime = footRingTime;
    }

    public String getFootRingIDTo() {
        return FootRingIDTo;
    }

    public void setFootRingIDTo(String footRingIDTo) {
        FootRingIDTo = footRingIDTo;
    }

    public String getPigeonBloodName() {
        return PigeonBloodName;
    }

    public void setPigeonBloodName(String pigeonBloodName) {
        PigeonBloodName = pigeonBloodName;
    }

    public String getSourceName() {
        return SourceName;
    }

    public void setSourceName(String sourceName) {
        SourceName = sourceName;
    }

    public String getPigeonEyeName() {
        return PigeonEyeName;
    }

    public void setPigeonEyeName(String pigeonEyeName) {
        PigeonEyeName = pigeonEyeName;
    }

    public String getPigeonBloodID() {
        return PigeonBloodID;
    }

    public void setPigeonBloodID(String pigeonBloodID) {
        PigeonBloodID = pigeonBloodID;
    }

    public String getPigeonPlumeID() {
        return PigeonPlumeID;
    }

    public void setPigeonPlumeID(String pigeonPlumeID) {
        PigeonPlumeID = pigeonPlumeID;
    }

    public String getRemark() {
        return Remark;
    }

    public void setRemark(String remark) {
        Remark = remark;
    }

    public String getPigeonEyeID() {
        return PigeonEyeID;
    }

    public void setPigeonEyeID(String pigeonEyeID) {
        PigeonEyeID = pigeonEyeID;
    }

    public String getPigeonSexID() {
        return PigeonSexID;
    }

    public void setPigeonSexID(String pigeonSexID) {
        PigeonSexID = pigeonSexID;
    }

    public String getMenFootRingNum() {
        return MenFootRingNum;
    }

    public void setMenFootRingNum(String menFootRingNum) {
        MenFootRingNum = menFootRingNum;
    }

    public String getWoFootRingNum() {
        return WoFootRingNum;
    }

    public void setWoFootRingNum(String woFootRingNum) {
        WoFootRingNum = woFootRingNum;
    }

    public String getFootCode() {
        return FootCode;
    }

    public void setFootCode(String footCode) {
        FootCode = footCode;
    }

    public String getFootCodeID() {
        return FootCodeID;
    }

    public void setFootCodeID(String footCodeID) {
        FootCodeID = footCodeID;
    }


    public static final class Builder {
        private String CoverPhotoUrl;
        private String PigeonPlume;
        private String CoverPhotoID;
        private String PigeonSex;
        private String FootRingID;
        private String StateID;
        private String StateName;
        private String PigeonPlumeName;
        private String PigeonSexName;
        private String PigeonID;
        private String FootRingNum;
        private String PigeonEye;
        private String FootRingIDToNum;
        private String TypeID;
        private String FootRingTimeTo;
        private String SourceID;
        private String PigeonName;
        private String PigeonBreedID;
        private String TypeName;
        private String OutShellTime;
        private String PigeonBlood;
        private String PigeonScore;
        private String FootRingTime;
        private String FootRingIDTo;
        private String PigeonBloodName;
        private String SourceName;
        private String PigeonEyeName;
        private String PigeonBloodID;
        private String PigeonPlumeID;
        private String Remark;
        private String PigeonEyeID;
        private String PigeonSexID;
        private String MenFootRingNum;
        private String WoFootRingNum;
        private String FootCode;
        private String FootCodeID;

        public Builder() {
        }

        public Builder CoverPhotoUrl(String val) {
            CoverPhotoUrl = val;
            return this;
        }

        public Builder PigeonPlume(String val) {
            PigeonPlume = val;
            return this;
        }

        public Builder CoverPhotoID(String val) {
            CoverPhotoID = val;
            return this;
        }

        public Builder PigeonSex(String val) {
            PigeonSex = val;
            return this;
        }

        public Builder FootRingID(String val) {
            FootRingID = val;
            return this;
        }

        public Builder StateID(String val) {
            StateID = val;
            return this;
        }

        public Builder StateName(String val) {
            StateName = val;
            return this;
        }

        public Builder PigeonPlumeName(String val) {
            PigeonPlumeName = val;
            return this;
        }

        public Builder PigeonSexName(String val) {
            PigeonSexName = val;
            return this;
        }

        public Builder PigeonID(String val) {
            PigeonID = val;
            return this;
        }

        public Builder FootRingNum(String val) {
            FootRingNum = val;
            return this;
        }

        public Builder PigeonEye(String val) {
            PigeonEye = val;
            return this;
        }

        public Builder FootRingIDToNum(String val) {
            FootRingIDToNum = val;
            return this;
        }

        public Builder TypeID(String val) {
            TypeID = val;
            return this;
        }

        public Builder FootRingTimeTo(String val) {
            FootRingTimeTo = val;
            return this;
        }

        public Builder SourceID(String val) {
            SourceID = val;
            return this;
        }

        public Builder PigeonName(String val) {
            PigeonName = val;
            return this;
        }

        public Builder PigeonBreedID(String val) {
            PigeonBreedID = val;
            return this;
        }

        public Builder TypeName(String val) {
            TypeName = val;
            return this;
        }

        public Builder OutShellTime(String val) {
            OutShellTime = val;
            return this;
        }

        public Builder PigeonBlood(String val) {
            PigeonBlood = val;
            return this;
        }

        public Builder PigeonScore(String val) {
            PigeonScore = val;
            return this;
        }

        public Builder FootRingTime(String val) {
            FootRingTime = val;
            return this;
        }

        public Builder FootRingIDTo(String val) {
            FootRingIDTo = val;
            return this;
        }

        public Builder PigeonBloodName(String val) {
            PigeonBloodName = val;
            return this;
        }

        public Builder SourceName(String val) {
            SourceName = val;
            return this;
        }

        public Builder PigeonEyeName(String val) {
            PigeonEyeName = val;
            return this;
        }

        public Builder PigeonBloodID(String val) {
            PigeonBloodID = val;
            return this;
        }

        public Builder PigeonPlumeID(String val) {
            PigeonPlumeID = val;
            return this;
        }

        public Builder Remark(String val) {
            Remark = val;
            return this;
        }

        public Builder PigeonEyeID(String val) {
            PigeonEyeID = val;
            return this;
        }

        public Builder PigeonSexID(String val) {
            PigeonSexID = val;
            return this;
        }

        public Builder MenFootRingNum(String val) {
            MenFootRingNum = val;
            return this;
        }

        public Builder WoFootRingNum(String val) {
            WoFootRingNum = val;
            return this;
        }

        public Builder FootCode(String val) {
            FootCode = val;
            return this;
        }

        public Builder FootCodeID(String val) {
            FootCodeID = val;
            return this;
        }

        public BreedPigeonEntity build() {
            return new BreedPigeonEntity(this);
        }
    }
}
