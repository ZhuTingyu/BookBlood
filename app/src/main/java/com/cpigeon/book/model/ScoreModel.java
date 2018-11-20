package com.cpigeon.book.model;

import com.base.http.ApiResponse;
import com.cpigeon.book.R;
import com.cpigeon.book.http.RequestData;
import com.cpigeon.book.model.entity.PigeonScoreItemEntity;
import com.google.gson.reflect.TypeToken;

import java.util.List;

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


    public static Observable<ApiResponse<List<PigeonScoreItemEntity>>> getPigeonScoreItem() {
        return RequestData.<ApiResponse<List<PigeonScoreItemEntity>>>build()
                .setToJsonType(new TypeToken<ApiResponse<List<PigeonScoreItemEntity>>>() {
                }.getType())
                .url(R.string.get_pigeon_score_item)
                .request();
    }

    public static Observable<ApiResponse> setPigeonItemScore(
            String pigeonId,//：信鸽ID
            String scoreId,//：评分项目ID
            String score,//：分数字
            String allScore//：总评分
    ) {
        return RequestData.<ApiResponse>build()
                .setToJsonType(new TypeToken<ApiResponse>() {
                }.getType())
                .url(R.string.set_pigeon_item_score)
                .addBody("pid", pigeonId)
                .addBody("ids", scoreId)
                .addBody("fs", score)
                .addBody("zp", allScore)
                .request();
    }


}
