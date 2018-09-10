package com.cpigeon.book.model.entity;

/**
 * 繁育信息
 * Created by Administrator on 2018/9/10.
 */

public class BreedingInfoEntity {

    private String   info ;

    private BreedingInfoEntity(Builder builder) {
        setInfo(builder.info);
    }


    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public static final class Builder {
        private String info;

        public Builder() {
        }

        public Builder info(String val) {
            info = val;
            return this;
        }

        public BreedingInfoEntity build() {
            return new BreedingInfoEntity(this);
        }
    }
}
