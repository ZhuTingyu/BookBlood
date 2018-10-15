package com.cpigeon.book.model;

import com.base.http.ApiResponse;
import com.cpigeon.book.R;
import com.cpigeon.book.http.RequestData;
import com.cpigeon.book.model.entity.HomeTopEntity;
import com.cpigeon.book.model.entity.PigeonEntity;
import com.google.gson.reflect.TypeToken;

import java.util.List;

import io.reactivex.Observable;

/**
 * Created by Zhu TingYu on 2018/10/15.
 */

public class CountModel {
    public static Observable<ApiResponse<List<HomeTopEntity>>> getHomeTop() {
        return RequestData.<ApiResponse<List<HomeTopEntity>>>build()
                .setToJsonType(new TypeToken<ApiResponse<List<HomeTopEntity>>>() {
                }.getType())
                .url(R.string.get_home_count)
                .request();
    }
}
