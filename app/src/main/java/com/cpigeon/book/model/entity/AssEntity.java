package com.cpigeon.book.model.entity;

import android.os.Parcel;
import android.os.Parcelable;

import com.base.entity.LetterSortEntity;

/**
 * Created by Zhu TingYu on 2018/8/3.
 */

public class AssEntity extends LetterSortEntity implements Parcelable {

    String PigeonISOCID;
    String ISOCName;

    @Override
    public String getContent() {
        return ISOCName;
    }

    public String getPigeonISOCID() {
        return PigeonISOCID;
    }

    public void setPigeonISOCID(String pigeonISOCID) {
        PigeonISOCID = pigeonISOCID;
    }

    public String getISOCName() {
        return ISOCName;
    }

    public void setISOCName(String ISOCName) {
        this.ISOCName = ISOCName;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.PigeonISOCID);
        dest.writeString(this.ISOCName);
    }

    public AssEntity() {
    }

    protected AssEntity(Parcel in) {
        this.PigeonISOCID = in.readString();
        this.ISOCName = in.readString();
    }

    public static final Parcelable.Creator<AssEntity> CREATOR = new Parcelable.Creator<AssEntity>() {
        @Override
        public AssEntity createFromParcel(Parcel source) {
            return new AssEntity(source);
        }

        @Override
        public AssEntity[] newArray(int size) {
            return new AssEntity[size];
        }
    };
}
