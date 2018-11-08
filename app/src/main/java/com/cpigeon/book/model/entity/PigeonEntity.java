package com.cpigeon.book.model.entity;

import android.os.Parcel;
import android.os.Parcelable;

import com.base.util.Lists;
import com.base.util.Utils;
import com.base.util.utility.StringUtil;
import com.cpigeon.book.R;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2018/9/4.
 */

public class PigeonEntity implements Serializable {

    public static final String ID_NONE_SEX = "12";//幼鸽未知
    public static final String ID_FEMALE = "13";//雌
    public static final String ID_MALE = "14";//雄
    public static final String ID_BREED_PIGEON = "9";//种鸽
    public static final String ID_MATCH_PIGEON = "10";//赛鸽
    public static final String ID_ALL = "35,36,37,38,39,40";
    public static final String IN_THE_SHED= "35,36";
    public static  String TIME = "TIME";
    public static final String BIT_MATCH = "0"; //是否返回赛绩（0，不返回）
    public static final String BIT_MATCH_NO = "1"; //是否返回赛绩（1，返回）
    public static final String SEXYEAR = "1";
    public static final String STATUS_OPEN_SERVICE = "1"; //已经开通服务
    public static final String STATUS_NOT_OPEN_SERVICE = "0"; //没开通服务

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
    private String PigeonPlumeName;//雨色
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
    private String TypeID = "";
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
    private String PigeonSexID; //25雄  24雌
    private String MenFootRingNum;//  父足环号码
    private String WoFootRingNum;//母足环号码
    private String FootCode;// 国家编码
    private String FootCodeID;// 国家id
    private String CoverPhotoTypeID;// 图片typeId
    private String CoverPhotoTypeName;// 图片typeName
    private String MatchCount;// 参赛次数
    //共享厅
    private String ShareTime;
    private String Province;
    private String City;
    private String UserID;
    private String UserName;
    private String UserService;//0 未开通 1 已开通
    private String PigeonHomePhone;
    private String MenPigeonStateID;
    private String WoPigeonStateID;
    private String WoPigeonStateName;
    private String MenPigeonStateName;
    private List<PigeonPlayEntity> MatchInfoList;
    private List<PigeonPlayEntity> MatchList;

    public String getWoPigeonStateName() {
        return WoPigeonStateName;
    }

    public void setWoPigeonStateName(String woPigeonStateName) {
        WoPigeonStateName = woPigeonStateName;
    }

    public String getMenPigeonStateName() {
        return MenPigeonStateName;
    }

    public void setMenPigeonStateName(String menPigeonStateName) {
        MenPigeonStateName = menPigeonStateName;
    }

    private String PigeonMoney;
    private String MatchInfoID;
    private String MatchInfo;
    private String PigeonMatchID;

    public List<PigeonPlayEntity> getMatchList() {
        return MatchList;
    }

    public void setMatchInfoList(List<PigeonPlayEntity> matchInfoList) {
        MatchInfoList = matchInfoList;
    }

    public String getMenPigeonStateID() {
        return MenPigeonStateID;
    }

    public void setMenPigeonStateID(String menPigeonStateID) {
        this.MenPigeonStateID = menPigeonStateID;
    }

    public String getWoPigeonStateID() {
        return WoPigeonStateID;
    }

    public void setWoPigeonStateID(String woPigeonStateID) {
        this.WoPigeonStateID = woPigeonStateID;
    }

    public String getPigeonMoney() {
        return PigeonMoney;
    }

