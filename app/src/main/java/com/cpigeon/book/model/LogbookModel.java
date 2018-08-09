package com.cpigeon.book.model;

import com.base.http.ApiResponse;
import com.cpigeon.book.R;
import com.cpigeon.book.http.RequestData;
import com.cpigeon.book.model.entity.LogbookEntity;
import com.google.gson.reflect.TypeToken;

import java.util.List;

import io.reactivex.Observable;

/**
 * Created by Administrator on 2018/8/8.
 */

public class LogbookModel {


    //hl 获取操作日志
    public static Observable<ApiResponse<List<LogbookEntity>>> getZGW_Users_GetLog(int pi, int ps) {
        return RequestData.<ApiResponse<List<LogbookEntity>>>build()
                .setToJsonType(new TypeToken<ApiResponse<List<LogbookEntity>>>() {
                }.getType())
                .url(R.string.user_log_book)
                .addBody("pi", String.valueOf(pi))
                .addBody("ps", String.valueOf(ps))
                .request();
    }

}
