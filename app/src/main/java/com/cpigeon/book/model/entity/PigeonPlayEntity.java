package com.cpigeon.book.model.entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Administrator on 2018/9/4.
 */

public class PigeonPlayEntity implements Parcelable {


    /**
     * MatchTime : 2016-05-12 14:09:55
     * MatchName : ceshi2
     * MatchNumber : 36
     * FootRingID : 0
     * MatchCount : 100
     * PigeonID : 6
     * MatchInterval : 0
     * PigeonMatchID : 7
     */

    private String MatchTime;
    private String MatchName;
    private String MatchNumber;
    private String FootRingID;
    private String MatchCount;
    private String PigeonID;
    private String MatchInterval;
    private String PigeonMatchID;
    private String MatchInfoID;
    private String BitUpdate;
    private String ConnectUrl;
    private String MatchISOCName;
    private String MatchISOCID;
    private String MatchAdds;
    private String FootRingNum;
    private String MatchInfo;

    private PigeonPlayEntity(Builder builder) {
        setMatchTime(builder.MatchTime);
        setMatchName(builder.MatchName);
        setMatchNumber(builder.MatchNumber);
        setFootRingID(builder.FootRingID);
        setMatchCount(builder.MatchCount);
        setPigeonID(builder.PigeonID);
        setMatchInterval(builder.MatchInterval);
        setPigeonMatchID(builder.PigeonMatchID);
        setMatchInfoID(builder.MatchInfoID);
        setBitUpdate(builder.BitUpdate);
        setConnectUrl(builder.ConnectUrl);
        setMatchISOCName(builder.MatchISOCName);
        setMatchISOCID(builder.MatchISOCID);
        setMatchAdds(builder.MatchAdds);
        setFootRingNum(builder.FootRingNum);
    }

    public String getMatchInfo() {
        return MatchInfo;
    }

    public String getMatchTime() {
        return MatchTime;
    }

    public void setMatchTime(String matchTime) {
        MatchTime = matchTime;
    }

    public String getMatchName() {
        return MatchName;
    }

    public void setMatchName(String matchName) {
        MatchName = matchName;
    }

    public String getMatchNumber() {
        return MatchNumber;
    }

    public void setMatchNumber(String matchNumber) {
        MatchNumber = matchNumber;
    }

    public String getFootRingID() {
        return FootRingID;
    }

    public void setFootRingID(String footRingID) {
        FootRingID = footRingID;
    }

    public String getMatchCount() {
        return MatchCount;
    }

    public void setMatchCount(String matchCount) {
        MatchCount = matchCount;
    }

    public String getPigeonID() {
        return PigeonID;
    }

    public void setPigeonID(String pigeonID) {
        PigeonID = pigeonID;
    }

    public String getMatchInterval() {
        return MatchInterval;
    }

    public void setMatchInterval(String matchInterval) {
        MatchInterval = matchInterval;
    }

    public String getPigeonMatchID() {
        return PigeonMatchID;
    }

    public void setPigeonMatchID(String pigeonMatchID) {
        PigeonMatchID = pigeonMatchID;
    }

    public String getMatchInfoID() {
        return MatchInfoID;
    }

    public void setMatchInfoID(String matchInfoID) {
        MatchInfoID = matchInfoID;
    }

    public String getBitUpdate() {
        return BitUpdate;
    }

    public void setBitUpdate(String bitUpdate) {
        BitUpdate = bitUpdate;
    }

    public String getConnectUrl() {
        return ConnectUrl;
    }

    public void setConnectUrl(String connectUrl) {
        ConnectUrl = connectUrl;
    }

    public String getMatchISOCName() {
        return MatchISOCName;
    }

    public void setMatchISOCName(String matchISOCName) {
        MatchISOCName = matchISOCName;
    }

    public String getMatchISOCID() {
        return MatchISOCID;
    }

    public void setMatchISOCID(String matchISOCID) {
        MatchISOCID = matchISOCID;
    }

