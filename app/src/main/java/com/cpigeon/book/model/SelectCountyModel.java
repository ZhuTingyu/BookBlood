package com.cpigeon.book.model;

import com.base.http.ApiResponse;
import com.cpigeon.book.R;
import com.cpigeon.book.http.RequestData;
import com.cpigeon.book.model.entity.CountyAreaEntity;
import com.cpigeon.book.model.entity.CountyEntity;
import com.google.gson.reflect.TypeToken;

import java.util.List;

import io.reactivex.Observable;

/**
 * Created by Zhu TingYu on 2018/8/20.
 */

public class SelectCountyModel {

    public static Observable<ApiResponse<List<CountyEntity>>> getCountyList(String key) {
        return RequestData.<ApiResponse<List<CountyEntity>>>build()
                .setToJsonType(new TypeToken<ApiResponse<List<CountyEntity>>>() {
                }.getType())
                .url(R.string.get_county_list)
                .addBody("key", key)
                .request();
    }

    public static Observable<ApiResponse<List<CountyAreaEntity>>> getAreaList(String id) {
        return RequestData.<ApiResponse<List<CountyAreaEntity>>>build()
                .setToJsonType(new TypeToken<ApiResponse<List<CountyAreaEntity>>>() {
                }.getType())
                .url(R.string.get_county_area_list)
                .addBody("sort",id)
                .request();
    }


}
