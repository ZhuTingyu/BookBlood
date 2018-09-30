package com.cpigeon.book.model.entity;

import android.os.Parcel;
import android.os.Parcelable;


/**
 * Created by Zhu TingYu on 2018/3/21.
 */

public class UserEntity implements Parcelable {

    /**
     * yonghuming : pigeonof
     * token : 5e886e7a4800998ba6701af55bdbca10
     * nicheng : 中鸽网
     * basicinfo : 1
     * handphone : 13730873310
     * touxiangurl :
     * userid : 0
     */

    public String yonghuming;
    public String token;
    public String nicheng;
    public String basicinfo;
    public String handphone;
    public String touxiangurl;
    public String userid;
    public String password;
    public String province;

    public PigeonHouseEntity pigeonHouseEntity;

    public UserEntity() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.yonghuming);
        dest.writeString(this.token);
        dest.writeString(this.nicheng);
        dest.writeString(this.basicinfo);
        dest.writeString(this.handphone);
        dest.writeString(this.touxiangurl);
        dest.writeString(this.userid);
        dest.writeString(this.password);
        dest.writeString(this.province);
        dest.writeParcelable(this.pigeonHouseEntity, flags);
    }

    protected UserEntity(Parcel in) {
        this.yonghuming = in.readString();
        this.token = in.readString();
        this.nicheng = in.readString();
        this.basicinfo = in.readString();
        this.handphone = in.readString();
        this.touxiangurl = in.readString();
        this.userid = in.readString();
        this.password = in.readString();
        this.province = in.readString();
        this.pigeonHouseEntity = in.readParcelable(PigeonHouseEntity.class.getClassLoader());
    }

    public static final Creator<UserEntity> CREATOR = new Creator<UserEntity>() {
        @Override
        public UserEntity createFromParcel(Parcel source) {
            return new UserEntity(source);
        }

        @Override
        public UserEntity[] newArray(int size) {
            return new UserEntity[size];
        }
    };
}
