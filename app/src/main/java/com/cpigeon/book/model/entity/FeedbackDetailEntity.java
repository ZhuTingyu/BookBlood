package com.cpigeon.book.model.entity;

import com.base.util.utility.StringUtil;
import com.chad.library.adapter.base.entity.MultiItemEntity;

import java.util.List;

/**
 * Created by Zhu TingYu on 2018/8/23.
 */

public class FeedbackDetailEntity implements MultiItemEntity {

    public static final int TYPE_FEEDBACK = 0;
    public static final int TYPE_REPLY = 1;

    /**
     * state : 0未处理，1已处理，2已回复
     * content : test
     * datetime : 2018-08-10 11:42:30
     * id : 1
     * imglist : [{"imgurl":"图片地址"}]
     * replycontent : 回复内容
     */

    private String state;
    private String content;
    private String datetime;
    private String id;
    private String replycontent;
    private String replytime;
    private List<ImageEntity> imglist;

    private int itemType;

    public String getReplytime() {
        return replytime;
    }

    public void setReplytime(String replytime) {
        this.replytime = replytime;
    }

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

    public String getReplycontent() {
        return replycontent;
    }

    public void setReplycontent(String replycontent) {
        this.replycontent = replycontent;
    }

    public List<ImageEntity> getImglist() {
        return imglist;
    }

    public void setImglist(List<ImageEntity> imglist) {
        this.imglist = imglist;
    }

    @Override
    public int getItemType() {
      return  StringUtil.isStringValid(replycontent) ? TYPE_REPLY : TYPE_FEEDBACK;
    }
}
