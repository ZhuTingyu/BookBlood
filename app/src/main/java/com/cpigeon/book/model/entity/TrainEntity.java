package com.cpigeon.book.model.entity;

import android.os.Parcel;
import android.os.Parcelable;

import com.base.entity.MultiSelectEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Zhu TingYu on 2018/9/11.
 */

public class  TrainEntity extends MultiSelectEntity implements Parcelable {

    /**
     * AddTime : 添加时间
     * PigeonTrainName : 训练名称
     * TrainStateID : 训练状态ID
     * TrainCount : 训练次数
     * TrainStateName : 训练状态名称
     * FlyCount  : 放飞羽数
     */

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
    public String FromFlyTime;//放飞时间
    public int ReturnCount;//归巢羽数
    public List<PigeonEntity> FootRingList;//归巢羽数

    public List<PigeonEntity> getFootRingList() {
        return FootRingList;
    }

    public void setFootRingList(List<PigeonEntity> footRingList) {
        FootRingList = footRingList;
    }

    public int getReturnCount() {
        return ReturnCount;
    }

    public void setReturnCount(int returnCount) {
        ReturnCount = returnCount;
    }

    public void setFromFlyTime(String fromFlyTime) {
        FromFlyTime = fromFlyTime;
    }

    public String getFromFlyTime() {
        return FromFlyTime;
    }

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
        super.writeToParcel(dest, flags);
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
        dest.writeString(this.FromFlyTime);
        dest.writeInt(this.ReturnCount);
        dest.writeList(this.FootRingList);
    }

    protected TrainEntity(Parcel in) {
        super(in);
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
        this.FromFlyTime = in.readString();
        this.ReturnCount = in.readInt();
        this.FootRingList = new ArrayList<PigeonEntity>();
        in.readList(this.FootRingList, PigeonEntity.class.getClassLoader());
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
