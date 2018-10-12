package com.cpigeon.book.model.entity;

import android.os.Parcel;
import android.os.Parcelable;

import com.base.entity.LetterSortEntity;

/**
 * Created by Zhu TingYu on 2018/8/20.
 */

public class CountyEntity extends LetterSortEntity implements Parcelable {

    /**
     * Country : 挪威（国家）
     * Sort : 25（国家所属的排序字段和国家id）
     * Code :
     */

    private String Country;
    private String CountryIDSort;
    private String Code;
    private String FootCodeID;

    @Override
    public String getContent() {
        return Country;
    }

    public String getCountry() {
        return Country;
    }

    public String getFootCodeID() {
        return FootCodeID;
    }

    public void setFootCodeID(String footCodeID) {
        FootCodeID = footCodeID;
    }

    public void setCountry(String Country) {
        this.Country = Country;
    }


    public String getSort() {
        return CountryIDSort;
    }

    public void setSort(String Sort) {
        this.CountryIDSort = Sort;
    }

    public String getCode() {
        return Code;
    }

    public void setCode(String Code) {
        this.Code = Code;
    }

    public CountyEntity() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.Country);
        dest.writeString(this.CountryIDSort);
        dest.writeString(this.Code);
        dest.writeString(this.FootCodeID);
    }

    protected CountyEntity(Parcel in) {
        this.Country = in.readString();
        this.CountryIDSort = in.readString();
        this.Code = in.readString();
        this.FootCodeID = in.readString();
    }

    public static final Creator<CountyEntity> CREATOR = new Creator<CountyEntity>() {
        @Override
        public CountyEntity createFromParcel(Parcel source) {
            return new CountyEntity(source);
        }

        @Override
        public CountyEntity[] newArray(int size) {
            return new CountyEntity[size];
        }
    };
}