    public String getMatchAdds() {
        return MatchAdds;
    }

    public void setMatchAdds(String matchAdds) {
        MatchAdds = matchAdds;
    }

    public String getFootRingNum() {
        return FootRingNum;
    }

    public void setFootRingNum(String footRingNum) {
        FootRingNum = footRingNum;
    }


    public static final class Builder {
        private String MatchTime;
        private String MatchName;
        private String MatchNumber;
        private String FootRingID;
        private String MatchCount;
        private String PigeonID;
        private String MatchInterval;
        private String PigeonMatchID;
        private String MatchInfoID;
        private String BitUpdate;
        private String ConnectUrl;
        private String MatchISOCName;
        private String MatchISOCID;
        private String MatchAdds;
        private String FootRingNum;

        public Builder() {
        }

        public Builder MatchTime(String val) {
            MatchTime = val;
            return this;
        }

        public Builder MatchName(String val) {
            MatchName = val;
            return this;
        }

        public Builder MatchNumber(String val) {
            MatchNumber = val;
            return this;
        }

        public Builder FootRingID(String val) {
            FootRingID = val;
            return this;
        }

        public Builder MatchCount(String val) {
            MatchCount = val;
            return this;
        }

        public Builder PigeonID(String val) {
            PigeonID = val;
            return this;
        }

        public Builder MatchInterval(String val) {
            MatchInterval = val;
            return this;
        }

        public Builder PigeonMatchID(String val) {
            PigeonMatchID = val;
            return this;
        }

        public Builder MatchInfoID(String val) {
            MatchInfoID = val;
            return this;
        }

        public Builder BitUpdate(String val) {
            BitUpdate = val;
            return this;
        }

        public Builder ConnectUrl(String val) {
            ConnectUrl = val;
            return this;
        }

        public Builder MatchISOCName(String val) {
            MatchISOCName = val;
            return this;
        }

        public Builder MatchISOCID(String val) {
            MatchISOCID = val;
            return this;
        }

        public Builder MatchAdds(String val) {
            MatchAdds = val;
            return this;
        }

        public Builder FootRingNum(String val) {
            FootRingNum = val;
            return this;
        }

        public PigeonPlayEntity build() {
            return new PigeonPlayEntity(this);
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.MatchTime);
        dest.writeString(this.MatchName);
        dest.writeString(this.MatchNumber);
        dest.writeString(this.FootRingID);
        dest.writeString(this.MatchCount);
        dest.writeString(this.PigeonID);
        dest.writeString(this.MatchInterval);
        dest.writeString(this.PigeonMatchID);
        dest.writeString(this.MatchInfoID);
        dest.writeString(this.BitUpdate);
        dest.writeString(this.ConnectUrl);
        dest.writeString(this.MatchISOCName);
        dest.writeString(this.MatchISOCID);
        dest.writeString(this.MatchAdds);
        dest.writeString(this.FootRingNum);
    }

    protected PigeonPlayEntity(Parcel in) {
        this.MatchTime = in.readString();
        this.MatchName = in.readString();
        this.MatchNumber = in.readString();
        this.FootRingID = in.readString();
        this.MatchCount = in.readString();
        this.PigeonID = in.readString();
        this.MatchInterval = in.readString();
        this.PigeonMatchID = in.readString();
        this.MatchInfoID = in.readString();
        this.BitUpdate = in.readString();
        this.ConnectUrl = in.readString();
        this.MatchISOCName = in.readString();
        this.MatchISOCID = in.readString();
        this.MatchAdds = in.readString();
        this.FootRingNum = in.readString();
    }

    public static final Parcelable.Creator<PigeonPlayEntity> CREATOR = new Parcelable.Creator<PigeonPlayEntity>() {
        @Override
        public PigeonPlayEntity createFromParcel(Parcel source) {
            return new PigeonPlayEntity(source);
        }

        @Override
        public PigeonPlayEntity[] newArray(int size) {
            return new PigeonPlayEntity[size];
        }
    };
}
