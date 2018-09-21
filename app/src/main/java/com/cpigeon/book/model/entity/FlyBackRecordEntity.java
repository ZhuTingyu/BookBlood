package com.cpigeon.book.model.entity;

import android.os.Parcel;
import android.os.Parcelable;

import com.base.entity.ExpendEntity;
import com.base.entity.RaceEntity;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * Created by Zhu TingYu on 2018/9/20.
 */

public class FlyBackRecordEntity extends ExpendEntity implements RaceEntity
        , Parcelable, Comparator<FlyBackRecordEntity> {

    /**
     * PigeonTrainID : 训练表id
     * PigeonTrainCountID : 训练次数id
     * PigeonTrainDataID : 训练鸽子id
     * FootRingID : 足环id
     * FootRingNum : 足环号码
     * Fraction : 分速
     * FromFlyTime : 返回时间
     * EndFlyTime : 结束时间
     * Score : 分数
     * PigeonPlumeName : 羽数
     * PigeonBloodName : 血统
     */

    private String PigeonTrainID;
    private String PigeonTrainCountID;
    private String PigeonTrainDataID;
    private String FootRingID;
    private String FootRingNum;
    private double Fraction;
    private String FromFlyTime;
    private String EndFlyTime;
    private String Score;
    private String PigeonPlumeName;
    private String PigeonBloodName;
    private int FlyCount;
    private String PigeonID;

    public int order;
    public List<FlyBackRecordEntity> mFlyBackRecordExpandEntity;

    public void setFlyCount(int flyCount) {
        FlyCount = flyCount;
    }

    public String getPigeonID() {
        return PigeonID;
    }

    public void setPigeonID(String pigeonID) {
        PigeonID = pigeonID;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public List<FlyBackRecordEntity> getFlyBackRecordExpandEntity() {
        return mFlyBackRecordExpandEntity;
    }

    public void setFlyBackRecordExpandEntity(List<FlyBackRecordEntity> flyBackRecordExpandEntity) {
        mFlyBackRecordExpandEntity = flyBackRecordExpandEntity;
    }

    public int getFlyCount() {
        return FlyCount;
    }

    public int getOrder() {
        return order;
    }

    public String getPigeonTrainID() {
        return PigeonTrainID;
    }

    public void setPigeonTrainID(String PigeonTrainID) {
        this.PigeonTrainID = PigeonTrainID;
    }

    public String getPigeonTrainCountID() {
        return PigeonTrainCountID;
    }

    public void setPigeonTrainCountID(String PigeonTrainCountID) {
        this.PigeonTrainCountID = PigeonTrainCountID;
    }

    public String getPigeonTrainDataID() {
        return PigeonTrainDataID;
    }

    public void setPigeonTrainDataID(String PigeonTrainDataID) {
        this.PigeonTrainDataID = PigeonTrainDataID;
    }

    public String getFootRingID() {
        return FootRingID;
    }

    public void setFootRingID(String FootRingID) {
        this.FootRingID = FootRingID;
    }

    public String getFootRingNum() {
        return FootRingNum;
    }

    public void setFootRingNum(String FootRingNum) {
        this.FootRingNum = FootRingNum;
    }

    public double getFraction() {
        return Fraction;
    }

    public void setFraction(double Fraction) {
        this.Fraction = Fraction;
    }

    public String getFromFlyTime() {
        return FromFlyTime;
    }

    public void setFromFlyTime(String FromFlyTime) {
        this.FromFlyTime = FromFlyTime;
    }

    public String getEndFlyTime() {
        return EndFlyTime;
    }

    public void setEndFlyTime(String EndFlyTime) {
        this.EndFlyTime = EndFlyTime;
    }

    public String getScore() {
        return Score;
    }

    public void setScore(String Score) {
        this.Score = Score;
    }

    public String getPigeonPlumeName() {
        return PigeonPlumeName;
    }

    public void setPigeonPlumeName(String PigeonPlumeName) {
        this.PigeonPlumeName = PigeonPlumeName;
    }

    public String getPigeonBloodName() {
        return PigeonBloodName;
    }

    public void setPigeonBloodName(String PigeonBloodName) {
        this.PigeonBloodName = PigeonBloodName;
    }

    @Override
    public List<FlyBackRecordEntity> getRace() {
        return mFlyBackRecordExpandEntity;
    }

    public FlyBackRecordEntity() {
    }

    @Override
    public int compare(FlyBackRecordEntity o1, FlyBackRecordEntity o2) {
        return (int) ((o1.getFraction() * 1000) - (o2.getFraction() * 1000));
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.PigeonTrainID);
        dest.writeString(this.PigeonTrainCountID);
        dest.writeString(this.PigeonTrainDataID);
        dest.writeString(this.FootRingID);
        dest.writeString(this.FootRingNum);
        dest.writeDouble(this.Fraction);
        dest.writeString(this.FromFlyTime);
        dest.writeString(this.EndFlyTime);
        dest.writeString(this.Score);
        dest.writeString(this.PigeonPlumeName);
        dest.writeString(this.PigeonBloodName);
        dest.writeInt(this.FlyCount);
        dest.writeString(this.PigeonID);
        dest.writeInt(this.order);
        dest.writeTypedList(this.mFlyBackRecordExpandEntity);
    }

    protected FlyBackRecordEntity(Parcel in) {
        this.PigeonTrainID = in.readString();
        this.PigeonTrainCountID = in.readString();
        this.PigeonTrainDataID = in.readString();
        this.FootRingID = in.readString();
        this.FootRingNum = in.readString();
        this.Fraction = in.readDouble();
        this.FromFlyTime = in.readString();
        this.EndFlyTime = in.readString();
        this.Score = in.readString();
        this.PigeonPlumeName = in.readString();
        this.PigeonBloodName = in.readString();
        this.FlyCount = in.readInt();
        this.PigeonID = in.readString();
        this.order = in.readInt();
        this.mFlyBackRecordExpandEntity = in.createTypedArrayList(FlyBackRecordEntity.CREATOR);
    }

    public static final Creator<FlyBackRecordEntity> CREATOR = new Creator<FlyBackRecordEntity>() {
        @Override
        public FlyBackRecordEntity createFromParcel(Parcel source) {
            return new FlyBackRecordEntity(source);
        }

        @Override
        public FlyBackRecordEntity[] newArray(int size) {
            return new FlyBackRecordEntity[size];
        }
    };
}
