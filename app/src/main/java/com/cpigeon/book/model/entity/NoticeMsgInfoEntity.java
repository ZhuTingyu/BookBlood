package com.cpigeon.book.model.entity;

/**
 * Created by Administrator on 2018/10/11 0011.
 */

public class NoticeMsgInfoEntity {


    /**
     * state : 0为有未读消息
     * title : 标题
     * type : gg
     * datetime : 公告通知时间
     */

    private String state;
    private String title;
    private String type;
    private String datetime;

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDatetime() {
        return datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }
}
