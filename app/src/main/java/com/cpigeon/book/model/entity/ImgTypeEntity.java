package com.cpigeon.book.model.entity;

import java.io.Serializable;

/**
 * Created by Administrator on 2018/8/29.
 */

public class ImgTypeEntity implements Serializable{

    private String imgPath;
    private String imgType;
    private String imgTypeId;


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

    private ImgTypeEntity(Builder builder) {
        imgPath = builder.imgPath;
        imgType = builder.imgType;
        imgTypeId = builder.imgTypeId;
    }


    public static final class Builder implements Serializable{
        private String imgPath;
        private String imgType;
        private String imgTypeId;

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

        public ImgTypeEntity build() {
            return new ImgTypeEntity(this);
        }
    }
}
