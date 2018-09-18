package com.cpigeon.book.model.entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Administrator on 2018/9/17 0017.
 */

public class FeedPigeonEntity implements Parcelable {


    /**
     * ViewID : 5
     * PigeonID : 286
     * FootRingID : 1202849
     * TypeID : 1   1：保健品，2疫苗，3用药，4病情，5图片
     * Name : 保安康
     * UseTime : 2018-08-18 00:00:00
     * BitEffect : 黄眼
     * State : 有效果
     * ListInfo : 提神醒脑
     */

    private String ViewID;
    private String PigeonID;
    private String FootRingID;
    private int TypeID;
    private String Name;
    private String UseTime;
    private String BitEffect;
    private String State;
    private String ListInfo;

    public String getViewID() {
        return ViewID;
    }

    public void setViewID(String viewID) {
        ViewID = viewID;
    }

    public String getPigeonID() {
        return PigeonID;
    }

    public void setPigeonID(String pigeonID) {
        PigeonID = pigeonID;
    }

    public String getFootRingID() {
        return FootRingID;
    }

    public void setFootRingID(String footRingID) {
        FootRingID = footRingID;
    }

    public int getTypeID() {
        return TypeID;
    }

    public void setTypeID(int typeID) {
        TypeID = typeID;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getUseTime() {
        return UseTime;
    }

    public void setUseTime(String useTime) {
        UseTime = useTime;
    }

    public String getBitEffect() {
        return BitEffect;
    }

    public void setBitEffect(String bitEffect) {
        BitEffect = bitEffect;
    }

    public String getState() {
        return State;
    }

    public void setState(String state) {
        State = state;
    }

    public String getListInfo() {
        return ListInfo;
    }

    public void setListInfo(String listInfo) {
        ListInfo = listInfo;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.ViewID);
        dest.writeString(this.PigeonID);
        dest.writeString(this.FootRingID);
        dest.writeInt(this.TypeID);
        dest.writeString(this.Name);
        dest.writeString(this.UseTime);
        dest.writeString(this.BitEffect);
        dest.writeString(this.State);
        dest.writeString(this.ListInfo);
    }

    public FeedPigeonEntity() {
    }

    protected FeedPigeonEntity(Parcel in) {
        this.ViewID = in.readString();
        this.PigeonID = in.readString();
        this.FootRingID = in.readString();
        this.TypeID = in.readInt();
        this.Name = in.readString();
        this.UseTime = in.readString();
        this.BitEffect = in.readString();
        this.State = in.readString();
        this.ListInfo = in.readString();
    }

    public static final Parcelable.Creator<FeedPigeonEntity> CREATOR = new Parcelable.Creator<FeedPigeonEntity>() {
        @Override
        public FeedPigeonEntity createFromParcel(Parcel source) {
            return new FeedPigeonEntity(source);
        }

        @Override
        public FeedPigeonEntity[] newArray(int size) {
            return new FeedPigeonEntity[size];
        }
    };
}
