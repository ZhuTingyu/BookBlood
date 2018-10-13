package com.cpigeon.book.model.entity;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/9/15 0015.
 */

public class PairingNestInfoEntity implements Parcelable {


    /**
     * PigeonBreedNestID : 14
     * OutWeather : 多云
     * LayNum : 0
     * LayEggTime : 0001-01-01
     * PigeonBreedID : 88
     * OutHumidity : 75
     * EggDirection : 西南
     * EggTemperature : 25
     * FertEggCount : 0
     * EggHumidity : 75
     * EggWeather : 多云
     * PigeonBreedTime : 2018-09-20
     * OutTemperature : 25
     * InseEggCount : 0
     * OutShellTime : 0001-01-01
     * PigeonList : [{"PigeonID":299,"PigeonPlumeName":"雨点","FootRingNum":"2018-01-6466646","PigeonSexName":"雌","FootRingID":1282864},{"PigeonID":302,"PigeonPlumeName":"25","FootRingNum":"2018-01-3161536","PigeonSexName":"雌","FootRingID":1282870},{"PigeonID":303,"PigeonPlumeName":"25","FootRingNum":"2018-01-3161536","PigeonSexName":"雌","FootRingID":1282870}]
     * OutShellCount : 0
     * OutDirection : 西南
     */

    private String PigeonBreedNestID;
    private String OutWeather;
    private String LayNum;
    private String LayEggTime;
    private String PigeonBreedID;
    private String OutHumidity;
    private String EggDirection;
    private String EggTemperature;
    private String FertEggCount;
    private String EggHumidity;
    private String EggWeather;
    private String PigeonBreedTime;
    private String OutTemperature;
    private String InseEggCount;
    private String OutShellTime;
    private String OutShellCount;
    private String OutDirection;
    private String GivePrson;
    private List<PigeonListBean> PigeonList;

    private PairingNestInfoEntity(Builder builder) {
        setPigeonBreedNestID(builder.PigeonBreedNestID);
        setOutWeather(builder.OutWeather);
        setLayNum(builder.LayNum);
        setLayEggTime(builder.LayEggTime);
        setPigeonBreedID(builder.PigeonBreedID);
        setOutHumidity(builder.OutHumidity);
        setEggDirection(builder.EggDirection);
        setEggTemperature(builder.EggTemperature);
        setFertEggCount(builder.FertEggCount);
        setEggHumidity(builder.EggHumidity);
        setEggWeather(builder.EggWeather);
        setPigeonBreedTime(builder.PigeonBreedTime);
        setOutTemperature(builder.OutTemperature);
        setInseEggCount(builder.InseEggCount);
        setOutShellTime(builder.OutShellTime);
        setOutShellCount(builder.OutShellCount);
        setOutDirection(builder.OutDirection);
        GivePrson = builder.GivePrson;
        setPigeonList(builder.PigeonList);
    }

    public String getGivePrson() {
        return GivePrson;
    }

    public void setGivePrson(String givePrson) {
        GivePrson = givePrson;
    }

    public String getPigeonBreedNestID() {
        return PigeonBreedNestID;
    }

    public void setPigeonBreedNestID(String pigeonBreedNestID) {
        PigeonBreedNestID = pigeonBreedNestID;
    }

    public String getOutWeather() {
        return OutWeather;
    }

    public void setOutWeather(String outWeather) {
        OutWeather = outWeather;
    }

    public String getLayNum() {
        return LayNum;
    }

    public void setLayNum(String layNum) {
        LayNum = layNum;
    }

    public String getLayEggTime() {
        return LayEggTime;
    }

    public void setLayEggTime(String layEggTime) {
        LayEggTime = layEggTime;
    }

    public String getPigeonBreedID() {
        return PigeonBreedID;
    }

    public void setPigeonBreedID(String pigeonBreedID) {
        PigeonBreedID = pigeonBreedID;
    }

    public String getOutHumidity() {
        return OutHumidity;
    }

    public void setOutHumidity(String outHumidity) {
        OutHumidity = outHumidity;
    }

    public String getEggDirection() {
        return EggDirection;
    }

    public void setEggDirection(String eggDirection) {
        EggDirection = eggDirection;
    }

    public String getEggTemperature() {
        return EggTemperature;
    }

    public void setEggTemperature(String eggTemperature) {
        EggTemperature = eggTemperature;
    }

