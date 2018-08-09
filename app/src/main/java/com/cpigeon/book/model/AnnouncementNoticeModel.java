package com.cpigeon.book.model;

import com.base.http.ApiResponse;
import com.cpigeon.book.R;
import com.cpigeon.book.http.RequestData;
import com.cpigeon.book.model.entity.AnnouncementNoticeEntity;
import com.google.gson.reflect.TypeToken;

import java.util.List;

import io.reactivex.Observable;

/**
 * Created by Administrator on 2018/8/9.
 */

public class AnnouncementNoticeModel {


    //hl 公告通知
    public static Observable<ApiResponse<List<AnnouncementNoticeEntity>>> getTXGP_GetGongGao(int pi, int ps) {
        return RequestData.<ApiResponse<List<AnnouncementNoticeEntity>>>build()
                .setToJsonType(new TypeToken<ApiResponse<List<AnnouncementNoticeEntity>>>() {
                }.getType())
                .url(R.string.get_announcement_notice)
                .addBody("pi", String.valueOf(pi))
                .addBody("ps", String.valueOf(ps))
                .request();
    }
}
