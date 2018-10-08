package com.cpigeon.book.model;

import com.base.http.ApiResponse;
import com.cpigeon.book.R;
import com.cpigeon.book.http.RequestData;
import com.cpigeon.book.model.entity.GoodPigeonCountEntity;
import com.cpigeon.book.model.entity.PigeonEntity;
import com.google.gson.reflect.TypeToken;

import java.util.List;

import io.reactivex.Observable;

/**
 * Created by Zhu TingYu on 2018/9/27.
 */

public class GoodPigeonModel {
    public static Observable<ApiResponse<List<PigeonEntity>>> getGoodPigeon(int type, int pi, String footNumber) {
        return RequestData.<ApiResponse<List<PigeonEntity>>>build()
                .setToJsonType(new TypeToken<ApiResponse<List<PigeonEntity>>>() {
                }.getType())
                .url(R.string.get_good_pigeon)
                .addBody("type", String.valueOf(type))
                .addBody("pi", String.valueOf(pi))
                .addBody("ps", String.valueOf(20))
                .addBody("footnum", footNumber)
                .request();
    }

    public static Observable<ApiResponse<GoodPigeonCountEntity>> getGoodPigeonCount() {
        return RequestData.<ApiResponse<GoodPigeonCountEntity>>build()
                .setToJsonType(new TypeToken<ApiResponse<GoodPigeonCountEntity>>() {
                }.getType())
                .url(R.string.get_good_pigeon_count)
                .request();
    }

    public static Observable<ApiResponse> applyAddGoodPigeon(String foodId, String pigeonId, String breedName, String flyName) {
        return RequestData.<ApiResponse>build()
                .setToJsonType(new TypeToken<ApiResponse>() {
                }.getType())
                .url(R.string.apply_add_good_pigeon)
                .addBody("pigeonid", pigeonId)
                .addBody("footid", foodId)
                .addBody("breedname", breedName)
                .addBody("flyname", flyName)
                .request();
    }
}
