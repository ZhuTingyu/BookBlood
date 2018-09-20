package com.cpigeon.book.model.entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Zhu TingYu on 2018/9/11.
 */

public class  TrainEntity implements Parcelable {

    /**
     * AddTime : 添加时间
     * PigeonTrainName : 训练名称
     * TrainStateID : 训练状态ID
     * TrainCount : 训练次数
     * TrainStateName : 训练状态名称
     * FlyCount  : 放飞羽数
     */

    private String Time;
    private String PigeonTrainName;
    private String TrainStateID;
    private int TrainCount;
    private String TrainStateName;
    public String PigeonTrainID;//":"训练表id""+

    private int FlyCount;
    public double EndLatitude;//结束北纬
    public double EndLongitude;//:"结束东经",
    public double FromLatitude;//:"开始北纬",
    public double FromLongitude;//"开始东经",
    public String FromPlace;//开始地点", "
    public String PigeonTrainCountID;//":"训练次数id""+
    public double Distance;//":"训练次数id""+

    public double getDistance() {
        return Distance;
    }

    public void setDistance(double distance) {
        Distance = distance;
    }

    public double getEndLatitude() {
        return EndLatitude;
    }

    public void setEndLatitude(double endLatitude) {
        EndLatitude = endLatitude;
    }

    public double getEndLongitude() {
        return EndLongitude;
    }

    public void setEndLongitude(double endLongitude) {
        EndLongitude = endLongitude;
    }

    public double getFromLatitude() {
        return FromLatitude;
    }

    public void setFromLatitude(double fromLatitude) {
        FromLatitude = fromLatitude;
    }

    public double getFromLongitude() {
        return FromLongitude;
    }

    public void setFromLongitude(double fromLongitude) {
        FromLongitude = fromLongitude;
    }

    public String getFromPlace() {
        return FromPlace;
    }

    public void setFromPlace(String fromPlace) {
        FromPlace = fromPlace;
    }

    public String getPigeonTrainCountID() {
        return PigeonTrainCountID;
    }

    public void setPigeonTrainCountID(String pigeonTrainCountID) {
        PigeonTrainCountID = pigeonTrainCountID;
    }

    public String getPigeonTrainID() {
        return PigeonTrainID;
    }

    public void setPigeonTrainID(String pigeonTrainID) {
        PigeonTrainID = pigeonTrainID;
    }

    public String getTime() {
        return Time;
    }

    public void setTime(String AddTime) {
        this.Time = AddTime;
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

    public int getTrainCount() {
        return TrainCount;
    }

    public void setTrainCount(int TrainCount) {
        this.TrainCount = TrainCount;
    }

    public String getTrainStateName() {
        return TrainStateName;
    }

    public void setTrainStateName(String TrainStateName) {
        this.TrainStateName = TrainStateName;
    }

    public int getFlyCount() {
        return FlyCount;
    }

    public void setFlyCount(int FlyCount) {
        this.FlyCount = FlyCount;
    }

    public TrainEntity() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.Time);
        dest.writeString(this.PigeonTrainName);
        dest.writeString(this.TrainStateID);
        dest.writeInt(this.TrainCount);
        dest.writeString(this.TrainStateName);
        dest.writeString(this.PigeonTrainID);
        dest.writeInt(this.FlyCount);
        dest.writeDouble(this.EndLatitude);
        dest.writeDouble(this.EndLongitude);
        dest.writeDouble(this.FromLatitude);
        dest.writeDouble(this.FromLongitude);
        dest.writeString(this.FromPlace);
        dest.writeString(this.PigeonTrainCountID);
        dest.writeDouble(this.Distance);
    }

    protected TrainEntity(Parcel in) {
        this.Time = in.readString();
        this.PigeonTrainName = in.readString();
        this.TrainStateID = in.readString();
        this.TrainCount = in.readInt();
        this.TrainStateName = in.readString();
        this.PigeonTrainID = in.readString();
        this.FlyCount = in.readInt();
        this.EndLatitude = in.readDouble();
        this.EndLongitude = in.readDouble();
        this.FromLatitude = in.readDouble();
        this.FromLongitude = in.readDouble();
        this.FromPlace = in.readString();
        this.PigeonTrainCountID = in.readString();
        this.Distance = in.readDouble();
    }

    public static final Creator<TrainEntity> CREATOR = new Creator<TrainEntity>() {
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
