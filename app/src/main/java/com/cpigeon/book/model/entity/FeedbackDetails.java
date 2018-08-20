package com.cpigeon.book.model.entity;

import com.base.util.http.GsonUtil;

import java.util.List;

/**
 * Created by Administrator on 2018/8/20.
 */

public class FeedbackDetails {


    /**
     * id : 11
     * imglist : [{"imgurl":"http://192.168.0.18:8188/uploadfiles/feedback/picture/59249_9f8d04597f3319d20820fc67dd69731a.jpg"},{"imgurl":"http://192.168.0.18:8188/uploadfiles/feedback/picture/61844_d5c2b072f868601fcefa5c0fdb54c282.jpg"},{"imgurl":"http://192.168.0.18:8188/uploadfiles/feedback/picture/194747_ee7e466a88268f80828e4fc8c3f9d413.png"},{"imgurl":"http://192.168.0.18:8188/uploadfiles/feedback/picture/57661_4b7371e6346f830504f185680fda94ec.jpg"},{"imgurl":"http://192.168.0.18:8188/uploadfiles/feedback/picture/216725_730ac0f65c2b604aca2c2655a43ae636.jpg"},{"imgurl":"http://192.168.0.18:8188/uploadfiles/feedback/picture/121141_b7007d4a3fca4d4ccb8195cf7d62a780.jpg"}]
     * replycontent :
     * content : 多张图片
     * state : 0
     * datetime : 2018-08-20 15:56:21
     */

    private String id;
    private String replycontent;
    private String content;
    private String state;
    private String datetime;
    private List<ImglistBean> imglist;

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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getDatetime() {
        return datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }

    public List<ImglistBean> getImglist() {
        return imglist;
    }

    public void setImglist(List<ImglistBean> imglist) {
        this.imglist = imglist;
    }

    public static class ImglistBean {
        /**
         * imgurl : http://192.168.0.18:8188/uploadfiles/feedback/picture/59249_9f8d04597f3319d20820fc67dd69731a.jpg
         */

        private String imgurl;

        public String getImgurl() {
            return imgurl;
        }

        public void setImgurl(String imgurl) {
            this.imgurl = imgurl;
        }
    }

    public String toJsonString() {
        return GsonUtil.toJson(this);
    }
}
