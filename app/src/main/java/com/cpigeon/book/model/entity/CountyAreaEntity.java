package com.cpigeon.book.model.entity;

import android.os.Parcel;
import android.os.Parcelable;

import com.base.entity.LetterSortEntity;

/**
 * Created by Zhu TingYu on 2018/8/20.
 */

public class CountyAreaEntity extends LetterSortEntity implements Parcelable {

    /**
     * FootCodeName : 卢森堡信鸽协会(足环国家协会名称)
     * Code : Luxemburg(足环协会code)
     * FootCodeID : 22（足环国家协会ID）
     */

    private String FootCodeName;
    private String Code;
    private String FootCodeID;

    private String Country;
    private String Sort;

    @Override
    public String getContent() {
        return Country;
    }

    public String getCountry() {
        return Country;
    }

    public void setCountry(String country) {
        Country = country;
    }

    public String getSort() {
        return Sort;
    }

    public void setSort(String sort) {
        Sort = sort;
    }

    public String getFootCodeName() {
        return FootCodeName;
    }

    public void setFootCodeName(String FootCodeName) {
        this.FootCodeName = FootCodeName;
    }

    public String getCode() {
        return Code;
    }

    public void setCode(String Code) {
        this.Code = Code;
    }

    public String getFootCodeID() {
        return FootCodeID;
    }

    public void setFootCodeID(String FootCodeID) {
        this.FootCodeID = FootCodeID;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.FootCodeName);
        dest.writeString(this.Code);
        dest.writeString(this.FootCodeID);
        dest.writeString(this.Country);
        dest.writeString(this.Sort);
    }

    public CountyAreaEntity() {
    }

    protected CountyAreaEntity(Parcel in) {
        this.FootCodeName = in.readString();
        this.Code = in.readString();
        this.FootCodeID = in.readString();
        this.Country = in.readString();
        this.Sort = in.readString();
    }

    public static final Parcelable.Creator<CountyAreaEntity> CREATOR = new Parcelable.Creator<CountyAreaEntity>() {
        @Override
        public CountyAreaEntity createFromParcel(Parcel source) {
            return new CountyAreaEntity(source);
        }

        @Override
        public CountyAreaEntity[] newArray(int size) {
            return new CountyAreaEntity[size];
        }
    };
}
