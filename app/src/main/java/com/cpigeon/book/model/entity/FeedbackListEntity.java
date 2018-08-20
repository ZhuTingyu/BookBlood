package com.cpigeon.book.model.entity;

/**
 * Created by Administrator on 2018/8/9.
 */

public class FeedbackListEntity {


    /**
     * state : 0
     * content : 测试反馈2
     * datetime : 2018-08-20 14:37:46
     * id : 5
     */

    private String state;
    private String content;
    private String datetime;
    private String id;

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getDatetime() {
        return datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
