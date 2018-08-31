package com.cpigeon.book.model.entity;

import java.io.Serializable;

/**
 * Created by Administrator on 2018/8/29.
 */

public class ImgTypeEntity implements Serializable{

    private String imgPath;
    private String imgType;
    private String imgTypeId;
    private String imgRemark;


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

    private ImgTypeEntity(Builder builder) {
        imgPath = builder.imgPath;
        imgType = builder.imgType;
        imgTypeId = builder.imgTypeId;
        imgRemark = builder.imgRemark;
    }


    public static final class Builder {
        private String imgPath;
        private String imgType;
        private String imgTypeId;
        private String imgRemark;

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

        public ImgTypeEntity build() {
            return new ImgTypeEntity(this);
        }
    }
}
