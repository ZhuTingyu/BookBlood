package com.cpigeon.book.model;

import com.base.http.ApiResponse;
import com.cpigeon.book.R;
import com.cpigeon.book.http.RequestData;
import com.cpigeon.book.model.entity.FeedPigeonEntity;
import com.cpigeon.book.model.entity.GrowthReportEntity;
import com.google.gson.reflect.TypeToken;

import java.util.List;

import io.reactivex.Observable;

/**
 * Created by Administrator on 2018/9/27 0027.
 */

public class GrowthReportModel {


    //hl 获取 成长记录  列表
    public static Observable<ApiResponse<List<GrowthReportEntity>>> getTXGP_Pigeon_SelectGrowAll(String pi,
                                                                                                 String ps,
                                                                                                 String footid,
                                                                                                 String pigeonid) {
        return RequestData.<ApiResponse<List<GrowthReportEntity>>>build()
                .setToJsonType(new TypeToken<ApiResponse<List<GrowthReportEntity>>>() {
                }.getType())
                .url(R.string.pigeon_growth_record)
                .addBody("pi", pi)//
                .addBody("ps", ps)//
                .addBody("footid", footid)//
                .addBody("pigeonid", pigeonid)//
                .request();
    }

}
