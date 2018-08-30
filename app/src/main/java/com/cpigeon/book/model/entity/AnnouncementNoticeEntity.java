package com.cpigeon.book.model.entity;

/**
 * 公告通知
 * Created by Administrator on 2018/8/9.
 */

public class AnnouncementNoticeEntity {
    /**
     * id : 42
     * isread : 1
     * content : <p>
     尊敬的鸽友，您好！
     </p>
     <p>
     &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;中秋佳节即将来临，中鸽网全体同仁祝全国鸽友节日快乐，阖家安康!
     </p>
     <p>
     &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;根据国家相关部门关于2016年中秋节假期安排的通知，结合公司的实际情况，现就2016年中秋节放假通知如下：9月15日（星期四）起至9月17日（星期六）放假，共3天。9月18日（星期日）起正常上班。
     </p>
     <p>
     &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;如果您在假期遇到网站问题，请拨打值班电话：13730873310
     </p>
     * title : 2016年中秋放假通知
     * source : 网站公告
     * date : 2016-09-14 11:16:54
     */

    private String id;
    private String isread;
    private String content;
    private String title;
    private String source;
    private String date;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIsread() {
        return isread;
    }

    public void setIsread(String isread) {
        this.isread = isread;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

//    {
//        "id": "42",
//            "isread": "1",
//            "content": "\u003cp\u003e\r\n\t尊敬的鸽友，您好！\r\n\u003c/p\u003e\r\n\u003cp\u003e\r\n\t\u0026nbsp;\u0026nbsp;\u0026nbsp;\u0026nbsp;\u0026nbsp;\u0026nbsp;\u0026nbsp;中秋佳节即将来临，中鸽网全体同仁祝全国鸽友节日快乐，阖家安康!\r\n\u003c/p\u003e\r\n\u003cp\u003e\r\n\t\u0026nbsp;\u0026nbsp;\u0026nbsp;\u0026nbsp;\u0026nbsp;\u0026nbsp;\u0026nbsp;根据国家相关部门关于2016年中秋节假期安排的通知，结合公司的实际情况，现就2016年中秋节放假通知如下：9月15日（星期四）起至9月17日（星期六）放假，共3天。9月18日（星期日）起正常上班。\r\n\u003c/p\u003e\r\n\u003cp\u003e\r\n\t\u0026nbsp;\u0026nbsp;\u0026nbsp;\u0026nbsp;\u0026nbsp;\u0026nbsp;\u0026nbsp;如果您在假期遇到网站问题，请拨打值班电话：13730873310\r\n\u003c/p\u003e",
//            "title": "2016年中秋放假通知 ",
//            "source": "网站公告",
//            "date": "2016-09-14 11:16:54"
//    }



}
