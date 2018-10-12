package com.cpigeon.book.model.entity;

import android.os.Parcel;
import android.os.Parcelable;

import com.base.entity.LetterSortEntity;
import com.base.util.Lists;
import com.base.util.utility.StringUtil;
import com.chad.library.adapter.base.entity.MultiItemEntity;

import java.util.List;

/**
 * Created by Zhu TingYu on 2018/8/19.
 */

public class SelectTypeEntity extends LetterSortEntity implements MultiItemEntity, Parcelable {

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

    private SelectTypeEntity(Builder builder) {
        setTypeName(builder.TypeName);
        setTypeID(builder.TypeID);
        setWhichType(builder.WhichType);
        setSelect(builder.isSelect);
        setType(builder.type);
    }

    @Override
    public String getContent() {
        return TypeName;
    }

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
            String typeName = entity.getTypeName();
            names.add(StringUtil.isStringValid(typeName) ? typeName : StringUtil.emptyString());
        }
        return names;
    }


    public static String getTypeName(List<SelectTypeEntity> data){
        List<String> names = Lists.newArrayList();
        for (SelectTypeEntity entity : data) {
            String typeName = entity.getTypeName();
            names.add(StringUtil.isStringValid(typeName) ? typeName : StringUtil.emptyString());
        }
        return Lists.appendStringByList(names);
    }


    public static String getTypeIds(List<SelectTypeEntity> data){
        //全部
        if(data.size() == 1 && !StringUtil.isStringValid(data.get(0).getTypeName())){
            return StringUtil.emptyString();
        }else {
            List<String> ids = Lists.newArrayList();
            for (SelectTypeEntity entity : data) {
                ids.add(entity.getTypeID());
            }
            return Lists.appendStringByList(ids);
        }
    }

    public static SelectTypeEntity getCustomEntity(String whichType){
        SelectTypeEntity entity = new SelectTypeEntity();
        entity.setWhichType(whichType);
        return entity;
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

    public static final Creator<SelectTypeEntity> CREATOR = new Creator<SelectTypeEntity>() {
        @Override
        public SelectTypeEntity createFromParcel(Parcel source) {
            return new SelectTypeEntity(source);
        }

        @Override
        public SelectTypeEntity[] newArray(int size) {
            return new SelectTypeEntity[size];
        }
    };

    public static final class Builder {
        private String TypeName;
        private String TypeID;
        private String WhichType;
        private boolean isSelect;
        private int type;

        public Builder() {
        }

        public Builder TypeName(String val) {
            TypeName = val;
            return this;
        }

        public Builder TypeID(String val) {
            TypeID = val;
            return this;
        }

        public Builder WhichType(String val) {
            WhichType = val;
            return this;
        }

        public Builder isSelect(boolean val) {
            isSelect = val;
            return this;
        }

        public Builder type(int val) {
            type = val;
            return this;
        }

        public SelectTypeEntity build() {
            return new SelectTypeEntity(this);
        }
    }
}
