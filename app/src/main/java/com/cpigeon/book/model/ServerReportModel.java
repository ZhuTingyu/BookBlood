package com.cpigeon.book.model;

import com.base.http.ApiResponse;
import com.cpigeon.book.R;
import com.cpigeon.book.http.RequestData;
import com.cpigeon.book.model.entity.AnnouncementNoticeEntity;
import com.cpigeon.book.model.entity.ServerReportEntity;
import com.google.gson.reflect.TypeToken;

import java.util.List;

import io.reactivex.Observable;

/**
 * Created by Administrator on 2018/9/14 0014.
 */

public class ServerReportModel {

    //hl 服务开通续费纪录
    public static Observable<ApiResponse<List<ServerReportEntity>>> getTXGP_GetServiceList(int pi, int ps) {
        return RequestData.<ApiResponse<List<ServerReportEntity>>>build()
                .setToJsonType(new TypeToken<ApiResponse<List<ServerReportEntity>>>() {
                }.getType())
                .url(R.string.get_server_record)
                .addBody("pi", String.valueOf(pi))
                .addBody("ps", String.valueOf(ps))
                .request();
    }

}
