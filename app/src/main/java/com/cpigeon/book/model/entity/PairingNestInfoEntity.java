package com.cpigeon.book.model.entity;

import java.util.List;

/**
 * Created by Administrator on 2018/9/15 0015.
 */

public class PairingNestInfoEntity {


    /**
     * OutShellTime : 0001-01-01
     * PigeonList : [{"PigeonSexName":"性别","PigeonPlumeName":"羽色","FootRingID":"足环id","FootRingNum":"足环号码","PigeonID":"母鸽子id"}]
     * PigeonBreedNestID : 5
     * LayNum : 0
     * OutShellCount : 0
     * FertEggCount : 0
     * PigeonBreedTime : 2018-09-19
     * InseEggCount : 0
     * LayEggTime : 0001-01-01
     * PigeonBreedID : 88
     */

    private String OutShellTime;
    private String PigeonBreedNestID;
    private String LayNum;
    private String OutShellCount;
    private String FertEggCount;//无精蛋
    private String PigeonBreedTime;
    private String InseEggCount;//受精蛋
    private String LayEggTime;
    private String PigeonBreedID;
    private List<PigeonListBean> PigeonList;

    public String getOutShellTime() {
        return OutShellTime;
    }

    public void setOutShellTime(String OutShellTime) {
        this.OutShellTime = OutShellTime;
    }

    public String getPigeonBreedNestID() {
        return PigeonBreedNestID;
    }

    public void setPigeonBreedNestID(String PigeonBreedNestID) {
        this.PigeonBreedNestID = PigeonBreedNestID;
    }

    public String getLayNum() {
        return LayNum;
    }

    public void setLayNum(String LayNum) {
        this.LayNum = LayNum;
    }

    public String getOutShellCount() {
        return OutShellCount;
    }

    public void setOutShellCount(String OutShellCount) {
        this.OutShellCount = OutShellCount;
    }

    public String getFertEggCount() {
        return FertEggCount;
    }

    public void setFertEggCount(String FertEggCount) {
        this.FertEggCount = FertEggCount;
    }

    public String getPigeonBreedTime() {
        return PigeonBreedTime;
    }

    public void setPigeonBreedTime(String PigeonBreedTime) {
        this.PigeonBreedTime = PigeonBreedTime;
    }

    public String getInseEggCount() {
        return InseEggCount;
    }

    public void setInseEggCount(String InseEggCount) {
        this.InseEggCount = InseEggCount;
    }

    public String getLayEggTime() {
        return LayEggTime;
    }

    public void setLayEggTime(String LayEggTime) {
        this.LayEggTime = LayEggTime;
    }

    public String getPigeonBreedID() {
        return PigeonBreedID;
    }

    public void setPigeonBreedID(String PigeonBreedID) {
        this.PigeonBreedID = PigeonBreedID;
    }

    public List<PigeonListBean> getPigeonList() {
        return PigeonList;
    }

    public void setPigeonList(List<PigeonListBean> PigeonList) {
        this.PigeonList = PigeonList;
    }


    public static class PigeonListBean {
        /**
         * PigeonSexName : 性别
         * PigeonPlumeName : 羽色
         * FootRingID : 足环id
         * FootRingNum : 足环号码
         * PigeonID : 母鸽子id
         */

        private String PigeonSexName;
        private String PigeonPlumeName;
        private String FootRingID;
        private String FootRingNum;
        private String PigeonID;

        public String getPigeonSexName() {
            return PigeonSexName;
        }

        public void setPigeonSexName(String PigeonSexName) {
            this.PigeonSexName = PigeonSexName;
        }

        public String getPigeonPlumeName() {
            return PigeonPlumeName;
        }

        public void setPigeonPlumeName(String PigeonPlumeName) {
            this.PigeonPlumeName = PigeonPlumeName;
        }

        public String getFootRingID() {
            return FootRingID;
        }

        public void setFootRingID(String FootRingID) {
            this.FootRingID = FootRingID;
        }

        public String getFootRingNum() {
            return FootRingNum;
        }

        public void setFootRingNum(String FootRingNum) {
            this.FootRingNum = FootRingNum;
        }

        public String getPigeonID() {
            return PigeonID;
        }

        public void setPigeonID(String PigeonID) {
            this.PigeonID = PigeonID;
        }
    }
}
