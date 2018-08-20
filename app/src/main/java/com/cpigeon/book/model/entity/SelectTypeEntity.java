package com.cpigeon.book.model.entity;

import android.os.Parcel;
import android.os.Parcelable;

import com.base.util.Lists;
import com.chad.library.adapter.base.entity.MultiItemEntity;

import java.util.List;

/**
 * Created by Zhu TingYu on 2018/8/19.
 */

public class SelectTypeEntity implements MultiItemEntity, Parcelable {

    public static final int TYPE_NORMAL = 0;
    public static final int TYPE_CUSTOM = 1;

    /**
     * TypeName : 特比环
     * TypeID : 4
     */

    private String TypeName;
    private String TypeID;
    private String WhichType;

    private boolean isSelect;

    private int type;

    public boolean isSelect() {
        return isSelect;
    }

    public void setSelect(boolean select) {
        isSelect = select;
    }

    public String getWhichType() {
        return WhichType;
    }

    public void setWhichType(String whichType) {
        WhichType = whichType;
    }

    public String getTypeName() {
        return TypeName;
    }

    public void setTypeName(String TypeName) {
        this.TypeName = TypeName;
    }

    public String getTypeID() {
        return TypeID;
    }

    public void setTypeID(String TypeID) {
        this.TypeID = TypeID;
    }

    public static List<String> getTypeNames(List<SelectTypeEntity> data){
        List<String> names = Lists.newArrayList();
        for (SelectTypeEntity entity : data) {
            names.add(entity.getTypeName());
        }
        return names;
    }

    @Override
    public int getItemType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.TypeName);
        dest.writeString(this.TypeID);
        dest.writeString(this.WhichType);
        dest.writeByte(this.isSelect ? (byte) 1 : (byte) 0);
        dest.writeInt(this.type);
    }

    public SelectTypeEntity() {
    }

    protected SelectTypeEntity(Parcel in) {
        this.TypeName = in.readString();
        this.TypeID = in.readString();
        this.WhichType = in.readString();
        this.isSelect = in.readByte() != 0;
        this.type = in.readInt();
    }

    public static final Parcelable.Creator<SelectTypeEntity> CREATOR = new Parcelable.Creator<SelectTypeEntity>() {
        @Override
        public SelectTypeEntity createFromParcel(Parcel source) {
            return new SelectTypeEntity(source);
        }

        @Override
        public SelectTypeEntity[] newArray(int size) {
            return new SelectTypeEntity[size];
        }
    };
}
