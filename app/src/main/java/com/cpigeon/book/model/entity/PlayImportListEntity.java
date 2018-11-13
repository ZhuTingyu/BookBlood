package com.cpigeon.book.model.entity;

import android.os.Parcel;

import com.base.entity.MultiSelectEntity;

/**
 * Created by Administrator on 2018/9/3.
 */

public class PlayImportListEntity extends MultiSelectEntity {

    /**
     * id : 索引ID
     * sfkj : 比赛空距（单位千米）
     * sfdd : 放飞地点
     * gzxm : 鸽主姓名
     * bsmc : 比赛名次
     * bsgm : 比赛规模
     * fensu : 比赛分速（单位米）
     * zdfensu : 比赛第一名分速（单位米）
     * bsrq : 比赛时间
     * xmmc : 项目名称
     * sjly : 数据来源
     */

    private String rid;
    private String id;
    private String sfkj;
    private String sfdd;
    private String gzxm;
    private String bsmc;
    private String bsgm;
    private String fensu;
    private String zdfensu;
    private String bsrq;
    private String xmmc;
    private String sjly;

    public void setRid(String rid) {
        this.rid = rid;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSfkj() {
        return sfkj;
    }

    public void setSfkj(String sfkj) {
        this.sfkj = sfkj;
    }

    public String getSfdd() {
        return sfdd;
    }

    public void setSfdd(String sfdd) {
        this.sfdd = sfdd;
    }

    public String getGzxm() {
        return gzxm;
    }

    public void setGzxm(String gzxm) {
        this.gzxm = gzxm;
    }

    public String getBsmc() {
        return bsmc;
    }

    public void setBsmc(String bsmc) {
        this.bsmc = bsmc;
    }

    public String getBsgm() {
        return bsgm;
    }

    public void setBsgm(String bsgm) {
        this.bsgm = bsgm;
    }

    public String getFensu() {
        return fensu;
    }

    public void setFensu(String fensu) {
        this.fensu = fensu;
    }

    public String getZdfensu() {
        return zdfensu;
    }

    public void setZdfensu(String zdfensu) {
        this.zdfensu = zdfensu;
    }

    public String getBsrq() {
        return bsrq;
    }

    public void setBsrq(String bsrq) {
        this.bsrq = bsrq;
    }

    public String getXmmc() {
        return xmmc;
    }

    public void setXmmc(String xmmc) {
        this.xmmc = xmmc;
    }

    public String getSjly() {
        return sjly;
    }

    public void setSjly(String sjly) {
        this.sjly = sjly;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeString(this.id);
        dest.writeString(this.sfkj);
        dest.writeString(this.sfdd);
        dest.writeString(this.gzxm);
        dest.writeString(this.bsmc);
        dest.writeString(this.bsgm);
        dest.writeString(this.fensu);
        dest.writeString(this.zdfensu);
        dest.writeString(this.bsrq);
        dest.writeString(this.xmmc);
        dest.writeString(this.sjly);
    }

    public PlayImportListEntity() {
    }

    protected PlayImportListEntity(Parcel in) {
        super(in);
        this.id = in.readString();
        this.sfkj = in.readString();
        this.sfdd = in.readString();
        this.gzxm = in.readString();
        this.bsmc = in.readString();
        this.bsgm = in.readString();
        this.fensu = in.readString();
        this.zdfensu = in.readString();
        this.bsrq = in.readString();
        this.xmmc = in.readString();
        this.sjly = in.readString();
    }

    public static final Creator<PlayImportListEntity> CREATOR = new Creator<PlayImportListEntity>() {
        @Override
        public PlayImportListEntity createFromParcel(Parcel source) {
            return new PlayImportListEntity(source);
        }

        @Override
        public PlayImportListEntity[] newArray(int size) {
            return new PlayImportListEntity[size];
        }
    };
}
