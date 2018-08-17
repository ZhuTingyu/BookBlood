package com.cpigeon.book.model.entity;

/**
 * 公告通知
 * Created by Administrator on 2018/8/9.
 */

public class AnnouncementNoticeEntity {

//    {
//        "source": "网站公告",
//            "date": "2016-09-30 18:40:43",
//            "title": "2016年国庆节放假通知",
//            "content": "\u003cdiv class\u003d\"note1\"\u003e\r\n\t\u003cp\u003e\r\n\t\t尊敬的鸽友，您好！\r\n\t\u003c/p\u003e\r\n\t\u003cp\u003e\r\n\t\t\u0026nbsp;\u0026nbsp;\u0026nbsp;\u0026nbsp;\u0026nbsp;\u0026nbsp;\u0026nbsp;国庆节即将来临，中鸽网全体同仁祝全国鸽友节日快乐，阖家安康!\r\n\t\u003c/p\u003e\r\n\t\u003cp\u003e\r\n\t\t\u0026nbsp;\u0026nbsp;\u0026nbsp;\u0026nbsp;\u0026nbsp;\u0026nbsp;\u0026nbsp;根据国家相关部门关于2016年国庆节假期安排的通知，结合公司的实际情况，现就2016年国庆节放假通知如下：10月1日（星期六）起至10月7日（星期五）放假，共7天。10月8日（星期六）起正常上班。\r\n\t\u003c/p\u003e\r\n\t\u003cp\u003e\r\n\t\t\u0026nbsp;\u0026nbsp;\u0026nbsp;\u0026nbsp;\u0026nbsp;\u0026nbsp;\u0026nbsp;如果您在假期遇到网站问题，请拨打值班电话：13730873310\r\n\t\u003c/p\u003e\r\n\u003c/div\u003e",
//            "id": "43"
//    }

    /**
     * source : 网站公告
     * date : 2016-09-30 18:40:43
     * title : 2016年国庆节放假通知
     * content : <div class="note1">
     <p>
     尊敬的鸽友，您好！
     </p>
     <p>
     &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;国庆节即将来临，中鸽网全体同仁祝全国鸽友节日快乐，阖家安康!
     </p>
     <p>
     &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;根据国家相关部门关于2016年国庆节假期安排的通知，结合公司的实际情况，现就2016年国庆节放假通知如下：10月1日（星期六）起至10月7日（星期五）放假，共7天。10月8日（星期六）起正常上班。
     </p>
     <p>
     &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;如果您在假期遇到网站问题，请拨打值班电话：13730873310
     </p>
     </div>
     * id : 43
     */

    private String source;
    private String date;
    private String title;
    private String content;
    private String id;

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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
