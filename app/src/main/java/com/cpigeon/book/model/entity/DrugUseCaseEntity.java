package com.cpigeon.book.model.entity;

/**
 * 用药情况
 * Created by Administrator on 2018/9/17 0017.
 */

public class DrugUseCaseEntity {


    /**
     * FootRingID : 1202849
     * PigeonDiseaseID : 6
     * BitEffect : 2
     * UseDrugTime : 2018-08-18
     * DrugName : 药品1
     * Bodytemper : 12
     * RecordTime : 2018-08-18
     * PigeonDrugID : 4
     * Weather : 多云
     * Temperature : 22
     * FootRingNum : 2018-01-6433161
     * PigeonDiseaseName : 疾病名称
     * Remark :
     * Humidity : 西南
     * PigeonID : 286
     * Direction :
     * EffectStateID : 57
     * EffectStateName : 有效果
     */

    private int FootRingID;
    private int PigeonDiseaseID;
    private int BitEffect;
    private String UseDrugTime;
    private String DrugName;
    private String Bodytemper;
    private String RecordTime;
    private int PigeonDrugID;
    private int DrugNameID;
    private String Weather;
    private String Temperature;
    private String FootRingNum;
    private String PigeonDiseaseName;
    private String PigeonDrugName;
    private String Remark;
    private String Humidity;
    private int PigeonID;
    private String Direction;
    private int EffectStateID;
    private String EffectStateName;

    public int getDrugNameID() {
        return DrugNameID;
    }

    public void setDrugNameID(int drugNameID) {
        DrugNameID = drugNameID;
    }

    public String getPigeonDrugName() {
        return PigeonDrugName;
    }

    public void setPigeonDrugName(String pigeonDrugName) {
        PigeonDrugName = pigeonDrugName;
    }

    public int getFootRingID() {
        return FootRingID;
    }

    public void setFootRingID(int FootRingID) {
        this.FootRingID = FootRingID;
    }

    public int getPigeonDiseaseID() {
        return PigeonDiseaseID;
    }

    public void setPigeonDiseaseID(int PigeonDiseaseID) {
        this.PigeonDiseaseID = PigeonDiseaseID;
    }

    public int getBitEffect() {
        return BitEffect;
    }

    public void setBitEffect(int BitEffect) {
        this.BitEffect = BitEffect;
    }

    public String getUseDrugTime() {
        return UseDrugTime;
    }

    public void setUseDrugTime(String UseDrugTime) {
        this.UseDrugTime = UseDrugTime;
    }

    public String getDrugName() {
        return DrugName;
    }

    public void setDrugName(String DrugName) {
        this.DrugName = DrugName;
    }

    public String getBodytemper() {
        return Bodytemper;
    }

    public void setBodytemper(String Bodytemper) {
        this.Bodytemper = Bodytemper;
    }

    public String getRecordTime() {
        return RecordTime;
    }

    public void setRecordTime(String RecordTime) {
        this.RecordTime = RecordTime;
    }

    public int getPigeonDrugID() {
        return PigeonDrugID;
    }

    public void setPigeonDrugID(int PigeonDrugID) {
        this.PigeonDrugID = PigeonDrugID;
    }

    public String getWeather() {
        return Weather;
    }

    public void setWeather(String Weather) {
        this.Weather = Weather;
    }

    public String getTemperature() {
        return Temperature;
    }

    public void setTemperature(String Temperature) {
        this.Temperature = Temperature;
    }

    public String getFootRingNum() {
        return FootRingNum;
    }

    public void setFootRingNum(String FootRingNum) {
        this.FootRingNum = FootRingNum;
    }

    public String getPigeonDiseaseName() {
        return PigeonDiseaseName;
    }

    public void setPigeonDiseaseName(String PigeonDiseaseName) {
        this.PigeonDiseaseName = PigeonDiseaseName;
    }

    public String getRemark() {
        return Remark;
    }

    public void setRemark(String Remark) {
        this.Remark = Remark;
    }

    public String getHumidity() {
        return Humidity;
    }

    public void setHumidity(String Humidity) {
        this.Humidity = Humidity;
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

    public int getEffectStateID() {
        return EffectStateID;
    }

    public void setEffectStateID(int EffectStateID) {
        this.EffectStateID = EffectStateID;
    }

    public String getEffectStateName() {
        return EffectStateName;
    }

    public void setEffectStateName(String EffectStateName) {
        this.EffectStateName = EffectStateName;
    }
}
