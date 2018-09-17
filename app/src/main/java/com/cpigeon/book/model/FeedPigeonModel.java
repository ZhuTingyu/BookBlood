package com.cpigeon.book.model;

import com.base.http.ApiResponse;
import com.cpigeon.book.R;
import com.cpigeon.book.http.RequestData;
import com.cpigeon.book.model.entity.FeedPigeonEntity;
import com.cpigeon.book.model.entity.FeedPigeonStatistical;
import com.cpigeon.book.model.entity.PairingNestInfoEntity;
import com.google.gson.reflect.TypeToken;

import java.util.List;

import io.reactivex.Observable;

/**
 * Created by Administrator on 2018/9/17 0017.
 */

public class FeedPigeonModel {

    //hl 获取 养鸽记录  列表
    public static Observable<ApiResponse<List<FeedPigeonEntity>>> getTXGP_Pigeon_SelectRecord(String pi,
                                                                                              String ps,
                                                                                              String footid,
                                                                                              String pigeonid) {
        return RequestData.<ApiResponse<List<FeedPigeonEntity>>>build()
                .setToJsonType(new TypeToken<ApiResponse<List<FeedPigeonEntity>>>() {
                }.getType())
                .url(R.string.feed_pigeon_list)
                .addBody("pi", pi)//
                .addBody("ps", ps)//
                .addBody("footid", footid)//
                .addBody("pigeonid", pigeonid)//
                .request();
    }

    //hl 养鸽记录 统计
    public static Observable<ApiResponse<FeedPigeonStatistical>> getTXGP_Pigeon_SelectIDCount(String footid,
                                                                                              String pigeonid) {
        return RequestData.<ApiResponse<FeedPigeonStatistical>>build()
                .setToJsonType(new TypeToken<ApiResponse<FeedPigeonStatistical>>() {
                }.getType())
                .url(R.string.feed_pigeon_statistical)
                .addBody("footid", footid)//
                .addBody("pigeonid", pigeonid)//
                .request();
    }
}
