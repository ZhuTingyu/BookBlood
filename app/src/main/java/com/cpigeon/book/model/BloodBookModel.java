package com.cpigeon.book.model;

import com.base.http.ApiResponse;
import com.cpigeon.book.R;
import com.cpigeon.book.http.RequestData;
import com.cpigeon.book.model.entity.BloodBookEntity;
import com.google.gson.reflect.TypeToken;

import io.reactivex.Observable;

/**
 * Created by Zhu TingYu on 2018/9/11.
 */

public class BloodBookModel {
    public static Observable<ApiResponse<BloodBookEntity>> getBloodBook4(String puid,String foodId, String pigeonId, boolean isNeedMatch) {
        return RequestData.<ApiResponse<BloodBookEntity>>build()
                .setToJsonType(new TypeToken<ApiResponse<BloodBookEntity>>() {
                }.getType())
                .url(R.string.get_blood_book_4)
                .addBody("puid", puid)
                .addBody("footid", foodId)
                .addBody("pigeonid", pigeonId)
                .addBody("bitmatch",isNeedMatch ? "1" : "0")//是否要查询赛绩（1：是，其他不是）
                .request();
    }
}
