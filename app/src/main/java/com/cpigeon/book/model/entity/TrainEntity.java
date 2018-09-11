package com.cpigeon.book.model.entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Zhu TingYu on 2018/9/11.
 */

public class TrainEntity implements Parcelable {

    /**
     * AddTime : 添加时间
     * PigeonTrainName : 训练名称
     * TrainStateID : 训练状态ID
     * TrainCount : 训练次数
     * TrainStateName : 训练状态名称
     * FlyCount  : 放飞羽数
     */

    private String AddTime;
    private String PigeonTrainName;
    private String TrainStateID;
    private String TrainCount;
    private String TrainStateName;
    private String FlyCount;

    public String getAddTime() {
        return AddTime;
    }

    public void setAddTime(String AddTime) {
        this.AddTime = AddTime;
    }

    public String getPigeonTrainName() {
        return PigeonTrainName;
    }

    public void setPigeonTrainName(String PigeonTrainName) {
        this.PigeonTrainName = PigeonTrainName;
    }

    public String getTrainStateID() {
        return TrainStateID;
    }

    public void setTrainStateID(String TrainStateID) {
        this.TrainStateID = TrainStateID;
    }

    public String getTrainCount() {
        return TrainCount;
    }

    public void setTrainCount(String TrainCount) {
        this.TrainCount = TrainCount;
    }

    public String getTrainStateName() {
        return TrainStateName;
    }

    public void setTrainStateName(String TrainStateName) {
        this.TrainStateName = TrainStateName;
    }

    public String getFlyCount() {
        return FlyCount;
    }

    public void setFlyCount(String FlyCount) {
        this.FlyCount = FlyCount;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.AddTime);
        dest.writeString(this.PigeonTrainName);
        dest.writeString(this.TrainStateID);
        dest.writeString(this.TrainCount);
        dest.writeString(this.TrainStateName);
        dest.writeString(this.FlyCount);
    }

    public TrainEntity() {
    }

    protected TrainEntity(Parcel in) {
        this.AddTime = in.readString();
        this.PigeonTrainName = in.readString();
        this.TrainStateID = in.readString();
        this.TrainCount = in.readString();
        this.TrainStateName = in.readString();
        this.FlyCount = in.readString();
    }

    public static final Parcelable.Creator<TrainEntity> CREATOR = new Parcelable.Creator<TrainEntity>() {
        @Override
        public TrainEntity createFromParcel(Parcel source) {
            return new TrainEntity(source);
        }

        @Override
        public TrainEntity[] newArray(int size) {
            return new TrainEntity[size];
        }
    };
}
