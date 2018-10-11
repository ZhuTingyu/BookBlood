package com.cpigeon.book.model.entity;

/**
 * Created by Administrator on 2018/10/11 0011.
 */

public class RecycleBinEntity {


    /**
     * AddTime : 添加时间
     * TypeID : 名称id
     * RecycleID : 回收站id
     * FootRingID : 足环id
     * MeID : 删除数据id
     * RelaMeID : 删除数据关联id
     * RecycleName : 名称
     * PigeonID : 鸽子id
     * RelaInfo : 回收数据信息
     */

    private String AddTime;
    private String TypeID;
    private String RecycleID;
    private String FootRingID;
    private String MeID;
    private String RelaMeID;
    private String RecycleName;
    private String PigeonID;
    private String RelaInfo;

    public String getAddTime() {
        return AddTime;
    }

    public void setAddTime(String AddTime) {
        this.AddTime = AddTime;
    }

    public String getTypeID() {
        return TypeID;
    }

    public void setTypeID(String TypeID) {
        this.TypeID = TypeID;
    }

    public String getRecycleID() {
        return RecycleID;
    }

    public void setRecycleID(String RecycleID) {
        this.RecycleID = RecycleID;
    }

    public String getFootRingID() {
        return FootRingID;
    }

    public void setFootRingID(String FootRingID) {
        this.FootRingID = FootRingID;
    }

    public String getMeID() {
        return MeID;
    }

    public void setMeID(String MeID) {
        this.MeID = MeID;
    }

    public String getRelaMeID() {
        return RelaMeID;
    }

    public void setRelaMeID(String RelaMeID) {
        this.RelaMeID = RelaMeID;
    }

    public String getRecycleName() {
        return RecycleName;
    }

    public void setRecycleName(String RecycleName) {
        this.RecycleName = RecycleName;
    }

    public String getPigeonID() {
        return PigeonID;
    }

    public void setPigeonID(String PigeonID) {
        this.PigeonID = PigeonID;
    }

    public String getRelaInfo() {
        return RelaInfo;
    }

    public void setRelaInfo(String RelaInfo) {
        this.RelaInfo = RelaInfo;
    }
}
