package com.cpigeon.book.model.entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Zhu TingYu on 2018/9/29.
 */

public class ServiceEntity implements Parcelable {


    /**
     * score : 1680000
     * num : 1
     * sname : 打印血统书开通
     * danwei : 年
     * sid : 5
     * price : 168.00
     * sintro : 天下鸽谱天下鸽谱
     */

    private String score;
    private String num;
    private String sname;
    private String danwei;
    private String sid;
    private String price;
    private String sintro;
    private String imgurl;
    private String expiredate;

    public String getExpiredate() {
        return expiredate;
    }

    public void setExpiredate(String expiredate) {
        this.expiredate = expiredate;
    }

    private ServiceEntity(Builder builder) {
        setScore(builder.score);
        setNum(builder.num);
        setSname(builder.sname);
        setDanwei(builder.danwei);
        setSid(builder.sid);
        setPrice(builder.price);
        setSintro(builder.sintro);
        setImgurl(builder.imgurl);
    }


    public String getImgurl() {
        return imgurl;
    }

    public void setImgurl(String imgurl) {
        this.imgurl = imgurl;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public String getSname() {
        return sname;
    }

    public void setSname(String sname) {
        this.sname = sname;
    }

    public String getDanwei() {
        return danwei;
    }

    public void setDanwei(String danwei) {
        this.danwei = danwei;
    }

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getSintro() {
        return sintro;
    }

    public void setSintro(String sintro) {
        this.sintro = sintro;
    }

    public ServiceEntity() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.score);
        dest.writeString(this.num);
        dest.writeString(this.sname);
        dest.writeString(this.danwei);
        dest.writeString(this.sid);
        dest.writeString(this.price);
        dest.writeString(this.sintro);
        dest.writeString(this.imgurl);
    }

    protected ServiceEntity(Parcel in) {
        this.score = in.readString();
        this.num = in.readString();
        this.sname = in.readString();
        this.danwei = in.readString();
        this.sid = in.readString();
        this.price = in.readString();
        this.sintro = in.readString();
        this.imgurl = in.readString();
    }

    public static final Creator<ServiceEntity> CREATOR = new Creator<ServiceEntity>() {
        @Override
        public ServiceEntity createFromParcel(Parcel source) {
            return new ServiceEntity(source);
        }

        @Override
        public ServiceEntity[] newArray(int size) {
            return new ServiceEntity[size];
        }
    };

    public static final class Builder {
        private String score;
        private String num;
        private String sname;
        private String danwei;
        private String sid;
        private String price;
        private String sintro;
        private String imgurl;

        public Builder() {
        }

        public Builder score(String val) {
            score = val;
            return this;
        }

        public Builder num(String val) {
            num = val;
            return this;
        }

        public Builder sname(String val) {
            sname = val;
            return this;
        }

        public Builder danwei(String val) {
            danwei = val;
            return this;
        }

        public Builder sid(String val) {
            sid = val;
            return this;
        }

        public Builder price(String val) {
            price = val;
            return this;
        }

        public Builder sintro(String val) {
            sintro = val;
            return this;
        }

        public Builder imgurl(String val) {
            imgurl = val;
            return this;
        }

        public ServiceEntity build() {
            return new ServiceEntity(this);
        }
    }
}
