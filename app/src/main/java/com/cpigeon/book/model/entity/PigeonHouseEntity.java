package com.cpigeon.book.model.entity;

/**
 * Created by Zhu TingYu on 2018/8/3.
 */

public class PigeonHouseEntity {

    /**
     * PigeonISOCID : 公棚名称 id
     * PigeonHomeID : 1
     * PigeonHomeName : name修改
     * Province : 省市修改
     * PigeonMatchNum : 比赛编号修改
     * Remark : 备注修改
     * PigeonHomeAdds : 地址修改
     * UsePigeonHomeNum : 修改
     */

    private int PigeonISOCID;
    private int PigeonHomeID;
    private String PigeonHomeName;
    private String Province;
    private String PigeonMatchNum;
    private String Remark;
    private String PigeonHomeAdds;
    private String UsePigeonHomeNum;

    public int getPigeonISOCID() {
        return PigeonISOCID;
    }

    public void setPigeonISOCID(int PigeonISOCID) {
        this.PigeonISOCID = PigeonISOCID;
    }

    public int getPigeonHomeID() {
        return PigeonHomeID;
    }

    public void setPigeonHomeID(int PigeonHomeID) {
        this.PigeonHomeID = PigeonHomeID;
    }

    public String getPigeonHomeName() {
        return PigeonHomeName;
    }

    public void setPigeonHomeName(String PigeonHomeName) {
        this.PigeonHomeName = PigeonHomeName;
    }

    public String getProvince() {
        return Province;
    }

    public void setProvince(String Province) {
        this.Province = Province;
    }

    public String getPigeonMatchNum() {
        return PigeonMatchNum;
    }

    public void setPigeonMatchNum(String PigeonMatchNum) {
        this.PigeonMatchNum = PigeonMatchNum;
    }

    public String getRemark() {
        return Remark;
    }

    public void setRemark(String Remark) {
        this.Remark = Remark;
    }

    public String getPigeonHomeAdds() {
        return PigeonHomeAdds;
    }

    public void setPigeonHomeAdds(String PigeonHomeAdds) {
        this.PigeonHomeAdds = PigeonHomeAdds;
    }

    public String getUsePigeonHomeNum() {
        return UsePigeonHomeNum;
    }

    public void setUsePigeonHomeNum(String UsePigeonHomeNum) {
        this.UsePigeonHomeNum = UsePigeonHomeNum;
    }
}
