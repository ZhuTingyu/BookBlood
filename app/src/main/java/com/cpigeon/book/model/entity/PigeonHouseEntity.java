package com.cpigeon.book.model.entity;

/**
 * Created by Zhu TingYu on 2018/8/3.
 */

public class PigeonHouseEntity {


    /**
     * Longitude :
     * UsePigeonHomeNum : 123456
     * touxiangurl : http://192.168.0.18:8188/Content/faces/default.png
     * PigeonHomeName :
     * PigeonISOCID : 中鸽
     * xingming :
     * PigeonMatchNum : 654321
     * xingbie :
     * PigeonHomePhone : 18302810737
     * Province : 四川省
     * Remark :
     * Latitude :
     * PigeonHomeAdds :
     * PigeonHomeID : 3
     */

    private double Longitude;
    private String UsePigeonHomeNum;
    private String touxiangurl;
    private String PigeonHomeName;
    private String PigeonISOCID;
    private String xingming;
    private String PigeonMatchNum;
    private String xingbie;
    private String PigeonHomePhone;
    private String Province;
    private String City;
    private String County;
    private String Remark;
    private double Latitude;
    private String PigeonHomeAdds;
    private String PigeonHomeID;

    public String getCity() {
        return City;
    }

    public void setCity(String city) {
        City = city;
    }

    public String getCounty() {
        return County;
    }

    public void setCounty(String county) {
        County = county;
    }

    public double getLongitude() {
        return Longitude;
    }

    public void setLongitude(double Longitude) {
        this.Longitude = Longitude;
    }

    public String getUsePigeonHomeNum() {
        return UsePigeonHomeNum;
    }

    public void setUsePigeonHomeNum(String UsePigeonHomeNum) {
        this.UsePigeonHomeNum = UsePigeonHomeNum;
    }

    public String getTouxiangurl() {
        return touxiangurl;
    }

    public void setTouxiangurl(String touxiangurl) {
        this.touxiangurl = touxiangurl;
    }

    public String getPigeonHomeName() {
        return PigeonHomeName;
    }

    public void setPigeonHomeName(String PigeonHomeName) {
        this.PigeonHomeName = PigeonHomeName;
    }

    public String getPigeonISOCID() {
        return PigeonISOCID;
    }

    public void setPigeonISOCID(String PigeonISOCID) {
        this.PigeonISOCID = PigeonISOCID;
    }

    public String getXingming() {
        return xingming;
    }

    public void setXingming(String xingming) {
        this.xingming = xingming;
    }

    public String getPigeonMatchNum() {
        return PigeonMatchNum;
    }

    public void setPigeonMatchNum(String PigeonMatchNum) {
        this.PigeonMatchNum = PigeonMatchNum;
    }

    public String getXingbie() {
        return xingbie;
    }

    public void setXingbie(String xingbie) {
        this.xingbie = xingbie;
    }

    public String getPigeonHomePhone() {
        return PigeonHomePhone;
    }

    public void setPigeonHomePhone(String PigeonHomePhone) {
        this.PigeonHomePhone = PigeonHomePhone;
    }

    public String getProvince() {
        return Province;
    }

    public void setProvince(String Province) {
        this.Province = Province;
    }

    public String getRemark() {
        return Remark;
    }

    public void setRemark(String Remark) {
        this.Remark = Remark;
    }

    public double getLatitude() {
        return Latitude;
    }

    public void setLatitude(double Latitude) {
        this.Latitude = Latitude;
    }

    public String getPigeonHomeAdds() {
        return PigeonHomeAdds;
    }

    public void setPigeonHomeAdds(String PigeonHomeAdds) {
        this.PigeonHomeAdds = PigeonHomeAdds;
    }

    public String getPigeonHomeID() {
        return PigeonHomeID;
    }

    public void setPigeonHomeID(String PigeonHomeID) {
        this.PigeonHomeID = PigeonHomeID;
    }
}
