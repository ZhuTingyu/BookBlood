package com.cpigeon.book.model.entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Zhu TingYu on 2018/10/19.
 */

public class FootRingStateEntity implements Parcelable {

    /**
     * PigeonID : 足环所挂环的鸽子id
     * FootStateName : 足环状态名称
     * FootStateID : 状态id
     */

    private String PigeonID;
    private String FootStateName;
    private String FootStateID;
    private String FootId;

    public String getFootId() {
        return FootId;
    }

    public void setFootId(String footId) {
        FootId = footId;
    }

    public String getPigeonID() {
        return PigeonID;
    }

    public void setPigeonID(String PigeonID) {
        this.PigeonID = PigeonID;
    }

    public String getFootStateName() {
        return FootStateName;
    }

    public void setFootStateName(String FootStateName) {
        this.FootStateName = FootStateName;
    }

    public String getFootStateID() {
        return FootStateID;
    }

    public void setFootStateID(String FootStateID) {
        this.FootStateID = FootStateID;
    }

    public FootRingStateEntity() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.PigeonID);
        dest.writeString(this.FootStateName);
        dest.writeString(this.FootStateID);
        dest.writeString(this.FootId);
    }

    protected FootRingStateEntity(Parcel in) {
        this.PigeonID = in.readString();
        this.FootStateName = in.readString();
        this.FootStateID = in.readString();
        this.FootId = in.readString();
    }

    public static final Creator<FootRingStateEntity> CREATOR = new Creator<FootRingStateEntity>() {
        @Override
        public FootRingStateEntity createFromParcel(Parcel source) {
            return new FootRingStateEntity(source);
        }

        @Override
        public FootRingStateEntity[] newArray(int size) {
            return new FootRingStateEntity[size];
        }
    };
}
