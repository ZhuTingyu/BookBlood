package com.cpigeon.book.model;

import com.base.http.ApiResponse;
import com.cpigeon.book.R;
import com.cpigeon.book.http.RequestData;
import com.google.gson.reflect.TypeToken;

import io.reactivex.Observable;

/**
 * Created by Zhu TingYu on 2018/8/6.
 */

public class PigeonHouseModel {
    public static Observable<ApiResponse> addPigeonHouse(String PigeonHomeName
            , String UsePigeonHomeNum
            , String PigeonHomePhone
            , String Latitude
            , String Longitude
            , String Province
            , String PigeonISOCID
            , String PigeonHomeAdds
            , String PigeonMatchNum) {
        return RequestData.<ApiResponse>build()
                .setToJsonType(new TypeToken<ApiResponse>() {
                }.getType())
                .url(R.string.add_pigeon_house)
                .addBody("PigeonHomeName", PigeonHomeName)
                .addBody("UsePigeonHomeNum", UsePigeonHomeNum)
                .addBody("PigeonHomePhone", PigeonHomePhone)
                .addBody("Latitude", Latitude)
                .addBody("Longitude", Longitude)
                .addBody("Province", Province)
                .addBody("PigeonISOCID", PigeonISOCID)
                .addBody("PigeonHomeAdds", PigeonHomeAdds)
                .addBody("PigeonMatchNum", PigeonMatchNum)
                .request();
    }
}
