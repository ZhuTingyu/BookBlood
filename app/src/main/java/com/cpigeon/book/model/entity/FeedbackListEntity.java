package com.cpigeon.book.model.entity;

import com.base.util.Utils;
import com.cpigeon.book.R;

/**
 * Created by Administrator on 2018/8/9.
 */

public class FeedbackListEntity {


    /**
     * state : 0未处理，1已处理，2已回复
     * content : 测试反馈2
     * datetime : 2018-08-20 14:37:46
     * id : 5
     */

    private int state;
    private String content;
    private String datetime;
    private String id;

    public int getState() {
        return state;
    }

    public void setState(int state) {
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

    public String getStatusName(){
        String name = null;
        if(state == 0){
            name = Utils.getString(R.string.text_not_handle);
        }else if(state == 1){
            name = Utils.getString(R.string.text_handled);
        }else if(state == 2){
            name = Utils.getString(R.string.text_not_reply);
        }
        return name;
    }
}
