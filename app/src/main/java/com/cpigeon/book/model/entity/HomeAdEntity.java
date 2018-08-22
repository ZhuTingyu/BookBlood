package com.cpigeon.book.model.entity;

/**
 * Created by Zhu TingYu on 2018/8/22.
 */

public class HomeAdEntity {

    /**
     * id : 2
     * enable : true
     * start : 2016-11-11 00:00:00
     * end : 2099-12-21 10:14:35
     * type : 6
     * adUrl : http://www.cpigeon.com/
     * adImageUrl : http://www.cpigeon.com/uploadfiles/ad/appad/20180807174509.png
     */

    private String id;
    private boolean enable;
    private String start;
    private String end;
    private String type;
    private String adUrl;
    private String adImageUrl;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public boolean isEnable() {
        return enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getAdUrl() {
        return adUrl;
    }

    public void setAdUrl(String adUrl) {
        this.adUrl = adUrl;
    }

    public String getAdImageUrl() {
        return adImageUrl;
    }

    public void setAdImageUrl(String adImageUrl) {
        this.adImageUrl = adImageUrl;
    }
}
