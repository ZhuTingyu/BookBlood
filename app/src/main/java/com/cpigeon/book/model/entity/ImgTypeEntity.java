package com.cpigeon.book.model.entity;

import java.io.Serializable;

/**
 * Created by Administrator on 2018/8/29.
 */

public class ImgTypeEntity implements Serializable {

    public static final String TYPE_NEF = "随拍";
    private String imgPath = "";//图片路径
    private String imgType = "";//图片类型
    private String imgTypeId = "";//图片类型
    private String imgRemark = "";//备注
    private String imgTypeSpecified = "";//指定图片类型
    private String pigeonId = "";//信鸽id
    private String foootId = "";//足环id

    private ImgTypeEntity(Builder builder) {
        setImgPath(builder.imgPath);
        setImgType(builder.imgType);
        setImgTypeId(builder.imgTypeId);
        setImgRemark(builder.imgRemark);
        setImgTypeSpecified(builder.imgTypeSpecified);
        setPigeonId(builder.pigeonId);
        setFoootId(builder.foootId);
    }


    public String getImgPath() {
        return imgPath;
    }

    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
    }

    public String getImgType() {
        return imgType;
    }

    public void setImgType(String imgType) {
        this.imgType = imgType;
    }

    public String getImgTypeId() {
        return imgTypeId;
    }

    public void setImgTypeId(String imgTypeId) {
        this.imgTypeId = imgTypeId;
    }

    public String getImgRemark() {
        return imgRemark;
    }

    public void setImgRemark(String imgRemark) {
        this.imgRemark = imgRemark;
    }

    public String getImgTypeSpecified() {
        return imgTypeSpecified;
    }

    public void setImgTypeSpecified(String imgTypeSpecified) {
        this.imgTypeSpecified = imgTypeSpecified;
    }

    public String getPigeonId() {
        return pigeonId;
    }

    public void setPigeonId(String pigeonId) {
        this.pigeonId = pigeonId;
    }

    public String getFoootId() {
        return foootId;
    }

    public void setFoootId(String foootId) {
        this.foootId = foootId;
    }

    public static final class Builder {
        private String imgPath;
        private String imgType;
        private String imgTypeId;
        private String imgRemark;
        private String imgTypeSpecified;
        private String pigeonId;
        private String foootId;

        public Builder() {
        }

        public Builder imgPath(String val) {
            imgPath = val;
            return this;
        }

        public Builder imgType(String val) {
            imgType = val;
            return this;
        }

        public Builder imgTypeId(String val) {
            imgTypeId = val;
            return this;
        }

        public Builder imgRemark(String val) {
            imgRemark = val;
            return this;
        }

        public Builder imgTypeSpecified(String val) {
            imgTypeSpecified = val;
            return this;
        }

        public Builder pigeonId(String val) {
            pigeonId = val;
            return this;
        }

        public Builder foootId(String val) {
            foootId = val;
            return this;
        }

        public ImgTypeEntity build() {
            return new ImgTypeEntity(this);
        }
    }
}
