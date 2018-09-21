package com.cpigeon.book.model.entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Administrator on 2018/9/15 0015.
 */

public class PriringRecommendEntity implements Parcelable {


    /**
     * PigeonBloodID : 17
     * PigeonSexName : 雄
     * PigeonSexID : 25
     * PigeonPlumeID : 12
     * FootRingID : 1202849
     * PigeonBloodName : 詹森
     * PigeonPlumeName : 红（绛）
     * PigeonID : 286
     * FootRingNum : 2018-01-6433161
     */

    private String PigeonBloodID;
    private String PigeonSexName;
    private String PigeonSexID;
    private String PigeonPlumeID;
    private String FootRingID;
    private String PigeonBloodName;
    private String PigeonPlumeName;
    private String PigeonID;
    private String FootRingNum;
    private String PigeonScore;
    private String MatchNumber;

    public String getPigeonBloodID() {
        return PigeonBloodID;
    }

    public void setPigeonBloodID(String pigeonBloodID) {
        PigeonBloodID = pigeonBloodID;
    }

    public String getPigeonSexName() {
        return PigeonSexName;
    }

    public void setPigeonSexName(String pigeonSexName) {
        PigeonSexName = pigeonSexName;
    }

    public String getPigeonSexID() {
        return PigeonSexID;
    }

    public void setPigeonSexID(String pigeonSexID) {
        PigeonSexID = pigeonSexID;
    }

    public String getPigeonPlumeID() {
        return PigeonPlumeID;
    }

    public void setPigeonPlumeID(String pigeonPlumeID) {
        PigeonPlumeID = pigeonPlumeID;
    }

    public String getFootRingID() {
        return FootRingID;
    }

    public void setFootRingID(String footRingID) {
        FootRingID = footRingID;
    }

    public String getPigeonBloodName() {
        return PigeonBloodName;
    }

    public void setPigeonBloodName(String pigeonBloodName) {
        PigeonBloodName = pigeonBloodName;
    }

    public String getPigeonPlumeName() {
        return PigeonPlumeName;
    }

    public void setPigeonPlumeName(String pigeonPlumeName) {
        PigeonPlumeName = pigeonPlumeName;
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

    public String getPigeonScore() {
        return PigeonScore;
    }

    public void setPigeonScore(String pigeonScore) {
        PigeonScore = pigeonScore;
    }

    public String getMatchNumber() {
        return MatchNumber;
    }

    public void setMatchNumber(String matchNumber) {
        MatchNumber = matchNumber;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.PigeonBloodID);
        dest.writeString(this.PigeonSexName);
        dest.writeString(this.PigeonSexID);
        dest.writeString(this.PigeonPlumeID);
        dest.writeString(this.FootRingID);
        dest.writeString(this.PigeonBloodName);
        dest.writeString(this.PigeonPlumeName);
        dest.writeString(this.PigeonID);
        dest.writeString(this.FootRingNum);
        dest.writeString(this.PigeonScore);
        dest.writeString(this.MatchNumber);
    }

    public PriringRecommendEntity() {
    }

    protected PriringRecommendEntity(Parcel in) {
        this.PigeonBloodID = in.readString();
        this.PigeonSexName = in.readString();
        this.PigeonSexID = in.readString();
        this.PigeonPlumeID = in.readString();
        this.FootRingID = in.readString();
        this.PigeonBloodName = in.readString();
        this.PigeonPlumeName = in.readString();
        this.PigeonID = in.readString();
        this.FootRingNum = in.readString();
        this.PigeonScore = in.readString();
        this.MatchNumber = in.readString();
    }

    public static final Parcelable.Creator<PriringRecommendEntity> CREATOR = new Parcelable.Creator<PriringRecommendEntity>() {
        @Override
        public PriringRecommendEntity createFromParcel(Parcel source) {
            return new PriringRecommendEntity(source);
        }

        @Override
        public PriringRecommendEntity[] newArray(int size) {
            return new PriringRecommendEntity[size];
        }
    };
}
