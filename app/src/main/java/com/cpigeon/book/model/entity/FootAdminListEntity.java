package com.cpigeon.book.model.entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Administrator on 2018/8/17.
 */

public class FootAdminListEntity implements Parcelable {


    /**
     * UseFootRingNum : 0
     * FootRingMoney : 100.0
     * StateID : 48
     * TypeName : 特比环
     * FootRingID : 66
     * Remark :
     * StateName : 未挂环
     * TypeID : 4
     * SourceID : 63
     * SourceName : 2
     * FootRingNum : 966643
     */

    private int UseFootRingNum;
    private double FootRingMoney;
    private int StateID;
    private String TypeName;
    private int FootRingID;
    private String Remark;
    private String StateName;
    private int TypeID;
    private int SourceID;
    private String SourceName;
    private String FootRingNum;

    public int getUseFootRingNum() {
        return UseFootRingNum;
    }

    public void setUseFootRingNum(int UseFootRingNum) {
        this.UseFootRingNum = UseFootRingNum;
    }

    public double getFootRingMoney() {
        return FootRingMoney;
    }

    public void setFootRingMoney(double FootRingMoney) {
        this.FootRingMoney = FootRingMoney;
    }

    public int getStateID() {
        return StateID;
    }

    public void setStateID(int StateID) {
        this.StateID = StateID;
    }

    public String getTypeName() {
        return TypeName;
    }

    public void setTypeName(String TypeName) {
        this.TypeName = TypeName;
    }

    public int getFootRingID() {
        return FootRingID;
    }

    public void setFootRingID(int FootRingID) {
        this.FootRingID = FootRingID;
    }

    public String getRemark() {
        return Remark;
    }

    public void setRemark(String Remark) {
        this.Remark = Remark;
    }

    public String getStateName() {
        return StateName;
    }

    public void setStateName(String StateName) {
        this.StateName = StateName;
    }

    public int getTypeID() {
        return TypeID;
    }

    public void setTypeID(int TypeID) {
        this.TypeID = TypeID;
    }

    public int getSourceID() {
        return SourceID;
    }

    public void setSourceID(int SourceID) {
        this.SourceID = SourceID;
    }

    public String getSourceName() {
        return SourceName;
    }

    public void setSourceName(String SourceName) {
        this.SourceName = SourceName;
    }

    public String getFootRingNum() {
        return FootRingNum;
    }

    public void setFootRingNum(String FootRingNum) {
        this.FootRingNum = FootRingNum;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.UseFootRingNum);
        dest.writeDouble(this.FootRingMoney);
        dest.writeInt(this.StateID);
        dest.writeString(this.TypeName);
        dest.writeInt(this.FootRingID);
        dest.writeString(this.Remark);
        dest.writeString(this.StateName);
        dest.writeInt(this.TypeID);
        dest.writeInt(this.SourceID);
        dest.writeString(this.SourceName);
        dest.writeString(this.FootRingNum);
    }

    public FootAdminListEntity() {
    }

    protected FootAdminListEntity(Parcel in) {
        this.UseFootRingNum = in.readInt();
        this.FootRingMoney = in.readDouble();
        this.StateID = in.readInt();
        this.TypeName = in.readString();
        this.FootRingID = in.readInt();
        this.Remark = in.readString();
        this.StateName = in.readString();
        this.TypeID = in.readInt();
        this.SourceID = in.readInt();
        this.SourceName = in.readString();
        this.FootRingNum = in.readString();
    }

    public static final Parcelable.Creator<FootAdminListEntity> CREATOR = new Parcelable.Creator<FootAdminListEntity>() {
        @Override
        public FootAdminListEntity createFromParcel(Parcel source) {
            return new FootAdminListEntity(source);
        }

        @Override
        public FootAdminListEntity[] newArray(int size) {
            return new FootAdminListEntity[size];
        }
    };
}
