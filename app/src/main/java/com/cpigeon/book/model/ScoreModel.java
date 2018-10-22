package com.cpigeon.book.model;

import com.base.http.ApiResponse;
import com.cpigeon.book.R;
import com.cpigeon.book.http.RequestData;
import com.google.gson.reflect.TypeToken;

import io.reactivex.Observable;

/**
 * Created by Administrator on 2018/10/20 0020.
 */

public class ScoreModel {


    //修改鸽子评分
    public static Observable<ApiResponse<Object>> getTXGP_Pigeon_UpdateScore(String pigeonid,
                                                                             String footid,
                                                                             String score) {
        return RequestData.<ApiResponse<Object>>build()
                .setToJsonType(new TypeToken<ApiResponse<Object>>() {
                }.getType())
                .url(R.string.pigeon_score_edit)
                .addBody("pigeonid", pigeonid)
                .addBody("footid", footid)
                .addBody("score", score)
                .request();
    }

}
