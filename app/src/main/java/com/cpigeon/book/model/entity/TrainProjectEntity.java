package com.cpigeon.book.model.entity;

import android.os.Parcel;

import com.base.entity.MultiSelectEntity;

/**
 * Created by Zhu TingYu on 2018/9/6.
 */

public class TrainProjectEntity extends MultiSelectEntity {
    String id;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeString(this.id);
    }

    public TrainProjectEntity() {
    }

    protected TrainProjectEntity(Parcel in) {
        super(in);
        this.id = in.readString();
    }

    public static final Creator<TrainProjectEntity> CREATOR = new Creator<TrainProjectEntity>() {
        @Override
        public TrainProjectEntity createFromParcel(Parcel source) {
            return new TrainProjectEntity(source);
        }

        @Override
        public TrainProjectEntity[] newArray(int size) {
            return new TrainProjectEntity[size];
        }
    };
}
