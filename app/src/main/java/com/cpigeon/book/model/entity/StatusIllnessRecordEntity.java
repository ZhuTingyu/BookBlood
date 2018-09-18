package com.cpigeon.book.model.entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 病情记录
 * Created by Administrator on 2018/9/18 0018.
 */

public class StatusIllnessRecordEntity implements Parcelable {


    /**
     * FootRingID : 1202849
     * footRingNum : 2018-01-6433161
     * PigeonDiseaseId : 6
     * DiseaseInfo : 这是症状
     * PigeonDiseaseName : 疾病名称
     * DiseaseTime : 2018-08-17
     * Temperature : 27
     * Bodytemper : 11
     * Remark :
     * PigeonID : 286
     * Direction :
     * Weather : 多云
     * Humidity : 北
     */

    private int FootRingID;
    private String footRingNum;
    private int PigeonDiseaseId;
    private String DiseaseInfo;
    private String PigeonDiseaseName;
    private String DiseaseTime;
    private String Temperature;
    private String Bodytemper;
    private String Remark;
    private int PigeonID;
    private String Direction;
    private String Weather;
    private String Humidity;

    public int getFootRingID() {
        return FootRingID;
    }

    public void setFootRingID(int FootRingID) {
        this.FootRingID = FootRingID;
    }

    public String getFootRingNum() {
        return footRingNum;
    }

    public void setFootRingNum(String footRingNum) {
        this.footRingNum = footRingNum;
    }

    public int getPigeonDiseaseId() {
        return PigeonDiseaseId;
    }

    public void setPigeonDiseaseId(int PigeonDiseaseId) {
        this.PigeonDiseaseId = PigeonDiseaseId;
    }

    public String getDiseaseInfo() {
        return DiseaseInfo;
    }

    public void setDiseaseInfo(String DiseaseInfo) {
        this.DiseaseInfo = DiseaseInfo;
    }

    public String getPigeonDiseaseName() {
        return PigeonDiseaseName;
    }

    public void setPigeonDiseaseName(String PigeonDiseaseName) {
        this.PigeonDiseaseName = PigeonDiseaseName;
    }

    public String getDiseaseTime() {
        return DiseaseTime;
    }

    public void setDiseaseTime(String DiseaseTime) {
        this.DiseaseTime = DiseaseTime;
    }

    public String getTemperature() {
        return Temperature;
    }

    public void setTemperature(String Temperature) {
        this.Temperature = Temperature;
    }

    public String getBodytemper() {
        return Bodytemper;
    }

    public void setBodytemper(String Bodytemper) {
        this.Bodytemper = Bodytemper;
    }

    public String getRemark() {
        return Remark;
    }

    public void setRemark(String Remark) {
        this.Remark = Remark;
    }

    public int getPigeonID() {
        return PigeonID;
    }

    public void setPigeonID(int PigeonID) {
        this.PigeonID = PigeonID;
    }

    public String getDirection() {
        return Direction;
    }

    public void setDirection(String Direction) {
        this.Direction = Direction;
    }

    public String getWeather() {
        return Weather;
    }

    public void setWeather(String Weather) {
        this.Weather = Weather;
    }

    public String getHumidity() {
        return Humidity;
    }

    public void setHumidity(String Humidity) {
        this.Humidity = Humidity;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.FootRingID);
        dest.writeString(this.footRingNum);
        dest.writeInt(this.PigeonDiseaseId);
        dest.writeString(this.DiseaseInfo);
        dest.writeString(this.PigeonDiseaseName);
        dest.writeString(this.DiseaseTime);
        dest.writeString(this.Temperature);
        dest.writeString(this.Bodytemper);
        dest.writeString(this.Remark);
        dest.writeInt(this.PigeonID);
        dest.writeString(this.Direction);
        dest.writeString(this.Weather);
        dest.writeString(this.Humidity);
    }

    public StatusIllnessRecordEntity() {
    }

    protected StatusIllnessRecordEntity(Parcel in) {
        this.FootRingID = in.readInt();
        this.footRingNum = in.readString();
        this.PigeonDiseaseId = in.readInt();
        this.DiseaseInfo = in.readString();
        this.PigeonDiseaseName = in.readString();
        this.DiseaseTime = in.readString();
        this.Temperature = in.readString();
        this.Bodytemper = in.readString();
        this.Remark = in.readString();
        this.PigeonID = in.readInt();
        this.Direction = in.readString();
        this.Weather = in.readString();
        this.Humidity = in.readString();
    }

    public static final Creator<StatusIllnessRecordEntity> CREATOR = new Creator<StatusIllnessRecordEntity>() {
        @Override
        public StatusIllnessRecordEntity createFromParcel(Parcel source) {
            return new StatusIllnessRecordEntity(source);
        }

        @Override
        public StatusIllnessRecordEntity[] newArray(int size) {
            return new StatusIllnessRecordEntity[size];
        }
    };
}
