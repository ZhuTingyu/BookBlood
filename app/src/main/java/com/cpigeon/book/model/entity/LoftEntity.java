package com.cpigeon.book.model.entity;

import android.os.Parcel;
import android.os.Parcelable;

import com.base.entity.LetterSortEntity;

/**
 * Created by Administrator on 2018/9/28 0028.
 */

public class LoftEntity extends LetterSortEntity implements Parcelable {


    /**
     * gpid : 索引ID
     * gpuid : 公棚用户ID
     * gpname : 公棚名称
     */

    private String gpid;
    private String gpuid;
    private String gpname;

    public String getGpid() {
        return gpid;
    }

    public void setGpid(String gpid) {
        this.gpid = gpid;
    }

    public String getGpuid() {
        return gpuid;
    }

    public void setGpuid(String gpuid) {
        this.gpuid = gpuid;
    }

    public String getGpname() {
        return gpname;
    }

    public void setGpname(String gpname) {
        this.gpname = gpname;
    }


    @Override
    public String getContent() {
        return gpname;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.gpid);
        dest.writeString(this.gpuid);
        dest.writeString(this.gpname);
    }

    public LoftEntity() {
    }

    protected LoftEntity(Parcel in) {
        this.gpid = in.readString();
        this.gpuid = in.readString();
        this.gpname = in.readString();
    }

    public static final Parcelable.Creator<LoftEntity> CREATOR = new Parcelable.Creator<LoftEntity>() {
        @Override
        public LoftEntity createFromParcel(Parcel source) {
            return new LoftEntity(source);
        }

        @Override
        public LoftEntity[] newArray(int size) {
            return new LoftEntity[size];
        }
    };
}
