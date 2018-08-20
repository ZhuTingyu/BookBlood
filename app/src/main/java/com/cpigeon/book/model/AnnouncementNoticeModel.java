package com.cpigeon.book.model;

import com.base.http.ApiResponse;
import com.cpigeon.book.R;
import com.cpigeon.book.http.RequestData;
import com.cpigeon.book.model.entity.MsgCountEntity;
import com.cpigeon.book.model.entity.AnnouncementNoticeEntity;
import com.google.gson.reflect.TypeToken;

import java.util.List;

import io.reactivex.Observable;

/**
 * Created by Administrator on 2018/8/9.
 */

public class AnnouncementNoticeModel {


    //hl 公告通知列表
    public static Observable<ApiResponse<List<AnnouncementNoticeEntity>>> getTXGP_GetGongGao(int pi, int ps) {
        return RequestData.<ApiResponse<List<AnnouncementNoticeEntity>>>build()
                .setToJsonType(new TypeToken<ApiResponse<List<AnnouncementNoticeEntity>>>() {
                }.getType())
                .url(R.string.get_announcement_notice_list)
                .addBody("pi", String.valueOf(pi))
                .addBody("ps", String.valueOf(ps))
                .request();
    }


    //hl 公告通知统计
    public static Observable<ApiResponse<MsgCountEntity>> getTXGP_GongGao_Count() {
        return RequestData.<ApiResponse<MsgCountEntity>>build()
                .setToJsonType(new TypeToken<ApiResponse<MsgCountEntity>>() {
                }.getType())
                .url(R.string.get_announcement_notice_count)
                .request();
    }


    //hl 公告通知详情
    public static Observable<ApiResponse<MsgCountEntity>> getTXGP_GongGao_Detail(String id) {
        return RequestData.<ApiResponse<MsgCountEntity>>build()
                .setToJsonType(new TypeToken<ApiResponse<MsgCountEntity>>() {
                }.getType())
                .url(R.string.get_announcement_notice_detail)
                .addBody("id", id)
                .request();
    }
}
