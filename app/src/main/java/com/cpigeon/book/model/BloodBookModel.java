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
    public static Observable<ApiResponse<BloodBookEntity>> getBloodBook4(String puid, String foodId, String pigeonId
            , boolean isNeedMatch, boolean isGoodPigeon) {
        return RequestData.<ApiResponse<BloodBookEntity>>build()
                .setToJsonType(new TypeToken<ApiResponse<BloodBookEntity>>() {
                }.getType())
                .url(R.string.get_blood_book_4)
                .addBody("puid", puid)
                .addBody("footid", foodId)
                .addBody("pigeonid", pigeonId)
                .addBody("bitmatch", isNeedMatch ? "1" : "0")//是否要查询赛绩（1：是，其他不是）
                .addBody("motto", isGoodPigeon ? "1" : "")//是否隐藏足环号（1：是，其他不是）
                .request();
    }

    public static Observable<ApiResponse> addParent(
            String pigeonId,//：鸽子id
            String footId,//:足环id
            String sexId,// ：性别ID
            String sonFootId,// ：子代足环id
            String sonPigeonId//：子代鸽子足环
    ) {
        return RequestData.<ApiResponse>build()
                .setToJsonType(new TypeToken<ApiResponse>() {
                }.getType())
                .url(R.string.add_book_parent)
                .addBody("pigeonid", pigeonId)
                .addBody("footid", footId)
                .addBody("sexid", sexId)
                .addBody("sonfootid", sonFootId)//是否要查询赛绩（1：是，其他不是）
                .addBody("sonpigeonid", sonPigeonId)//是否隐藏足环号（1：是，其他不是）
                .request();
    }


}
