package com.cpigeon.book.model;

import com.base.http.ApiResponse;
import com.cpigeon.book.R;
import com.cpigeon.book.http.RequestData;
import com.cpigeon.book.model.entity.AssEntity;
import com.cpigeon.book.model.entity.HomeAdEntity;
import com.google.gson.reflect.TypeToken;

import java.util.List;

import io.reactivex.Observable;

/**
 * Created by Zhu TingYu on 2018/8/22.
 */

public class AdModel {
    public static Observable<ApiResponse<HomeAdEntity>> getHomeAd() {
        return RequestData.<ApiResponse<HomeAdEntity>>build()
                .setToJsonType(new TypeToken<ApiResponse<HomeAdEntity>>() {
                }.getType())
                .url(R.string.get_home_ad)
                .request();
    }
}
