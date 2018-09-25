package com.cpigeon.book.model.entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Administrator on 2018/9/25 0025.
 */

public class PigeonPhotoEntity implements Parcelable {


    /**
     * PhotoUrl : 图片地址
     * TypeID : 28
     * TypeName : 类型名称
     * FootRingID : 102
     * PigeonID : 12
     * UserID : 10
     * AddTime : 添加时间
     * Remark : 备注
     * PigeonPhotoID : 图片id
     * CoverPhotoUrl : 封面
     */

    private String PhotoUrl;
    private String TypeID;
    private String TypeName;
    private String FootRingID;
    private String PigeonID;
    private String UserID;
    private String AddTime;
    private String Remark;
    private String PigeonPhotoID;
    private String CoverPhotoUrl;
    private String CoverPhotoTypeName;


    /**
     * PhotoCount : 图片数量
     * TotalCount : 可存储总量
     * UseCount : 已使用量
     */

    private String PhotoCount;
    private String TotalCount;
    private String UseCount;

    private PigeonPhotoEntity(Builder builder) {
        setPhotoUrl(builder.PhotoUrl);
        setTypeID(builder.TypeID);
        setTypeName(builder.TypeName);
        setFootRingID(builder.FootRingID);
        setPigeonID(builder.PigeonID);
        setUserID(builder.UserID);
        setAddTime(builder.AddTime);
        setRemark(builder.Remark);
        setPigeonPhotoID(builder.PigeonPhotoID);
        setCoverPhotoUrl(builder.CoverPhotoUrl);
        setCoverPhotoTypeName(builder.CoverPhotoTypeName);
        setPhotoCount(builder.PhotoCount);
        setTotalCount(builder.TotalCount);
        setUseCount(builder.UseCount);
    }


    public String getPhotoUrl() {
        return PhotoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        PhotoUrl = photoUrl;
    }

    public String getTypeID() {
        return TypeID;
    }

    public void setTypeID(String typeID) {
        TypeID = typeID;
    }

    public String getTypeName() {
        return TypeName;
    }

    public void setTypeName(String typeName) {
        TypeName = typeName;
    }

    public String getFootRingID() {
        return FootRingID;
    }

    public void setFootRingID(String footRingID) {
        FootRingID = footRingID;
    }

    public String getPigeonID() {
        return PigeonID;
    }

    public void setPigeonID(String pigeonID) {
        PigeonID = pigeonID;
    }

    public String getUserID() {
        return UserID;
    }

    public void setUserID(String userID) {
        UserID = userID;
    }

    public String getAddTime() {
        return AddTime;
    }

    public void setAddTime(String addTime) {
        AddTime = addTime;
    }

    public String getRemark() {
        return Remark;
    }

    public void setRemark(String remark) {
        Remark = remark;
    }

    public String getPigeonPhotoID() {
        return PigeonPhotoID;
    }

    public void setPigeonPhotoID(String pigeonPhotoID) {
        PigeonPhotoID = pigeonPhotoID;
    }

    public String getCoverPhotoUrl() {
        return CoverPhotoUrl;
    }

    public void setCoverPhotoUrl(String coverPhotoUrl) {
        CoverPhotoUrl = coverPhotoUrl;
    }

    public String getCoverPhotoTypeName() {
        return CoverPhotoTypeName;
    }

    public void setCoverPhotoTypeName(String coverPhotoTypeName) {
        CoverPhotoTypeName = coverPhotoTypeName;
    }

    public String getPhotoCount() {
        return PhotoCount;
    }

    public void setPhotoCount(String photoCount) {
        PhotoCount = photoCount;
    }

    public String getTotalCount() {
        return TotalCount;
    }

    public void setTotalCount(String totalCount) {
        TotalCount = totalCount;
    }

    public String getUseCount() {
        return UseCount;
    }

    public void setUseCount(String useCount) {
        UseCount = useCount;
    }

    public static final class Builder {
        private String PhotoUrl;
        private String TypeID;
        private String TypeName;
        private String FootRingID;
        private String PigeonID;
        private String UserID;
        private String AddTime;
        private String Remark;
        private String PigeonPhotoID;
        private String CoverPhotoUrl;
        private String CoverPhotoTypeName;
        private String PhotoCount;
        private String TotalCount;
        private String UseCount;

        public Builder() {
        }

        public Builder PhotoUrl(String val) {
            PhotoUrl = val;
            return this;
        }

        public Builder TypeID(String val) {
            TypeID = val;
            return this;
        }

        public Builder TypeName(String val) {
            TypeName = val;
            return this;
        }

        public Builder FootRingID(String val) {
            FootRingID = val;
            return this;
        }

        public Builder PigeonID(String val) {
            PigeonID = val;
            return this;
        }

        public Builder UserID(String val) {
            UserID = val;
            return this;
        }

        public Builder AddTime(String val) {
            AddTime = val;
            return this;
        }

        public Builder Remark(String val) {
            Remark = val;
            return this;
        }

        public Builder PigeonPhotoID(String val) {
            PigeonPhotoID = val;
            return this;
        }

        public Builder CoverPhotoUrl(String val) {
            CoverPhotoUrl = val;
            return this;
        }

        public Builder CoverPhotoTypeName(String val) {
            CoverPhotoTypeName = val;
            return this;
        }

        public Builder PhotoCount(String val) {
            PhotoCount = val;
            return this;
        }

        public Builder TotalCount(String val) {
            TotalCount = val;
            return this;
        }

        public Builder UseCount(String val) {
            UseCount = val;
            return this;
        }

        public PigeonPhotoEntity build() {
            return new PigeonPhotoEntity(this);
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.PhotoUrl);
        dest.writeString(this.TypeID);
        dest.writeString(this.TypeName);
        dest.writeString(this.FootRingID);
        dest.writeString(this.PigeonID);
        dest.writeString(this.UserID);
        dest.writeString(this.AddTime);
        dest.writeString(this.Remark);
        dest.writeString(this.PigeonPhotoID);
        dest.writeString(this.CoverPhotoUrl);
        dest.writeString(this.CoverPhotoTypeName);
        dest.writeString(this.PhotoCount);
        dest.writeString(this.TotalCount);
        dest.writeString(this.UseCount);
    }

    protected PigeonPhotoEntity(Parcel in) {
        this.PhotoUrl = in.readString();
        this.TypeID = in.readString();
        this.TypeName = in.readString();
        this.FootRingID = in.readString();
        this.PigeonID = in.readString();
        this.UserID = in.readString();
        this.AddTime = in.readString();
        this.Remark = in.readString();
        this.PigeonPhotoID = in.readString();
        this.CoverPhotoUrl = in.readString();
        this.CoverPhotoTypeName = in.readString();
        this.PhotoCount = in.readString();
        this.TotalCount = in.readString();
        this.UseCount = in.readString();
    }

    public static final Creator<PigeonPhotoEntity> CREATOR = new Creator<PigeonPhotoEntity>() {
        @Override
        public PigeonPhotoEntity createFromParcel(Parcel source) {
            return new PigeonPhotoEntity(source);
        }

        @Override
        public PigeonPhotoEntity[] newArray(int size) {
            return new PigeonPhotoEntity[size];
        }
    };
}
