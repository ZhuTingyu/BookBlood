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

    private String sszz = "";
    private String imgurl = "";


    protected ShootInfoEntity(Parcel in) {
        sszz = in.readString();
        imgurl = in.readString();
    }

    private ShootInfoEntity(Builder builder) {
        setSszz(builder.sszz);
        setImgurl(builder.imgurl);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(sszz);
        dest.writeString(imgurl);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<ShootInfoEntity> CREATOR = new Creator<ShootInfoEntity>() {
        @Override
        public ShootInfoEntity createFromParcel(Parcel in) {
            return new ShootInfoEntity(in);
        }

        @Override
        public ShootInfoEntity[] newArray(int size) {
            return new ShootInfoEntity[size];
        }
    };

    public String getSszz() {
        return sszz;
    }

    public void setSszz(String sszz) {
        this.sszz = sszz;
    }

    public String getImgurl() {
        return imgurl;
    }

    public void setImgurl(String imgurl) {
        this.imgurl = imgurl;
    }


    public static final class Builder {
        private String sszz;
        private String imgurl;

        public Builder() {
        }

        public Builder sszz(String val) {
            sszz = val;
            return this;
        }

        public Builder imgurl(String val) {
            imgurl = val;
            return this;
        }

        public ShootInfoEntity build() {
            return new ShootInfoEntity(this);
        }
    }
}
