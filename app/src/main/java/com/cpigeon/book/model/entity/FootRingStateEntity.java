package com.cpigeon.book.model.entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Zhu TingYu on 2018/10/19.
 */

public class FootRingStateEntity implements Parcelable {


    /**
     * PigeonID : 0
     * FootStateID : 0
     * FootStateName :
     * FootRingID : 0
     */

    private int PigeonID;
    private int FootStateID;
    private String FootStateName;
    private int FootRingID;

    public int getPigeonID() {
        return PigeonID;
    }

    public void setPigeonID(int PigeonID) {
        this.PigeonID = PigeonID;
    }

    public int getFootStateID() {
        return FootStateID;
    }

    public void setFootStateID(int FootStateID) {
        this.FootStateID = FootStateID;
    }

    public String getFootStateName() {
        return FootStateName;
    }

    public void setFootStateName(String FootStateName) {
        this.FootStateName = FootStateName;
    }

    public int getFootRingID() {
        return FootRingID;
    }

    public void setFootRingID(int FootRingID) {
        this.FootRingID = FootRingID;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.PigeonID);
        dest.writeInt(this.FootStateID);
        dest.writeString(this.FootStateName);
        dest.writeInt(this.FootRingID);
    }

    public FootRingStateEntity() {
    }

    protected FootRingStateEntity(Parcel in) {
        this.PigeonID = in.readInt();
        this.FootStateID = in.readInt();
        this.FootStateName = in.readString();
        this.FootRingID = in.readInt();
    }

    public static final Parcelable.Creator<FootRingStateEntity> CREATOR = new Parcelable.Creator<FootRingStateEntity>() {
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
