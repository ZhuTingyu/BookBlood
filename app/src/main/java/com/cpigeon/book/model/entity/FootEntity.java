package com.cpigeon.book.model.entity;

import android.os.Parcel;
import android.os.Parcelable;

import com.base.util.Utils;
import com.cpigeon.book.R;

/**
 * Created by Administrator on 2018/8/17.
 */

public class FootEntity implements Parcelable {


    /**
     * FootRingID : 48
     * FootCodeName :
     * FootCodeID : 0
     * FootRingMoney : 100
     * SourceID : 60
     * TypeID : 4
     * StateID : 48
     * SourceName : 2
     * stateName : 未挂环
     * TypeName : 特比环
     * FootRingNum : 2018-11-112233
     * Remark :
     * UseFootRingNum : 0
     * Section : 1
     */

    private int FootRingID;
    private String FootCodeName;
    private int FootCodeID;
    private String FootRingMoney;
    private int SourceID;
    private int TypeID;
    private int StateID;
    private String SourceName;
    private String StateName;
    private String TypeName;
    private String FootRingNum;
    private String Remark;
    private int UseFootRingNum;
    private int Section;
    private String PigeonTypeName;

    private String EndFootRingNum;
    private String EndFootRingID;
    private String PigeonID;
    private String MenFootRingNum;
    private String WoFootRingNum;
    private String PigeonSexName;

    private String PigeonEyeName;
    private String PigeonPlumeName;

    public boolean isSetRing() {
        return Utils.getString(R.string.text_status_set_foot_ring).equals(getStateName());
    }

    public String getPigeonEyeName() {
        return PigeonEyeName;
    }

    public void setPigeonEyeName(String pigeonEyeName) {
        PigeonEyeName = pigeonEyeName;
    }

    public String getPigeonPlumeName() {
        return PigeonPlumeName;
    }

    public void setPigeonPlumeName(String pigeonPlumeName) {
        PigeonPlumeName = pigeonPlumeName;
    }

    public String getPigeonSexName() {
        return PigeonSexName;
    }

    public void setPigeonSexName(String pigeonSexName) {
        PigeonSexName = pigeonSexName;
    }

    public String getMenFootRingNum() {
        return MenFootRingNum;
    }

    public void setMenFootRingNum(String menFootRingNum) {
        MenFootRingNum = menFootRingNum;
    }

    public String getWoFootRingNum() {
        return WoFootRingNum;
    }

    public void setWoFootRingNum(String woFootRingNum) {
        WoFootRingNum = woFootRingNum;
    }

    public String getPigeonID() {
        return PigeonID;
    }

    public void setPigeonID(String pigeonID) {
        PigeonID = pigeonID;
    }

    public String getPigeonTypeName() {
        return PigeonTypeName;
    }

    public void setPigeonTypeName(String pigeonTypeName) {
        PigeonTypeName = pigeonTypeName;
    }

    public int getFootRingID() {
        return FootRingID;
    }

    public String getEndFootRingID() {
        return EndFootRingID;
    }

    public void setEndFootRingID(String endFootRingID) {
        EndFootRingID = endFootRingID;
    }

    public void setFootRingID(int FootRingID) {
        this.FootRingID = FootRingID;
    }

    public String getFootCodeName() {
        return FootCodeName;
    }

    public void setFootCodeName(String FootCodeName) {
        this.FootCodeName = FootCodeName;
    }

    public int getFootCodeID() {
        return FootCodeID;
    }

    public void setFootCodeID(int FootCodeID) {
        this.FootCodeID = FootCodeID;
    }

    public String getFootRingMoney() {
        return FootRingMoney;
    }

    public void setFootRingMoney(String FootRingMoney) {
        this.FootRingMoney = FootRingMoney;
    }

    public int getSourceID() {
        return SourceID;
    }

    public void setSourceID(int SourceID) {
        this.SourceID = SourceID;
    }

    public int getTypeID() {
        return TypeID;
    }

    public void setTypeID(int TypeID) {
        this.TypeID = TypeID;
    }

    public int getStateID() {
        return StateID;
    }

    public void setStateID(int StateID) {
        this.StateID = StateID;
    }

    public String getSourceName() {
        return SourceName;
    }

    public void setSourceName(String SourceName) {
        this.SourceName = SourceName;
    }

    public String getStateName() {
        return StateName;
    }

    public void setStateName(String stateName) {
        this.StateName = stateName;
    }

    public String getTypeName() {
        return TypeName;
    }

    public void setTypeName(String TypeName) {
        this.TypeName = TypeName;
    }

    public String getFootRingNum() {
        return FootRingNum;
    }

    public void setFootRingNum(String FootRingNum) {
        this.FootRingNum = FootRingNum;
    }

    public String getRemark() {
        return Remark;
    }

    public void setRemark(String Remark) {
        this.Remark = Remark;
    }

    public int getUseFootRingNum() {
        return UseFootRingNum;
    }

    public void setUseFootRingNum(int UseFootRingNum) {
        this.UseFootRingNum = UseFootRingNum;
    }

    public int getSection() {
        return Section;
    }

    public void setSection(int Section) {
        this.Section = Section;
    }

    public FootEntity() {
    }

    public String getEndFootRingNum() {
        return EndFootRingNum;
    }

    public void setEndFootRingNum(String EndFootRingNum) {
        this.EndFootRingNum = EndFootRingNum;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.FootRingID);
        dest.writeString(this.FootCodeName);
        dest.writeInt(this.FootCodeID);
        dest.writeString(this.FootRingMoney);
        dest.writeInt(this.SourceID);
        dest.writeInt(this.TypeID);
        dest.writeInt(this.StateID);
        dest.writeString(this.SourceName);
        dest.writeString(this.StateName);
        dest.writeString(this.TypeName);
        dest.writeString(this.FootRingNum);
        dest.writeString(this.Remark);
        dest.writeInt(this.UseFootRingNum);
        dest.writeInt(this.Section);
        dest.writeString(this.PigeonTypeName);
        dest.writeString(this.EndFootRingNum);
        dest.writeString(this.EndFootRingID);
        dest.writeString(this.PigeonID);
        dest.writeString(this.MenFootRingNum);
        dest.writeString(this.WoFootRingNum);
        dest.writeString(this.PigeonSexName);
        dest.writeString(this.PigeonEyeName);
        dest.writeString(this.PigeonPlumeName);
    }

    protected FootEntity(Parcel in) {
        this.FootRingID = in.readInt();
        this.FootCodeName = in.readString();
        this.FootCodeID = in.readInt();
        this.FootRingMoney = in.readString();
        this.SourceID = in.readInt();
        this.TypeID = in.readInt();
        this.StateID = in.readInt();
        this.SourceName = in.readString();
        this.StateName = in.readString();
        this.TypeName = in.readString();
        this.FootRingNum = in.readString();
        this.Remark = in.readString();
        this.UseFootRingNum = in.readInt();
        this.Section = in.readInt();
        this.PigeonTypeName = in.readString();
        this.EndFootRingNum = in.readString();
        this.EndFootRingID = in.readString();
        this.PigeonID = in.readString();
        this.MenFootRingNum = in.readString();
        this.WoFootRingNum = in.readString();
        this.PigeonSexName = in.readString();
        this.PigeonEyeName = in.readString();
        this.PigeonPlumeName = in.readString();
    }

    public static final Creator<FootEntity> CREATOR = new Creator<FootEntity>() {
        @Override
        public FootEntity createFromParcel(Parcel source) {
            return new FootEntity(source);
        }

        @Override
        public FootEntity[] newArray(int size) {
            return new FootEntity[size];
        }
    };
}