    public String getFertEggCount() {
        return FertEggCount;
    }

    public void setFertEggCount(String fertEggCount) {
        FertEggCount = fertEggCount;
    }

    public String getEggHumidity() {
        return EggHumidity;
    }

    public void setEggHumidity(String eggHumidity) {
        EggHumidity = eggHumidity;
    }

    public String getEggWeather() {
        return EggWeather;
    }

    public void setEggWeather(String eggWeather) {
        EggWeather = eggWeather;
    }

    public String getPigeonBreedTime() {
        return PigeonBreedTime;
    }

    public void setPigeonBreedTime(String pigeonBreedTime) {
        PigeonBreedTime = pigeonBreedTime;
    }

    public String getOutTemperature() {
        return OutTemperature;
    }

    public void setOutTemperature(String outTemperature) {
        OutTemperature = outTemperature;
    }

    public String getInseEggCount() {
        return InseEggCount;
    }

    public void setInseEggCount(String inseEggCount) {
        InseEggCount = inseEggCount;
    }

    public String getOutShellTime() {
        return OutShellTime;
    }

    public void setOutShellTime(String outShellTime) {
        OutShellTime = outShellTime;
    }

    public String getOutShellCount() {
        return OutShellCount;
    }

    public void setOutShellCount(String outShellCount) {
        OutShellCount = outShellCount;
    }

    public String getOutDirection() {
        return OutDirection;
    }

    public void setOutDirection(String outDirection) {
        OutDirection = outDirection;
    }

    public List<PigeonListBean> getPigeonList() {
        return PigeonList;
    }

    public void setPigeonList(List<PigeonListBean> pigeonList) {
        PigeonList = pigeonList;
    }

    public static class PigeonListBean {
        /**
         * PigeonID : 299
         * PigeonPlumeName : 雨点
         * FootRingNum : 2018-01-6466646
         * PigeonSexName : 雌
         * FootRingID : 1282864
         */

        private String PigeonID;
        private String PigeonPlumeName;
        private String FootRingNum;
        private String PigeonSexName;
        private String FootRingID;

        public String getPigeonID() {
            return PigeonID;
        }

        public void setPigeonID(String pigeonID) {
            PigeonID = pigeonID;
        }

        public String getPigeonPlumeName() {
            return PigeonPlumeName;
        }

        public void setPigeonPlumeName(String pigeonPlumeName) {
            PigeonPlumeName = pigeonPlumeName;
        }

        public String getFootRingNum() {
            return FootRingNum;
        }

        public void setFootRingNum(String footRingNum) {
            FootRingNum = footRingNum;
        }

        public String getPigeonSexName() {
            return PigeonSexName;
        }

        public void setPigeonSexName(String pigeonSexName) {
            PigeonSexName = pigeonSexName;
        }

        public String getFootRingID() {
            return FootRingID;
        }

        public void setFootRingID(String footRingID) {
            FootRingID = footRingID;
        }

        private PigeonListBean(Builder builder) {
            PigeonID = builder.PigeonID;
            PigeonPlumeName = builder.PigeonPlumeName;
            FootRingNum = builder.FootRingNum;
            PigeonSexName = builder.PigeonSexName;
            FootRingID = builder.FootRingID;
        }


        public static final class Builder {
            private String PigeonID;
            private String PigeonPlumeName;
            private String FootRingNum;
            private String PigeonSexName;
            private String FootRingID;

            public Builder() {
            }

            public Builder PigeonID(String val) {
                PigeonID = val;
                return this;
            }

            public Builder PigeonPlumeName(String val) {
                PigeonPlumeName = val;
                return this;
            }

            public Builder FootRingNum(String val) {
                FootRingNum = val;
                return this;
            }

            public Builder PigeonSexName(String val) {
                PigeonSexName = val;
                return this;
            }

            public Builder FootRingID(String val) {
                FootRingID = val;
                return this;
            }

            public PigeonListBean build() {
                return new PigeonListBean(this);
            }
        }
    }

    public static final class Builder {
        private String PigeonBreedNestID;
        private String OutWeather;
        private String LayNum;
        private String LayEggTime;
        private String PigeonBreedID;
        private String OutHumidity;
        private String EggDirection;
        private String EggTemperature;
        private String FertEggCount;
        private String EggHumidity;
        private String EggWeather;
        private String PigeonBreedTime;
        private String OutTemperature;
        private String InseEggCount;
        private String OutShellTime;
        private String OutShellCount;
        private String OutDirection;
        private String GivePrson;
        private List<PigeonListBean> PigeonList;

