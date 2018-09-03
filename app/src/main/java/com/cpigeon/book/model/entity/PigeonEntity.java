package com.cpigeon.book.model.entity;

/**
 * Created by Zhu TingYu on 2018/9/3.
 */

public class PigeonEntity {

    /**
     * FootRingID : 342820
     * FootRingNum : 2017-22-123467
     * PigeonID : 0
     * PigeonBloodName :
     */

    private int FootRingID;
    private String FootRingNum;
    private int PigeonID;
    private String PigeonBloodName;

    public int getFootRingID() {
        return FootRingID;
    }

    public void setFootRingID(int FootRingID) {
        this.FootRingID = FootRingID;
    }

    public String getFootRingNum() {
        return FootRingNum;
    }

    public void setFootRingNum(String FootRingNum) {
        this.FootRingNum = FootRingNum;
    }

    public int getPigeonID() {
        return PigeonID;
    }

    public void setPigeonID(int PigeonID) {
        this.PigeonID = PigeonID;
    }

    public String getPigeonBloodName() {
        return PigeonBloodName;
    }

    public void setPigeonBloodName(String PigeonBloodName) {
        this.PigeonBloodName = PigeonBloodName;
    }
}
