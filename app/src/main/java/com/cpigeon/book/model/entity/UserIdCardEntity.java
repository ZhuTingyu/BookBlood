package com.cpigeon.book.model.entity;

import java.util.List;

/**
 * Created by Zhu TingYu on 2018/8/9.
 */

public class UserIdCardEntity {

    /**
     * xingbie : 性别
     * youxiaoqi : 有效期
     * qfjg : 签发机关
     * sfzzm : ["身份证正面图片文件数组"]
     * sfzid : 索引ID
     * sfzhm : 身份证号码
     * minzu : 民族
     * zhuzhi : 住址
     * sfzbm : ["身份证背面图片文件数组"]
     * xingming : 姓名
     */

    private String xingbie;
    private String youxiaoqi;
    private String qfjg;
    private String sfzid;
    private String sfzhm;
    private String minzu;
    private String zhuzhi;
    private String xingming;
    private byte[] sfzzm;
    private byte[] sfzbm;

    public String getXingbie() {
        return xingbie;
    }

    public void setXingbie(String xingbie) {
        this.xingbie = xingbie;
    }

    public String getYouxiaoqi() {
        return youxiaoqi;
    }

    public void setYouxiaoqi(String youxiaoqi) {
        this.youxiaoqi = youxiaoqi;
    }

    public String getQfjg() {
        return qfjg;
    }

    public void setQfjg(String qfjg) {
        this.qfjg = qfjg;
    }

    public String getSfzid() {
        return sfzid;
    }

    public void setSfzid(String sfzid) {
        this.sfzid = sfzid;
    }

    public String getSfzhm() {
        return sfzhm;
    }

    public void setSfzhm(String sfzhm) {
        this.sfzhm = sfzhm;
    }

    public String getMinzu() {
        return minzu;
    }

    public void setMinzu(String minzu) {
        this.minzu = minzu;
    }

    public String getZhuzhi() {
        return zhuzhi;
    }

    public void setZhuzhi(String zhuzhi) {
        this.zhuzhi = zhuzhi;
    }

    public String getXingming() {
        return xingming;
    }

    public void setXingming(String xingming) {
        this.xingming = xingming;
    }

    public byte[] getSfzzm() {
        return sfzzm;
    }

    public void setSfzzm(byte[] sfzzm) {
        this.sfzzm = sfzzm;
    }

    public byte[] getSfzbm() {
        return sfzbm;
    }

    public void setSfzbm(byte[] sfzbm) {
        this.sfzbm = sfzbm;
    }
}