    public void setPigeonMoney(String pigeonMoney) {
        PigeonMoney = pigeonMoney;
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

    public List<PigeonPlayEntity> getMatchInfoList() {
        return MatchInfoList;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public String getUserService() {
        return UserService;
    }

    public void setUserService(String userService) {
        UserService = userService;
    }

    public String getPigeonHomePhone() {
        return PigeonHomePhone;
    }

    public void setPigeonHomePhone(String pigeonHomePhone) {
        PigeonHomePhone = pigeonHomePhone;
    }

    public String getMatchCount() {
        return MatchCount;
    }

    public void setMatchCount(String matchCount) {
        MatchCount = matchCount;
    }

    private boolean isSelect = false;

    public PigeonEntity() {
    }


    public boolean isEmpty() {
        if (StringUtil.isStringValid(PigeonID)) {
            return false;
        } else return true;
    }

    public boolean isHaveDetailsInfo() {
        if (StringUtil.isStringValid(PigeonBloodName)) {
            return true;
        } else return false;
    }

    public boolean isMale() {
        return Utils.getString(R.string.text_male_a).equals(PigeonSexName);
    }

    public List<PigeonPlayEntity> getMatchData() {
        List<PigeonPlayEntity> data = Lists.newArrayList();
        data.addAll(getMatchList());
        if (data.size() >= 3) {
            return data.subList(0, 2);
        }else {
            data.addAll(getMatchInfoList());
            if(data.size() >= 3){
                return data.subList(0, 2);
            }else {
                return data;
            }
        }
    }

    private PigeonEntity(Builder builder) {
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
        setCoverPhotoTypeID(builder.CoverPhotoTypeID);
        setCoverPhotoTypeName(builder.CoverPhotoTypeName);
        setMatchCount(builder.MatchCount);
        setShareTime(builder.ShareTime);
        setProvince(builder.Province);
        setCity(builder.City);
        setUserID(builder.UserID);
        setUserName(builder.UserName);
        setUserService(builder.UserService);
        setPigeonHomePhone(builder.PigeonHomePhone);
        setMatchInfoList(builder.MatchInfoList);
        MatchList = builder.MatchList;
        setPigeonMoney(builder.PigeonMoney);
        setMatchInfoID(builder.MatchInfoID);
        setMatchInfo(builder.MatchInfo);
        setPigeonMatchID(builder.PigeonMatchID);
        setMenPigeonStateID(builder.MenPigeonStateID);
        setWoPigeonStateID(builder.WoPigeonStateID);
        isSelect = builder.isSelect;
    }

    public String getShareTime() {
        return ShareTime;
    }

    public void setShareTime(String shareTime) {
        ShareTime = shareTime;
    }

    public String getProvince() {
        return Province;
    }

    public void setProvince(String province) {
        Province = province;
    }

    public String getCity() {
        return City;
    }

    public void setCity(String city) {
        City = city;
    }

    public String getUserID() {
        return UserID;
    }

    public void setUserID(String userID) {
        UserID = userID;
    }

    public String getCoverPhotoTypeID() {
        return CoverPhotoTypeID;
    }

    public void setCoverPhotoTypeID(String coverPhotoTypeID) {
        CoverPhotoTypeID = coverPhotoTypeID;
    }

    public String getCoverPhotoTypeName() {
        return CoverPhotoTypeName;
    }

    public void setCoverPhotoTypeName(String coverPhotoTypeName) {
        CoverPhotoTypeName = coverPhotoTypeName;
    }


    public boolean isSelect() {
        return isSelect;
    }

    public void setSelecte(boolean select) {
        isSelect = select;
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
        private String CoverPhotoTypeID;// 图片typeId
        private String CoverPhotoTypeName;// 图片typeName
        private String MatchCount;
        private String ShareTime;
        private String Province;
        private String City;
        private String UserID;
        private String UserName;
        private String UserService;
        private String PigeonHomePhone;
        private List<PigeonPlayEntity> MatchInfoList;
        private List<PigeonPlayEntity> MatchList;
        private String PigeonMoney;
        private String MatchInfoID;
        private String MatchInfo;
        private String PigeonMatchID;
        private String MenPigeonStateID;
        private String WoPigeonStateID;
        private boolean isSelect;
        private String WoPigeonStateName;
        private String MenPigeonStateName;
        public Builder() {
        }

        public Builder CoverPhotoUrl(String val) {
            CoverPhotoUrl = val;
            return this;
        }

        public String getCoverPhotoUrl() {
            return CoverPhotoUrl;
        }

        public void setCoverPhotoUrl(String coverPhotoUrl) {
            CoverPhotoUrl = coverPhotoUrl;
        }

        public String getCoverPhotoTypeID() {
            return CoverPhotoTypeID;
        }

        public void setCoverPhotoTypeID(String coverPhotoTypeID) {
            CoverPhotoTypeID = coverPhotoTypeID;
        }

        public String getCoverPhotoTypeName() {
            return CoverPhotoTypeName;
        }

        public void setCoverPhotoTypeName(String coverPhotoTypeName) {
            CoverPhotoTypeName = coverPhotoTypeName;
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
        public Builder MenPigeonStateID(String val) {
            MenPigeonStateID = val;
            return this;
        }
        public Builder WoPigeonStateID(String val) {
            WoPigeonStateID = val;
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

        public Builder CoverPhotoTypeID(String val) {
            CoverPhotoTypeID = val;
            return this;
        }

        public Builder CoverPhotoTypeName(String val) {
            CoverPhotoTypeName = val;
            return this;
        }

        public Builder MatchCount(String val) {
            MatchCount = val;
            return this;
        }

        public Builder ShareTime(String val) {
            ShareTime = val;
            return this;
        }

        public Builder Province(String val) {
            Province = val;
            return this;
        }

        public Builder City(String val) {
            City = val;
            return this;
        }

        public Builder UserID(String val) {
            UserID = val;
            return this;
        }

        public Builder UserName(String val) {
            UserName = val;
            return this;
        }

        public Builder UserService(String val) {
            UserService = val;
            return this;
        }

        public Builder PigeonHomePhone(String val) {
            PigeonHomePhone = val;
            return this;
        }

        public Builder MatchInfoList(List<PigeonPlayEntity> val) {
            MatchInfoList = val;
            return this;
        }

        public Builder MatchList(List<PigeonPlayEntity> val) {
            MatchList = val;
            return this;
        }

        public Builder PigeonMoney(String val) {
            PigeonMoney = val;
            return this;
        }

        public Builder MenPigeonStateName(String val) {
            MenPigeonStateName = val;
            return this;
        }
        public Builder WoPigeonStateName(String val) {
            WoPigeonStateName = val;
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

        public Builder isSelect(boolean val) {
            isSelect = val;
            return this;
        }

        public PigeonEntity build() {
            return new PigeonEntity(this);
        }
    }


    protected PigeonEntity(Parcel in) {
        this.WoPigeonStateName= in.readString();
        this.MenPigeonStateName= in.readString();
        this.MenPigeonStateID= in.readString();
        this.WoPigeonStateID= in.readString();
        this.CoverPhotoUrl = in.readString();
        this.CoverPhotoID = in.readString();
        this.FootRingID = in.readString();
        this.StateID = in.readString();
        this.StateName = in.readString();
        this.PigeonPlumeName = in.readString();
        this.PigeonSexName = in.readString();
        this.PigeonID = in.readString();
        this.FootRingNum = in.readString();
        this.FootRingIDToNum = in.readString();
        this.TypeID = in.readString();
        this.FootRingTimeTo = in.readString();
        this.SourceID = in.readString();
        this.PigeonName = in.readString();
        this.PigeonBreedID = in.readString();
        this.TypeName = in.readString();
        this.OutShellTime = in.readString();
        this.PigeonScore = in.readString();
        this.FootRingTime = in.readString();
        this.FootRingIDTo = in.readString();
        this.PigeonBloodName = in.readString();
        this.SourceName = in.readString();
        this.PigeonEyeName = in.readString();
        this.PigeonBloodID = in.readString();
        this.PigeonPlumeID = in.readString();
        this.Remark = in.readString();
        this.PigeonEyeID = in.readString();
        this.PigeonSexID = in.readString();
        this.MenFootRingNum = in.readString();
        this.WoFootRingNum = in.readString();
        this.FootCode = in.readString();
        this.FootCodeID = in.readString();
        this.CoverPhotoTypeID = in.readString();
        this.CoverPhotoTypeName = in.readString();
        this.MatchCount = in.readString();
        this.ShareTime = in.readString();
        this.Province = in.readString();
        this.City = in.readString();
        this.UserID = in.readString();
        this.UserName = in.readString();
        this.UserService = in.readString();
        this.PigeonHomePhone = in.readString();
        this.MatchInfoList = in.createTypedArrayList(PigeonPlayEntity.CREATOR);
        this.isSelect = in.readByte() != 0;
    }

    public static final Parcelable.Creator<PigeonEntity> CREATOR = new Parcelable.Creator<PigeonEntity>() {
        @Override
        public PigeonEntity createFromParcel(Parcel source) {
            return new PigeonEntity(source);
        }

        @Override
        public PigeonEntity[] newArray(int size) {
            return new PigeonEntity[size];
        }
    };
}
