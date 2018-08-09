package com.cpigeon.book.model;

import com.base.http.ApiResponse;
import com.cpigeon.book.R;
import com.cpigeon.book.http.RequestData;
import com.cpigeon.book.model.entity.PigeonHouseEntity;
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
            , String county
            , String city
            , String PigeonISOCID
            , String PigeonHomeAdds
            , String PigeonMatchNum) {
        return RequestData.<ApiResponse>build()
                .setToJsonType(new TypeToken<ApiResponse>() {
                }.getType())
                .url(R.string.add_pigeon_house)
                .addBody("name", PigeonHomeName)
                .addBody("usenum", UsePigeonHomeNum)
                .addBody("phone", PigeonHomePhone)
                .addBody("la", Latitude)
                .addBody("lo", Longitude)
                .addBody("province", Province)
                .addBody("county", county)
                .addBody("city", city)
                .addBody("isocid", PigeonISOCID)
                .addBody("adds", PigeonHomeAdds)
                .addBody("matchnum", PigeonMatchNum)
                .request();
    }

    public static Observable<ApiResponse<PigeonHouseEntity>> getPigeonHouse() {
        return RequestData.<ApiResponse<PigeonHouseEntity>>build()
                .setToJsonType(new TypeToken<ApiResponse<PigeonHouseEntity>>() {
                }.getType())
                .url(R.string.get_pigeon_house)
                .request();
    }
}