        public Builder() {
        }

        public Builder PigeonBreedNestID(String val) {
            PigeonBreedNestID = val;
            return this;
        }

        public Builder OutWeather(String val) {
            OutWeather = val;
            return this;
        }

        public Builder LayNum(String val) {
            LayNum = val;
            return this;
        }

        public Builder LayEggTime(String val) {
            LayEggTime = val;
            return this;
        }

        public Builder PigeonBreedID(String val) {
            PigeonBreedID = val;
            return this;
        }

        public Builder OutHumidity(String val) {
            OutHumidity = val;
            return this;
        }

        public Builder EggDirection(String val) {
            EggDirection = val;
            return this;
        }

        public Builder EggTemperature(String val) {
            EggTemperature = val;
            return this;
        }

        public Builder FertEggCount(String val) {
            FertEggCount = val;
            return this;
        }

        public Builder EggHumidity(String val) {
            EggHumidity = val;
            return this;
        }

        public Builder EggWeather(String val) {
            EggWeather = val;
            return this;
        }

        public Builder PigeonBreedTime(String val) {
            PigeonBreedTime = val;
            return this;
        }

        public Builder OutTemperature(String val) {
            OutTemperature = val;
            return this;
        }

        public Builder InseEggCount(String val) {
            InseEggCount = val;
            return this;
        }

        public Builder OutShellTime(String val) {
            OutShellTime = val;
            return this;
        }

        public Builder OutShellCount(String val) {
            OutShellCount = val;
            return this;
        }

        public Builder OutDirection(String val) {
            OutDirection = val;
            return this;
        }

        public Builder GivePrson(String val) {
            GivePrson = val;
            return this;
        }

        public Builder PigeonList(List<PigeonListBean> val) {
            PigeonList = val;
            return this;
        }

        public PairingNestInfoEntity build() {
            return new PairingNestInfoEntity(this);
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.PigeonBreedNestID);
        dest.writeString(this.OutWeather);
        dest.writeString(this.LayNum);
        dest.writeString(this.LayEggTime);
        dest.writeString(this.PigeonBreedID);
        dest.writeString(this.OutHumidity);
        dest.writeString(this.EggDirection);
        dest.writeString(this.EggTemperature);
        dest.writeString(this.FertEggCount);
        dest.writeString(this.EggHumidity);
        dest.writeString(this.EggWeather);
        dest.writeString(this.PigeonBreedTime);
        dest.writeString(this.OutTemperature);
        dest.writeString(this.InseEggCount);
        dest.writeString(this.OutShellTime);
        dest.writeString(this.OutShellCount);
        dest.writeString(this.OutDirection);
        dest.writeString(this.GivePrson);
        dest.writeList(this.PigeonList);
    }

    protected PairingNestInfoEntity(Parcel in) {
        this.PigeonBreedNestID = in.readString();
        this.OutWeather = in.readString();
        this.LayNum = in.readString();
        this.LayEggTime = in.readString();
        this.PigeonBreedID = in.readString();
        this.OutHumidity = in.readString();
        this.EggDirection = in.readString();
        this.EggTemperature = in.readString();
        this.FertEggCount = in.readString();
        this.EggHumidity = in.readString();
        this.EggWeather = in.readString();
        this.PigeonBreedTime = in.readString();
        this.OutTemperature = in.readString();
        this.InseEggCount = in.readString();
        this.OutShellTime = in.readString();
        this.OutShellCount = in.readString();
        this.OutDirection = in.readString();
        this.GivePrson = in.readString();
        this.PigeonList = new ArrayList<PigeonListBean>();
        in.readList(this.PigeonList, PigeonListBean.class.getClassLoader());
    }

    public static final Creator<PairingNestInfoEntity> CREATOR = new Creator<PairingNestInfoEntity>() {
        @Override
        public PairingNestInfoEntity createFromParcel(Parcel source) {
            return new PairingNestInfoEntity(source);
        }

        @Override
        public PairingNestInfoEntity[] newArray(int size) {
            return new PairingNestInfoEntity[size];
        }
    };
}
