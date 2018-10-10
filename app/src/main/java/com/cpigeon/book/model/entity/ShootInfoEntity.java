package com.cpigeon.book.model.entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Administrator on 2018/10/10 0010.
 */

public class ShootInfoEntity implements Parcelable {


    /**
     * gsname : 鸽舍名称
     * imgurl : 图片地址
     */

    private String gsname = "";
    private String imgurl = "";

    public String getGsname() {
        return gsname;
    }

    public void setGsname(String gsname) {
        this.gsname = gsname;
    }

    public String getImgurl() {
        return imgurl;
    }

    public void setImgurl(String imgurl) {
        this.imgurl = imgurl;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.gsname);
        dest.writeString(this.imgurl);
    }

    public ShootInfoEntity() {
    }

    protected ShootInfoEntity(Parcel in) {
        this.gsname = in.readString();
        this.imgurl = in.readString();
    }

    public static final Parcelable.Creator<ShootInfoEntity> CREATOR = new Parcelable.Creator<ShootInfoEntity>() {
        @Override
        public ShootInfoEntity createFromParcel(Parcel source) {
            return new ShootInfoEntity(source);
        }

        @Override
        public ShootInfoEntity[] newArray(int size) {
            return new ShootInfoEntity[size];
        }
    };
}
