package com.cpigeon.book.model.entity;

/**
 * Created by Administrator on 2018/9/17 0017.
 */

public class FeedPigeonStatistical {


    /**
     * HealthCount : 保健品个数
     * VaccineCount : 疫苗个数
     * DrugCount : 用药个数
     * DiseaseCount : 生病个数
     * PhotoCount : 图片个数
     */

    private String HealthCount;
    private String VaccineCount;
    private String DrugCount;
    private String DiseaseCount;
    private String PhotoCount;

    public String getHealthCount() {
        return HealthCount;
    }

    public void setHealthCount(String HealthCount) {
        this.HealthCount = HealthCount;
    }

    public String getVaccineCount() {
        return VaccineCount;
    }

    public void setVaccineCount(String VaccineCount) {
        this.VaccineCount = VaccineCount;
    }

    public String getDrugCount() {
        return DrugCount;
    }

    public void setDrugCount(String DrugCount) {
        this.DrugCount = DrugCount;
    }

    public String getDiseaseCount() {
        return DiseaseCount;
    }

    public void setDiseaseCount(String DiseaseCount) {
        this.DiseaseCount = DiseaseCount;
    }

    public String getPhotoCount() {
        return PhotoCount;
    }

    public void setPhotoCount(String PhotoCount) {
        this.PhotoCount = PhotoCount;
    }
}
